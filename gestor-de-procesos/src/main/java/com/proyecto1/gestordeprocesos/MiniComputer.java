package com.proyecto1.gestordeprocesos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MiniComputer {
    private final Storage storage;
    private Queue<PCB> workQueue;//List of PCBs associated to each files
    private Memory memory;

    private final int PCB_DEFAULT_SIZE = 15;

    public MiniComputer() {
        this.storage = new Storage();
        this.workQueue = new LinkedList<>();
        this.memory = new Memory();
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
        //todo: handle opened files
        //cb.setOpenFiles

        workQueue.add(pcb);
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
