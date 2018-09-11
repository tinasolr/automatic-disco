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
    private String newNombre;

    public SistOpDB(String nombre) {
        this.nombre = nombre;
    }

    public SistOpDB(){connect();}

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
         try {
            CallableStatement sp = conn.prepareCall("{call alta_so(?)}");

            sp.setString(1, nombre);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la escritura de una fila de la tabla SistOperativos :: " + ex.getLocalizedMessage());
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
            System.err.println("Problema en la modificación de una fila de la tabla SistOperativos :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeDelete() {
        try {
            CallableStatement sp = conn.prepareCall("{call elim_so(?)}");

            sp.setString(1, nombre);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la modificación de una fila de la tabla SistOperativos :: " + ex.getLocalizedMessage());
        }    }

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
