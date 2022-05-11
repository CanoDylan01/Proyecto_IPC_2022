/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Navegacion;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author dcano
 */
public class RegistroController implements Initializable {

    @FXML
    private TextField campoCorreo;
    @FXML
    private PasswordField campoContra1;
    @FXML
    private TextField campoUsuario;
    @FXML
    private Button btn_Volver;
    @FXML
    private Button btn_Registrarse;
    @FXML
    private DatePicker fechaNacimiento;
    @FXML
    private PasswordField campoContra2;
    @FXML
    private Button btn_seleccionarImagen;
    
    public Navegacion navegacion;
    
    public User usuario;
    
    public Session sesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void click_Volver(MouseEvent event) {
        try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/Login.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            LoginController login = (LoginController) fxmlLoaderMenu.getController();
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Menu");
            stage.centerOnScreen();
            stage.show();
            
            ((Stage)btn_Volver.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }

    @FXML
    private void click_Resgistrarse(MouseEvent event) throws IOException {
        if(checkCamposVacios()) 
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
                
                usuario = navegacion.registerUser(campoUsuario.getText(), campoCorreo.getText(), campoContra1.getText(), fechaNacimiento.getValue());
                
                if (usuario != null) 
                {
                    Stage stage= new Stage();
                    stage.setScene(new Scene(root1));
                    stage.setTitle("Menu");
                    stage.centerOnScreen();
                    stage.show();

                    ((Stage)btn_Registrarse.getScene().getWindow()).close();
                }
                else {System.out.print("No se ha podido crear el usuario");}
            }
            catch(NavegacionDAOException e) {System.out.print(e);}
        }          
    }

    private boolean checkCamposVacios() {
        if("".equals(campoUsuario.getText()) || "".equals(campoCorreo.getText()) ||
           "".equals(campoContra1.getText()) || "".equals(campoContra2.getText()) || fechaNacimiento.getValue() == null)
        {
            return true;
        }
        return false;
    }

    @FXML
    private void pulsadoImagen(ActionEvent event) {
        
    }
    
}
