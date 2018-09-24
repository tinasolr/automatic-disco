/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.*;

/**
 *
 * @author tinar
 */
public class Medios {

    private String codigo;
    private String nombre;
    private String formato;
    private boolean caja;
    private boolean manual;
    private int origen;
    private Ubicaciones ubiDepo;
    private boolean enDepo;
    private String imagen;
    private String observ;
    private int partes;
    private List<Copias> copias = new ArrayList<>();

    public Medios(){}

    public Medios(String codigo, String nombre, String formato, boolean empaque, boolean manual, int origen, Ubicaciones ubiDepo, boolean enDepo, String imagen, String observ, int partes) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.formato = formato;
        this.caja = empaque;
        this.manual = manual;
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

    public int getOrigen() {return origen;}
    public void setOrigen(int origen) {this.origen = origen;}

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

    public boolean isCaja() {        return caja;    }
    public void setCaja(boolean caja) {        this.caja = caja;    }

    public boolean isManual() {        return manual;   }
    public void setManual(boolean manual) {        this.manual = manual;    }

    public List<Copias> getCopias() {        return copias;    }
    public void setCopias(String formato, Ubicaciones ubiDepo, String observ) {
        copias.add(new Copias(formato, observ, ubiDepo));
    }
    public void setCopias(int pos, String formato, Ubicaciones ubiDepo, String observ) {
        copias.set(pos, new Copias(formato, observ, ubiDepo));
    }
}
