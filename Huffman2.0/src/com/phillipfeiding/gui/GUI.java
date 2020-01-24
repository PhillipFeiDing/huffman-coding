package com.phillipfeiding.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.phillipfeiding.domain.*;
import com.phillipfeiding.fileIO.*;

public class GUI extends Application {

    private Stage window;
    private TextField fromField;
    private TextField toField;
    private static final String TXT_EXTENSION = ".txt";
    private static final String DAT_EXTENSION = ".dat";

    /**
     * create the window and show
     * @param window the primary stage of the application
     */
    public void start(Stage window) {
        this.window = window;
        this.window.setTitle("Huffman Encoder/Decoder2.0");
        this.window.setResizable(false);

        Scene encodeScene = createEncodingScene();
        window.setScene(encodeScene);
        this.window.show();
    }

    /**
     * Create the encoding scene
     * @return the encoding com.phillipfeiding.gui.GUI scene
     */
    private Scene createEncodingScene() {
        // All Elements
        Label title = new Label("Huffman Encoder");
        StackPane titleWrapper = new StackPane();
        titleWrapper.getChildren().add(title);
        titleWrapper.setAlignment(Pos.CENTER);

        Label prompt1 = new Label(
                "Input the path of file to encode.");

        Label prompt2 = new Label(
                "This version only supports .txt: ");

        Label fromLabel = new Label("Read from: ");

        Label toLabel = new Label("Save to: ");

        TextField fromField = new TextField();
        this.fromField = fromField;
        fromField.textProperty().addListener(
                (observable, oldValue, newValue) ->
            toField.setText(newValue)
        );

        TextField toField = new TextField();
        this.toField = toField;

        Label fromExtension = new Label(TXT_EXTENSION);

        Label toExtension = new Label(DAT_EXTENSION);

        Button encodeButton = new Button("Encode");
        encodeButton.setMinWidth(140);
        encodeButton.setMaxWidth(140);
        encodeButton.setOnAction(e -> {
            String path = this.fromField.getText() + TXT_EXTENSION;
            String to = this.toField.getText() + DAT_EXTENSION;
            encode(path, to);
        });
        Button decodeButton = new Button("To Decoder ...");
        decodeButton.setOnAction(e -> {
            window.setScene(createDecodingScene());
        });
        decodeButton.setMinWidth(140);
        decodeButton.setMaxWidth(140);

        // Nested Layouts
        VBox texts = new VBox();
        texts.getChildren().addAll(prompt1, prompt2);

        GridPane grid = new GridPane();
        grid.add(fromLabel, 0, 0);
        grid.add(fromField, 1, 0);
        grid.add(fromExtension, 2, 0);
        grid.add(toLabel, 0, 1);
        grid.add(toField, 1, 1);
        grid.add(toExtension, 2, 1);
        grid.setHgap(10);
        grid.setVgap(10);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(encodeButton, decodeButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(titleWrapper, texts, grid, buttons);
        vbox.setSpacing(10);

        return new Scene(vbox);
    }

    /**
     * encoding process
     * @param path from path
     * @param to to directory
     */
    private void encode(String path, String to) {

        // read file
        String message = null;
        try {
            message = FileIO.read(path);
        } catch (FileNotFoundException e) {
            AlertBox.display(
                    "File Exception", "File not found.");
            return;
        } catch (IOException e) {
            message = null;
        }
        if (message == null) {
            AlertBox.display(
                    "File Exception", "Cannot read file.");
            return;
        }

        // encode file
        byte[] encoded = null;
        Map<Character, String> scheme = null;
        int finalCut = 8;
        try {
            Encoder encoder = new Encoder();
            encoder.encode(message);
            encoded = encoder.getEncoded();
            scheme = encoder.getScheme();
            finalCut = encoder.getFinalCut();
        } catch (Exception e) {
            encoded = null;
            scheme = null;
        }
        if (encoded == null || scheme == null) {
            AlertBox.display("Encoding Error",
                    "The encoder cannot encode this file, "
                            + "because it is empty, \n"
                            + "Or it uses an unknown encoding rule.");
            return;
        }

        // check overwrite
        if (!overwrite(to)) {
            return;
        }

        // write encoded file
        try {
            ByteFileWriter.write(to, encoded, scheme, finalCut);
        } catch (IOException e) {
            AlertBox.display(
                    "File Exception",
                    "Failed to write encoded file.");
            return;
        }

        AlertBox.display("Completed", "Encoded file has been successfully saved.");
    }

    /**
     * Create the encoding scene
     * @return the encoding com.phillipfeiding.gui.GUI scene
     */
    private Scene createDecodingScene() {
        // All Elements
        Label title = new Label("Huffman Decoder");
        StackPane titleWrapper = new StackPane();
        titleWrapper.getChildren().add(title);
        titleWrapper.setAlignment(Pos.CENTER);

        Label prompt1 = new Label(
                "Input the path of file to decode.");

        Label prompt2 = new Label(
                "This version only supports .dat: ");

        Label fromLabel = new Label("Read from: ");

        Label toLabel = new Label("Save to: ");

        TextField fromField = new TextField();
        this.fromField = fromField;
        fromField.textProperty().addListener(
                (observable, oldValue, newValue) ->
                        toField.setText(newValue)
        );

        TextField toField = new TextField();
        this.toField = toField;

        Label fromExtension = new Label(DAT_EXTENSION);

        Label toExtension = new Label(TXT_EXTENSION);

        Button decodeButton = new Button("Decode");
        decodeButton.setMinWidth(140);
        decodeButton.setMaxWidth(140);
        decodeButton.setOnAction(e -> {
            String path = this.fromField.getText() + DAT_EXTENSION;
            String to = this.toField.getText() + TXT_EXTENSION;
            decode(path, to);
        });
        Button encodeButton = new Button("To Encoder ...");
        encodeButton.setOnAction(e -> {
            window.setScene(createEncodingScene());
        });
        encodeButton.setMinWidth(140);
        encodeButton.setMaxWidth(140);

        // Nested Layouts
        VBox texts = new VBox();
        texts.getChildren().addAll(prompt1, prompt2);

        GridPane grid = new GridPane();
        grid.add(fromLabel, 0, 0);
        grid.add(fromField, 1, 0);
        grid.add(fromExtension, 2, 0);
        grid.add(toLabel, 0, 1);
        grid.add(toField, 1, 1);
        grid.add(toExtension, 2, 1);
        grid.setHgap(10);
        grid.setVgap(10);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(decodeButton, encodeButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(titleWrapper, texts, grid, buttons);
        vbox.setSpacing(10);

        return new Scene(vbox);
    }

    /**
     * decoding process
     * @param path from path
     * @param to directory
     */
    private void decode(String path, String to) {
        String s = null;
        try {
            Decoder decoder = new Decoder(ByteFileReader.read(path));
            s = decoder.decode();
        } catch (FileNotFoundException e) {
            AlertBox.display(
                    "File Exception", "File not found");
            return;
        } catch (IOException e) {
            s = null;
        } catch (IllegalArgumentException e) {
            AlertBox.display(
                    "Decoder Exception", e.getMessage());
            return;
        }
        if (s == null) {
            AlertBox.display(
                    "File Exception", "Cannot read file.");
            return;
        }

        // check overwrite
        if (!overwrite(to)) {
            return;
        }

        try {
            FileIO.write(to, s);
        } catch (IOException e) {
            AlertBox.display(
                    "File Exception",
                    "Failed to write decoded file, " + "\nplease check if the folder exists.");
            return;
        }

        AlertBox.display("Completed", "Decoded file has been successfully saved.");
    }

    /**
     * check if the file exists and let the user decide if to overwrite
     * @param to the path to the file to be written
     * @return if overwrite should occur
     */
    private boolean overwrite(String to) {
        File file = new File(to);
        if (file.exists()) {
            return ConfirmBox.display("Warning", "File \""
                    + to + "\" exists. \nAre you sure to overwrite?");
        }
        return true;
    }

    /**
     * entry of the program
     * @param args whatever is user input
     */
    public static void main(String[] args) {
        launch(args);
    }
}