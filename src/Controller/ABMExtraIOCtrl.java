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

    private ExtrasCtrl exCtrl = new ExtrasCtrl();
    //CAMBIAR ESTO POR EL SOFTWARE ENVIADO DE LA VENTANA ANTERIOR
    Software soft = new Software(1, "SW 1", Arrays.asList("Penguin", "Pineapple", "Windy"), "1.0");

    @FXML
    private void agregarExtra(ActionEvent event) {
        String nombre = txtNombre.getText();
        String version = txtVersion.getText();
        int partes = Integer.parseInt(txtPartes.getText());
        String descrip = txtDescripcion.getText();

        Extras nuevo = exCtrl.altaExtra(nombre, descrip, version, partes, soft);
        tblExtras.getItems().add(nuevo);
        clearFields();
    }

    @FXML
    private void quitarExtra(ActionEvent event) {
        Extras eliminar = tblExtras.getSelectionModel().getSelectedItem();
        exCtrl.eliminarExtra(eliminar.getNombre(), soft);
        exCtrl.getExtras().remove(eliminar);
        tblExtras.getItems().remove(eliminar);
    }

    @FXML
    private void eliminarTodos(ActionEvent event) {
        List<Extras> a = tblExtras.getItems();
        exCtrl.eliminarExtras(soft);
        tblExtras.getItems().clear();
    }

    @FXML
    private void finalizar(ActionEvent event) {
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTable();
    }

    public void loadTable(){
        exCtrl.cargarExtras(1);
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colPartes.setCellValueFactory(new PropertyValueFactory<>("partes"));
        colDescrip.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        tblExtras.getItems().setAll(exCtrl.getExtras());
    }

    public void clearFields(){
        txtNombre.setText("");
        txtVersion.setText("");
        txtDescripcion.setText("");
        txtPartes.setText("");
    }
}
