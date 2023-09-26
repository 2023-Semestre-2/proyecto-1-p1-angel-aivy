package com.proyecto1.gestordeprocesos;

import javafx.beans.property.SimpleStringProperty;

public class CPURow {
    private final SimpleStringProperty content;

    public CPURow(String content) {
        this.content = new SimpleStringProperty(content);
    }

    public SimpleStringProperty contentProperty() {
        return this.content;
    }
}
