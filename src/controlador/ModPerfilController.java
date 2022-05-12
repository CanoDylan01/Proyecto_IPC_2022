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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
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
        try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/Menu.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            MenuController menu = (MenuController) fxmlLoaderMenu.getController();
            
            menu.usuario = this.usuario;
            menu.navegacion = this.navegacion;
            menu.sesion = this.sesion;
            
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
    private void clickModificar(ActionEvent event) throws IOException {
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
                
                usuario.setEmail(campoCorreo.getText());
                usuario.setPassword(campoContra1.getText());
                usuario.setAvatar(avatar.getImage());
                usuario.setBirthdate(fechaNacimiento.getValue());
                
                if (usuario != null) 
                {
                    menu.usuario = this.usuario;
                    menu.navegacion = this.navegacion;
                    menu.sesion = this.sesion;
                    
                    Stage stage= new Stage();
                    stage.setScene(new Scene(root1));
                    stage.setTitle("Menu");
                    stage.centerOnScreen();
                    stage.show();

                    ((Stage)btn_Volver.getScene().getWindow()).close();
                }
                else {System.out.print("No se ha podido crear el usuario");}
            }
            catch(NavegacionDAOException e) {System.out.print(e);}
        }
    }

    @FXML
    private void clickSeleccionar(ActionEvent event) {
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
    
    private boolean checkCamposVacios() {
        if("".equals(campoUsuario.getText()) || "".equals(campoCorreo.getText()) ||
           "".equals(campoContra1.getText()) || "".equals(campoContra2.getText()) || fechaNacimiento.getValue() == null)
        {
            return true;
        }
        return false;
    }
    
}
