/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.sql.*;
import java.util.*;

/**
 *
 * @author tinar
 */
public class MediosDB extends DBObject{

    private String id;
    private String nuevoId;
    private int formid;
    private boolean caja;
    private String imagen;
    private boolean manual;
    private String nombre;
    private String observ;
    private int partes;
    private int origen;
    private int swid;
    private String ubic;
    private boolean enDepo;

    public MediosDB() { }

    public MediosDB(String id, int formid, boolean caja, String imagen, boolean manual, String nombre, String observ, int partes, int origen) {
        this.id = id;
        this.formid = formid;
        this.caja = caja;
        this.imagen = imagen;
        this.manual = manual;
        this.nombre = nombre;
        this.observ = observ;
        this.partes = partes;
        this.origen = origen;
    }

    public List<MediosDB> mediosDeSoftware(int sw_id){
        ResultSet res = null;
        List<MediosDB> objDB = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        try {
            if(conn == null || conn.isClosed())
                connect();

            res = instr.executeQuery("Select medio_id from Medios_Software AS s WHERE s.sw_id = " + sw_id);

            while(res.next()){
                ids.add(res.getString("medio_id"));
            }

            res.close();
            res = null;
            for(String x : ids){
                res = instr.executeQuery("Select * from `medios` AS s WHERE s.medio_id LIKE '" + x + "'");
                res.next();
                objDB.add(readResultSet(res));
                res.close();
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error >> Lectura >> Medios de Software " + sw_id + " :: " + e.getLocalizedMessage());
        }
        return objDB;
    }

    public boolean asociarMedioASoftware(){
        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("{CALL asociar_medio_software(?, ?)}");

            sp.setString(1, id);
            sp.setInt(2, swid);

            sp.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            System.err.println("Escritura >> tabla Medios_Software :: " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

        public boolean desasociarMedioASoftware(){
        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("{CALL desasociar_medio_software(?, ?)}");

            sp.setString(1, id);
            sp.setInt(2, swid);

            sp.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            System.err.println("Eliminar >> tabla Medios_Software :: " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public boolean asociarUbicacionAMedio(){
        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("{CALL asociar_medio_ubicacion(?, ?, ?)}");

            sp.setString(1, id);
            sp.setString(2, ubic);
            sp.setBoolean(3, enDepo);

            sp.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Escritura >> tabla Medio_Ubic :: " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public boolean desasociarUbicacionAMedio(){
        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("{CALL desasociar_medio_ubicacion(?, ?)}");

            sp.setString(1, id);
            sp.setString(2, ubic);

            sp.executeUpdate();

            conn.close();
        } catch (SQLException ex) {
            System.err.println("Eliminar >> tabla Medio_Ubic :: " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public MediosDB readResultSet(ResultSet res) {
        MediosDB obj = null;

         try {

            this.id = res.getString("medio_id");
            this.formid = res.getInt("form_id");
            this.caja = res.getBoolean("medio_caja");
            this.imagen = res.getString("medio_imagen");
            this.manual = res.getBoolean("medio_manual");
            this.observ = res.getString("medio_obs");
            this.partes = res.getInt("medio_partes");
            this.origen = res.getInt("origen_id");
            this.nombre = res.getString("medio_nom");

            obj = new MediosDB(id, formid, caja, imagen, manual, nombre, observ, partes, origen);

        } catch (SQLException ex) {
            System.err.println("Error >> la lectura >> tabla Medios :: " + ex.getLocalizedMessage());
        }

        return obj;
    }

    public boolean isMedioEnDepo(String codigo) {

        boolean esta = false;
        ResultSet rs = null;
        try {

            if(conn == null || conn.isClosed())
                connect();

            rs = instr.executeQuery("SELECT ubi_id, medio_enDep FROM medio_ubic WHERE medio_id LIKE '" + id + "'");
            rs.first();
            this.ubic = rs.getString(1);
            esta = rs.getBoolean(2);

            conn.close();

        } catch (SQLException e) {
            System.out.println("MediosDB >> fetchUnMedio() :: " + e.getMessage());
        }

        return esta;
    }

    @Override
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("{call alta_medio (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            sp.setString(1, id);
            sp.setString(2, nombre);
            sp.setInt(3,partes);
            sp.setBoolean(4,manual);
            sp.setBoolean(5,caja);
            if(imagen != null && !imagen.isEmpty())
                sp.setString(6, imagen);
            else
                sp.setString(6, "no-image-available.png");
            sp.setString(7, observ);
            sp.setInt(8,formid);
            sp.setInt(9,origen);
            sp.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Problema en la escritura de una fila de la tabla Medios :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeUpdate() {
        try {

            CallableStatement sp = conn.prepareCall("{call mod_medio (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            sp.setString(1, nuevoId);
            sp.setString(2, id);
            sp.setString(3, nombre);
            sp.setInt(4,partes);
            sp.setBoolean(5,manual);
            sp.setBoolean(6,caja);
            if(imagen != null && !imagen.isEmpty())
                sp.setString(7, imagen);
            else
                sp.setString(7, "no-image-available.png");
            sp.setString(8, observ);
            sp.setInt(9,formid);
            sp.setInt(10,origen);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Update >> Medios :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeDelete() {

        try {
            CallableStatement sp = conn.prepareCall("{call elim_medio (?)}");
            sp.setString(1, id);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Delete >> Medios :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public String executeSearch() {
        String result = null;
        ResultSet res = null;
        try {
            CallableStatement sp = conn.prepareCall("{call find_medioid(?,?)}");
            sp.setString(1, nombre);
            res = sp.executeQuery();
            res.first();
            int r = res.getInt(1);
            result = String.valueOf(r);
            res.close();
        } catch (SQLException ex) {
            System.err.println("Problema en la busqueda de la tabla Medios :: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }

    public boolean isCaja() {
        return caja;
    }

    public void setCaja(boolean caja) {
        this.caja = caja;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public int getPartes() {
        return partes;
    }

    public void setPartes(int partes) {
        this.partes = partes;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getSwid() {
        return swid;
    }

    public void setSwid(int swid) {
        this.swid = swid;
    }

    public String getUbic() {
        return ubic;
    }

    public void setUbic(String ubic) {
        this.ubic = ubic;
    }

    public boolean isEnDepo() {
        return enDepo;
    }

    public void setEnDepo(boolean enDepo) {
        this.enDepo = enDepo;
    }

    public String getNuevoId() {
        return nuevoId;
    }

    public void setNuevoId(String nuevoId) {
        this.nuevoId = nuevoId;
    }





}
