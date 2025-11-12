package com.sergiosr.plantasMVC.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.sergiosr.plantasMVC.base.Adorno;

import javax.swing.*;

public class Ventana {
    private JPanel panel1;
    public JRadioButton ramoRadioButton;
    public JRadioButton centroRadioButton;
    public JTextField nombreTxt;
    public JTextField apellidosTxt;
    public JTextField floresTxt;
    public JLabel precioLbl;
    public JTextField precioTxt;
    public JButton nuevoButton;
    public JButton exportarButton;
    public JButton importarButton;
    public JList<Adorno> list1;
    public DatePicker fechaEntregaDPicker;
    public JRadioButton coronaRadioButton;
    public JComboBox ceremoniaComboBox;
    public JTextField mensajeTarjetaTxt;
    public JRadioButton enTiendaRadioButton;
    public JRadioButton porEnvioRadioButton;
    public JTextField direccionTxt;
    public JButton limpiarCamposButton;
    public JLabel datoExtraLbl;
    public JTextField datoExtraTxt;
    public JLabel mensajeTarjetaLbl;
    public JLabel direccionLbl;
    public JRadioButton nocheRadioButton;
    public JButton eliminarButton;

    //declarado por mi
    public JFrame frame;
    //para poner los datos de adornos en la lista
    public DefaultListModel<Adorno> dlmAdorno;

    public Ventana() {
        frame = new JFrame("FloristeríaMVC");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("src/flor.png").getImage());

        initComponents();
    }

    public void initComponents(){
        dlmAdorno = new DefaultListModel<Adorno>();
        list1.setModel(dlmAdorno);
    }
}
