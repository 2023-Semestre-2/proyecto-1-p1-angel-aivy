package com.proyecto1.gestordeprocesos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PCB implements MemoryData {
    private String id; //file name to identify it
    private static int count = 0;
    private State state;
    private int PC; //program counter
    private HashMap<String, Integer> registers;
    int[] stack = new int[5]; //no sure how this works. I think it's to store calculated values
    private int executingCPU;
    private long startTime;
    private long elapsedTime;
    private final List<String> openFiles = new ArrayList<>(); //I/O state information: opened files
    private PCB nextPCB;
    private int startAddress;
    private int processSize;
    private int priority;
    private int idNumber = 0;

    public PCB() {
        this.registers = new HashMap<>();
        this.registers.put("AC", 0);
        this.registers.put("AX", 0);
        this.registers.put("BX", 0);
        this.registers.put("CX", 0);
        this.registers.put("DX", 0);
        this.executingCPU = 1;
        count++;
    }


    public void setState(State state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public void addRegister(String key, int value) {
        this.registers.put(key, value);
    }

    public void setStack(int[] stack) {
        this.stack = stack;
    }

    public void setExecutingCPU(int executingCPU) {
        this.executingCPU = executingCPU;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getProcessSize() {
        return processSize;
    }

    public void setNextPCB(PCB nextPCB) {
        this.nextPCB = nextPCB;
    }

    public void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    public void setProcessSize(int processSize) {
        this.processSize = processSize;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStartAddress() {
        return startAddress;
    }

    @Override
    public String getData() {
        return "PCB: " + count + " " + id;
    }

    public int getCount() {
        return count;
    }

    public State getState() {
        return state;
    }
}
