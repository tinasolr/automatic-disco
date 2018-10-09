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
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Nico
 */
public class IOCtrlABMCopias {
   
    @FXML private Label lblMedio;
    @FXML private Label lblFormato;
    @FXML private TextField txtMedio;
    @FXML private TextArea txtDescripcion;
    @FXML private Button btnFinalizar;
    @FXML private Button btnCancelar;
    @FXML private ComboBox cmbFormato;
    @FXML private AnchorPane Pane;
    
    private int codMedio;
    private String nomMedio;
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        txtMedio.setText(nomMedio);
        FormatoDB formdb = new FormatoDB();
        java.util.List<FormatoDB> form = formdb.read("formatos");
        if(!cmbFormato.getItems().isEmpty())
            cmbFormato.getItems().clear();
        form.forEach((x) -> { cmbFormato.getItems().add(x.getFormato()); });
        new AutoCompleteComboBoxListener<>(cmbFormato);
    }
    
    
 
    
    @FXML
    private void agregar(ActionEvent event) throws SQLException {

        String descripcion = txtDescripcion.getText();

        CopiasCtrl coCtrl = new CopiasCtrl();
        int formId=0;
       
        if(cmbFormato.getSelectionModel().getSelectedItem() != null){
                
                
                
                coCtrl.CrearCopia(codMedio,formId , descripcion);
                
                
                
                popUpExito("Copia creada con éxito.");
                txtMedio.setText("");
                txtDescripcion.setText("");
           
        }else{popUpError("Por favor, seleccione un formato para la copia.");}

        
        
        
    }

    private void cancelar(ActionEvent event) {
        Stage x = (Stage) Pane.getScene().getWindow();
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
    
}
