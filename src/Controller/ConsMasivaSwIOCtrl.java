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

public class ConsMasivaSwIOCtrl implements Initializable {

    @FXML private AnchorPane showSw;
    @FXML    private TableView<Software> tblSoftware;
    @FXML    private TableColumn<Software, String> colCodigoSw;
    @FXML    private TableColumn<Software, String> colNombreSw;
    @FXML    private TableColumn<Software, String> colVersionSw;
    @FXML    private TableColumn<Software, String> colSistOpSw;
    @FXML    private ContextMenu mnuDerecho;
    @FXML    private MenuItem derVer;
    @FXML    private MenuItem derModificar;
    @FXML    private MenuItem derEliminar;

    private SoftwareCtrl swCtrl = new SoftwareCtrl();

    @FXML
    private void consIndividual(ActionEvent event) {
    }

    @FXML
    private void modSoftware(ActionEvent event) {
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ModSoftware.fxml"));
            ModSwConExtrasIOCtrl msce = new ModSwConExtrasIOCtrl();
            msce.setCodigoSw(tblSoftware.getSelectionModel().getSelectedItem().getCodigo());
            loader.setController(msce);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Modificar Software");
            stage.setScene(scene);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void elimSoftware(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTable();
    }

    public void loadTable(){
        swCtrl.cargarSoftware();
        colCodigoSw.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombreSw.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVersionSw.setCellValueFactory(new PropertyValueFactory<>("version"));
        colSistOpSw.setCellValueFactory(new PropertyValueFactory<>("sistOp"));
        if(swCtrl.getSws()!=null)
            tblSoftware.getItems().setAll(swCtrl.getSws());
    }

}
