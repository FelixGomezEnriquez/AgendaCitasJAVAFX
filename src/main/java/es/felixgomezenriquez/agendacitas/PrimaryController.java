package es.felixgomezenriquez.agendacitas;

import es.felixgomezenriquez.agendacitas.entities.Reunion;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.Query;

public class PrimaryController {
    
    private TableView tableViewReuniones ;
    
    private TableColumn<Reunion,String > columnNombre;

    private TableColumn<Reunion,String > columnLugar;

    private TableColumn<Reunion,String > columnFecha;
    
    private TableColumn<Reunion,String > columnTemas_A_Tratar;
    
    private TableColumn<Reunion,String > columnEmpresa;



    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    public void initialize(URL url, ResourceBundle rb){
    
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("NOMBRE_REUNION"));
        columnLugar.setCellValueFactory(new PropertyValueFactory<>("LUGAR_REUNION"));
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("FECHA_REUNION"));
        columnTemas_A_Tratar.setCellValueFactory(new PropertyValueFactory<>("TEMAS_A_TRATAR"));
        columnEmpresa.setCellValueFactory(cellData-> {
           SimpleStringProperty property=new SimpleStringProperty();
            if(cellData.getValue().getEmpresa()!=null){
               String nombreEmpresa = cellData.getValue().getEmpresa().getNombre();
               property.setValue(nombreEmpresa);
           }
            return property;
       });

    }
    
    
    
    private void cargarTodasReuniones(){
    Query queryReunionFindAll =App.em.createNamedQuery("Reunion.findAll");

    List<Reunion>listReunion =queryReunionFindAll.getResultList();
    tableViewReuniones.setItems(FXCollections.observableArrayList(listReunion));
}
}
