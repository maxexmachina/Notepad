package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.IndexRange;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class NotepadViewModel {
    private final StringProperty textArea = new SimpleStringProperty();
    private Clipboard systemClipboard = Clipboard.getSystemClipboard();
    private Stage primaryStage;

    public NotepadViewModel(Stage stage) {
        primaryStage = stage;
    }

    public StringProperty textAreaProperty() {
        return textArea;
    }
    
    public String getText() {
        return textArea.get();
    }

    public void setText(String text) {
        textArea.set(text);
    }

    public void newFile() {
        setText("");
        Main.setStageTitle(null, primaryStage);
    }

    public void open() {
        FileChooser fileChooser = new FileChooser();

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                String content = "";
                for (String line : FileUtils.readAll(selectedFile.getPath())) {
                    content += line + "\n";
                }
                setText(content);
                String fileName = selectedFile.getName();
                Main.setStageTitle(fileName, primaryStage);
            } catch (IOException e) {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setHeaderText("IOException thrown in Open method\n");
                errorDialog.setContentText(e.getMessage());
                errorDialog.showAndWait();
            }
        }
    }

    public void save() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File savedFile = fileChooser.showSaveDialog(primaryStage);
        if (savedFile != null) {
            try {
                String fileName = savedFile.getName();
                Main.setStageTitle(fileName, primaryStage);
                FileUtils.saveFile(getText(), savedFile);
            } catch (IOException e) {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setHeaderText("IOException thrown in Save method\n");
                errorDialog.setContentText(e.getMessage());
                errorDialog.showAndWait();
            }
        }
    }

    public void cut(String selectedText, IndexRange range) {

        ClipboardContent content = new ClipboardContent();
        content.putString(selectedText);
        systemClipboard.setContent(content);

        String firstPart = textAreaProperty().get().substring(0, range.getStart());
        String lastPart = textAreaProperty().get().substring(range.getEnd());

        setText(firstPart + lastPart);
    }

    public void copy(String selectedText) {
        ClipboardContent content = new ClipboardContent();
        content.putString(selectedText);
        systemClipboard.setContent(content);
    }

    public void paste(IndexRange range) {
        String clipboardText = systemClipboard.getString();

        String firstPart = getText().substring(0, range.getStart());
        String lastPart = getText().substring(range.getEnd());
        String updText = firstPart + clipboardText + lastPart;

        if (clipboardText != null) {
            setText(updText);
        }
    }

    public void delete(IndexRange range) {
        String firstPart = getText().substring(0, range.getStart());
        String lastPart = getText().substring(range.getEnd());
        String updText = firstPart + lastPart;

        setText(updText);
    }

    public void help() {
        try {
            Desktop.getDesktop().browse(new URI("https://lmgtfy.com/?q=download+microsoft+vscode"));
        } catch (IOException | URISyntaxException e) {
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setHeaderText("Exception thrown in Help method\n");
            errorDialog.setContentText(e.getMessage());
            errorDialog.showAndWait();
        }
    }

}
