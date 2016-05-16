/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neuroph.core.data.DataSetRow;
import persistence.*;

/**
 * Función principal para el manejo de la capa lógica
 * @author Carlos Valencia y Cesar Vega
 */
public class ServicesFacade {
    
    private static ServicesFacade instance=null; 
    
    private final Properties properties=new Properties(); // propiedades de la sesión actual
    
    /**
     * Método de creación de un ServiceFacade 
     * @param propFileName nombre del archivo de configuración 
     * @throws IOException 
     */
    private ServicesFacade(String propFileName) throws IOException{        
	InputStream input = null;
        input = this.getClass().getClassLoader().getResourceAsStream(propFileName);        
        properties.load(input);
    }
    /**
     * Obtiene la instancia de la sección actual
     * @param propertiesFileName nombre del archivo que contiene las especificaciones de la sesión que se desea abrir
     * @return ServicesFacade de la sección abierta
     * @throws RuntimeException 
     */
    public static ServicesFacade getInstance(String propertiesFileName) throws RuntimeException{
        if (instance==null){
            try {
                instance=new ServicesFacade(propertiesFileName);
            } catch (IOException ex) {
                throw new RuntimeException("Error on application configuration:",ex);
            }
        }        
        return instance;
    }

    /**
     * Carga todos los datos de la base de datos
     * @return Set<Animal> Conjunto de datos
     * @throws SQLException 
     */
    public Set<Animal> getData() throws SQLException{
        Set<Animal> ans = new LinkedHashSet<>();
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getAnimals(); // función de carga para todos los datos de la base de datos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión
            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }

    /**
     * Convierte un String a double
     * @param s String a convertir
     * @return double valor del String
     */
    public static double pdou(String s){
        return Double.parseDouble(s);
    }
    
    /**
     * Convierte un String a integer
     * @param s String a convertir
     * @return Integer valor del String
     */
    public static int pint(String s){
        return Integer.parseInt(s);
    }

    //Contiene la información de la Red Neuronal entrenada previamente y es la de mayor nivel de efectividad
    private static RedNeuronal nnet = new RedNeuronal();
    
    public RedNeuronal getNnet(){
        return nnet;
    }
    
    
    /**
     * Con relación a la red neuronal ya entrenada y más óptima se dan los resultados
     * @param animal Entrada
     * @return String valor de la clasificación
     */
    public Animal valueOf(Animal animal){
        String ans = null;
        //Se prepara la información para que sea recibida en una DataSet
        ArrayList<AtributoCarne> at = animal.getCarne().getCalidades();
        //Arreglo de Atributos de la carne
        int[] calidades = new int[at.size()];
        for (AtributoCarne a: at){
            calidades[a.getKey()-1] = a.getCalidad().getKey();
        }        
        
        Carne carne = animal.getCarne();
        //Se ingresan los datos normalizados
        DataSetRow dataRow = new DataSetRow(
                new double[]{
                    normalizacion( 0, animal.getLocalidad().getKey()), // Localidad
                    normalizacion( 1, animal.getGrupoRacial().getKey()), //Grupo Racial
                    normalizacion( 2, animal.getTipo().getKey()), // Tipo
                    normalizacion( 3, animal.getSistema().getKey()), // Sistema
                    normalizacion( 4, animal.getEdad()), // Edad
                    normalizacion( 5, animal.getKpv()), // Kilogramo por peso vivo
                    normalizacion( 6, calidades[0]), // Conformación de la canal
                    normalizacion( 7, calidades[1]), // Distribución de grasa subcutánea
                    normalizacion( 8, calidades[2]), // Cobertura de grasa peri renal
                    normalizacion( 9, calidades[3]), // Color de la grasa
                    normalizacion( 10, calidades[4]), // Color de la carne
                    normalizacion( 11, carne.getPesoCanalFrioDer()), // canal_frio_der
                    normalizacion( 12, carne.getPesoCanalFrioIzq()), // canal_frio_izq
                    normalizacion( 13, carne.getOjoDeLaChuleta()), // ojo_chuleta
                    normalizacion( 14, carne.getGrosorDeGrasaDorsal()) // grosor_grasa_dorsal
                }
        );
        
        nnet.getNnet().setInput(dataRow.getInput()); // se ingresa el valor del input a la red
        nnet.getNnet().calculate(); // se calcula
        
        // Redondear los valores para dar la respuesta
        double[] networkOutput = nnet.getNnet().getOutput();        
        for (int i = 0; i < networkOutput.length; i++){
            networkOutput[i] = Math.round(networkOutput[i]);
        }
        
        // Se crea la calidad y se modifica el parámetro del animal (en carne)
        Calidad calidad = valorRespuesta(networkOutput);
        animal.getCarne().setCalidad(calidad);
        return animal;
    }
    
    /**
     * Función de normalización de un dato
     * @param index Valor de la posición en el arregla de mínimos o máximos a comparar
     * @param a Valor entero a ser comparado con lo guardo en los arreglos mínimos y máximos
     * @return valor normalizado y redondeado a 5 dígitos
     */
    private double normalizacion(int index, double a){
        return Math.rint(((a-nnet.getMins()[index])/nnet.getDiff()[index])*100000)/100000;
    }
    
    /**
     * Convierte el vector de la solución con los valores redondeados en una Calidad 
     * @param d double[] contiene los valores redondeados de la solución
     * @return Calidad esta hace referencia a la calidad final de la carne
     */
    private Calidad valorRespuesta(double[] d){
        // se toma como primera instancia suponiendo que la carne es de calidad superior
        String ans = "SUPERIOR";        
        int ID = 1;
        // Verifica si el estado inicial es mentiras
        switch (Arrays.toString(d)) {
            case "[0.0, 0.0, 1.0, 0.0]":
                ans = "SELECCIONADA";
                ID = 2;
                break;
            case "[0.0, 1.0, 0.0, 0.0]":
                ans = "ESTANDAR";
                ID = 3;
                break;
            case "[1.0, 0.0, 0.0, 0.0]":
                ans = "COMERCIAL";
                ID = 4;
                break;
            default:
                break;
        }        
        
        return new Calidad(ID, ans);
    }
    
    /**
     * Implementa la opción de modificar varios animales
     * @param data Set< Animal > Grupo de animales a evaluar
     */
    public void valueOfall(Set<Animal> data){
        // envia cada uno de los animales a modificar su valor de calidad en la carne
        for (Animal a: data){
            valueOf(a);
        }
    }
    
    /**
     * Obtiene la localidad en base a su ID
     * @param ID identificador de la localidad
     * @return Localidad a la que pertenece el animal
     */
    public Localidad getLocalidad(int ID){
        Localidad ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getLocalidad(ID); // función de carga la localidad de la que procede el animal

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    /**
     * Obtiene el grupo racial en base a su ID
     * @param ID identificador del grupo racial
     * @return GrupoRacial al que pertenece el animal
     */
    public GrupoRacial getGrupoRacial(int ID){
        GrupoRacial ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getGrupoRacial(ID); // función de carga el grupo racial al que pertenece el animal

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    /**
     * Obtiene el sistema en base a su ID
     * @param ID identificador del sistema con el que fue alimentado el animal
     * @return Sistema con el que fue alimentado el animal
     */
    public Sistema getSistema(int ID){
        Sistema ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getSistema(ID); // función de carga el sistema con el que fue alimentado el animal

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    /**
     * Obtiene el tipo en base a su ID
     * @param ID identificador del tipo de animal
     * @return Tipo al que pertenece el animal
     */
    public Tipo getTipo(int ID){
        Tipo ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getTipo(ID); // función de carga el tipo de animal

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    /**
     * Obtiene la Carne en base a su ID
     * @param ID identificador de la carne es el mismo del animal
     * @return Carne que contiene las características de esta y pertenece al animal
     */
    public Carne getCarne(int ID){
        Carne ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getCarne(ID); // función de carga la carne que pertenece a un animal

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    /**
     * Obtiene el grupo de atributos adicionales de la carne en base a su ID
     * @param ID identificador de la carne
     * @return ArrayList< AtributoCarne > de la carne estos cuentan con su propia calificación de calidad
     */
    public ArrayList<AtributoCarne> getAtributo(int ID){
        ArrayList<AtributoCarne> ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getAtributo(ID); // función de carga de todos los atributos especiales de la carne que tiene calidad

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
        /**
     * Obtiene la descripcion de un atributo
     * @param ID identificador del atributo
     * @return String nombre del atributo
     */
    public String detDescripcioAtributo(int ID){
        String ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getDescripcioAtributo(ID); // función de carga Descripcion de un atributo especial de la carne

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    /**
     * Obtiene la calidad en base a su ID
     * @param ID identificador de la calidad
     * @return Calidad del atributo o la carne del animal
     */
    public Calidad getCalidad(int ID){
        Calidad ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getCalidad(ID); // función de carga Calidad

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
    
    /**
     * Obtiene todos los tipos en la base de datos
     * @return ArrayList-Tipo- al que pertenece el animal
     */
    public ArrayList<Tipo> getTodosTipo(){
        ArrayList<Tipo> ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getTodosTipo(); // función de carga todos los tipos de animal

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }

    /**
     * Obtiene todas las localidades de las que puede proceder un animal 
     * @return ArrayList-Localidad- a las que puede pertener un animal
     */
    public ArrayList<Localidad> getTodosLocalidad() {
        ArrayList<Localidad> ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getTodosLocalidad(); // función de carga todas las localidades

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }

    /**
     * Obtiene todos los grupos raciales que pueden pertenecer un animal  
     * @return ArrayList-GrupoRacial- al que puede pertenecer un animal
     */
    public ArrayList<GrupoRacial> getTodosGrupoRacial() {
        ArrayList<GrupoRacial> ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getTodosGrupoRacial(); // función de carga todos los Grupos Raciales

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }

    /**
     * Obtiene todos los sistemas de alimentación del animal  
     * @return ArrayList-Sistema- descripción de todos los sistemas
     */
    public ArrayList<Sistema> getTodosSistema() {
        ArrayList<Sistema> ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getTodosSistema(); // función de carga todos los sistemas

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }

    /**
     * Obtiene todas las calidades en la base de datos
     * @return ArrayList-Calidad- del atributo o la carne del animal
     */
    public ArrayList<Calidad> getTodosCalidad() {
        ArrayList<Calidad> ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getTodosCalidad(); // función de carga Calidad

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }

    /**
     * Obtiene la descripción de todos los atributos adicionales de la carne  
     * @return ArrayList-AtributoCarne- contiene la descipción de todos los atributos
     */
    public ArrayList<AtributoCarne> getTodosAtributoCarne() {
        ArrayList<AtributoCarne> ans  = null;
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            ans = dpro.getTodosDescripcioAtributo(); // llama a todos los atributos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ans;
    }
     
}
