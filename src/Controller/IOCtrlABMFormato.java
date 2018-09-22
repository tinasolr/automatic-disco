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
public class IOCtrlABMFormato implements Initializable {

    private boolean moveCaretToPos = false;
    @FXML    private ListView<String> lstFormato;
    @FXML    private Button btnQuitar;
    @FXML    private Button btnAgregar;
    @FXML    private Button btnAceptar;
    @FXML    private TextField txtFormato;
    @FXML    private AnchorPane abmFormato;

    @FXML
    private void quitar(ActionEvent event) {
        if(!lstFormato.getSelectionModel().getSelectedItem().isEmpty()){
            FormatoDB f = new FormatoDB();
            f.setFormato(lstFormato.getSelectionModel().getSelectedItem());
            f.delete();
            loadList();
            txtFormato.clear();
        }
    }

    @FXML
    private void keyTyped(KeyEvent event) {
        if(txtFormato.getText().isEmpty()){
            loadList();
            btnAgregar.setDisable(true);
        }else
            btnAgregar.setDisable(false);

        ObservableList<String> list = FXCollections.observableArrayList();
        ObservableList<String> data = lstFormato.getItems();

        for (int i=0; i< data.size(); i++)
            if(data.get(i).toLowerCase().startsWith(txtFormato.getText().toLowerCase())
                    || data.get(i).toLowerCase().contains(txtFormato.getText().toLowerCase()))
                list.add(data.get(i));

        lstFormato.setItems(list);
        lstFormato.getSelectionModel().selectFirst();
    }

    @FXML
    private void agregar(ActionEvent event) {
        FormatoDB f = new FormatoDB();
        f.setFormato(txtFormato.getText());
        f.write();
        loadList();
        txtFormato.clear();
    }

    @FXML
    private void aceptar(ActionEvent event) {
        Stage x = (Stage) abmFormato.getScene().getWindow();
        x.close();
    }

    @FXML
    private void keyReleased(KeyEvent event) {
        if(null != event.getCode())
            switch (event.getCode()) {
                case UP:
                    lstFormato.getSelectionModel().selectPrevious();
                    return;
                case DOWN:
                    lstFormato.getSelectionModel().selectNext();
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
    private void enter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            agregar(new ActionEvent());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadList();
    }

    public void loadList(){
        FormatoDB fo = new FormatoDB();
        List<String> formatos = fo.read("Formatos");
        if(!lstFormato.getItems().isEmpty())
            lstFormato.getItems().clear();
        formatos.forEach((x) -> { lstFormato.getItems().add(x); });
        if(formatos.size()>0)
            btnQuitar.setDisable(false);
    }
}
