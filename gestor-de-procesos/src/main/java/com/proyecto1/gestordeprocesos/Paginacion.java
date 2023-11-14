package com.proyecto1.gestordeprocesos;

import java.util.ArrayList;
import java.util.List;

public class Paginacion implements AsignacionStrategy {

    private int pagesSize;
    private int tablePagesSize;
    private int indexMemoria = 0;

    //===========================
    private List<MarcoDePagina> marcosDePagina; // Todos los marcos de página disponibles en la memoria

    public Paginacion() {
        this.pagesSize = Configuracion.getInstance().getPagesSize();
        this.tablePagesSize = Configuracion.getInstance().getTablePagesSize();

        this.marcosDePagina = new ArrayList<>(tablePagesSize);
        for (int i = 0; i < tablePagesSize; i++) {
            marcosDePagina.add(new MarcoDePagina()); // Inicialmente, todos los marcos están vacíos
        }
    }

    public Paginacion(int pagesSize, int tablePagesSize) {
        this.pagesSize = pagesSize;
        this.tablePagesSize = tablePagesSize;

    }

    @Override
    public void asignarMemoria(Proceso proceso, MemoriaPrincipal memoriaPrincipal) {
        int numeroDePaginas = calcularNumeroDePaginas(proceso);
        TablaDePaginas tablaDePaginas = new TablaDePaginas();
//        System.out.println("ID de proceso: " + proceso.getPID());

//        int indexMemoria = 0;
        for (int i = 0; i < numeroDePaginas; i++) {
            MarcoDePagina marcoLibre = encontrarMarcoLibre(memoriaPrincipal, indexMemoria);

            if (marcoLibre != null) {
                marcoLibre.setProceso(proceso);
                marcoLibre.setNumeroPagina(i);
                tablaDePaginas.agregarEntrada(marcoLibre);

                // Asignar las instrucciones de la página actual a la memoria
                List<Instruction> instruccionesPagina = proceso.getInstrucciones().subList(i * this.pagesSize, Math.min((i + 1) * this.pagesSize, proceso.getInstrucciones().size()));
                for (Instruction instruccion : instruccionesPagina) {
                    memoriaPrincipal.getAlmacenamientoPrincipal()[indexMemoria++] = instruccion;
                    memoriaPrincipal.addToObservables(indexMemoria, instruccion.getData(), Integer.toString(proceso.getPID()));

                }
            } else {
                System.out.println("No hay frames disponibles");
                // Manejar el caso cuando no hay marcos de página disponibles.
                // Podrías implementar swapping o alguna otra estrategia aquí.
            }
        }
        proceso.setTablaDePaginas(tablaDePaginas);
    }

    private MarcoDePagina encontrarMarcoLibre(MemoriaPrincipal memoria, int start) {
        for (int i = start; i < memoria.getAlmacenamientoPrincipal().length; i += this.pagesSize) {
            if (memoria.getAlmacenamientoPrincipal()[i] == null) {
                return new MarcoDePagina(i);
            }
        }
        return null;
    }

    private int calcularNumeroDePaginas(Proceso proceso) {
        int numInstrucciones = proceso.getInstrucciones().size();

        int paginasNecesarias = numInstrucciones / this.pagesSize;
        if (numInstrucciones % this.pagesSize > 0) {
            paginasNecesarias++;
        }

        return paginasNecesarias;
    }

    private void liberarMarcos(Proceso proceso) {
        for (MarcoDePagina marco : marcosDePagina) {
            if (marco.getProceso() == proceso) {
                marco.setProceso(null);
                marco.setNumeroPagina(-1);
            }
        }
    }

    @Override
    public void asignarMemoria(List<Proceso> procesos, Disco disco) {

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
}
