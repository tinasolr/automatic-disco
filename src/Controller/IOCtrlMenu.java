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
    private IOCtrlABMFormato formato;
    private IOCtrlABMSistemaOperat so;
    private IOCtrlABMUbicaciones ubi;
    private IOCtrlAltaMedio altamedio;
    private IOCtrlAltaSwConExtras altasoftware;
    private IOCtrlConsMasivaSw consmasivasw;
    private IOCtrlConsMasivaMedios consmasivamed;
    private IOCtrlModSwConExtras modsoftware;
    private IOCtrlLogin login;

    private int accesos;

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
    @FXML    private MenuItem ConsultaMedios;
    @FXML    private BorderPane mainWindow;
    @FXML    private MenuItem ABMFormatos;
    @FXML    private MenuItem ABMUbicaciones;
    @FXML    private MenuItem ABMSistemaOperat;
    @FXML    private MenuItem arcOpciones;

    /*** Initializes the controller class.************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ConsultaMedios.fxml"));
            Node x = loader.load();
            consmasivamed = loader.getController();
            consmasivamed.setAccess(accesos);
            consmasivamed.setControlMenu(this);
            mainWindow.setCenter(x);
            disableEverything(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Login.fxml"));

            Parent root = loader.load();
            login = loader.getController();
            login.setConsMasivaMedios(consmasivamed);
            login.setControlmenu(this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /***************JAVAFX FUNCTIONS*******************************************/

    @FXML
    public void altaSoftware(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/AltaSoftware.fxml"));

            Parent root = loader.load();
            altasoftware = loader.getController();
            altasoftware.setConsmasivasw(consmasivasw);
            altasoftware.setControlMenu(this);

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
    public void altaMedio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/AltaMedio.fxml"));

            Parent root = loader.load();
            altamedio = loader.getController();
            altamedio.setConsmasivamed(consmasivamed);
            altamedio.setControlMenu(this);
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
    public void editarFormato(ActionEvent event) {
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
    public void editarUbicaciones(ActionEvent event) { try {
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
    public void editarSistOperativos(ActionEvent event) {
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
            if(menuItemID.contains("ConsultaMedios")){
                consmasivamed = loader.getController();
                consmasivamed.setControlMenu(this);
                consmasivamed.setAccess(accesos);
                consmasivamed.disableMenuItems();
            }else if(menuItemID.contains("ConsultaSoftware")){
                consmasivasw = loader.getController();
                consmasivasw.setControlMenu(this);
                consmasivasw.setAccess(accesos);
                consmasivasw.disableMenuItems();
            }
            mainWindow.setCenter(x);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void openOptions(ActionEvent event) {
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

    public void disableEverything(boolean x){

        arcImportar.setDisable(x);
        arcExportar.setDisable(x);
        mEditar.setDisable(x);
        mVer.setDisable(x);
        arcNuevo.setDisable(x);
        AltaSoftware.setDisable(x);
        AltaMedio.setDisable(x);
        ConsultaMedios.setDisable(x);
        ConsultaSoftware.setDisable(x);
        ABMFormatos.setDisable(x);
        ABMUbicaciones.setDisable(x);
        ABMSistemaOperat.setDisable(x);
    }

    public void disableEverything(int access) {

        switch(access){
            case 0:
                arcImportar.setDisable(true);
                arcExportar.setDisable(true);
                mEditar.setDisable(true);
                mVer.setDisable(false);
                arcNuevo.setDisable(true);
                boolean ed = mEditar.isDisable();
                boolean ver = mVer.isDisable();
                boolean nu = arcNuevo.isDisable();
                AltaSoftware.setDisable(nu);
                AltaMedio.setDisable(nu);
                ConsultaMedios.setDisable(ver);
                ConsultaSoftware.setDisable(ver);
                ABMFormatos.setDisable(ed);
                ABMUbicaciones.setDisable(ed);
                ABMSistemaOperat.setDisable(ed);
                break;
            case 1:
                arcImportar.setDisable(true);
                arcExportar.setDisable(true);
                mEditar.setDisable(true);
                mVer.setDisable(false);
                arcNuevo.setDisable(false);
                boolean ed1 = mEditar.isDisable();
                boolean ver1 = mVer.isDisable();
                boolean nu1 = arcNuevo.isDisable();
                AltaSoftware.setDisable(nu1);
                AltaMedio.setDisable(nu1);
                ConsultaMedios.setDisable(ver1);
                ConsultaSoftware.setDisable(ver1);
                ABMFormatos.setDisable(ed1);
                ABMUbicaciones.setDisable(ed1);
                ABMSistemaOperat.setDisable(ed1);
                break;
            case 2:
                arcImportar.setDisable(false);
                arcExportar.setDisable(false);
                mEditar.setDisable(false);
                mVer.setDisable(false);
                arcNuevo.setDisable(false);
                boolean ed2 = mEditar.isDisable();
                boolean ver2 = mVer.isDisable();
                boolean nu2 = arcNuevo.isDisable();
                AltaSoftware.setDisable(nu2);
                AltaMedio.setDisable(nu2);
                ConsultaMedios.setDisable(ver2);
                ConsultaSoftware.setDisable(ver2);
                ABMFormatos.setDisable(ed2);
                ABMUbicaciones.setDisable(ed2);
                ABMSistemaOperat.setDisable(ed2);
                break;
            case 3:
                arcImportar.setDisable(false);
                arcExportar.setDisable(false);
                mEditar.setDisable(false);
                mVer.setDisable(false);
                arcNuevo.setDisable(false);
                boolean ed3 = mEditar.isDisable();
                boolean ver3 = mVer.isDisable();
                boolean nu3 = arcNuevo.isDisable();
                AltaSoftware.setDisable(nu3);
                AltaMedio.setDisable(nu3);
                ConsultaMedios.setDisable(ver3);
                ConsultaSoftware.setDisable(ver3);
                ABMFormatos.setDisable(ed3);
                ABMUbicaciones.setDisable(ed3);
                ABMSistemaOperat.setDisable(ed3);
                break;
        }
    }

    public int getAccesos() {
        return accesos;
    }

    public void setAccesos(int accesos) {
        this.accesos = accesos;
    }

}
