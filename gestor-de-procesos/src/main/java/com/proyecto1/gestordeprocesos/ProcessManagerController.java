package com.proyecto1.gestordeprocesos;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessManagerController {
    @FXML
    private Button loadFiles;
    @FXML
    private Button execute;

    @FXML
    private TableView<MemoryRow> memoryTable;
    @FXML
    private TableColumn<MemoryRow, Number> posColumn;
    @FXML
    private TableColumn<MemoryRow, String> contColumn;

    @FXML
    private TableView<DiskData> diskTable;
    @FXML
    private TableColumn<DiskData, Number> positionColumn;
    @FXML
    private TableColumn<DiskData, String> contentColumn;

    @FXML
    private TableView<ProcessRow> processTable;
    @FXML
    private TableColumn<ProcessRow, Number> processColumn;
    @FXML
    private TableColumn<ProcessRow, String> stateColumn;

    @FXML
    private TableView<RegisterRow> registerTable;
    @FXML
    private TableColumn<RegisterRow, String> registerColumn;
    @FXML
    private TableView<CPURow> cpuTable;
    @FXML
    private TableColumn<CPURow, String> cpuColumn;

    @FXML
    private TextArea stats;

    @FXML
    private Label config;

    private MiniComputer miniComputer;


    @FXML
    public void initialize() {
        miniComputer = new MiniComputer();

        positionColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
        contentColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
        posColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
        contColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());

        processColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
        stateColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());

        registerColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
        cpuColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());

        stats.setText("Estadísticas: \n");

        //Load memory config
        Helpers.setMemory_DiskConfig("config.txt", miniComputer, config);

    }

    @FXML
    private void onExecuteClick() {
        //todo: delete this after debugging
        System.out.println(miniComputer.getFileContentFromStorage(miniComputer.getPCB().getId()).toString());

        miniComputer.loadFileContentToMemory();

        updateMemoryTable();
//        miniComputer.execute(this.registerTable);
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                miniComputer.execute(registerTable, cpuTable, stats, processTable);
                return null;
            }
        };
        new Thread(task).start();
    }

    @FXML
    private void onLoadFilesClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ASM Files", "*.asm", "*.txt")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            miniComputer.setIndexSpaceSizeToStorage(selectedFiles.size());
            for (File file : selectedFiles) {
                System.out.println("File selected: " + file.getAbsolutePath());
                miniComputer.addFileToStorage(file);
            }
            miniComputer.setMemoryReservedSpace();
            updateDiskTable();
            miniComputer.addPCBToTable(this.processTable);
        } else {
            System.out.println("No files selected");
        }
    }

    private void updateDiskTable() {
        Helpers.updateDiskTable(diskTable, miniComputer.getStorage());
    }

    private void updateMemoryTable() {
        Helpers.updateMemoryTable(memoryTable, miniComputer.getMemory());
    }

    private void updateProcessTable() {
        //todo Helpers.updateProcessTable(memoryTable, miniComputer.getMemory());
    }
}
