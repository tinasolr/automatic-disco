/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.sql.*;

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

    public ExtrasDB() {connect();}

    public ExtrasDB(String nombre, String version, String descrip, int partes) {
        this.nombre = nombre;
        this.version = version;
        this.descrip = descrip;
        this.partes = partes;
    }

    @Override
    public ExtrasDB readResultSet(ResultSet res) {
        ExtrasDB extra = null;

        try {

            this.nombre = res.getString("extra_nom");
            this.version = res.getString("extra_vers");
            this.descrip = res.getString("extra_descr");
            this.partes = res.getInt("extra_partes");

            extra = new ExtrasDB(nombre, version, descrip, partes);

        } catch (SQLException ex) {
            System.out.println("Problema en la lectura de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }

        return extra;
    }

    @Override
    public void executePStoWrite(Connection con) {
        try {

            PreparedStatement ps = con.prepareStatement("INSERT INTO "
                    + "Extras "
                    + "(extra_nom,extra_vers,extra_descr,extra_partes,sw_id) "
                    + "VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, version);
            ps.setString(3, descrip);
            ps.setInt(4, partes);
            ps.setInt(5, swid);

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("Problema en la escritura de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }
    }


    @Override
    public String executePStoSearch(Connection conn) {
        String result = null;
        try {

            PreparedStatement ps = conn.prepareStatement("Select extra_id "
                    + "FROM Extras "
                    + "WHERE extra_nombre LIKE ?");

            ps.setString(1, nombre);

            ResultSet res = ps.executeQuery();
            res.next();
            int r = res.getInt("extra_id");
            result = String.valueOf(r);
        } catch (SQLException ex) {
            System.out.println("Problema en la escritura de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }
        return result;
    }

    public String getNombre() {  return nombre;  }
    public void setNombre(String nombre) {  this.nombre = nombre;  }

    public String getVersion() { return version;   }
    public void setVersion(String version) { this.version = version;  }

    public String getDescrip() {  return descrip;  }
    public void setDescrip(String descrip) {  this.descrip = descrip;  }

    public int getPartes() {  return partes;  }
    public void setPartes(int partes) {  this.partes = partes;   }

    @Override
    public String toString() {
        return "ExtrasDB{" + "nombre=" + nombre + ", version=" + version + ", descrip=" + descrip + ", partes=" + partes + '}';
    }

}
