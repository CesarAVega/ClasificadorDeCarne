/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * Atributos adicionales de la carne
 * @author Carlos Valencia Y Cesar Vega
 */
public class AtributoCarne {
    
    private int key; //ID de referencia en la base de datos
    private String descripcion; // descripción del atributo adicional para la carne
    private Calidad calidad;

    /**
     * Permite modificar el valor de la Calidad
     * @param calidad 
     */
    public void setCalidad(Calidad calidad) {
        this.calidad = calidad;
    }
    
    /**
     * Obtiene el ID de referencia para el atributo
     * @return int ID
     */
    public int getKey() {
        return key;
    }

    /**
     * Obtiene la descripción del atributo de la carne
     * @return String descripción
     */
    public String getDescripcion() {
        return descripcion;
    }    
    
    /**
     * Obtiene la calidad para este atributo
     * @return Calidad del atributo
     */
    public Calidad getCalidad() {
        return calidad;
    }
    
    
    
    /**
     * Crea un atributo para la carne y contiene una calidad
     * @param key int ID
     * @param descripcion String descripción del atributo
     * @param calidad Calidad para este atributo adicional
     */
    public AtributoCarne(int key, String descripcion, Calidad calidad){
        this.key = key;
        this.descripcion = descripcion;
        this.calidad = calidad;
    }            
}
