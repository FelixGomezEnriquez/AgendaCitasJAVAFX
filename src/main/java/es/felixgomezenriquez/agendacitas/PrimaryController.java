package es.felixgomezenriquez.agendacitas;

import es.felixgomezenriquez.agendacitas.entities.Reunion;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.Query;

public class PrimaryController implements Initializable {
    
    private Reunion reunionSeleccionada;
    
    @FXML
    private TableView tableViewReuniones ;
    @FXML
    private TableColumn<Reunion,String > columnNombre;
    @FXML
    private TableColumn<Reunion,String > columnLugar;
    @FXML
    private TableColumn<Reunion,String > columnFecha;
    @FXML
    private TableColumn<Reunion,String > columnTemas_A_Tratar;
    @FXML
    private TableColumn<Reunion,String > columnEmpresa;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldLugar;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
    
        columnNombre.setCellValueFactory(new PropertyValueFactory<Reunion,String>("nombreReunion"));
        columnLugar.setCellValueFactory(new PropertyValueFactory<Reunion,String>("lugarReunion"));
        columnFecha.setCellValueFactory(cellData-> {
           SimpleStringProperty property=new SimpleStringProperty();
            if(cellData.getValue().getFechaReunion()!=null){
                
            DateFormat formateadorFechaCorta = DateFormat.getDateInstance(DateFormat.SHORT);
            
            String fechaReunion =formateadorFechaCorta.format(cellData.getValue().getFechaReunion());
               
               property.setValue(fechaReunion);
           }
            return property;
       });
        columnTemas_A_Tratar.setCellValueFactory(new PropertyValueFactory<>("temasATratar"));
        columnEmpresa.setCellValueFactory(cellData-> {
           SimpleStringProperty property=new SimpleStringProperty();
            if(cellData.getValue().getEmpresa()!=null){
               String nombreEmpresa = cellData.getValue().getEmpresa().getNombre();
               property.setValue(nombreEmpresa);
           }
            return property;
       });
        
         tableViewReuniones.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    reunionSeleccionada =  (Reunion) newValue;
                    if (reunionSeleccionada != null) {
                        textFieldNombre.setText(reunionSeleccionada.getNombreReunion());
                        textFieldLugar.setText(reunionSeleccionada.getLugarReunion());
                    } else {
                        textFieldNombre.setText("");
                        textFieldLugar.setText("");
                    }
                });
        cargarTodasReuniones();
    

    }
    
    private void cargarTodasReuniones(){
    Query queryReunionFindAll =App.em.createNamedQuery("Reunion.findAll");

    List<Reunion>listReunion =queryReunionFindAll.getResultList();
        System.out.println("tamaño de la lista"+ listReunion);
    tableViewReuniones.setItems(FXCollections.observableArrayList(listReunion));
}
    
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
