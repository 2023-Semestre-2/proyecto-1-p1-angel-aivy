package com.proyecto1.gestordeprocesos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

public class EntradaEjecucion {
    private SimpleIntegerProperty pid;
    private SimpleIntegerProperty tiempo;
    private SimpleStringProperty x;
    private final Map<Integer, SimpleStringProperty> executionMap;


    public EntradaEjecucion(int pid) {
        this.pid = new SimpleIntegerProperty(pid);
        this.executionMap = new HashMap<>();

        // Inicializa el mapa con las 25 columnas
        for (int i = 1; i <= 25; i++) {
            executionMap.put(i, new SimpleStringProperty(""));
        }
    }


//    public void agregarXToMap(String key, String value) {
//        this.tiempoEjecucion.put(key, new SimpleObjectProperty<>(value));
//    }
//
    public String getColumnData(int key) {
        return executionMap.get(Integer.toString(key)).toString();
    }

    public String getX() {
        return x.get();
    }

    public SimpleStringProperty xProperty() {
        return x;
    }

    public void setX(String x) {
        this.x.set(x);
    }

    public int getPid() {
        return pid.get();
    }

    public SimpleIntegerProperty pidProperty() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid.set(pid);
    }

    public int getTiempo() {
        return tiempo.get();
    }

    public SimpleIntegerProperty tiempoProperty() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo.set(tiempo);
    }

    // para actualizar el valor de una columna
    public void setTiempo(int index, String value) {
        if (executionMap.containsKey(index)) {
            executionMap.get(index).set(value);
        } else {
            executionMap.put(index, new SimpleStringProperty(value));
        }
    }
    // Genera getters para las 25 columnas
    public String getTiempo1() {
        return executionMap.getOrDefault(1, new SimpleStringProperty("")).get();
    }
    public String getTiempo2() {
        return executionMap.getOrDefault(2, new SimpleStringProperty("")).get();
    }
    public String getTiempo3() {
        return executionMap.getOrDefault(3, new SimpleStringProperty("")).get();

    }public String getTiempo4() {
        return executionMap.getOrDefault(4, new SimpleStringProperty("")).get();
    }
    public String getTiempo5() {
        return executionMap.getOrDefault(5, new SimpleStringProperty("")).get();
    }
    public String getTiempo6() {
        return executionMap.getOrDefault(6, new SimpleStringProperty("")).get();
    }
    public String getTiempo7() {
        return executionMap.getOrDefault(7, new SimpleStringProperty("")).get();
    }
    public String getTiempo8() {
        return executionMap.getOrDefault(8, new SimpleStringProperty("")).get();
    }
    public String getTiempo9() {
        return executionMap.getOrDefault(9, new SimpleStringProperty("")).get();
    }
    public String getTiempo10() {
        return executionMap.getOrDefault(10, new SimpleStringProperty("")).get();
    }
    public String getTiempo11() {
        return executionMap.getOrDefault(11, new SimpleStringProperty("")).get();
    }
    public String getTiempo12() {
        return executionMap.getOrDefault(12, new SimpleStringProperty("")).get();
    }
    public String getTiempo13() {
        return executionMap.getOrDefault(13, new SimpleStringProperty("")).get();
    }
    public String getTiempo14() {
        return executionMap.getOrDefault(14, new SimpleStringProperty("")).get();
    }
    public String getTiempo15() {
        return executionMap.getOrDefault(15, new SimpleStringProperty("")).get();
    }
    public String getTiempo16() {
        return executionMap.getOrDefault(16, new SimpleStringProperty("")).get();
    }
    public String getTiempo17() {
        return executionMap.getOrDefault(17, new SimpleStringProperty("")).get();
    }
    public String getTiempo18() {
        return executionMap.getOrDefault(18, new SimpleStringProperty("")).get();
    }
    public String getTiempo19() {
        return executionMap.getOrDefault(19, new SimpleStringProperty("")).get();
    }
    public String getTiempo20() {
        return executionMap.getOrDefault(20, new SimpleStringProperty("")).get();
    }

    public void updateExecutionTime(int timeIndex, String value) {
        if (executionMap.containsKey(timeIndex)) {
            executionMap.get(timeIndex).set(value);
        } else {
            executionMap.put(timeIndex, new SimpleStringProperty(value));
        }
    }
}
