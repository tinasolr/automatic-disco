/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import Model.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author tinar
 */
public class IOCtrlABMUbicaciones implements Initializable, EventHandler<KeyEvent> {

    @FXML    private Button btnQuitar;
    @FXML    private TextField txtCodigo;
    @FXML    private Button btnAgregar;
    @FXML    private Button btnAceptar;
    @FXML    private TextArea txtDescripcion;
    @FXML    private TableView<Ubicaciones> tblUbicaciones;
    @FXML    private TableColumn<Ubicaciones, String> colCodigo;
    @FXML    private TableColumn<Ubicaciones, String> colDescripcion;
    @FXML    private Label lblCodigo;
    @FXML    private AnchorPane abmUbicaciones;

    /*******Initializes the controller class.**********************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTable();
        txtCodigo.setOnKeyPressed((KeyEvent event) -> {

        });
        txtCodigo.setOnKeyReleased(this);
        txtDescripcion.setOnKeyPressed((KeyEvent k) -> {});
        txtDescripcion.setOnKeyReleased(this);
        tblUbicaciones.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /****************************JAVAFX FUNCTIONS******************************/

    @FXML
    private void quitar(ActionEvent event) {
        if(tblUbicaciones.getSelectionModel().getSelectedItem() != null){
            if(!tblUbicaciones.getSelectionModel().getSelectedItem().getId().equalsIgnoreCase("NOASIG")){
                if(popUpWarning("Está seguro de que desea eliminar la ubicación " + tblUbicaciones.getSelectionModel().getSelectedItem().getId() + "?")){
                    UbicacionesDB u = new UbicacionesDB();
                    u.connect();
                    u.setCodUbi(tblUbicaciones.getSelectionModel().getSelectedItem().getId());
                    u.delete();
                    UbicacionesCtrl ubictrl = new UbicacionesCtrl();
                    ubictrl.getUbis().remove(tblUbicaciones.getSelectionModel().getSelectedItem());
                    loadTable();
                    txtCodigo.clear();
                    txtDescripcion.clear();
                }
            }else{popUpError("No puede eliminar este valor.");}
        }else{popUpError("Por favor, seleccione una ubicación a eliminar.");}
    }

    @FXML
    private void agregar(ActionEvent event) throws SQLException {
        String id = txtCodigo.getText();
        String descripcion = txtDescripcion.getText();

        if(id.matches("[A-Z]+[0-9]+(\\-[0-9]+)?")){
            UbicacionesDB u = new UbicacionesDB();
            u.connect();
            if(!u.isDuplicate(id)){
                u.setCodUbi(txtCodigo.getText());
                u.setObsUbi(txtDescripcion.getText());
                u.write();
                UbicacionesCtrl ubictrl = new UbicacionesCtrl();
                ubictrl.getUbis().add(new Ubicaciones(txtCodigo.getText(), txtDescripcion.getText()));
                loadTable();
                txtCodigo.clear();
                txtDescripcion.clear();
                btnAgregar.setDisable(true);
            }else{popUpError("El código ya existe. Por favor, ingrese uno diferente"); u.getConn().close();}
        }else{
            String error = "Algo salió mal.";
            if(id.isEmpty()) error = "Por favor, ingrese un código.";
            else if(!id.matches("[A-Z]+[0-9]+")) error = "Por favor, ingrese un código válido.";
            popUpError(error);
        }
    }

    @FXML
    private void aceptar(ActionEvent event) {
        Stage x = (Stage) abmUbicaciones.getScene().getWindow();
        x.close();
    }

    /*********************OTHER FUNCTIONS*************************************/

    public void loadTable(){
        UbicacionesCtrl ubictrl = new UbicacionesCtrl();
        if(ubictrl.getUbis().isEmpty())
            ubictrl.cargarUbicaciones();

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        if(ubictrl.getUbis()!=null)
            tblUbicaciones.getItems().setAll(ubictrl.getUbis());

        if(tblUbicaciones.getItems().isEmpty())
            btnQuitar.setDisable(true);
        else
            btnQuitar.setDisable(false);
    }

    public void loadTable(String partId, String partDescr){
        UbicacionesCtrl u = new UbicacionesCtrl();
        if(u.getUbis().isEmpty())
            u.cargarUbicaciones();
        List<Ubicaciones> ubi = new ArrayList<>();

        if(!partId.isEmpty() && !partDescr.isEmpty())
            for(Ubicaciones x : u.getUbis()){
                if(x.getDescripcion() != null)
                    if(x.getId().toLowerCase().contains(partId.toLowerCase()) && x.getDescripcion().toLowerCase().contains(partDescr.toLowerCase()))
                        ubi.add(x);
            }
        if(partId.isEmpty() && !partDescr.isEmpty())
            for(Ubicaciones x : u.getUbis()){
                if(x.getDescripcion() != null)
                    if(x.getDescripcion().toLowerCase().contains(partDescr.toLowerCase()))
                        ubi.add(x);
            }
        if(!partId.isEmpty() && partDescr.isEmpty())
            for(Ubicaciones x : u.getUbis()){
                if(x.getId().toLowerCase().contains(partId.toLowerCase()))
                    ubi.add(x);
            }
        tblUbicaciones.getItems().setAll(ubi);

        if(tblUbicaciones.getItems().isEmpty())
            btnQuitar.setDisable(true);
        else
            btnQuitar.setDisable(false);
    }

    public void popUpError(String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR, texto);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
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

    /**************EVENT HANDLERS*********************************************/
    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType().equals(KeyEvent.KEY_RELEASED) && event.getSource().equals(txtCodigo)){
            if(txtCodigo.getText().isEmpty() || txtCodigo.getText() == null){
                loadTable();
                btnAgregar.setDisable(true);
            }else{
                loadTable(txtCodigo.getText(), txtDescripcion.getText());
                btnAgregar.setDisable(false);
            }
        }else if(event.getEventType().equals(KeyEvent.KEY_RELEASED) && event.getSource().equals(txtDescripcion)){
            if(txtDescripcion.getText().isEmpty() || txtDescripcion.getText() == null)
                loadTable();
            else
                loadTable(txtCodigo.getText(), txtDescripcion.getText());
        }
    }
}
