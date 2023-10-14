package com.proyecto1.gestordeprocesos;

public class Instruction implements MemoryData {
    private String operator;
    private String register1;
    private String register2;
    //    private String value;
    private int value;
    private int weight;
    private String label;

    private String strInstruction;

    public Instruction(String instructionLine) {
        this.strInstruction = instructionLine;
        String[] parts = instructionLine.split(" ");

//        if(parts[0].endsWith(":")) {
//            this.label = parts[0].substring(0, parts[0].length() - 1);
//            return;  // No hay más procesamiento necesario para etiquetas
//        }


        if (parts.length < 2) {
            System.out.print("Error extracting the operator");
            return;
        }
        this.operator = parts[0];
        this.register1 = parts[1].replace(",", "").trim();

        if (parts.length > 2) {
            if (isInteger(parts[2].trim())) {
//                this.value = parts[2].trim();
                this.value = Integer.parseInt(parts[2].trim());
            } else {
                this.register2 = parts[2].trim();
            }
        }
        setWeight(operator);
    }

//    public String getLabel() {
//        return label;
//    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(String operator) {
        switch (operator) {
            case "MOV", "INC", "DEC", "SWAP", "PUSH", "POP" -> this.weight = 1;
            case "LOAD" -> // LOAD
                    this.weight = 2;
            case "ADD" -> // ADD
                    this.weight = 3;
            case "SUB" -> // SUB
                    this.weight = 3;
            case "STORE" -> // STORE
                    this.weight = 2;
            case "INT", "JMP", "CMP", "JNE", "JE" -> this.weight = 2;
            case "PARAM" -> this.weight = 3;
        }
    }

    public String getOperator() {
        return operator;
    }

    public String getRegister() {
        return register1;
    }

    public String getRegister2() {
        return register2;
    }

    // Por que es un string?
    public int getValue() {
//        return value != null ? Integer.parseInt(value) : -1;
        return value;
    }

    @Override
    public String getData() {
        return strInstruction;
    }
}
