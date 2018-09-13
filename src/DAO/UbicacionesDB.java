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
public class UbicacionesDB extends DBObject {

    private String codUbi;
    private String obsUbi;
    private String newCod;
    private String newObs;

    public UbicacionesDB() {
    }

    public UbicacionesDB(String codUbi, String obsUbi) {
        this.codUbi = codUbi;
        this.obsUbi = obsUbi;
    }

    @Override
    public UbicacionesDB readResultSet(ResultSet res) {
        UbicacionesDB obj = null;

        try {

            this.codUbi = res.getString("ubi_id");
            this.obsUbi = res.getString("ubi_obs");

            obj = new UbicacionesDB(codUbi, obsUbi);

        } catch (SQLException ex) {
            System.err.println("Lectura >> tabla Ubicaciones :: " + ex.getLocalizedMessage());
        }

        return obj;
    }

    @Override
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("{call alta_ubicacion(?,?)}");

            sp.setString(1, codUbi);
            sp.setString(2, obsUbi);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Escritura >> tabla Ubicaciones :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeUpdate() {
        try {
            CallableStatement sp = conn.prepareCall("{CALL mod_ubicacion(?, ?, ?)}");

            sp.setString(1, codUbi);
            sp.setString(2, newCod);
            sp.setString(3, newObs);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Modificacion >> tabla Ubicaciones :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeDelete() {
        try {
            CallableStatement sp = conn.prepareCall("{CALL eliminar_ubicacion(?)}");

            sp.setString(1, codUbi);

            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Eliminacion >> tabla Ubicaciones :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public String executeSearch() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCodUbi() {
        return codUbi;
    }

    public void setCodUbi(String codUbi) {
        this.codUbi = codUbi;
    }

    public String getObsUbi() {
        return obsUbi;
    }

    public void setObsUbi(String obsUbi) {
        this.obsUbi = obsUbi;
    }

    public String getNewCod() {
        return newCod;
    }

    public void setNewCod(String newCod) {
        this.newCod = newCod;
    }

    public String getNewObs() {
        return newObs;
    }

    public void setNewObs(String newObs) {
        this.newObs = newObs;
    }

}
