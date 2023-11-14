package com.proyecto1.gestordeprocesos;

public class Configuracion {

    private static Configuracion instance;
    private String cpuExecutionAlgorithm;
    private String memoryAllocation;
    private AsignacionStrategy tipoAsignacion;
    private int cpuCount = 1;
    private ExecutionAlgorithmStrategy cpuExecutionStrategy;
    private int quantum;
    //Paginacion
    private int pagesSize;
    private int tablePagesSize;

    //Particionmiento dinamico
    private int initialSize;
    private String politicaAsig;
    //[articionmiento fijo
    private int numeroParticiones;
    private int particionSize;

    private Configuracion() {
    }

    public static Configuracion getInstance() {
        if (instance == null) {
            instance = new Configuracion();
        }
        return instance;
    }

    public ExecutionAlgorithmStrategy getCpuExecutionStrategy() {
        return cpuExecutionStrategy;
    }

    public void setExecutionStrategy(ExecutionAlgorithmStrategy strategy) {
        this.cpuExecutionStrategy = strategy;
    }


    public String getCpuExecutionAlgorithm() {
        return cpuExecutionAlgorithm;
    }

    public void setCpuExecutionAlgorithm(String cpuExecutionAlgorithm) {
        this.cpuExecutionAlgorithm = cpuExecutionAlgorithm;
    }

    public String getMemoryAllocation() {
        return memoryAllocation;
    }

    public void setMemoryAllocation(String memoryAllocation) {
        this.memoryAllocation = memoryAllocation;

        switch (memoryAllocation) {
            case "Paginación (física y virtual)" -> this.tipoAsignacion = new Paginacion();
//            case "Particionamiento dinámico" -> this.tipoAsignacion = new ParticionamientoDinamico();
        }
    }

    public AsignacionStrategy getTipoAsignacion() {
        return tipoAsignacion;
    }

    public void actualizarParamsPaginacionPageSize(int pagesSize) {
        if (tipoAsignacion != null && tipoAsignacion instanceof Paginacion) {
            ((Paginacion) tipoAsignacion).setPagesSize(pagesSize);
        }
    }

    public void actualizarParamsPaginacionTablePagesSize(int tablePagesSize) {
        if (tipoAsignacion != null && tipoAsignacion instanceof Paginacion) {
            ((Paginacion) tipoAsignacion).setTablePagesSize(tablePagesSize);
        }
    }

    public int getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(int cpuCount) {
        this.cpuCount = cpuCount;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getPagesSize() {
        return pagesSize;
    }

    public void setPagesSize(int pagesSize) {
        this.pagesSize = pagesSize;
    }

    public int getTablePagesSize() {
        return tablePagesSize;
    }

    public void setTablePagesSize(int tablePagesSize) {
        this.tablePagesSize = tablePagesSize;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public String getPoliticaAsig() {
        return politicaAsig;
    }

    public void setPoliticaAsig(String politicaAsig) {
        this.politicaAsig = politicaAsig;
    }

    public int getNumeroParticiones() {
        return numeroParticiones;
    }

    public void setNumeroParticiones(int numeroParticiones) {
        this.numeroParticiones = numeroParticiones;
    }

    public int getParticionSize() {
        return particionSize;
    }

    public void setParticionSize(int particionSize) {
        this.particionSize = particionSize;
    }
}
