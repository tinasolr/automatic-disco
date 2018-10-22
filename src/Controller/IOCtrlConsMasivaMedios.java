/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Vista.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author tinar
 */
public class IOCtrlConsMasivaMedios implements Initializable, EventHandler<Event> {

    private MediosCtrl meCtrl;
    private IOCtrlMenu controlmenu;
    private int access;

    @FXML    private TextField txtFiltrar;
    @FXML    private RadioButton rdbTodos;
    @FXML    private ToggleGroup groupFiltrar;
    @FXML    private RadioButton rdbCodigo;
    @FXML    private RadioButton rdbNombre;
    @FXML    private RadioButton rdbFormato;
    @FXML    private RadioButton rdbUbicacion;
    @FXML    private BorderPane mainWindow;
    @FXML    private AnchorPane showMedios;
    @FXML    private TableView<MediosTableFormat> tblMedios;
    @FXML    private ContextMenu mnuDerecho;
    @FXML    private Menu derAdministrar;
    @FXML    private MenuItem derVerCopias;
    @FXML    private MenuItem derAltaCopia;
    @FXML    private MenuItem derModifCopia;
    @FXML    private MenuItem derElimCopia;
    @FXML    private MenuItem derVer;
    @FXML    private MenuItem derModificar;
    @FXML    private MenuItem derEliminar;
    @FXML    private TableColumn<MediosTableFormat, String> colCodigo;
    @FXML    private TableColumn<MediosTableFormat, String> colNombre;
    @FXML    private TableColumn<MediosTableFormat, String> colFormato;
    @FXML    private TableColumn<MediosTableFormat, Boolean> colOriginal;
    @FXML    private TableColumn<MediosTableFormat, String> colUbicacion;
    @FXML    private TableColumn<MediosTableFormat, Boolean> colEnDepo;
    @FXML    private TableColumn<MediosTableFormat, Integer> colCantCopias;
    @FXML    private CheckBox chkOriginal;
    @FXML    private Button btnTodo;
    @FXML    private CheckBox chkGuardado;
    
    

    /************Initializes the controller class.*****************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtFiltrar.setOnKeyPressed((KeyEvent event) -> {  });
        txtFiltrar.setOnKeyReleased(this);
        rdbCodigo.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        rdbTodos.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        rdbNombre.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        rdbFormato.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        rdbUbicacion.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        chkGuardado.setOnAction((event)->{loadTable(txtFiltrar.getText());});
        chkOriginal.setOnAction((event)->{loadTable(txtFiltrar.getText());});
    }

    /***************************JAVAFX FUNCTIONS*******************************/
        @FXML
    public void loadTable() {
        meCtrl = new MediosCtrl();
        meCtrl.formatMediosForTable();

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        colOriginal.setCellValueFactory(new PropertyValueFactory(("original")));
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubiDepo"));
        colEnDepo.setCellValueFactory(new PropertyValueFactory<>("enDepo"));
        colCantCopias.setCellValueFactory(new PropertyValueFactory<>("copias"));


        if(meCtrl.getMedios()!=null)
            tblMedios.getItems().setAll(meCtrl.getMediosForTable());
        rdbTodos.setSelected(true);
    }

    @FXML
    private void consIndividual(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ConsultaIndividualMedio.fxml"));
            IOCtrlConsultaIndividualMedio cis = new IOCtrlConsultaIndividualMedio();
            cis.setCodigoMedio(tblMedios.getSelectionModel().getSelectedItem().getCodigo());
            cis.setControlMenu(controlmenu);
            cis.setConsmasiva(this);
            loader.setController(cis);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ver Medio");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void modMedio(ActionEvent event) {
         try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ModMedio.fxml"));
            IOCtrlModMedio msce = new IOCtrlModMedio();
            msce.setCodigoMedio(tblMedios.getSelectionModel().getSelectedItem().getCodigo());
            msce.setConsmasiva(this);
            loader.setController(msce);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Modificar Medio");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void elimMedio(ActionEvent event) {
        if(popUpWarning("Está seguro de que desea eliminar el medio?")){
            meCtrl = new MediosCtrl();
            String codigo = tblMedios.getSelectionModel().getSelectedItem().getCodigo();
            meCtrl.elimMedio(codigo);
            loadTable();
        }
    }

    @FXML
    private void verCopias(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ABMCopias.fxml"));
            IOCtrlABMCopias CtrlCopia =  new IOCtrlABMCopias();
            CtrlCopia.setNomMedio(tblMedios.getSelectionModel().getSelectedItem().getNombre());
            CtrlCopia.setMedioID(tblMedios.getSelectionModel().getSelectedItem().getCodigo());
            CtrlCopia.setControlMenu(this);
      
            loader.setController(CtrlCopia);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ver Copias");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
            CtrlCopia.visualizarVerCopia();
                  
            
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    public void altaCopia(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ABMCopias.fxml"));
            IOCtrlABMCopias CtrlCopia =  new IOCtrlABMCopias();
            CtrlCopia.setNomMedio(tblMedios.getSelectionModel().getSelectedItem().getNombre());
            CtrlCopia.setMedioID(tblMedios.getSelectionModel().getSelectedItem().getCodigo());
            CtrlCopia.setControlMenu(this);

            loader.setController(CtrlCopia);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Crear Copias");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
            CtrlCopia.visualizarAltaCopia();
                  
            
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    private void ModificarCopia(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ABMCopias.fxml"));
            IOCtrlABMCopias CtrlCopia =  new IOCtrlABMCopias();
            CtrlCopia.setNomMedio(tblMedios.getSelectionModel().getSelectedItem().getNombre());
            CtrlCopia.setMedioID(tblMedios.getSelectionModel().getSelectedItem().getCodigo());
            CtrlCopia.setControlMenu(this);
      
            loader.setController(CtrlCopia);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Modificar Copias");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
            CtrlCopia.visualizarModificarCopia();
                  
            
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    @FXML
    private void EliminarCopia(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ABMCopias.fxml"));
            IOCtrlABMCopias CtrlCopia =  new IOCtrlABMCopias();
            CtrlCopia.setNomMedio(tblMedios.getSelectionModel().getSelectedItem().getNombre());
            CtrlCopia.setMedioID(tblMedios.getSelectionModel().getSelectedItem().getCodigo());
            CtrlCopia.setControlMenu(this);
      
            loader.setController(CtrlCopia);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Eliminar Copias");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
            CtrlCopia.visualizarEliminarCopia();
                  
            
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    /************************OTHER FUNCTIONS**********************************/

    public void loadTable(String searchTerm){
        searchTerm = searchTerm.toLowerCase(Locale.ROOT);

        MediosCtrl s = new MediosCtrl();
        if(s.getMediosForTable().isEmpty())
            s.formatMediosForTable();

        List<MediosTableFormat> med = new ArrayList<>();

        boolean searchAll = rdbTodos.isSelected();
        boolean searchCodigo = rdbCodigo.isSelected();
        boolean searchFormato = rdbFormato.isSelected();
        boolean searchNombre = rdbNombre.isSelected();
        boolean searchUbicacion = rdbUbicacion.isSelected();

        for(MediosTableFormat x : s.getMediosForTable()){
            String codigo = String.valueOf(x.getCodigo());
            String nombre = x.getNombre().toLowerCase(Locale.ROOT);
            String formato = x.getFormato().toLowerCase(Locale.ROOT);
            String ubicacion = x.getUbiDepo().toLowerCase(Locale.ROOT);

            if(!chkGuardado.isSelected() && !chkOriginal.isSelected()){
                if(searchCodigo && codigo.contains(searchTerm))
                    med.add(x);
                else if(searchNombre && nombre.contains(searchTerm))
                    med.add(x);
                else if(searchFormato && formato.contains(searchTerm))
                    med.add(x);
                else if(searchUbicacion && ubicacion.contains(searchTerm)){
                    med.add(x);
                }else if(searchAll){
                    if(codigo.contains(searchTerm) || nombre.contains(searchTerm) || formato.contains(searchTerm) || ubicacion.contains(searchTerm))
                        med.add(x);
                }
            }else if (chkOriginal.isSelected() && chkGuardado.isSelected()) {
                if(searchTerm.isEmpty()){
                    if(!x.isOriginal().equalsIgnoreCase("no") && !x.isEnDepo().equalsIgnoreCase("no"))
                        med.add(x);
                }else{
                    if(!x.isOriginal().equalsIgnoreCase("no") && !x.isEnDepo().equalsIgnoreCase("no")){
                        if(searchCodigo && codigo.contains(searchTerm))
                            med.add(x);
                        else if(searchNombre && nombre.contains(searchTerm))
                            med.add(x);
                        else if(searchFormato && formato.contains(searchTerm))
                            med.add(x);
                        else if(searchUbicacion && ubicacion.contains(searchTerm)){
                            med.add(x);
                        }else if(searchAll){
                            if(codigo.contains(searchTerm) || nombre.contains(searchTerm) || formato.contains(searchTerm) || ubicacion.contains(searchTerm))
                                med.add(x);
                        }
                    }
                }
            }else if (chkGuardado.isSelected()) {
                if(searchTerm.isEmpty()){
                    if(!x.isEnDepo().equalsIgnoreCase("no"))
                        med.add(x);
                }else{
                    if(!x.isEnDepo().equalsIgnoreCase("no")){
                        if(searchCodigo && codigo.contains(searchTerm))
                            med.add(x);
                        else if(searchNombre && nombre.contains(searchTerm))
                            med.add(x);
                        else if(searchFormato && formato.contains(searchTerm))
                            med.add(x);
                        else if(searchUbicacion && ubicacion.contains(searchTerm)){
                            med.add(x);
                        }else if(searchAll){
                            if(codigo.contains(searchTerm) || nombre.contains(searchTerm) || formato.contains(searchTerm) || ubicacion.contains(searchTerm))
                                med.add(x);
                        }
                    }
                }
            }else if (chkOriginal.isSelected()) {
                if(searchTerm.isEmpty()){
                    if(!x.isOriginal().equalsIgnoreCase("no"))
                        med.add(x);
                }else{
                    if(!x.isOriginal().equalsIgnoreCase("no")){
                        if(searchCodigo && codigo.contains(searchTerm))
                            med.add(x);
                        else if(searchNombre && nombre.contains(searchTerm))
                            med.add(x);
                        else if(searchFormato && formato.contains(searchTerm))
                            med.add(x);
                        else if(searchUbicacion && ubicacion.contains(searchTerm)){
                            med.add(x);
                        }else if(searchAll){
                            if(codigo.contains(searchTerm) || nombre.contains(searchTerm) || formato.contains(searchTerm) || ubicacion.contains(searchTerm))
                                med.add(x);
                        }
                    }
                }
            }

        }
        tblMedios.getItems().setAll(med);
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

    public void disableSearchItems(boolean x){
        txtFiltrar.setDisable(x);
        btnTodo.setDisable(x);
        rdbCodigo.setDisable(x);
        rdbNombre.setDisable(x);
        rdbFormato.setDisable(x);
        rdbUbicacion.setDisable(x);
        rdbTodos.setDisable(x);
        chkGuardado.setDisable(x);
        chkOriginal.setDisable(x);
        tblMedios.setDisable(x);
    }

    public void disableMenuItems(){
        if(access==0){
            derVer.setDisable(false);
            derModificar.setDisable(true);
            derEliminar.setDisable(true);
            derAltaCopia.setDisable(true);
            disableSearchItems(true);
        }else{
            derVer.setDisable(false);
            derModificar.setDisable(false);
            derEliminar.setDisable(false);
            derAltaCopia.setDisable(false);
            disableSearchItems(false);
            loadTable();
        }
    }

/*******************************EVENT HANDLER**********************************/

    @Override
    public void handle(Event event) {
        if(event.getEventType().equals(KeyEvent.KEY_RELEASED) && event.getSource().equals(txtFiltrar))
            if(txtFiltrar.getText().isEmpty() || txtFiltrar.getText() == null)
                loadTable();
            else
                loadTable(txtFiltrar.getText());
    }

    public void setControlMenu(IOCtrlMenu aThis) {
        this.controlmenu = aThis;
    }

    public IOCtrlMenu setControlMenu() {
        return controlmenu;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }


}
