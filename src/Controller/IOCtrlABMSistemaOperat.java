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
public class IOCtrlABMSistemaOperat implements Initializable, EventHandler<KeyEvent>  {

    private List<String> soDB = new ArrayList<>();
    @FXML    private ListView<String> lstSistemaOperativo;
    @FXML    private Button btnQuitar;
    @FXML    private TextField txtSistOperativo;
    @FXML    private Button btnAgregar;
    @FXML    private Button btnAceptar;
    @FXML    private AnchorPane abmSistOperat;

    /****Initializes the controller class.**********************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadList();
        txtSistOperativo.setOnKeyPressed((KeyEvent t) -> {
            if(txtSistOperativo.getText().isEmpty())
                btnAgregar.setDisable(true);
            else
                btnAgregar.setDisable(false);
        });
        txtSistOperativo.setOnKeyReleased(this);
        lstSistemaOperativo.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /****************************JAVAFX FUNCTIONS******************************/

    @FXML
    private void quitar(ActionEvent event) {
        if(lstSistemaOperativo.getSelectionModel().getSelectedItem()!=null){

            TextInputDialog dialog = new TextInputDialog(lstSistemaOperativo.getSelectionModel().getSelectedItem());
            dialog.setTitle("Eliminar sistema operativo");
            dialog.setHeaderText("El sistema operativo debe ser reemplazado donde era utilizado.");
            dialog.setContentText("Ingrese el valor por el cual lo quiera "
                    + "reemplazar: ");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                SistOpDB s = new SistOpDB();
                s.connect();
                s.setNombre(lstSistemaOperativo.getSelectionModel().getSelectedItem());
                s.setNewNombre(name);
                /*
                    Update en bd se fija si existe uno con este nuevo nombre,
                    si existe, modifica las tablas que lo usan al id del que
                    corresponde con este nombre nuevo y borra el otro.
                    De no existir, cambia el nombre de este al nuevo nombre.
                */
                s.update();
                soDB.set(lstSistemaOperativo.getSelectionModel().getSelectedIndex(), name);
                loadList();
                txtSistOperativo.clear();
            });
//            if(popUpWarning("Está seguro de que desea eliminar el sistema operativo '" + lstSistemaOperativo.getSelectionModel().getSelectedItem() + "'?")){
//                SistOpDB s = new SistOpDB();
//                s.connect();
//                s.setNombre(lstSistemaOperativo.getSelectionModel().getSelectedItem());
//                s.delete();
//                soDB.remove(lstSistemaOperativo.getSelectionModel().getSelectedItem());
//                loadList();
//                txtSistOperativo.clear();
//            }
        }else{popUpError("Por favor, seleccione un sistema operativo a eliminar.");}
    }

    @FXML
    private void agregar(ActionEvent event) {
        String sistemaOperativo = txtSistOperativo.getText();
        if(sistemaOperativo.matches("[a-zA-Z][0-9a-zA-Z\\s]*")){
            boolean dup = false;
            if(soDB.isEmpty())
                getSOFromDB();
            for (String so : soDB)
                if(so.equalsIgnoreCase(sistemaOperativo)){
                    dup=true;
                    break;
                }
            if(!dup){
                SistOpDB s = new SistOpDB();
                s.connect();
                s.setNombre(sistemaOperativo);
                s.write();
                soDB.add(sistemaOperativo);
                loadList();
                txtSistOperativo.clear();
                btnAgregar.setDisable(true);
            }else{popUpError("Ese sistema operativo ya se encuenta ingresado. Por favor, ingrese otro.");}
        }else{popUpError("Por favor, ingrese un sistema operativo válido.");}
    }

    @FXML
    private void aceptar(ActionEvent event) {
        Stage x = (Stage) abmSistOperat.getScene().getWindow();
        x.close();
    }

    /*********************OTHER FUNCTIONS*************************************/

    public void getSOFromDB(){
        SistOpDB so = new SistOpDB();
        so.connect();
        List<SistOpDB> sos = so.read("SistOperativos");
        for(SistOpDB s : sos)
            soDB.add(s.getNombre());
    }

    public void loadList(){
        if(soDB.isEmpty())
            getSOFromDB();
        if(!lstSistemaOperativo.getItems().isEmpty())
            lstSistemaOperativo.getItems().clear();
        lstSistemaOperativo.getItems().setAll(soDB);
        if(lstSistemaOperativo.getItems().size()>0)
            btnQuitar.setDisable(false);
    }

    private void loadList(String x){
        ObservableList<String> list = FXCollections.observableArrayList();
        List<String> data = soDB;

        for (String f : data)
            if(f.toLowerCase().contains(x.toLowerCase()))
                list.add(f);

        lstSistemaOperativo.getItems().setAll(list);
        lstSistemaOperativo.getSelectionModel().selectFirst();
        if(lstSistemaOperativo.getItems().size()>0)
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
        if(event.getEventType().equals(KeyEvent.KEY_RELEASED) && event.getSource().equals(txtSistOperativo)){
            switch (event.getCode()) {
                case ENTER:
                    agregar(new ActionEvent());
                    break;
                default:
                    if(txtSistOperativo.getText().isEmpty()){
                        loadList();
                        btnAgregar.setDisable(true);
                    }else{
                        loadList(txtSistOperativo.getText());
                        btnAgregar.setDisable(false);
                    }
                    break;
            }
        }
    }
}
