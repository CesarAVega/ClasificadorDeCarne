/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.controller;

import entities.RedNeuronal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;
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
        lineModel.setAnimate(true);
        return lineModel;    
    }
    
    private Set<double[]> data = nnet.getData();

    public Set<double[]> getTable() {
        return data;
    }
    
    /**
    public void graficar(){
        Set<double[]> data = nnet.getData();
        //Table
        table = new ArrayList<>();
        String[] temp = new String[]{      
        };
        table.add(temp);        
        int i = 1;
        for (double[] d : data){
            temp = new String[d.length+1];
            temp[0] = (i++)+"";
            for (int j = 0; j < d.length; j++){
                temp[j+1] = d[j]+"";
            }
            System.out.println(Arrays.toString(temp));
            table.add(temp);
        }              
    }
    */
}
