package com.proyecto1.gestordeprocesos;

public class MarcoDePagina {
    private Proceso proceso;
    private int numeroPagina;
    private int direccionInicio;

    public MarcoDePagina(Proceso proceso, int numeroPagina) {
        this.proceso = proceso;
        this.numeroPagina = numeroPagina;
    }
    public MarcoDePagina(int direccionInicio) {
        this.direccionInicio = direccionInicio;
    }

    public MarcoDePagina() {
        this.proceso = null;
        this.numeroPagina = -1;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
}
