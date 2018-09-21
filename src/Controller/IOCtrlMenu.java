/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author Nico
 */
public class IOCtrlMenu implements Initializable {

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
    private BorderPane borderPane;
    @FXML    private MenuItem AltaSoftware;
    @FXML    private MenuItem AltaMedio;
    @FXML    private MenuItem AltaCopia;
    @FXML    private MenuItem ModFormatos;
    @FXML    private MenuItem ModUbicaciones;
    @FXML    private MenuItem ModSistOp;
    @FXML    private MenuItem ConsultaMedios;
    @FXML    private MenuItem ConsultaCopias;
    @FXML    private TextField txtFiltrar;
    @FXML    private Button btnFiltrar;
    @FXML    private BorderPane mainWindow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

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
                System.out.println(aw);
                aw.setMainWindow(mainWindow);
            }
            

            
            mainWindow.setCenter(x);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
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
