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
public class Copias {

    private int id;
    private String formato;
    private String observ;
    private Ubicaciones ubiDepo;

    public Copias(){}
    public Copias(int id,String formato, String observ, Ubicaciones ubiDepo){
        this.id = id;
        this.formato = formato;
        this.observ = observ;
        this.ubiDepo = ubiDepo;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    
    public String getFormato() {return formato;}
    public void setFormato(String formato) {this.formato = formato;}

    public String getObserv() {return observ;}
    public void setObserv(String observ) {this.observ = observ;}

    public Ubicaciones getUbiDepo() {return ubiDepo;}
    public void setUbiDepo(Ubicaciones ubiDepo) {this.ubiDepo = ubiDepo;}
}
