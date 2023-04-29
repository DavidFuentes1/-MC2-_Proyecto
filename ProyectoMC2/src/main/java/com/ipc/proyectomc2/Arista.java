package com.ipc.proyectomc2;
public class Arista {
    private Vertice inicio;
    private Vertice fin;
    private boolean dirigida;

    public Arista(Vertice inicio, Vertice fin, boolean dirigida) {
        this.inicio = inicio;
        this.fin = fin;
        this.dirigida = dirigida;
    }

    public Vertice getInicio() {
        return inicio;
    }

    public void setInicio(Vertice inicio) {
        this.inicio = inicio;
    }

    public Vertice getFin() {
        return fin;
    }

    public void setFin(Vertice fin) {
        this.fin = fin;
    }

    public boolean isDirigida() {
        return dirigida;
    }

    public void setDirigida(boolean dirigida) {
        this.dirigida = dirigida;
    }
}

