/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
/**
 * FXML
 *
 * @author tinar
 */
public class ABMExtraIOCtrl implements Initializable {

    @FXML  private Label lblNombre;
    @FXML  private Label lblVersion;
    @FXML  private Label lblPartes;
    @FXML  private Label lblDescripcion;
    @FXML  private TextField txtNombre;
    @FXML  private TextField txtVersion;
    @FXML  private TextField txtPartes;
    @FXML  private TextArea txtDescripcion;
    @FXML  private Button btnAgregar;
    @FXML  private Button btnElimTodos;
    @FXML  private Tooltip toolTipPartes;
    @FXML  private Button btnQuitar;
    @FXML    private TableView<Extras> tblExtras;
    @FXML    private TableColumn<Extras, String> colNombre;
    @FXML    private TableColumn<Extras, String> colVersion;
    @FXML    private TableColumn<Extras, String> colPartes;
    @FXML    private TableColumn<Extras, String> colDescrip;
    @FXML    private Button btnFinalizar;
    @FXML    private Label lblTituloSw;
    @FXML    private Label lblNombreSw;
    @FXML    private Label lblVersionSw;
    @FXML    private Label lblSistOpSw;
    @FXML    private TextField txtNombreSw;
    @FXML    private TextField txtVersionSw;
    @FXML    private ComboBox<String> cmbSos;
    @FXML    private ListView<String> lstSistemasOp;
    @FXML    private Button btnAgregarSo;
    @FXML    private Button btnQuitarSo;
    @FXML    private Label lblTituloSw1;

    private int codigoSW;
    private Software sw;
    private final SoftwareCtrl swCtrl = new SoftwareCtrl();
    private final ExtrasCtrl exCtrl = new ExtrasCtrl();

    public ABMExtraIOCtrl(){}
    public ABMExtraIOCtrl(int codigo){
        this.codigoSW = codigo;
        this.sw = swCtrl.findSoftware(codigo);
    }

    @FXML
    private void agregarExtra(ActionEvent event) {
        String nombre = txtNombre.getText();
        String version = txtVersion.getText();
        int partes = Integer.parseInt(txtPartes.getText());
        String descrip = txtDescripcion.getText();

        sw.setExtras(nombre, version, descrip, partes);
        exCtrl.altaExtra(nombre, descrip, version, partes, codigoSW);

        Extras nuevo = new Extras(nombre, version, descrip, partes);
        tblExtras.getItems().add(nuevo);
        clearFields();
    }

    @FXML
    private void quitarExtra(ActionEvent event) {
        Extras eliminar = tblExtras.getSelectionModel().getSelectedItem();
        exCtrl.eliminarExtra(eliminar.getNombre(), sw);

        sw.getExtras().remove(eliminar);
        tblExtras.getItems().remove(eliminar);
    }

    @FXML
    private void eliminarTodos(ActionEvent event) {
        exCtrl.eliminarExtras(sw);
        sw.getExtras().clear();
        tblExtras.getItems().clear();
    }

    @FXML
    private void finalizar(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void agregarSistemaOperativo(ActionEvent event) {
    }

    @FXML
    private void quitarSistemaOperativo(ActionEvent event) {
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTable();
    }

    public void loadTable(){
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colPartes.setCellValueFactory(new PropertyValueFactory<>("partes"));
        colDescrip.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        if(!sw.getExtras().isEmpty())
        tblExtras.getItems().setAll(sw.getExtras());
    }

    public void clearFields(){
        txtNombre.setText("");
        txtVersion.setText("");
        txtDescripcion.setText("");
        txtPartes.setText("");
    }
}
