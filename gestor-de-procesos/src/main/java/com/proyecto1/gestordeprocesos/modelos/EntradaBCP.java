package com.proyecto1.gestordeprocesos.modelos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Map;
import java.util.HashMap;

public class EntradaBCP {
    private SimpleIntegerProperty pid;
    private SimpleStringProperty estado;
    private SimpleIntegerProperty tiempoCPU;
    private SimpleIntegerProperty pc;
    private SimpleStringProperty registro;
    private SimpleIntegerProperty ac;
    private SimpleIntegerProperty dirInicio;
    private SimpleIntegerProperty dirFin;
    private SimpleIntegerProperty sigBCP;

    //para la tabla ejecucion
    private Map<String, SimpleObjectProperty<String>> dynamicProperties;
    private Map<String, SimpleObjectProperty<String>> tiempoEjecucion;

    public EntradaBCP(int pid, String estado, int tiempoCPU, int pc, String registro, int ac, int dirInicio,
                      int dirFin, int sigBCP) {
        this.pid = new SimpleIntegerProperty(pid);
        this.estado = new SimpleStringProperty(estado);
        this.tiempoCPU = new SimpleIntegerProperty(tiempoCPU);
        this.pc = new SimpleIntegerProperty(pc);
        this.registro = new SimpleStringProperty(registro);
        this.ac = new SimpleIntegerProperty(ac);
        this.dirInicio = new SimpleIntegerProperty(dirInicio);
        this.dirFin = new SimpleIntegerProperty(dirFin);
        this.sigBCP = new SimpleIntegerProperty(sigBCP);

        this.dynamicProperties = new HashMap<>();
    }

    public String getColumnData(int key) {
        return tiempoEjecucion.get(Integer.toString(key)).toString();
    }

    public void setDynamicProperty(String propertyName, String value) {
        dynamicProperties.computeIfAbsent(propertyName, k -> new SimpleObjectProperty<>()).set(value);
    }
    public SimpleObjectProperty<String> dynamicProperty(String propertyName) {
        return dynamicProperties.get(propertyName);
    }

    public int getPid() {
        return pid.get();
    }

    public SimpleIntegerProperty pidProperty() {
        return pid;
    }

    public String getEstado() {
        return estado.get();
    }

    public SimpleStringProperty estadoProperty() {
        return estado;
    }

    public int getTiempoCPU() {
        return tiempoCPU.get();
    }

    public SimpleIntegerProperty tiempoCPUProperty() {
        return tiempoCPU;
    }

    public int getPc() {
        return pc.get();
    }

    public SimpleIntegerProperty pcProperty() {
        return pc;
    }

    public String getRegistro() {
        return registro.get();
    }

    public SimpleStringProperty registroProperty() {
        return registro;
    }

    public int getAc() {
        return ac.get();
    }

    public SimpleIntegerProperty acProperty() {
        return ac;
    }

    public int getDirInicio() {
        return dirInicio.get();
    }

    public SimpleIntegerProperty dirInicioProperty() {
        return dirInicio;
    }

    public int getDirFin() {
        return dirFin.get();
    }

    public SimpleIntegerProperty dirFinProperty() {
        return dirFin;
    }

    public int getSigBCP() {
        return sigBCP.get();
    }

    public SimpleIntegerProperty sigBCPProperty() {
        return sigBCP;
    }
}
