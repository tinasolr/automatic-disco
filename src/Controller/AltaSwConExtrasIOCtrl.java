/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import DAO.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;

/**
 *
 * @author Nico
 */
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


    private int codigoSW;
    private Software sw;
    private final SoftwareCtrl swCtrl = new SoftwareCtrl();
    private final ExtrasCtrl exCtrl = new ExtrasCtrl();
    private List<String> sistOperativos = new ArrayList<>();

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
            if(!version.matches("[0-9]+(\\.[0-9]+)*")){
                if(!txtPartes.getText().matches("[0-9]+")){
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
        int cantSO = lstSistemasOp.getItems().size();
        
        if(valIngresoSoft(nombre, version,cantSO)){    
            if(!version.matches("[0-9]+(\\.[0-9]+)*")){
                    if(cantSO>0){
                        
                        List<String> so = new ArrayList<String>();
                        lstSistemasOp.getSelectionModel().getSelectedItems().addAll(so);
                        
                        //Falta guardar SO en BD
                        
                        //GuardaSW en BD
                        swCtrl.altaSoftware(nombre, version);
                        
                        //GuardaExtras en BD
                        List<Extras> a = tblExtras.getItems();
                        for(Extras e : a)
                        {
                            sw.setExtras(e.getNombre(), e.getVersion(), e.getDescrip(), e.getPartes());
                            exCtrl.altaExtra(e.getNombre(), e.getVersion(), e.getDescrip(), e.getPartes(), codigoSW);
                         }
                        
                        clearFields();
                    }else{ popUp("Ingrese el sistema operativo.");}
                }else{ popUp("Ingresar un número de versión válido.");}
            }else{ popUp("Rellenar espacios vacios y volver a intentar. ");}    
          
        
      
    }

    /*FALTA AGREGAR SISTEMA OPERATIVO A PARTIR DE LA SELECCION DE LA COMBOBOX*/
    @FXML
    private void agregarSistemaOperativo(ActionEvent event) {
        //Tomar selección de la combobox
        //agregar selección a la ListView
        //agregar selección a sistOperativos para poder después cargarlo con el software
    }

    /*FALTA AGREGAR SISTEMA OPERATIVO A PARTIR DE LA SELECCION DE LA COMBOBOX*/
    @FXML
    private void quitarSistemaOperativo(ActionEvent event) {
        //Tomar selección de la ListView
        //Removerla de la listview
        //remover selección de sistOperativos
    }


    @Override
   
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        SistOpDB so = new SistOpDB();
        List<SistOpDB> sos = so.read("SistOperativos");
        for(SistOpDB x : sos)
            lstSistemasOp.getItems().add(x.getNombre());
        
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
    
}
