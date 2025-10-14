package com.example.group6project2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BakeryOrderApp extends Application {

    private TextField nameField;
    private TextField phoneField;
    private ComboBox<String> cakeTypeComboBox;
    private ToggleGroup sizeToggleGroup;
    private RadioButton smallRadio, mediumRadio, largeRadio;
    private CheckBox deliveryCheckBox;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bakery Order System");

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(25));
        mainLayout.getStyleClass().add("main-container");

        VBox formSection = createFormSection();
        HBox buttonSection = createButtonSection();

        mainLayout.getChildren().addAll(formSection, buttonSection);

        Scene scene = new Scene(mainLayout, 500, 550);

        try {
            String css = getClass().getResource("/com/example/group6project2/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("CSS file not found: " + e.getMessage());
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createFormSection() {
        VBox formSection = new VBox(15);
        formSection.getStyleClass().add("form-section");

        Label titleLabel = new Label("Bakery Order Form");
        titleLabel.getStyleClass().add("form-title");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));

        Label nameLabel = new Label("Customer Name:");
        nameLabel.getStyleClass().add("input-label");
        nameField = new TextField();
        nameField.setPromptText("Enter full name");
        nameField.getStyleClass().add("text-field");
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.getStyleClass().add("input-label");
        phoneField = new TextField();
        phoneField.setPromptText("Enter phone number");
        phoneField.getStyleClass().add("text-field");
        grid.add(phoneLabel, 0, 1);
        grid.add(phoneField, 1, 1);

        Label cakeTypeLabel = new Label("Cake Type:");
        cakeTypeLabel.getStyleClass().add("input-label");
        cakeTypeComboBox = new ComboBox<>();
        cakeTypeComboBox.getItems().addAll(
                "Apple", "Carrot", "Cheesecake", "Chocolate",
                "Coffee", "Opera", "Tiramisu"
        );
        cakeTypeComboBox.setPromptText("Select cake type");
        cakeTypeComboBox.getStyleClass().add("combo-box");
        grid.add(cakeTypeLabel, 0, 2);
        grid.add(cakeTypeComboBox, 1, 2);

        Label sizeLabel = new Label("Cake Size:");
        sizeLabel.getStyleClass().add("input-label");
        sizeToggleGroup = new ToggleGroup();

        VBox sizeBox = new VBox(10);
        smallRadio = new RadioButton("Small");
        mediumRadio = new RadioButton("Medium");
        largeRadio = new RadioButton("Large");

        smallRadio.setToggleGroup(sizeToggleGroup);
        mediumRadio.setToggleGroup(sizeToggleGroup);
        largeRadio.setToggleGroup(sizeToggleGroup);

        smallRadio.getStyleClass().add("radio-button");
        mediumRadio.getStyleClass().add("radio-button");
        largeRadio.getStyleClass().add("radio-button");

        sizeBox.getChildren().addAll(smallRadio, mediumRadio, largeRadio);
        grid.add(sizeLabel, 0, 3);
        grid.add(sizeBox, 1, 3);

        deliveryCheckBox = new CheckBox("I live within the free delivery area");
        deliveryCheckBox.getStyleClass().add("check-box");
        grid.add(deliveryCheckBox, 0, 4, 2, 1);

        formSection.getChildren().addAll(titleLabel, grid);
        return formSection;
    }

    private HBox createButtonSection() {
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        Button saveButton = new Button("Save Order");
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnAction(e -> saveOrder());

        Button quitButton = new Button("Quit");
        quitButton.getStyleClass().add("quit-button");
        quitButton.setOnAction(e -> quitApplication());

        buttonBox.getChildren().addAll(saveButton, quitButton);
        return buttonBox;
    }

    private void saveOrder() {
        if (!validateInput()) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Orders.txt", true))) {
            String orderData = formatOrderData();
            writer.write(orderData);
            writer.newLine();

            showAlert("Success", "Order saved successfully", Alert.AlertType.INFORMATION);
            resetForm();

        } catch (IOException e) {
            showAlert("Error", "Failed to save order: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter customer name.", Alert.AlertType.WARNING);
            nameField.requestFocus();
            return false;
        }

        if (phoneField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter phone number.", Alert.AlertType.WARNING);
            phoneField.requestFocus();
            return false;
        }

        if (cakeTypeComboBox.getValue() == null) {
            showAlert("Validation Error", "Please select a cake type.", Alert.AlertType.WARNING);
            cakeTypeComboBox.requestFocus();
            return false;
        }

        if (sizeToggleGroup.getSelectedToggle() == null) {
            showAlert("Validation Error", "Please select a cake size.", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private String formatOrderData() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String cakeType = cakeTypeComboBox.getValue();
        String size = ((RadioButton) sizeToggleGroup.getSelectedToggle()).getText();
        String delivery = deliveryCheckBox.isSelected() ? "Yes" : "No";

        return String.format(
                "Order Time: %s | Name: %s | Phone: %s | Cake: %s | Size: %s | Free Delivery: %s",
                timestamp, name, phone, cakeType, size, delivery
        );
    }

    private void resetForm() {
        nameField.clear();
        phoneField.clear();
        cakeTypeComboBox.setValue(null);
        sizeToggleGroup.selectToggle(null);
        deliveryCheckBox.setSelected(false);
    }

    private void quitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Any unsaved data will be lost.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}