package com.example.group6project2;

import javafx.scene.control.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This class handles validation, saving, formatting and alerts
public class OrderController {
    private final OrderForm form;

    // constructor
    public OrderController(OrderForm form) {
        this.form = form;
    }

    // method for button click save
    public void saveOrder() {
        // validating input using validateInput() method
        if (!validateInput()) return;

        // writes the formatted order data to Orders.txt, and displays appropriate alerts
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Orders.txt", true))) {
            writer.write(formatOrderData());
            writer.newLine();
            showAlert("Success", "Order saved successfully", Alert.AlertType.INFORMATION);
            resetForm();
        } catch (IOException e) {
            showAlert("Error", "Failed to save order: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // method for the quit button, and asks for confirmation that user wants to exit
    public void quitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Any unsaved data will be lost.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    // method to validate input
    private boolean validateInput() {
        // checking if any of the fields are empty
        if (form.getNameField().getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter customer name.", Alert.AlertType.WARNING);
            form.getNameField().requestFocus();
            return false;
        }
        if (form.getPhoneField().getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter phone number.", Alert.AlertType.WARNING);
            form.getPhoneField().requestFocus();
            return false;
        }
        String phone = form.getPhoneField().getText().trim();
        if (!phone.matches("[\\d\\s\\-()]{7,20}")) {
            showAlert("Validation Error", "Phone number must contain only digits, spaces, dashes, or brackets.", Alert.AlertType.WARNING);
            form.getPhoneField().requestFocus();
            return false;
        }
        if (form.getCakeTypeComboBox().getValue() == null) {
            showAlert("Validation Error", "Please select a cake type.", Alert.AlertType.WARNING);
            form.getCakeTypeComboBox().requestFocus();
            return false;
        }
        if (form.getSizeToggleGroup().getSelectedToggle() == null) {
            showAlert("Validation Error", "Please select a cake size.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    // collects all input values and formats them to a readable string with a time stamp
    private String formatOrderData() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String name = form.getNameField().getText().trim();
        String phone = form.getPhoneField().getText().trim();
        String cakeType = form.getCakeTypeComboBox().getValue();
        String size = ((RadioButton) form.getSizeToggleGroup().getSelectedToggle()).getText();
        String delivery = form.getDeliveryCheckBox().isSelected() ? "Yes" : "No";

        return String.format("Order Time: %s | Name: %s | Phone: %s | Cake: %s | Size: %s | Free Delivery: %s",
                timestamp, name, phone, cakeType, size, delivery);
    }

    // resets the form by clearing all the fields
    private void resetForm() {
        form.getNameField().clear();
        form.getPhoneField().clear();
        form.getCakeTypeComboBox().setValue(null);
        form.getSizeToggleGroup().selectToggle(null);
        form.getDeliveryCheckBox().setSelected(false);
    }

    // utility method to show alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}