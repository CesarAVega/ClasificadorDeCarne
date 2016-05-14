/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * Crea una calidad de referencia a la carne de res
 * @author Carlos Valencia Y Cesar Vega
 */
public class Calidad {
    
    private int key; // ID del grupo racial
    private String descripcion; // Descripción de la calidad
    
    /**
     * Obtiene el ID de referencia de la calidad
     * @return int ID
     */
    public int getKey() {
        return key;
    }

    /**
     * Obtiene la descripción de la calidad
     * @return String descripción
     */
    public String getDescripcion() {
        return descripcion;
    }    
    
    /**
     * Crea una calidad de referencia a la carne de res
     * @param key int ID
     * @param descripcion String descripción de la calidad
     */
    public Calidad(int key, String descripcion){
        this.key = key;
        this.descripcion = descripcion;
    } 
}
