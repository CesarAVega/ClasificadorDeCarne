/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.redesNeuronales;;

import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

/**
 * Inicializa la Red Neuronal de la función SIN
 * @author Carlos Valencia y Cesar Vega
 */
public class SIN extends RedesNeuronales{
    
    @Override
    public void iniciar(int x, int num, int y){
        System.out.println("===========================SIN=======================================");
        //crear el perceptron multicapa
        MLP = new MultiLayerPerceptron(TransferFunctionType.SIN, x, num, y);
        tipo = "TransferFunctionType.SIN";
    }
    
}