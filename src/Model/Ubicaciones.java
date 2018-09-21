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
public class Ubicaciones {

    private String id;
    private String descripcion;
    
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    
    public Ubicaciones(){}
    public Ubicaciones(String i, String d){
    
    id= i;
    descripcion=d;
    }
}
