/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Navegacion;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author dcano
 */
public class LoginController implements Initializable {

    @FXML
    private Button btn_inisesion;
    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoPassword;
    @FXML
    private Hyperlink link_Registro;
    
    public static Navegacion navegacion;   
    public User usuario; 
    public Session sesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            navegacion = Navegacion.getSingletonNavegacion();
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void ini_sesion(MouseEvent event) {
        iniciarSesion();
    }

    private void iniciarSesion() {
        if ("".equals(campoUsuario.getText()) || "".equals(campoPassword.getText())) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Campos vacíos.\n Por favor complete los campos.");
            alert.showAndWait();
        }   
        else
        {
            try 
            {
                FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/Menu.fxml"));
                Parent root1= (Parent)fxmlLoaderMenu.load();
                MenuController menu = (MenuController) fxmlLoaderMenu.getController();

                usuario = navegacion.loginUser(campoUsuario.getText(), campoPassword.getText());

                if (usuario != null ) 
                {
                    menu.navegacion = this.navegacion;
                    menu.sesion = this.sesion;
                    menu.usuario = this.usuario;
                    
                    Stage stage= new Stage();
                    stage.setScene(new Scene(root1));
                    stage.setTitle("Menu");
                    stage.centerOnScreen();
                    stage.show();

                    ((Stage)btn_inisesion.getScene().getWindow()).close();
                }
                else 
                {
                    
                }
                
            }
            catch(Exception e) 
            {
                System.out.print(e);
            }
        }
    }  

    @FXML
    private void click_Registro(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoaderRegistro= new FXMLLoader(getClass().getResource("/vistas/Registro.fxml"));
        Parent root1= (Parent)fxmlLoaderRegistro.load();
        RegistroController registro = (RegistroController) fxmlLoaderRegistro.getController();
        registro.navegacion = this.navegacion;
        
        Stage stage= new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Registro de usuarios");
        stage.centerOnScreen();
        
        stage.show();

        ((Stage)btn_inisesion.getScene().getWindow()).close();
    }
}
