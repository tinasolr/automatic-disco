/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javafx.scene.*;
import javax.swing.*;
import Model.*;
import DAO.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Nico
 */
public class IOCtrlABMCopias implements Initializable{
   
    @FXML private AnchorPane AltaCopia;
    @FXML private Label lblMedio;
    @FXML private Label lblFormato;
    @FXML private Label lblUbicaciones;
    @FXML private TextField txtMedio;
    @FXML private TextArea txtDescripcion;
    @FXML private Button btnFinalizar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnCancelar;
    @FXML private ComboBox<String> cmbFormato;
    @FXML private ComboBox<String> cmbUbicaciones;
    @FXML private CheckBox chkDeposito;
    
    private String medioID;
    private String nomMedio;
    private IOCtrlConsMasivaMedios controlMenu;
    
    /*******Initializes the controller class.**********************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtMedio.setText(nomMedio);
        
        
        FormatoDB formdb = new FormatoDB();
        List<String> form = formdb.read("formatos");
        if(!cmbFormato.getItems().isEmpty())
            cmbFormato.getItems().clear();

        form.forEach((x)-> {cmbFormato.getItems().add(x);});
        new AutoCompleteComboBoxListener<>(cmbFormato);
        
        /*SistOpDB so = new SistOpDB();
        List<SistOpDB> sos = so.read("SistOperativos");
        if(!cmbSos.getItems().isEmpty())
            cmbSos.getItems().clear();
        sos.forEach((x) -> { cmbSos.getItems().add(x.getNombre()); });
        new AutoCompleteComboBoxListener<>(cmbSos);
        loadTable();*/

        UbicacionesDB ubdb = new UbicacionesDB();
        List<UbicacionesDB> ubic = ubdb.read("ubicaciones");
        if(!cmbUbicaciones.getItems().isEmpty())
            cmbUbicaciones.getItems().clear();
        ubic.forEach((y) -> { cmbUbicaciones.getItems().add(y.getCodUbi()); });
        new AutoCompleteComboBoxListener<>(cmbUbicaciones);

        
    }
    
    
 
    
    @FXML
    private void agregarCopia(ActionEvent event) throws SQLException {

        String descripcion = txtDescripcion.getText();
        CopiasCtrl coCtrl = new CopiasCtrl();
        FormatoDB foDB = new FormatoDB();
        UbicacionesDB ubDB = new UbicacionesDB();
        Copias copia;
        Ubicaciones ubicacion;

        if(cmbFormato.getSelectionModel().getSelectedItem() != null){
                
            
            foDB.setFormato(cmbFormato.getSelectionModel().getSelectedItem());
            foDB.connect();
            
            //Buscar ID Formato
            int formId=Integer.parseInt(foDB.executeSearch());
            //Creación de Copia en BD
            coCtrl.CrearCopia(medioID,formId ,descripcion);
            
            //Buscar ID de la copia Creada
            int id = Integer.parseInt(coCtrl.buscarUltimoID());
            
            //Buscar ID de Ubicaciones
            ubDB.setCodUbi(cmbUbicaciones.getSelectionModel().getSelectedItem());
            ubDB.connect();
            String ubiid = ubDB.executeSearch();
            ubicacion = new Ubicaciones(ubiid,cmbUbicaciones.getSelectionModel().getSelectedItem());
            
            //Crea copia en la lista de copias
            copia = new Copias(id,cmbFormato.getSelectionModel().getSelectedItem(),descripcion,ubicacion);
            coCtrl.getCopias().add(copia);
  
            
            //Asocia la Copia con la Ubicacion en la BD
            coCtrl.asociarUbicacionACopia(id, cmbUbicaciones.getSelectionModel().getSelectedItem(), chkDeposito.isSelected());

            popUpExito("Copia creada con éxito.");
            txtMedio.setText("");
            txtDescripcion.setText("");
           
            reloadConsultaMedio();
            
            
        }else{popUpError("Por favor, seleccione un formato para la copia.");}

        
        
        
    }
    @FXML
    private void cancelar(ActionEvent event) {
        Stage x = (Stage) AltaCopia.getScene().getWindow();
        x.close();
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
    
     public void popUpExito(String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, texto);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }
     
     
     public void reloadConsultaMedio(){
         controlMenu.loadTable();
     }
     

    public Label getLblMedio() {
        return lblMedio;
    }

    public void setLblMedio(Label lblMedio) {
        this.lblMedio = lblMedio;
    }

    public Label getLblFormato() {
        return lblFormato;
    }

    public void setLblFormato(Label lblFormato) {
        this.lblFormato = lblFormato;
    }

    public Label getLblUbicaciones() {
        return lblUbicaciones;
    }

    public void setLblUbicaciones(Label lblUbicaciones) {
        this.lblUbicaciones = lblUbicaciones;
    }

    public TextField getTxtMedio() {
        return txtMedio;
    }

    public void setTxtMedio(TextField txtMedio) {
        this.txtMedio = txtMedio;
    }

    public TextArea getTxtDescripcion() {
        return txtDescripcion;
    }

    public void setTxtDescripcion(TextArea txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
    }

    public Button getBtnFinalizar() {
        return btnFinalizar;
    }

    public void setBtnFinalizar(Button btnFinalizar) {
        this.btnFinalizar = btnFinalizar;
    }

    public Button getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(Button btnModificar) {
        this.btnModificar = btnModificar;
    }

    public Button getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(Button btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public Button getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(Button btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public ComboBox getCmbFormato() {
        return cmbFormato;
    }

    public void setCmbFormato(ComboBox cmbFormato) {
        this.cmbFormato = cmbFormato;
    }

    public ComboBox getCmbUbicaciones() {
        return cmbUbicaciones;
    }

    public void setCmbUbicaciones(ComboBox cmbUbicaciones) {
        this.cmbUbicaciones = cmbUbicaciones;
    }

    public CheckBox getChkDeposito() {
        return chkDeposito;
    }

    public void setChkDeposito(CheckBox chkDeposito) {
        this.chkDeposito = chkDeposito;
    }

    public AnchorPane getPane() {
        return AltaCopia;
    }

    public void setPane(AnchorPane Pane) {
        this.AltaCopia = Pane;
    }

    public String getMedioID() {
        return medioID;
    }

    public void setMedioID(String medioID) {
        this.medioID = medioID;
    }

    public String getNomMedio() {
        return nomMedio;
    }

    public void setNomMedio(String nomMedio) {
        this.nomMedio = nomMedio;
    }

    public IOCtrlConsMasivaMedios getControlMenu() {
        return controlMenu;
    }

    public void setControlMenu(IOCtrlConsMasivaMedios controlMenu) {
        this.controlMenu = controlMenu;
    }

   
     
     
     
     
}
