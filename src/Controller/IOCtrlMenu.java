/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author Nico
 */
public class IOCtrlMenu implements Initializable {

    private BorderPane borderPane;

    @FXML    private MenuBar mnuPpal;
    @FXML    private Menu mArchivo;
    @FXML    private Menu arcNuevo;
    @FXML    private MenuItem arcImportar;
    @FXML    private MenuItem arcExportar;
    @FXML    private MenuItem arcSalir;
    @FXML    private Menu mEditar;
    @FXML    private Menu mVer;
    @FXML    private MenuItem ConsultaSoftware;
    @FXML    private Menu mAyuda;
    @FXML    private MenuItem ayAbout;
    @FXML    private MenuItem AltaSoftware;
    @FXML    private MenuItem AltaMedio;
    @FXML    private MenuItem AltaCopia;
    @FXML    private MenuItem ConsultaMedios;
    @FXML    private MenuItem ConsultaCopias;
    @FXML    private BorderPane mainWindow;
    @FXML    private MenuItem ABMFormatos;
    @FXML    private MenuItem ABMUbicaciones;
    @FXML    private MenuItem ABMSistemaOperat;

    /*** Initializes the controller class.************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    /***************JAVAFX FUNCTIONS*******************************************/

    @FXML
    private void altaSoftware(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/AltaSoftware.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Software");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void altaMedio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/AltaMedio.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Medio de Almacenamiento");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editarFormato(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ABMFormato.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Formatos");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editarUbicaciones(ActionEvent event) { try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ABMUbicaciones.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ubicaciones");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void editarSistOperativos(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ABMSistemaOperat.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Sistemas Operativos");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleChangeView(ActionEvent event) {
        try{
            String menuItemID = ((MenuItem) event.getSource()).getId();

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/Vista/" + menuItemID + ".fxml"));

            Node x = loader.load();

            mainWindow.setCenter(x);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /************************OTHER FUNCTIONS*********************************/

    public void changeScene(String url){
        try {
            FXMLLoader loader = new FXMLLoader(IOCtrlMenu.class.getResource("/Vista/Menu.fxml"));

            Scene scene = new Scene(loader.load(getClass().getResource(url)));
            swteca.SWTeca.primaryStage.setScene(scene);
            swteca.SWTeca.primaryStage.show();

        } catch (IOException iOException) {
            System.err.println(iOException.getLocalizedMessage());
        }
    }
}
