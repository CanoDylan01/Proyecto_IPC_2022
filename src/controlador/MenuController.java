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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
            stage.setMinWidth(1290);
            stage.setMinHeight(758);
            stage.setMaxWidth(1295);
            stage.setMaxHeight(758);
            stage.centerOnScreen();
            stage.show();
            
            //stage.setOnCloseRequest(e -> .closeWindows());
            
            //Stage myStage = (Stage) this.inicioSesion.getScene().getWindow();
            //myStage.close();
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
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Práctica");
            stage.setMinWidth(1290);
            stage.setMinHeight(758);
            stage.setMaxWidth(1295);
            stage.setMaxHeight(758);
            stage.centerOnScreen();
            stage.show();
            
            
            //stage.setOnCloseRequest(e -> .closeWindows());
            
            //Stage myStage = (Stage) this.inicioSesion.getScene().getWindow();
            //myStage.close();
        }
        catch(Exception e) {System.out.print(e);}
    }
}
