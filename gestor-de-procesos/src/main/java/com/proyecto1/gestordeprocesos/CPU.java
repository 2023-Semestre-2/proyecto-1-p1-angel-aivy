package com.proyecto1.gestordeprocesos;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CPU {
    Queue<Instruction> queue = new LinkedList<>() {
    };
    Stack<Integer> stack = new Stack<>();
    int AC; // Acumulador
    int PC; // Program Counter
    HashMap<String, Integer> registers;
//    HashMap<String, Integer> labelToInstructionPointerMap = new HashMap<>();

    public CPU() {
        registers = new HashMap<>();
    }

    public void printRegistros() {
        System.out.printf(registers.toString());
    }

    public void agregarRegistro(String registro, int valor) {
        this.registers.put(registro, valor);
    }

//    private void buildLabelToInstructionPointerMap(Instruction[] program) {
//        for (int i = 0; i < program.length; i++) {
//            Instruction instr = program[i];
//            if (instr.getOperator() == null) {
//                labelToInstructionPointerMap.put(instr.getLabel(), i);
//            }
//        }
//    }


    public void updateRegisterTable(TableView<RegisterRow> registerTable, String content) {
        ObservableList<RegisterRow> data = FXCollections.observableArrayList();
        data.add(new RegisterRow(content));

        registerTable.setItems(data);
    }

    public void updateCPUTable(TableView<CPURow> cpuTableView, String content) {
        ObservableList<CPURow> data = cpuTableView.getItems();

        if (data == null) {
            data = FXCollections.observableArrayList();
            cpuTableView.setItems(data);
        }
        data.add(new CPURow(content));
//        cpuTableView.setItems(data);
    }


    //Execute all instructions
    public String executeInstruction(Memory memory, PCB pcb, TableView<RegisterRow> regTable, TableView<CPURow> cpuTable) {

        System.out.println("------from CPU---------");
        if (Platform.isFxApplicationThread()) {
            System.out.println("Hilo principal de la UI");
        } else {
            System.out.println("Hilo separado");
        }

        System.out.println(pcb.getIdNumber() + " - " + pcb.getId());

        MemoryData[] mainMemory = memory.getMemory();
        int startAddress = pcb.getStartAddress();
        pcb.setStartTime(LocalTime.now());
        pcb.setProcessName("P:" + pcb.getIdNumber());
        System.out.println("right before for:" + pcb.getId());
        System.out.println("start address: " + startAddress+ " process size: " + pcb.getProcessSize() + "end memory: " + startAddress+pcb.getProcessSize());

        for (int i = startAddress; i < startAddress + pcb.getProcessSize(); i++) {
            this.PC = i;
            Instruction instruction = new Instruction(mainMemory[i].getData());
            this.operatorCalc(instruction);

            Platform.runLater(() -> {
                updateRegisterTable(regTable, "PC: " + this.PC + "\n" + "IR: " + instruction.getData() + "\n" + "AC: " + this.AC);
                regTable.refresh();
                updateCPUTable(cpuTable, "P" + pcb.getIdNumber() + ": " + instruction.getWeight());
                System.out.println("P" + pcb.getIdNumber() + ": " + instruction.getWeight());
            });

            System.out.println(instruction.getData());
            System.out.println("---------\n" + "PC: " + this.PC + "\n" + "IR: " + instruction.getData() + "\n" + "AC: " + this.AC);
            try {
                Thread.sleep(instruction.getWeight() * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pcb.setEndTime(LocalTime.now());
        System.out.println("------from CPU---------");
        return pcb.getStats();
    }

    public int[] ejecutarInstruction(int memoryPos, Memory memory) {
//        this.PC = memoryPos;
//        Instruction instruction = memory.getInstruction(memoryPos);
//        int valor = instruction.getValue();
//        if (valor == 0){
//            valor = registers.getOrDefault(instruction.getRegister(), 0);
//        }
//        registers.put(instruction.getRegister(), valor);
//
//        operatorCalc(instruction);
//
//        int[] resultArray = new int[7];
//        resultArray[0] = this.PC;
//        resultArray[1] = instruction.getBinaryStringToDecimal();
//        resultArray[2] = this.AC;
//        resultArray[3] = registers.getOrDefault("AX", 0);
//        resultArray[4] = registers.getOrDefault("BX", 0);
//        resultArray[5] = registers.getOrDefault("CX", 0);
//        resultArray[6] = registers.getOrDefault("DX", 0);
//
//
//
//        return resultArray;
        return null;
    }

    public void operatorCalc(Instruction instruccion) {
        String operator = instruccion.getOperator();
        String registroKey = instruccion.getRegister();
        String registroKey2 = instruccion.getRegister2();
        int valor = instruccion.getValue();
//        boolean comparisonFlag = false;

        switch (operator) {
            case "MOV" -> // MOV
//                    this.registers.put(registroKey, valor);
            {
                if (registroKey2 != null) {
                    int valorFromRegister = this.registers.getOrDefault(registroKey2, 0);
                    this.registers.put(registroKey, valorFromRegister);
                } else {
                    this.registers.put(registroKey, instruccion.getValue());
                }
            }
            case "LOAD" -> // LOAD
                    this.AC = this.registers.getOrDefault(registroKey, 0);
            case "ADD" -> // ADD
                    this.AC += this.registers.getOrDefault(registroKey, 0);
            case "SUB" -> // SUB
                    this.AC -= this.registers.getOrDefault(registroKey, 0);
            case "STORE" -> // STORE
                    this.registers.put(registroKey, this.AC);
            case "INC" -> { // Increment in 1
                if (registroKey == null) {
                    this.AC = this.AC + 1;
                } else {
                    this.registers.put(registroKey, this.registers.getOrDefault(registroKey, 0) + 1);
                }

            }
            case "DEC" -> { // Decrement in 1
                if (registroKey == null) {
                    this.AC = this.AC - 1;
                } else {
                    this.registers.put(registroKey, this.registers.getOrDefault(registroKey, 1) - 1);
                }

            }
            case "SWAP" -> { //Swap values between two registers
                int temp = this.registers.getOrDefault(registroKey, 0);
                this.registers.put(registroKey, this.registers.getOrDefault(registroKey2, 0));
                this.registers.put(registroKey2, this.registers.getOrDefault(temp, 0));
            }
            case "INT" -> {
                if (registroKey.equals("20H")) {
                    //Finaliza el programa
                    //queue.clear();
                } else if (registroKey.equals("10H")) {
                    //Imprime valor de DX
                    int tempValue = this.registers.getOrDefault("DX", 0);
                    // print in screen
                } else if (registroKey.equals("09H")) {
                    //Input from keyboard 0-255, saves in DX and ends with ENTER
                    int tempVal = 0; // has to be a keyboard input
                    if (tempVal >= 0 && tempVal <= 255) {
                        this.registers.put("DX", tempVal);
                    }
                }
            }
            case "JMP" -> {
                //PC = labelToInstructionPointerMap.get(registroKey) - 1;
            }
            case "CMP" -> {
//                int value1 = this.registers.getOrDefault(registroKey, 0);
//                int value2 = registroKey2 != null ? this.registers.getOrDefault(registroKey2, 0) : valor;
//                comparisonFlag = value1 == value2;
            }
            case "JNE" -> {
//                if (!comparisonFlag) {
//                    PC = labelToInstructionPointerMap.get(registroKey) - 1;  // -1 porque al final del loop el PC incrementará
//                }
            }
            case "JE" -> {
//                if (comparisonFlag) {
//                    PC = labelToInstructionPointerMap.get(registroKey) - 1;  // -1 porque al final del loop el PC incrementará
//                }
            }
            case "PARAM" -> {
            }
            case "PUSH" -> //Saves in the stack value in AX register
                    stack.push(this.registers.getOrDefault("AX", 0));
            case "POP" -> //Saves the value from the stack to the register
                    this.registers.put(registroKey, stack.pop());


            default -> {
            }
            // Manejar operator no válido
        }
    }
}