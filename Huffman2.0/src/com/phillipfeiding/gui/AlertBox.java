package com.phillipfeiding.gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

    private static final int MAX_LEN = 40;

    /**
     * display an error/info message and wait
     * @param title the title fo the box
     * @param message the message the box is about to display
     */
    public static void display(String title, String message) {
        if (title == null || message == null) {
            throw new IllegalArgumentException(
                    "Title and message cannot be null.");
        }
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMinWidth(200);
        window.setMinHeight(120);
        window.setResizable(false);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");

        closeButton.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
