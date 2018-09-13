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
public class SoftwareCtrl {
    private List<Software> sws = new ArrayList<>();
    private Software sw;
    private SoftwareDB swDB;
    private MediosCtrl medCtrl = new MediosCtrl();
    private MediosDB medDB = new MediosDB();
    private SistOpDB soDB = new SistOpDB();
    private ExtrasCtrl exCtrl = new ExtrasCtrl();
    private ExtrasDB extrasDB;

    public SoftwareCtrl(){}

    //INGRESO DE SOFTWARE
    public void cargarSoftware(){
        swDB  = new SoftwareDB();
        sws.clear();
        List<SoftwareDB> swdb = swDB.read("Software");

        for(SoftwareDB s : swdb){

            int codigo = s.getCodigo();
            String nombre = s.getNombre();
            String version = s.getVersion();
            //BUSCAR SO
            List<SistOpDB> sistOp = soDB.sistopDeSoftware(codigo);
            List<String> so = new ArrayList<>();
            if(!sistOp.isEmpty()){
                for(SistOpDB si : sistOp)
                    so.add(si.getNombre());
            }
            //BUSCAR MEDIOS
            List<MediosDB> m = medDB.mediosDeSoftware(codigo);
            List<Medios> medios = new ArrayList<>();
            if(!m.isEmpty()){
                medCtrl.buscarMedios(m);
                medios = medCtrl.getMedSw();
            }
            //CREAR SOFTWARE
//            List<Medios> medios = new ArrayList<>();
            Software soft = new Software(codigo, nombre, so, version, medios);
            //AGREGAR EXTRAS
            extrasDB = new ExtrasDB();
            List<ExtrasDB> ex = extrasDB.extrasDeSoftware(codigo);
            for(ExtrasDB x : ex){
                soft.setExtras(x.getNombre(),x.getVersion(),x.getDescrip(),x.getPartes());
            }

            sws.add(soft);
        }
    }

    public Software findSoftware(int codigo){
        for(Software s : sws){
            if(s.getCodigo() == codigo)
                return s;
        }
        return null;
    }

    public void eliminarSoDeSw(int codigo, String sistOp){
        swDB  = new SoftwareDB();
        swDB.setCodigo(codigo);
        swDB.setSoNom(sistOp);
        swDB.deleteSOSw();
    }

    public void agregarSoDeSw(int codigo, String sistOp){
        swDB  = new SoftwareDB();
        swDB.setCodigo(codigo);
        swDB.setSoNom(sistOp);
        swDB.insertSOSw();
    }

     public void altaSoftware(String nombre, String version){
        swDB  = new SoftwareDB();
        swDB.setNombre(nombre);
        swDB.setVersion(version);
        swDB.write();
    }

     public void modSoftware(String nombre, String version, String newNom, String newVers){
        swDB  = new SoftwareDB();
        swDB.setNombre(nombre);
        swDB.setNuevoNom(newNom);
        swDB.setVersion(version);
        swDB.setNuevoVers(newVers);
        swDB.update();
    }

     public void elimSoftware(int codigo){
        swDB  = new SoftwareDB();
        swDB.setCodigo(codigo);
        swDB.delete();
    }

    public List<Software> getSws() {
        return sws;
    }

    public void setSws(List<Software> sws) {
        this.sws = sws;
    }

    public Software getSw() {
        return sw;
    }

    public void setSw(Software sw) {
        this.sw = sw;
    }

    public SoftwareDB getSwDB() {
        return swDB;
    }

    public void setSwDB(SoftwareDB swDB) {
        this.swDB = swDB;
    }

    public MediosCtrl getMedCtrl() {
        return medCtrl;
    }

    public void setMedCtrl(MediosCtrl medCtrl) {
        this.medCtrl = medCtrl;
    }

    public MediosDB getMedDB() {
        return medDB;
    }

    public void setMedDB(MediosDB medDB) {
        this.medDB = medDB;
    }

    public SistOpDB getSoDB() {
        return soDB;
    }

    public void setSoDB(SistOpDB soDB) {
        this.soDB = soDB;
    }

    public ExtrasCtrl getExCtrl() {
        return exCtrl;
    }

    public void setExCtrl(ExtrasCtrl exCtrl) {
        this.exCtrl = exCtrl;
    }

    public ExtrasDB getExtrasDB() {
        return extrasDB;
    }

    public void setExtrasDB(ExtrasDB extrasDB) {
        this.extrasDB = extrasDB;
    }


}
