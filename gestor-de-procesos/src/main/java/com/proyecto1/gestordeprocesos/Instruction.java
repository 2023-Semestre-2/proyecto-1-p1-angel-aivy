package com.proyecto1.gestordeprocesos;

public class Instruction implements MemoryData{
    private String operator;
    private String register1;
    private String register2;
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
        this.register1 = parts[1].replace(",", "").trim();

        if (parts.length > 2) {
            if (isInteger(parts[2].trim())){
                this.value = parts[2].trim();
            }
            else{
                this.register2 = parts[2].trim();
            }
        }
    }

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
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
    public String getValue() {
        return value;
    }

    @Override
    public String getData() {
        return strInstruction;
    }
}
