/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import Model.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 *
 * @author tinar
 */
public class IOCtrlModSwConExtras implements Initializable {

    @FXML    private Pane panelSuperior;
    @FXML    private Label lblTituloSw;
    @FXML    private Label lblNombreSw;
    @FXML    private Label lblVersionSw;
    @FXML    private Label lblSistOpSw;
    @FXML    private TextField txtNombreSw;
    @FXML    private TextField txtVersionSw;
    @FXML    private ComboBox<String> cmbSos;
    @FXML    private ListView<String> lstSistemasOp = new ListView<>();
    @FXML    private Button btnAgregarSo;
    @FXML    private Button btnQuitarSo;
    @FXML    private Pane panelInferior;
    @FXML    private TableView<Extras> tblExtras;
    @FXML    private TableColumn<Extras, String> colNombre;
    @FXML    private TableColumn<Extras, String> colVersion;
    @FXML    private TableColumn<Extras, String> colPartes;
    @FXML    private TableColumn<Extras, String> colDescrip;
    @FXML    private Label lblNombre;
    @FXML    private Label lblVersion;
    @FXML    private Label lblPartes;
    @FXML    private Tooltip toolTipPartes;
    @FXML    private Label lblDescripcion;
    @FXML    private TextField txtNombre;
    @FXML    private TextField txtVersion;
    @FXML    private TextField txtPartes;
    @FXML    private TextArea txtDescripcion;
    @FXML    private Button btnAgregar;
    @FXML    private Button btnQuitar;
    @FXML    private Button btnElimTodos;
    @FXML    private Button btnFinalizar;
    @FXML    private Label lblTituloSw1;
    @FXML    private VBox vboxSw;

    private IOCtrlConsMasivaSw consMas;
    private SoftwareCtrl swCtrl;
    private int codigoSw;
    private Software s;
    private ExtrasCtrl exCtrl;

    /*********************Initialize Controller******************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        swCtrl = new SoftwareCtrl();
        if(swCtrl.getSws().isEmpty())
            swCtrl.cargarSoftware();

        s = swCtrl.findSoftware(codigoSw);
        txtNombreSw.setText(s.getNombre());
        txtVersionSw.setText(s.getVersion());
        SistOpDB so = new SistOpDB();
        List<SistOpDB> sos = so.read("SistOperativos");
        if(!cmbSos.getItems().isEmpty())
            cmbSos.getItems().clear();
        if(!lstSistemasOp.getItems().isEmpty())
            lstSistemasOp.getItems().clear();
        sos.forEach((x) -> { cmbSos.getItems().add(x.getNombre()); });
        List<SistOpDB> sos1 = so.sistopDeSoftware(codigoSw);
        sos1.forEach((x) -> { lstSistemasOp.getItems().add(x.getNombre());});
        new AutoCompleteComboBoxListener<>(cmbSos);
        loadTable();
    }

    /***************************JAVAFX FUNCTIONS*******************************/
    @FXML
    private void agregarSistemaOperativo(ActionEvent event) {
        String so = cmbSos.getSelectionModel().getSelectedItem();
        if(!duplicate(so)){
            swCtrl  = new SoftwareCtrl();
            swCtrl.agregarSoDeSw(codigoSw, so);
            lstSistemasOp.getItems().add(so);
        }
    }

    @FXML
    private void quitarSistemaOperativo(ActionEvent event) {
        String so = lstSistemasOp.getSelectionModel().getSelectedItem();
        if(popUpWarning("Está seguro de que desea eliminar el sistema operativo " + so + "?")){
            swCtrl  = new SoftwareCtrl();
            swCtrl.eliminarSoDeSw(codigoSw, so);
            Software s = swCtrl.findSoftware(codigoSw);
            if(lstSistemasOp.getItems().size()>0)
                lstSistemasOp.getItems().removeAll(lstSistemasOp.getSelectionModel().getSelectedItems());
        }
    }

    @FXML
    private void ComboBoxActivo(ActionEvent event) {
        if(cmbSos.getSelectionModel().getSelectedItem()!=null)
            btnAgregarSo.setDisable(false);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage x = (Stage) vboxSw.getScene().getWindow();
        x.close();
    }

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
                    Extras nuevo = new Extras(nombre, version, descrip, partes);
                    if(!duplicateEx(nuevo)){
                        tblExtras.getItems().add(nuevo);
                        exCtrl = new ExtrasCtrl();
                        exCtrl.altaExtra(nombre, descrip, version, partes, codigoSw);
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
            if(popUpWarning("Está seguro de que desea eliminar: " + eliminar.getNombre() + "?")){
                exCtrl = new ExtrasCtrl();
                s = swCtrl.findSoftware(codigoSw);
                exCtrl.eliminarExtra(eliminar.getNombre(), s.getCodigo());
                tblExtras.getItems().remove(eliminar);
            }
        }
    }

    @FXML
    private void eliminarTodos(ActionEvent event) {
        if(popUpWarning("Está seguro de que desea eliminarlos todos?")){
            exCtrl = new ExtrasCtrl();
            s = swCtrl.findSoftware(codigoSw);
            exCtrl.eliminarExtras(s);
            tblExtras.getItems().clear();
        }
    }

    @FXML
    private void finalizar(ActionEvent event) {
        Stage ventana = (Stage) panelSuperior.getScene().getWindow();
        if(s==null){
            swCtrl = new SoftwareCtrl();
            s = swCtrl.findSoftware(codigoSw);
        }
        String nomViejo = s.getNombre();
        String versViejo = s.getVersion();
        String nomNuevo = txtNombreSw.getText();
        String versNuevo = txtVersionSw.getText();
        s.setNombre(nomNuevo);
        s.setVersion(versNuevo);
        List<String> sistOp = new ArrayList<>();
        for(String x : lstSistemasOp.getItems())
            sistOp.add(x);
        s.setSistOp(sistOp);
        boolean add;
        for(Extras x : tblExtras.getItems()){
            add = true;
            for(Extras y : s.getExtras()){
                if(y.getNombre().equalsIgnoreCase(x.getNombre())
                    && y.getVersion().equalsIgnoreCase(x.getVersion())
                    && y.getPartes()==y.getPartes())
                    add = false;
            }
            if(add)
                s.setExtras(x.getNombre(), x.getVersion(), x.getDescrip(), x.getPartes());
        }
        System.out.println(s.toString());
        swCtrl.modSoftware(nomViejo, versViejo, nomNuevo, versNuevo);
        ventana.close();
        consMas.loadTable();
    }

    /***************************OTHER FUNCTIONS*****************************/

    public void loadTable(){
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colPartes.setCellValueFactory(new PropertyValueFactory<>("partes"));
        colDescrip.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        if(s!=null && !s.getExtras().isEmpty())
            tblExtras.getItems().setAll(s.getExtras());
    }

    private boolean duplicate(String sist){
        if (lstSistemasOp.getItems().stream().anyMatch((x) -> (x.equalsIgnoreCase(sist)))) {
            return true;
        }
        return false;
    }

    private boolean duplicateEx(Extras xx){
        if (tblExtras.getItems().stream().anyMatch((e) -> (e.getNombre().equalsIgnoreCase(xx.getNombre())
                && e.getVersion().equalsIgnoreCase(xx.getVersion())
                && e.getPartes()==xx.getPartes()
                && e.getDescrip().equalsIgnoreCase(xx.getDescrip()))))
            return true;

        return false;
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

    public boolean valIngresoExtra(String nombre, String version, String partes)
    {
        boolean res= true;

        if(nombre.equals("")|| version.equals("")|| partes.equals(""))
            res= false;
        return res;
    }

    public void clearFields(){
        txtNombre.setText("");
        txtVersion.setText("");
        txtDescripcion.setText("");
        txtPartes.setText("");
    }

    /*******************Getters & Setters************************************/

    public void setCodigoSw(int codigo) {
        this.codigoSw = codigo;
    }

    public Pane getPanelSuperior() {
        return panelSuperior;
    }

    public void setPanelSuperior(Pane panelSuperior) {
        this.panelSuperior = panelSuperior;
    }

    public Label getLblTituloSw() {
        return lblTituloSw;
    }

    public void setLblTituloSw(Label lblTituloSw) {
        this.lblTituloSw = lblTituloSw;
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

    public Pane getPanelInferior() {
        return panelInferior;
    }

    public void setPanelInferior(Pane panelInferior) {
        this.panelInferior = panelInferior;
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

    public Tooltip getToolTipPartes() {
        return toolTipPartes;
    }

    public void setToolTipPartes(Tooltip toolTipPartes) {
        this.toolTipPartes = toolTipPartes;
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

    public Button getBtnQuitar() {
        return btnQuitar;
    }

    public void setBtnQuitar(Button btnQuitar) {
        this.btnQuitar = btnQuitar;
    }

    public Button getBtnElimTodos() {
        return btnElimTodos;
    }

    public void setBtnElimTodos(Button btnElimTodos) {
        this.btnElimTodos = btnElimTodos;
    }

    public Button getBtnFinalizar() {
        return btnFinalizar;
    }

    public void setBtnFinalizar(Button btnFinalizar) {
        this.btnFinalizar = btnFinalizar;
    }

    public Label getLblTituloSw1() {
        return lblTituloSw1;
    }

    public void setLblTituloSw1(Label lblTituloSw1) {
        this.lblTituloSw1 = lblTituloSw1;
    }

    public SoftwareCtrl getSwCtrl() {
        return swCtrl;
    }

    public void setSwCtrl(SoftwareCtrl swCtrl) {
        this.swCtrl = swCtrl;
    }

    public Software getS() {
        return s;
    }

    public void setS(Software s) {
        this.s = s;
    }

    public ExtrasCtrl getExCtrl() {
        return exCtrl;
    }

    public void setExCtrl(ExtrasCtrl exCtrl) {
        this.exCtrl = exCtrl;
    }

    public void setConsMas(IOCtrlConsMasivaSw consMas) {
        this.consMas = consMas;
    }


}
