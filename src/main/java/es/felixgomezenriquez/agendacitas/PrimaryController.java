package es.felixgomezenriquez.agendacitas;

import es.felixgomezenriquez.agendacitas.entities.Reunion;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private Button buttonGuardar;
    @FXML
    private TableColumn<Reunion, String> columnOrganizador;
    @FXML
    private TextField textFieldBuscar;
    @FXML
    private CheckBox checkBoxCoincide;
    @FXML
    private Button buttonBuscar;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
    
        columnOrganizador.setCellValueFactory(new PropertyValueFactory<Reunion,String>("organizador"));
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
        
        textFieldBuscar.addEventHandler(KeyEvent.KEY_PRESSED,(event)->{
                if(event.getCode()== KeyCode.ENTER){
                    buttonBuscar.fire();
                    event.consume();
                }});
    

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

    @FXML
    private void OnActionButtonGuardar(ActionEvent event) {
        
        if (reunionSeleccionada != null){
            reunionSeleccionada.setNombreReunion(textFieldNombre.getText());
            reunionSeleccionada.setLugarReunion(textFieldLugar.getText());
            App.em.getTransaction().begin();
            App.em.merge(reunionSeleccionada);
            App.em.getTransaction().commit();
            
            int numFilaSeleccionada = tableViewReuniones.getSelectionModel().getSelectedIndex();
            tableViewReuniones.getItems().set(numFilaSeleccionada, reunionSeleccionada);
            TablePosition pos = new TablePosition(tableViewReuniones, numFilaSeleccionada, null);
            tableViewReuniones.getFocusModel().focus(pos);
            tableViewReuniones.requestFocus();
        }
        
    }

    @FXML
    private void OnActionButtonSuprimir(ActionEvent event) {
        
        if (reunionSeleccionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("¿Desea suprimir el siguiente registro?");
            alert.setContentText(reunionSeleccionada.getNombreReunion()+" "+reunionSeleccionada.getLugarReunion());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                App.em.getTransaction().begin();
                App.em.remove(reunionSeleccionada);
                App.em.getTransaction().commit();
                tableViewReuniones.getItems().remove(reunionSeleccionada);
                tableViewReuniones.getFocusModel().focus(null);
                tableViewReuniones.requestFocus();
            } else {
                int numFilaSeleccionada = tableViewReuniones.getSelectionModel().getSelectedIndex();
                tableViewReuniones.getItems().set(numFilaSeleccionada, reunionSeleccionada);
                TablePosition pos = new TablePosition(tableViewReuniones, numFilaSeleccionada, null);
                tableViewReuniones.getFocusModel().focus(pos);
                tableViewReuniones.requestFocus();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
        
        
        
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        
        try {
            App.setRoot("secondary");
            SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController();
            reunionSeleccionada = new Reunion();
            secondaryController.setReunion(reunionSeleccionada, true);
        } catch (IOException ex){
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
    }   
        
    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        if(reunionSeleccionada != null){
            try {
                App.setRoot("secondary");
                SecondaryController secondaryController = (SecondaryController) App.fxmlLoader.getController();
                secondaryController.setReunion(reunionSeleccionada, false);
            } catch (IOException ex){
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
        
    }

    @FXML
    private void onActionButtonBuscar(ActionEvent event) {
        
        if (!textFieldBuscar.getText().isEmpty()){
            if(checkBoxCoincide.isSelected()){
                Query queryReunionFindByOrganizador = App.em.createNamedQuery("Reunion.findByOrganizador");
                queryReunionFindByOrganizador.setParameter("organizador", textFieldBuscar.getText());
                List<Reunion> listReunion = queryReunionFindByOrganizador.getResultList();
                tableViewReuniones.setItems(FXCollections.observableArrayList(listReunion));
            } else {
                String strQuery = "SELECT * FROM Reunion WHERE LOWER(organizador) LIKE ";
                strQuery += "\'%" + textFieldBuscar.getText().toLowerCase() + "%\'";
                Query queryReunionLikeOrganizador = App.em.createNativeQuery(strQuery, Reunion.class);

                List<Reunion> listReunion = queryReunionLikeOrganizador.getResultList();
                tableViewReuniones.setItems(FXCollections.observableArrayList(listReunion));

                Logger.getLogger(this.getClass().getName()).log(Level.INFO, strQuery);
            
            }
            
        } else {
            cargarTodasReuniones();
        }
        
        
        
        
        
    }
    
    
        
        
}
    
    
    
    

