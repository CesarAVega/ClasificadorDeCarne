/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.Graficador;

import entities.Calidad;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import services.ServicesFacade;

/**
 * Crea una grafica apartir de la información enviada
 * @author Carlos Valencia Y Cesar Vega
 */
public class Graficador {
    
    private HashMap<String, ArrayList<double[]>> data;
    private double[] dataRow;    
    private LineChartSeries lineSeries;
    
    public Graficador(){
    }
    
    public LineChartModel getLineChartModel(ArrayList<Calidad> calidades) {
        LineChartModel model = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader("FinalDataTraining.in"));
            in.readLine(); //lectura porcentaje
            in.readLine(); //lectura de la información
            in.readLine(); // lectura de minimos
            in.readLine(); // lectura de maximos
            
            // lectura de los datos
            String[] tok;
            String key;
            data = new HashMap<>();
            int x, y; double d;
            while(in.ready()){
                tok = in.readLine().split(",");
                x = 0; y = 0;
                //key en String de la calidad
                key = calidades.get(Integer.parseInt(tok[tok.length-1])).getDescripcion();                
                for(int i = 0; i < tok.length-1; i++){
                    d = ServicesFacade.pdou(tok[i]);
                    if(i < 6){
                        x += (d*d);
                    }else{
                        y += (d*d);
                    }
                }
                //prepara los datos para la graficadores
                dataRow = new double[]{Math.sqrt(x), Math.sqrt(y)};
                
                //si la hash no tiene la key la crea
                if (!data.containsKey(key)){                    
                    data.put(key, new ArrayList<double[]>());
                }
                //adjunta la dataRow
                data.get(key).add(dataRow);
            }
            
            // preparando el lineChartModel
            model = new LineChartModel();
            for (String s: data.keySet()){
                lineSeries = new LineChartSeries();
                lineSeries.setLabel(s);
                for (double[] dou: data.get(s)){
                    lineSeries.set(dou[0], dou[1]);
                }
                
                model.addSeries(lineSeries);
            }            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }
   
}
