/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;

/**
 *
 * @author Nico
 */
public class AltaUbicacionesCtrl {
    
    @FXML   private Label lblTituloUb;
    @FXML   private Label lblCodId;
    @FXML   private Label lblDescripcionId;
    @FXML   private TextField txtCodId;
    @FXML   private TextArea txtDescripcionId;
    @FXML   private Button btnAgregarUb;
    @FXML   private Button btnCancelarUb;
    
    private BorderPane mainWindow;

    
    
    public AltaUbicacionesCtrl(){}
    
    
    
    
    
    public void changeBackToConsultaSw(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/ConsultaSoftware.fxml"));
            Node x = loader.load();
            mainWindow.setCenter(x);
        } catch (IOException ex) {
            Logger.getLogger(AltaSwConExtrasIOCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setMainWindow(BorderPane mainWindow) {
        this.mainWindow = mainWindow;
    }
    
    
    
    
    
}
