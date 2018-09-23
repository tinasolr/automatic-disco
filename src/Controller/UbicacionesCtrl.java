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
public class UbicacionesCtrl {

    private List<Ubicaciones> ubis = new ArrayList<>();
    private Ubicaciones ubicacion;
    private UbicacionesDB dbubi = new UbicacionesDB();

    public UbicacionesCtrl() {}

    public void cargarUbicaciones(){
        if(!ubis.isEmpty())
            ubis.clear();
        dbubi.connect();
        List<UbicacionesDB> dbubis = dbubi.read("Ubicaciones");
        for(UbicacionesDB b : dbubis){
            ubis.add(new Ubicaciones(b.getCodUbi(), b.getObsUbi()));
        }
    }

    public List<Ubicaciones> getUbis() {        return ubis;    }
    public void setUbis(List<Ubicaciones> ubis) {        this.ubis = ubis;   }

    public Ubicaciones getUbicacion() {        return ubicacion;    }
    public void setUbicacion(Ubicaciones ubicacion) {        this.ubicacion = ubicacion;    }

}
