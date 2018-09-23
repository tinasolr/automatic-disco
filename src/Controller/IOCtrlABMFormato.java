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
public class IOCtrlABMFormato implements Initializable, EventHandler<KeyEvent> {

    private List<String> formatosDB = new ArrayList<>();
    @FXML    private ListView<String> lstFormato;
    @FXML    private Button btnQuitar;
    @FXML    private Button btnAgregar;
    @FXML    private Button btnAceptar;
    @FXML    private TextField txtFormato;
    @FXML    private AnchorPane abmFormato;

    /*******Initializes the controller class.**********************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadList();

        txtFormato.setOnKeyPressed((KeyEvent t) -> {
            if(txtFormato.getText().isEmpty())
                btnAgregar.setDisable(true);
            else
                btnAgregar.setDisable(false);});
        txtFormato.setOnKeyReleased(this);
        lstFormato.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /****************************JAVAFX FUNCTIONS******************************/

        @FXML
    private void quitar(ActionEvent event) {
        if(lstFormato.getSelectionModel().getSelectedItem()!=null){
            if(popUpWarning("Está seguro de que desea eliminar el formato '" + lstFormato.getSelectionModel().getSelectedItem() + "'?")){

                FormatoDB f = new FormatoDB();
                f.connect();
                f.setFormato(lstFormato.getSelectionModel().getSelectedItem());
                f.delete();
                formatosDB.remove(lstFormato.getSelectionModel().getSelectedItem());
                loadList();
                txtFormato.clear();

            }
        }else{
            popUpError("Por favor, seleccione un formato a eliminar.");
        }
    }

    @FXML
    private void agregar(ActionEvent event) {
        if(txtFormato.getText().matches("[a-zA-Z][0-9a-zA-Z\\s]*")){
            boolean dup = false;
            for (String fo : formatosDB) {
                if(fo.equalsIgnoreCase(txtFormato.getText())){
                    dup=true;
                    break;
                }
            }
            if(!dup){
                FormatoDB f = new FormatoDB();
                f.connect();
                f.setFormato(txtFormato.getText());
                f.write();
                formatosDB.add(txtFormato.getText());
                txtFormato.clear();
                btnAgregar.setDisable(true);
                loadList();
            }else{popUpError("Ese formato ya se encuenta ingresado. Por favor, ingrese otro.");}
        }else{popUpError("Por favor, ingrese un formato válido.");}
    }

    @FXML
    private void aceptar(ActionEvent event) {
        Stage x = (Stage) abmFormato.getScene().getWindow();
        x.close();
    }

    /*********************OTHER FUNCTIONS*************************************/

    public void getFormatosFromDB(){
        FormatoDB fo = new FormatoDB();
        fo.connect();
        formatosDB = fo.read("Formatos");
    }

    public void loadList(){
        if(formatosDB.isEmpty())
            getFormatosFromDB();
        if(!lstFormato.getItems().isEmpty())
            lstFormato.getItems().clear();
        lstFormato.getItems().setAll(formatosDB);
        if(lstFormato.getItems().size()>0)
            btnQuitar.setDisable(false);
    }

    public void loadList(String x){
        ObservableList<String> list = FXCollections.observableArrayList();
        List<String> data = formatosDB;

        for (String f : data)
            if(f.toLowerCase().contains(x.toLowerCase()))
                list.add(f);

        lstFormato.getItems().setAll(list);
        lstFormato.getSelectionModel().selectFirst();
        if(lstFormato.getItems().size()>0)
            btnQuitar.setDisable(false);
        else
           btnQuitar.setDisable(true);
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
        if(event.getEventType().equals(KeyEvent.KEY_RELEASED) && event.getSource().equals(txtFormato)){
            if(null == event.getCode()) {
                if(txtFormato.getText().isEmpty()){
                    loadList();
                    btnAgregar.setDisable(true);
                }else{
                    loadList(txtFormato.getText());
                    btnAgregar.setDisable(false);
                }
            } else switch (event.getCode()) {
                case ENTER:
                    agregar(new ActionEvent());
                    break;
                case BACK_SPACE:
                case DELETE:
                    if(txtFormato.getText().isEmpty()){
                        loadList();
                        btnAgregar.setDisable(true);
                    }else{
                        loadList(txtFormato.getText());
                        btnAgregar.setDisable(false);
                    }   break;
                default:
                    if(txtFormato.getText().isEmpty()){
                        loadList();
                        btnAgregar.setDisable(true);
                    }else{
                        loadList(txtFormato.getText());
                        btnAgregar.setDisable(false);
                    }   break;
            }
        }
    }

}

