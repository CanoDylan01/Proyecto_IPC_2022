/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author dcano
 */
public class LoginController implements Initializable {

    @FXML
    private Button btn_inisesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ini_sesion(MouseEvent event) {
        iniciarSesion();
    }

    private void iniciarSesion() {
        try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/Menu.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            MenuController menu = (MenuController) fxmlLoaderMenu.getController();
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Menu");
            stage.setMinWidth(1290);
            stage.setMinHeight(758);
            stage.setMaxWidth(1295);
            stage.setMaxHeight(758);
            stage.centerOnScreen();
            stage.show();
            
            //stage.setOnCloseRequest(e -> .closeWindows());
            
            ((Stage)btn_inisesion.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }
    
}
