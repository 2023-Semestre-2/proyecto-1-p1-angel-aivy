package com.proyecto1.gestordeprocesos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MiniComputer {
    private final Storage storage;
    private final Queue<PCB> workQueue;//List of PCBs associated to each files

    private Memory memory;
    private CPU cpu;
    private final int PCB_DEFAULT_SIZE = 15;

    List<String> allStats = new ArrayList<>();

    public MiniComputer() {
        this.storage = new Storage();
        this.workQueue = new LinkedList<>();
        this.memory = new Memory();
        this.cpu = new CPU();
    }

    public void setIndexSpaceSizeToStorage(int size) {
        storage.setIndexSpaceSize(size);
    }

    public void addFileToStorage(File file) {
        try {
            String fileName = file.getName();
            List<String> fileContent = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            storage.addFile(fileName, fileContent);
            //storage.printStorageContent();
            createAndAddPCB(fileName, fileContent);
        } catch (IOException e) {
            System.out.print("Error reading file: " + e.getMessage());
        }
    }

    public void setMemoryReservedSpace() {
        int reservedSpace = workQueue.size() * PCB_DEFAULT_SIZE;
        memory.setReservedSpace(reservedSpace);
        memory.setStart(reservedSpace);
    }

    public List<String> getFileContentFromStorage(String fileName) {
        return this.storage.getFileContent(fileName);
    }

    public void loadFileContentToMemory() {
        PCB pcb = getPCB();
        List<String> fileContent = getFileContentFromStorage(pcb.getId());
        memory.loadToMemory(pcb, fileContent);
//        memory.loadToMemory();

    }

    public void createAndAddPCB(String fileName, List<String> fileContent) {
        PCB pcb = new PCB();
        pcb.setState(State.NEW);
        pcb.setPC(0);
        pcb.setExecutingCPU(-1);
        pcb.setStartTime(System.currentTimeMillis());
        pcb.setElapsedTime(0);
        pcb.setNextPCB(null);
        pcb.setStartAddress(0);
        pcb.setProcessSize(fileContent.size());
        pcb.setPriority(0);
        pcb.setId(fileName);
        pcb.setIdNumber(pcb.getCount());
        //todo: handle opened files
        //cb.setOpenFiles

        workQueue.add(pcb);
        //agregar a la tabla UI
    }

    public void addPCBToTable(TableView<ProcessRow> processTable) {
        ObservableList<ProcessRow> data = FXCollections.observableArrayList();

        int index = 1;
        for (PCB pcb : this.workQueue) {
            System.out.println(index + " - " + pcb.getId());
            data.add(new ProcessRow(index, pcb.getState().toString()));
            index++;
        }

        processTable.setItems(data);
    }

    public void execute(TableView<RegisterRow> table, TableView<CPURow> cpuTable, TextArea textField, TableView<ProcessRow> processTable) {
        for (PCB pcb : workQueue) {
            pcb.setState(State.RUNNING);
            addPCBToTable(processTable);
            String stats = this.cpu.executeInstruction(this.memory, pcb, table, cpuTable);
            pcb.setState(State.FINISHED);
            this.allStats.add(stats);
            for (String processStats : allStats) {
                System.out.println(processStats);
                textField.appendText(processStats);
            }
            System.out.println(workQueue.peek().getState());
            addPCBToTable(processTable);
//            workQueue.poll();
        }
    }

    public Storage getStorage() {
        return this.storage;
    }

    public PCB getPCB() {
        return this.workQueue.peek();
    }

    public Memory getMemory() {
        return memory;
    }

    public Queue<PCB> getWorkQueue() {
        return workQueue;
    }
}
