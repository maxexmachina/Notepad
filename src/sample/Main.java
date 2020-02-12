package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Main extends Application {

    private Clipboard systemClipboard = Clipboard.getSystemClipboard();

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        HBox topButtons = new HBox();

        TextArea tArea = new TextArea();
        tArea.setWrapText(true);
        tArea.setStyle("-fx-focus-color: transparent;");
        VBox.setVgrow(tArea, Priority.ALWAYS);


        MenuButton file = new MenuButton("File");
        file.setStyle("-fx-focus-color: transparent;");

        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open...");
        MenuItem save = new MenuItem("Save");

        FileMenu fm = new FileMenu();
        newFile.setOnAction(event -> fm.newFile(tArea, primaryStage));
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        open.setOnAction(event -> fm.open(tArea, primaryStage));
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        save.setOnAction(event -> fm.save(tArea, primaryStage));
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        file.getItems().addAll(newFile, open, save);


        MenuButton edit = new MenuButton("Edit");
        edit.setStyle("-fx-focus-color: transparent;");

        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        MenuItem delete = new MenuItem("Delete");

        EditMenu em = new EditMenu();
        cut.setOnAction(event -> em.cut(tArea, systemClipboard));
        cut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        copy.setOnAction(event -> em.copy(tArea, systemClipboard));
        copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

        paste.setOnAction(event -> em.paste(tArea, systemClipboard));
        paste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

        delete.setOnAction(event -> em.delete(tArea, systemClipboard));
        delete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));

        edit.getItems().addAll(cut, copy, paste, delete);


        MenuButton help = new MenuButton("Help");
        help.setStyle("-fx-focus-color: transparent;");

        MenuItem viewHelp = new MenuItem("View Help");

        viewHelp.setOnAction(event ->  {
            try {
                Desktop.getDesktop().browse(new URI("https://lmgtfy.com/?q=download+microsoft+vscode"));
            } catch (IOException | URISyntaxException e) {
                System.out.println(e.getMessage());
            }
        });
        help.getItems().addAll(viewHelp);

        topButtons.getChildren().addAll(file, edit, help);
        vbox.getChildren().addAll(topButtons, tArea);

        Scene scene = new Scene(vbox);
        if (primaryStage.getTitle() == null) {
            setStageTitle(null, primaryStage);
        }
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setStageTitle(String fileName, Stage stage) {
        if (fileName != null) {
            fileName = fileName.substring(0, fileName.length() - 4);
            stage.setTitle(fileName + " - Notepad");
        } else {
            stage.setTitle("Untitled - Notepad");
        }
    }
}
