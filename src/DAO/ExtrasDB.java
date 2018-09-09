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
public class ExtrasDB extends DBObject{

    private String nombre;
    private String version;
    private String descrip;
    private int partes;
    private int swid;
    private int id;

    public ExtrasDB() {connect();}

    public ExtrasDB(String nombre, String version, String descrip, int partes) {
        this.nombre = nombre;
        this.version = version;
        this.descrip = descrip;
        this.partes = partes;
    }

    @Override
    //LEER TODOS LOS EXTRAS - LA USA LA FUNCION READ() DE DBOBJECT
    public ExtrasDB readResultSet(ResultSet res) {
        ExtrasDB extra = null;

        try {

            this.nombre = res.getString("extra_nom");
            this.version = res.getString("extra_vers");
            this.descrip = res.getString("extra_descr");
            this.partes = res.getInt("extra_partes");

            extra = new ExtrasDB(nombre, version, descrip, partes);

        } catch (SQLException ex) {
            System.err.println("Problema en la lectura de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }

        return extra;
    }

    @Override
    //ESCRIBIR EN LA BD CON UN STORED PROCEDURE - LO USA WRITE() DE DBOBJECT
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("{call alta_extra(?,?,?,?,?)}");
//            PreparedStatement ps = conn.prepareStatement("INSERT INTO "
//                    + "Extras(extra_nom,extra_vers,extra_descr,extra_partes,sw_id) "
//                    + "VALUES (?,?,?,?,?)");
            sp.setString(1, nombre);
            sp.setString(2, version);
            sp.setString(3, descrip);
            sp.setInt(4, partes);
            sp.setInt(5, swid);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la escritura de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }
    }


    @Override
    public String executeSearch() {
        String result = null;
        ResultSet res = null;
        try {

            CallableStatement storedProc = conn.prepareCall("{call search_idextra(?,?)}");
            storedProc.setInt(1, swid);
            storedProc.setString(2, nombre);
            res = storedProc.executeQuery();
            res.next();
            int r = res.getInt(1);
            result = String.valueOf(r);

              res.close();
        } catch (SQLException ex) {
            System.err.println("Problema en la busqueda de la tabla Extras :: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void executeUpdate() {
        try {

            CallableStatement sp = conn.prepareCall("{call mod_extra(?,?,?,?,?,?)}");
//            PreparedStatement ps = conn.prepareStatement("UPDATE 'Extras' "
//                    + "SET 'extra_nom' = ?,'extra_vers' = ?,'extra_descr' = ?,'extra_partes' = ? "
//                    + "WHERE 'sw_id' = ? AND 'extra_id' = ?");

            sp.setString(1, nombre);
            sp.setString(2, version);
            sp.setString(3, descrip);
            sp.setInt(4, partes);
            sp.setInt(5, swid);
            sp.setInt(6, id);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la modificación de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }
    }

    public List<ExtrasDB> extrasDeSoftware(int sw_id){
        ResultSet res = null;
        List<ExtrasDB> exDB = new ArrayList<>();

        try {

            if(conn.isClosed())
                connect();

            res = instr.executeQuery("SELECT * FROM Extras WHERE sw_id = " + sw_id);

            while(res.next()){
                exDB.add(readResultSet(res));
            }

            conn.close();

        } catch (SQLException e) {
            System.err.println("Error >> extras de software " + sw_id + " :: " + e.getLocalizedMessage());
        }
        return exDB;
    }

        @Override
    public void executeDelete() {
        try {

            CallableStatement storedProc = conn.prepareCall("{call eliminar_extra(?,?)}");
            storedProc.setInt(1, id);
            storedProc.setInt(2, swid);
            storedProc.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la baja de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }
    }

    public void deleteAllExtras(){
        try {

            if(conn.isClosed())
                connect();

            executeDeleteAll();

            conn.close();

        } catch (SQLException e) {
            System.err.println("BDObject deleteAllExtras() :: " + e.getMessage());
        }
    }

    public void executeDeleteAll(){
        try{
            CallableStatement storedProc = conn.prepareCall("{call eliminar_todos_extra(?)}");
            storedProc.setInt(1, swid);
            storedProc.executeUpdate();
        }catch(SQLException ex){
            System.err.println("Error >> Baja de filas para software id - " + swid + " >> Tabla Extras :: " + ex.getLocalizedMessage());
        }
    }

    public String getNombre() {  return nombre;  }
    public void setNombre(String nombre) {  this.nombre = nombre;  }

    public String getVersion() { return version;   }
    public void setVersion(String version) { this.version = version;  }

    public String getDescrip() {  return descrip;  }
    public void setDescrip(String descrip) {  this.descrip = descrip;  }

    public int getPartes() {  return partes;  }
    public void setPartes(int partes) {  this.partes = partes;   }

    public int getSwid() {return swid;}
    public void setSwid(int swid) {this.swid = swid;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    @Override
    public String toString() {
        return "ExtrasDB{" + "nombre=" + nombre + ", version=" + version + ", descrip=" + descrip + ", partes=" + partes + '}';
    }

}
