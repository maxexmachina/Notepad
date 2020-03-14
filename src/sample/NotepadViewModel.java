package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.IndexRange;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class NotepadViewModel {
    private final StringProperty textArea = new SimpleStringProperty();
    private Clipboard systemClipboard = Clipboard.getSystemClipboard();

    public StringProperty textAreaProperty() {
        return textArea;
    }
    
    public String getText() {
        return textArea.get();
    }

    public void setText(String text) {
        textArea.set(text);
    }

    public void open(File file) throws IOException {
        if (file != null) {
            StringBuilder content = new StringBuilder();
            for (String line : FileUtils.readAll(file.getPath())) {
                content.append(line).append("\n");
            }
            setText(content.toString());
        }
    }

    public void save(File file) throws IOException {
        if (file != null) {
            System.out.println(file.getName());
            FileUtils.saveFile(getText(), file);
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
