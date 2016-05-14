/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * Contiene información acerca del lugar de procedencia de la carne
 * @author Carlos Valencia Y Cesar Vega
 */
public class Localidad {
    
    private int key; //ID de referencia en la base de datos
    private String descripcion; // nombre de la localidad a la que pertenece
    
    /**
     * Obtiene el ID de referencia de la localidad
     * @return int ID
     */
    public int getKey() {
        return key;
    }

    /**
     * Obtiene el lugar de procedencia de la carne
     * @return String descripción
     */
    public String getDescripcion() {
        return descripcion;
    }    
    
    /**
     * Crea una localidad de procedencia de la carne
     * @param key int ID
     * @param descripcion String lugar de donde viene la carne 
     */
    public Localidad(int key, String descripcion){
        this.key = key;
        this.descripcion = descripcion;
    }            
    
}
