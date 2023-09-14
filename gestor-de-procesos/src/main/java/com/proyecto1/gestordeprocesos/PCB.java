package com.proyecto1.gestordeprocesos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PCB {
    private State state;
    private int PC; //program counter
    private HashMap<String, Integer> registers;
    int[] stack = new int[5];
    private int executingCPU;
    private long startTime;
    private long elapsedTime;
    private final List<String> openFiles = new ArrayList<>(); //no sure how this works
    private PCB nextPCB;
    private int startAddress;
    private int processSize;
    private int priority;

}
