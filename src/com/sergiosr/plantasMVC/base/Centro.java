package com.sergiosr.plantasMVC.base;

import java.time.LocalDate;

public class Centro extends Adorno{
    private String tamanio;

    public Centro() {
    }

    public Centro(String nombre, String apellidos, String tipoCeremonia, String tipoFlores,
                  String mensaje, LocalDate fechaEntrega, String lugarEntrega, String direccionEnvio, double precio, String tamanio) {
        super(nombre, apellidos, tipoCeremonia, tipoFlores, mensaje, fechaEntrega, lugarEntrega, direccionEnvio, precio);
        this.tamanio = tamanio;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    @Override
    public String toString() {
        return "CENTRO: " + super.toString() +
                ", tamaño=" + tamanio;
    }
}
