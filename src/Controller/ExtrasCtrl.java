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
        extras.clear();
        for(ExtrasDB x : ex){
            extras.add(new Extras(x.getNombre(),x.getVersion(),x.getDescrip(),x.getPartes()));
        }
    }

    public void cargarExtras(){
        List<ExtrasDB> ex = (List<ExtrasDB>) extrasDB.read("Extras");
        extras.clear();
        for(ExtrasDB x : ex){
            extras.add(new Extras(x.getNombre(),x.getVersion(),x.getDescrip(),x.getPartes()));
        }
    }

    public void modificarExtra(){

    }

    public void eliminarExtra(){

    }

    public void altaExtra(String nombre, String descrip, String version, int partes, Software soft){
        extrasDB.setNombre(nombre);
        extrasDB.setDescrip(descrip);
        extrasDB.setVersion(version);
        extrasDB.setPartes(partes);
        extrasDB.setSwid(soft.getCodigo());
        extrasDB.write("Extras");
    }
    public List<Extras> getExtras() { return extras; }
    public void setExtras(List<Extras> extras) {        this.extras = extras;    }
    public Extras getExtra() {        return extra;    }
    public void setExtra(Extras extra) {        this.extra = extra;    }
}
