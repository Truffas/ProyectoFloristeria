package com.sergiosr.plantasMVC.base;

import java.time.LocalDate;

public class Ramo extends Adorno {
    private int numFlores;

    public Ramo(){

    }

    public Ramo(String nombre, String apellidos, String tipoCeremonia, String tipoFlores,
                String mensaje, LocalDate fechaEntrega, String lugarEntrega, String direccionEnvio, double precio, int numFlores) {
        super(nombre, apellidos, tipoCeremonia, tipoFlores, mensaje, fechaEntrega, lugarEntrega, direccionEnvio, precio);
        this.numFlores = numFlores;
    }

    public int getNumFlores() {
        return numFlores;
    }

    public void setNumFlores(int numFlores) {
        this.numFlores = numFlores;
    }

    @Override
    public String toString() {
        return "RAMO: " + super.toString() +
                ", numFlores=" + numFlores;
    }
}
