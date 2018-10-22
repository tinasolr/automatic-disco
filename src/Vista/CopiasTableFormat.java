/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

/**
 *
 * @author Nico
 */
public class CopiasTableFormat {
    
    
    private int id;
    private String formato;
    private String enDepo;
    private String descripcion;

    
    public CopiasTableFormat(){}
    public CopiasTableFormat(int i,String f, String ed,String d)
    {
        id=i;
        formato=f;
        enDepo=ed;
        descripcion=d;
        
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String isEnDepo() {
        return enDepo;
    }

    public void setEnDepo(String enDepo) {
        this.enDepo = enDepo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
    
    
}
