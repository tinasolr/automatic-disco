/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swteca;

import Controller.*;
import Model.*;
import java.io.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author tinar
 */
public class SWTeca extends Application {

    private Stage primaryStage;
    private AnchorPane root;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SoftwareTeca");
        initRoot();
    }

    public void initRoot(){
        try{

            Parent root = FXMLLoader.load(getClass().getResource("/Vista/Menu.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExtrasCtrl x = new ExtrasCtrl();
        x.cargarExtras();
        for(Extras c : x.getExtras()){
            System.out.println(c.getNombre());
        }
    //    launch(args);
    }

}
