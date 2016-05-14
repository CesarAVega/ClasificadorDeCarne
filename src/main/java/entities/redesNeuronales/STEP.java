/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.redesNeuronales;;

import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

/**
 * Inicializa la Red Neuronal de la función STEP
 * @author Carlos Valencia y Cesar Vega
 */
public class STEP extends RedesNeuronales{
    
    @Override
    public void iniciar(int x, int num, int y){
        System.out.println("===========================STEP=======================================");
        //crear el perceptron multicapa
        MLP = new MultiLayerPerceptron(TransferFunctionType.STEP, x, num, y);
        tipo = "TransferFunctionType.STEP";
    }
    
}