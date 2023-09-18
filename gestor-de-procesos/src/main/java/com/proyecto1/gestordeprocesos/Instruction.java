package com.proyecto1.gestordeprocesos;

public class Instruction implements MemoryData{
    private String operator;
    private String register;
    private String value;

    private String strInstruction;

    public Instruction(String instructionLine) {
        this.strInstruction = instructionLine;
        String[] parts = instructionLine.split(" ");
        if (parts.length < 2) {
            System.out.print("Error extracting the operator");
            return;
        }
        this.operator = parts[0];
        this.register = parts[1].replace(",", "").trim();

        if (parts.length > 2) {
            this.value = parts[2].trim();
        }
    }

    public String getOperator() {
        return operator;
    }

    public String getRegister() {
        return register;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getData() {
        return strInstruction;
    }
}
