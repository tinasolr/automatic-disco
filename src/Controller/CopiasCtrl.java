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
    
    
    
    
    public void cargarCopias(String medid){
        if(!copias.isEmpty())
            copias.clear();
        copdb.connect();
        List<CopiasDB> dbcopia = copdb.read("copias");
        
        FormatoDB formdb = new FormatoDB();
        UbicacionesDB ubidb = new UbicacionesDB();
        formdb.connect();
        ubidb.connect();
        Ubicaciones ubi;
        String nombreform,ubiid;
        
        for(CopiasDB c : dbcopia){
            if (c.getMedioid().equalsIgnoreCase(medid))
            {
                copdb.setId(c.getId());
                ubiid = copdb.findubiid();
                ubidb.setCodUbi(ubiid);
                ubi = new Ubicaciones(ubiid, ubidb.executeSearch());

                nombreform = formdb.find_formatonom(c.getFormid());
                copias.add(new Copias(c.getId(),nombreform,c.getObs(),ubi));
            }
        }
    }
    
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
     
    public void desasociarUbicacionACopia(int idCopia){
        copdb  = new CopiasDB();
        copdb.connect();
        copdb.setId(idCopia);
        copdb.desasociarUbicacionACopia();
     }  
    
    public void modificarUbicacionACopia(int idCopia, String idUbi, boolean EnDepo){
        copdb  = new CopiasDB();
        copdb.connect();
        copdb.setId(idCopia);
        copdb.setUbi(idUbi);
        copdb.setEnDepo(EnDepo);
        copdb.actualizarUbicacionACopia();
     } 
     
    public void ModificarCopia(){
        copdb  = new CopiasDB();
        copdb.connect();
      
    }
     
    public void EliminarCopia(int id){
        copdb = new CopiasDB();
        copdb.connect();
        copdb.setId(id);
        copdb.delete();
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
