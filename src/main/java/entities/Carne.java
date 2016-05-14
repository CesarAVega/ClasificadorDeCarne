/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;

/**
 * Contiene la descripción de la carne del animal
 * @author Carlos Valencia Y Cesar Vega
 */
public class Carne {
    
    private double PesoCanalFrioDer; // peso de la canal frio derecha
    private double PesoCanalFrioIzq; // peso de la canal frio izquierda
    private double OjoDeLaChuleta; // ancho del ojo de la chuleta
    private double GrosorDeGrasaDorsal; // grosor de la grasa dorsal de la carne
    private ArrayList<AtributoCarne> calidades; // Array de calidades
    private Calidad calidad; // Calidad final

    /**
     * Poner la calidad que coresponde a la carne
     * @param calidad nueva calidad
     */
    public void setCalidad(Calidad calidad) {
        this.calidad = calidad;
    }

    /**
     * Obtiene el Peso de la canal frio derecho de la carne
     * @return double peso KG
     */
    public double getPesoCanalFrioDer() {
        return PesoCanalFrioDer;
    }

    /**
     * Obtiene el Peso de la canal frio izquierdo de la carne
     * @return double peso Kg
     */
    public double getPesoCanalFrioIzq() {
        return PesoCanalFrioIzq;
    }

    /**
     * Obtiene el ancho del ojo de la chuleta
     * @return double anchura en cm2
     */
    public double getOjoDeLaChuleta() {
        return OjoDeLaChuleta;
    }

    /**
     * Obtiene el grosor de grasa dorsal del animal
     * @return double grosor en ml
     */
    public double getGrosorDeGrasaDorsal() {
        return GrosorDeGrasaDorsal;
    }

    /**
     * Obtiene la descripción de en calidades según las partes de la carne que se analizan
     * @return ArrayList<AtributoCarne> contiene la descripción de calidades de la carne
     */
    public ArrayList<AtributoCarne> getCalidades() {
        return calidades;
    }

    /**
     * Obtiene la calidad de la carne suministrada por la red neuronal
     * @return Calidad de la carne 
     */
    public Calidad getCalidad() {
        return calidad;
    }
    
    /**
     * Crea una Carne de res en base a la información suministrada
     * @param PesoCanalFrioDer peso de la canal frio derecha
     * @param PesoCanalFrioIzq peso de la canal frio izquierda
     * @param OjoDeLaChuleta ancho del ojo de la chuleta
     * @param GrosorDeGrasaDorsal grosor de la grasa dorsal de la carne
     * @param calidades Array de calidades
     * @param calidad Calidad final
     */    
    public Carne(double PesoCanalFrioDer, double PesoCanalFrioIzq, 
            double OjoDeLaChuleta, double GrosorDeGrasaDorsal, 
            ArrayList<AtributoCarne> calidades, Calidad calidad){

        this.PesoCanalFrioDer = PesoCanalFrioDer;
        this.PesoCanalFrioIzq = PesoCanalFrioIzq; 
        this.OjoDeLaChuleta = OjoDeLaChuleta;
        this.GrosorDeGrasaDorsal = GrosorDeGrasaDorsal;
        this.calidades = calidades; 
        this.calidad = calidad; 

    }
    
    
}
