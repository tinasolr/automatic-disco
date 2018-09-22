/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *eventL*laimport javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
ss
 *
 * @author tinar
 */
public class IOCtrlABMUbicaciones implements Initializable {

    @FXML    private Button btnQuitar;
    @FXML    private TextField txtCodigo;
    @FXML    private Button btnAgregar;
    @FXML    private Button btnAceptar;
    @FXML    private TextArea txtDescripcion;
    @FXML    private TableView<?> tblUbicaciones;
    @FXML    private Label lblCodigo;
    @FXML    private AnchorPane abmUbicaciones;

    @FXML
    private void quitar(ActionEvent event) {
    }

    @FXML
    private void keyTyped(KeyEvent event) {
         if(txtCodigo.getText().isEmpty())
            btnAgregar.setDisable(true);
        else
            btnAgregar.setDisable(false);
    }

    @FXML
    private void agregar(ActionEvent event) {
    }

    @FXML
    private void aceptar(ActionEvent event) {
        Stage x = (Stage) abmUbicaciones.getScene().getWindow();
        x.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
