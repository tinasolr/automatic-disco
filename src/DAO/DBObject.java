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
            conn = DriverManager.getConnection(URL, USER, PASSW);
            instr = conn.createStatement();

            System.out.println("Connection Established.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public <E> List<E> read(String table) {
        List<E> objetos = new ArrayList<>();
        ResultSet res = null;
        try {

            if(conn.isClosed())
                connect();

            res = instr.executeQuery("SELECT * FROM " + table);

            while(res.next()){
                objetos.add(readResultSet(res));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("BDObject read() :: " + e.getMessage());
        }

        return objetos;
    }

    public abstract <E> E readResultSet(ResultSet res);

    public void write() {
        try {

            if(conn.isClosed())
                connect();

            executeWrite();

            conn.close();

        } catch (SQLException e) {
            System.out.println("BDObject write() :: " + e.getMessage());
        }
    }

    public abstract void executeWrite();

    public void update() {
        try {

            if(conn.isClosed())
                connect();

            executeUpdate();

            conn.close();

        } catch (SQLException e) {
            System.out.println("BDObject update() :: " + e.getMessage());
        }
    }

    public abstract void executeUpdate();

    public void delete(){
        try {

            if(conn.isClosed())
                connect();

            executeDelete();

            conn.close();

        } catch (SQLException e) {
            System.out.println("BDObject delete() :: " + e.getMessage());
        }
    }

    public abstract void executeDelete();

    public String searchTable(){
        String id = null;
        try {

            if(conn.isClosed())
                connect();

            id = executeSearch();

            conn.close();

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



}