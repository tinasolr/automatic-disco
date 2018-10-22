/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import Model.*;
import Vista.*;
import java.util.*;

/**
 *
 * @author tinar
 */
public class MediosCtrl {

    private List<Medios> medSw = new ArrayList<>();
    private MediosDB mdb = new MediosDB();
    private List<MediosDB> deDB = new ArrayList<>();
    private static List<Medios> medios = new ArrayList<>();
    private List<MediosTableFormat> mediosForTable = new ArrayList<>();

    public void buscarMedios(List<MediosDB> m) {
        medSw.clear();
        if(medios.isEmpty())
            cargarMedios();

        for(MediosDB x : m){
            FormatoDB f = new FormatoDB();

            String formato = f.fetchFormatoByID(x.getFormid());
            boolean estaEnDepo = x.isMedioEnDepo(x.getId());

            Ubicaciones ubicacion = new UbicacionesCtrl().fetchUbicacion(x.getUbic());
            medSw.add(findMedio(x.getId()));
        }
    }

    public void cargarMedios() {
        medios.clear();
        deDB = mdb.read("Medios");
        for(MediosDB x : deDB){
            FormatoDB f = new FormatoDB();

            String formato = f.fetchFormatoByID(x.getFormid());
            boolean estaEnDepo = x.isMedioEnDepo(x.getId());

            Ubicaciones ubicacion = new UbicacionesCtrl().fetchUbicacion(x.getUbic());
            Medios nuevo = new Medios(x.getId(), x.getNombre(), formato, x.isCaja(),
                    x.isManual(), x.getOrigen(), ubicacion, estaEnDepo, x.getImagen(),
                    x.getObserv(), x.getPartes());
            medios.add(nuevo);

        }
    }

    public void formatMediosForTable(){
        if(medios.isEmpty())
            cargarMedios();
        for(Medios m : medios){
            String isOrig = m.getOrigen()==1?"Sí" : "No";
            String enDep = m.isEnDepo()?"Sí":"No";

            mediosForTable.add(new MediosTableFormat(m.getCodigo(), m.getNombre(), m.getFormato(), isOrig, m.getUbiDepo().getId(),enDep, m.getCopias().size()));
        }
    }

    public Medios findMedio(String codigo){
       for(Medios x : medios){
           if(x.getCodigo().equalsIgnoreCase(codigo))
               return x;
       }
       return null;
    }

     public boolean altaMedio(String codigo, String nombre, int formId, boolean caja,
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
        meDB.setFormid(formId);
        meDB.setOrigen(origen);
        meDB.write();
        boolean todoOK = true;
        for(Software s : soft){
            meDB.setSwid(s.getCodigo());
            todoOK = meDB.asociarMedioASoftware();
        }
        if(ubiDepo==null)
            ubiDepo = new UbicacionesCtrl().fetchUbicacion("NOASIG");
        meDB.setUbic(ubiDepo.getId());
        meDB.setEnDepo(enDepo);
        todoOK = meDB.asociarUbicacionAMedio();

        return todoOK;
    }

     public boolean modMedio(String nuevoid, String id, String nombre, int formid, boolean caja,
        boolean manual, int origen, Ubicaciones ubiDepo, boolean enDepo,
        String imagen, String observ, int partes, List<Software> soft){
        System.out.println("Controller.MediosCtrl.modMedio()");
        MediosDB meDB = new MediosDB();
        meDB.connect();
        meDB.setNuevoId(nuevoid);
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

        boolean todoOK = false;
        SoftwareCtrl sctrl = new SoftwareCtrl();
        sctrl.softwareDeMedio(id);
        Set<Software> borrados = findUnCommon(sctrl.getSwDeMed(), soft);
        Iterator<Software> it = borrados.iterator();
        while(it.hasNext()){
            Software x = it.next();
            meDB.setId(nuevoid);
            meDB.setSwid(x.getCodigo());
            todoOK = meDB.desasociarMedioASoftware();
        }
        if(todoOK){
            Set<Software> agregados = findUnCommon(soft, sctrl.getSwDeMed());
            Iterator<Software> it2 = agregados.iterator();
            while(it2.hasNext()){
                Software x = it2.next();
                meDB.setId(nuevoid);
                meDB.setSwid(x.getCodigo());
                todoOK = meDB.asociarMedioASoftware();
            }
        }else{System.err.println("Error modif software borrado");}
        Medios m = findMedio(id);

        if(todoOK){

            if(ubiDepo==null)
                ubiDepo = new UbicacionesCtrl().fetchUbicacion("NOASIG");

            meDB.setUbic(m.getUbiDepo().getId());
            meDB.setEnDepo(m.isEnDepo());
            todoOK = meDB.desasociarUbicacionAMedio();

        }else{System.err.println("Error modif software agregado");}

        if(todoOK){
            meDB.setUbic(ubiDepo.getId());
            meDB.setEnDepo(enDepo);
            todoOK = meDB.asociarUbicacionAMedio();
        }else{System.err.println("Error modif ubicacion");}
        return todoOK;
    }

     public void elimMedio(String codigo){
        medios.remove(findMedio(codigo));
        MediosDB meDB  = new MediosDB();
        meDB.connect();
        meDB.setId(codigo);
        meDB.delete();
    }

    public Set<Software> findUnCommon(List<Software> listaUno , List<Software> listaDos){

        Set<Software> a = new HashSet<>(listaUno);
        Set<Software> b = new HashSet<>(listaDos);

        Set<Software> result = new HashSet<>();
        for (Software el: a) {
          if (!b.contains(el)) {
            result.add(el);
          }
        }
        return result;
    }
    public List<Medios> getMedSw() {        return medSw;    }
    public void setMedSw(List<Medios> medSw) {        this.medSw = medSw;    }
    public MediosDB getMdb() {   return mdb;    }
    public void setMdb(MediosDB mdb) {        this.mdb = mdb;    }
    public List<MediosDB> getDeDB() {        return deDB;    }
    public void setDeDB(List<MediosDB> deDB) {        this.deDB = deDB;    }
    public static List<Medios> getMedios() {        return medios;    }
    public static void setMedios(List<Medios> med) {        medios = med;    }
    public List<MediosTableFormat> getMediosForTable() {   return mediosForTable;  }
}
