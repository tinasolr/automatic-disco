/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.*;
import ar.com.fdvs.dj.core.*;
import ar.com.fdvs.dj.core.layout.*;
import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.builders.*;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;

public class IOCtrlConsMasivaSw implements Initializable, EventHandler<Event> {

    private SoftwareCtrl swCtrl;
    private IOCtrlMenu controlMenu;
    private int access;

    @FXML    private AnchorPane showSw;
    @FXML    private TableView<Software> tblSoftware;
    @FXML    private TableColumn<Software, String> colCodigoSw;
    @FXML    private TableColumn<Software, String> colNombreSw;
    @FXML    private TableColumn<Software, String> colVersionSw;
    @FXML    private TableColumn<Software, String> colSistOpSw;
    @FXML    private ContextMenu mnuDerecho;
    @FXML    private MenuItem derVer;
    @FXML    private MenuItem derModificar;
    @FXML    private MenuItem derEliminar;
    @FXML    private TextField txtFiltrar;
    @FXML    private RadioButton rdbTodos;
    @FXML    private ToggleGroup groupFiltrar;
    @FXML    private RadioButton rdbCodigo;
    @FXML    private RadioButton rdbSistOp;
    @FXML    private RadioButton rdbNombre;
    @FXML    private RadioButton rdbVersion;
    @FXML    private Button btnFiltrar;
    @FXML    private BorderPane mainWindow;
    @FXML    private Button btnExport;



/***********************INITIALIZE CONTROLLER CLASS***************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTable();

        txtFiltrar.setOnKeyPressed((KeyEvent event) -> {  });
        txtFiltrar.setOnKeyReleased(this);
        derVer.setDisable(true);
        derModificar.setDisable(true);
        derEliminar.setDisable(true);
        rdbCodigo.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        rdbTodos.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        rdbNombre.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        rdbVersion.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
        rdbSistOp.setOnAction((event) -> {loadTable(txtFiltrar.getText());});
    }

/*******************RIGHT CLICK MENU*****************************************/

    @FXML
    private void exportarTabla(ActionEvent event) {
        try {
            SoftwareReport report = new SoftwareReport(tblSoftware.getItems());

            JasperPrint jp = report.getReport();
            //JasperViewer jasperViewer = new JasperViewer(jp);
            //jasperViewer.setVisible(true);
            JasperViewer.viewReport(jp, false);
        } catch (JRException | ColumnBuilderException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @FXML    private void consIndividual(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ConsultaIndividualSoftware.fxml"));
            IOCtrlConsultaIndividualSoftware cis = new IOCtrlConsultaIndividualSoftware();
            cis.setCodigoSW(tblSoftware.getSelectionModel().getSelectedItem().getCodigo());
            cis.setConsMasivaSw(this);
            loader.setController(cis);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ver Software");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IOCtrlConsMasivaSw.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modSoftware(ActionEvent event) {
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/ModSoftware.fxml"));
            IOCtrlModSwConExtras msce = new IOCtrlModSwConExtras();
            msce.setCodigoSw(tblSoftware.getSelectionModel().getSelectedItem().getCodigo());
            msce.setConsMas(this);
            loader.setController(msce);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Modificar Software");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void elimSoftware(ActionEvent event) {
        if(popUpWarning("Está seguro de que desea eliminar el software?")){
            swCtrl = new SoftwareCtrl();
            int codigo = tblSoftware.getSelectionModel().getSelectedItem().getCodigo();
            swCtrl.elimSoftware(codigo);
            loadTable();
        }
    }

/************************OTHER FUNCTIONS**************************************/

    @FXML
    public void loadTable(){
        swCtrl = new SoftwareCtrl();
        swCtrl.cargarSoftware();
        colCodigoSw.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombreSw.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVersionSw.setCellValueFactory(new PropertyValueFactory<>("version"));
        colSistOpSw.setCellValueFactory(new PropertyValueFactory<>("sistOp"));

        if(swCtrl.getSws()!=null)
            tblSoftware.getItems().setAll(swCtrl.getSws());
        rdbTodos.setSelected(true);
    }

    public void loadTable(String searchTerm){
        searchTerm = searchTerm.toLowerCase(Locale.ROOT);

        SoftwareCtrl s = new SoftwareCtrl();
        if(s.getSws().isEmpty())
            s.cargarSoftware();

        List<Software> soft = new ArrayList<>();

        boolean searchAll = rdbTodos.isSelected();
        boolean searchCodigo = rdbCodigo.isSelected();
        boolean searchSistOperativo = rdbSistOp.isSelected();
        boolean searchNombre = rdbNombre.isSelected();
        boolean searchVersion = rdbVersion.isSelected();

        for(Software x : s.getSws()){
            String codigo = String.valueOf(x.getCodigo());
            String nombre = x.getNombre().toLowerCase(Locale.ROOT);
            String version = x.getVersion().toLowerCase(Locale.ROOT);

            if(searchCodigo && codigo.contains(searchTerm))
                soft.add(x);
            else if(searchNombre && nombre.contains(searchTerm))
                soft.add(x);
            else if(searchVersion && version.contains(searchTerm))
                soft.add(x);
            else if(searchSistOperativo){
                for(String y : x.getSistOp()){
                    y = y.toLowerCase();
                    if(y.contains(searchTerm)){
                        soft.add(x);
                        break;
                    }
                }
            }else if(searchAll){
                if(codigo.contains(searchTerm) || nombre.contains(searchTerm) || version.contains(searchTerm)){
                    soft.add(x);
                }else{
                    for(String y : x.getSistOp()){
                        y = y.toLowerCase();
                        if(y.contains(searchTerm)){
                            soft.add(x);
                            break;
                        }
                    }
                }
            }
        }
        tblSoftware.getItems().setAll(soft);
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
        btnFiltrar.setDisable(x);
        rdbCodigo.setDisable(x);
        rdbNombre.setDisable(x);
        rdbSistOp.setDisable(x);
        rdbVersion.setDisable(x);
        rdbTodos.setDisable(x);
    }

    public void disableMenuItems(){
        if(access==0){
            derVer.setDisable(false);
            derModificar.setDisable(true);
            derEliminar.setDisable(true);
        }else{
            derVer.setDisable(false);
            derModificar.setDisable(false);
            derEliminar.setDisable(false);
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
/******************************GETTERS AND SETTERS*****************************/

    public IOCtrlMenu getControlMenu() {     return controlMenu;   }
    public void setControlMenu(IOCtrlMenu controlMenu) {     this.controlMenu = controlMenu;   }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    protected void exportToJRXML(JasperReport jr, DynamicReport dr, Map params) throws JRException {
        if (jr != null){
            DynamicJasperHelper.generateJRXML(jr, "UTF-8",System.getProperty("user.dir")+ "/target/reports/" + this.getClass().getName() + ".jrxml");
        } else {
            DynamicJasperHelper.generateJRXML(dr, new ClassicLayoutManager(), params, "UTF-8",System.getProperty("user.dir")+ "/target/reports/" +this.getClass().getName() + ".jrxml");
        }
    }

}
