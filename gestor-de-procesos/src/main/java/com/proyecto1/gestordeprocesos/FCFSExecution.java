package com.proyecto1.gestordeprocesos;

import com.proyecto1.gestordeprocesos.core.CPU;
import javafx.scene.control.TableView;

public class FCFSExecution implements ExecutionAlgorithmStrategy {

    private Configuracion configuracion;
    private int tamanoPagina;

    public FCFSExecution() {
        this.configuracion = Configuracion.getInstance();
        this.tamanoPagina = configuracion.getPagesSize();
    }

    @Override
    public void execute(CPU cpu, TableView<EntradaEjecucion> tablaEjecucion) {
        cpu.ejecutarFCFS(tablaEjecucion);
    }
}
