package com.sergiosr.plantasMVC;

import com.formdev.flatlaf.FlatLightLaf;
import com.sergiosr.plantasMVC.gui.*;

import javax.swing.*;

public class Principal {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.out.println("No se ha podido aplicar FlatLaf.");
        }

        Ventana vista = new Ventana();
        FloristeriaModelo modelo = new FloristeriaModelo();
        FloristeriaControlador controlador = new FloristeriaControlador(vista, modelo);
    }
}
