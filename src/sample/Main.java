package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();

        TextArea tArea = new TextArea();
        tArea.setWrapText(true);

        HBox topButtons = new HBox();
        MenuButton file = new MenuButton("File");

        MenuItem save = new MenuItem("Save");
        file.getItems().addAll(save);
        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File savedFile = fileChooser.showSaveDialog(primaryStage);
            primaryStage.setTitle(savedFile.getName() + " - Notepad");

            if(savedFile != null) {
                try {
                    SaveFile(tArea.getText(), savedFile);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        MenuButton help = new MenuButton("Help");

        topButtons.getChildren().addAll(file, help);



        vbox.getChildren().addAll(topButtons, tArea);
        VBox.setVgrow(tArea, Priority.ALWAYS);

        Scene scene = new Scene(vbox);
        if (primaryStage.getTitle() == null) {
            primaryStage.setTitle("Untitled - Notepad");
        }
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void SaveFile(String content, File file) throws IOException {
        try(FileWriter fw = new FileWriter(file)) {
            fw.write(content);
        }

    }
}
