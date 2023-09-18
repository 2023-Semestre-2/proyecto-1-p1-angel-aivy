package com.proyecto1.gestordeprocesos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class Helpers {
    /**
     * Updates the given TableView with the data from the storage array.
     *
     * @param diskTable the TableView to update
     * @param storage   the Storage instance containing the data
     */
    public static void updateDiskTable(TableView<DiskData> diskTable, Storage storage) {
        // Get the data from the storage array
        String[] diskStorage = storage.getDiskStorage();

        // Create an observable list to hold the data for the TableView
        ObservableList<DiskData> data = FXCollections.observableArrayList();

        // Loop through the storage array and create DiskData objects for each non-null entry
        for (int i = 0; i < diskStorage.length; i++) {
            if (diskStorage[i] != null) {
                data.add(new DiskData(i, diskStorage[i]));
            }
        }

        // Set the data in the TableView
        diskTable.setItems(data);
    }
    public static void updateMemoryTable(TableView<MemoryRow> memoryTable, Memory memory) {
        // Get the data from the storage array
        MemoryData[] mainMemory = memory.getMemory();

        // Create an observable list to hold the data for the TableView
        ObservableList<MemoryRow> data = FXCollections.observableArrayList();

        // Loop through the storage array and create DiskData objects for each non-null entry
        for (int i = 0; i < mainMemory.length; i++) {
            if (mainMemory[i] != null) {
                data.add(new MemoryRow(i, mainMemory[i].getData()));
            }
        }

        // Set the data in the TableView
        memoryTable.setItems(data);
    }


    public static void colorRowsInRange(TableView<DiskData> table, int startIndex, int endIndex, String color) {
        table.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(DiskData item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setStyle("");
                } else if (item.getIndex() >= startIndex && item.getIndex() <= endIndex) {
                    setStyle("-fx-background-color: " + color + ";");
                } else {
                    setStyle("");
                }
            }
        });
    }

}
