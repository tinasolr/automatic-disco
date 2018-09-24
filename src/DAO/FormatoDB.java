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
public class FormatoDB extends DBObject {

    private String formato;
    private String newFormat;

    public FormatoDB(String nombre) {
        this.formato = nombre;
    }

    public FormatoDB(){}

    @Override
    public String readResultSet(ResultSet res) {
        String obj = null;

        try {

            this.formato = res.getString("form_nom");
            obj = formato;

        } catch (SQLException ex) {
            System.err.println("Error >> la lectura >> tabla Formato :: " + ex.getLocalizedMessage());
        }

        return obj;
    }

    @Override
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("{call alta_formato(?)}");

            sp.setString(1, formato);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la escritura de una fila de la tabla Formato :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeUpdate() {
        try {

            CallableStatement sp = conn.prepareCall("{call mod_formato(?, ?)}");

            sp.setString(1, newFormat);
            sp.setString(2, formato);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la modificación de una fila de la tabla Formato :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void executeDelete() {
        try {
            CallableStatement sp = conn.prepareCall("{call elim_formato(?)}");

            sp.setString(1, formato);
            sp.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Problema en la modificación de una fila de la tabla Formato :: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public String executeSearch() {
        try {
            CallableStatement sp = conn.prepareCall("{call find_formatoid(?)}");

            sp.setString(1, formato);
            ResultSet r = sp.executeQuery();
            r.first();
            int id = r.getInt(1);
            return String.valueOf(id);

        } catch (SQLException ex) {
            System.err.println("Search ID >> Formatos :: " + ex.getLocalizedMessage());
        }
        return null;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getNewFormat() {
        return newFormat;
    }

    public void setNewFormat(String newFormat) {
        this.newFormat = newFormat;
    }


}
