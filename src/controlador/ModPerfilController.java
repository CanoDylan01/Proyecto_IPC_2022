/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Navegacion;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author jovma
 */
public class ModPerfilController implements Initializable {

    @FXML
    public TextField campoCorreo;
    @FXML
    public PasswordField campoContra1;
    @FXML
    public TextField campoUsuario;
    @FXML
    private Button btn_Volver;
    @FXML
    private Button btn_Modificar;
    @FXML
    public DatePicker fechaNacimiento;
    @FXML
    private PasswordField campoContra2;
    @FXML
    private Button btn_seleccionarImagen;
    
    public static Navegacion navegacion;
    
    public User usuario;
    
    public Session sesion;
    @FXML
    public ImageView avatar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void click_Volver(ActionEvent event) {
    }

    @FXML
    private void clickModificar(ActionEvent event) {
    }
    
}
