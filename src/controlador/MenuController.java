/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Navegacion;
import model.Problem;
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
    @FXML
    private Button btn_verSesiones;
    
    public Navegacion navegacion;
    
    public User usuario;
    
    public Session sesion;




    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/RealizarEjercicio.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            RealizarEjercicioController realizarEjercicio = (RealizarEjercicioController) fxmlLoaderMenu.getController();
            realizarEjercicio.usuario = this.usuario;
            realizarEjercicio.sesion = this.sesion;
            realizarEjercicio.navegacion = this.navegacion;
            
            realizarEjercicio.disableButtons();
             
            var datos = FXCollections.observableList(navegacion.getProblems());
            realizarEjercicio.list_problemas.setItems(datos);
            
            sesion = new Session( LocalDateTime.now(), 3, 1);
            usuario.addSession(sesion);


            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Elección de ejercicios");
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
            
            ((Stage)btn_Modificar.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }

    @FXML
    private void click_verSesiones(MouseEvent event) {
        try 
        {
            FXMLLoader fxmlLoaderMod= new FXMLLoader(getClass().getResource("/vistas/VerSesiones.fxml"));
            Parent root1= (Parent)fxmlLoaderMod.load();
            VerSesionesController verSesiones = (VerSesionesController) fxmlLoaderMod.getController();
            verSesiones.navegacion = this.navegacion;
            verSesiones.usuario = this.usuario;
            
            verSesiones.getSesionUser();
            
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Ver Sesiones");
            stage.centerOnScreen();
            stage.show();
            
            ((Stage)btn_verSesiones.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }
}
