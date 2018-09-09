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
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author tinar
 */
public class SWTeca extends Application {

    public static Stage primaryStage;
    public static AnchorPane root;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SoftwareTeca");
        initRoot();
    }

    public void initRoot(){
        try{

            FXMLLoader loader = new FXMLLoader();
            root = (AnchorPane)loader.load(getClass().getResource("/Vista/Menu.fxml"));

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
        launch(args);
    }

}
