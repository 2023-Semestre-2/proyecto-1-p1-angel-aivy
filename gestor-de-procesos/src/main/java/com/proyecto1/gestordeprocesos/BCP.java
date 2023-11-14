package com.proyecto1.gestordeprocesos;

import java.util.HashMap;
import java.util.Random;

public class BCP {
    // Contador estático para mantener la cuenta de los BCPs creados
    private static int counter = 0;
    // Identificador del Proceso
    private int PID;

    // Estado del proceso (listo, ejecutando, bloqueado, etc.)
    private State estado;

    // Tiempo en CPU (cuánto tiempo ha pasado en la CPU)
    private int tiempoCPU;

    // Contador de programa (indica la siguiente instrucción a ejecutar)
    private int PC;

    // Valor en un registro específico
    private int registro;
    private String registroName;

    // Acumulador
    private int AC;

    // Dirección de inicio en memoria
    private int dirInicio;

    // Dirección final en memoria
    private int dirFin;

    // Dirección del siguiente BCP en la lista (para manejo de procesos en cola)
    private BCP siguienteBCP;
    private HashMap<String, Integer> registros;
    private Proceso proceso;
    private int tiempoEnCPU;
    private int tiempoLlegada;

    public BCP() {
        this.registros = new HashMap<>();
        this.registros.put("AC", 0);
        this.registros.put("AX", 0);
        this.registros.put("BX", 0);
        this.registros.put("CX", 0);
        this.registros.put("DX", 0);
    }

    public BCP(State estado, int tiempoCPU, int PC, int registro, int AC, int dirInicio, int dirFin, Proceso proceso) {
        this.PID = ++counter;
        this.estado = estado;
        this.tiempoCPU = tiempoCPU; // tiempo total de ejecucion
        this.tiempoEnCPU = 0; //tiempo en CPU
        this.PC = PC;
        this.registro = registro;
        this.AC = AC;
        this.dirInicio = dirInicio;
        this.dirFin = dirFin;
        this.siguienteBCP = null; // Por defecto, sin siguiente BCP

        this.registros = new HashMap<>();
        this.registros.put("AC", 0);
        this.registros.put("AX", 0);
        this.registros.put("BX", 0);
        this.registros.put("CX", 0);
        this.registros.put("DX", 0);
        this.proceso = proceso;
        this.tiempoLlegada = generarRandom();

    }

    public int generarRandom() {
        Random rand = new Random();
        return rand.nextInt(10) + 1;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoEnCPU() {
        //todo borrar luego de pruebas
        Random random = new Random();
        this.tiempoEnCPU = 1 + random.nextInt(tiempoCPU - 1);

//        this.tiempoEnCPU += 1;
    }

    public int getTiempoRestante() {
        return tiempoCPU - tiempoEnCPU;
    }

    public void setSiguienteBCP(BCP siguienteBCP) {
        this.siguienteBCP = siguienteBCP;
    }

    public BCP getSigBCP() {
        return this.siguienteBCP;
    }

    public static int getCounter() {
        return counter;
    }

    public int getPID() {
        return PID;
    }

    public State getEstado() {
        return estado;
    }

    public int getTiempoCPU() {
        return tiempoCPU;
    }

    public int getPC() {
        return PC;
    }

    public int getRegistro() {
        return registro;
    }

    public String getRegistroName() {
        return registroName;
    }

    public void setRegistroName(String registroName) {
        this.registroName = registroName;
    }

    public int getAC() {
        return AC;
    }

    public int getDirInicio() {
        return dirInicio;
    }

    public int getDirFin() {
        return dirFin;
    }

    public BCP getSiguienteBCP() {
        return siguienteBCP;
    }

    public int getSigBCPDir() {
        System.out.println(siguienteBCP);
        if (this.siguienteBCP == null) {
            return -1;
        }
        return siguienteBCP.getPID();
    }

    public Proceso getProceso() {
        return proceso;
    }

    //funciones con el resgistro
    public int getOrDefaultRegsitro(String key, int value) {
        return this.registros.getOrDefault(key, value);
    }

    public void putRegistro(String key, int value) {
        this.registros.put(key, value);
    }

    public void setAC(int AC) {
        this.AC = AC;
    }

}
