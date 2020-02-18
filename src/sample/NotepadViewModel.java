package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class NotepadViewModel {
    private final StringProperty textArea = new SimpleStringProperty();

    public StringProperty textAreaProperty() {
        return textArea;
    }
    
    public String getText() {
        return textArea.get();
    }

    public void setText(String text) {
        textArea.set(text);
    }

    public void newFile(Stage primaryStage) {
        setText("");
        Main.setStageTitle(null, primaryStage);
    }

    public void open(Stage primaryStage) {
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

    public void save(Stage primaryStage) {
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
//
//    public void cut() {
//        String text = tArea.getSelectedText();
//        ClipboardContent content = new ClipboardContent();
//        content.putString(text);
//        systemClipboard.setContent(content);
//
//        IndexRange range = tArea.getSelection();
//        String origText = tArea.getText();
//        String firstPart = origText.substring(0, range.getStart());
//        String lastPart = origText.substring(range.getEnd());
//
//        tArea.setText(firstPart + lastPart);
//        tArea.positionCaret(range.getStart());
//    }
//
//    public void copy() {
//        String text = tArea.getSelectedText();
//        ClipboardContent content = new ClipboardContent();
//        content.putString(text);
//        systemClipboard.setContent(content);
//    }
//
//    public void paste() {
//        String clipboardText = systemClipboard.getString();
//        IndexRange range = tArea.getSelection();
//
//        String origText = tArea.getText();
//        String firstPart = origText.substring(0, range.getStart());
//        String lastPart = origText.substring(range.getEnd());
//        String updText = firstPart + clipboardText + lastPart;
//
//        if (clipboardText != null) {
//            tArea.setText(updText);
//            tArea.positionCaret(range.getStart() + clipboardText.length());
//            System.out.println(range.getStart() + clipboardText.length());
//        }
//    }
//
//    public void delete() {
//        IndexRange range = tArea.getSelection();
//
//        String origText = tArea.getText();
//        String firstPart = origText.substring(0, range.getStart());
//        String lastPart = origText.substring(range.getEnd());
//        String updText = firstPart + lastPart;
//
//        tArea.setText(updText);
//        tArea.positionCaret(range.getStart());
//    }

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
