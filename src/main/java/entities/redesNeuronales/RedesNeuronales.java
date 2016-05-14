/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.redesNeuronales;;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;

/**
 * Implementa las funciones básicas para cualquier red Neuronal
 * @author Carlos Valencia Y Cesar Vega
 */
public abstract class RedesNeuronales {
    
    private static DecimalFormat douf = new DecimalFormat("0.000");
    static MultiLayerPerceptron MLP;
    double porcentaje;
    String tipo;
    
    /**
     * Inicial Una red Neuronal del tipo seleccionado
     * @param x int Número de neuronas de entrada
     * @param num int Número de neuronas en la capa oculta
     * @param y int Número de neuronas en la capa de salida
     */
    public void iniciar(int x, int num, int y){}
    
    /**
     * Función de Aprendizaje para la red neuronal
     * @param trainingSet Archivo de entrenamiento 
     */
    public void learn(DataSet trainingSet){
        MLP.learn(trainingSet);
        System.out.println("Red Entrenada");
    }
    
    /**
     * @param testSet Conjunto de datos a clasificar/probar
     */
    public void testNeuralNetwork(DataSet testSet) {
        System.out.println("Comprobando Red Neuronal");
        double[] networkOutput; int[] networkOutputInt;
        double cont = 0; int[] testSetOutInt;
        for (DataSetRow dataRow : testSet.getRows()) {
            MLP.setInput(dataRow.getInput());
            MLP.calculate();
            networkOutput = MLP.getOutput();
            
            //MODIFICAR
            networkOutputInt = new int[networkOutput.length];
            testSetOutInt = new int [networkOutput.length];
            for (int i = 0; i < networkOutput.length; i++){
                networkOutputInt[i] = (int)Math.round(networkOutput[i]);
                testSetOutInt[i] = (int)dataRow.getDesiredOutput()[i];
            }           
            
            //System.out.println(Arrays.toString(networkOutputInt)+""+Arrays.toString(testSetOutInt));
            if(Arrays.toString(networkOutputInt).equals(Arrays.toString(testSetOutInt))){
                cont++;            
            }
        }
        System.out.println("La red neuronal resolvió el problema un: "+(douf.format((cont*100)/testSet.size()))+"%");
        this.porcentaje = Math.rint(((cont*100)/testSet.size())*10000)/10000;
    }
    
    /**
     * Guarda la red neuronal, una de las condiciones es si el valor de porcentaje correcto de la red es superior al que se encuentra guardado
     */
    public void guardar(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("FinalDataTraining.in"));
            if(Double.parseDouble(in.readLine()) < this.porcentaje){
                in.close();
                //guardarText(new BufferedReader(new FileReader("dataTraining.in")));
            }
        } catch (FileNotFoundException ex) {
            /**
            try {
                //guardarText(new BufferedReader(new FileReader("dataTraining.in")));
            } catch (IOException ex1) {
                Logger.getLogger(RedesNeuronales.class.getName()).log(Level.SEVERE, null, ex1);
            }
            * */
        } catch (IOException ex) {
            File archivo = new File("FinalDataTraining.in");
            archivo.delete();
            //guardar();
        }
        
    }
    
    
    /**
     * Guarda la red en un archivo
     * @param in BufferedReader a leer
     * @throws IOException 
     */
    /**
    private void guardarText(BufferedReader in) throws IOException{
        StringBuilder s;
        s = new StringBuilder();
        s.append(this.porcentaje).append("\n");
        s.append(this.tipo).append("\t");
        s.append("in= ").append(main.input).append("\t");
        s.append("k= ").append(main.k).append("\t");
        s.append("out= ").append(main.output).append("\t");
        s.append("porcentajeSelec= ").append(main.porcentaje).append("\n");
        while(in.ready()){
            s.append(in.readLine()).append("\n");
        }              
        File archivo = new File("FinalDataTraining.in");
        archivo.delete();
        try (PrintWriter out = new PrintWriter(new FileWriter("FinalDataTraining.in"))) {
            out.append(s);
        }
        MLP.save("myMlPerceptron.nnet");
    }
    * */
    
}
