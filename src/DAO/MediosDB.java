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

    private int id;
    private int formid;
    private boolean caja;
    private String imagen;
    private boolean manual;
    private String nombre;
    private String observ;
    private int partes;
    private int origen;

    public MediosDB() { connect(); }

    public MediosDB(int id, int formid, boolean caja, String imagen, boolean manual, String nombre, String observ, int partes, int origen) {
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
        List<Integer> ids = new ArrayList<>();
        try {

            if(conn.isClosed())
                connect();

            res = instr.executeQuery("SELECT medio_id FROM `Medios_Software` WHERE `sw_id` = " + sw_id);

            while(res.next()){
                ids.add(res.getInt("medio_id"));
            }

            res.close();

            for(Integer x : ids){
                res = instr.executeQuery("Select * from `Medios` AS m WHERE m.medio_id = " + x);
                objDB.add(readResultSet(res));
                res.close();
            }
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error >> medios de software " + sw_id + " :: " + e.getLocalizedMessage());
        }
        return objDB;
    }

    @Override
    public MediosDB readResultSet(ResultSet res) {
        MediosDB obj = null;

         try {

            this.id = res.getInt("medio_id");
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
