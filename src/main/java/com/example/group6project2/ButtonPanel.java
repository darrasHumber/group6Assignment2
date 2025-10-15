package com.example.group6project2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

// class that creates buttons and wires their actions
public class ButtonPanel {
    private final OrderController controller;

    // constructor
    public ButtonPanel(OrderController controller) {
        this.controller = controller;
    }

    // creating the HBox for the buttons, and triggers the various methods on the button click
    public HBox getButtonSection() {
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        Button saveButton = new Button("Save Order");
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnAction(e -> controller.saveOrder());

        Button quitButton = new Button("Quit");
        quitButton.getStyleClass().add("quit-button");
        quitButton.setOnAction(e -> controller.quitApplication());

        buttonBox.getChildren().addAll(saveButton, quitButton);
        return buttonBox;
    }
}
