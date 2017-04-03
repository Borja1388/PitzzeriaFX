/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitzeria;

import Modelo.Pizza;
import Modelo.Precios;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Borja
 */
public class FXMLDocumentController implements Initializable {

    ObservableList<String> tipoPizza = FXCollections.observableArrayList("Basica", "Cuatro Quesos", "Barbacoa", "Mexicana");
    ObservableList<String> ingredientesExtra = FXCollections.observableArrayList("SIN INGREDIENTES", "Jamon", "Tomate", "Cebolla", "Queso", "Olivas");
    ObservableList<String> tamaPizza = FXCollections.observableArrayList("Pequeña", "Mediana", "Grande");

    private String tipomasa;
    private String tipopizza;
    private String tamanyo;
    Pizza p1 = new Pizza();
    Precios precios = new Precios();
    File archivo;
    @FXML
    private AnchorPane panel;
    @FXML
    private AnchorPane Masa;
    @FXML
    private RadioButton normal;
    @FXML
    private RadioButton integral;
    @FXML
    private AnchorPane pizza;
    @FXML
    private ComboBox<String> combo;
    @FXML
    private AnchorPane ingrediente;
    @FXML
    private ListView<String> lista;
    @FXML
    private AnchorPane taman;
    @FXML
    private AnchorPane tiket;
    @FXML
    private Label pMasa;
    @FXML
    private Label Ptipo;
    @FXML
    private Label pIngredientes;
    @FXML
    private Label pTama;
    @FXML
    private TextField precioTotal;
    @FXML
    private ToggleGroup group;
    @FXML
    private ComboBox<String> tamano;
    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label l4;
    @FXML
    private Pane pane;
    @FXML
    private Button factura;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        combo.setItems(tipoPizza);
        combo.setValue("Basica");
        Ptipo.setText(precios.buscarPrecio("Basica") + "");
        Ptipo.setText(precios.precio.get(p1.getTipo()) + "");
        tamano.setValue("Pequeña");
        pTama.setText(precios.precio.get(p1.getTamanyo()) + "");
        normal.setSelected(true);
        pMasa.setText(precios.precio.get(p1.getMasa()) + "");
        lista.getSelectionModel().select("SIN INGREDIENTES");
        pIngredientes.setText(precios.precio.get(p1.getIngredientes()) + "");
        this.precioTotal.setText(p1.calcularPrecio() + "");
        lista.setItems(ingredientesExtra);
        tamano.setItems(tamaPizza);
        lista.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

//        tipos.put("Basica", 3.0);
//        tipos.put("Cuatro Quesos", 5.0);
//        tipos.put("Barbacoa", 7.0);
//        tipos.put("Mexicana", 8.50);
//        ingredientes.put("SIN INGREDIENTES", 0.0);
//        ingredientes.put("Jamon", 0.50);
//        ingredientes.put("Tomate", 1.50);
//        ingredientes.put("Cebolla", 2.50);
//        ingredientes.put("Queso", 0.75);
//        ingredientes.put("Olivas", 1.0);
//        masas.put("Normal", 9.0);
//        masas.put("Integral", 9.50);
//        tama.put("Pequeña",0.0);
//        tama.put("Mediana",0.15);
//        tama.put("Grande",0.30);
    }

    @FXML
    private void clicarCombo(ActionEvent event) {
        String tipoPizza1 = combo.getValue();
        double tipoPizza2 = precios.buscarPrecio(tipoPizza1);
        p1.setTipo(tipoPizza1);
        Ptipo.setText(tipoPizza2 + "");
        l2.setText(tipoPizza1);
        precioTotal.setText(p1.calcularPrecio() + "");

    }

    @FXML
    private void cliclarIngredientes(MouseEvent event) {
        double ingre = 0.0;
        String texto = "";
        String texto1 = "";
        ObservableList<String> tipoIngredientes = lista.getSelectionModel().getSelectedItems();
        for (int i = 0; i < tipoIngredientes.size(); i++) {
            texto1 = tipoIngredientes.get(i);
            texto += tipoIngredientes.get(i) + ",";
            ingre = ingre + precios.buscarPrecio(tipoIngredientes.get(i));
            p1.ingredientesSeleccionado.put(texto1, precios.buscarPrecio(texto1));

        }

        l3.setText(texto);
        pIngredientes.setText(ingre + "");

        precioTotal.setText(p1.calcularPrecio() + "");
    }

    @FXML
    private void clicarMasa(ActionEvent event) {

        if (normal.isSelected()) {
            p1.setMasa("Normal");
            double masa = precios.buscarPrecio("Normal");
            pMasa.setText(masa + " ");
            l1.setText("Normal");

        }
        if (integral.isSelected()) {
            p1.setMasa("Integral");
            double masa = precios.buscarPrecio("Integral");
            pMasa.setText(masa + " ");
            l1.setText("Integral");
        }
        precioTotal.setText(p1.calcularPrecio() + "");
    }

    @FXML
    private void clicarCombo2(ActionEvent event) {

        String ta = tamano.getValue();
        double ta1 = precios.buscarPrecio(ta);
        p1.setTamanyo(ta);
        pTama.setText(ta1 + " ");
        l4.setText(ta);
        precioTotal.setText(p1.calcularPrecio() + "");
    }

    @FXML
    private void generarFactura(MouseEvent event) throws IOException {
        DirectoryChooser f1=new DirectoryChooser();
        archivo=f1.showDialog(new Stage());
        p1.generarTicket(archivo.getAbsoluteFile());
    }

}
