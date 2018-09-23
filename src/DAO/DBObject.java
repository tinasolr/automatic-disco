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
public abstract class DBObject {
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://db4free.net:3306/dbpruebaswt?autoReconnect=true&useSSL=false";
    private final String URLBDLOCAL = "jdbc:mysql://localhost/dbpruebaswt?autoReconnect=true&useSSL=false";
    private boolean isRemote = false;
    private final String USER = "nicotina";
    private final String PASSW = "nicotina";
    protected Connection conn;
    protected Statement instr;

    public void connect(){
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load driver.");
        }
        try {
            if(isRemote){
                conn = DriverManager.getConnection(URL, USER, PASSW);

                instr = conn.createStatement();

                System.out.println("Connection Established.");
            }else{
                conn = DriverManager.getConnection(URLBDLOCAL, USER, PASSW);

                instr = conn.createStatement();

                System.out.println("Connection Established.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public <E> List<E> read(String table) {
        List<E> objetos = new ArrayList<>();
        ResultSet rs = null;
        try {

            if(conn == null || conn.isClosed())
                connect();

            rs = instr.executeQuery("SELECT * FROM " + table);

            while (rs.next()) {
                objetos.add(readResultSet(rs));
            }
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("BDObject read() :: " + e.getMessage());
        }

        return objetos;
    }

    public abstract <E> E readResultSet(ResultSet res);

    public void write() {
        try {

            if(conn == null || conn.isClosed())
                connect();

            executeWrite();

            conn.close();
            System.out.println("Connection closed.");

        } catch (SQLException e) {
            System.out.println("BDObject write() :: " + e.getMessage());
        }
    }

    public abstract void executeWrite();

    public void update() {
        try {

            if(conn == null || conn.isClosed())
                connect();

            executeUpdate();

            conn.close();
            System.out.println("Connection closed.");

        } catch (SQLException e) {
            System.out.println("BDObject update() :: " + e.getMessage());
        }
    }

    public abstract void executeUpdate();

    public void delete(){
        try {

            if(conn == null || conn.isClosed())
                connect();

            executeDelete();

            conn.close();
            System.out.println("Connection closed.");

        } catch (SQLException e) {
            System.out.println("BDObject delete() :: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public abstract void executeDelete();

    public String searchTable(){
        String id = null;
        try {

            if(conn == null || conn.isClosed())
                connect();

            id = executeSearch();

            conn.close();
            System.out.println("Connection closed.");

        } catch (SQLException e) {
            System.out.println("BDObject search() :: " + e.getMessage());
        }
        return id;
    }

    public abstract String executeSearch();

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Statement getInstr() {
        return instr;
    }

    final public static void printResultSet(ResultSet rs) throws SQLException{
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }

}