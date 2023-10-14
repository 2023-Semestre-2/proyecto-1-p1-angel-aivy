package com.proyecto1.gestordeprocesos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

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

    //
    public static void updateProcessTable(TableView<ProcessRow> processTable, Queue<PCB> workQueue) {
        ObservableList<ProcessRow> data = FXCollections.observableArrayList();

        //todo
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

    public static void setMemory_DiskConfig(String fileName, MiniComputer miniComputer, Label label) {
        Map<String, Integer> memoryConfig = loadMemoryConfig(fileName);
        // Obteniendo y asignando valores

        int memoriaPrincipal = memoryConfig.getOrDefault("memoriaPrincipal", -1);
        int disco = memoryConfig.getOrDefault("disco", -1);

        miniComputer.setSizeMemoryDisk(memoriaPrincipal, disco);

        label.setText("Memoria principal " + memoriaPrincipal + ", Disco " + disco);

        System.out.println("Memoria Principal: " + memoriaPrincipal);
        System.out.println("Disco: " + disco);
    }

    public static Map<String, Integer> loadMemoryConfig(String filename) {
        Map<String, Integer> config = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    config.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return config;
    }

}
