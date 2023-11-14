package com.proyecto1.gestordeprocesos;
import java.util.ArrayList;
import java.util.List;

public class TablaDePaginas {
    private List<MarcoDePagina> entradas;

    public TablaDePaginas(List<MarcoDePagina> entradas) {
        this.entradas = entradas;
    }
    public TablaDePaginas() {
        this.entradas = new ArrayList<>();
    }
    public List<MarcoDePagina> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<MarcoDePagina> entradas) {
        this.entradas = entradas;
    }

    public void agregarEntrada(MarcoDePagina marco) {
        entradas.add(marco);
    }
}
