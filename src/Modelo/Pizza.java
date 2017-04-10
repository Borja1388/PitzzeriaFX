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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/**
 *
 * @author admin
 */
public class Pizza {

    private String tipo;
    private String ingredientes;
    private String tamanyo;
    private String masa;
    static int contador = 0;

    public Map<String, Double> ingredientesSeleccionado = new HashMap<>();
    public Map<String, Double> preciosCarta = new HashMap<>();

    

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
        if (this.getTipo()!=null) {
            tPrecio = this.buscarPrecio(getTipo());
        } else {
            tPrecio = 0.0;
        }
        if (this.getMasa()!=null) {
            mPrecio = this.buscarPrecio(getMasa());
        } else {
            mPrecio = 0.0;
        }
        if (this.getTamanyo() != null) {
            taPrecio = this.buscarPrecio(getTamanyo());
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

    public void generarTicket(File directorio) {

        double precio;
        String ingre;
        LocalDateTime fecha = LocalDateTime.now();

        String destino = directorio.getAbsolutePath() + "\\Factura" + contador + ".txt";

        Path dir = Paths.get(destino);
        try (BufferedWriter out = Files.newBufferedWriter(dir.toAbsolutePath(), StandardOpenOption.CREATE)) {
            out.write("|" + "TICKET" + "|");
            out.newLine();
            out.write("Masa:" + getMasa() + "  " + this.buscarPrecio(getMasa()));
            out.newLine();
            out.write("Tipo:" + getTipo() + "  " + this.buscarPrecio(getTipo()));
            out.newLine();
            out.write("Ingredientes:");
            Iterator it = ingredientesSeleccionado.keySet().iterator();
            while (it.hasNext()) {
                ingre = (String) it.next();
                precio = ingredientesSeleccionado.get(ingre);
                out.write(" " + ingre + "  " + precio);

            }
            out.newLine();
            out.write("Tamaño:" + getTamanyo() + "  " + this.buscarPrecio(getTamanyo()));
            out.newLine();
            out.write("PrecioTotal:" + calcularPrecio());
            out.newLine();
            out.write("Fecha:" + fecha.toString());
        } catch (IOException e) {
            System.out.println("Error al abrir el archivo");
        }
        contador++;
    }
    public double buscarPrecio(String ingrediente){
       
       return preciosCarta.get(ingrediente);
    }

    public void cargarPrecios(File archivo1) {
        List<String> trozos = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        List<String> resultadoLeer = new ArrayList<>();
        
        String destino = archivo1.getAbsolutePath();
        Path archivo = Paths.get(destino);

        try (Stream<String> datos = Files.lines(archivo)) {
            Iterator<String> it = datos.iterator();
            while (it.hasNext()) {
                resultadoLeer.add(it.next());
                
            }
            for (int i = 0; i < resultadoLeer.size(); i++) {
                StringTokenizer t1 = new StringTokenizer(resultadoLeer.get(i), ":");
                while (t1.hasMoreTokens()) {
                    trozos.add(t1.nextToken());
                    
                }
            }
            for (int i = 0; i < trozos.size(); i++) {

                if (i % 2 != 0) {
                     
                    valores.add(Double.parseDouble(trozos.get(i)));
                }
            }
            for (int i = 0; i < trozos.size(); i++) {
                if (i % 2 == 0) {
                    nombres.add(trozos.get(i));
                }
            }
            
            for (int i = 0; i < nombres.size(); i++) {
                for (int j = 0; j < valores.size(); j++) {
                    
                    preciosCarta.put(nombres.get(i),valores.get(i));
                }
            }
        } catch (IOException ex) {
            System.out.println("Error en la lectura del archivo");
        }
        System.out.println(preciosCarta);

    }

}
