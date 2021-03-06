 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.sql.*;
import java.util.logging.*;


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
            int id = 0;
            if(r.next())
                r.getInt(1);
            return String.valueOf(id);

        } catch (SQLException ex) {
            System.err.println("Search ID >> Formatos :: " + ex.getLocalizedMessage());
        }
        return null;
    }

    public String find_formatonom(int id)
    {
        try {
            CallableStatement sp = conn.prepareCall("{call find_formatonom(?)}");

            sp.setInt(1, id);
            ResultSet r = sp.executeQuery();
            r.first();
            return  r.getString(1);

        } catch (SQLException ex) {
            System.err.println("Search Nombre >> Formatos :: " + ex.getLocalizedMessage());
        } finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormatoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }



    public String fetchFormatoByID(int id) {
        try {

            if(conn == null || conn.isClosed())
                connect();

            PreparedStatement sp = conn.prepareStatement("SELECT form_nom FROM Formatos WHERE form_id=?");

            sp.setInt(1, id);
            ResultSet r = sp.executeQuery();
            r.first();
            String nombre = r.getString(1);

            conn.close();
            return nombre;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormatoDB.class.getName()).log(Level.SEVERE, null, ex);
            }
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
