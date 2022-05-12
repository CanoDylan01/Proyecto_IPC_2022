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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class MenuController implements Initializable {

    @FXML
    private Button btn_volver;
    @FXML
    private Button btn_practica;
    @FXML
    private Button btn_Modificar;
    
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
    private void pulsadoVolver(MouseEvent event) {
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
            
            ((Stage)btn_volver.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
        
    }

    @FXML
    private void pulsadoPracticar(MouseEvent event) {
            try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/CartaNautica.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            CartaNauticaController cartaNautica = (CartaNauticaController) fxmlLoaderMenu.getController();
            cartaNautica.navegacion = this.navegacion;
            cartaNautica.usuario = this.usuario;
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Práctica");
            stage.setWidth(1200);
            stage.setHeight(700);
            stage.centerOnScreen();
            stage.show();
            
            ((Stage)btn_practica.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }

    @FXML
    private void modPerfil(ActionEvent event) {
        try 
        {
            FXMLLoader fxmlLoaderMod= new FXMLLoader(getClass().getResource("/vistas/modPerfil.fxml"));
            Parent root1= (Parent)fxmlLoaderMod.load();
            ModPerfilController modPerfil = (ModPerfilController) fxmlLoaderMod.getController();
            modPerfil.navegacion = this.navegacion;
            modPerfil.usuario = this.usuario;
            modPerfil.sesion = this.sesion;
            
            modPerfil.campoUsuario.setText(usuario.getNickName());
            modPerfil.campoContra1.setText(usuario.getPassword());
            modPerfil.campoCorreo.setText(usuario.getEmail());
            modPerfil.fechaNacimiento.setValue(usuario.getBirthdate());
            modPerfil.avatar.setImage(usuario.getAvatar());
            
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Práctica");
            stage.centerOnScreen();
            stage.show();
            
            ((Stage)btn_practica.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }
}
