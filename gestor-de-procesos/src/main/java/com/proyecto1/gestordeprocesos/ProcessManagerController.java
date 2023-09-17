package com.proyecto1.gestordeprocesos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class ProcessManagerController {
    @FXML
    private Button loadFiles;

    @FXML
    private TableView<DiskData> diskTable;
    @FXML
    private TableColumn<DiskData, Number> positionColumn;
    @FXML
    private TableColumn<DiskData, String> contentColumn;

    private MiniComputer miniComputer;


    @FXML
    public void initialize() {
        miniComputer = new MiniComputer();

        positionColumn.setCellValueFactory(cellData -> cellData.getValue().indexProperty());
        contentColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());

//        Helpers.colorRowsInRange(diskTable, 0, 64 + 2, "indianRed");

    }

    @FXML
    private void onLoadFilesClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ASM Files", "*.asm")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            miniComputer.setIndexSpaceSizeToStorage(selectedFiles.size());
            for (File file : selectedFiles) {
                System.out.println("File selected: " + file.getAbsolutePath());
                miniComputer.addFileToStorage(file);
            }
            updateDiskTable();
        } else {
            System.out.println("No files selected");
        }
    }

    private void updateDiskTable() {
        Helpers.updateDiskTable(diskTable, miniComputer.getStorage());
    }
}
