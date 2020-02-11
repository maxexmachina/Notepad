package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    private Clipboard systemClipboard = Clipboard.getSystemClipboard();

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
                    tArea.setText(content);
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

        MenuButton edit = new MenuButton("Edit");
        edit.setStyle("-fx-focus-color: transparent;");
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        MenuItem delete = new MenuItem("Delete");

        cut.setOnAction(event -> {
            String text = tArea.getSelectedText();
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            systemClipboard.setContent(content);

            IndexRange range = tArea.getSelection();
            String origText = tArea.getText();
            String firstPart = origText.substring(0, range.getStart());
            String lastPart = origText.substring(range.getEnd());
            tArea.setText(firstPart + lastPart);
            tArea.positionCaret(range.getStart());
        });
        cut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        copy.setOnAction(event -> {
            String text = tArea.getSelectedText();
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            systemClipboard.setContent(content);
        });
        copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

        paste.setOnAction(event -> {
            String clipboardText = systemClipboard.getString();
            IndexRange range = tArea.getSelection();

            String origText = tArea.getText();
            String firstPart = origText.substring(0, range.getStart());
            String lastPart = origText.substring(range.getEnd());

            String updText = firstPart + clipboardText + lastPart;

            if (clipboardText != null) {
                tArea.setText(updText);
                tArea.positionCaret(range.getStart() + clipboardText.length());
                System.out.println(range.getStart() + clipboardText.length());
            }
        });
        paste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

        delete.setOnAction(event -> {
            IndexRange range = tArea.getSelection();

            String origText = tArea.getText();
            String firstPart = origText.substring(0, range.getStart());
            String lastPart = origText.substring(range.getEnd());

            String updText = firstPart + lastPart;

            tArea.setText(updText);
            tArea.positionCaret(range.getStart());
        });
        delete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));

        edit.getItems().addAll(cut, copy, paste, delete);

        MenuButton help = new MenuButton("Help");
        help.setStyle("-fx-focus-color: transparent;");
        MenuItem viewHelp = new MenuItem("View Help");
        viewHelp.setOnAction(event ->  {
            try {
                Desktop.getDesktop().browse(new URI("https://lmgtfy.com/?q=download+microsoft+vscode"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        help.getItems().addAll(viewHelp);

        topButtons.getChildren().addAll(file, edit, help);

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
