/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author tinar
 */
public class SistOpDB extends DBObject{

    private String nombre;
    private String newNombre;

    public SistOpDB(String nombre) {
        this.nombre = nombre;
    }

    public SistOpDB(){}

    public List<SistOpDB> sistopDeSoftware(int sw_id){
        ResultSet res = null;
        List<Integer> ids = new ArrayList<>();
        List<SistOpDB> objDB = new ArrayList<>();

        try {

            if(conn == null || conn.isClosed())
                connect();

            res = instr.executeQuery("Select so_id from `SistOperativos_Software` AS s WHERE s.sw_id = " + sw_id);

            while(res.next()){
                ids.add(res.getInt("so_id"));
            }

            res.close();
            res = null;
            for(Integer x : ids){
                res = instr.executeQuery("Select * from `SistOperativos` AS s WHERE s.so_id = " + x);
                res.next();
                objDB.add(readResultSet(res));
                res.close();
            }

            conn.close();

        } catch (SQLException e) {
            System.err.println("GET >> so de software " + sw_id + " :: " + e.getLocalizedMessage());
        } finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormatoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return objDB;
    }

    @Override
    public SistOpDB readResultSet(ResultSet res) {
        SistOpDB obj = null;

        try {

            this.nombre = res.getString("so_nom");
            obj = new SistOpDB(nombre);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Read >> SistOperativos :: " + ex.getLocalizedMessage());
        }

        return obj;
    }

    @Override
    public void executeWrite() {
         try {
            CallableStatement sp = conn.prepareCall("{call alta_so(?)}");

            sp.setString(1, nombre);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Write >> SistOperativos :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeUpdate() {
        try {
            CallableStatement sp = conn.prepareCall("{call mod_so(?, ?)}");

            sp.setString(1, newNombre);
            sp.setString(2, nombre);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Update >> SistOperativos :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeDelete() {
        try {
            CallableStatement sp = conn.prepareCall("{call elim_so(?)}");

            sp.setString(1, nombre);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Delete >> SistOperativos :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public String executeSearch() {
        try {
            CallableStatement sp = conn.prepareCall("{call get_idSo(?)}");

            sp.setString(1, nombre);
            ResultSet r = sp.executeQuery();
            r.first();
            int id = r.getInt(1);
            return String.valueOf(id);

        } catch (SQLException ex) {
            System.err.println("Search ID >> SistOperativos :: " + ex.getLocalizedMessage());
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNewNombre() {
        return newNombre;
    }

    public void setNewNombre(String newNombre) {
        this.newNombre = newNombre;
    }


}
