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
public class CopiasDB extends DBObject {

    private int id;
    private int swid;
    private int formid;
    private String obs;
    private String ubi;
    private boolean enDepo;


    private CopiasDB(int id, String obs, int formid, int swid) {
        this.id = id;
        this.swid = swid;
        this.obs = obs;
        this.formid = formid;
    }

        public void asociarUbicacionACopia(){
        try {
            CallableStatement sp = conn.prepareCall("{CALL asociar_copia_ubicacion(?, ?, ?)}");

            sp.setInt(1, id);
            sp.setString(2, ubi);
            sp.setBoolean(3, enDepo);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Write >> tabla Copia_Ubic :: " + ex.getLocalizedMessage());
        }
    }

    public void desasociarUbicacionACopia(){
        try {
            CallableStatement sp = conn.prepareCall("{CALL desasociar_copia_ubicacion(?, ?)}");

            sp.setInt(1, id);
            sp.setString(2, ubi);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Delete >> tabla Copia_Ubic :: " + ex.getLocalizedMessage());
        }
    }

    public CopiasDB() { connect(); }

    public List<CopiasDB> copiasDeSoftware(int sw_id){
        ResultSet res = null;
        List<CopiasDB> objDB = new ArrayList<>();

        try {

            if(conn.isClosed())
                connect();

            res = instr.executeQuery("SELECT * FROM `Copias` WHERE `sw_id` = " + sw_id);

            while(res.next()){
                objDB.add(readResultSet(res));
            }

            conn.close();

        } catch (SQLException e) {
            System.err.println("Read >> Copias >> Software " + sw_id + " :: " + e.getLocalizedMessage());
        }
        return objDB;
    }

    @Override
    public CopiasDB readResultSet(ResultSet res) {
        CopiasDB obj = null;

        try {

            this.id = res.getInt("cp_id");
            this.obs = res.getString("cp_obs");
            this.formid = res.getInt("form_id");
            this.swid = res.getInt("sw_id");

            obj = new CopiasDB(id, obs, formid, swid);

        } catch (SQLException ex) {
            System.err.println("Read >> Copias :: " + ex.getLocalizedMessage());
        }

        return obj;
    }

    @Override
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("{CALL alta_copia(?, ?, ?)}");

            sp.setInt(1, swid);
            sp.setInt(2, formid);
            sp.setString(3, obs);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Write >> Copias :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeUpdate() {
       try {
            CallableStatement sp = conn.prepareCall("{CALL mod_copia (?, ?, ?, ?)}");

            sp.setInt(1, id);
            sp.setInt(2, swid);
            sp.setInt(3, formid);
            sp.setString(4, obs);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Update >> Copias :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeDelete() {
        try {
            CallableStatement sp = conn.prepareCall("{CALL elim_copia (?)}");

            sp.setInt(1, swid);
            sp.setInt(2, formid);
            sp.setString(3, obs);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Delete >> Copias :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public String executeSearch() {
        try {
            CallableStatement sp = conn.prepareCall("{CALL get_idcopia (?, ?)}");

            sp.setInt(1, swid);
            sp.setInt(2, formid);

            ResultSet r = sp.executeQuery();
            r.first();
            int id = r.getInt(1);
            return String.valueOf(id);
        } catch (SQLException ex) {
            System.err.println("Search ID >> Copias :: " + ex.getLocalizedMessage());
        }
        return null;
    }
}
