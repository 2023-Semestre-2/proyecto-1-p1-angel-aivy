package com.proyecto1.gestordeprocesos;

import com.proyecto1.gestordeprocesos.core.CPU;
import javafx.scene.control.TableView;

public interface ExecutionAlgorithmStrategy {
    void execute(CPU cpu, TableView<EntradaEjecucion> tablaEjecucion);
}
