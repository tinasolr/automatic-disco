/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.*;
import Vista.CopiasTableFormat;
import Model.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.KeyEvent;


/**
 *
 * @author Nico
 */
public class IOCtrlABMCopias implements Initializable, EventHandler<Event>{

    @FXML private AnchorPane AltaCopia;
    @FXML private Label lblBuscarCopia;
    @FXML private TextField txtBuscarCopia;
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
    @FXML private ScrollPane ScrollPaneTabla;
    @FXML private TableView<CopiasTableFormat> TablaCopias;
    @FXML private TableColumn colIDCopia;
    @FXML private TableColumn colFormato;
    @FXML private TableColumn colEnDeposito;
    @FXML private TableColumn colDescripcion;
    
    private String medioID;
    private String nomMedio;
    private IOCtrlConsMasivaMedios controlMenu;
    private List<CopiasTableFormat> CopTabla = new ArrayList();
    /*******Initializes the controller class.**********************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtBuscarCopia.setOnKeyPressed((KeyEvent event) -> {  });
        txtBuscarCopia.setOnKeyReleased(this);
        txtMedio.setText(nomMedio);
        cargarTabla();
        FormatoDB formdb = new FormatoDB();
        List<String> form = formdb.read("formatos");
        if(!cmbFormato.getItems().isEmpty())
            cmbFormato.getItems().clear();

        form.forEach((x)-> {cmbFormato.getItems().add(x);});
        new AutoCompleteComboBoxListener<>(cmbFormato);

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
            if(cmbUbicaciones.getSelectionModel().getSelectedItem() != null){
            
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
                String ubiobs = ubDB.executeSearch();
                ubicacion = new Ubicaciones(cmbUbicaciones.getSelectionModel().getSelectedItem(),ubiobs);

                //Crea copia en la lista de copias
                copia = new Copias(id,cmbFormato.getSelectionModel().getSelectedItem(),descripcion,ubicacion);
                coCtrl.getCopias().add(copia);


                //Asocia la Copia con la Ubicacion en la BD
                coCtrl.asociarUbicacionACopia(id, cmbUbicaciones.getSelectionModel().getSelectedItem(), chkDeposito.isSelected());

                popUpExito("Copia creada con éxito.");
                txtDescripcion.setText("");
                cargarTabla();
            }else{popUpError("Por favor, seleccione una ubicacion para la copia.");}    
        }else{popUpError("Por favor, seleccione un formato para la copia.");}
    }
    
    @FXML
    private void modificarCopia(ActionEvent event) throws SQLException{
        
        if (TablaCopias.getSelectionModel().getSelectedItem()!=null){
            if(cmbFormato.getSelectionModel().getSelectedItem() != null){
                if(cmbUbicaciones.getSelectionModel().getSelectedItem() != null){
                    if(popUpWarning("¿Está seguro de que desea modificar la copia " + TablaCopias.getSelectionModel().getSelectedItem().getId() + "?"))
                    {
                        FormatoDB foDB = new FormatoDB();
                        foDB.setFormato(cmbFormato.getSelectionModel().getSelectedItem());
                        foDB.connect();
                        //Buscar ID Formato
                        int formId=Integer.parseInt(foDB.executeSearch());
                        
                        CopiasDB c = new CopiasDB();
                        c.connect();
                        c.setId(TablaCopias.getSelectionModel().getSelectedItem().getId());
                        c.setMedioid(medioID);
                        c.setFormid(formId);
                        c.setObs(txtDescripcion.getText());                                
                        c.update();
                        CopiasCtrl copctrl = new CopiasCtrl();
                        
                        //Crea Ubicacion
                        UbicacionesDB ubDB = new UbicacionesDB();
                        Ubicaciones ubicacion = new Ubicaciones();
                        ubDB.setCodUbi(cmbUbicaciones.getSelectionModel().getSelectedItem());
                        ubDB.connect();
                        String ubiobs = ubDB.executeSearch();
                        ubicacion = new Ubicaciones(cmbUbicaciones.getSelectionModel().getSelectedItem(),ubiobs);
                        
                        
                        for (Copias co: copctrl.getCopias())
                        {
                            if(co.getId()==TablaCopias.getSelectionModel().getSelectedItem().getId())
                            {
                                co.setFormato(cmbFormato.getSelectionModel().getSelectedItem());
                                co.setObserv(txtDescripcion.getText());
                                co.setUbiDepo(ubicacion);
                            }    
                        }

                        copctrl.modificarUbicacionACopia(TablaCopias.getSelectionModel().getSelectedItem().getId(), ubicacion.getId(), chkDeposito.isSelected());
                        
                        
                        cargarTabla();
                        txtBuscarCopia.setText("");
                    }else{popUpError("No se pudo modidifcar este valor.");}
                }else{popUpError("Por favor, seleccione una ubicacion para la copia.");}    
            }else{popUpError("Por favor, seleccione un formato para la copia.");}        
        }else{popUpError("Seleccione una copia de la tabla para modificar");}
    }
    
    
    @FXML
    private void eliminarCopia(ActionEvent event) throws SQLException{ 
    
     if(TablaCopias.getSelectionModel().getSelectedItem()!=null)
        if(popUpWarning("¿Está seguro de que desea eliminar la copia " + TablaCopias.getSelectionModel().getSelectedItem().getId() + "?"))
        {
            CopiasCtrl c = new CopiasCtrl();
            c.EliminarCopia(TablaCopias.getSelectionModel().getSelectedItem().getId());
            c.desasociarUbicacionACopia(TablaCopias.getSelectionModel().getSelectedItem().getId());
            c.getCopias().remove(TablaCopias.getSelectionModel().getSelectedItem());
            cargarTabla();
            txtBuscarCopia.setText("");
            
        }else{popUpError("No se pudo eliminar este valor.");}    
     else{popUpError("Seleccione una copia de la tabla para eliminar");}
        
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
     
    public void cargarTabla(){
        
        CopiasCtrl copctrl = new CopiasCtrl();
        UbicacionesDB ubidb = new UbicacionesDB();
        
        String enDepo="";
        if(copctrl.getCopias().isEmpty())
            copctrl.cargarCopias(medioID);
        
        if(!CopTabla.isEmpty())
            CopTabla.clear();
        
        colIDCopia.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        colEnDeposito.setCellValueFactory(new PropertyValueFactory<>("enDepo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        ubidb.connect();
        for(Copias c : copctrl.getCopias()){
            ubidb.setCodUbi(c.getUbiDepo().getId());
            
            int ubi= ubidb.find_EnDepo();
            if (ubi==1)enDepo="Si";
            else enDepo="No";
            CopTabla.add(new CopiasTableFormat(c.getId(),c.getFormato(),enDepo,c.getObserv()));
        }
 
        if(copctrl.getCopias()!=null)
            TablaCopias.getItems().setAll(CopTabla);
    } 
    
    public void visualizarAltaCopia(){
        lblBuscarCopia.setVisible(false);
        txtBuscarCopia.setVisible(false);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        btnFinalizar.setVisible(true);
        cmbFormato.setDisable(false);
        cmbUbicaciones.setDisable(false);
        chkDeposito.setVisible(true);
        txtDescripcion.setEditable(true);
        //TablaCopias.setEditable(false);
    }
    public void visualizarVerCopia(){
        lblBuscarCopia.setVisible(false);
        txtBuscarCopia.setVisible(false);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        btnFinalizar.setVisible(false);
        cmbFormato.setDisable(true);
        cmbUbicaciones.setDisable(true);
        chkDeposito.setVisible(false);
        txtDescripcion.setEditable(false);
    }
    
    public void visualizarModificarCopia(){
        lblBuscarCopia.setVisible(true);
        txtBuscarCopia.setVisible(true);
        btnModificar.setVisible(true);
        btnEliminar.setVisible(false);
        btnFinalizar.setVisible(false);
        cmbFormato.setDisable(false);
        cmbUbicaciones.setDisable(false);
        chkDeposito.setVisible(true);
        txtDescripcion.setEditable(true);
    }
    
    public void visualizarEliminarCopia(){
        lblBuscarCopia.setVisible(true);
        txtBuscarCopia.setVisible(true);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(true);
        btnFinalizar.setVisible(false);
        cmbFormato.setDisable(true);
        cmbUbicaciones.setDisable(true);
        chkDeposito.setVisible(true);
        txtDescripcion.setEditable(true);
    }
    
    @Override
    public void handle(Event event) {
        if(event.getEventType().equals(KeyEvent.KEY_RELEASED) && event.getSource().equals(txtBuscarCopia))
            if(txtBuscarCopia.getText().isEmpty() || txtBuscarCopia.getText() == null)
                cargarTodo();
            else
                loadTable(txtBuscarCopia.getText());
    }
    
    public void loadTable(String id){
        if(CopTabla.isEmpty())
            cargarTabla();
        
        String idtabla;
        id = id.toLowerCase(Locale.ROOT);
        
        List<CopiasTableFormat> aux = new ArrayList();

        for(CopiasTableFormat c: CopTabla){
            idtabla = String.valueOf(c.getId()).toLowerCase(Locale.ROOT);
            if(idtabla.contains(id)) aux.add(c);
        }
        
        
        TablaCopias.getItems().setAll(aux);

    }
    
    public void cargarTodo(){
    
        colIDCopia.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        colEnDeposito.setCellValueFactory(new PropertyValueFactory<>("enDepo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
         
        if(!CopTabla.isEmpty()) TablaCopias.getItems().setAll(CopTabla);
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
