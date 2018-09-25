/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import Model.*;
import java.util.*;

/**
 *
 * @author tinar
 */
public class MediosCtrl {

    List<Medios> medSw = new ArrayList<>();

    public void buscarMedios(List<MediosDB> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     public void altaMedio(String codigo, String nombre, int formato, boolean caja,
        boolean manual, int origen, Ubicaciones ubiDepo, boolean enDepo,
        String imagen, String observ, int partes, List<Software> soft){

        MediosDB meDB = new MediosDB();
        meDB.connect();
        meDB.setId(codigo);
        meDB.setNombre(nombre);
        meDB.setPartes(partes);
        meDB.setManual(manual);
        meDB.setCaja(caja);
        meDB.setImagen(imagen);
        meDB.setObserv(observ);
        meDB.setFormid(formato);
        meDB.setOrigen(origen);
        meDB.write();
        for(Software s : soft){
            meDB.setSwid(s.getCodigo());
            meDB.asociarMedioASoftware();
        }
        meDB.setUbic(ubiDepo.getId());
        meDB.setEnDepo(enDepo);
        meDB.asociarUbicacionAMedio();
    }

     public void modMedio(String id, String nombre, int partes, boolean manual, boolean caja, String imagen, String observ, int formid, int origen){
        MediosDB meDB = new MediosDB();
        meDB.connect();
        meDB.setNombre(nombre);
        meDB.setId(id);
        meDB.setNombre(nombre);
        meDB.setPartes(partes);
        meDB.setManual(manual);
        meDB.setCaja(caja);
        meDB.setImagen(imagen);
        meDB.setObserv(observ);
        meDB.setFormid(formid);
        meDB.setOrigen(origen);
        meDB.update();
    }

     public void elimMedio(String codigo){
        MediosDB meDB  = new MediosDB();
        meDB.connect();
        meDB.setId(codigo);
        meDB.delete();
    }

    public List<Medios> getMedSw() {        return medSw;    }
    public void setMedSw(List<Medios> medSw) {        this.medSw = medSw;    }

}
