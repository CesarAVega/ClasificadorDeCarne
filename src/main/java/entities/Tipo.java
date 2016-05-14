/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * Informacion del tipo de animal
 * @author Carlos Valencia Y Cesar Vega
 */
public class Tipo {
    
    private int key; //ID de referencia en la base de datos
    private String descripcion; // nombre del tipo de animal
    
    /**
     * Obtiene el ID de referencia del tipo de animal
     * @return int ID
     */
    public int getKey() {
        return key;
    }

    /**
     * Obtiene la descripción del tipo de animal
     * @return String descripción
     */
    public String getDescripcion() {
        return descripcion;
    }    
    
    /**
     * Crea un tipo de animal
     * @param key int ID
     * @param descripcion String descripción del tipo de animal
     */
    public Tipo(int key, String descripcion){
        this.key = key;
        this.descripcion = descripcion;
    }  
    
}
