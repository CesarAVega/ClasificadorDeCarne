/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neuroph.core.NeuralNetwork;
import services.ServicesFacade;
import static services.ServicesFacade.pdou;

/**
 * Contiene la información de la red neuronal base, es la red que en el entrenamiento es la de mayor efecitividad
 * @author Cesar Augusto Vega Fernández
 */
public class RedNeuronal {
    
    private double porcentajeEfectividad;
    private String TransferFunctionType;
    private int input, output, k, numDatosUsados;
    private final NeuralNetwork nnet = NeuralNetwork.createFromFile("myMlPerceptron.nnet");
    private final File file = new File("FinalDataTraining.in");
    private double[] mins, maxs, diff;
    private Set<double[]> data;

    /**
     * Porcentaje de efecitividad de la Red Neuronal Base
     * @return double
     */
    public double getPorcentajeEfectividad() {
        return porcentajeEfectividad;
    }

    /**
     * Función de transferencia con la que fue entrenada la Red Neuronal Base
     * @return String
     */
    public String getTransferFunctionType() {
        return TransferFunctionType;
    }

    /**
     * Número de entradas que tiene la Red Neuronal Base
     * @return int input
     */
    public int getInput() {
        return input;
    }

    /**
     * Número de salidas de la Red Neuronal Base
     * @return int output
     */
    public int getOutput() {
        return output;
    }

    /**
     * Número de neuronas en la capa oculta
     * @return int Número de neuronas
     */
    public int getK() {
        return k;
    }

    /**
     * Porcentaje de datos usados al ser entrenada la Red Neuronal Base
     * @return int porcentaje de datos usados
     */
    public int getNumDatosUsados() {
        return numDatosUsados;
    }

    /**
     * Red Neuronal Base
     * @return NeuralNetwork red Almacenada entrenada previamente
     */
    public NeuralNetwork getNnet() {
        return nnet;
    }

    /**
     * Nombre del archivo en el que se encuentra la Red Neuronal
     * @return File nombre del archivo
     */
    public File getFile() {
        return file;
    }

    /**
     * Vector de números minimos de los datos con los que se entreno la Red Neuronal
     * @return double[] igual al número de entradas de la red neuronal
     */
    public double[] getMins() {
        return mins;
    }

    /**
     * Vector de números maximos de los datos con los que se entreno la Red Neuronal
     * @return double[] igual al número de entradas de la red neuronal
     */
    public double[] getMaxs() {
        return maxs;
    }

    /**
     * Vector de diferencias entre maximos y minimos de los datos con los que se entreno la Red Neuronal
     * @return double[] igual al número de entradas de la red neuronal
     */
    public double[] getDiff() {
        return diff;
    }
    
    /**
     * Datos con los que fue entrenada la Red Neuronal
     * @return Set double[] 
     */
    public Set<double[]> getData() {
        return data;
    }
            
    
    /**
     * Funcion de apoyo String a integer
     * @param s String a convertir
     * @return int 
     */
    private int pint(String s){
        return Integer.parseInt(s);
    }
    
    /**
     * Carga la informacion de la red neuronal base, o la red ya entrenada
     */
    public RedNeuronal(){
        try {
            //Porcentaje de eficiencia de la red Neuronal
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                //Porcentaje de eficiencia de la red Neuronal
                porcentajeEfectividad = pdou(in.readLine());
                
                String[] tok = in.readLine().split("\\s+");
                
                // Datos de la red neuronal con la que fue entrenada la red Base
                TransferFunctionType = tok[0];
                input = pint(tok[1]);
                output = pint(tok[2]);
                k = pint(tok[3]);
                numDatosUsados = pint(tok[4]);
                
                // Obtiene los minimos de los datos con los que fue entrenada la red neuronal
                tok = in.readLine().split(",");
                
                mins = new double[input+1];
                for (int i =  0; i < input+1; i++){
                    mins[i] = ServicesFacade.pdou(tok[i]);
                }
                
                // Obtiene los maximos de los datos con los que fue entrenada la red neuronal
                tok = in.readLine().split(",");
                maxs = new double[input+1];
                for (int i =  0; i < input+1; i++){
                    maxs[i] = ServicesFacade.pdou(tok[i]);
                }
                //Calcula la diferencia entre los minimos y maximos
                diff = new double[input+1];
                for (int i = 0; i < input+1; i++){
                    diff[i] = maxs[i]-mins[i];
                }
                data = new LinkedHashSet<>();
                // Almacena los datos con los que la red fue entrenada                
                double[] temp;
                Animal animal;
                int j = 1;
                while(in.ready()){
                    tok = in.readLine().split(",");                     
                    temp = new double[tok.length];
                    for (int i = 0; i < tok.length-1; i++){
                        temp[i] = desNormalizar(i, ServicesFacade.pdou(tok[i]));
                    }                 
                    temp[tok.length-1] = Integer.parseInt(tok[tok.length-1]);                   
                    data.add(temp);
                }
                
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
        /**
     * Función de normalización de un dato
     * @param index Valor de la posición en el arregla de mínimos o máximos a comparar
     * @param a Valor entero a ser comparado con lo guardo en los arreglos mínimos y máximos
     * @return valor normalizado y redondeado a 5 dígitos
     */
    private double desNormalizar(int index, double y){
        return Math.rint(((y*diff[index])+mins[index])*1000)/1000;
    }
}
