/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.Graficador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
 * Crea una grafica apartir de la información enviada
 * @author Carlos Valencia Y Cesar Vega
 */
public class Graficador {
    
    private static JFrame ventana;
    private static double[] temp;
    private static HashMap<String, ArrayList<ArrayList<Double>>> dataHash = new HashMap<>();
    private static DefaultXYDataset data = new DefaultXYDataset();
    private static ArrayList<Double> aDX, aDY;
    private static ArrayList<ArrayList<Double>> aDXY;
    private static String s;
    private static double x, y;
    
    public Graficador(){
    } 
    
    private double pdou(String s){return Double.parseDouble(s);}
    /**
     * Metodo que empieza a mostrar los valores que se van a graficar
     * @param cadena    Los numeros que se decean graficar      
     * @param fileOutput      
     */
    public void empezar(DataSet cadena, String fileOutput) {
        
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileOutput));
            String[] tok;
            //Extrae los datos en el archivo de salidas los archivos de configuración están con 4 y 2 neuronas de salida
            ArrayList<double[]> OutputData = new ArrayList<>();                       
            while(in.ready()){
                tok = in.readLine().split("\\s+");
                temp = new double[tok.length];
                for(int i = 0; i < tok.length; i++){
                    temp[i] = pdou(tok[i]);
                }   
                OutputData.add(temp);
            }
            in.close();
            
            List<DataSetRow> t = cadena.getRows();
            for(DataSetRow d : t) {
                s = Arrays.toString(d.getDesiredOutput());
                temp = d.getInput();
                for (int i = 0; i < temp.length; i++){
                    if(i < 6){
                        x += (temp[i]*temp[i]);
                    }else{
                        y += (temp[i]*temp[i]);
                    }

                }
                x = Math.sqrt(x);
                y = Math.sqrt(y);
                if (dataHash.containsKey(s)){
                    dataHash.get(s).get(0).add(Math.sqrt(x));
                    dataHash.get(s).get(1).add(Math.sqrt(y));
                }else{
                    aDX = new ArrayList<>();
                    aDY = new ArrayList<>();
                    aDXY = new ArrayList<>();
                    aDX.add(x);
                    aDY.add(y);
                    aDXY.add(aDX);
                    aDXY.add(aDY);
                    dataHash.put(s, aDXY); 
                }
            }
            
            Iterator iter = dataHash.keySet().iterator();
            ArrayList<ArrayList<Double>> iterNext;
            double[][] dataDouble;
            while (iter.hasNext()) {
                s = iter.next().toString();
                iterNext = dataHash.get(s);
                dataDouble = new double[iterNext.size()][iterNext.get(0).size()];
                for (int i = 0; i < iterNext.size(); i++){
                    for (int j = 0; j < iterNext.get(0).size(); j++){
                        dataDouble[i][j] = iterNext.get(i).get(j);
                    }
                }
                for (int i = 0; i < 2; i++){
                    Arrays.sort(dataDouble[i]);
                }
                //se modifica el tipo no mas para que quede de forma dinamica
                data.addSeries(s, dataDouble);
            }   //Crea la grafica de numeros a graficar
            JFreeChart grafica = ChartFactory.createXYLineChart("Datos de Entrenamiento", "Animal", "Carne", data, PlotOrientation.VERTICAL, true, true, false);
            ChartPanel contenedor = new ChartPanel(grafica);
            ventana  = new JFrame("Grafica en java");
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventana.add(contenedor);
            ventana.setSize(300,200);
            ventana.setVisible(true);
            ventana.setLocationRelativeTo(null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
