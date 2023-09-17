package com.proyecto1.gestordeprocesos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DiskData {
    private final SimpleIntegerProperty index;
    private final SimpleStringProperty content;

    /**
     * Creates a new DiskData object with the given index and content.
     *
     * @param index   the index in the storage array
     * @param content the content at that index in the storage array
     */
    public DiskData(int index, String content) {
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
