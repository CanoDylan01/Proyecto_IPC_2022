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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class VerSesionesController implements Initializable {

    @FXML
    private Button btn_volver;
    @FXML
    private TableView<Session> tableView_Sesiones;
    
    public Navegacion navegacion;
    public User usuario;
    @FXML
    private TableColumn<Session, String> colSesion;
    @FXML
    private TableColumn<Session, Integer> colAcertadas;
    @FXML
    private TableColumn<Session, Integer> colFalladas;
    @FXML
    private TableColumn<Session, LocalDateTime> colFecha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //ObservableList<Session> sesiones = (ObservableList)usuario.getSessions();
        //this.tableView_Sesiones.setItems(sesiones);
        
        //tableView_Sesiones;
    }    

    @FXML
    private void click_volver(MouseEvent event) {
        try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/Menu.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            MenuController menu = (MenuController) fxmlLoaderMenu.getController();
            menu.navegacion = this.navegacion;
            menu.usuario = this.usuario;
            
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Menu");
            stage.centerOnScreen();
            stage.show();
                
            ((Stage)btn_volver.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }
    
}
