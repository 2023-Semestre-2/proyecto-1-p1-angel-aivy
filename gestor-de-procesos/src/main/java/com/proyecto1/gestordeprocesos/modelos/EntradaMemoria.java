package com.proyecto1.gestordeprocesos.modelos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EntradaMemoria {
    private SimpleStringProperty procesoId;
    private SimpleIntegerProperty pos;
    private SimpleStringProperty valor;

    public EntradaMemoria(int pos, String valor, String procesoId) {
        this.pos = new SimpleIntegerProperty(pos);
        this.valor = new SimpleStringProperty(valor);
        this.procesoId = new SimpleStringProperty(procesoId);
    }

    public int getPos() {
        return pos.get();
    }

    public String getValor() {
        return valor.get();
    }
    public String getProcesoId() { return procesoId.get(); }

    public SimpleStringProperty getProcesoIdProperty() { return procesoId; }

    public SimpleIntegerProperty getPosProperty() {
        return pos;
    }

    public SimpleStringProperty getValorProperty() {
        return valor;
    }
}
