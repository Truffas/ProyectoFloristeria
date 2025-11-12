package com.sergiosr.plantasMVC.base;

import java.time.LocalDate;

public class Adorno {
    private String nombre;
    private String apellidos;
    private String tipoCeremonia;
    private String tipoFlores;
    private String mensaje;
    private LocalDate fechaEntrega;
    private String lugarEntrega;
    private String direccionEnvio;
    private double precio;

    public Adorno(){
    }

    public Adorno(String nombre, String apellidos, String tipoCeremonia,
                  String tipoFlores, String mensaje, LocalDate fechaEntrega,
                  String lugarEntrega, String direccionEnvio, double precio) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoCeremonia = tipoCeremonia;
        this.tipoFlores = tipoFlores;
        this.mensaje = mensaje;
        this.fechaEntrega = fechaEntrega;
        this.lugarEntrega = lugarEntrega;
        this.direccionEnvio = direccionEnvio;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoCeremonia() {
        return tipoCeremonia;
    }

    public void setTipoCeremonia(String tipoCeremonia) {
        this.tipoCeremonia = tipoCeremonia;
    }

    public String getTipoFlores() {
        return tipoFlores;
    }

    public void setTipoFlores(String tipoFlores) {
        this.tipoFlores = tipoFlores;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return  "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", tipoCeremonia='" + tipoCeremonia + '\'' +
                ", tipoFlores='" + tipoFlores + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", lugarEntrega='" + lugarEntrega + '\'' +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                ", precio=" + precio + '\'' ;
    }
}
