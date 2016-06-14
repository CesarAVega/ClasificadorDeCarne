/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.controller;

import entities.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
public class consultasBackingBean implements Serializable{
    
    //información del animal
    private int edad;
    private int kpv;
    private String tipo;
    private String localidad;
    private String grupoRacial;
    private String sistema;
    //atributos adicionales de la carne estos tiene calidad
    private String conformacionCanal;
    private String distribucionGrasaSubcutanea;
    private String grasaPerirrenal;
    private String colorGrasa;
    private String colorCarne;
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

    public ArrayList<String> getTodosTipoString() {
        return todosTipoString;
    }

    public void setTodosTipoString(ArrayList<String> todosTipoString) {
        this.todosTipoString = todosTipoString;
    }

    public ArrayList<String> getTodosLocalidadString() {
        return todosLocalidadString;
    }

    public void setTodosLocalidadString(ArrayList<String> todosLocalidadString) {
        this.todosLocalidadString = todosLocalidadString;
    }

    public ArrayList<String> getTodosGrupoRacialString() {
        return todosGrupoRacialString;
    }

    public void setTodosGrupoRacialString(ArrayList<String> todosGrupoRacialString) {
        this.todosGrupoRacialString = todosGrupoRacialString;
    }

    public ArrayList<String> getTodosSistemaString() {
        return todosSistemaString;
    }

    public void setTodosSistemaString(ArrayList<String> todosSistemaString) {
        this.todosSistemaString = todosSistemaString;
    }

    public ArrayList<String> getTodosCalidadString() {
        return todosCalidadString;
    }

    public void setTodosCalidadString(ArrayList<String> todosCalidadString) {
        this.todosCalidadString = todosCalidadString;
    }
    
    private ArrayList<String> todosTipoString;
    private ArrayList<String> todosLocalidadString;
    private ArrayList<String> todosGrupoRacialString;
    private ArrayList<String> todosSistemaString;
    private ArrayList<String> todosCalidadString;
    
    @PostConstruct
    public void init() {
        todosTipoString = new ArrayList<>();
        for(Tipo t: todosTipo){ todosTipoString.add(t.getDescripcion());}
        todosLocalidadString = new ArrayList<>();
        for(Localidad t: todosLocalidad){ todosLocalidadString.add(t.getDescripcion());}
        todosGrupoRacialString = new ArrayList<>();
        for(GrupoRacial t: todosGrupoRacial){ todosGrupoRacialString.add(t.getDescripcion());}
        todosSistemaString = new ArrayList<>();
        for(Sistema t: todosSistema){ todosSistemaString.add(t.getDescripcion());}
        todosCalidadString = new ArrayList<>();
        for(Calidad t: todosCalidad){ todosCalidadString.add(t.getDescripcion());}
    }

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getGrupoRacial() {
        return grupoRacial;
    }

    public void setGrupoRacial(String grupoRacial) {
        this.grupoRacial = grupoRacial;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getConformacionCanal() {
        return conformacionCanal;
    }

    public void setConformacionCanal(String conformacionCanal) {
        this.conformacionCanal = conformacionCanal;
    }

    public String getDistribucionGrasaSubcutanea() {
        return distribucionGrasaSubcutanea;
    }

    public void setDistribucionGrasaSubcutanea(String distribucionGrasaSubcutanea) {
        this.distribucionGrasaSubcutanea = distribucionGrasaSubcutanea;
    }

    public String getGrasaPerirrenal() {
        return grasaPerirrenal;
    }

    public void setGrasaPerirrenal(String grasaPerirrenal) {
        this.grasaPerirrenal = grasaPerirrenal;
    }

    public String getColorGrasa() {
        return colorGrasa;
    }

    public void setColorGrasa(String colorGrasa) {
        this.colorGrasa = colorGrasa;
    }

    public String getColorCarne() {
        return colorCarne;
    }

    public void setColorCarne(String colorCarne) {
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
    public Boolean seObtuvoCalidad;

    public Boolean getSeObtuvoCalidad() {
        return seObtuvoCalidad;
    }
    /**
     * Metodó que obtiene la calidad de la carne por medio de la red neuronal
     */
    public  void obtenerCalidad(){
        if(!datosValidos()){
            Calidad cal1 = null, cal2 = null, cal3 = null, cal4 = null, cal5 = null;
            Tipo tipof = null;
            for(Tipo t: todosTipo){ if(t.getDescripcion().equals(tipo)){tipof = t;}}
            Localidad localidadf = null;
            for(Localidad t: todosLocalidad){ if(t.getDescripcion().equals(localidad)){localidadf = t;}}
            GrupoRacial grupoRacialf = null;
            for(GrupoRacial t: todosGrupoRacial){ if(t.getDescripcion().equals(grupoRacial)){grupoRacialf = t;}}
            Sistema sistemaf = null;
            for(Sistema t: todosSistema){ if(t.getDescripcion().equals(sistema)){sistemaf = t;}}
            
            for(Calidad t: todosCalidad){ if(t.getDescripcion().equals(conformacionCanal)){cal1 = t;}}
            for(Calidad t: todosCalidad){ if(t.getDescripcion().equals(distribucionGrasaSubcutanea)){cal2 = t;}}
            for(Calidad t: todosCalidad){ if(t.getDescripcion().equals(grasaPerirrenal)){cal3 = t;}}
            for(Calidad t: todosCalidad){ if(t.getDescripcion().equals(colorGrasa)){cal4 = t;}}
            for(Calidad t: todosCalidad){ if(t.getDescripcion().equals(colorCarne)){cal5 = t;}}
                       
            ArrayList<AtributoCarne> atributoCarnes = new  ArrayList<>();
            atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(0).getKey(), todosAtributoCarne.get(0).getDescripcion(), cal1));
            atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(1).getKey(), todosAtributoCarne.get(1).getDescripcion(), cal2));
            atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(2).getKey(), todosAtributoCarne.get(2).getDescripcion(), cal3));
            atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(3).getKey(), todosAtributoCarne.get(3).getDescripcion(), cal4));
            atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(4).getKey(), todosAtributoCarne.get(4).getDescripcion(), cal5));
            Carne carne = new Carne(pesoCanalFrioDer, pesoCanalFrioIzq, ojoDeLaChuleta, grosorDeGrasaDorsal, atributoCarnes, null);
            Animal animal = new Animal(1, tipof, edad, kpv, localidadf, carne, grupoRacialf, sistemaf);
            Animal animalTmp = ServicesFacade.getInstance("applicationconfig.properties").valueOf(animal);
            calidad = animalTmp.getCarne().getCalidad();
            seObtuvoCalidad = true;
            reset();
        }else{
            seObtuvoCalidad = false;
        }

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
                if (datos.length >= 13) {
                    //Creacion de animales                    
                    ArrayList<AtributoCarne> atributoCarnes = new  ArrayList<>();
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(0).getKey(), todosAtributoCarne.get(0).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[6]))-1)));
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(1).getKey(), todosAtributoCarne.get(1).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[7]))-1)));
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(2).getKey(), todosAtributoCarne.get(2).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[8]))-1)));
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(3).getKey(), todosAtributoCarne.get(3).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[9]))-1)));
                    atributoCarnes.add(new AtributoCarne(todosAtributoCarne.get(4).getKey(), todosAtributoCarne.get(4).getDescripcion(), todosCalidad.get((Integer.parseInt(datos[10]))-1)));
                    
                    Animal animal = new Animal(Integer.parseInt(datos[5]), 
                            todosTipo.get(Integer.parseInt(datos[2])-1),
                            Integer.parseInt(datos[4]), 
                            Integer.parseInt(datos[5]),
                            todosLocalidad.get(Integer.parseInt(datos[0])-1),
                            new Carne(convDouble(datos[11]),
                                    convDouble(datos[12]), 
                                    convDouble(datos[13]), 
                                    convDouble(datos[14]),
                                    atributoCarnes, 
                                    null),
                            todosGrupoRacial.get(Integer.parseInt(datos[1])-1), 
                            todosSistema.get(Integer.parseInt(datos[3])-1)
                    );
                    
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
        if(fileD == null){
            FacesMessage message = new FacesMessage("Aun no hay un archivo cargado");
            FacesContext.getCurrentInstance().addMessage(null, message);            
        }
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

    private boolean datosValidos() {
        return edad == 0
                || kpv == 0
                || tipo.equals("") 
                || grupoRacial.equals("")
                || sistema.equals("")
                || localidad.equals("")
                || conformacionCanal.equals("")
                || distribucionGrasaSubcutanea.equals("")
                || grasaPerirrenal.equals("")
                || colorGrasa.equals("")
                || colorCarne.equals("")
                || pesoCanalFrioDer == 0D
                || pesoCanalFrioIzq == 0D
                || ojoDeLaChuleta == 0D
                || grosorDeGrasaDorsal == 0D;
    }

    public void reset() {
        edad = 0;
        kpv = 0;
        tipo = "";
        grupoRacial = "";
        sistema = "";
        localidad = "";
        conformacionCanal = "";
        distribucionGrasaSubcutanea = "";
        grasaPerirrenal = "";
        colorGrasa = "";
        colorCarne = "";
        pesoCanalFrioDer = 0D;
        pesoCanalFrioIzq = 0D;
        ojoDeLaChuleta = 0D;
        grosorDeGrasaDorsal = 0D;
    }
}
