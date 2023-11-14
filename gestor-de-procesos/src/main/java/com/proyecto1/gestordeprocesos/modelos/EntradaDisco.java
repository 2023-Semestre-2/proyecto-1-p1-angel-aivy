package com.proyecto1.gestordeprocesos.modelos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EntradaDisco {
    private SimpleIntegerProperty pos;
    private SimpleStringProperty valor;

    public EntradaDisco(int pos, String valor) {
        this.pos = new SimpleIntegerProperty(pos);
        this.valor = new SimpleStringProperty(valor);
    }

    public int getPos() {
        return pos.get();
    }

    public String getValor() {
        return valor.get();
    }

    public SimpleIntegerProperty getPosProperty() {
        return pos;
    }

    public SimpleStringProperty getValorProperty() {
        return valor;
    }
}
