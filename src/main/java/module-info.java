module com.example.wgjavafxmlapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wgjavafxmlapplication to javafx.fxml;
    exports com.example.wgjavafxmlapplication;
}