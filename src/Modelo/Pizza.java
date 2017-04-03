/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author admin
 */
public class Pizza {

    private String tipo = "Basica";
    private String ingredientes = "SIN INGREDIENTES";
    private String tamanyo = "Pequeña";
    private String masa = "Normal";
     int contador = 0;

    public Map<String, Double> ingredientesSeleccionado = new HashMap<>();

    Precios precios = new Precios();

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getTamanyo() {
        return tamanyo;
    }

    public void setTamanyo(String tamanyo) {
        this.tamanyo = tamanyo;
    }

    public String getMasa() {
        return masa;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public double calcularPrecio() {
        double precioFinal;
        double precio;
        double iPrecio = 0.0;
        boolean encontrado = false;
        double tPrecio, mPrecio, taPrecio;
        if (precios.precio.get(this.getTipo()) != null) {
            tPrecio = precios.buscarPrecio(getTipo());
        } else {
            tPrecio = 0.0;
        }
        if (precios.precio.get(this.getMasa()) != null) {
            mPrecio = precios.buscarPrecio(getMasa());
        } else {
            mPrecio = 0.0;
        }
        if (precios.precio.get(this.getTamanyo()) != null) {
            taPrecio = precios.buscarPrecio(getTamanyo());
        } else {
            taPrecio = 0.0;
        }
        Iterator it = ingredientesSeleccionado.keySet().iterator();
        while (it.hasNext() && encontrado == false) {
            String ingre = (String) it.next();
            precio = ingredientesSeleccionado.get(ingre);
            iPrecio += precio;
            encontrado = true;

        }
        precioFinal = tPrecio + mPrecio + iPrecio;
        precioFinal = precioFinal + (precioFinal * taPrecio);

        return precioFinal;
    }

    public void generarTicket(File archivo) {
        
        double precio;
        String ingre;
        LocalDateTime fecha = LocalDateTime.now();
        if (archivo.lastModified() != fecha.getSecond()) {

            archivo = Paths.get("Factura" + contador + ".txt").toFile();
            try (BufferedWriter out = Files.newBufferedWriter(archivo.toPath(), StandardOpenOption.CREATE)) {
                out.write("Masa:" + getMasa() + "  " + precios.buscarPrecio(getMasa()));
                out.newLine();
                out.write("Tipo:" + getTipo() + "  " + precios.buscarPrecio(getTipo()));
                out.newLine();
                out.write("Ingredientes:");
                Iterator it = ingredientesSeleccionado.keySet().iterator();
                while (it.hasNext()) {
                    ingre = (String) it.next();
                    precio = ingredientesSeleccionado.get(ingre);
                    out.write(" " + ingre + "  " + precio);

                }
                out.newLine();
                out.write("Tamaño:" + getTamanyo() + "  " + precios.buscarPrecio(getTamanyo()));
                out.newLine();
                out.write("PrecioTotal:" + calcularPrecio() + " ");
                out.newLine();
                out.write("Fecha:" + fecha.toString());
                out.newLine();
            } catch (IOException e) {
                System.out.println("Error al abrir el archivo");
            }
            contador++;
        }

    }

}
