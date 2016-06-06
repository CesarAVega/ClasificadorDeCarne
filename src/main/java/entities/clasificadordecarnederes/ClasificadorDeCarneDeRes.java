/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.clasificadordecarnederes;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
 * Implementa las funciones básicas para el manejo de los archivos de configuración
 * @author Carlos Valencia Y Cesar Vega
 */
public class ClasificadorDeCarneDeRes {

    // Variables
    private static final int n = 16, inf = Integer.MAX_VALUE;
    private static double[] mins = new double[n], maxs = new double[n], diff = new double[n];
    
    /**
     * Lee los datos de un archivo y los separa según su tipo
     * @param file Archivo a leer los datos deben estar separados por un espacio
     * @return una Hash con los datos clasificados según su clase
     */
    public HashMap<String, ArrayList<double[]>> readData(File file) {
        //inicializar valores 
        Arrays.fill(mins, inf);
        Arrays.fill(maxs, 0);
        HashMap<String, ArrayList<double[]>> data = null;
        
        //obteniendo información del archivo
        try {
            data = read(file);
        } catch (Throwable ex) {
            Logger.getLogger(ClasificadorDeCarneDeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
        
    }
    
    /**
     * Muestra en consola la información en un HASH de datos
     * @param data HashMap<String, ArrayList<double[]>> 
     */
    public static void print(HashMap<String, ArrayList<double[]>> data){
        Set<String> set = data.keySet();
        Iterator<String> i = set.iterator();
        String key;
        while(i.hasNext()){
            key = i.next();
            System.out.println(key);
            for (double[] d : data.get(key)){
                System.out.println(Arrays.toString(d));
            }
        }
        
        System.out.println("Mínimos y máximos");
        System.out.println(Arrays.toString(mins));
        System.out.println(Arrays.toString(maxs));
        
    }
    
    /**
     * Halla mínimos y máximos de una posición
     * @param index Valor de la posición en el arregla de mínimos o máximos a comparar
     * @param x Valor entero a ser comparado con lo guardo en los arreglos mínimos y máximos
     */
    private static void minMax(int index, double x){
        mins[index] = (mins[index] == inf ? x : Math.min(mins[index], x));
        maxs[index] = (maxs[index] == 0 ? x : Math.max(maxs[index], x));
    }
    

    
    /**
     * Redondea a 5 dígitos
     * @param d Valor a redondear
     * @return Valor redondeado
     */    /**
     * Función de normalización de un dato
     * @param index Valor de la posición en el arregla de mínimos o máximos a comparar
     * @param a Valor entero a ser comparado con lo guardo en los arreglos mínimos y máximos
     * @return valor normalizado y redondeado a 5 dígitos
     */
    private static double normalizacion(int index, double a){
        return Math.rint(((a-mins[index])/diff[index])*100000)/100000;
    }
    private static double round(double d){
        return Math.rint(d*100000)/100000;
    }
    
    /**
     * Transforma de String a Double
     * @param s Valor a transforma a Double
     * @return String transformado en Double
     */
    public static double pdouConComa(String s){
        // Tratamiento para valores que están separados por una “,” ya que la conversión directa no lo reconoce como valido 
        String[] tok = s.split(",");
        StringBuilder stri = new StringBuilder();
        for(int i = 0; i < tok.length; i++){
            stri.append(tok[i]);
        }
        double num;
        if(tok.length == 2){
            num = Integer.parseInt(stri.toString())/(Math.pow(10, tok[1].toCharArray().length));
        }else{
            num = Double.parseDouble(s);
        }
            
        
        return num;
    }
    
    /**
     * Lectura del archivo y la clasifica según el número de clases
     * @param file archivo a leer
     * @return HashMap<String, ArrayList<double[]>> de valores según el archivo de configuración 
     * @throws Throwable 
     */
    private static HashMap<String, ArrayList<double[]>> read(File file) throws Throwable{
        // Establecer las variables
        BufferedReader in = new BufferedReader(new FileReader(file));
        HashMap<String, ArrayList<double[]>> data = new HashMap<>();
        String[] tok; double[] temp; String key; ArrayList<double[]> adou;
        double normal, num, suma;
        while (in.ready()){
            tok = in.readLine().split("\\s+");
            key = tok[n-1]; // posición que tiene la clase del dato (por configuración predeterminada se encuentra al final de cada fila)
            suma = 0;
            temp = new double[n];
            
            for (int i = 0; i < n-1; i++){
                
                // Trasformación de los datos 
                num = pdouConComa(tok[i]);
                // Suma para hallar más a delante la normal del vector               
                suma += (num*num);
                // Hallar el mínimo para esta posición
                minMax(i, num);
                // Lo adjuntamos al vector de resultados
                temp[i] = num;
            }
            
            normal = round(Math.sqrt(suma)); // halla la normal del vector para hacer una clasificación posteriormente
            minMax(n-1, normal);
            
            // Ingreso de datos al HASHMAP
            temp[n-1] = normal;                        
            
            if(data.containsKey(key)){
                data.get(key).add(temp);
            }else{
                adou = new ArrayList<>();
                adou.add(temp);
                data.put(key, adou);
            }
        }
        return data;
    }

    /**
     * Se normalizan los valores de una HASHMAP
     * @param data HashMap<String, ArrayList<double[]>> de datos
     * @return HashMap<String, ArrayList<double[]>>
     */
    public HashMap<String, ArrayList<double[]>> normalizarData(HashMap<String, ArrayList<double[]>> data) {
        HashMap<String, ArrayList<double[]>> dataNorm = new HashMap<>();
        ArrayList<double[]> adou; double[] temp;
        
        //Estableciendo las diferencias
        for (int i = 0; i < n; i++){
            diff[i] = maxs[i]-mins[i];
        }
        
        //lectura de los datos en la HASH
        Set<String> set = data.keySet();
        Iterator<String> i = set.iterator();
        String key;
        while(i.hasNext()){
            key = i.next();
            adou = new ArrayList<>();         
            for (double[] d : data.get(key)){
                temp = new double[n];
                for(int j = 0; j < n ; j++){
                    // Con cada uno de los arreglos dentro de la clase se normaliza los valores
                    temp[j] = normalizacion(j,d[j]);
                }
                
                adou.add(temp);
            }
            dataNorm.put(key, adou);
        }
        
        return dataNorm;
    }

    /**
     * Guarda en un archivo los datos seleccionados para el entrenamiento de la red y con los que se trabajara en dar la solución
     * @param data HashMap<String, ArrayList<double[]>>
     * @param por Integer que define el valor de cuantos datos de la muestra se seleccionaran
     */
    public void seleccionarDatos(HashMap<String, ArrayList<double[]>> data, int por) {        
        
        StringBuilder traing = new StringBuilder();
        StringBuilder solve = new StringBuilder();
        //lectura de los datos en la HASH
        Set<String> set = data.keySet();
        Iterator<String> i = set.iterator();
        String key;

        ArrayList<LinkedList<double[]>> datos;

        while(i.hasNext()){
            key = i.next();
            datos = new ArrayList<>(); // para data clase se hace una clasificación
            for (int j = 0; j < 5; j++){
                datos.add(new LinkedList<double[]>());
            }

            // Dividiendo la información por sectores de a 0.2 por cientos
            for (double[] d: data.get(key)){
                datos.get(selec(d[d.length-1])).add(d);
            }

            // Forma dinámica para hallar cuantos valores tiene cada clase
            int suma = 0;
            for (LinkedList<double[]> l: datos){
                suma += l.size();
            }

            int numeroDatos = (suma*por)/100; // número de datos que se tomaran dependiendo del porcentaje que se requiere

            LinkedList<double[]> temp; double[] d;
            int bandera = 0; int index = 0; int random;
            
            while(bandera < numeroDatos){ // se selecciona hasta que el número de datos según el porcentaje
                temp = datos.get(index);
                if (!temp.isEmpty()){
                    // Selección del índice dependiendo del número de elementos que contiene el sector a analizar
                    random = (int)((Math.random() * Math.pow(10D, contarDigitos(temp.size())))%temp.size());
                    d = temp.get(random);
                    // Agregando a un string los datos seleccionados para el entrenamiento
                    for(int j = 0 ; j < n-1; j++){
                        traing.append(d[j]).append(",");
                    }
                    
                    traing.append(key).append("\n");
                    
                    temp.remove((int)random); // se remueve para evitar repeticiones
                    bandera++; // Aumenta el índice de salida
                }
                
                // Se restablece el índice
                if (index+1 == datos.size()){
                    index = 0;
                }else{
                    index++;
                }
            }
            
            // Los datos sobrantes se toman para analizar la solución
            for(LinkedList<double[]> l: datos){
                for (double[] dou : l){
                    // Agregando a un string los datos seleccionados para el entrenamiento
                    for(int j = 0 ; j < n-1; j++){
                        solve.append(dou[j]).append(",");
                    }
                    
                    solve.append(key).append("\n");
                }
            }

        }
            
        // Creando los archivos para el entrenamiento y la solución
        try {
            // Ingresar en un archivo los datos seleccionados
            try (PrintWriter out = new PrintWriter(new FileOutputStream("dataTraining.in"))) {
                out.append(traing);
            }
            try (PrintWriter out = new PrintWriter(new FileOutputStream("dataSolve.in"))) {
                out.append(solve);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClasificadorDeCarneDeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función de apoyo, clasifica el dato según su valor normalizado
     * @param d Valor a clasificar
     * @return Integer con el valor a de índice al que corresponde
     */
    private static int selec(double d) {
        int ans = 0;
        if(0.2D <= d && d < 0.4){
            ans = 1;
        }else if(0.4D <= d && d < 0.6){
            ans = 2;
        }else if(0.6D <= d && d < 0.8){
            ans = 3;
        }if(0.8D <= d && d <= 1 ){
            ans = 4;
        }
        return ans;
    }
    
    /**
     * Cuenta los dígitos que tiene un numero
     * @param num Numero a contar para contar los dígitos
     * @return El número de dígitos que tiene el número
     */
    private static int contarDigitos(int num){
        int ans = 0;
        while(num != 0){
            num /= 10;
            ans++;
        }
        return ans;
    }
    
    /**
     * Convierte un archivo en un DataSet según especificaciones del problema, como clases y numero de datos de forma estática
     * @param file String correspondiente al nombre del archivo donde se encuentra la información de entrada
     * @param x Número de entradas del DataSet
     * @param y Número de salidas del DataSet
     * @param fileOutput String correspondiente al nombre del archivo donde se encuentra la información de las salidas, este archivo esta predeterminado 
     * @return DataSet Correspondiente a la información del archivo de entrada  
     */
    public DataSet fileTODataSet(String file, int x, int y, String fileOutput){
        DataSet data;
        data = new DataSet(x, y);
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileOutput));
            String[] tok; double[] temp, tempNum;
            double num;
            //Extrae los datos en el archivo de salidas los archivos de configuración están con 4 y 2 neuronas de salida
            ArrayList<double[]> OutputData = new ArrayList<>();                       
            while(in.ready()){
                tok = in.readLine().split("\\s+");
                temp = new double[tok.length];
                for(int i = 0; i < tok.length; i++){
                    temp[i] = pdouConComa(tok[i]);
                }   
                OutputData.add(temp);
            }
            in.close();
            
            //Lectura del archivo previamente guardado puede ser DataTraining o DataSolve
            in = new BufferedReader(new FileReader(file));            
            while (in.ready()){
                tok = in.readLine().split(",");
                
                // selecciona la salida para esta clase lectura del archivo de configuración previa                
                temp = OutputData.get(Integer.parseInt(tok[n-1])-1).clone(); 
                
                tempNum = new double[n-1];
                for (int i = 0; i < n-1; i++){
                    //trasformación de los datos 
                    num = pdouConComa(tok[i]);
                    //lo adjuntamos al vector de resultados
                    tempNum[i] = num;
                }
                
                data.addRow(new DataSetRow(tempNum, temp));                
            }
            in.close();            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClasificadorDeCarneDeRes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClasificadorDeCarneDeRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    
    
}
