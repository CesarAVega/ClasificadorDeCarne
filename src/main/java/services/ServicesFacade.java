/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.*;
import entities.Graficador.Graficador;
import entities.clasificadordecarnederes.ClasificadorDeCarneDeRes;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.primefaces.model.chart.LineChartModel;
import persistence.*;

/**
 * Función principal para el manejo de la capa lógica
 * @author Carlos Valencia y Cesar Vega
 */
public class ServicesFacade {
    
        
    private ArrayList<Localidad> localidades;
    private Set<Animal> data;
    private ArrayList<Calidad> calidades;
    private ArrayList<AtributoCarne> atributos;
    private ArrayList<Sistema> sistemas;
    private ArrayList<GrupoRacial> gruposRaciales;
    private ArrayList<Tipo> tipos;
    
    
    private static ServicesFacade instance=null; 
    
    private final Properties properties=new Properties(); // propiedades de la sesión actual
    
    /**
     * Método de creación de un ServiceFacade 
     * @param propFileName nombre del archivo de configuración 
     * @throws IOException 
     */
    private ServicesFacade(String propFileName) throws IOException{        
	InputStream input = this.getClass().getClassLoader().getResourceAsStream(propFileName);        
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

    /****************************** FUNCIONES DE APOYO ***************************************/
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

        /****************************** USO DE LA BASE DE DATOS ***************************************/
    
    /**
     * Carga todos los datos de la base de datos
     * @return Set<Animal> Conjunto de datos
     * @throws SQLException 
     */
    public Set<Animal> getData() throws SQLException{        
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            data = dpro.getAnimals(); // función de carga para todos los datos de la base de datos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión
            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }    
    
    /**
     * Obtiene todos los tipos en la base de datos
     * @return ArrayList-Tipo- al que pertenece el animal
     */
    public ArrayList<Tipo> getTodosTipo(){
        
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            tipos = dpro.getTodosTipo(); // función de carga todos los tipos de animal

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipos;
    }
    
    /**
     * Obtiene todas las localidades de las que puede proceder un animal 
     * @return ArrayList-Localidad- a las que puede pertener un animal
     */
    public ArrayList<Localidad> getTodosLocalidad() {
        
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            localidades = dpro.getTodosLocalidad(); // función de carga todas las localidades

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return localidades;
    }
    
    /**
     * Obtiene todos los grupos raciales que pueden pertenecer un animal  
     * @return ArrayList-GrupoRacial- al que puede pertenecer un animal
     */
    public ArrayList<GrupoRacial> getTodosGrupoRacial() {
        
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            gruposRaciales = dpro.getTodosGrupoRacial(); // función de carga todos los Grupos Raciales

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gruposRaciales;
    }

    /**
     * Obtiene todos los sistemas de alimentación del animal  
     * @return ArrayList-Sistema- descripción de todos los sistemas
     */
    public ArrayList<Sistema> getTodosSistema() {
        
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            sistemas = dpro.getTodosSistema(); // función de carga todos los sistemas

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sistemas;
    }
    
    /**
     * Obtiene todas las calidades en la base de datos
     * @return ArrayList-Calidad- del atributo o la carne del animal
     */
    public ArrayList<Calidad> getTodosCalidad() {
        
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            calidades = dpro.getTodosCalidad(); // función de carga Calidad

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calidades;
    }
    
    /**
     * Obtiene la descripción de todos los atributos adicionales de la carne  
     * @return ArrayList-AtributoCarne- contiene la descipción de todos los atributos
     */
    public ArrayList<AtributoCarne> getTodosAtributoCarne() {
        
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            atributos = dpro.getTodosDescripcioAtributo(); // llama a todos los atributos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atributos;
    }
    
    /**
     * Inserta una Localidad a la base de datos
     * @param localidad a ser ingresada en la base de datos
     */
    public void insertLocalidad(Localidad localidad) {        
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            dpro.insertLocalidad(localidad); // ingresa la localidad a la base de datos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inserta un Grupo Racial a la base de datos
     * @param grupoRacial a ser ingresada en la base de datos
     */
    public void insertGrupoRacial(GrupoRacial grupoRacial) {
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            dpro.insertGrupoRacial(grupoRacial); // llama a todos los atributos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inserta un Tipo a la base de datos
     * @param tipo a ser ingresada en la base de datos
     */
    public void insertTipo(Tipo tipo) {
                try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            dpro.insertTipo(tipo); // llama a todos los atributos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inserta un sistema a la base de datos
     * @param sistema a ser ingresada en la base de datos
     */
    public void insertSistema(Sistema sistema) {
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            dpro.insertSistema(sistema); // llama a todos los atributos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inserta una calidad a la base de datos
     * @param calidad a ser ingresada en la base de datos
     */
    public void insertCalidad(Calidad calidad) {
        try{
            DaoFactory df = DaoFactory.getInstance(properties); // se obtiene las características de la conexión
            
            df.beginSession(); // se abre la sesión

            DaoAnimal dpro = df.getDaoAnimal(); // se obtiene la referencia del DAO con respecto a la conexión abierta
            
            dpro.insertCalidad(calidad); // llama a todos los atributos

            df.commitTransaction(); // se verifica que toda la acción se cumplió con éxito
 
            df.endSession(); // se cierra la conexión            
            
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /****************************** RED NEURONAL BASE ***************************************/
    //Contiene la información de la Red Neuronal entrenada previamente y es la de mayor nivel de efectividad
    private static RedNeuronal nnet = new RedNeuronal();
    
    public RedNeuronal getNnet(){
        return nnet;
    }
    
    
    /****************************** SERVICIOS DE LA APP ***************************************/
    /**************** EVALUAR ANIMALES *******************************/    
    /**
     * Con relación a la red neuronal ya entrenada y más óptima se dan los resultados
     * @param animal Entrada
     * @return String valor de la clasificación
     */
    public Animal valueOf(Animal animal){
        if(animalValido(animal)){
            String ans = null;
            //Se prepara la información para que sea recibida en una DataSet
            ArrayList<AtributoCarne> at = animal.getCarne().getCalidades();

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
                        normalizacion( 6, at.get(0).getCalidad().getKey()), // Conformación de la canal
                        normalizacion( 7, at.get(1).getCalidad().getKey()), // Distribución de grasa subcutánea
                        normalizacion( 8, at.get(2).getCalidad().getKey()), // Cobertura de grasa peri renal
                        normalizacion( 9, at.get(3).getCalidad().getKey()), // Color de la grasa
                        normalizacion( 10, at.get(4).getCalidad().getKey()), // Color de la carne
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
            animal.getCarne().setCalidad(valorRespuesta(networkOutput));
        }
        
        return animal;
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
    
    /**************** CREAR ANIMAL *******************************/ 
        
    /**
     * Implementa la opción de obtener todos los animales de un conjunto de datos
     * @param data Set< double[] > Grupo de animales a evaluar
     * @return Set< Animal >
     */
    public Set<Animal> vectorToAnimalAll(Set<double[]> data){
        // verificar si existen los datos necesarios
        if (localidades == null){getTodosLocalidad();} 
        if (calidades == null){ getTodosCalidad();}         
        if (atributos == null){ getTodosAtributoCarne();}         
        if (sistemas == null){ getTodosSistema();}        
        if (gruposRaciales == null){ getTodosGrupoRacial();}         
        if (tipos == null){ getTodosTipo();}         
        
        // envia cada uno de los animales a modificar su valor de calidad en la carne
        Set<Animal> ans = new LinkedHashSet<>();
        int i = 1;
        for (double[] d: data){
            ans.add(vectorToAnimal(i++, d));
        }
        return ans;
    }
    
    /**
     * Crea una animal apartir de la información de un vector
     * @param id identificador del animal
     * @param d vector de propiedades del animal
     * @return Animal con las caracteristicas del vector
     */
    public Animal vectorToAnimal(int id,double[] d){
        ArrayList<AtributoCarne> ac = new ArrayList<>();
        for(int i = 0; i < atributos.size(); i++){
            ac.add(new AtributoCarne(atributos.get(i).getKey(), atributos.get(i).getDescripcion(), calidades.get((int)d[5+atributos.get(i).getKey()]-1)));
        }
        Carne carne = new Carne(d[11], d[12], d[13], d[14], ac, calidades.get((int)d[15]-1));
        Animal animal = new Animal(id, tipos.get((int)d[2]-1), (int)d[4], (int)d[5], localidades.get((int)d[0]-1), carne, gruposRaciales.get((int)d[1]-1), sistemas.get((int)d[3]-1));
        return animal;
    }
    
    /**************** GRAFICADOR *******************************/
    
    /**
     * Obtiene un objeto graficador para se usado por la capa de presentación
     * @return LineChartModel objeto listo para ser presentado
     */
    public LineChartModel getGraficador(){
        Graficador graficador = new Graficador();
        if(calidades == null){ getTodosCalidad();}
        return graficador.getLineChartModel(calidades);
    }

    private boolean animalValido(Animal animal) {
        ArrayList<AtributoCarne> at = animal.getCarne().getCalidades();
        Carne carne = animal.getCarne();
        return animal.getLocalidad().getKey() > 0 
                && animal.getGrupoRacial().getKey() > 0
                && animal.getTipo().getKey() > 0
                && animal.getSistema().getKey() > 0
                && animal.getEdad() > 0
                && animal.getKpv() > 0
                && at.get(0).getCalidad().getKey() > 0
                && at.get(1).getCalidad().getKey() > 0
                && at.get(2).getCalidad().getKey() > 0
                && at.get(3).getCalidad().getKey() > 0
                && at.get(4).getCalidad().getKey() > 0
                && carne.getPesoCanalFrioDer() > 0
                && carne.getPesoCanalFrioIzq() > 0
                && carne.getOjoDeLaChuleta() > 0
                && carne.getGrosorDeGrasaDorsal() > 0;
    }
    
     
}
