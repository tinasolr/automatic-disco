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



    private CopiasDB(int id, String obs, int formid, int swid) {
        this.id = id;
        this.swid = swid;
        this.obs = obs;
        this.formid = formid;
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
            System.err.println("Error >> copias de software " + sw_id + " :: " + e.getLocalizedMessage());
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
            System.err.println("Error >> lectura >> tabla Copias :: " + ex.getLocalizedMessage());
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
