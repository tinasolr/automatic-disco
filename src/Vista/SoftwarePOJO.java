/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

/**
 *
 * @author tinar
 */
public class SoftwarePOJO {

    private int codigo;
    private String nombre;
    private String sistOp;
    private String version;
    private String exNomb;
    private String exVers;
    private String exDescr;
    private int exPartes;

    public SoftwarePOJO(){}

    public SoftwarePOJO(int codigo, String nombre, String sistOp, String version, String exNomb, String exVers, String exDescr, int exPartes) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sistOp = sistOp;
        this.version = version;
        this.exNomb = exNomb;
        this.exVers = exVers;
        this.exDescr = exDescr;
        this.exPartes = exPartes;
    }

    public int getCodigo() {return codigo;}
    public void setCodigo(int codigo) {this.codigo = codigo;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getVersion() {return version;}
    public void setVersion(String version) {this.version = version;}

    public String getSistOp() {return sistOp;}
    public void setSistOp(String sistOp) { this.sistOp = sistOp; }

    public String getExNomb() {return exNomb; }
    public void setExNomb(String exNomb) {  this.exNomb = exNomb;  }

    public String getExVers() {  return exVers;  }
    public void setExVers(String exVers) { this.exVers = exVers; }

    public String getExDescr() { return exDescr;  }
    public void setExDescr(String exDescr) {this.exDescr = exDescr; }

    public int getExPartes() { return exPartes; }
    public void setExPartes(int exPartes) { this.exPartes = exPartes; }


}
