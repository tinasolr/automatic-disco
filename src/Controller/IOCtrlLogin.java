/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author tinar
 */
public class IOCtrlLogin implements Initializable {

    private IOCtrlMenu controlmenu;
    private IOCtrlConsMasivaMedios consMasivaMedios;
    private IOCtrlConsMasivaSw consMasivaSw;
    private static UsersDB userdb = new UsersDB();
    private int access;

    @FXML    private TextField txtUsuario;
    @FXML    private PasswordField txtPass;
    @FXML    private Button btnIngresar;
    @FXML    private Button btnCancelar;
    @FXML    private AnchorPane window;

    @FXML
    private void login() {

        access = userdb.validarIngreso(txtUsuario.getText(), txtPass.getText());
        if(access>-1){

            Stage x = (Stage) window.getScene().getWindow();
            x.close();

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ConsultaMedios.fxml"));
                Node y = loader.load();

                consMasivaMedios = loader.getController();
                consMasivaMedios.setControlMenu(controlmenu);
                controlmenu.getMainWindow().setCenter(y);
                controlmenu.disableEverything(access);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }else {
            popUpError("Acceso denegado", "Por favor ingrese un usuario y/o contraseña correctos");
            ((Stage)window.getScene().getWindow()).setAlwaysOnTop(true);
        }
    }

    public void popUpError(String header, String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR, texto);
        alert.setHeaderText(header);
        ((Stage)window.getScene().getWindow()).setAlwaysOnTop(false);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUsuario.requestFocus();
//        window.setOnKeyPressed((event) -> { });
//        window.setOnKeyReleased((event)->{
//            if(event.getEventType().equals(KeyEvent.KEY_RELEASED) && event.getCode().equals(KeyCode.ENTER)){
//            login();
//        }
//        });
    }

    public IOCtrlMenu getControlmenu() {
        return controlmenu;
    }

    public void setControlmenu(IOCtrlMenu controlmenu) {
        this.controlmenu = controlmenu;
    }

    public IOCtrlConsMasivaMedios getConsMasivaMedios() {
        return consMasivaMedios;
    }

    public void setConsMasivaMedios(IOCtrlConsMasivaMedios consMasivaMedios) {
        this.consMasivaMedios = consMasivaMedios;
    }

    public IOCtrlConsMasivaSw getConsMasivaSw() {
        return consMasivaSw;
    }

    public void setConsMasivaSw(IOCtrlConsMasivaSw consMasivaSw) {
        this.consMasivaSw = consMasivaSw;
    }

    public static UsersDB getUserdb() {
        return userdb;
    }

    public static void setUserdb(UsersDB udb) {
        IOCtrlLogin.userdb = udb;
    }

}
