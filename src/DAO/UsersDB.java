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
public class UsersDB extends DBObject{

    private int access;

    public int validar_ingreso(String txtUsuario, String txtPass){

        String usuario = txtUsuario;
        String clave = String.valueOf(txtPass);

        int resultado=0;

        String SSQL="SELECT access FROM users WHERE user='"+usuario+"' AND password=sha1('"+clave+"')";

        try {
            if(conn == null || conn.isClosed())
                connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SSQL);

            if(rs.next()){  resultado=1;  access = rs.getInt("access");}
            conn.close();
        } catch (SQLException ex) {System.out.println("Error de conexión");}

        return resultado;

    }

    public void createUser(String user, String pass, int access){

        String SSQL="INSERT INTO users(`user`, `password`, `access`) VALUES ('" + user + "', SHA1('" + pass + "'), '" + access + "')";

        try {
            if(conn == null || conn.isClosed())
                connect();
            Statement st = conn.createStatement();
            st.executeUpdate(SSQL);

            conn.close();
        } catch (SQLException ex) {System.out.println("Error de conexión");}


    }

    @Override
    public <E> E readResultSet(ResultSet res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }


}
