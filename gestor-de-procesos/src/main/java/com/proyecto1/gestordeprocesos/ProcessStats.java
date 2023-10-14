package com.proyecto1.gestordeprocesos;

import java.time.Duration;
import java.time.LocalTime;

public class ProcessStats {
    private String processName;
    private LocalTime startTime;
    private LocalTime endTime;

    Duration duration;
    long seconds;

    public ProcessStats() {
    }

    public void setDuration() {
        this.duration = Duration.between(getStartTime(), getEndTime());
        this.seconds = this.duration.getSeconds();
    }

    public String getStats() {
        setDuration();
        return "---------------\n" +
                "Proceso: " + this.processName + "\n" +
                "Hora inicio: " + this.getStartTime() + "\n" +
                "Hora fin: " + this.endTime + "\n" +
                "Duración en segundos: " + this.seconds + "\n" +
                "----------------\n";
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
