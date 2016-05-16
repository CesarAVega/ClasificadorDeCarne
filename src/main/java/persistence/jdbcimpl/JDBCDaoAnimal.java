/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jdbcimpl;

import entities.*;
import java.sql.*;
import java.util.*;
import persistence.*;
import services.ServicesFacade;

/**
 * Crea los objetos relacionados con el animal implementando JDBC
 * @author Carlos Valencia Y Cesar Vega
 */
public class JDBCDaoAnimal implements DaoAnimal{
    Connection con;

    public JDBCDaoAnimal(Connection con) {
        this.con = con;
    }

    @Override
    public Set<Animal> getAnimals() throws PersistenceException {
        try{
            PreparedStatement ps;
            Set<Animal> ans = new LinkedHashSet<>();
            Animal animal;
            Localidad localidad;
            GrupoRacial grupoRacial;
            Sistema sistema;
            Tipo tipo;
            Carne carne;
            
            //Consulta de las reservas que existen en la semana ingresada como parámetro 
            ps = con.prepareStatement("SELECT id, edad, kpv, tipo_id, localidad_id, carne_id, grupo_racial_id, sistema_id "
                    + "FROM animal");
            ResultSet rs = ps.executeQuery(); // conjunto de soluciones
            while (rs.next()){
                /**Valores que retorna la consulta
                 * id --> 1 INT
                 * edad --> 2 INT
                 * kpv --> 3 INT
                 * tipo_id --> 4 INT
                 * localidad_id --> 5 INT
                 * carne_id --> 6 INT
                 * grupo_racial_id --> 7 INT
                 * sistem_id --> 8 INT
                 */

                // obtiene los objetos necesarios para crear un animal
                localidad = getLocalidad(rs.getInt(5));
                grupoRacial = getGrupoRacial(rs.getInt(7));
                sistema = getSistema(rs.getInt(8));
                tipo = getTipo(rs.getInt(4));
                carne = getCarne(rs.getInt(6));
                // crea un animal
                animal = new Animal(rs.getInt(1), tipo, rs.getInt(2), rs.getInt(3), localidad, carne, grupoRacial, sistema);
                // Adiciona el animal al conjunto de solución
                ans.add(animal);
            }
            return ans;
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading the animals.",ex);
        }
    }

    @Override
    public Localidad getLocalidad(int ID) throws PersistenceException {
        try{
            PreparedStatement ps;            
            Localidad localidad = null;
            // prepara la consulta
            ps = con.prepareStatement("SELECT nombre "
                    + "FROM localidad "
                    + "WHERE id = ?");
            ps.setInt(1, ID); // modifica el valor a ser consultado
            ResultSet rs = ps.executeQuery(); // Conjunto de soluciones
            while (rs.next()){
                /**Valores que retorna la consulta
                 * nombre --> 1 STRING
                 */
                // crea una localidad a partir de la información anterior
                localidad = new Localidad(ID, rs.getString(1));
            }
            return localidad;
                    
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Localidad'.",ex);
        }
    }

    @Override
    public GrupoRacial getGrupoRacial(int ID) throws PersistenceException {
        try{
            PreparedStatement ps;            
            GrupoRacial grupoRacial = null;
            // Prepara la consulta
            ps = con.prepareStatement("SELECT nombre "
                    + "FROM grupo_racial "
                    + "WHERE id = ?");
            ps.setInt(1, ID); // Modifica el valor a ser consultado
            ResultSet rs = ps.executeQuery(); // Conjunto de soluciones
            while (rs.next()){
                /**Valores que retorna la consulta
                 * nombre --> 1 STRING
                 */
                // Crea un grupo racial a partir de la información anterior
                grupoRacial = new GrupoRacial(ID, rs.getString(1));
            }
            return grupoRacial;
                    
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'GrupoRacial'.",ex);
        }
    }

    @Override
    public Sistema getSistema(int ID) throws PersistenceException {
        try{
            PreparedStatement ps;            
            Sistema sistema = null;
            // Prepara la consulta
            ps = con.prepareStatement("SELECT nombre "
                    + "FROM sistema "
                    + "WHERE id = ?");
            ps.setInt(1, ID); // Modifica el valor a consultar
            ResultSet rs = ps.executeQuery(); // conjunto de soluciones 
            while (rs.next()){
                /**Valores que retorna la consulta
                 * nombre --> 1 STRING
                 */
                //crea un Sistema en base a la información anterior
                sistema = new Sistema(ID, rs.getString(1));
            }
            return sistema;
                    
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Sistema'.",ex);
        }
    }

    @Override
    public Tipo getTipo(int ID) throws PersistenceException {
        try{
            PreparedStatement ps;            
            Tipo tipo = null;
            // prepara la consulta
            ps = con.prepareStatement("SELECT nombre "
                    + "FROM tipo "
                    + "WHERE id = ?");
            ps.setInt(1, ID); // modifica el valor a consultar
            ResultSet rs = ps.executeQuery(); // conjunto de soluciones
            while (rs.next()){
                /**Valores que retorna la consulta
                 * nombre --> 1 STRING
                 */
                //crear el tipo en base a la información anterior
                tipo = new Tipo(ID, rs.getString(1));
            }
            return tipo;
                    
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Tipo'.",ex);
        }
    }

    @Override
    public Carne getCarne(int ID) throws PersistenceException {
        try{
            PreparedStatement ps ;
            Carne carne = null;
            Calidad calidad;
            ArrayList<AtributoCarne> calidades = null;
            //consulta en la base de datos
            ps = con.prepareStatement("SELECT canal_frio_der, canal_frio_izq , ojo_chuleta, grosor_grasa_dorsal, calidad_id "
                    + "FROM carne " 
                    + "WHERE id = ?");
            ps.setInt(1, ID); // modifica el valor a buscar
            // Conjunto de respuestas
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                /**Valores que retorna la consulta
                 * canal_frio_der --> 1 STRING
                 * canal_frio_izq --> 2 STRING
                 * ojo_chuleta --> 3 STRING
                 * grosor_grasa_dorsal --> 4 STRING
                 * calidad_id --> 5 INT
                 */
                
                //obtiene la Calidad
                calidad = getCalidad(rs.getInt(5));
                
                //obtiene los atributos que tiene otras calidades
                calidades = getAtributo(ID);
                
                //crea una carne en base a la información anterior
                carne = new Carne(ServicesFacade.pdou(rs.getString(1)), ServicesFacade.pdou(rs.getString(2)), 
                        ServicesFacade.pdou(rs.getString(3)), ServicesFacade.pdou(rs.getString(4)), calidades, calidad);
            }
            
            return carne;
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Carne'.",ex);
        }
    }

    @Override
    public ArrayList<AtributoCarne> getAtributo(int ID) throws PersistenceException {
        try{
            PreparedStatement ps ;
            AtributoCarne[] atributos = new AtributoCarne[5];                              
            int key;
            Calidad calidad;
            // prepara la consulta
            ps = con.prepareStatement("SELECT  cac.calidad_id, cac.atributos_carne_id, ac.descripcion "
                    + "FROM calidad_atributos_carne AS cac, atributos_carne AS ac "
                    + "WHERE cac.carne_id = ? AND cac.atributos_carne_id = ac.id");
            ps.setInt(1, ID); //modifica el valor a consultar
            ResultSet rs = ps.executeQuery(); // conjunto de soluciones
            while (rs.next()){
                /**Valores que retorna la consulta
                 * cac.calidad_id --> 1 INT
                 * cac.atributos_carne_id --> 2 INT
                 * ac.descripcion --> 3 STRING
                 */
                //obtiene la calidad
                calidad = getCalidad(rs.getInt(1));
                //crea el atributo a partir de la información anterior
                key = rs.getInt(2);                
                atributos[key-1] = new AtributoCarne(key, rs.getString(3), calidad);
            }
            ArrayList<AtributoCarne> temp = new ArrayList<>();
            temp.addAll(Arrays.asList(atributos));
            return temp;
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'AtributoCarne'.",ex);
        }
    }

    @Override
    public Calidad getCalidad(int ID) throws PersistenceException {
        try{
            PreparedStatement ps ;
            Calidad calidad = null;
            
            ps = con.prepareStatement("SELECT descripcion "
                    + "FROM calidad "
                    + "WHERE id = ?");
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                /**Valores que retorna la consulta
                 * nombre --> 1 STRING
                 */
                calidad = new Calidad(ID, rs.getString(1));
            }
            return calidad;
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Calidad'.",ex);
        }
    }

    @Override
    public String getDescripcioAtributo(int ID) throws PersistenceException {
        try{
            PreparedStatement ps ;
            String string = null;
            
            ps = con.prepareStatement("SELECT descripcion "
                    + "FROM  atributos_carne "
                    + "WHERE id = ?");
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                /**Valores que retorna la consulta
                 * descripcion --> 1 STRING
                 */
                string = rs.getString(1);
            }
            return string;
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Calidad'.",ex);
        }
    }

    @Override
    public ArrayList<Localidad> getTodosLocalidad() throws PersistenceException {
        try{
            PreparedStatement ps;            
            ArrayList<Localidad> localidades = new ArrayList<>();
            // prepara la consulta
            ps = con.prepareStatement("SELECT id, nombre "
                    + "FROM localidad ");
            ResultSet rs = ps.executeQuery(); // Conjunto de soluciones
            while (rs.next()){
                /**Valores que retorna la consulta
                 * id --> 1 INT
                 * nombre --> 2 STRING
                 */
                // crea una localidad a partir de la información anterior
                localidades.add(new Localidad(rs.getInt(1), rs.getString(2)));
            }
            return localidades;
                    
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Todas las Localidades'.",ex);
        }
    }

    @Override
    public ArrayList<GrupoRacial> getTodosGrupoRacial() throws PersistenceException {
        try{
            PreparedStatement ps;            
            ArrayList<GrupoRacial> gruposRaciales = new ArrayList<>();
            // Prepara la consulta
            ps = con.prepareStatement("SELECT id, nombre "
                    + "FROM grupo_racial ");            
            ResultSet rs = ps.executeQuery(); // Conjunto de soluciones
            while (rs.next()){
                /**Valores que retorna la consulta
                 * id --> 1 INT
                 * nombre --> 2 STRING
                 */
                // Crea un grupo racial a partir de la información anterior
                gruposRaciales.add(new GrupoRacial(rs.getInt(1), rs.getString(2)));
            }
            return gruposRaciales;
                    
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Todos los Grupos Raciales'.",ex);
        }
    }

    @Override
    public ArrayList<Sistema> getTodosSistema() throws PersistenceException {
        try{
            PreparedStatement ps;            
            ArrayList<Sistema> sistemas = new ArrayList<>();
            // Prepara la consulta
            ps = con.prepareStatement("SELECT id, nombre "
                    + "FROM sistema ");            
            ResultSet rs = ps.executeQuery(); // conjunto de soluciones 
            while (rs.next()){
                /**Valores que retorna la consulta
                 * id --> 1 INT
                 * nombre --> 2 STRING
                 */
                //crea un Sistema en base a la información anterior
                sistemas.add(new Sistema(rs.getInt(1), rs.getString(2)));
            }
            return sistemas;
                    
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Todos los Sistemas'.",ex);
        }
    }

    @Override
    public ArrayList<Tipo> getTodosTipo() throws PersistenceException {
        try{
            PreparedStatement ps;            
            ArrayList<Tipo> tipos = new ArrayList<>();
            // prepara la consulta
            ps = con.prepareStatement("SELECT id, nombre "
                    + "FROM tipo ");            
            ResultSet rs = ps.executeQuery(); // conjunto de soluciones
            while (rs.next()){
                /**Valores que retorna la consulta
                 * id --> 1 INT
                 * nombre --> 2 STRING
                 */
                //crear el tipo en base a la información anterior
                tipos.add(new Tipo(rs.getInt(1), rs.getString(2)));
            }
            return tipos;
                    
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Todos los Tipos'.",ex);
        }
    }

    @Override
    public ArrayList<AtributoCarne> getTodosDescripcioAtributo() throws PersistenceException {
        try{
            PreparedStatement ps ;
            ArrayList<AtributoCarne> atributosCarne = new ArrayList<>();
            Calidad calidad = null;
            ps = con.prepareStatement("SELECT id, descripcion "
                    + "FROM  atributos_carne ");            
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                /**Valores que retorna la consulta
                 * id --> 1 INT
                 * descripcion --> 2 STRING
                 */
                atributosCarne.add(new AtributoCarne(rs.getInt(1), rs.getString(2), calidad));
            }
            return atributosCarne;
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Todos los atributos adicionales de la carne'.",ex);
        }
    }

    @Override
    public ArrayList<Calidad> getTodosCalidad() throws PersistenceException {
        try{
            PreparedStatement ps ;
            ArrayList<Calidad> calidades = new ArrayList<>();
            
            ps = con.prepareStatement("SELECT id, descripcion "
                    + "FROM calidad ");            
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                /**Valores que retorna la consulta
                 * id --> 1 INT
                 * nombre --> 2 STRING
                 */
                calidades.add(new Calidad(rs.getInt(1), rs.getString(2)));
            }
            return calidades;
        }catch(SQLException ex){
            throw new PersistenceException("An error ocurred while loading 'Todas las Calidades'.",ex);
        }
    }
    
}
