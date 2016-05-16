/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import entities.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Contiene las funciones de consulta para un objeto animal
 * @author Carlos Valencia y Cesar Vega
 */
public interface DaoAnimal {
    
    /**
     * Obtiene todos los datos de la base de datos
     * @return Set< Animal > - conjunto de animales en base a la información de la base de datos
     * @throws PersistenceException 
     */
    public Set<Animal> getAnimals() throws PersistenceException;
    
    /**
     * Obtiene la localidad en base a su ID
     * @param ID identificador de la localidad
     * @return Localidad a la que pertenece el animal
     * @throws PersistenceException 
     */
    public Localidad getLocalidad(int ID) throws PersistenceException;
    
    /**
     * Obtiene el grupo racial en base a su ID
     * @param ID identificador del grupo racial
     * @return GrupoRacial al que pertenece el animal
     * @throws PersistenceException 
     */
    public GrupoRacial getGrupoRacial(int ID) throws PersistenceException;
    
    /**
     * Obtiene el sistema en base a su ID
     * @param ID identificador del sistema con el que fue alimentado el animal
     * @return Sistema con el que fue alimentado el animal
     * @throws PersistenceException 
     */
    public Sistema getSistema(int ID) throws PersistenceException;
    
    /**
     * Obtiene el tipo en base a su ID
     * @param ID identificador del tipo de animal
     * @return Tipo al que pertenece el animal
     * @throws PersistenceException 
     */
    public Tipo getTipo(int ID) throws PersistenceException;
    
    /**
     * Obtiene la Carne en base a su ID
     * @param ID identificador de la carne es el mismo del animal
     * @return Carne que contiene las características de esta y pertenece al animal
     * @throws PersistenceException 
     */
    public Carne getCarne(int ID) throws PersistenceException;
    
    /**
     * Obtiene el grupo de atributos adicionales de la carne en base a su ID
     * @param ID identificador de la carne
     * @return ArrayList< AtributoCarne > de la carne estos cuentan con su propia calificación de calidad
     * @throws PersistenceException 
     */
    public ArrayList<AtributoCarne> getAtributo(int ID) throws PersistenceException;
    
    /**
     * Obtiene la descripcion de un atributo
     * @param ID identificador del atributo
     * @return String nombre del atributo
     * @throws PersistenceException 
     */
    public String getDescripcioAtributo(int ID) throws PersistenceException;
    
    /**
     * Obtiene la calidad en base a su ID
     * @param ID identificador de la calidad
     * @return Calidad del atributo o la carne del animal
     * @throws PersistenceException 
     */
    public Calidad getCalidad(int ID) throws PersistenceException;

    /**
     * Obtiene todas las localidades de la base de datos
     * @return ArrayList-Localidad- al que puede pertenecer un animal
     * @throws PersistenceException 
     */
    public ArrayList<Localidad> getTodosLocalidad() throws PersistenceException;
    
    /**
     * Obtiene todos los grupos raciales de la base de datos
     * @return ArrayList-GrupoRacial- al que puede pertenecer un animal
     * @throws PersistenceException 
     */
    public ArrayList<GrupoRacial> getTodosGrupoRacial() throws PersistenceException;
    
    /**
     * Obtiene los sistemas de alimentación con los que se puede alimentar un animal contenidos en la base de datos
     * @return ArrayList-Sistema- con los que puede ser alimentado un animal
     * @throws PersistenceException 
     */
    public ArrayList<Sistema> getTodosSistema() throws PersistenceException;
    
    /**
     * Obtiene los tipo de animal que hay en la base de datos
     * @return ArrayList-Tipo- al que puede pertenecer el animal
     * @throws PersistenceException 
     */
    public ArrayList<Tipo> getTodosTipo() throws PersistenceException;    
    
    /**
     * Obtiene las descripciones de los atributos adicionales de la carne    
     * @return ArrayList-AtributoCarne- nombre del atributo
     * @throws PersistenceException 
     */
    public ArrayList<AtributoCarne> getTodosDescripcioAtributo() throws PersistenceException;
    
    /**
     * Obtiene las calidades con las que puede ser evaluada la carne y estan contenidas en la base de datos
     * @return ArrayList-Calidad- que puede tener el atributo o la carne del animal
     * @throws PersistenceException 
     */
    public ArrayList<Calidad> getTodosCalidad() throws PersistenceException;
    
    
}

