package es.felixgomezenriquez.agendacitas;

import es.felixgomezenriquez.agendacitas.entities.Empresa;
import es.felixgomezenriquez.agendacitas.entities.Reunion;
import java.io.File;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Optional;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.RollbackException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.StringConverter;
import javafx.scene.image.ImageView;
import javax.persistence.Query;

public class SecondaryController {
    
    private static final String CARPETA_FOTOS = "Fotos";
    private final int TAM_NOMBRE = 20;
    private final int TAM_LUGAR = 30;
    private final int TAM_TEMAS = 100;
    private final int TAM_ORGANIZADOR = 30;
    private Reunion reunion;
    private boolean nuevaReunion;
    
    @FXML
    private HBox rootSecondary;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldReunion;
    @FXML
    private DatePicker datePickerFechaReunion;
    @FXML
    private TextField textFieldTemas;
    @FXML
    private ComboBox<Empresa> comboBoxEmpresa;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private TextField textFieldOrganizador;

    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    
    
    public void setReunion(Reunion reunion,boolean nuevaReunion){
               
    App.em.getTransaction().begin();
    if(!nuevaReunion){
        this.reunion=App.em. find(Reunion.class,reunion.getId());
    }else{
        this.reunion= reunion;
    }
    this.nuevaReunion=nuevaReunion;
    mostrarDatos();
    }
    
    
    
    private void mostrarDatos(){
        textFieldNombre.setText(reunion.getNombreReunion());
        textFieldReunion.setText(reunion.getLugarReunion());
        textFieldTemas.setText(reunion.getTemasATratar());
        textFieldOrganizador.setText(reunion.getOrganizador());
                
        if (reunion.getFechaReunion() != null){
            Date date = reunion.getFechaReunion();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            datePickerFechaReunion.setValue(localDate);
        }
         
        Query queryEmpresaFindAll = App.em.createNamedQuery("Empresa.findAll");
        List <Empresa> listEmpresa = queryEmpresaFindAll.getResultList();
        
        comboBoxEmpresa.setItems(FXCollections.observableList(listEmpresa));
        if (reunion.getEmpresa() != null){
            comboBoxEmpresa.setValue(reunion.getEmpresa());
        }
        
        comboBoxEmpresa.setCellFactory((ListView<Empresa> l ) -> new ListCell<Empresa>() {
                @Override
                protected void updateItem(Empresa empresa, boolean empty) {
                    super.updateItem(empresa, empty);
                    if (empresa == null || empty){
                        setText("");
                    } else {
                        setText(empresa.getCodigo() + "/" + empresa.getNombre());
                    }               
                }
            });
                 comboBoxEmpresa.setConverter(new StringConverter<Empresa>(){
                @Override
                public String toString(Empresa empresa) {
                    if (empresa == null){
                        return null;
                    } else {
                        return empresa.getCodigo() + "/" + empresa.getNombre();
                    }
                }
                
                @Override
                public Empresa fromString(String userId){
                    return null;
                }
            });
                 
        if (reunion.getFotoOrganizador()!= null){
            String imageFileName = reunion.getFotoOrganizador();
            File file = new File(CARPETA_FOTOS+"\\"+imageFileName);
            System.out.println("La ruta a la foto es: "+ CARPETA_FOTOS+"\\"+imageFileName);

            if (file.exists()){
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imagen");
                alert.showAndWait();
            }
        }
       
 }
    @FXML
    private void OnActionButtonGuardar(ActionEvent event) {
        
        boolean errorFormato = false;
        String nombreR =textFieldNombre.getText();
        
        if(nombreR.length() <= TAM_NOMBRE) {
            reunion.setNombreReunion(nombreR);
        } else {
            errorFormato = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                    "El tamaño del nombre de la reunion no puede ser superior a"
                            + " " + TAM_NOMBRE + " caracteres");
            alert.showAndWait();
            textFieldNombre.requestFocus();
        }
        
        
        String lugar =textFieldReunion.getText();
        if(lugar.length() <= TAM_LUGAR) {
            reunion.setLugarReunion(lugar);
        } else {
            errorFormato = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                    "El tamaño del lugar de la reunion no puede ser superior a " 
                            + TAM_LUGAR + " caracteres");
            alert.showAndWait();
            textFieldReunion.requestFocus();
        }
        
                
        String temas =textFieldTemas.getText();
        if(temas.length() <= TAM_TEMAS) {
            reunion.setTemasATratar(temas);
        } else {
            errorFormato = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                    "El tamaño de los temas a tratar no puede ser superior a " 
                            + TAM_TEMAS + " caracteres");
            alert.showAndWait();
            textFieldTemas.requestFocus();
        }
        
        
        
        String org =textFieldOrganizador.getText();
        if(org.length() <= TAM_ORGANIZADOR) {
            reunion.setOrganizador(org);
        } else {
            errorFormato = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                    "El tamaño del nombre del organizador no puede ser superior a " 
                            + TAM_ORGANIZADOR + " caracteres");
            alert.showAndWait();
            textFieldOrganizador.requestFocus();
        }
        
                
        
        
        if(datePickerFechaReunion.getValue() != null){
            LocalDate localDate = datePickerFechaReunion.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            reunion.setFechaReunion(date);
        }else{
            reunion.setFechaReunion(null);
        }
        
        reunion.setEmpresa(comboBoxEmpresa.getValue());
        
        if(!errorFormato){
            try{
                if(reunion.getId() == null){
                    System.out.println("Guardando nueva reunión en BD");
                    App.em.persist(reunion);
                }else{
                    System.out.println("Actualizando reunión en BD");
                    App.em.merge(reunion);
                }
                App.em.getTransaction().commit();
                App.setRoot("primary");
            }catch(RollbackException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios."
                        + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }catch(IOException ex){
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    @FXML
    private void OnActionButtonCancelar(ActionEvent event) throws IOException {
        App.em.getTransaction().rollback();
        try{
            App.setRoot("primary");
        }catch (IOException ex){
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
     @FXML
    private void OnActionButtonExaminar(ActionEvent event) throws IOException {
        File carpertaFotos = new File(CARPETA_FOTOS);
        if(!carpertaFotos.exists()){
            carpertaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg","*.png"),
                 new FileChooser.ExtensionFilter("Todos los archivos", ".")
        );
        
        File file = fileChooser.showOpenDialog(rootSecondary.getScene().getWindow());
        if (file != null){
            try{
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/" + file.getName()).toPath());
                reunion.setFotoOrganizador(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Nombre de archivo duplicado");
                alert.showAndWait();
            }
        }
    }
    
     @FXML
    private void onActionButtonSuprimirFoto(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresion ed imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen, \n"
                + "quitar la foto pero MANTENER el archivo, \no CANCELAR la operación?");
        alert.setContentText("Elija a opción deseada");
        
        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == buttonTypeEliminar) {
            String imageFileName = reunion.getFotoOrganizador();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if(file.exists()){
                file.delete();
            }
            reunion.setFotoOrganizador(null);
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener){
            reunion.setFotoOrganizador(null);
            imageViewFoto.setImage(null);
        }
    }
}

