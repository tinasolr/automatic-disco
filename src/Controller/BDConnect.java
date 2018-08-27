/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import java.sql.*;

/**
 *
 * @author tinar
 */
public class BDConnect {
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://db4free.net:3306/dbpruebaswt";
    private final String USER = "nicotina";
    private final String PASSW = "nicotina";
    private Connection conn;

    public BDConnect(){}

    public void connect(){
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(" Unable to load driver. ");
        }
        try {
            conn = DriverManager.getConnection(URL, USER, PASSW);
             System.out.println(" Connection Established. ");
        } catch (SQLException e) {
            System.out.println(" Error connecting to database:  "
                    + e);
        }
    }
}