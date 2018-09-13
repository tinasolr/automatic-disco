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

public class AltaSwConExtrasIOCtrl  implements Initializable {

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
    @FXML    private TableView<Extras> tblExtras;
    @FXML    private TableColumn<Extras, String> colNombre;
    @FXML    private TableColumn<Extras, String> colVersion;
    @FXML    private TableColumn<Extras, String> colPartes;
    @FXML    private TableColumn<Extras, String> colDescrip;
    @FXML    private Button btnFinalizar;
    @FXML    private Label lblTituloSw;
    @FXML    private Label lblNombreSw;
    @FXML    private Label lblVersionSw;
    @FXML    private Label lblSistOpSw;
    @FXML    private TextField txtNombreSw;
    @FXML    private TextField txtVersionSw;
    @FXML    private ComboBox<String> cmbSos;
    @FXML    private ListView<String> lstSistemasOp;
    @FXML    private Button btnAgregarSo;
    @FXML    private Button btnQuitarSo;
    @FXML    private Label lblTituloSw1;
    @FXML    private AnchorPane panelSuperior;
    @FXML    private AnchorPane panelInferior;

    private BorderPane mainWindow;
    private int codigoSW;
    private Software sw;
    private final SoftwareCtrl swCtrl = new SoftwareCtrl();
    private final ExtrasCtrl exCtrl = new ExtrasCtrl();
    private final SoftwareDB swDB = new SoftwareDB();
    private List<String> sistOperativos = new ArrayList<>();
    private ConsMasivaSwIOCtrl consMas;

    public AltaSwConExtrasIOCtrl(){
        swCtrl.cargarSoftware();
    }
    public AltaSwConExtrasIOCtrl(int codigo){
        swCtrl.cargarSoftware();
        this.codigoSW = codigo;
        this.sw = swCtrl.findSoftware(codigo);
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
                        clearFields();
                    }else {popUp("Ya se encuentra ingresado.");}
                }else{ popUp("Ingrese un número de partes.");}
            }else{ popUp("Ingresar un número de versión válido.");}
        }else{ popUp("Rellenar espacios vacios y volver a intentar. ");}

    }

     private boolean duplicateEx(Extras xx){
        if (tblExtras.getItems().stream().anyMatch((e) -> (e.getNombre().equalsIgnoreCase(xx.getNombre())
                && e.getVersion().equalsIgnoreCase(xx.getVersion())
                && e.getPartes()==xx.getPartes()
                && e.getDescrip().equalsIgnoreCase(xx.getDescrip())))) {
            return true;
        }


        return false;
    }

    public void popUp(String texto){
        Alert alert = new Alert(AlertType.ERROR, texto);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                alert.close();
            }
    }

    @FXML
    private void quitarExtra(ActionEvent event) {
        Extras eliminar = tblExtras.getSelectionModel().getSelectedItem();
        if(eliminar==null)
            popUp("Seleccione un extra a eliminar");
        else{
            tblExtras.getItems().remove(eliminar);
        }
    }

    @FXML
    private void eliminarTodos(ActionEvent event) {
        tblExtras.getItems().clear();
    }

    @FXML
    private void finalizar(ActionEvent event) {

        //Verificar que los datos ingresados sean válidos o mandar popUp(textoError)
        //Armar un objeto software con el nombre, versión y la lista de sistOperativos
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
                        cod= Integer.parseInt(swDB.executeSearch());                  
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
                            //sw.setExtras(e.getNombre(), e.getVersion(), e.getDescrip(), e.getPartes());
                            exCtrl.altaExtra(e.getNombre(), e.getVersion(), e.getDescrip(), e.getPartes(), cod);
                            soft.setExtras(e.getNombre(), e.getVersion(), e.getDescrip(), e.getPartes());
                         }
                        
                        SoftwareCtrl swCtrl = new SoftwareCtrl();
                        swCtrl.getSws().add(soft);
                        popUp("Software ingresado con éxito.");
                        changeBackToConsultaSw();             
                        
                    }else{ popUp("Ingrese el sistema operativo.");}
                }else{ popUp("Ingresar un número de versión válido.");}
            }else{ popUp("Rellenar espacios vacios y volver a intentar. ");}

    }

    /*FALTA AGREGAR SISTEMA OPERATIVO A PARTIR DE LA SELECCION DE LA COMBOBOX*/
    @FXML
    private void agregarSistemaOperativo(ActionEvent event) {
        //Tomar selección de la combobox
        //agregar selección a la ListView
        //agregar selección a sistOperativos para poder después cargarlo con el software. Esto se soluciono agregando los items de la lista en un array de String cuando se apreta el boton finalizar. Mas Optimo
        
        //String aux = cmbSos.getSelectionModel().getSelectedItem();
        boolean agregarEnLista = true;
        
        

        //if(cmbSos.getSelectionModel().getSelectedItem().toString()!="null")
        //{    
            if(lstSistemasOp.getItems().size()==0) lstSistemasOp.getItems().add(cmbSos.getSelectionModel().getSelectedItem());
            else
            {
                //Averiguo si el SO a agregar no se encuentra en lista
                for(int x=0;x<lstSistemasOp.getItems().size();x++) 
                {    
                    if(cmbSos.getSelectionModel().getSelectedItem().equals((String)lstSistemasOp.getItems().get(x))) agregarEnLista=false;    
                    System.out.println(""+cmbSos.getSelectionModel().getSelectedItem()+"-"+(String)lstSistemasOp.getItems().get(x)+"");
                }
                //Si no esta en la lista, lo agrego
                if(agregarEnLista==true) lstSistemasOp.getItems().add(cmbSos.getSelectionModel().getSelectedItem());
            }
        //}
    }

    /*FALTA AGREGAR SISTEMA OPERATIVO A PARTIR DE LA SELECCION DE LA COMBOBOX*/
    @FXML
    private void quitarSistemaOperativo(ActionEvent event) {
        //Tomar selección de la ListView
        //Removerla de la listview
        //remover selección de sistOperativos ---- Esto se soluciono agregando los items de la lista en un array de String cuando se apreta el boton finalizar. Mas Optimo

        //lstSistemasOp.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        if(lstSistemasOp.getItems().size()>0) lstSistemasOp.getItems().removeAll(lstSistemasOp.getSelectionModel().getSelectedItems());
     
    }
    @FXML
    private void ComboBoxActivo(ActionEvent event) 
    {
        if(cmbSos.getSelectionModel().getSelectedItem()!=null) btnAgregarSo.setDisable(false);
    }

    public void changeBackToConsultaSw(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/ConsultaSoftware.fxml"));
            Node x = loader.load();
            mainWindow.setCenter(x);
        } catch (IOException ex) {
            Logger.getLogger(AltaSwConExtrasIOCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        SistOpDB so = new SistOpDB();
        List<SistOpDB> sos = so.read("SistOperativos");
        if(!cmbSos.getItems().isEmpty())
            cmbSos.getItems().clear();
        sos.forEach((x) -> { cmbSos.getItems().add(x.getNombre()); });
        loadTable();

    }

    public void loadTable(){
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colPartes.setCellValueFactory(new PropertyValueFactory<>("partes"));
        colDescrip.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        if(sw!=null && !sw.getExtras().isEmpty())
            tblExtras.getItems().setAll(sw.getExtras());
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

    public Label getLblTituloSw1() {
        return lblTituloSw1;
    }

    public void setLblTituloSw1(Label lblTituloSw1) {
        this.lblTituloSw1 = lblTituloSw1;
    }

    public AnchorPane getPanelSuperior() {
        return panelSuperior;
    }

    public void setPanelSuperior(AnchorPane panelSuperior) {
        this.panelSuperior = panelSuperior;
    }

    public AnchorPane getPanelInferior() {
        return panelInferior;
    }

    public void setPanelInferior(AnchorPane panelInferior) {
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
}
