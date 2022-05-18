package es.felixgomezenriquez.agendacitas;

import es.felixgomezenriquez.agendacitas.entities.Reunion;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javax.persistence.Query;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    private void cargarTodasReuniones(){
    Query queryReunionFindAll =App.em.createNamedQuery("Reunion.findAll");

    List<Reunion>listReunion =queryReunionFindAll.getResultList();
    tableViewReuniones.setItems(FXCollections.observableArrayList(listReunion));
}
}
