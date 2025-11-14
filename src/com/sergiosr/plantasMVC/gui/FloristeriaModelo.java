package com.sergiosr.plantasMVC.gui;

import com.sergiosr.plantasMVC.base.Centro;
import com.sergiosr.plantasMVC.base.Corona;
import com.sergiosr.plantasMVC.base.Ramo;
import com.sergiosr.plantasMVC.base.Adorno;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FloristeriaModelo {
    private ArrayList<Adorno> listaAdornos;

    public FloristeriaModelo() {
        listaAdornos = new ArrayList<Adorno>();
    }

    public ArrayList<Adorno> obtenerPlantas() {
        return listaAdornos;
    }

    //altaRamo
    public void altaRamo(String nombre, String apellidos, String tipoCeremonia, String tipoFlores,
                         String mensaje, LocalDate fechaEntrega, String lugarEntrega, String direccionEnvio, double precio, int numFlores) {
        Ramo nuevoRamo = new Ramo(nombre, apellidos, tipoCeremonia, tipoFlores, mensaje, fechaEntrega, lugarEntrega, direccionEnvio, precio, numFlores);
        listaAdornos.add(nuevoRamo);
    }

    //altaCentro
    public void altaCentro(String nombre, String apellidos, String tipoCeremonia, String tipoFlores,
                           String mensaje, LocalDate fechaEntrega, String lugarEntrega, String direccionEnvio, double precio, String tamanio) {
        Centro nuevaCentro = new Centro(nombre, apellidos, tipoCeremonia, tipoFlores, mensaje, fechaEntrega, lugarEntrega, direccionEnvio, precio, tamanio);
        listaAdornos.add(nuevaCentro);
    }

    //altaCorona
    public void altaCorona(String nombre, String apellidos, String tipoCeremonia, String tipoFlores,
                           String mensaje, LocalDate fechaEntrega, String lugarEntrega, String direccionEnvio, double precio, double diametro) {
        Corona nuevaCorona = new Corona(nombre, apellidos, tipoCeremonia, tipoFlores, mensaje, fechaEntrega, lugarEntrega, direccionEnvio, precio, diametro);
        listaAdornos.add(nuevaCorona);
    }

    //exiteApellidos
    public boolean existeApellidos(String apellidos) {
        for (Adorno unAdorno : listaAdornos) {
            if (unAdorno.getApellidos().equals(apellidos)) {
                return true;
            }
        }
        return false;
    }

    //eliminar adorno
    public void eliminarAdorno(Adorno adorno) {

        listaAdornos.remove(adorno);
    }

    //exportarXML
    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //implementacion DOM -> web
        DOMImplementation dom = builder.getDOMImplementation();
        Document documento = dom.createDocument(null, "xml", null);

        //añado el nodo raiz (primera etiqueta)
        Element raiz = documento.createElement("Adornos");
        documento.getDocumentElement().appendChild(raiz);

        Element nodoAdorno = null;
        Element nodoDatos = null;
        Text texto = null;

        //voy a añadir una etiqueta dentro de vehiculo en funcion si es coche o moto

        for (Adorno unAdorno : listaAdornos) {
            if (unAdorno instanceof Ramo) {
                nodoAdorno = documento.createElement("Ramo");
            } else if (unAdorno instanceof Corona) {
                nodoAdorno = documento.createElement("Corona");
            } else {
                nodoAdorno = documento.createElement("Centro");
            }
            raiz.appendChild(nodoAdorno);

            //dentro de la etiqueta vehiculo tengo coche y moto
            //atributos comunes (metricula, marca, modelo, fechaMatriculacion)

            nodoDatos = documento.createElement("nombre");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(unAdorno.getNombre());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("apellidos");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(unAdorno.getApellidos());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("tipo-ceremonia");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(unAdorno.getTipoCeremonia());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("tipo-flores");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(unAdorno.getTipoFlores());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("mensaje");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(unAdorno.getMensaje());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("fecha-entrega");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(String.valueOf(unAdorno.getFechaEntrega()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("lugar-entrega");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(unAdorno.getLugarEntrega());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("direccion-envio");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(unAdorno.getDireccionEnvio());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("precio");
            nodoAdorno.appendChild(nodoDatos);

            texto = documento.createTextNode(String.valueOf(unAdorno.getPrecio()));
            nodoDatos.appendChild(texto);

            //como hay un campo que depende del tipo de adorno
            // volvemos a comprobar

            if (unAdorno instanceof Ramo) {
                nodoDatos = documento.createElement("numero-flores");
                nodoAdorno.appendChild(nodoDatos);

                texto = documento.createTextNode(String.valueOf(((Ramo) unAdorno).getNumFlores()));
                nodoDatos.appendChild(texto);
            } else if (unAdorno instanceof Corona) {
                nodoDatos = documento.createElement("diametro");
                nodoAdorno.appendChild(nodoDatos);

                texto = documento.createTextNode(String.valueOf(((Corona) unAdorno).getDiametro()));
                nodoDatos.appendChild(texto);
            } else {
                nodoDatos = documento.createElement("tamanio");
                nodoAdorno.appendChild(nodoDatos);

                texto = documento.createTextNode(((Centro) unAdorno).getTamanio());
                nodoDatos.appendChild(texto);
            }

        }

        //guardo los datos en fichero
        Source source = new DOMSource(documento);
        Result result = new StreamResult(fichero);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);

    }

    //importarXML

    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {
        listaAdornos = new ArrayList<Adorno>();
        Ramo nuevoRamo = null;
        Corona nuevaCorona = null;
        Centro nuevoCentro = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        NodeList listaElementos = documento.getElementsByTagName("*"); // el "*" es para coger todas las etiquetas

        for (int i = 0; i < listaElementos.getLength(); i++) {
            Element nodoAdorno = (Element) listaElementos.item(i);

            if (nodoAdorno.getTagName().equals("Ramo")) {
                nuevoRamo = new Ramo();
                nuevoRamo.setNombre(nodoAdorno.getChildNodes().item(0).getTextContent());
                nuevoRamo.setApellidos(nodoAdorno.getChildNodes().item(1).getTextContent());
                nuevoRamo.setTipoCeremonia(nodoAdorno.getChildNodes().item(2).getTextContent());
                nuevoRamo.setTipoFlores(nodoAdorno.getChildNodes().item(3).getTextContent());
                nuevoRamo.setMensaje(nodoAdorno.getChildNodes().item(4).getTextContent());
                nuevoRamo.setFechaEntrega(LocalDate.parse(nodoAdorno.getChildNodes().item(5).getTextContent()));
                nuevoRamo.setLugarEntrega(nodoAdorno.getChildNodes().item(6).getTextContent());
                nuevoRamo.setDireccionEnvio(nodoAdorno.getChildNodes().item(7).getTextContent());
                nuevoRamo.setPrecio(Double.parseDouble(nodoAdorno.getChildNodes().item(8).getTextContent()));
                nuevoRamo.setNumFlores(Integer.parseInt(nodoAdorno.getChildNodes().item(9).getTextContent()));
                listaAdornos.add(nuevoRamo);
            } else if (nodoAdorno.getTagName().equals("Corona")) {
                nuevaCorona = new Corona();
                nuevaCorona.setNombre(nodoAdorno.getChildNodes().item(0).getTextContent());
                nuevaCorona.setApellidos(nodoAdorno.getChildNodes().item(1).getTextContent());
                nuevaCorona.setTipoCeremonia(nodoAdorno.getChildNodes().item(2).getTextContent());
                nuevaCorona.setTipoFlores(nodoAdorno.getChildNodes().item(3).getTextContent());
                nuevaCorona.setMensaje(nodoAdorno.getChildNodes().item(4).getTextContent());
                nuevaCorona.setFechaEntrega(LocalDate.parse(nodoAdorno.getChildNodes().item(5).getTextContent()));
                nuevaCorona.setLugarEntrega(nodoAdorno.getChildNodes().item(6).getTextContent());
                nuevaCorona.setDireccionEnvio(nodoAdorno.getChildNodes().item(7).getTextContent());
                nuevaCorona.setPrecio(Double.parseDouble(nodoAdorno.getChildNodes().item(8).getTextContent()));
                nuevaCorona.setDiametro(Double.parseDouble(nodoAdorno.getChildNodes().item(9).getTextContent()));
                listaAdornos.add(nuevaCorona);
            } else {
                if (nodoAdorno.getTagName().equals("Centro")) {
                    nuevoCentro = new Centro();
                    nuevoCentro.setNombre(nodoAdorno.getChildNodes().item(0).getTextContent());
                    nuevoCentro.setApellidos(nodoAdorno.getChildNodes().item(1).getTextContent());
                    nuevoCentro.setTipoCeremonia(nodoAdorno.getChildNodes().item(2).getTextContent());
                    nuevoCentro.setTipoFlores(nodoAdorno.getChildNodes().item(3).getTextContent());
                    nuevoCentro.setMensaje(nodoAdorno.getChildNodes().item(4).getTextContent());
                    nuevoCentro.setFechaEntrega(LocalDate.parse(nodoAdorno.getChildNodes().item(5).getTextContent()));
                    nuevoCentro.setLugarEntrega(nodoAdorno.getChildNodes().item(6).getTextContent());
                    nuevoCentro.setDireccionEnvio(nodoAdorno.getChildNodes().item(7).getTextContent());
                    nuevoCentro.setPrecio(Double.parseDouble(nodoAdorno.getChildNodes().item(8).getTextContent()));
                    nuevoCentro.setTamanio(nodoAdorno.getChildNodes().item(9).getTextContent());
                    listaAdornos.add(nuevoCentro);
                }
            }
        }
    }
}
