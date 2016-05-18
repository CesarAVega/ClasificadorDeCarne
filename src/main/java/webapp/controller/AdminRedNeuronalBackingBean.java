/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.controller;

import entities.Animal;
import entities.Calidad;
import entities.RedNeuronal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.LineChartModel;
import services.ServicesFacade;



/**
 *
 * @author Cesar Augusto Vega Fernández
 */
@ManagedBean(name = "beanAdminRedNeuronal")
@SessionScoped
public class AdminRedNeuronalBackingBean implements Serializable{
    
    private static final RedNeuronal nnet = ServicesFacade.getInstance("applicationconfig.properties").getNnet();

    public RedNeuronal getNnet() {
        return nnet;
    }        
    
    private LineChartModel lineModel = ServicesFacade.getInstance("applicationconfig.properties").getGraficador();

    public LineChartModel getLineModel() {
        
        return lineModel;    
    }
    
    private Set<Animal> data = ServicesFacade.getInstance("applicationconfig.properties").vectorToAnimalAll(nnet.getData());

    public Set<Animal> getTable() {
        return data;
    }

    
}
