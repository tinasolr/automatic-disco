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
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class IOCtrlConsMasivaSw implements Initializable {

    @FXML    private AnchorPane showSw;
    @FXML    private TableView<Software> tblSoftware;
    @FXML    private TableColumn<Software, String> colCodigoSw;
    @FXML    private TableColumn<Software, String> colNombreSw;
    @FXML    private TableColumn<Software, String> colVersionSw;
    @FXML    private TableColumn<Software, String> colSistOpSw;
    @FXML    private ContextMenu mnuDerecho;
    @FXML    private MenuItem derVer;
    @FXML    private MenuItem derModificar;
    @FXML    private MenuItem derEliminar;

    private SoftwareCtrl swCtrl;
    @FXML
    private TextField txtFiltrar;
    @FXML
    private RadioButton rdbTodos;
    @FXML
    private ToggleGroup groupFiltrar;
    @FXML
    private RadioButton rdbCodigo;
    @FXML
    private RadioButton rdbSistOp;
    @FXML
    private RadioButton rdbNombre;
    @FXML
    private RadioButton rdbVersion;
    @FXML
    private Button btnFiltrar;
    @FXML
    private BorderPane mainWindow;

    @FXML
    private void consIndividual(ActionEvent event) {
    }

    @FXML
    private void modSoftware(ActionEvent event) {
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ModSoftware.fxml"));
            IOCtrlModSwConExtras msce = new IOCtrlModSwConExtras();
            msce.setCodigoSw(tblSoftware.getSelectionModel().getSelectedItem().getCodigo());
            msce.setConsMas(this);
            loader.setController(msce);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Modificar Software");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void elimSoftware(ActionEvent event) {
        if(popUpWarning("Está seguro de que desea eliminar el software?")){
            swCtrl = new SoftwareCtrl();
            int codigo = tblSoftware.getSelectionModel().getSelectedItem().getCodigo();
            swCtrl.elimSoftware(codigo);
            loadTable();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTable();
    }

    public void loadTable(){
        swCtrl = new SoftwareCtrl();
        swCtrl.cargarSoftware();
        colCodigoSw.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombreSw.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVersionSw.setCellValueFactory(new PropertyValueFactory<>("version"));
        colSistOpSw.setCellValueFactory(new PropertyValueFactory<>("sistOp"));

        if(swCtrl.getSws()!=null)
            tblSoftware.getItems().setAll(swCtrl.getSws());
    }

    public boolean popUpWarning(String texto){
        Alert alert = new Alert(Alert.AlertType.WARNING, texto, ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                alert.close();
                return true;
            }
        alert.close();
        return false;
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
