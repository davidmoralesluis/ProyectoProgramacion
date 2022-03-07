package com.buscaminas;

public class Celda {

    private boolean isDestapada;
    private boolean isMina;
    private int minasAdyacentes;

    public Celda() {
        super();
        this.isDestapada = false;
        this.isMina = false;
        this.minasAdyacentes = 0;
    }

    public boolean isDestapada() {
        return isDestapada;
    }

    public void setDestapada(boolean isDestapada) {
        this.isDestapada = isDestapada;
    }

    public boolean isMina() {
        return isMina;
    }

    public void setMina(boolean isMina) {
        this.isMina = isMina;
    }

    public void incrementarMinas() {
        this.minasAdyacentes++;
    }

    public int getMinasAdyacentes() {
        return this.minasAdyacentes;
    }
}
