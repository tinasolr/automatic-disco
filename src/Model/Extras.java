/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * @author tinar
 */
public class Extras {
    private String nombre;
    private String version;
    private String descrip;
    private int partes;

    public Extras(){}

    public Extras(String nombre, String version, String descrip, int partes){
        this.nombre = nombre;
        this.version = version;
        this.descrip = descrip;
        this.partes = partes;
    }

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getVersion() {return version;}
    public void setVersion(String version) {this.version = version;}

    public String getDescrip() {return descrip;}
    public void setDescrip(String descrip) {this.descrip = descrip;}

    public int getPartes() {return partes;}
    public void setPartes(int partes) {this.partes = partes;}

}
