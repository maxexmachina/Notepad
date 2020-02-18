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

    @Override
    public void start(Stage primaryStage) {
        NotepadView view = new NotepadView(primaryStage);

        Scene scene = new Scene(view);
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
