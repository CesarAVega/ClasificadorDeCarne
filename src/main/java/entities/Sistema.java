/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * Contiene y crea la descripción de la forma en la que se alimenta el ganado
 * @author Carlos Valencia Y Cesar Vega
 */
public class Sistema {
    
    private int key; //ID de referencia en la base de datos
    private String descripcion; // Nombre del sistema con el que se alimenta el ganado
    
    /**
     * Obtiene el ID de referencia del sistema
     * @return int ID
     */
    public int getKey() {
        return key;
    }

    /**
     * Obtiene la descripción de la forma en la que se alimenta el ganado
     * @return String descripción
     */
    public String getDescripcion() {
        return descripcion;
    }    
    
    /**
     * Crea la descripción de la forma en la que se alimenta el ganado
     * @param key int ID
     * @param descripcion String la descripción de la forma en la que se alimenta el ganado
     */
    public Sistema(int key, String descripcion){
        this.key = key;
        this.descripcion = descripcion;
    }         
}
