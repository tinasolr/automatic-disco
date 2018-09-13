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
public class SoftwareDB extends DBObject{

    private int codigo;
    private String nombre;
    private String version;
    private String nuevoNom;
    private String nuevoVers;
    private String soNom;

    public SoftwareDB() { connect();  }

    public SoftwareDB(int codigo, String nombre, String version) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.version = version;
    }

    @Override
    public SoftwareDB readResultSet(ResultSet res) {
        SoftwareDB soft = null;

        try {

            this.codigo = res.getInt("sw_id");
            this.nombre = res.getString("sw_nom");
            this.version = res.getString("sw_vers");

            soft = new SoftwareDB(codigo, nombre, version);

        } catch (SQLException ex) {
            System.err.println("Problema en la lectura de una fila de la tabla Software :: " + ex.getLocalizedMessage());
        }

        return soft;
    }

    @Override
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("{call alta_software(?,?)}");
            sp.setString(1, nombre);
            sp.setString(2, version);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la escritura de una fila de la tabla Software :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeUpdate() {
         try {
            CallableStatement sp = conn.prepareCall("{call mod_software(?,?,?,?)}");
            sp.setString(1, nombre);
            sp.setString(2, version);
            sp.setString(3, nuevoNom);
            sp.setString(4, nuevoVers);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la modificacion de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }
    }

    /**
     * Eliminar sofware según el código ingresado
     */
    @Override
    public void executeDelete() {
        try {
            CallableStatement sp = conn.prepareCall("{call eliminar_software(?)}");
            sp.setInt(1, codigo);

            sp.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Problema en la eliminación de una fila de la tabla Extras :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public String executeSearch() {
        String result = null;
        ResultSet res = null;
        try {

            //CallableStatement storedProc = conn.prepareCall("{call find_softwareid(<nombre de software>, <version de software>)}");
            CallableStatement storedProc = conn.prepareCall("{call find_softwareid(?,?)}");
            storedProc.setString(1, nombre);
            storedProc.setString(2, version);
            res = storedProc.executeQuery();
            res.next();
            int r = res.getInt(1);
            result = String.valueOf(r);

              res.close();
        } catch (SQLException ex) {
            System.err.println("Problema en la busqueda de la tabla Software :: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return result;
    }
  
    public void deleteSOSw(){
        ResultSet res = null;
        try {

            if(conn.isClosed())
                connect();

            res = instr.executeQuery("SELECT so_id FROM sistoperativos WHERE `so_nom` = '" + soNom + "'");
            res.next();
            int cod = res.getInt("so_id");

            res.close();

            instr.execute("DELETE FROM sistoperativos_software WHERE `so_id` = " + cod + " AND `sw_id` = " + codigo);

            conn.close();
            System.out.println("Connection closed.");

        } catch (SQLException e) {
            System.out.println("BDObject delete() :: " + e.getMessage());
        }
    }

    public void insertSOSw(){
        ResultSet res = null;
        try {

            if(conn.isClosed())
                connect();

//            CallableStatement ps = conn.prepareCall("{call search_soid_byname(?)}");
            PreparedStatement ps = conn.prepareStatement("SELECT `so_id` FROM `SistOperativos` WHERE `so_nom` = ?");
            ps.setString(1, soNom);

            res = ps.executeQuery();
            int cod = 0;
            if(res.last())
                cod = res.getInt(1);
            if(cod>0)
                instr.executeUpdate("INSERT INTO SistOperativos_Software(so_id, sw_id) VALUES (" + cod + ", " + codigo + ")");

            printResultSet(res);

            res.close();
            conn.close();
            System.out.println("Connection closed.");

        } catch (SQLException e) {
            System.out.println("BDObject inserSOSw() :: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getCodigo() {return codigo;}
    public void setCodigo(int codigo) {    this.codigo = codigo;}
    public String getNombre() {    return nombre;}
    public void setNombre(String nombre) {    this.nombre = nombre;}
    public String getVersion() {    return version;}
    public void setVersion(String version) {   this.version = version;}
    public String getNuevoNom() {        return nuevoNom;    }
    public void setNuevoNom(String nuevoNom) {        this.nuevoNom = nuevoNom;    }
    public String getNuevoVers() {        return nuevoVers;    }
    public void setNuevoVers(String nuevoVers) {        this.nuevoVers = nuevoVers;   }
    public String getSoNom() {        return soNom;    }
    public void setSoNom(String soNom) {        this.soNom = soNom;   }
}
