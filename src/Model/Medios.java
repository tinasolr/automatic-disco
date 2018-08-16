/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author tinar
 */
public class Medios {
    
    private String codigo;
    private String nombre;
    private String formato;
    private boolean empaque;
    private Origen origen;
    private Ubicaciones ubiDepo;
    private boolean enDepo;
    private String imagen;
    private String observ;
    private int partes;

    public Medios(){}

    public Medios(String codigo, String nombre, String formato, boolean empaque, Origen origen, Ubicaciones ubiDepo, boolean enDepo, String imagen, String observ, int partes) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.formato = formato;
        this.empaque = empaque;
        this.origen = origen;
        this.ubiDepo = ubiDepo;
        this.enDepo = enDepo;
        this.imagen = imagen;
        this.observ = observ;
        this.partes = partes;
    }

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getFormato() {return formato;}
    public void setFormato(String formato) {this.formato = formato;}

    public boolean isEmpaque() {return empaque;}
    public void setEmpaque(boolean empaque) {this.empaque = empaque;}

    public Origen getOrigen() {return origen;}
    public void setOrigen(Origen origen) {this.origen = origen;}

    public Ubicaciones getUbiDepo() {return ubiDepo;}
    public void setUbiDepo(Ubicaciones ubiDepo) {this.ubiDepo = ubiDepo;}

    public boolean isEnDepo() {return enDepo;}
    public void setEnDepo(boolean enDepo) {this.enDepo = enDepo;}

    public String getImagen() {return imagen;}
    public void setImagen(String imagen) {this.imagen = imagen;}

    public String getObserv() {return observ;}
    public void setObserv(String observ) {this.observ = observ;}

    public int getPartes() {return partes;}
    public void setPartes(int partes) {this.partes = partes;}

}
