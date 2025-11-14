package com.sergiosr.plantasMVC.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.sergiosr.plantasMVC.base.Adorno;
import com.sergiosr.plantasMVC.base.Centro;
import com.sergiosr.plantasMVC.base.Corona;
import com.sergiosr.plantasMVC.base.Ramo;
import com.sergiosr.plantasMVC.util.Util;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class FloristeriaControlador implements ActionListener, ListSelectionListener, WindowListener {

    private Ventana vista;
    private FloristeriaModelo modelo;
    private File ultimaRutaExportada;
    private boolean modoOscuro = false;

    public FloristeriaControlador(Ventana vista, FloristeriaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        try {
            cargarDatosConfiguracion();
        } catch (IOException e) {
            System.out.println("No existe fichero de configuracion");
        }

        //listener al arrancar el controlador
        addActionListener(this);
        addWindowListener(this);
        addListSelectionListener(this);
    }

    private boolean hayCamposVacios() {
        if (vista.nombreTxt.getText().isEmpty() ||
                vista.apellidosTxt.getText().isEmpty() ||
                vista.floresTxt.getText().isEmpty() ||
                vista.datoExtraTxt.getText().isEmpty() ||
                vista.fechaEntregaDPicker.getText().isEmpty() ||
                //vista.direccionTxt.getText().isEmpty()||
                vista.precioTxt.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    private void limpiarCampos() {
        vista.nombreTxt.setText(null);
        vista.apellidosTxt.setText(null);
        vista.ceremoniaComboBox.setSelectedIndex(0);
        vista.floresTxt.setText(null);
        vista.datoExtraTxt.setText(null);
        vista.mensajeTarjetaTxt.setText(null);
        vista.fechaEntregaDPicker.setText(null);
        vista.direccionTxt.setText(null);
        vista.precioTxt.setText(null);
    }

    //listener botones
    private void addActionListener(ActionListener listener) {
        vista.importarButton.addActionListener(listener);
        vista.exportarButton.addActionListener(listener);
        vista.nuevoButton.addActionListener(listener);
        vista.eliminarButton.addActionListener(listener);
        vista.limpiarCamposButton.addActionListener(listener);
        vista.ramoRadioButton.addActionListener(listener);
        vista.centroRadioButton.addActionListener(listener);
        vista.coronaRadioButton.addActionListener(listener);
        vista.porEnvioRadioButton.addActionListener(listener);
        vista.enTiendaRadioButton.addActionListener(listener);
        vista.nocheRadioButton.addActionListener(listener);
    }

    //listener ventana (boton cerrar)
    private void addWindowListener(WindowListener listener) {
        vista.addWindowListener(listener);
    }

    //listener de la lista
    private void addListSelectionListener(ListSelectionListener listener) {
        vista.list1.addListSelectionListener(listener);
    }

    public void refrescar() {
        vista.dlmAdorno.clear();
        //modelo.obtenerVehiculos -> contiene la lista de vehiculos
        for (Adorno unAdorno : modelo.obtenerPlantas()) {
            vista.dlmAdorno.addElement(unAdorno);
        }
    }

    private void cargarDatosConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.load(new FileReader("plantas.conf"));
        ultimaRutaExportada = new File(configuracion.getProperty("ultimaRutaExportada"));
    }

    private void actualizarDatosConfiguracion(File ultimaRutaExportada) {
        this.ultimaRutaExportada = ultimaRutaExportada;
    }

    private void guardarConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        configuracion.setProperty("ultimaRutaExportada"
                , ultimaRutaExportada.getAbsolutePath());
        configuracion.store(new PrintWriter("plantas.conf")
                , "Datos configuracion adornos");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        switch (actionCommand) {
            case "Nuevo":
                if (hayCamposVacios()) {
                    Util.mensajeError("Los siguientes campos no pueden estar vacíos: " +
                            "\nNombre\nApellidos\nTipo de flores\n" + vista.datoExtraLbl.getText() + "\n" + vista.mensajeTarjetaLbl.getText() +
                            "\nFecha de entrega\nPrecio");
                    break;
                }
                /*if (modelo.existeApellidos(vista.apellidosTxt.getText())) {
                    Util.mensajeError("Ya existe un pedido para esta persona" +
                            "\n"+vista.apellidosTxt.getText());
                    break;
                }*/
                if (vista.ramoRadioButton.isSelected()) {
                    String lugarEntrega = "";
                    if (vista.enTiendaRadioButton.isSelected()) {
                        lugarEntrega = "En tienda";
                    } else if (vista.porEnvioRadioButton.isSelected()) {
                        lugarEntrega = "Por envío";
                    }

                    double precio;
                    int numFlores;
                    try {
                        precio = Double.parseDouble(vista.precioTxt.getText());
                        numFlores = Integer.parseInt(vista.datoExtraTxt.getText());
                    } catch (NumberFormatException ex) {
                        Util.mensajeError("Introduce valores válidos en Precio y " + vista.datoExtraLbl.getText());
                        return;
                    }
                        modelo.altaRamo(
                                vista.nombreTxt.getText(),
                                vista.apellidosTxt.getText(),
                                vista.ceremoniaComboBox.getSelectedItem().toString(),
                                vista.floresTxt.getText(),
                                vista.mensajeTarjetaTxt.getText(),
                                vista.fechaEntregaDPicker.getDate(),
                                lugarEntrega,
                                vista.direccionTxt.getText(),
                                precio,
                                numFlores);

                } else if (vista.centroRadioButton.isSelected()) {
                    String lugarEntrega = "";
                    if (vista.enTiendaRadioButton.isSelected()) {
                        lugarEntrega = "En tienda";
                    } else if (vista.porEnvioRadioButton.isSelected()) {
                        lugarEntrega = "Por envío";
                    }
                    double precio;
                    try {
                        precio = Double.parseDouble(vista.precioTxt.getText());
                    } catch (NumberFormatException ex) {
                        Util.mensajeError("Introduce valores válidos en Precio");
                        return;
                    }
                        modelo.altaCentro(
                                vista.nombreTxt.getText(),
                                vista.apellidosTxt.getText(),
                                vista.ceremoniaComboBox.getSelectedItem().toString(),
                                vista.floresTxt.getText(),
                                vista.mensajeTarjetaTxt.getText(),
                                vista.fechaEntregaDPicker.getDate(),
                                lugarEntrega,
                                vista.direccionTxt.getText(),
                                precio,
                                vista.datoExtraTxt.getText());
                } else {
                    String lugarEntrega = "";
                    if (vista.enTiendaRadioButton.isSelected()) {
                        lugarEntrega = "En tienda";
                    } else if (vista.porEnvioRadioButton.isSelected()) {
                        lugarEntrega = "Por envío";
                    }
                    double precio;
                    double diametro;
                    try {
                        precio = Double.parseDouble(vista.precioTxt.getText());
                        diametro = Double.parseDouble(vista.datoExtraTxt.getText());
                    } catch (NumberFormatException ex) {
                        Util.mensajeError("Introduce valores válidos en Precio y " + vista.datoExtraLbl.getText());
                        return;
                    }
                        modelo.altaCorona(
                                vista.nombreTxt.getText(),
                                vista.apellidosTxt.getText(),
                                vista.ceremoniaComboBox.getSelectedItem().toString(),
                                vista.floresTxt.getText(),
                                vista.mensajeTarjetaTxt.getText(),
                                vista.fechaEntregaDPicker.getDate(),
                                lugarEntrega,
                                vista.direccionTxt.getText(),
                                precio,
                                diametro);
                }
                limpiarCampos();
                refrescar();
                break;
            case "Eliminar":
                Adorno adornoSeleccionado = vista.list1.getSelectedValue();
                modelo.eliminarAdorno(adornoSeleccionado);
                limpiarCampos();
                refrescar();
                break;
            case "Importar":
                JFileChooser selectorFichero = Util.crearSelectorFichero(ultimaRutaExportada
                        , "Archivos XML", "xml");
                int opt = selectorFichero.showOpenDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    try {
                        modelo.importarXML(selectorFichero.getSelectedFile());
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    }
                    refrescar();
                }
                break;
            case "Limpiar campos":
                limpiarCampos();
                refrescar();
                break;
            case "Exportar":
                JFileChooser selectorFichero2 = Util.crearSelectorFichero(ultimaRutaExportada
                        , "Archivos XML", "xml");
                int opt2 = selectorFichero2.showSaveDialog(null);
                if (opt2 == JFileChooser.APPROVE_OPTION) {
                    try {
                        modelo.exportarXML(selectorFichero2.getSelectedFile());
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "Ramo":
                vista.datoExtraLbl.setText("Número de flores");
                vista.mensajeTarjetaLbl.setText("Mensaje de la tarjeta");
                break;
            case "Centro":
                vista.datoExtraLbl.setText("Tamaño");
                vista.mensajeTarjetaLbl.setText("Mensaje de la cinta");
                break;
            case "Corona":
                vista.datoExtraLbl.setText("Diámetro (cms)");
                vista.mensajeTarjetaLbl.setText("Mensaje de la cinta");
                break;
            case "En tienda":
                vista.direccionTxt.setText(null);
                vista.direccionLbl.setVisible(false);
                vista.direccionTxt.setVisible(false);
                break;
            case "Por envío":
                vista.direccionLbl.setVisible(true);
                vista.direccionTxt.setVisible(true);
                break;
            case "Modo oscuro":
                try {
                    if (modoOscuro == false) {
                        UIManager.setLookAndFeel(new FlatDarkLaf());
                        modoOscuro = true;
                    } else {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        modoOscuro = false;
                    }
                    //Con esto cambio el tema, pero no se actualiza automáticamente en las ventanas abiertas
                    // Actualizo la apariencia de todos los componentes Swing de la ventana abierta
                    SwingUtilities.updateComponentTreeUI(vista);
                } catch (Exception ex) {
                    System.out.println("No se ha podido cambiar al tema oscuro.");
                }
                break;
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int resp = Util.mensajeConfirmacion("¿Desea cerrar la ventana?", "Salir");

        // FlatLaf devuelve CLOSED_OPTION (0) al pulsar "Sí" en algunos cuadros de diálogo
        if (resp == JOptionPane.OK_OPTION || resp == JOptionPane.YES_OPTION || resp == JOptionPane.CLOSED_OPTION) {

            try {
                guardarConfiguracion();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            Adorno adornoSeleccionado = vista.list1.getSelectedValue();
            vista.nombreTxt.setText(adornoSeleccionado.getNombre());
            vista.apellidosTxt.setText(adornoSeleccionado.getApellidos());
            vista.ceremoniaComboBox.setSelectedItem(adornoSeleccionado.getTipoCeremonia());
            vista.floresTxt.setText(adornoSeleccionado.getTipoFlores());
            vista.mensajeTarjetaTxt.setText(adornoSeleccionado.getMensaje());
            vista.fechaEntregaDPicker.setDate(adornoSeleccionado.getFechaEntrega());
            if (adornoSeleccionado.getLugarEntrega().equals("En tienda")) {
                vista.enTiendaRadioButton.doClick();
            } else {
                vista.porEnvioRadioButton.doClick();
            }
            vista.direccionTxt.setText(adornoSeleccionado.getDireccionEnvio());
            vista.precioTxt.setText(String.valueOf(adornoSeleccionado.getPrecio()));

            if (adornoSeleccionado instanceof Ramo) {
                vista.ramoRadioButton.doClick();
                vista.datoExtraTxt.setText(String.valueOf(((Ramo) adornoSeleccionado).getNumFlores()));
            } if (adornoSeleccionado instanceof Centro) {
                vista.centroRadioButton.doClick();
                vista.datoExtraTxt.setText(((Centro) adornoSeleccionado).getTamanio());
            } else if (adornoSeleccionado instanceof Corona){
                vista.coronaRadioButton.doClick();
                vista.datoExtraTxt.setText(String.valueOf(((Corona) adornoSeleccionado).getDiametro()));
            }
        }
    }


    //no los uso

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}