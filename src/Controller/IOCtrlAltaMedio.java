/*
 * Copyright (C) 2018 tinar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package Controller;

import DAO.*;
import Model.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.*;
import javafx.stage.*;


/**
 * FXML Controller class
 *
 * @author tinar
 */
public class IOCtrlAltaMedio implements Initializable {

    private BorderPane mainWindow;
    @FXML    private TitledPane tpaneDatosMedio;
    @FXML    private TextField txtCodigo;
    @FXML    private TextField txtNombre;
    @FXML    private Label lblCodigo;
    @FXML    private Label lblNombre;
    @FXML    private Label lblImagen;
    @FXML    private ImageView image;
    @FXML    private Label lblFormato;
    @FXML    private Label lblUbicacion;
    @FXML    private Label lblObservaciones;
    @FXML    private Label lblPartes;
    @FXML    private ComboBox<?> cmbFormato;
    @FXML    private Label lblEmpaque;
    @FXML    private TitledPane tpaneInfoFisica;
    @FXML    private ComboBox<?> cmbUbicacion;
    @FXML    private TextField txtPartes;
    @FXML    private CheckBox rdbCaja;
    @FXML    private CheckBox rdbManual;
    @FXML    private TextArea txtObservaciones;
    @FXML    private TitledPane tPaneSwMedio;
    @FXML    private TableView<?> tblSoftwareDisp;
    @FXML    private TableColumn<?, ?> colNombre;
    @FXML    private TableColumn<?, ?> colVersion;
    @FXML    private ListView<?> lstSwContenido;
    @FXML    private Button btnAgregar;
    @FXML    private Button btnQuitar;
    @FXML    private Button btnIngresar;
    @FXML    private Button btnCancelar;
    @FXML    private TextField txtBusqueda;
    
    private MediosDB meDB = new MediosDB();
    private FormatoDB foDB = new FormatoDB();

    /********Initializes the controller class.*********************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**********************JAVAFX FUNCTIONS**********************************/

        @FXML
    private void agregarImagen(MouseEvent event) {
    }

    @FXML
    private void agregar(MouseEvent event) {
    }

    @FXML
    private void quitar(MouseEvent event) {
    }

    @FXML
    private void ingresarMedio(MouseEvent event) {
        
        String id = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String formato = (String) cmbFormato.getSelectionModel().getSelectedItem();
        String ubicacion = (String) cmbUbicacion.getSelectionModel().getSelectedItem();
        String imagen, observ = txtObservaciones.getText();
        int partes = Integer.parseInt(txtPartes.getText()), formid, origen;
        boolean manual=false, caja=false;
        

        if(id.matches("[0-9]+(\\.[0-9]+)*")){
            if(nombre.matches("[0-9]+(\\.[0-9]+)*")){
                if(formato!= null && ubicacion!=null){
                   
                           
                    if(rdbCaja.isSelected()) caja = true;
                    if(rdbManual.isSelected()) manual = true;
                    
                    //formid= Integer.parseInt(foDB.se(formato));
                    
        /*            
        meDB.setId(id);
        meDB.setNombre(nombre);
        meDB.setPartes(partes);
        meDB.setManual(manual);
        meDB.setCaja(caja);
        meDB.setImagen(imagen);
        meDB.setObserv(observ);
        meDB.setFormid(formid);
        meDB.setOrigen(origen); */
                       
        
                        /* //GuardaSW en BD
                        swCtrl.altaSoftware(nombre, version);
                        soft.setNombre(nombre);
                        soft.setVersion(version);
                        //GuardaSO en BD
                        swDB.setNombre(nombre);
                        swDB.setVersion(version);
                        swDB.connect();
                        cod = Integer.parseInt(swDB.executeSearch());
                        List<String> sistOp = new ArrayList<>();
                        for(String x : lstSistemasOp.getItems()){
                           swCtrl.agregarSoDeSw(cod, x);
                           sistOp.add(x);
                        }
                        soft.setSistOp(sistOp);
                        //GuardaExtras en BD
                        List<Extras> a = tblExtras.getItems();
                        for(Extras e : a)
                        {
                            exCtrl.altaExtra(e.getNombre(), e.getVersion(), e.getDescrip(), e.getPartes(), cod);
                            soft.setExtras(e.getNombre(), e.getVersion(), e.getDescrip(), e.getPartes());
                         }

                        SoftwareCtrl swCtrl = new SoftwareCtrl();
                        swCtrl.getSws().add(soft);
                        popUpExito("Software ingresado con éxito.");
                        changeBackToConsultaSw();
*/
                }else{ popUpError("Ingrese el sistema operativo.");}
            }else{ popUpError("Ingresar un número de versión válido.");}
         }else{ popUpError("Rellenar espacios vacios y volver a intentar. ");}
        
        
    }
         
    @FXML
    private void cancelar(MouseEvent event) {
    }
  
    
    /*********************OTHER FUNCTIONS*************************************/

    public void changeBackToConsultaSw(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/ConsultaSoftware.fxml"));
            Node x = loader.load();
            mainWindow.setCenter(x);
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlAltaSwConExtras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /********************GETTERS & SETTERS************************************/

    public BorderPane getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(BorderPane mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void popUpError(String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR, texto);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

}
