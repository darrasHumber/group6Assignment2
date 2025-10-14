module com.example.group6project2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.group6project2 to javafx.fxml;
    exports com.example.group6project2;
}