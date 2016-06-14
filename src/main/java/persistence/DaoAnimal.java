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
     * @return Set Animal - conjunto de animales en base a la información de la base de datos
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public Set<Animal> getAnimals() throws PersistenceException;
    
    /**
     * Obtiene la localidad en base a su ID
     * @param ID identificador de la localidad
     * @return Localidad a la que pertenece el animal
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public Localidad getLocalidad(int ID) throws PersistenceException;
    
    /**
     * Obtiene el grupo racial en base a su ID
     * @param ID identificador del grupo racial
     * @return GrupoRacial al que pertenece el animal
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public GrupoRacial getGrupoRacial(int ID) throws PersistenceException;
    
    /**
     * Obtiene el sistema en base a su ID
     * @param ID identificador del sistema con el que fue alimentado el animal
     * @return Sistema con el que fue alimentado el animal
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public Sistema getSistema(int ID) throws PersistenceException;
    
    /**
     * Obtiene el tipo en base a su ID
     * @param ID identificador del tipo de animal
     * @return Tipo al que pertenece el animal
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public Tipo getTipo(int ID) throws PersistenceException;
    
    /**
     * Obtiene la Carne en base a su ID
     * @param ID identificador de la carne es el mismo del animal
     * @return Carne que contiene las características de esta y pertenece al animal
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public Carne getCarne(int ID) throws PersistenceException;
    
    /**
     * Obtiene el grupo de atributos adicionales de la carne en base a su ID
     * @param ID identificador de la carne
     * @return ArrayList AtributoCarne de la carne estos cuentan con su propia calificación de calidad
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public ArrayList<AtributoCarne> getAtributo(int ID) throws PersistenceException;
    
    /**
     * Obtiene la descripcion de un atributo
     * @param ID identificador del atributo
     * @return String nombre del atributo
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public String getDescripcioAtributo(int ID) throws PersistenceException;
    
    /**
     * Obtiene la calidad en base a su ID
     * @param ID identificador de la calidad
     * @return Calidad del atributo o la carne del animal
     * @throws PersistenceException Clase encargada de atrapar las excepciones de la capa de persistencia
     */
    public Calidad getCalidad(int ID) throws PersistenceException;

    /**
     * Obtiene todas las localidades de la base de datos
     * @return ArrayList-Localidad- al que puede pertenecer un animal
     * @throws PersistenceException Clase encargada de administrar las excepciones de la capa de persistencia
     */
    public ArrayList<Localidad> getTodosLocalidad() throws PersistenceException;
    
    /**
     * Obtiene todos los grupos raciales de la base de datos
     * @return ArrayList-GrupoRacial- al que puede pertenecer un animal
     * @throws PersistenceException Clase encargada de administrar las excepciones de la capa de persistencia
     */
    public ArrayList<GrupoRacial> getTodosGrupoRacial() throws PersistenceException;
    
    /**
     * Obtiene los sistemas de alimentación con los que se puede alimentar un animal contenidos en la base de datos
     * @return ArrayList-Sistema- con los que puede ser alimentado un animal
     * @throws PersistenceException Clase encargada de administrar las excepciones de la capa de persistencia
     */
    public ArrayList<Sistema> getTodosSistema() throws PersistenceException;
    
    /**
     * Obtiene los tipo de animal que hay en la base de datos
     * @return ArrayList-Tipo- al que puede pertenecer el animal
     * @throws PersistenceException Clase encargada de administrar las excepciones de la capa de persistencia
     */
    public ArrayList<Tipo> getTodosTipo() throws PersistenceException;    
    
    /**
     * Obtiene las descripciones de los atributos adicionales de la carne    
     * @return ArrayList-AtributoCarne- nombre del atributo
     * @throws PersistenceException Clase encargada de administrar las excepciones de la capa de persistencia
     */
    public ArrayList<AtributoCarne> getTodosDescripcioAtributo() throws PersistenceException;
    
    /**
     * Obtiene las calidades con las que puede ser evaluada la carne y estan contenidas en la base de datos
     * @return ArrayList-Calidad- que puede tener el atributo o la carne del animal
     * @throws PersistenceException Clase encargada de administrar las excepciones de la capa de persistencia
     */
    public ArrayList<Calidad> getTodosCalidad() throws PersistenceException;
    
    /**
     * Inserta una calidad a la base de datos
     * @param localidad a ser ingresada en la base de datos
     */
    public void insertLocalidad(Localidad localidad);
    
    /**
     * Inserta una calidad a la base de datos
     * @param grupoRacial a ser ingresada en la base de datos
     */
    public void insertGrupoRacial(GrupoRacial grupoRacial);
    
    /**
     * Inserta una calidad a la base de datos
     * @param tipo a ser ingresada en la base de datos
     */
    public void insertTipo(Tipo tipo);
    
    /**
     * Inserta una calidad a la base de datos
     * @param sistema a ser ingresada en la base de datos 
     */
    public void insertSistema(Sistema sistema);
    
    /**
     * Inserta una calidad a la base de datos
     * @param calidad a ser ingresada en la base de datos
     */
    public void insertCalidad(Calidad calidad);
    
    
}

