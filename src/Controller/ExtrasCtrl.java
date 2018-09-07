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
public class ExtrasCtrl {
    private List<Extras> extras = new ArrayList<>();
    private Extras extra;
    private ExtrasDB extrasDB = new ExtrasDB();

    public ExtrasCtrl() {
    }

    public void cargarExtras(int swid){
        List<ExtrasDB> ex = extrasDB.extrasDeSoftware(swid);
        if(!extras.isEmpty())
            extras.clear();
        for(ExtrasDB x : ex){
            extras.add(new Extras(x.getNombre(),x.getVersion(),x.getDescrip(),x.getPartes()));
        }
    }

    public void modificarExtra(String nombre, String descrip, String version, int partes, Software soft){
        extrasDB.setNombre(nombre);
        extrasDB.setSwid(soft.getCodigo());
        String id = extrasDB.searchTable();
        int extraid = Integer.parseInt(id);
        extrasDB.setId(extraid);
        extrasDB.setDescrip(descrip);
        extrasDB.setVersion(version);
        extrasDB.setPartes(partes);
        extrasDB.update();
        if(!extras.isEmpty())
            extras.clear();
    }

    public void eliminarExtra(String nombre, Software soft){
        extrasDB.setNombre(nombre);
        extrasDB.setSwid(soft.getCodigo());
        String id = extrasDB.searchTable();
        int extraid = Integer.parseInt(id);
        extrasDB.setId(extraid);
        extrasDB.delete();
        if(!extras.isEmpty())
            extras.clear();
    }

    public void eliminarExtras(Software soft){
        extrasDB.setSwid(soft.getCodigo());
        extrasDB.deleteAllExtras();
        if(!extras.isEmpty())
            extras.clear();
    }

    public Extras altaExtra(String nombre, String descrip, String version, int partes, Software soft){
        extrasDB.setNombre(nombre);
        extrasDB.setDescrip(descrip);
        extrasDB.setVersion(version);
        extrasDB.setPartes(partes);
        extrasDB.setSwid(soft.getCodigo());
        extrasDB.write();
        if(!extras.isEmpty())
            extras.clear();
        Extras nuevo = new Extras(nombre, version, descrip, partes);
        return nuevo;
    }
    public List<Extras> getExtras() { return extras; }
    public void setExtras(List<Extras> extras) {        this.extras = extras;    }
    public Extras getExtra() {        return extra;    }
    public void setExtra(Extras extra) {        this.extra = extra;    }
}
