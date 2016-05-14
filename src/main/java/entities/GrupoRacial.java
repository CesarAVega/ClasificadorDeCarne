/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * Crea un grupo racial de la res
 * @author Carlos Valencia Y Cesar Vega
 */
public class GrupoRacial {
    
    private int key; // ID del grupo racial
    private String descripcion; // Descripción del grupo racial
    
    /**
     * Obtiene el ID de referencia del grupo racial
     * @return int ID
     */
    public int getKey() {
        return key;
    }

    /**
     * Obtiene el grupo racial de la vaca
     * @return String descripción
     */
    public String getDescripcion() {
        return descripcion;
    }    
    
    /**
     * Crea un grupo racial de la vaca
     * @param key int ID
     * @param descripcion String grupo racial de la vaca 
     */
    public GrupoRacial(int key, String descripcion){
        this.key = key;
        this.descripcion = descripcion;
    }         
}

