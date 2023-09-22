package com.proyecto1.gestordeprocesos;


    /*coje lo que esta en memria principal y ejecuta por lotes de 5
    tiene la cola de trabajo
    todo el BCP y el programa que corresponde a ese BCP
ejecuta un proceso a la vez
solo lee de la mem proincipal, ejecutar las funciones y tiene que actualizar el estado
     */

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

    public CPU() {
        registers = new HashMap<>();
    }

    public void printRegistros() {
        System.out.printf(registers.toString());
    }

    public void agregarRegistro(String registro, int valor) {
        this.registers.put(registro, valor);
    }

    public int[] ejecutarInstruction(int memoryPos, Memory memory) {
        this.PC = memoryPos;
        Instruction instruction = memory.getInstruction(memoryPos);
        int valor = instruction.getValue();
        if (valor == 0){
            valor = registers.getOrDefault(instruction.getRegister(), 0);
        }
        registers.put(instruction.getRegister(), valor);

        operatorCalc(instruction);

        int[] resultArray = new int[7];
        resultArray[0] = this.PC;
        resultArray[1] = instruction.getBinaryStringToDecimal();
        resultArray[2] = this.AC;
        resultArray[3] = registers.getOrDefault("AX", 0);
        resultArray[4] = registers.getOrDefault("BX", 0);
        resultArray[5] = registers.getOrDefault("CX", 0);
        resultArray[6] = registers.getOrDefault("DX", 0);



        return resultArray;
    }

    public void operatorCalc (Instruction instruccion) {
        String operator = instruccion.getOperator();
        String registroKey = instruccion.getRegister();
        String registroKey2 = instruccion.getRegister2();
        int valor = instruccion.getValue();

        switch (operator) {
            case "MOV" -> // MOV
                    this.registers.put(registroKey, valor);
            case "LOAD" -> // LOAD
                    this.AC = this.registers.getOrDefault(registroKey, 0);
            case "ADD" -> // ADD
                    this.AC += this.registers.getOrDefault(registroKey, 0);
            case "SUB" -> // SUB
                    this.AC -= this.registers.getOrDefault(registroKey, 0);
            case "STORE" -> // STORE
                    this.registers.put(registroKey, this.AC);
            case "INC" -> { // Increment in 1
                if (registroKey == null){
                    this.AC = this.AC + 1;
                }
                else{
                    this.registers.put(registroKey, this.registers.getOrDefault(registroKey, 0) + 1);
                }

            }
            case "DEC" -> { // Decrement in 1
                if (registroKey == null){
                    this.AC = this.AC - 1;
                }
                else{
                    this.registers.put(registroKey, this.registers.getOrDefault(registroKey, 1) - 1);
                }

            }
            case "SWAP" -> { //Swap values between two registers
                int temp = this.registers.getOrDefault(registroKey, 0);
                this.registers.put(registroKey, this.registers.getOrDefault(registroKey2, 0));
                this.registers.put(registroKey2, this.registers.getOrDefault(temp, 0));
            }
            case "INT"-> {
                if (registroKey.equals("20H")){
                    //Finaliza el programa
                    //queue.clear();
                }
                else if (registroKey.equals("10H")) {
                        //Imprime valor de DX
                    int tempValue = this.registers.getOrDefault("DX", 0);
                    // print in screen
                }
                else if (registroKey.equals("09H")) {
                    //Input from keyboard 0-255, saves in DX and ends with ENTER
                    int tempVal = 0; // has to be a keyboard input
                    if (tempVal >= 0 && tempVal <= 255){
                        this.registers.put("DX", tempVal);
                    }
                }
            }
            case "JMP" ->{}
            case "CMP" ->{}
            case "JNE" ->{}
            case "JE" ->{}
            case "PARAM" ->{}
            case "PUSH" -> //Saves in the stack value in AX register
                stack.push(this.registers.getOrDefault("AX", 0));
            case "POP" -> //Saves the value from the stack to the register
                this.registers.put(registroKey,stack.pop());


            default -> {
            }
            // Manejar operator no válido
        }
    }
}