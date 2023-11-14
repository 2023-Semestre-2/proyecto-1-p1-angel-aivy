package com.proyecto1.gestordeprocesos.core;

import com.proyecto1.gestordeprocesos.BCP;
import com.proyecto1.gestordeprocesos.Instruction;
import com.proyecto1.gestordeprocesos.modelos.EntradaBCP;
import com.proyecto1.gestordeprocesos.EntradaEjecucion;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

enum SortType {
    TIEMPO_RESTANTE, TIEMPO_LLEGADA
}

public class CPU {
    private ListaBCP listaBCP;
    ObservableList<EntradaBCP> observables;

    private int nextColumnNumber = 1;
    private int globalTime = 1;

    public CPU() {
        this.listaBCP = new ListaBCP();
        this.observables = FXCollections.observableArrayList();
    }

    public void agregarBCP(BCP bcp) {
        this.listaBCP.addBCP(bcp);
    }

    public void agregarToObservables() {
        BCP actual = this.listaBCP.getHead();
        while (actual != null) {
            boolean existeEnObservables = false;

            // Recorrer la lista observables para ver si el BCP actual ya está listado
            for (EntradaBCP entrada : this.observables) {
                if (entrada.getPid() == actual.getPID()) {
                    existeEnObservables = true;
                    break; // Si encontramos el PID, no necesitamos seguir buscando
                }
            }

            // Si el BCP actual no está en observables, agregarlo
            if (!existeEnObservables) {
                EntradaBCP nuevaEntrada = new EntradaBCP(
                        actual.getPID(),
                        actual.getEstado().toString(),
                        actual.getTiempoCPU(),
                        actual.getPC(),
                        actual.getRegistroName(),
                        actual.getAC(),
                        actual.getDirInicio(),
                        actual.getDirFin(),
                        actual.getSigBCPDir()
                );
                this.observables.add(nuevaEntrada);
            }

            // Pasar al siguiente BCP en la lista
            actual = actual.getSigBCP();
        }
    }

    // Método para ordenar los BCPs por SRT
    public void ordenarPorAlgoritmo(SortType sortType) {
        // Convierte tlau ListaBCP a un ArrayList para poder ordenarlo
        ArrayList<BCP> listaOrdenada = new ArrayList<>();

        // Iterar sobre ListaBCP y obtener todos los BCPs
        BCP actual = this.listaBCP.getHead();
        while (actual != null) {
            System.out.println("------ Antes del sort ------");
            System.out.println("PID: " + actual.getPID() + " T.LL: " + actual.getTiempoLlegada());
            listaOrdenada.add(actual);
            actual = actual.getSiguienteBCP();
        }

        if (sortType == SortType.TIEMPO_RESTANTE) {
            // Ordena el ArrayList basado en el tiempo restante
            Collections.sort(listaOrdenada, Comparator.comparingInt(BCP::getTiempoRestante));
        } else if (sortType == SortType.TIEMPO_LLEGADA) {
            // Ordena el ArrayList basado en el tiempo de llegada
            Collections.sort(listaOrdenada, Comparator.comparingInt(BCP::getTiempoLlegada));
        }

        // Ahora reconstruir la ListaBCP con esta nueva lista ordenada
        if (!listaOrdenada.isEmpty()) {
            this.listaBCP.setHead(listaOrdenada.get(0));
            BCP bcp = this.listaBCP.getHead();
            for (int i = 1; i < listaOrdenada.size(); i++) {
                bcp.setSiguienteBCP(listaOrdenada.get(i));
                bcp = bcp.getSigBCP();
            }
            // El último BCP en la lista apunta a null
            bcp.setSiguienteBCP(null);
        }
    }

    public void ejecutarProceso(TableView<EntradaEjecucion> tablaEjecucion) {
        //cada bcp tiene el proceso asignado. Necesitamos recorrer cada BCP, leer su proceso y ejecutar cada linea
        BCP bcpActual = this.listaBCP.getHead();
        while (bcpActual != null) {
            final int pid = bcpActual.getPID();
            EntradaEjecucion entradaEjecucion = new EntradaEjecucion(pid);

            //Agregar la fila a la tabla
            Platform.runLater(() -> {
                tablaEjecucion.getItems().add(entradaEjecucion);
                tablaEjecucion.scrollTo(entradaEjecucion); // auto-scroll hacia la nueva fila.
            });

            List<Instruction> instructions = bcpActual.getProceso().getInstrucciones();
            for (int index = 0; index < instructions.size(); index++) {
                Instruction instruccion = instructions.get(index);
                ejecutarLinea(instruccion, bcpActual);

//                final int tiempo = index + 1;
                final int tiempo = index == 0 ? bcpActual.getTiempoLlegada() : bcpActual.getTiempoLlegada() + index;
                //Actualizar la UI con el progreso de la instrucción
                Platform.runLater(() -> {
                    entradaEjecucion.setTiempo(tiempo, "X");
                    tablaEjecucion.refresh();
                });
                // Espera un segundo antes de continuar
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            bcpActual = bcpActual.getSiguienteBCP();
        }
    }

    public void ejecutarSRT(TableView<EntradaEjecucion> tablaEjecucion) {
        this.agregarProcesosUI(tablaEjecucion);

        //Ahora ejecuta según el orden del algoritmo, primero ordena la cola
        this.ordenarPorAlgoritmo(SortType.TIEMPO_RESTANTE);

        this.ejecutar(tablaEjecucion);

    }

    public void ejecutarSJF(TableView<EntradaEjecucion> tablaEjecucion) {

    }

    public void ejecutarFCFS(TableView<EntradaEjecucion> tablaEjecucion) {
        this.agregarProcesosUI(tablaEjecucion);
        this.ordenarPorAlgoritmo(SortType.TIEMPO_LLEGADA);

        this.ejecutar(tablaEjecucion);
    }

    private void executeInstructions(BCP bcpActual, TableView<EntradaEjecucion> tablaEjecucion) {
        if (bcpActual == null) return;

        final int pid = bcpActual.getPID();
        List<Instruction> instructions = bcpActual.getProceso().getInstrucciones();
        AtomicReference<Integer> instructionIndex = new AtomicReference<>(0);
        AtomicReference<Integer> tiempo = new AtomicReference<>(bcpActual.getTiempoLlegada());

        // Runnable para ejecutar cada instrucción con un segundo de intervalo
        Runnable executeInstruction = new Runnable() {
            @Override
            public void run() {
                if (instructionIndex.get() < instructions.size()) {
                    Instruction instruccion = instructions.get(instructionIndex.getAndSet(instructionIndex.get() + 1));
                    ejecutarLinea(instruccion, bcpActual);

                    EntradaEjecucion entradaEjecucion = findEntradaEjecucionByPid(tablaEjecucion, pid);
                    if (entradaEjecucion != null) {
                        Platform.runLater(() -> {
                            entradaEjecucion.setTiempo(tiempo.get(), "X");
                            tablaEjecucion.refresh();
                        });
                    }

                    tiempo.set(tiempo.get() + 1);

                    // Espera un segundo antes de ejecutar la siguiente instrucción
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(e -> run());
                    pause.play();
                } else {
                    // Ejecuta el siguiente proceso una vez que todas las instrucciones se han completado
                    Platform.runLater(() -> executeInstructions(bcpActual.getSiguienteBCP(), tablaEjecucion));
                }
            }
        };

        // Inicia la ejecución del primer proceso
        Platform.runLater(executeInstruction);
    }

    private void executeInstruction(BCP bcp, TableView<EntradaEjecucion> tablaEjecucion, int instructionIndex) {
        if (bcp == null) {
            // No hay más procesos para ejecutar
            return;
        }

        final int pid = bcp.getPID();
        List<Instruction> instructions = bcp.getProceso().getInstrucciones();

        if (instructionIndex < instructions.size()) {
            Instruction instruccion = instructions.get(instructionIndex);
            ejecutarLinea(instruccion, bcp);

            int tiempo = bcp.getTiempoLlegada() + instructionIndex;
            EntradaEjecucion entradaEjecucion = findEntradaEjecucionByPid(tablaEjecucion, pid);

            if (entradaEjecucion != null) {
                // Actualizar la UI con el progreso de la instrucción
                final int finalTiempo = tiempo;
                Platform.runLater(() -> {
                    entradaEjecucion.setTiempo(finalTiempo, "X");
                    tablaEjecucion.refresh();
                });
            }

            // Configurar el Timeline para esperar un segundo antes de ejecutar la siguiente instrucción
            Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.seconds(1),
                            event -> executeInstruction(bcp, tablaEjecucion, instructionIndex + 1)
                    )
            );
            timeline.play();
        } else {
            // Si ya no hay más instrucciones, pasa al siguiente BCP
            executeInstruction(bcp.getSiguienteBCP(), tablaEjecucion, 0);
        }
    }

    private EntradaEjecucion findEntradaEjecucionByPid(TableView<EntradaEjecucion> tablaEjecucion, int pid) {
        for (EntradaEjecucion entrada : tablaEjecucion.getItems()) {
            if (entrada.getPid() == pid) {
                System.out.println("found: " + entrada.getPid());
                return entrada;
            }
        }
        return null;
    }

    public void agregarProcesosUI(TableView<EntradaEjecucion> tablaEjecucion) {
        BCP bcpActual = this.listaBCP.getHead();
        //Agrega todos los procesos a la UI
        ObservableList<EntradaEjecucion> data = FXCollections.observableArrayList();
        while (bcpActual != null) {
            int pid = bcpActual.getPID();
//            System.out.println("Agregando PID: " + pid); // Esto confirmará qué PIDs se están agregando.

//            EntradaEjecucion entradaEjecucion = new EntradaEjecucion(pid);
//            tablaEjecucion.getItems().add(entradaEjecucion);

            data.add(new EntradaEjecucion(pid));

//            // Obtén el siguiente BCP antes de imprimir para evitar NullPointerException
//            BCP siguiente = bcpActual.getSiguienteBCP();
//            if (siguiente != null) {
//                System.out.println("next " + siguiente.getPID());
//            } else {
//                System.out.println("No hay más procesos en la cola.");
//            }

            bcpActual = bcpActual.getSiguienteBCP(); // Avanza al siguiente BCP
        }
        tablaEjecucion.setItems(data);
    }


    public void ejecutar(TableView<EntradaEjecucion> tablaEjecucion) {
        BCP bcpActual = this.listaBCP.getHead();
        System.out.println("------ despues del sort ------");
        System.out.println("PID " + bcpActual.getPID() +" T.ll: "+ bcpActual.getTiempoLlegada());

        EntradaEjecucion filaToModify;
        while (bcpActual != null) {
            final int pid = bcpActual.getPID();

            filaToModify = findEntradaEjecucionByPid(tablaEjecucion, pid);
            List<Instruction> instructions = bcpActual.getProceso().getInstrucciones();

            int tiempo = bcpActual.getTiempoLlegada();

            for (int index = 0; index < instructions.size(); index++) {
                Instruction instruccion = instructions.get(index);
                ejecutarLinea(instruccion, bcpActual);

                if (filaToModify != null) {
                    final int finalTiempo = tiempo;
                    final EntradaEjecucion finalEntradaEjecucion = filaToModify;
                    // Actualizar la UI con el progreso de la instrucción
                    Platform.runLater(() -> {
                        System.out.println("Actualizando UI en hilo " + Thread.currentThread().getId() +
                                " para PID " + pid + " en tiempo " + finalTiempo);
                        finalEntradaEjecucion.setTiempo(finalTiempo, "X");
                        tablaEjecucion.refresh();
                    });
                }
                tiempo++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bcpActual = bcpActual.getSiguienteBCP();
        }
    }

    private void agregarColumna(TableView<EntradaEjecucion> tablaEjecucion, ObservableList<EntradaEjecucion> data) {
        tablaEjecucion.setItems(data);
    }

    public void ejecutarLinea(Instruction instruction, BCP bcpActual) {
        String operator = instruction.getOperator();
        String registroKey = instruction.getRegister();
        String registroKey2 = instruction.getRegister2();
        int valor = instruction.getValue();

        switch (operator) {
            case "MOV" -> {
                if (registroKey2 != null) {
                    int valorFromRegister = bcpActual.getOrDefaultRegsitro(registroKey2, 0);
                    bcpActual.putRegistro(registroKey, valorFromRegister);
                } else {
                    bcpActual.putRegistro(registroKey, instruction.getValue());
                }
            }
            case "LOAD" -> // LOAD
//                    this.AC = this.registers.getOrDefault(registroKey, 0);
                    bcpActual.setAC(bcpActual.getOrDefaultRegsitro(registroKey, 0));
            case "ADD" -> // ADD
//                    this.AC += this.registers.getOrDefault(registroKey, 0);
                    bcpActual.setAC(bcpActual.getOrDefaultRegsitro(registroKey, 0));
            case "SUB" -> // SUB
//                    this.AC -= this.registers.getOrDefault(registroKey, 0);
                    bcpActual.setAC(bcpActual.getOrDefaultRegsitro(registroKey, 0));
            case "STORE" -> // STORE
//                    this.registers.put(registroKey, this.AC);
                    bcpActual.setAC(bcpActual.getOrDefaultRegsitro(registroKey, 0));
            case "INC" -> { // Increment in 1
                if (registroKey == null) {
//                    this.AC = this.AC + 1;
                    bcpActual.setAC(bcpActual.getAC() + 1);
                } else {
//                    this.registers.put(registroKey, this.registers.getOrDefault(registroKey, 0) + 1);
                    bcpActual.putRegistro(registroKey, bcpActual.getOrDefaultRegsitro(registroKey, 0) + 1);
                }
            }
            case "DEC" -> { // Decrement in 1
                if (registroKey == null) {
//                    this.AC = this.AC - 1;
                    bcpActual.setAC(bcpActual.getAC() - 1);
                } else {
//                    this.registers.put(registroKey, this.registers.getOrDefault(registroKey, 1) - 1);
                    bcpActual.putRegistro(registroKey, bcpActual.getOrDefaultRegsitro(registroKey, 1) - 1);
                }
            }
            case "SWAP" -> { //Swap values between two registers
//                int temp = this.registers.getOrDefault(registroKey, 0);
                int temp = bcpActual.getOrDefaultRegsitro(registroKey, 0);
//                this.registers.put(registroKey, this.registers.getOrDefault(registroKey2, 0));
                bcpActual.putRegistro(registroKey, bcpActual.getOrDefaultRegsitro(registroKey2, 0));
//                this.registers.put(registroKey2, this.registers.getOrDefault(temp, 0));
                bcpActual.putRegistro(registroKey2, bcpActual.getOrDefaultRegsitro(Integer.toString(temp), 0));
            }
            default -> System.out.println("Instruccion desconocida!");
        }
    }

    public int getBCPCount() {
        return this.listaBCP.getSize();
    }


    public ObservableList<EntradaBCP> getObservables() {
        return observables;
    }

    public void verProcesos() {
        BCP actual = this.listaBCP.getHead();
        while (actual != null) {
            System.out.println("Proceso ID: " + actual.getPID());
            System.out.println("Tiempo restante: " + actual.getTiempoRestante());
            actual = actual.getSiguienteBCP();
        }
    }


}
