/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.controller;

import entities.Animal;
import entities.AtributoCarne;
import entities.Calidad;
import entities.Carne;
import entities.GrupoRacial;
import entities.Localidad;
import entities.Sistema;
import entities.Tipo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import services.ServicesFacade;

/**
 *
 * @author Carlos Valencia y Cesar Vega
 */
@ManagedBean(name = "beanConsulta")
@SessionScoped
public class consultasBackingBean{
    
    //información del animal
    private int edad;
    private int kpv;
    private int tipo;
    private int localidad;
    private int grupoRacial;
    private int sistema;
    //atributos adicionales de la carne estos tiene calidad
    private int conformacionCanal;
    private int distribucionGrasaSubcutanea;
    private int grasaPerirrenal;
    private int colorGrasa;
    private int colorCarne;
    //atributos de la carne
    private double pesoCanalFrioDer; // peso de la canal frio derecha
    private double pesoCanalFrioIzq; // peso de la canal frio izquierda
    private double ojoDeLaChuleta; // ancho del ojo de la chuleta
    private double grosorDeGrasaDorsal; // grosor de la grasa dorsal de la carne
    
    
    //Carga los valores predeterminados
    private static final ArrayList<Tipo> todosTipo = ServicesFacade.getInstance("applicationconfig.properties").getTodosTipo();
    private static final ArrayList<Localidad> todosLocalidad = ServicesFacade.getInstance("applicationconfig.properties").getTodosLocalidad();
    private static final ArrayList<GrupoRacial> todosGrupoRacial = ServicesFacade.getInstance("applicationconfig.properties").getTodosGrupoRacial();
    private static final ArrayList<Sistema> todosSistema = ServicesFacade.getInstance("applicationconfig.properties").getTodosSistema();
    private static final ArrayList<Calidad> todosCalidad = ServicesFacade.getInstance("applicationconfig.properties").getTodosCalidad();
    private static final ArrayList<AtributoCarne> todosAtributoCarne = ServicesFacade.getInstance("applicationconfig.properties").getTodosAtributoCarne();
    

    public ArrayList<Tipo> getTodosTipo() {        
        return todosTipo;
    }
    
    public ArrayList<Localidad> getTodosLocalidad() {
        return todosLocalidad;
    }
    
    public ArrayList<GrupoRacial> getTodosGrupoRacial() {
        return todosGrupoRacial;
    }
    
    public ArrayList<Sistema> getTodosSistema() {
        return todosSistema;
    }
    
    public ArrayList<Calidad> getTodosCalidad() {
        return todosCalidad;
    }
    
    /*public ArrayList<AtributoCarne> getTodosAtributoCarne() {
        return todosAtributoCarne;
    }*/

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getKpv() {
        return kpv;
    }

    public void setKpv(int kpv) {
        this.kpv = kpv;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public int getGrupoRacial() {
        return grupoRacial;
    }

    public void setGrupoRacial(int grupoRacial) {
        this.grupoRacial = grupoRacial;
    }

    public int getSistema() {
        return sistema;
    }

    public void setSistema(int sistema) {
        this.sistema = sistema;
    }

    public int getConformacionCanal() {
        return conformacionCanal;
    }

    public void setConformacionCanal(int conformacionCanal) {
        this.conformacionCanal = conformacionCanal;
    }

    public int getDistribucionGrasaSubcutanea() {
        return distribucionGrasaSubcutanea;
    }

    public void setDistribucionGrasaSubcutanea(int distribucionGrasaSubcutanea) {
        this.distribucionGrasaSubcutanea = distribucionGrasaSubcutanea;
    }

    public int getGrasaPerirrenal() {
        return grasaPerirrenal;
    }

    public void setGrasaPerirrenal(int grasaPerirrenal) {
        this.grasaPerirrenal = grasaPerirrenal;
    }

    public int getColorGrasa() {
        return colorGrasa;
    }

    public void setColorGrasa(int colorGrasa) {
        this.colorGrasa = colorGrasa;
    }

    public int getColorCarne() {
        return colorCarne;
    }

    public void setColorCarne(int colorCarne) {
        this.colorCarne = colorCarne;
    }

    public double getPesoCanalFrioDer() {
        return pesoCanalFrioDer;
    }

    public void setPesoCanalFrioDer(double pesoCanalFrioDer) {
        this.pesoCanalFrioDer = pesoCanalFrioDer;
    }

    public double getPesoCanalFrioIzq() {
        return pesoCanalFrioIzq;
    }

    public void setPesoCanalFrioIzq(double pesoCanalFrioIzq) {
        this.pesoCanalFrioIzq = pesoCanalFrioIzq;
    }

    public double getOjoDeLaChuleta() {
        return ojoDeLaChuleta;
    }

    public void setOjoDeLaChuleta(double ojoDeLaChuleta) {
        this.ojoDeLaChuleta = ojoDeLaChuleta;
    }

    public double getGrosorDeGrasaDorsal() {
        return grosorDeGrasaDorsal;
    }

    public void setGrosorDeGrasaDorsal(double grosorDeGrasaDorsal) {
        this.grosorDeGrasaDorsal = grosorDeGrasaDorsal;
    }
    
    /**
     * Calidad de la carne dada por la red neuronal
     */
    public Calidad calidad;
    
    /**
     * Metodó que obtiene la calidad de la carne por medio de la red neuronal
     */
    public  void obtenerCalidad(){
        ArrayList<AtributoCarne> atributoCarnes = new  ArrayList<>();
        atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(0).getKey(), todosAtributoCarne.get(0).getDescripcion(), todosCalidad.get(conformacionCanal-1)));
        atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(1).getKey(), todosAtributoCarne.get(1).getDescripcion(), todosCalidad.get(distribucionGrasaSubcutanea-1)));
        atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(2).getKey(), todosAtributoCarne.get(2).getDescripcion(), todosCalidad.get(grasaPerirrenal-1)));
        atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(3).getKey(), todosAtributoCarne.get(3).getDescripcion(), todosCalidad.get(colorGrasa-1)));
        atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(4).getKey(), todosAtributoCarne.get(4).getDescripcion(), todosCalidad.get(colorCarne-1)));
        Carne carne = new Carne(pesoCanalFrioDer, pesoCanalFrioIzq, ojoDeLaChuleta, grosorDeGrasaDorsal, atributoCarnes, null);
        Animal animal = new Animal(1, todosTipo.get(tipo-1), edad, kpv, todosLocalidad.get(localidad-1), carne, todosGrupoRacial.get(grupoRacial-1), todosSistema.get(sistema-1));
        Animal animalTmp = ServicesFacade.getInstance("applicationconfig.properties").valueOf(animal);
        calidad = animalTmp.getCarne().getCalidad();
    }

    public Calidad getCalidad() {
        return calidad;
    }

    public void setCalidad(Calidad calidad) {
        this.calidad = calidad;
    }
    
    private UploadedFile file;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    /**
     * Metodó que carga el archivo que se le va sacar la calidad
     * @param event el archivo
     */
    public void upload(FileUploadEvent event) {
        file = event.getFile();
        FacesMessage message = new FacesMessage("Enhorabuena ", event.getFile().getFileName() + " está cargado.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        obtenerCalidades();
        guardarCalidades();        
    }
    
    /**
     * Animales modificados con calidad
     */
    Set<Animal> data = new HashSet<>();
    
    /**
     * Metodó que obtiene las calidades de la carne por medio de la red neuronal
     */
    public void obtenerCalidades() {
        try {
            InputStream stream = file.getInputstream();
            BufferedReader archivo = new BufferedReader(new InputStreamReader(stream));
            String[] datos;
            String texto;
            //Leer el archivo
            while ((texto = archivo.readLine()) != null) {
                datos = texto.split("\\s+");
                if (datos.length >= 14) {
                    //Creacion de animales
                    ArrayList<AtributoCarne> atributoCarnes = new  ArrayList<>();
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(0).getKey(), todosAtributoCarne.get(0).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[6]))-1)));
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(1).getKey(), todosAtributoCarne.get(1).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[7]))-1)));
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(2).getKey(), todosAtributoCarne.get(2).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[8]))-1)));
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(3).getKey(), todosAtributoCarne.get(3).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[9]))-1)));
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(4).getKey(), todosAtributoCarne.get(4).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[10]))-1)));
                    
                    Animal animal = new Animal(Integer.parseInt(datos[5]), 
                            todosTipo.get((Integer.parseInt(datos[2])-1)),
                            Integer.parseInt(datos[4]), 
                            Integer.parseInt(datos[5]),
                            todosLocalidad.get(Integer.parseInt(datos[0])),
                            new Carne(convDouble(datos[11]),
                                    convDouble(datos[12]), 
                                    convDouble(datos[13]), 
                                    convDouble(datos[14]),
                                    atributoCarnes, 
                                    null),
                            todosGrupoRacial.get(Integer.parseInt(datos[1])), 
                            todosSistema.get(Integer.parseInt(datos[3])));
                    
                    data.add(animal);
                }
            }
            ServicesFacade.getInstance("applicationconfig.properties").valueOfall(data);
            
        } catch (IOException ex) {
            Logger.getLogger(consultasBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NumberFormatException ex){
            
        }
    }
    
    private StreamedContent fileD;

    public StreamedContent getFileD() {
        return fileD;
    }

    public void setFileD(StreamedContent fileD) {
        this.fileD = fileD;
    }
    
    /**
     * Metodo que guarda las calidades en un archivo de texto
     */
    public void guardarCalidades(){
        BufferedWriter bwr;
        try {
            bwr = new BufferedWriter(new FileWriter("clasificados.txt"));
            for (Animal animales : data) {                
                // escrive en la linea del archivo el texto
                bwr.write(animales.getCarne().getCalidad().getDescripcion());
                //Cambia de linea
                bwr.newLine();
            }
            //
            bwr.flush();
            InputStream stream = new FileInputStream(new File("clasificados.txt"));
            fileD = new DefaultStreamedContent(stream, "application/txt", "downloaded_Calidad.txt");
        } catch (IOException ex) {
            Logger.getLogger(consultasBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Transforma de String a Double
     * @param dato Valor a transforma a Double
     * @return String transformado en Double
     */
    private double convDouble(String dato) {
        // Tratamiento para valores que están separados por una “,” ya que la conversión directa no lo reconoce como valido 
        String[] tok = dato.split(",");
        StringBuilder stri = new StringBuilder();
        for(int i = 0; i < tok.length; i++){
            stri.append(tok[i]);
        }
        double num;
        if(tok.length == 2){
            num = Integer.parseInt(stri.toString())/(Math.pow(10, tok[1].toCharArray().length));
        }else{
            num = Double.parseDouble(dato);
        }
        return num;
    }
}
