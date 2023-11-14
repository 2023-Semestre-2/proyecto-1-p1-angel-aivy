package com.proyecto1.gestordeprocesos.core;

import com.proyecto1.gestordeprocesos.BCP;

public class ListaBCP {
    private BCP head;  // El primer BCP en la lista
    private BCP tail;  // El último BCP en la lista
    private int size;  // Tamaño de la lista

    public ListaBCP() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Añade un BCP al final de la lista
    public void addBCP(BCP bcp) {
        if (head == null) {
            head = bcp;
            tail = bcp;
        } else {
//            tail.siguienteBCP = bcp;
            tail.setSiguienteBCP(bcp);
            tail = bcp;
        }
        size++;
    }

    public BCP getHead() {
        return head;
    }

    public void setHead(BCP head) {
        this.head = head;
    }

    // Elimina un BCP del frente de la lista
    public BCP removeBCP() {
        if (head == null) {
            return null;
        }
        BCP temp = head;
//        head = head.siguienteBCP;
        head = head.getSiguienteBCP();
        if (head == null) {
            tail = null;
        }
        size--;
        return temp;
    }

    // Obtiene el tamaño de la lista
    public int getSize() {
        return size;
    }
}
