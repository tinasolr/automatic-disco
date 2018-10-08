/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swteca;

import java.io.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

/**
 *
 * @author tinar
 */
public class SWTeca extends Application {

    public static Stage primaryStage;
    public static Parent root;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SoftwareTeca");
        initRoot();
    }

    public void initRoot(){
        try{

            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("/Vista/Menu.fxml"));

            Scene scene = new Scene(root, 1056, 824);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
//        UsersDB u = new UsersDB();
//        u.createUser("admin", "culo", 3);
//       ExcelCtrl ex = new ExcelCtrl();
//       ex.read("C:\\Users\\tinar\\Desktop\\Registro-de-software.xlsx");
    }

}
