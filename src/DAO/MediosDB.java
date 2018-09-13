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

    public MediosDB() { connect(); }

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

            if(conn.isClosed())
                connect();

            res = instr.executeQuery("Select medio_id from `medios_software` AS s WHERE s.sw_id = " + sw_id);

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
            System.err.println("Error >> Lectura >> Medios de Software " + sw_id + " :: " + e.getLocalizedMessage());
        }
        return objDB;
    }

    public void asociarMedioASoftware(){
        try {
            CallableStatement sp = conn.prepareCall("{CALL asociar_medio_software(?, ?)}");

            sp.setString(1, id);
            sp.setInt(2, swid);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Escritura >> tabla Medios_Software :: " + ex.getLocalizedMessage());
        }
    }

        public void desasociarMedioASoftware(){
        try {
            CallableStatement sp = conn.prepareCall("{CALL desasociar_medio_software(?, ?)}");

            sp.setString(1, id);
            sp.setInt(2, swid);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Eliminar >> tabla Medios_Software :: " + ex.getLocalizedMessage());
        }
    }

    public void asociarUbicacionAMedio(){
        try {
            CallableStatement sp = conn.prepareCall("{CALL asociar_medio_ubicacion(?, ?, ?)}");

            sp.setString(1, id);
            sp.setString(2, ubic);
            sp.setBoolean(3, enDepo);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Escritura >> tabla Medios_Software :: " + ex.getLocalizedMessage());
        }
    }

    public void desasociarUbicacionAMedio(){
        try {
            CallableStatement sp = conn.prepareCall("{CALL desasociar_medio_ubicacion(?, ?)}");

            sp.setString(1, id);
            sp.setString(2, ubic);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Eliminar >> tabla Medios_Software :: " + ex.getLocalizedMessage());
        }
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

    @Override
    public void executeWrite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void executeUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void executeDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String executeSearch() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
