module es.felixgomezenriquez.agendacitas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.instrument;
    requires java.persistence;
    requires java.sql;
    requires java.base;
    
    opens es.felixgomezenriquez.agendacitas.entities;

    opens es.felixgomezenriquez.agendacitas to javafx.fxml;
    exports es.felixgomezenriquez.agendacitas;
}
