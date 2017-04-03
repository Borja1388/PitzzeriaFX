/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author admin
 */
public class Precios {
    
     public Map<String, Double> precio = new HashMap<>();

    public Precios() {
        
        precio.put("Basica", 3.0);
        precio.put("Cuatro Quesos", 5.0);
        precio.put("Barbacoa", 7.0);
        precio.put("Mexicana", 8.50);
        precio.put("SIN INGREDIENTES", 0.0);
        precio.put("Jamon", 0.50);
        precio.put("Tomate", 1.50);
        precio.put("Cebolla", 2.50);
        precio.put("Queso", 0.75);
        precio.put("Olivas", 1.0);
        precio.put("Normal", 9.0);
        precio.put("Integral", 9.50);
        precio.put("Pequeña",0.0);
        precio.put("Mediana",0.15);
        precio.put("Grande",0.30);
    }
    
    public double buscarPrecio(String ingrediente){
 
       return  precio.get(ingrediente);
    }

    public Map<String, Double> getPrecios() {
        return precio;
    }

    public void setPrecios(Map<String, Double> precios) {
        this.precio = precios;
    }
    
    
}
