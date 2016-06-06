/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.controller;

import entities.*;
import java.io.Serializable;
import java.util.*;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.LineChartModel;
import services.ServicesFacade;
import javax.servlet.annotation.MultipartConfig;

/**
 *
 * @author Cesar Augusto Vega Fernández
 */
@ManagedBean(name = "beanAdminRedNeuronal")
@SessionScoped
@MultipartConfig
public class AdminRedNeuronalBackingBean implements Serializable{
    
    private static final ServicesFacade sp = ServicesFacade.getInstance("applicationconfig.properties");
    
    private final RedNeuronal nnet = sp.getNnet();

    public RedNeuronal getNnet() {
        return nnet;
    }        
    
    private LineChartModel lineModel = sp.getGraficador();

    public LineChartModel getLineModel() {
        
        return lineModel;    
    }
    
    private Set<Animal> data = sp.vectorToAnimalAll(nnet.getData());

    public Set<Animal> getTable() {
        return data;
    }

    private ArrayList<Localidad> localidades;
    private ArrayList<GrupoRacial> grupoRaciales;
    private ArrayList<Calidad> calidades;
    private ArrayList<Tipo> tipos;
    private ArrayList<Sistema> sistemas;

    public ArrayList<Localidad> getLocalidades() {
        return sp.getTodosLocalidad();
    }

    public ArrayList<GrupoRacial> getGrupoRaciales() {
        return sp.getTodosGrupoRacial();
    }

    public ArrayList<Calidad> getCalidades() {
        return sp.getTodosCalidad();
    }

    public ArrayList<Tipo> getTipos() {
        return sp.getTodosTipo();
    }

    public ArrayList<Sistema> getSistemas() {
        return sp.getTodosSistema();
    }
    
    private int id;
    private String descripcion;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void insertLocalidad(){
        if(descripcion != null){
            id = localidades.get(localidades.size()-1).getKey()+1;
            Localidad localidad = new Localidad(id, descripcion);
            sp.insertLocalidad(localidad);
            localidades = sp.getTodosLocalidad();
            descripcion = null;
        }
        
    }
    
    public void insertGrupoRacial(){
        if(descripcion != null){
            id = grupoRaciales.get(grupoRaciales.size()-1).getKey()+1;
            GrupoRacial grupoRacial = new GrupoRacial(id, descripcion);
            sp.insertGrupoRacial(grupoRacial);
            grupoRaciales = sp.getTodosGrupoRacial();
            descripcion = null;
        }
        
    }
    
    public void insertTipo(){
        if(descripcion != null){
            id = tipos.get(tipos.size()-1).getKey()+1;
            Tipo tipo = new Tipo(id, descripcion);
            sp.insertTipo(tipo);
            tipos = sp.getTodosTipo();
            descripcion = null;
        }
        
    }
    
    public void insertSistema(){
        if(descripcion != null){
            id = sistemas.get(sistemas.size()-1).getKey()+1;
            Sistema sistema = new Sistema(id, descripcion);
            sp.insertSistema(sistema);
            sistemas = sp.getTodosSistema();
            descripcion = null;
        }
    }
    
    public void insertCalidad(){
        if(descripcion != null){
            calidades = getCalidades();
            id = calidades.get(calidades.size()-1).getKey()+1;
            Calidad calidad = new Calidad(id, descripcion);            
            sp.insertCalidad(calidad);           
            calidades = sp.getTodosCalidad();            
            descripcion = null;            
        }        
    }
    
}
