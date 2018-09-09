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
    private SoftwareDB swDB = new SoftwareDB();
    private MediosCtrl medCtrl = new MediosCtrl();
    private MediosDB medDB = new MediosDB();
    private SistOpDB soDB = new SistOpDB();
    private ExtrasCtrl exCtrl = new ExtrasCtrl();
    private ExtrasDB extrasDB = new ExtrasDB();

    public SoftwareCtrl(){}

    public void cargarSoftware(){
        List<SoftwareDB> swdb = swDB.read("Software");
        if(!sws.isEmpty())
            sws.clear();
        for(SoftwareDB s : swdb){
            int codigo = s.getCodigo();
            String nombre = s.getNombre();
            String version = s.getVersion();
            //BUSCAR SO
            List<SistOpDB> sistOp = soDB.sistopDeSoftware(codigo);
            List<String> so = new ArrayList<>();
            for(SistOpDB si : sistOp)
                so.add(si.getNombre());
            //BUSCAR MEDIOS
            List<MediosDB> m = medDB.mediosDeSoftware(codigo);
            medCtrl.buscarMedios(m);
            List<Medios> medios = medCtrl.getMedSw();
            //CREAR SOFTWARE
            Software soft = new Software(codigo, nombre, so, version, medios);
            //AGREGAR EXTRAS
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
}
