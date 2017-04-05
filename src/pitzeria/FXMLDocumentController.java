/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitzeria;

import Modelo.Pizza;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    @FXML
    private Button cargar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        combo.setDisable(true);
        normal.setDisable(true);
        integral.setDisable(true);
        lista.setDisable(true);
        tamano.setDisable(true);
        factura.setDisable(true);
        combo.setItems(tipoPizza);
        combo.setValue("Elige Tipo");
        lista.setItems(ingredientesExtra);
        tamano.setItems(tamaPizza);
        tamano.setValue("Elige Tamaño");
        lista.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void clicarCombo(ActionEvent event) {
        String tipoPizza1 = combo.getValue();
        double tipoPizza2 = p1.buscarPrecio(tipoPizza1);
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
            ingre = ingre + p1.buscarPrecio(tipoIngredientes.get(i));
            p1.ingredientesSeleccionado.put(texto1, p1.buscarPrecio(texto1));

        }

        l3.setText(texto);
        pIngredientes.setText(ingre + "");

        precioTotal.setText(p1.calcularPrecio() + "");
    }

    @FXML
    private void clicarMasa(ActionEvent event) {

        if (normal.isSelected()) {
            p1.setMasa("Normal");
            double masa = p1.buscarPrecio("Normal");
            pMasa.setText(masa + " ");
            l1.setText("Normal");

        }
        if (integral.isSelected()) {
            p1.setMasa("Integral");
            double masa = p1.buscarPrecio("Integral");
            pMasa.setText(masa + " ");
            l1.setText("Integral");
        }
        precioTotal.setText(p1.calcularPrecio() + "");
    }

    @FXML
    private void clicarCombo2(ActionEvent event) {

        String ta = tamano.getValue();
        double ta1 = p1.buscarPrecio(ta);
        p1.setTamanyo(ta);
        pTama.setText(ta1 + " ");
        l4.setText(ta);
        precioTotal.setText(p1.calcularPrecio() + "");
    }

    @FXML
    private void generarFactura(MouseEvent event) throws IOException {
        DirectoryChooser f1=new DirectoryChooser();
        File directorio=f1.showDialog(new Stage());
        p1.generarTicket(directorio);
    }

    @FXML
    private void clicarCargar(MouseEvent event) {
        FileChooser fc = new FileChooser();
        File archivo=fc.showOpenDialog(new Stage());
        p1.cargarPrecios(archivo);
        combo.setDisable(false);
        normal.setDisable(false);
        integral.setDisable(false);
        lista.setDisable(false);
        tamano.setDisable(false);
        factura.setDisable(false);
    }

}
