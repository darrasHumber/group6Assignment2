package com.example.group6project2;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

// This class builds the form section
public class OrderForm {
    // Name, Phone, ComboBox, Size Toggle Group and Delivery Checkbox fields
    private TextField nameField = new TextField();
    private TextField phoneField = new TextField();
    private ComboBox<String> cakeTypeComboBox = new ComboBox<>();
    private ToggleGroup sizeToggleGroup = new ToggleGroup();
    private CheckBox deliveryCheckBox = new CheckBox("I live within the free delivery area");

    // building the form layout using VBox and GridPane
    public VBox getFormSection() {
        VBox formSection = new VBox(15);
        formSection.getStyleClass().add("form-section");

        Label titleLabel = new Label("Bakery Order Form");
        titleLabel.getStyleClass().add("form-title");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));

        nameField.setPromptText("Enter full name");
        phoneField.setPromptText("Enter phone number");
        cakeTypeComboBox.getItems().addAll("Apple", "Carrot", "Cheesecake", "Chocolate", "Coffee", "Opera", "Tiramisu");
        cakeTypeComboBox.setPromptText("Select cake type");

        RadioButton smallRadio = new RadioButton("Small");
        RadioButton mediumRadio = new RadioButton("Medium");
        RadioButton largeRadio = new RadioButton("Large");
        smallRadio.setToggleGroup(sizeToggleGroup);
        mediumRadio.setToggleGroup(sizeToggleGroup);
        largeRadio.setToggleGroup(sizeToggleGroup);

        VBox sizeBox = new VBox(10, smallRadio, mediumRadio, largeRadio);

        grid.add(new Label("Customer Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Phone Number:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("Cake Type:"), 0, 2);
        grid.add(cakeTypeComboBox, 1, 2);
        grid.add(new Label("Cake Size:"), 0, 3);
        grid.add(sizeBox, 1, 3);
        grid.add(deliveryCheckBox, 0, 4, 2, 1);

        formSection.getChildren().addAll(titleLabel, grid);
        return formSection;
    }

    // getters for controller access
    public TextField getNameField() { return nameField; }
    public TextField getPhoneField() { return phoneField; }
    public ComboBox<String> getCakeTypeComboBox() { return cakeTypeComboBox; }
    public ToggleGroup getSizeToggleGroup() { return sizeToggleGroup; }
    public CheckBox getDeliveryCheckBox() { return deliveryCheckBox; }
}