/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapp.controller;

import entities.AtributoCarne;
import entities.Calidad;
import entities.GrupoRacial;
import entities.Localidad;
import entities.Sistema;
import entities.Tipo;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    
    public ArrayList<AtributoCarne> getTodosAtributoCarne() {
        return todosAtributoCarne;
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
    
    
    
}
