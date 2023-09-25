package com.proyecto1.gestordeprocesos;

import javafx.beans.property.SimpleStringProperty;

public class RegisterRow {

    private final SimpleStringProperty content;

    public RegisterRow(String cont) {
        this.content = new SimpleStringProperty(cont);
    }

    public SimpleStringProperty contentProperty() {
        return this.content;
    }

}
