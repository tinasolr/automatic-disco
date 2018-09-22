/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import java.net.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author tinar
 */
public class IOCtrlABMSistemaOperat implements Initializable {

    @FXML    private ListView<String> lstSistemaOperativo;
    @FXML    private Button btnQuitar;
    @FXML    private TextField txtSistOperativo;
    @FXML    private Button btnAgregar;
    @FXML    private Button btnAceptar;
    @FXML    private AnchorPane abmSistOperat;

    @FXML
    private void keyReleased(KeyEvent event) {
        if(null != event.getCode())
            switch (event.getCode()) {
                case UP:
                    lstSistemaOperativo.getSelectionModel().selectPrevious();
                    return;
                case DOWN:
                    lstSistemaOperativo.getSelectionModel().selectNext();
                    return;
                default:
                    break;
            }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }
    }

    @FXML
    private void quitar(ActionEvent event) {
        if(!lstSistemaOperativo.getSelectionModel().getSelectedItem().isEmpty()){
            SistOpDB f = new SistOpDB();
            f.setNombre(lstSistemaOperativo.getSelectionModel().getSelectedItem());
            f.delete();
            loadList();
            txtSistOperativo.clear();
        }
    }

    @FXML
    private void keyTyped(KeyEvent event) {
         if(txtSistOperativo.getText().isEmpty()){
            loadList();
            btnAgregar.setDisable(true);
        }else
            btnAgregar.setDisable(false);

          ObservableList<String> list = FXCollections.observableArrayList();
        ObservableList<String> data = lstSistemaOperativo.getItems();

        for (int i=0; i< data.size(); i++)
            if(data.get(i).toLowerCase().startsWith(txtSistOperativo.getText().toLowerCase())
                    || data.get(i).toLowerCase().contains(txtSistOperativo.getText().toLowerCase()))
                list.add(data.get(i));

        lstSistemaOperativo.setItems(list);
        lstSistemaOperativo.getSelectionModel().selectFirst();
    }

    @FXML
    private void agregar(ActionEvent event) {
        SistOpDB f = new SistOpDB();
        f.setNombre(txtSistOperativo.getText());
        f.write();
        loadList();
        txtSistOperativo.clear();
    }

    @FXML
    private void aceptar(ActionEvent event) {
        Stage x = (Stage) abmSistOperat.getScene().getWindow();
        x.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadList();
    }

    public void loadList(){
        SistOpDB so = new SistOpDB();
        List<SistOpDB> sos = so.read("SistOperativos");
        if(!lstSistemaOperativo.getItems().isEmpty())
            lstSistemaOperativo.getItems().clear();
        sos.forEach((x) -> { lstSistemaOperativo.getItems().add(x.getNombre()); });
        if(sos.size()>0)
            btnQuitar.setDisable(false);
    }
}
