 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Answer;
import model.Navegacion;
import model.Problem;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author dcano
 */
public class RealizarEjercicioController implements Initializable {

    @FXML
    public ListView<Problem> list_problemas;
    @FXML
    private Button btn_volver;
    @FXML
    private Button btn_realizarEjercicio;
    @FXML
    private Button btn_aleatorio;
    
    public User usuario;
    public Session sesion;
    public Navegacion navegacion;
    
    public int aciertos;
    public int fallos;
    
    public ObservableList<Problem> datos = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //disableButtons();
        //getProblems();
    }    

    public void disableButtons() 
    {
        btn_realizarEjercicio.disableProperty().bind(
                Bindings.equal(-1, list_problemas.getSelectionModel().selectedIndexProperty()));
    }
    
    public void getProblems() 
    {
        List<Problem> lista = navegacion.getProblems();
        datos = FXCollections.observableList(lista);
        list_problemas.setItems(datos);
    }

    @FXML
    private void click_volver(ActionEvent event) {
        try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/Menu.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            MenuController menu = (MenuController) fxmlLoaderMenu.getController();
            
            menu.usuario = this.usuario;
            menu.navegacion = this.navegacion;
            menu.sesion = this.sesion;
            menu.aciertos = this.aciertos;
            menu.fallos = this.fallos;
            
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Menu");
            stage.centerOnScreen();
            stage.setWidth(1000);
            stage.setHeight(650);
            stage.getIcons().add(new Image("/resources/icon-96px.png"));
            stage.show();
            
            ((Stage)btn_volver.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }

    @FXML
    private void click_realizarEjercicio(ActionEvent event) {
        try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/CartaNautica.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            CartaNauticaController cartaNautica = (CartaNauticaController) fxmlLoaderMenu.getController();
            
            cartaNautica.usuario = this.usuario;
            cartaNautica.sesion = this.sesion;
            cartaNautica.navegacion = this.navegacion;
            cartaNautica.aciertos = this.aciertos;
            cartaNautica.fallos = this.fallos;
            
            Problem problema = list_problemas.getSelectionModel().getSelectedItem();
            cartaNautica.problema = problema;
            cartaNautica.txt_enunciado.setText(problema.getText());
            var datos = FXCollections.observableList(problema.getAnswers());
            cartaNautica.list_answers.setItems(datos); 
           
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Carta Náutica");
            stage.setWidth(1000);
            stage.setHeight(650);
            stage.centerOnScreen();
            stage.getIcons().add(new Image("/resources/icon-96px.png"));
            stage.show();
            
            ((Stage)btn_realizarEjercicio.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }

    @FXML
    private void click_ejercicioAleatorio(ActionEvent event) {
        Random r = new Random();
        int low = 0;
        int high = list_problemas.getItems().size();
        int result = r.nextInt(high-low) + low;
        
        
        try 
        {
            FXMLLoader fxmlLoaderMenu= new FXMLLoader(getClass().getResource("/vistas/CartaNautica.fxml"));
            Parent root1= (Parent)fxmlLoaderMenu.load();
            CartaNauticaController cartaNautica = (CartaNauticaController) fxmlLoaderMenu.getController();
            
            cartaNautica.usuario = this.usuario;
            cartaNautica.sesion = this.sesion;
            cartaNautica.navegacion = this.navegacion;
            cartaNautica.aciertos = this.aciertos;
            cartaNautica.fallos = this.fallos;
            
            Problem problema = list_problemas.getSelectionModel().getSelectedItem();
            cartaNautica.problema = problema;
            cartaNautica.txt_enunciado.setText(problema.getText());
            var datos = FXCollections.observableList(problema.getAnswers());
            cartaNautica.list_answers.setItems(datos); 
           
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Carta Náutica");
            stage.setWidth(1000);
            stage.setHeight(650);
            stage.centerOnScreen();
            stage.getIcons().add(new Image("/resources/icon-96px.png"));
            stage.show();
            
            ((Stage)btn_realizarEjercicio.getScene().getWindow()).close();
        }
        catch(Exception e) {System.out.print(e);}
    }
    
}
