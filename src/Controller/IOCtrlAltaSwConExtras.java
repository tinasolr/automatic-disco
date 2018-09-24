/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.*;
import Model.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class IOCtrlAltaSwConExtras  implements Initializable {

    private BorderPane mainWindow;
    private int codigoSW;
    private Software sw;
    private final SoftwareCtrl swCtrl = new SoftwareCtrl();
    private final ExtrasCtrl exCtrl = new ExtrasCtrl();
    private final SoftwareDB swDB = new SoftwareDB();
    private List<String> sistOperativos = new ArrayList<>();

    @FXML  private Label lblNombre;
    @FXML  private Label lblVersion;
    @FXML  private Label lblPartes;
    @FXML  private Label lblDescripcion;
    @FXML  private TextField txtNombre;
    @FXML  private TextField txtVersion;
    @FXML  private TextField txtPartes;
    @FXML  private TextArea txtDescripcion;
    @FXML  private Button btnAgregar;
    @FXML  private Button btnElimTodos;
    @FXML  private Tooltip toolTipPartes;
    @FXML  private Button btnQuitar;
    @FXML  private TableView<Extras> tblExtras;
    @FXML  private TableColumn<Extras, String> colNombre;
    @FXML  private TableColumn<Extras, String> colVersion;
    @FXML  private TableColumn<Extras, String> colPartes;
    @FXML  private TableColumn<Extras, String> colDescrip;
    @FXML  private Button btnFinalizar;
    @FXML  private Label lblNombreSw;
    @FXML  private Label lblVersionSw;
    @FXML  private Label lblSistOpSw;
    @FXML  private TextField txtNombreSw;
    @FXML  private TextField txtVersionSw;
    @FXML  private ComboBox<String> cmbSos;
    @FXML  private ListView<String> lstSistemasOp;
    @FXML  private Button btnAgregarSo;
    @FXML  private Button btnQuitarSo;
    @FXML  private Pane panelSuperior;
    @FXML  private Pane panelInferior;
    @FXML  private Button btnCancelar;
    @FXML  private VBox vboxSw;
    @FXML  private TitledPane swPane;
    @FXML  private TitledPane extrasPane;
    @FXML  private ScrollPane scrollPane;
    @FXML  private BorderPane bpSoftware;
    @FXML  private BorderPane bpExtras;

    /*********************Initialize Controller******************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SistOpDB so = new SistOpDB();
        List<SistOpDB> sos = so.read("SistOperativos");
        if(!cmbSos.getItems().isEmpty())
            cmbSos.getItems().clear();
        sos.forEach((x) -> { cmbSos.getItems().add(x.getNombre()); });
        new AutoCompleteComboBoxListener<>(cmbSos);
        loadTable();
    }

    /********************JAVA FX FUNCTIONS**********************************/
    @FXML
    private void agregarExtra(ActionEvent event) {
        //VALIDAR INGRESO POR PANTALLA

        String nombre = txtNombre.getText();
        String version = txtVersion.getText();
        String descrip = txtDescripcion.getText();

        if (valIngresoExtra(nombre, version, txtPartes.getText())){
            if(version.matches("[0-9]+(\\.[0-9]+)*")){
                if(txtPartes.getText().matches("[0-9]+")){
                    int partes = Integer.parseInt(txtPartes.getText());
                    Extras nuevo = new Extras(nombre, descrip, version, partes);
                    if(!duplicateEx(nuevo)){
                        tblExtras.getItems().add(nuevo);
                        clearFields();
                    }else {popUpError("Ya se encuentra ingresado.");}
                }else{ popUpError("Ingrese un número de partes.");}
            }else{ popUpError("Ingresar un número de versión válido.");}
        }else{ popUpError("Rellenar espacios vacios y volver a intentar. ");}

    }

    @FXML
    private void quitarExtra(ActionEvent event) {
        Extras eliminar = tblExtras.getSelectionModel().getSelectedItem();
        if(eliminar==null)
            popUpError("Seleccione un extra a eliminar");
        else{
            tblExtras.getItems().remove(eliminar);
        }
    }

    @FXML
    private void eliminarTodos(ActionEvent event) {
        tblExtras.getItems().clear();
    }

    @FXML
    private void agregarSistemaOperativo(ActionEvent event) {

        boolean agregarEnLista = true;
        if(lstSistemasOp.getItems().isEmpty())
            lstSistemasOp.getItems().add(cmbSos.getSelectionModel().getSelectedItem());
        else {
            for(int x=0;x<lstSistemasOp.getItems().size();x++)
                if(cmbSos.getSelectionModel().getSelectedItem().equals((String)lstSistemasOp.getItems().get(x)))
                    agregarEnLista=false;

            if(agregarEnLista==true)
                lstSistemasOp.getItems().add(cmbSos.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void quitarSistemaOperativo(ActionEvent event) {
        if(lstSistemasOp.getItems().size()>0)
            lstSistemasOp.getItems().removeAll(lstSistemasOp.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void finalizar(ActionEvent event) {

        String nombre = txtNombreSw.getText();
        String version = txtVersionSw.getText();
        Software soft = new Software();

        int cantSO = lstSistemasOp.getItems().size(), cod;

        if(valIngresoSoft(nombre, version,cantSO)){
            if(version.matches("[0-9]+(\\.[0-9]+)*")){
                    if(cantSO>0){

                        //GuardaSW en BD
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

                    }else{ popUpError("Ingrese el sistema operativo.");}
                }else{ popUpError("Ingresar un número de versión válido.");}
            }else{ popUpError("Rellenar espacios vacios y volver a intentar. ");}

    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage x = (Stage) vboxSw.getScene().getWindow();
        x.close();
    }

    @FXML
    private void ComboBoxActivo(ActionEvent event)
    {
        if(cmbSos.getSelectionModel().getSelectedItem()!=null)
            btnAgregarSo.setDisable(false);
    }

    /********************OTHER FUNCTIONS**********************************/

     private boolean duplicateEx(Extras xx){
        if (tblExtras.getItems().stream().anyMatch((e) -> (e.getNombre().equalsIgnoreCase(xx.getNombre())
                && e.getVersion().equalsIgnoreCase(xx.getVersion())
                && e.getPartes()==xx.getPartes()
                && e.getDescrip().equalsIgnoreCase(xx.getDescrip())))) {
            return true;
        }
        return false;
    }

    public void popUpError(String texto){
        Alert alert = new Alert(AlertType.ERROR, texto);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    public void popUpExito(String texto){
        Alert alert = new Alert(AlertType.INFORMATION, texto);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

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

    public void loadTable(){
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colPartes.setCellValueFactory(new PropertyValueFactory<>("partes"));
        colDescrip.setCellValueFactory(new PropertyValueFactory<>("descrip"));
    }

    public void clearFields(){
        txtNombre.setText("");
        txtVersion.setText("");
        txtDescripcion.setText("");
        txtPartes.setText("");
    }

    public boolean valIngresoExtra(String nombre, String version, String partes)
    {
        boolean res= true;

        if(nombre.equals("")|| version.equals("")|| partes.equals(""))
            res= false;
        return res;
    }

    public boolean valIngresoSoft(String nombre, String version, int cantSO)
    {
        boolean res=true;
        if(nombre.equals("")|| version.equals("")|| cantSO==0) res=false;
        return res;
    }

    public void adjustHeightAndWidth(double height, double width){
        vboxSw.setPrefWidth(width);
        vboxSw.setPrefHeight(height);
        bpSoftware.resize(swPane.getWidth(), swPane.getHeight());
        bpExtras.resize(extrasPane.getWidth(), extrasPane.getHeight());
        panelSuperior.resize(bpSoftware.getWidth(), bpSoftware.getHeight());
        panelInferior.resize(bpExtras.getWidth(), bpExtras.getHeight());
    }

    /*******************Getters & Setters**********************************/

    public Label getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(Label lblNombre) {
        this.lblNombre = lblNombre;
    }

    public Label getLblVersion() {
        return lblVersion;
    }

    public void setLblVersion(Label lblVersion) {
        this.lblVersion = lblVersion;
    }

    public Label getLblPartes() {
        return lblPartes;
    }

    public void setLblPartes(Label lblPartes) {
        this.lblPartes = lblPartes;
    }

    public Label getLblDescripcion() {
        return lblDescripcion;
    }

    public void setLblDescripcion(Label lblDescripcion) {
        this.lblDescripcion = lblDescripcion;
    }

    public TextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(TextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public TextField getTxtVersion() {
        return txtVersion;
    }

    public void setTxtVersion(TextField txtVersion) {
        this.txtVersion = txtVersion;
    }

    public TextField getTxtPartes() {
        return txtPartes;
    }

    public void setTxtPartes(TextField txtPartes) {
        this.txtPartes = txtPartes;
    }

    public TextArea getTxtDescripcion() {
        return txtDescripcion;
    }

    public void setTxtDescripcion(TextArea txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
    }

    public Button getBtnAgregar() {
        return btnAgregar;
    }

    public void setBtnAgregar(Button btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public Button getBtnElimTodos() {
        return btnElimTodos;
    }

    public void setBtnElimTodos(Button btnElimTodos) {
        this.btnElimTodos = btnElimTodos;
    }

    public Tooltip getToolTipPartes() {
        return toolTipPartes;
    }

    public void setToolTipPartes(Tooltip toolTipPartes) {
        this.toolTipPartes = toolTipPartes;
    }

    public Button getBtnQuitar() {
        return btnQuitar;
    }

    public void setBtnQuitar(Button btnQuitar) {
        this.btnQuitar = btnQuitar;
    }

    public TableView<Extras> getTblExtras() {
        return tblExtras;
    }

    public void setTblExtras(TableView<Extras> tblExtras) {
        this.tblExtras = tblExtras;
    }

    public TableColumn<Extras, String> getColNombre() {
        return colNombre;
    }

    public void setColNombre(TableColumn<Extras, String> colNombre) {
        this.colNombre = colNombre;
    }

    public TableColumn<Extras, String> getColVersion() {
        return colVersion;
    }

    public void setColVersion(TableColumn<Extras, String> colVersion) {
        this.colVersion = colVersion;
    }

    public TableColumn<Extras, String> getColPartes() {
        return colPartes;
    }

    public void setColPartes(TableColumn<Extras, String> colPartes) {
        this.colPartes = colPartes;
    }

    public TableColumn<Extras, String> getColDescrip() {
        return colDescrip;
    }

    public void setColDescrip(TableColumn<Extras, String> colDescrip) {
        this.colDescrip = colDescrip;
    }

    public Button getBtnFinalizar() {
        return btnFinalizar;
    }

    public void setBtnFinalizar(Button btnFinalizar) {
        this.btnFinalizar = btnFinalizar;
    }

    public Label getLblNombreSw() {
        return lblNombreSw;
    }

    public void setLblNombreSw(Label lblNombreSw) {
        this.lblNombreSw = lblNombreSw;
    }

    public Label getLblVersionSw() {
        return lblVersionSw;
    }

    public void setLblVersionSw(Label lblVersionSw) {
        this.lblVersionSw = lblVersionSw;
    }

    public Label getLblSistOpSw() {
        return lblSistOpSw;
    }

    public void setLblSistOpSw(Label lblSistOpSw) {
        this.lblSistOpSw = lblSistOpSw;
    }

    public TextField getTxtNombreSw() {
        return txtNombreSw;
    }

    public void setTxtNombreSw(TextField txtNombreSw) {
        this.txtNombreSw = txtNombreSw;
    }

    public TextField getTxtVersionSw() {
        return txtVersionSw;
    }

    public void setTxtVersionSw(TextField txtVersionSw) {
        this.txtVersionSw = txtVersionSw;
    }

    public ComboBox<String> getCmbSos() {
        return cmbSos;
    }

    public void setCmbSos(ComboBox<String> cmbSos) {
        this.cmbSos = cmbSos;
    }

    public ListView<String> getLstSistemasOp() {
        return lstSistemasOp;
    }

    public void setLstSistemasOp(ListView<String> lstSistemasOp) {
        this.lstSistemasOp = lstSistemasOp;
    }

    public Button getBtnAgregarSo() {
        return btnAgregarSo;
    }

    public void setBtnAgregarSo(Button btnAgregarSo) {
        this.btnAgregarSo = btnAgregarSo;
    }

    public Button getBtnQuitarSo() {
        return btnQuitarSo;
    }

    public void setBtnQuitarSo(Button btnQuitarSo) {
        this.btnQuitarSo = btnQuitarSo;
    }

    public Pane getPanelSuperior() {
        return panelSuperior;
    }

    public void setPanelSuperior(Pane panelSuperior) {
        this.panelSuperior = panelSuperior;
    }

    public Pane getPanelInferior() {
        return panelInferior;
    }

    public void setPanelInferior(Pane panelInferior) {
        this.panelInferior = panelInferior;
    }

    public int getCodigoSW() {
        return codigoSW;
    }

    public void setCodigoSW(int codigoSW) {
        this.codigoSW = codigoSW;
    }

    public Software getSw() {
        return sw;
    }

    public void setSw(Software sw) {
        this.sw = sw;
    }

    public List<String> getSistOperativos() {
        return sistOperativos;
    }

    public void setSistOperativos(List<String> sistOperativos) {
        this.sistOperativos = sistOperativos;
    }

    public BorderPane getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(BorderPane mainWindow) {
        this.mainWindow = mainWindow;
    }

    public Button getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(Button btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public VBox getVboxSw() {
        return vboxSw;
    }

    public void setVboxSw(VBox vboxSw) {
        this.vboxSw = vboxSw;
    }

    public TitledPane getSwPane() {
        return swPane;
    }

    public void setSwPane(TitledPane swPane) {
        this.swPane = swPane;
    }

    public TitledPane getExtrasPane() {
        return extrasPane;
    }

    public void setExtrasPane(TitledPane extrasPane) {
        this.extrasPane = extrasPane;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public BorderPane getBpSoftware() {
        return bpSoftware;
    }

    public void setBpSoftware(BorderPane bpSoftware) {
        this.bpSoftware = bpSoftware;
    }

    public BorderPane getBpExtras() {
        return bpExtras;
    }

    public void setBpExtras(BorderPane bpExtras) {
        this.bpExtras = bpExtras;
    }


}
