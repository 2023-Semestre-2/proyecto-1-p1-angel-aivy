package com.proyecto1.gestordeprocesos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MemoryRow {
    private final SimpleIntegerProperty index;
    private final SimpleStringProperty content;


    public MemoryRow(int index, String content) {
        this.index = new SimpleIntegerProperty(index);
        this.content = new SimpleStringProperty(content);
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public int getIndex() {
        return index.get();
    }

    public String getContent() {
        return content.get();
    }
}
