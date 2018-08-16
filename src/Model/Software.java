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
public class Software {

    private int codigo;
    private String nombre;
    private List<String> sistOp;
    private String version;
    private List<Extras> extras;
    private List<Copias> copias;
    private List<Medios> medios;

    {
        sistOp = new ArrayList<>();
        extras = new ArrayList<>();
        copias = new ArrayList<>();
        medios = new ArrayList<>();
    }

    public Software(){}

    public Software(int codigo, String nombre, List<String> sistOp, String version, List<Medios> medios) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sistOp = sistOp;
        this.version = version;
        this.medios = medios;
    }

    public Software(int codigo, String nombre, List<String> sistOp, String version) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sistOp = sistOp;
        this.version = version;
        this.medios = new ArrayList<>();
    }

    public int getCodigo() {return codigo;}
    public void setCodigo(int codigo) {this.codigo = codigo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public List<String> getSistOp() {return sistOp;}
    public void setSistOp(List<String> sistOp) {this.sistOp = sistOp;}

    public String getVersion() {return version;}
    public void setVersion(String version) {this.version = version;}

    public List<Extras> getExtras() {return extras;}
    public void setExtras(List<Extras> extras) {this.extras = extras;}

    public List<Copias> getCopias() {return copias;}
    public void setCopias(List<Copias> copias) {this.copias = copias;}

    public List<Medios> getMedios() {return medios;}
    public void setMedios(List<Medios> medios) {this.medios = medios;}

}
