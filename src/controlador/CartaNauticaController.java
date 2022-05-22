/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.NavegacionDAOException;
import com.sun.glass.ui.Cursor;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Answer;
import modelos.Poi;
import model.Navegacion;
import model.Problem;
import model.Session;
import model.User;


/**
 *
 * @author jsoler
 */
public class CartaNauticaController implements Initializable {

    //=======================================
    // hashmap para guardar los puntos de interes POI
    private final HashMap<String, Poi> hm = new HashMap<>();
    // ======================================
    // la variable zoomGroup se utiliza para dar soporte al zoom
    // el escalado se realiza sobre este nodo, al escalar el Group no mueve sus nodo
    private Group zoomGroup;

    private ListView<Poi> map_listview;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private Label posicion;
    @FXML
    private Button btn_volver;
    @FXML
    private ImageView transportador;
    @FXML
    private Button btn_mover;
    @FXML
    private Button btn_linea;
    @FXML
    private Button btn_circulo;
    @FXML
    private Button btn_texto;
    
    public double inicioXTrans;
    public double inicioYTrans;
    public double baseX;
    public double baseY;
    
    public static Navegacion navegacion;
    
    public User usuario;
    
    public Session sesion;
    
    public Problem problema;
    
    Line linePainting;    
    
    @FXML
    Circle circlePainting;
    double inicioXArc;
    
    TextField textoPainting;
    @FXML
    public Text txt_enunciado;
    @FXML
    public ListView<Answer> list_answers;
    @FXML
    private Button btn_responder;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ChoiceBox<Double> cb_selectorGrosor;
    public double grosor;

    public enum OpcionCursor {MOVER, LINEA, CIRCULO, TEXTO};
    OpcionCursor cursor = OpcionCursor.MOVER;
    
    public int aciertos;
    public int fallos;

    
    @FXML
    void zoomIn(ActionEvent event) {
        //================================================
        // el incremento del zoom dependerá de los parametros del 
        // slider y del resultado esperado
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }
    
    // esta funcion es invocada al cambiar el value del slider zoom_slider
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    @FXML
    void listClicked(MouseEvent event) {
        //Poi itemSelected = map_listview.getSelectionModel().getSelectedItem();

        // Animación del scroll hasta la posicion del item seleccionado
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        //double scrollH = itemSelected.getPosition().getX() / mapWidth;
        //double scrollV = itemSelected.getPosition().getY() / mapHeight;
        final Timeline timeline = new Timeline();
        //final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        //final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        //final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        //timeline.getKeyFrames().add(kf);
        timeline.play();

        // movemos el objto map_pin hasta la posicion del POI
        double pinW = map_pin.getBoundsInLocal().getWidth();
        double pinH = map_pin.getBoundsInLocal().getHeight();
        //map_pin.setLayoutX(itemSelected.getPosition().getX());
        //map_pin.setLayoutY(itemSelected.getPosition().getY());
        //pin_info.setText(itemSelected.getDescription());
        map_pin.setVisible(true);
    }

    private void initData() {
        hm.put("2F", new Poi("2F", "Edificion del DSIC", 325, 225));
        hm.put("Agora", new Poi("Agora", "Agora", 600, 360));
        map_listview.getItems().add(hm.get("2F"));
        map_listview.getItems().add(hm.get("Agora"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //initData();
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        zoom_slider.setMin(0.2);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(0.8);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
        posicion.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY() + "\n"
                + "         X: " + (int) event.getX() + ",          Y: " + (int) event.getY());
    }

    @FXML
    private void cerrarAplicacion(ActionEvent event) throws NavegacionDAOException {
        registerSession();
        
        ((Stage)zoom_slider.getScene().getWindow()).close();
    }

    @FXML
    private void acercaDe(ActionEvent event) {
        Alert mensaje= new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText(usuario.getNickName());
        mensaje.showAndWait();
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
    private void finTraslacion(MouseEvent event) {
        
    }

    @FXML
    private void translacion(MouseEvent event) {
        double despX = event.getSceneX() - inicioXTrans;
        double despY = event.getSceneY() - inicioYTrans;
        transportador.setTranslateX(baseX + despX);
        transportador.setTranslateY(baseY + despY);
        event.consume();
    }

    @FXML
    private void inicioTranslacion(MouseEvent event) {
        inicioXTrans = event.getSceneX();
        inicioYTrans = event.getSceneY();
        baseX = transportador.getTranslateX();
        baseY = transportador.getTranslateY();
        event.consume();
    }

    @FXML
    private void finPintar(MouseEvent event) {
        switch(cursor) 
        {
            case MOVER:
                
                break;
            case LINEA:
                linePainting.setEndX(event.getX());
                linePainting.setEndY(event.getY());
                event.consume();
                break;
            case CIRCULO:
                double radio = Math.abs(event.getX() - inicioXArc);
                circlePainting.setRadius(radio);
                event.consume();
                break;
            case TEXTO:

                break;  
        }
    }

    @FXML
    private void inicioPintar(MouseEvent event) {
        switch(cursor) 
        {
            case MOVER:
                
                break;
                
            case LINEA:
                linePainting = new Line(event.getX(), event.getY(), event.getX(), event.getY());
                linePainting.setStroke(colorPicker.getValue());
                //linePainting.setStrokeWidth(chooseGrosor());
                linePainting.fillProperty();
                zoomGroup.getChildren().add(linePainting);
                break;
                
            case CIRCULO:
                circlePainting = new Circle(1);
                circlePainting.setStroke(colorPicker.getValue());
                circlePainting.setStrokeWidth(2);
                circlePainting.setFill(Color.TRANSPARENT);
                zoomGroup.getChildren().add(circlePainting);
                circlePainting.setCenterX(event.getX());
                circlePainting.setCenterY(event.getY());
                inicioXArc = event.getX();               
                break;
                
            case TEXTO:
                textoPainting = new TextField();
                zoomGroup.getChildren().add(textoPainting);
                textoPainting.setLayoutX(event.getX());
                textoPainting.setLayoutY(event.getY());
                textoPainting.requestFocus();
                textoPainting.setOnAction(e -> {
                    Text textoT = new Text(textoPainting.getText());
                    textoT.setX(textoPainting.getLayoutX());
                    textoT.setY(textoPainting.getLayoutY());
                    textoT.setStyle("-fx-font-family: Gafata; -fx-font-size: 40;");
                    textoT.setStroke(colorPicker.getValue());
                    zoomGroup.getChildren().add(textoT);
                    zoomGroup.getChildren().remove(textoPainting);
                    e.consume();
                });
                break;  
        }
    }

    @FXML
    private void editarPintaLinea(ContextMenuEvent event) {
        linePainting.setOnContextMenuRequested(e -> {
            ContextMenu menuContext = new ContextMenu();
            MenuItem borrarItem = new MenuItem("Eliminar");
            menuContext.getItems().add(borrarItem);
            borrarItem.setOnAction(ev -> {
                zoomGroup.getChildren().remove((Node)e.getSource());
                ev.consume();
            });
            menuContext.show(linePainting, e.getSceneX(), e.getSceneY());
            e.consume();
        });
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws NavegacionDAOException {
        registerSession();
        
        try 
        {
            FXMLLoader fxmlLoaderLogin= new FXMLLoader(getClass().getResource("/vistas/Login.fxml"));
            Parent root1= (Parent)fxmlLoaderLogin.load();
            LoginController login = (LoginController) fxmlLoaderLogin.getController();
            Stage stage= new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Login");
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
    private void cambia_mover(MouseEvent event) {
        cursor = OpcionCursor.MOVER;

    }

    @FXML
    private void cambia_linea(MouseEvent event) {
        cursor = OpcionCursor.LINEA;
    }

    @FXML
    private void cambia_circulo(MouseEvent event) {
        cursor = OpcionCursor.CIRCULO;
    }

    @FXML
    private void cambia_texto(MouseEvent event) {
        cursor = OpcionCursor.TEXTO;
    }
    
    @FXML
    private void click_responder(MouseEvent event) {
        Answer respuesta = list_answers.getSelectionModel().getSelectedItem();
        if(respuesta.getValidity()) 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Enhorabuena!");
            alert.setContentText("Has acertado el problema!");   
            ++aciertos;
            
            alert.showAndWait();
        }
        else 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Qué pena!!");
            alert.setContentText("Has fallado el problema!");
            ++fallos;
            
            alert.showAndWait();
        }
    }
    
    private void registerSession() throws NavegacionDAOException 
    {
        if(aciertos != 0 || fallos != 0) 
        {
            sesion = new Session(LocalDateTime.now(), aciertos, fallos);
            usuario.addSession(sesion);
            usuario = null;
            aciertos = 0;
            fallos = 0;
        }   
    }
    
    private double chooseGrosor() 
    {
        //final Double[] grosor = new Double[]{1.0, 2.0,3.0};
        
        cb_selectorGrosor = new ChoiceBox<>(FXCollections.observableArrayList(
                1.0, 2.0, 3.0
        ));
        return grosor = cb_selectorGrosor.getSelectionModel().getSelectedItem();
    }
}
