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
//import org.apache.log4j.*;

/**
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


            //        MediosCtrl meCtrl = new MediosCtrl();
//        SoftwareCtrl sctrl = new SoftwareCtrl();
//        meCtrl.cargarMedios();
//        for(Medios m : meCtrl.getMedios()){
//            System.out.println("swteca.SWTeca.main() > " + m.toString());
//            sctrl.softwareDeMedio(m.getCodigo());
//            for(Software s : sctrl.getSwDeMed()){
//                System.out.println("swteca.SWTeca.main() > " + s.toString());
//            }
//        }
//        BasicConfigurator.configure();
        launch(args);

//        UsersDB u = new UsersDB();
//        u.createUser("admin", "culo", 3);
//       ExcelCtrl ex = new ExcelCtrl();
//       ex.writeExcelTemplate();
//       ex.read("C:\\Users\\tinar\\Desktop\\Registro-de-software.xlsx");

    }

}
