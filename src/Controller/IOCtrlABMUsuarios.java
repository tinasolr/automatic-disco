/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import DAO.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;

/**
 * FXML Controller class
 *
 * @author tinar
 */
public class IOCtrlABMUsuarios implements Initializable {

    private IOCtrlLogin login;
    private IOCtrlMenu menu;
    private int access;
    private UsersDB udb = new UsersDB();

    @FXML    private TextField txtNombre;
    @FXML    private TextField txtContras;
    @FXML    private TextField txtUsuario;
    @FXML    private ComboBox<String> cmbUsuario;
    @FXML    private Button btnCancel;
    @FXML    private AnchorPane window;
    @FXML    private RadioButton rdb3;
    @FXML    private ToggleGroup grpAccess;
    @FXML    private RadioButton rdb2;
    @FXML    private RadioButton rdb1;
    @FXML    private RadioButton rdb0;
    @FXML    private Button btnModificar;
    @FXML    private Button btnEliminar;
    @FXML    private Button btnCrear;
    @FXML    private CheckBox chkNom;
    @FXML    private CheckBox chkContr;


    @FXML private void crearUsuario(ActionEvent event) {

        boolean nombreOK = (txtNombre.getText()!=null && !txtNombre.getText().isEmpty());
        boolean userOK = (txtUsuario.getText()!=null && !txtUsuario.getText().isEmpty());
        boolean constraOK = (txtContras.getText()!=null && !txtContras.getText().isEmpty());
        if(!nombreOK || !userOK || !constraOK)
            popUpError("Campos Vacíos", "Por favor, complete los campos que dejó vacíos.");
        else{
            String nombre = txtNombre.getText();
            if(nombre.matches("[A-Za-z]+( +[A-Za-z]+)*")){
                nombre = nombre.replace("  ", " ");
                String user = txtUsuario.getText();
                if(user.matches("\\w+")){
                    String contr = txtContras.getText();
                    if(contr.matches(".{8,20}")){
                        if (grpAccess.getSelectedToggle() != null) {
                            RadioButton selectedRadioButton = (RadioButton) grpAccess.getSelectedToggle();
                            String toggleGroupValue = selectedRadioButton.getId();
                            int newAccess = 0;
                            if(toggleGroupValue.contains("0")){
                                newAccess=0;
                            }else if(toggleGroupValue.contains("1")){
                                newAccess=1;
                            }else if(toggleGroupValue.contains("2")){
                                newAccess=2;
                            }else if(toggleGroupValue.contains("3")){
                                newAccess=3;
                            }
                            UsersDB udb = new UsersDB();
                            udb.setNombre(nombre);
                            udb.setUsername(user);
                            udb.setPassw(contr);
                            udb.setAccess(newAccess);
                            udb.write();
                            Stage x = (Stage) window.getScene().getWindow();
                            x.close();
                        }else{popUpError("Tipo de acceso", "Debe seleccionar el tipo de acceso que tendrá el usuario.");}
                    }else{popUpError("Contraseña", "La contraseña debe ser de 8 a 20 caracteres de longitud.");}
                }else{popUpError("Nombre de usuario", "El nombre de usuario no debe contener espacios. Caracteres válidos: letras, números y '_'.");}
            }else{popUpError("Nombre", "Sólo son válidos caracteres alfabéticos.");}
        }

    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage x = (Stage) window.getScene().getWindow();
        x.close();
    }

    @FXML
    private void modifUsuario(ActionEvent event) {

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Ingrese sus datos");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Nombre de usuario");
        username.setPrefWidth(200);
        PasswordField password = new PasswordField();
        password.setPromptText("Contraseña");
        password.setPrefWidth(200);

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            access = IOCtrlLogin.getUserdb().validarIngreso(usernamePassword.getKey(), usernamePassword.getValue());
        });

        if(access==3){
            String nombre = "";
            String user = cmbUsuario.getSelectionModel().getSelectedItem();
            String pass = "";
            if(chkNom.isSelected())
                nombre = txtNombre.getText();

            if(chkContr.isSelected())
                pass = txtContras.getText();

            if(!nombre.isEmpty())
                udb.modifyName(user, nombre);
            if(!pass.isEmpty())
                udb.modifyPassword(user, pass);
            RadioButton selectedRadioButton = (RadioButton) grpAccess.getSelectedToggle();
            if(selectedRadioButton!=null){
                String toggleGroupValue = selectedRadioButton.getId();
                int newAccess = 0;
                if(toggleGroupValue.contains("0")){
                    newAccess=0;
                }else if(toggleGroupValue.contains("1")){
                    newAccess=1;
                }else if(toggleGroupValue.contains("2")){
                    newAccess=2;
                }else if(toggleGroupValue.contains("3")){
                    newAccess=3;
                }
                udb.modifyAccess(user, newAccess);
                
                Stage x = (Stage) window.getScene().getWindow();
                x.close();
            }
        } else{popUpError("No tenés acceso", "Tu usuario no tiene acceso de borrado de usuarios.");}

    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Ingrese sus datos");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Nombre de usuario");
        username.setPrefWidth(100);
        PasswordField password = new PasswordField();
        password.setPromptText("Contraseña");
        password.setPrefWidth(100);

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            access = IOCtrlLogin.getUserdb().validarIngreso(usernamePassword.getKey(), usernamePassword.getValue());
        });

        if(access==3) {
                IOCtrlLogin.getUserdb().setUsername(cmbUsuario.getSelectionModel().getSelectedItem());
                IOCtrlLogin.getUserdb().delete();
                cmbUsuario.getItems().setAll(new UsersDB().read("users"));
                cmbUsuario.getSelectionModel().selectFirst();
        }else{popUpError("No tenés acceso", "Tu usuario no tiene acceso de borrado de usuarios.");}

    }

    /*** Initializes the controller class. ************************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (url.getPath().contains("ModifUsuario")){
            grpAccess.selectToggle(null);
            cmbUsuario.getItems().setAll(new UsersDB().read("users"));

            chkNom.setOnAction((event)->{
                txtNombre.setEditable(chkNom.isSelected());
            });
            chkContr.setOnAction((event)->{
                txtContras.setEditable(chkContr.isSelected());
            });

        }else if(url.getPath().contains("BajaUsuario")){
            cmbUsuario.getItems().setAll(new UsersDB().read("users"));
            cmbUsuario.getSelectionModel().selectFirst();
        }
    }

    /** OTHER FUNCTIONS ******************************************************/

    public void popUpError(String header, String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR, texto);
        alert.setHeaderText(header);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    /** GETTERS AND SETTERS ***************************************************/

    public IOCtrlLogin getLogin() {
        return login;
    }

    public void setLogin(IOCtrlLogin login) {
        this.login = login;
    }

    public IOCtrlMenu getMenu() {
        return menu;
    }

    public void setMenu(IOCtrlMenu menu) {
        this.menu = menu;
    }


}
