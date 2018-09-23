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

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

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
    @FXML    private Label lblSoftware;

    /********Initializes the controller class.*********************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
}
