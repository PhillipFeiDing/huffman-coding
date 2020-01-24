package com.phillipfeiding.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;

/**
 * @author dingfei
 * @version 1.0.0
 */
public class ConfirmBox {
    public static final int DEFAULT_MIN_WIDTH = 200;
    public static final int DEFAULT_MIN_HEIGHT = 120;

    private static boolean response;

    /**
     * pop out an confirm message box with given width and height that prompts the user
     * to click on "yer" or "no", and return the user's response as a boolean. The message
     * box can adjust its size based on contents given.
     * @param title a title that will be displayed on the tab
     * @param message a message that prompts the user to click on given options
     * @param minWidth minimum width of the window
     * @param minHeight minimum height of the window
     * @return user's response, where true means "yes", and false means "no"
     */
    public static boolean display(String title, String message, int minWidth, int minHeight) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMinWidth(minWidth);
        window.setMinHeight(minHeight);
        window.setResizable(false);

        // create the message label;
        Label label = new Label();
        label.setText(message);

        // create two buttons;
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        // assign functionality to the two buttons;
        yesButton.setOnAction(e -> {
            ConfirmBox.response = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            ConfirmBox.response = false;
            window.close();
        });

        // create proper layout and put in components
        HBox subLayout = new HBox();
        subLayout.getChildren().addAll(yesButton, noButton);
        subLayout.setSpacing(40);
        subLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.getChildren().addAll(label, subLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);

        // create scene and apply layout
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return ConfirmBox.response;
    }

    /**
     * pop out an confirm message box with default width and height that prompts the user
     * to click on "yer" or "no", and return the user's response as a boolean. The message
     * box can adjust its size based on contents given.
     * @param title a title that will be displayed on the tab
     * @param message a message that prompts the user to click on given options
     * @return user's response, where true means "yes", and false means "no"
     */
    public static boolean display(String title, String message) {
        return ConfirmBox.display(title, message, ConfirmBox.DEFAULT_MIN_WIDTH, ConfirmBox.DEFAULT_MIN_HEIGHT);
    }
}
