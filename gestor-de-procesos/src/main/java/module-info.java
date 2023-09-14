module com.proyecto1.gestordeprocesos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.proyecto1.gestordeprocesos to javafx.fxml;
    exports com.proyecto1.gestordeprocesos;
}