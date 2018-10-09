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
    private int medioid;
    private int formid;
    private String obs;
    private String ubi;
    private boolean enDepo;


    private CopiasDB(int id, String obs, int formid, int swid) {
        this.id = id;
        this.medioid = swid;
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

    public CopiasDB() {}

    public List<CopiasDB> copiasDeSoftware(int sw_id){
        ResultSet res = null;
        List<CopiasDB> objDB = new ArrayList<>();

        try {

            if(conn == null || conn.isClosed())
                connect();

            res = instr.executeQuery("SELECT * FROM `Copias` WHERE `medio_id` = " + medioid);

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

            this.id = res.getInt("copia_id");
            this.obs = res.getString("cp_obs");
            this.formid = res.getInt("form_id");
            this.medioid = res.getInt("medio_id");

            obj = new CopiasDB(id, obs, formid, medioid);

        } catch (SQLException ex) {
            System.err.println("Read >> Copias :: " + ex.getLocalizedMessage());
        }

        return obj;
    }

    @Override
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("{CALL alta_copia(?, ?, ?)}");

            sp.setInt(1, medioid);
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
            sp.setInt(2, medioid);
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

            sp.setInt(1, medioid);
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

            sp.setInt(1, medioid);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedioid() {
        return medioid;
    }

    public void setMedioid(int medioid) {
        this.medioid = medioid;
    }

    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getUbi() {
        return ubi;
    }

    public void setUbi(String ubi) {
        this.ubi = ubi;
    }

    public boolean isEnDepo() {
        return enDepo;
    }

    public void setEnDepo(boolean enDepo) {
        this.enDepo = enDepo;
    }
    
    
    
    
    
}
