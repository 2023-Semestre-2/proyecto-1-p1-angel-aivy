package com.proyecto1.gestordeprocesos;

import com.proyecto1.gestordeprocesos.modelos.EntradaDisco;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Disco {
    //    private String[] almacenamiento;
    private Instruction[] almacenamiento;
    private ObservableList<EntradaDisco> observables;
    //    private AsignacionStrategy asignacionStrategy;
    //    private List<Proceso> procesos;
    private int ultimaPosicionAlmacenada = -1;


    public Disco() {
        this.almacenamiento = new Instruction[256];
        this.observables = FXCollections.observableArrayList();
//        this.asignacionStrategy = Configuracion.getInstance().getTipoAsignacion();
    }

    public void cargarProceso(List<String> contenidoProceso) {
        if (contenidoProceso.size() > almacenamiento.length) {
            System.out.println("No hay espacio suficiente en disco");
            return;
        }

        int posicionActual = ultimaPosicionAlmacenada + 1;

        for (int i = 0; i < contenidoProceso.size() && i + posicionActual + 1 < almacenamiento.length; i++) {
            String strInstruccion = contenidoProceso.get(i);
            almacenamiento[posicionActual] = new Instruction(strInstruccion);

            //Actualizar la lista observada
            observables.add(new EntradaDisco(posicionActual, strInstruccion));
            posicionActual++;
        }
        ultimaPosicionAlmacenada = posicionActual - 1;
    }

    public BCP cargarProcesoYCrearBCP(List<String> contenidoProceso) {
        // Guardar la posición inicial antes de cargar el proceso
        int inicio = ultimaPosicionAlmacenada + 1;

        cargarProceso(contenidoProceso);

        // Crear el BCP
        State estadoInicial = State.READY;  // El proceso estará listo para ser ejecutado
        int tiempoCPU = contenidoProceso.size();  // Tiempo en CPU basado en el tamaño del contenido
        int PC = inicio;  // PC es la dirección inicial
        int registro = 0;  // Valor inicial
        int AC = 0;  // Valor inicial del acumulador
        int dirInicio = inicio;
        int dirFin = ultimaPosicionAlmacenada;

        return new BCP(estadoInicial, tiempoCPU, PC, registro, AC, dirInicio, dirFin, new Proceso(contenidoProceso));
    }

    public BCP crearBCP(Proceso proceso) {
        int inicio = getUltimaPosicionAlmacenada() + 1 - proceso.getProcesoSize();
        int fin = getUltimaPosicionAlmacenada();

        State estadoInicial = State.READY;
        int tiempoCPU = proceso.getProcesoSize();
        int registro = 0;
        int AC = 0;
        int PC = inicio;

        return new BCP(estadoInicial, tiempoCPU, PC, registro, AC, inicio, fin, proceso);
    }


    public int getUltimaPosicionAlmacenada() {
        return ultimaPosicionAlmacenada;
    }

    // Getter para la lista observable
    public ObservableList<EntradaDisco> getObservables() {
        return observables;
    }

}
