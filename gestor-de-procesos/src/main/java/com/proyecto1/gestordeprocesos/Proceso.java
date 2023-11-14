package com.proyecto1.gestordeprocesos;

import java.util.ArrayList;
import java.util.List;

public class Proceso {
    private static int counter = 0;
    private int PID;
    private List<String> proceso;
    private List<Instruction> instrucciones;

    private TablaDePaginas tablaDePaginas;

    public Proceso(List<String> proceso) {
        this.proceso = proceso;
        this.instrucciones = new ArrayList<>();
        this.setInstrucciones();
        this.tablaDePaginas = new TablaDePaginas();

        this.PID = ++counter;
    }

    private void setInstrucciones() {
        for (String strInstruccion : proceso) {
            instrucciones.add(new Instruction(strInstruccion));
        }
    }

    public List<String> getProceso() {
        return proceso;
    }

    public List<Instruction> getInstrucciones() {
        return instrucciones;
    }

    public int getProcesoSize() {
        return this.proceso.size();
    }

    public TablaDePaginas getTablaDePaginas() {
        return tablaDePaginas;
    }

    public int getPID() {
        return PID;
    }

    public void setTablaDePaginas(TablaDePaginas tablaDePaginas) {
        this.tablaDePaginas = tablaDePaginas;
    }
}
