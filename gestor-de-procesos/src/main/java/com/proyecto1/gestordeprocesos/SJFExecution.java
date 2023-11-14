package com.proyecto1.gestordeprocesos;

import com.proyecto1.gestordeprocesos.core.CPU;
import javafx.scene.control.TableView;

public class SJFExecution implements ExecutionAlgorithmStrategy {
    @Override
    public void execute(CPU cpu, TableView<EntradaEjecucion> tablaEjecucion) {
        cpu.ejecutarSJF(tablaEjecucion);
    }
}
