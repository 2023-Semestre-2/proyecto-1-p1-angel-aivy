package com.proyecto1.gestordeprocesos;//package com.proyecto1.gestordeprocesos;

import com.proyecto1.gestordeprocesos.core.CPU;
import com.proyecto1.gestordeprocesos.modelos.EntradaBCP;
import com.proyecto1.gestordeprocesos.modelos.EntradaMemoria;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class SistemaOperativo {
    private Configuracion configuracion;
    private Disco disco;
    private MemoriaPrincipal memoriaPrincipal;
    private List<Proceso> procesos;

    private CPU[] CPUs;

    public SistemaOperativo() {
        this.configuracion = Configuracion.getInstance();
        this.disco = new Disco();
        this.CPUs = new CPU[configuracion.getCpuCount()];
        for (int i = 0; i < CPUs.length; i++) {
            CPUs[i] = new CPU(); // Inicializar cada CPU.
        }
        this.procesos = new ArrayList<>();
        this.memoriaPrincipal = new MemoriaPrincipal();
    }

    public void ejecutar(TableView<EntradaEjecucion> tablaEjecucion) {
        //asignar memoria
        switch (Configuracion.getInstance().getMemoryAllocation()) {
            case "Paginación (física y virtual)" -> {
                Paginacion memoryAllocation = (Paginacion) Configuracion.getInstance().getTipoAsignacion();
                for (Proceso proceso : procesos) {
                    memoryAllocation.asignarMemoria(proceso, memoriaPrincipal);
                }
            }
        }

        //ejecutar proceso segun algoritmo de ejecucion
        ExecutionAlgorithmStrategy algorithmStrategy = Configuracion.getInstance().getCpuExecutionStrategy();
        for (CPU cpu : CPUs) {
          algorithmStrategy.execute(cpu, tablaEjecucion);
        }
//        if (algorithmStrategy instanceof FCFSExecution) {
//            for (CPU cpu : CPUs) {
//                cpu.ejecutarProceso(tablaEjecucion);
//            }
//        } else if (algorithmStrategy instanceof SRTExecution) {
//            //Ordenar la lista de BCPs de cada CPU según el algoritmo SRT. Cada BCP tiene el proceso (una lista de instrucciones)
//            for (CPU cpu : CPUs) {
//                //ordenar la lista de BCP segun el algoritmo SRT
////                cpu.ordenarPorSRT();
////                cpu.ejecutarProceso(tablaEjecucion);
////                cpu.ejecutarSRT(tablaEjecucion);
//                algorithmStrategy.execute(cpu, tablaEjecucion);
//            }
//
//            //todo: borrar luego de pruebas
//            for (CPU cpu : CPUs) {
//                //ordenar la lista de BCP segun el algoritmo SRT
//                cpu.verProcesos();
//            }
//        }

    }

    public void cargarProcesosDisco(List<String> proceso) {
        disco.cargarProceso(proceso);
        Proceso nuevoProceso = new Proceso(proceso);
        BCP bcp = disco.crearBCP(nuevoProceso);
        bcp.setTiempoEnCPU();
        agregarBCPaCPU(bcp);

        this.procesos.add(nuevoProceso);// lo agrega a una lista de procesos
    }

    private void agregarBCPaCPU(BCP bcp) {
        for (CPU cpu : CPUs) {
            if (cpu.getBCPCount() < 5) {
                cpu.agregarBCP(bcp);
                return;
            }
        }
        System.out.println("Todas las CPUs están llenas. No se puede agregar el BCP.");
    }

    public void agregarToObservables() {
        for (CPU cpu : CPUs) {
            if (cpu.getBCPCount() < 5) {
                cpu.agregarToObservables();
                return;
            }
        }
    }

    public ObservableList<EntradaBCP> getObservableListaBCP_CPU(int numCPU) {
        System.out.println(CPUs[0]);
        return CPUs[numCPU].getObservables();
    }

    public ObservableList<EntradaMemoria> getObservablesMemoriaPrincipal() {
        return this.memoriaPrincipal.getObservables();
    }

    public Disco getDisco() {
        return disco;
    }
}
