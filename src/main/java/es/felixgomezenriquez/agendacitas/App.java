package es.felixgomezenriquez.agendacitas;

import es.felixgomezenriquez.agendacitas.entities.Reunion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static EntityManager em;
    public static FXMLLoader fxmlLoader;
    
    
    @Override
    public void start(Stage stage) throws IOException {
        
        //Conexion con la base de datos base de datos y un catch para los posibles errores
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgendaCitasPU");
            em = emf.createEntityManager();
    
        } catch (PersistenceException e) {
            Logger.getLogger(App.class.getName()).log(Level.WARNING, e.getMessage());
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atencion");
            alert.setHeaderText("No se ha podido abrir la base de datos\n"
            +"Compruebe que no se encuentra ya abierta la aplicación");
            alert.showAndWait();
        }
        
        scene = new Scene(loadFXML("primary"), 700, 500);
        stage.setScene(scene);
        stage.show();
               
    }

    
    @Override
    public void stop() throws Exception {
        try {
            DriverManager.getConnection("jdbc:derby:BDAgendaCitas;create=true");
        } catch (Exception e) {
        }
        
    }
    //metodo para cambiar de pantallas
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
