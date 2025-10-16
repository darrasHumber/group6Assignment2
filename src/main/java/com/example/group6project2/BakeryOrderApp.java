package com.example.group6project2;

// imports
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



// This class is the main entry point to set up the UI and scene
public class BakeryOrderApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bakery Order System");

        OrderForm form = new OrderForm();
        OrderController controller = new OrderController(form);
        ButtonPanel buttons = new ButtonPanel(controller);

        // creates main vertical layout with spacing and padding
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(25));
        mainLayout.getStyleClass().add("main-container");
        mainLayout.getChildren().addAll(form.getFormSection(), buttons.getButtonSection());

        // setting the scene size to 500 X 550 pixels
        Scene scene = new Scene(mainLayout, 500, 550);

        // loading external CSS file from class path
        try {
            String css = getClass().getResource("styles.css").toExternalForm();
            scene.getStylesheets().add(css);

        } catch (Exception e) {
            System.err.println("CSS file not found: " + e.getMessage());
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}