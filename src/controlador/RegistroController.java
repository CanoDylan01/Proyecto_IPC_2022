/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.NavegacionDAOException;
import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    @FXML
    private ImageView avatar;
    @FXML
    private Button showPass;
    @FXML
    private Button showPass2;
    @FXML
    private TextField verContra1;
    @FXML
    private TextField verContra2;
    @FXML
    private ImageView ojo1;
    @FXML
    private ImageView ojo2;

    public Navegacion navegacion;
    
    public User usuario;
    
    public Session sesion;
    
    public LocalDate fecha;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fecha = LocalDate.now();
    }    

    @FXML
    private void click_Volver(MouseEvent event) {
        Volver();
    }

    @FXML
    private void click_Resgistrarse(MouseEvent event) throws IOException {  
        Registro();
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
        String currentDir = System.getProperty("user.dir") + File.separator + "src/avatars";
        File file = new File(currentDir);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file);
        fileChooser.setTitle("Buscar Imagen");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Window stage = null;

        // Obtener la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(stage);

        // Mostar la imagen
        if (imgFile != null) {
            Image image = new Image("file:" + imgFile.getAbsolutePath());
            avatar.setImage(image);
        }
    }

    @FXML
    private void hidePass1(MouseEvent event) {
        campoContra1.setVisible(true);
        verContra1.setDisable(true);
            
        ojo1.setStyle("-fx-image:url(/resources/n.png)");
    }

    @FXML
    private void showPass1(MouseEvent event) {
        campoContra1.setVisible(false);
        verContra1.setDisable(false);
        verContra1.setText(campoContra1.getText());
           
        ojo1.setStyle("-fx-image:url(/resources/y.jpg)");
    }

    @FXML
    private void hidePass2(MouseEvent event) {
        campoContra2.setVisible(true);
        verContra2.setDisable(true);
            
        ojo2.setStyle("-fx-image:url(/resources/n.png)");
    }

    @FXML
    private void showPass2(MouseEvent event) {
        campoContra2.setVisible(false);
        verContra2.setDisable(false);
        verContra2.setText(campoContra2.getText());
           
        ojo2.setStyle("-fx-image:url(/resources/y.jpg)");
    }
    
    private Boolean esMenor() {
        LocalDate aux = fecha.minusYears(16);
        return fechaNacimiento.getValue().isBefore(aux);
    }

    @FXML
    private void cambioCampo(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            cambiaFoco();
        }
    }

    @FXML
    private void kVolver(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Volver();
        }
    }

    @FXML
    private void kRegistro(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            Registro();
        }
    }
    
    private void cambiaFoco() {
        if (campoUsuario.isFocused()) {
            campoCorreo.requestFocus();
        }
        else if (campoCorreo.isFocused()) {
            if (campoContra1.isDisable()) {
                verContra1.requestFocus();
            }
            else { campoContra1.requestFocus();}
        }
        else if (campoContra1.isFocused() || verContra1.isFocused()) {
            if (campoContra2.isDisabled()) {
                verContra2.requestFocus();
            }
            else { campoContra2.requestFocus(); }
        }
        else if (campoContra2.isFocused() || verContra2.isFocused()) {
            btn_seleccionarImagen.requestFocus();
        }
        else if (btn_seleccionarImagen.isFocused()) {
            fechaNacimiento.requestFocus();
        }
        else { btn_Registrarse.requestFocus();}
    }
    
    private void Registro() throws IOException {
        if(checkCamposVacios()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Campos vacíos.\n Por favor complete los campos.");
            alert.showAndWait();
        }
        else if(navegacion.exitsNickName(campoUsuario.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Usuario Existente.\n Ya existe una cuenta con ese usuario. \n Elija otro usuario por favor.");
            alert.showAndWait();
        }
        else if(!User.checkNickName(campoUsuario.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Usuario inválido.\n Ese nombre de usuario no es válido. \n Introduzca uno que si lo sea.");
            alert.showAndWait();
        }
        else if(!User.checkEmail(campoCorreo.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Correo inválido.\n Ese correo no es válido. \n Introduzca uno que si lo sea.");
            alert.showAndWait();
        }
        else if(!User.checkPassword(campoContra1.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Contraseña inválida.\n La contraseña proporcionada no es válida. \n Introduzca una que si lo sea.");
            alert.showAndWait();
        }
        else if(!esMenor())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Usuario menor de edad.\n Ese nombre de usuario no es válido. \n Introduzca uno que si lo sea.");
            alert.showAndWait();
        }
        else
        {   
            try 
            {
                FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/Menu.fxml"));
                Parent root1= (Parent)fxmlLoaderMenu.load();
                MenuController menu = (MenuController) fxmlLoaderMenu.getController();
                
                usuario = navegacion.registerUser(campoUsuario.getText(), campoCorreo.getText(), campoContra1.getText(), avatar.getImage(), fechaNacimiento.getValue());
                
                if (usuario != null) 
                {
                    menu.usuario = this.usuario;
                    menu.navegacion = this.navegacion;
                    menu.sesion = this.sesion;
                    
                    Stage stage= new Stage();
                    stage.setScene(new Scene(root1));
                    stage.setTitle("Menu");
                    stage.centerOnScreen();
                    stage.setWidth(1000);
                    stage.setHeight(650);
                    stage.show();

                    ((Stage)btn_Registrarse.getScene().getWindow()).close();
                }
                else {System.out.print("No se ha podido crear el usuario");}
            }
            catch(NavegacionDAOException e) {System.out.print(e);}
        }
    }
    
    private void Volver() {
        try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/Login.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            LoginController login = (LoginController) fxmlLoaderMenu.getController();
            login.navegacion = this.navegacion;
            
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Menu");
            stage.centerOnScreen();
            stage.setWidth(1000);
            stage.setHeight(650);
            stage.show();
            
            ((Stage)btn_Volver.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }
}
