package com.sergiosr.plantasMVC.base;

import java.time.LocalDate;

public class Corona extends Adorno {
    private double diametro;

    public Corona(){
    }

    public Corona(String nombre, String apellidos, String tipoCeremonia, String tipoFlores,
                  String mensaje, LocalDate fechaEntrega, String lugarEntrega, String direccionEnvio, double precio, double diametro) {
        super(nombre, apellidos, tipoCeremonia, tipoFlores, mensaje, fechaEntrega, lugarEntrega, direccionEnvio, precio);
        this.diametro = diametro;
    }

    public double getDiametro() {
        return diametro;
    }

    public void setDiametro(double diametro) {
        this.diametro = diametro;
    }

    @Override
    public String toString() {
        return "CORONA: " + super.toString() +
                ", diametro=" + diametro;
    }
}
