package es.felixgomezenriquez.agendacitas;

import es.felixgomezenriquez.agendacitas.entities.Empresa;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    @Override
    public void start(Stage stage) throws IOException {
        //Conexion base de datos
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgendaCitasPU");
            em = emf.createEntityManager();
    
        } catch (PersistenceException e) {
            Logger.getLogger(App.class.getName()).log(Level.WARNING, e.getMessage());
        }
        
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
        
        //Crea una empresa y la añade a la bd
//        Empresa e = new Empresa(0, "DautoMotor");
//        em.getTransaction().begin();
//        em.persist(e);
//        em.getTransaction().commit();
        
        
    }

    @Override
    public void stop() throws Exception {
        try {
            DriverManager.getConnection("jdbc:derby:BDAgendaCitas;create=true");
        } catch (Exception e) {
        }
        
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}