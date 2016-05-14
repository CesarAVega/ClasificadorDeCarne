/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 * Contiene la descripción del animal 
 * @author Carlos Valencia Y Cesar Vega
 */
public class Animal {
    
    private int ID;
    private Tipo tipo;
    private int edad;
    private int kpv;
    private Localidad localidad;
    private Carne carne;
    private GrupoRacial grupoRacial;
    private Sistema sistema;

    /**
     * Obtiene ID de referencia al animal
     * @return int ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Obtiene Tipo de animal
     * @return Tipo de animal
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Obtiene la edad del animal
     * @return int edad del animal
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Obtiene los Kilogramos de peso vivo del animal
     * @return int Kilogramos de peso vivo del animal
     */
    public int getKpv() {
        return kpv;
    }

    /**
     * Obtiene la Localidad de procedencia del animal
     * @return Localidad de procedencia del animal
     */
    public Localidad getLocalidad() {
        return localidad;
    }

    /**
     * Obtiene la descripción de la carne de animal
     * @return Carne que contiene la descripción de la carne del animal
     */
    public Carne getCarne() {
        return carne;
    }

    /**
     * Obtiene Grupo Racial de animal
     * @return GrupoRacial descripción del cruce del animal
     */
    public GrupoRacial getGrupoRacial() {
        return grupoRacial;
    }

    /**
     * Obtiene sistema de cómo fue alimentado el animal
     * @return System descripción de cómo fue alimentado el animal
     */
    public Sistema getSistema() {
        return sistema;
    }
    

    /**
     * Crear un animal en base a la información suministrada
     * @param ID int de referencia
     * @param tipo tipo de animal
     * @param edad int edad del animal
     * @param kpv int kilogramo por peso vivo
     * @param localidad Localidad de procedencia del animal
     * @param carne Carne del animal
     * @param grupoRacial GrupoRacial del animal
     * @param sistema Sistema con el que alimentado el animal
     */
    public Animal(int ID, Tipo tipo, int edad, int kpv, Localidad localidad, 
            Carne carne, GrupoRacial grupoRacial, Sistema sistema){
        this.ID = ID;
        this.tipo = tipo;
        this.edad = edad;
        this.kpv = kpv;
        this.localidad = localidad;
        this.carne = carne;
        this.grupoRacial = grupoRacial;
        this.sistema = sistema;
    }
    
}
