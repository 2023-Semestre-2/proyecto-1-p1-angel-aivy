package com.proyecto1.gestordeprocesos;

import com.proyecto1.gestordeprocesos.modelos.EntradaDisco;
import com.proyecto1.gestordeprocesos.modelos.EntradaMemoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MemoriaPrincipal {
    private Instruction[] almacenamientoPrincipal;
    private ObservableList<EntradaMemoria> observables;

    public MemoriaPrincipal() {
        this.almacenamientoPrincipal = new Instruction[1024];
        this.observables = FXCollections.observableArrayList();
    }

    public void cargarAMemoria(Proceso proceso) {
        List<Instruction> instrucciones = proceso.getInstrucciones();

        if (instrucciones.size() <= almacenamientoPrincipal.length) {
            for (int i = 0; i < instrucciones.size(); i++) {
                almacenamientoPrincipal[i] = instrucciones.get(i);
            }
        } else {
            System.out.println("No hay suficiente espacio en la memoria principal para cargar el proceso.");
        }
    }

    public void addToObservables(int pos, String valor, String procesoId) {
        this.observables.add(new EntradaMemoria(pos, valor, procesoId));
    }

    public Instruction[] getAlmacenamientoPrincipal() {
        return almacenamientoPrincipal;
    }

    public ObservableList<EntradaMemoria> getObservables() {
        return observables;
    }
}
