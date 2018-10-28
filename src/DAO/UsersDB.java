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

    private String username;
    private String passw;
    private String nombre;
    private int access;

    public void getUserInfo(String username){
        String SQL = "SELECT name, access FROM users WHERE username='" + username + "'";

        try {
            if(conn == null || conn.isClosed())
                connect();

            Statement instr = conn.createStatement();
            ResultSet res = instr.executeQuery(SQL);

            while(res.first()){
                this.nombre = res.getString(1);
                this.access = res.getInt(2);
            }

            conn.close();
        } catch (SQLException ex) {ex.printStackTrace();}
    }

    public int validarIngreso(String txtUsuario, String txtPass){

        String usuario = txtUsuario;
        username = usuario;
        String clave = String.valueOf(txtPass);

        int access=0;

        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("CALL validar_ingreso(?, ?, ?)");
            sp.registerOutParameter(3, java.sql.Types.INTEGER);
            sp.setString(1, txtUsuario);
            sp.setString(2, txtPass);

            sp.execute();

            access = sp.getInt(3);

            conn.close();
        } catch (SQLException ex) {ex.printStackTrace();}

        return access;

    }

    public boolean isUser(String user){
        int returnValue = 0;
        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("CALL existe_user(?, ?)");
            sp.setString(1, username);
            sp.registerOutParameter(2, java.sql.Types.INTEGER);

            sp.execute();

            returnValue = sp.getInt(2);

            conn.close();
        } catch (SQLException ex) {ex.printStackTrace();}
        if(returnValue==1)
            return true;
        else
            return false;
    }

    public void modifyPassword(String user, String pass){

        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("CALL mod_userPass(?, ?)");
            sp.setString(1, user);
            sp.setString(2, pass);

            sp.executeUpdate();

            conn.close();
        } catch (SQLException ex) {ex.printStackTrace();}

    }

    public void modifyName(String user, String name){

        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("CALL mod_userNombre(?, ?)");
            sp.setString(1, user);
            sp.setString(2, name);

            sp.executeUpdate();

            conn.close();
        } catch (SQLException ex) {ex.printStackTrace();}

    }

    public void modifyAccess(String user, int access){

        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("CALL mod_userAccess(?, ?)");

            sp.setString(1, user);
            sp.setInt(2, access);

            sp.executeUpdate();

            conn.close();
        } catch (SQLException ex) {System.out.println("Error de conexión");}

    }

    public int getUserAccess(){
        int userAccess = -1;
        try {
            if(conn == null || conn.isClosed())
                connect();

            CallableStatement sp = conn.prepareCall("CALL get_userAccess(?, ?)");

            sp.setString(1, username);
            sp.registerOutParameter(2, java.sql.Types.INTEGER);

            sp.execute();

            userAccess = sp.getInt(2);

            conn.close();
        } catch (SQLException ex) {System.out.println("Error de conexión");}
        return userAccess;
    }

    @Override
    public String readResultSet(ResultSet res) {
        String obj = null;

        try {

            obj = res.getString("username");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return obj;
    }

    @Override
    public void executeWrite() {
        try {
            CallableStatement sp = conn.prepareCall("CALL alta_user(?, ?, ?, ?)");

            sp.setString(1, nombre);
            sp.setString(2, username);
            sp.setString(3, passw);
            sp.setInt(4, access);

            sp.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void executeUpdate() {throw new UnsupportedOperationException("Not supported.");}

    @Override
    public void executeDelete() {
        try {
            CallableStatement sp = conn.prepareCall("CALL elim_user(?)");

            sp.setString(1, username);

            sp.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String executeSearch() {throw new UnsupportedOperationException("Not supported.");}

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
