package com.proyecto1.gestordeprocesos;

import java.util.List;

public class Memory {
    private final int MEMORY_SIZE = 256;
    //    private final int PCB_MEMORY_SIZE = 15; // Space reserved to the PCBs
    private MemoryData[] memory = new MemoryData[MEMORY_SIZE]; //It's a generic type because we need to add two types of object
    private int reservedSpace; // Space reserved to the PCBs
    private int start = 0;


    public Memory() {}

    public void loadToMemory(PCB pcb, List<String> content) {
        if (reservedSpace < 1 || reservedSpace + content.size() >= MEMORY_SIZE) {
            System.out.println("There is no enough space");
            return;
        }

        memory[reservedSpace - 1] = pcb;

        for (int i = 0; i < content.size() && reservedSpace + i < MEMORY_SIZE; i++) {
            memory[reservedSpace + i] = new Instruction(content.get(i));
        }
    }

    public void loadToMemory(List<String> content) {

        for (int i = 0; i < content.size() && i < MEMORY_SIZE - start; i++) {
            memory[start + i] = new Instruction(content.get(i));
        }
    }

    public void loadToMemory(Instruction instruction, int memoryPos) {
        if (memoryPos <= reservedSpace || memoryPos >= MEMORY_SIZE) {
            throw new IllegalArgumentException("Invalid memory position: " + memoryPos);
        }

        memory[memoryPos] = instruction;
    }

    public MemoryData getInstruction(int memoryPos) {
        return this.memory[memoryPos];
    }

    public void setReservedSpace(int reservedMemorySize) {
        this.reservedSpace = reservedMemorySize;

        setStart(reservedMemorySize);// from here the loading instruction process will begin
    }

    public MemoryData[] getMemory() {
        return memory;
    }

    public void setStart(int start) {
        if (this.start == 0) {
            this.start = start;
        }
    }
}

