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
public class SistOpDB extends DBObject{

    private String nombre;

    public SistOpDB(String nombre) {
        this.nombre = nombre;
    }

    public SistOpDB(){}

    public List<SistOpDB> sistopDeSoftware(int sw_id){
        ResultSet res = null;
        List<Integer> ids = new ArrayList<>();
        List<SistOpDB> objDB = new ArrayList<>();

        try {

            if(conn.isClosed())
                connect();

            res = instr.executeQuery("Select so_id from `SistOperativos_Software` AS s WHERE s.sw_id = " + sw_id);

            while(res.next()){
                ids.add(res.getInt("so_id"));
            }

            res.close();

            for(Integer x : ids){
                res = instr.executeQuery("Select * from `SistOperativos` AS s WHERE s.so_id = " + x);
                objDB.add(readResultSet(res));
                res.close();
            }

            conn.close();

        } catch (SQLException e) {
            System.err.println("Error >> copias de software " + sw_id + " :: " + e.getLocalizedMessage());
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
            System.err.println("Error >> la lectura >> tabla SistOperativos :: " + ex.getLocalizedMessage());
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
