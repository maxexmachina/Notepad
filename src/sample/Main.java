package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        HBox topButtons = new HBox();

        TextArea tArea = new TextArea();
        tArea.setWrapText(true);
        tArea.setStyle("-fx-focus-color: transparent;");

        MenuButton file = new MenuButton("File");
        file.setStyle("-fx-focus-color: transparent;");
        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open...");
        MenuItem save = new MenuItem("Save");

        newFile.setOnAction(event -> {
            tArea.clear();
            setStageTitle(null, primaryStage);
        });
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        open.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    String content = listToString(readAll(selectedFile.getPath()));
                    tArea.textProperty().setValue(content);
                    setStageTitle(selectedFile.getName(), primaryStage);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File savedFile = fileChooser.showSaveDialog(primaryStage);
            if (savedFile != null) {
                try {
                    setStageTitle(savedFile.getName(), primaryStage);
                    saveFile(tArea.getText(), savedFile);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        file.getItems().addAll(newFile, open, save);

        MenuButton help = new MenuButton("Help");
        help.setStyle("-fx-focus-color: transparent;");
        MenuItem viewHelp = new MenuItem("View Help");

        help.getItems().addAll(viewHelp);

        topButtons.getChildren().addAll(file, help);

        vbox.getChildren().addAll(topButtons, tArea);
        VBox.setVgrow(tArea, Priority.ALWAYS);

        Scene scene = new Scene(vbox);
        if (primaryStage.getTitle() == null) {
            setStageTitle(null, primaryStage);
        }
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void saveFile(String content, File file) throws IOException {
        try(FileWriter fw = new FileWriter(file)) {
            fw.write(content);
        }
    }

    private void setStageTitle(String fileName, Stage stage) {
        if (fileName != null) {
            stage.setTitle(fileName + " - Notepad");
        } else {
            stage.setTitle("Untitled - Notepad");
        }
    }

    private static List<String> readAll(String path) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(path))) {
            while (scanner.hasNext()) {
                result.add(scanner.nextLine());
            }
            return result;
        }
    }

    private static String listToString(List<String> lines) {
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            result.append(line).append("\n");
        }
        return result.toString();
    }
}
