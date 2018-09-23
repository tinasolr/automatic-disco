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
import javafx.beans.value.*;
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
    @FXML    private RadioButton rdbTodos;
    @FXML    private ToggleGroup groupFiltrar;
    @FXML    private RadioButton rdbCodigo;
    @FXML    private RadioButton rdbSistOp;
    @FXML    private RadioButton rdbNombre;
    @FXML    private RadioButton rdbVersion;
    @FXML    private MenuItem AltaSoftware;
    @FXML    private MenuItem AltaMedio;
    @FXML    private MenuItem AltaCopia;
    @FXML    private MenuItem ConsultaMedios;
    @FXML    private MenuItem ConsultaCopias;
    @FXML    private TextField txtFiltrar;
    @FXML    private Button btnFiltrar;
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
    private void editarFormato(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ABMFormato.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Formatos");
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
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleChangeView(ActionEvent event) {
        try {
            String menuItemID = ((MenuItem) event.getSource()).getId();

            if(!menuItemID.matches("Consulta.*"))
                disableSearchItems(true);
            else
                disableSearchItems(false);

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/Vista/" + menuItemID + ".fxml"));

            Node x = loader.load();
            if(menuItemID.equalsIgnoreCase("AltaSoftware")){
                IOCtrlAltaSwConExtras aw = (IOCtrlAltaSwConExtras)loader.getController();
                aw.setMainWindow(mainWindow);

                mainWindow.getScene().widthProperty().addListener(new ChangeListener<Number>() {
                    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                        System.out.println("Width: " + newSceneWidth);
                        aw.adjustHeightAndWidth(aw.getVboxSw().getHeight(), newSceneWidth.doubleValue());
                        aw.getVboxSw().autosize();
                    }
                });
                 mainWindow.getScene().heightProperty().addListener(new ChangeListener<Number>() {
                    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                        System.out.println("Height: " + newSceneHeight);
                        aw.adjustHeightAndWidth(newSceneHeight.doubleValue(), aw.getVboxSw().getWidth());
                        aw.getVboxSw().autosize();
                    }
                });
            }else if(menuItemID.equalsIgnoreCase("AltaMedio")){
                IOCtrlAltaMedio a = (IOCtrlAltaMedio)loader.getController();
                a.setMainWindow(mainWindow);
            }
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

    private void disableSearchItems(boolean x){
        txtFiltrar.setDisable(x);
        btnFiltrar.setDisable(x);
        rdbCodigo.setDisable(x);
        rdbNombre.setDisable(x);
        rdbSistOp.setDisable(x);
        rdbVersion.setDisable(x);
        rdbTodos.setDisable(x);
    }
}
