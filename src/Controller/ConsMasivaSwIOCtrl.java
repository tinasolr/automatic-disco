/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.*;
import java.net.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;

public class ConsMasivaSwIOCtrl implements Initializable {

    @FXML private AnchorPane showSw;
    @FXML    private TableView<Software> tblSoftware;
    @FXML    private TableColumn<Software, String> colCodigoSw;
    @FXML    private TableColumn<Software, String> colNombreSw;
    @FXML    private TableColumn<Software, String> colVersionSw;
    @FXML    private TableColumn<Software, String> colSistOpSw;

    private SoftwareCtrl swCtrl = new SoftwareCtrl();

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
