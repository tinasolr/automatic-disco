/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import Model.*;

/**
 *
 * @author tinar
 */
public class ExtrasCtrl {
    private Extras extra;
    private ExtrasDB extrasDB = new ExtrasDB();

    public ExtrasCtrl() {
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
    }

    public void eliminarExtra(String nombre, Software soft){
        extrasDB.setNombre(nombre);
        extrasDB.setSwid(soft.getCodigo());
        String id = extrasDB.searchTable();
        int extraid = Integer.parseInt(id);
        extrasDB.setId(extraid);
        extrasDB.delete();
    }

    public void eliminarExtras(Software soft){
        extrasDB.setSwid(soft.getCodigo());
        extrasDB.deleteAllExtras();
    }

    public void altaExtra(String nombre, String descrip, String version, int partes, int sw){
        extrasDB.setNombre(nombre);
        extrasDB.setDescrip(descrip);
        extrasDB.setVersion(version);
        extrasDB.setPartes(partes);
        extrasDB.setSwid(sw);
        extrasDB.write();
    }

}
