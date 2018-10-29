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
public class ExtrasDB extends DBObject{

    private String nombre;
    private String version;
    private String descrip;
    private int partes;
    private int swid;
    private int id;

    public ExtrasDB() {}

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
            System.err.println("Read >> Extras :: " + ex.getLocalizedMessage());
        }

        return extra;
    }

    @Override
    //ESCRIBIR EN LA BD CON UN STORED PROCEDURE - LO USA WRITE() DE DBOBJECT
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("{call alta_extra(?,?,?,?,?)}");
            sp.setString(1, nombre);
            sp.setString(2, version);
            sp.setString(3, descrip);
            sp.setInt(4, partes);
            sp.setInt(5, swid);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Write >> Extras :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public String executeSearch() {
        String result = null;
        ResultSet res = null;
        try {

            CallableStatement storedProc = conn.prepareCall("{call get_idextra(?,?)}");
            storedProc.setInt(1, swid);
            storedProc.setString(2, nombre);
            res = storedProc.executeQuery();
            res.next();
            int r = res.getInt(1);
            result = String.valueOf(r);

              res.close();
        } catch (SQLException ex) {
            System.err.println("Search >> Extras ::  :: " + ex.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public void executeUpdate() {
        try {

            CallableStatement sp = conn.prepareCall("{call mod_extra(?,?,?,?,?,?)}");

            sp.setString(1, nombre);
            sp.setString(2, version);
            sp.setString(3, descrip);
            sp.setInt(4, partes);
            sp.setInt(5, swid);
            sp.setInt(6, id);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Update >> Extras :: " + ex.getLocalizedMessage());
        }
    }

    public List<ExtrasDB> extrasDeSoftware(int sw_id) {
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
            System.err.println("GET >> Extras de software " + sw_id + " :: " + e.getLocalizedMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ExtrasDB.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            System.err.println("Delete >> Extras :: " + ex.getLocalizedMessage());
        }
    }

    public void deleteAllExtras() {
        try {

            if(conn == null || conn.isClosed())
                connect();

            executeDeleteAll();

            conn.close();

        } catch (SQLException e) {
            System.err.println("deleteAllExtras() :: " + e.getMessage());
        } finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ExtrasDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void executeDeleteAll(){
        try{
            CallableStatement storedProc = conn.prepareCall("{call eliminar_todos_extra(?)}");
            storedProc.setInt(1, swid);
            storedProc.executeUpdate();
        }catch(SQLException ex){
            System.err.println("Delete >> Software id " + swid + " >> Tabla Extras :: " + ex.getLocalizedMessage());
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
