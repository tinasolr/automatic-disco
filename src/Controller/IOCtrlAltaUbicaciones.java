/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author Nico
 */
public class IOCtrlAltaUbicaciones {

    @FXML   private Label lblTituloUb;
    @FXML   private Label lblCodId;
    @FXML   private Label lblDescripcionId;
    @FXML   private TextField txtCodId;
    @FXML   private TextArea txtDescripcionId;
    @FXML   private Button btnAgregarUb;
    @FXML   private Button btnCancelarUb;

    private BorderPane mainWindow;
    private List<Ubicaciones> ub = new ArrayList<>();



    public IOCtrlAltaUbicaciones(){

    }

    @FXML
    private void AgregarUbicacion(ActionEvent event) {

        //Verificar que los datos ingresados sean válidos o mandar popUp(textoError)
        //Armar un objeto software con el nombre, versión y la lista de sistOperativos
        String id = txtCodId.getText();
        String descripcion = txtDescripcionId.getText();



        if(id.matches("[0-9]+(\\.[0-9]+)*") && descripcion.matches("[0-9]+(\\.[0-9]+)*")){




                /*}else{ popUp("El id de Ubicaciones ya existe. Ingrese uno diferente");}*/
            }else{ popUp("Rellenar espacios vacios y volver a intentar. ");}

    }

     public void popUp(String texto){
        Alert alert = new Alert(AlertType.ERROR, texto);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
    }

    public void changeBackToConsultaSw(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/ConsultaSoftware.fxml"));
            Node x = loader.load();
            mainWindow.setCenter(x);
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlAltaUbicaciones.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setMainWindow(BorderPane mainWindow) {
        this.mainWindow = mainWindow;
    }





}
