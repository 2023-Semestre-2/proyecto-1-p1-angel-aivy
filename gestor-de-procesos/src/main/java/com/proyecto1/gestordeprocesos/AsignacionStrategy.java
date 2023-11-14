package com.proyecto1.gestordeprocesos;

import java.util.List;

public interface AsignacionStrategy {
    void asignarMemoria(Proceso proceso, MemoriaPrincipal memoriaPrincipal);

    void asignarMemoria(List<Proceso> procesos, Disco disco);
}
