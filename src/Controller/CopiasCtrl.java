/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import Model.*;
import DAO.*;
import java.util.*;

/**
 *
 * @author tinar
 */
public class CopiasCtrl {

    
    private List<Copias> copias = new ArrayList<>();
    private CopiasDB copdb = new CopiasDB();
    
    public CopiasCtrl(){}
    
    
    
     public void CrearCopia(String medioid, int formatoid, String obs){
        copdb  = new CopiasDB();
        copdb.connect();
        copdb.setMedioid(medioid);
        copdb.setFormid(formatoid);
        copdb.setObs(obs);
        copdb.write();
    }
    
     
     public void asociarUbicacionACopia(int idCopia, String idUbi, boolean EnDepo){
        copdb  = new CopiasDB();
        copdb.connect();
        copdb.setId(idCopia);
        copdb.setUbi(idUbi);
        copdb.setEnDepo(EnDepo);
        copdb.asociarUbicacionACopia();
     } 
     
    public String buscarUltimoID() {
        copdb  = new CopiasDB();
        copdb.connect();
        return copdb.buscarUltimoID();
    }
     
     
    
    public List<Copias> getCopias() { return copias;}
    public void setCopias(List<Copias> copias) {this.copias = copias;}

    public CopiasDB getCopdb() {return copdb;}
    public void setCopdb(CopiasDB copdb) {this.copdb = copdb;}

    
    
    
}
