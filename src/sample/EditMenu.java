package sample;

import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class EditMenu {

    public static void cut(TextArea tArea, Clipboard systemClipboard) {
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
    }

    public static void copy(TextArea tArea, Clipboard systemClipboard) {
        String text = tArea.getSelectedText();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        systemClipboard.setContent(content);
    }

    public static void paste(TextArea tArea, Clipboard systemClipboard) {
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
    }

    public static void delete(TextArea tArea, Clipboard systemClipboard) {
        IndexRange range = tArea.getSelection();

        String origText = tArea.getText();
        String firstPart = origText.substring(0, range.getStart());
        String lastPart = origText.substring(range.getEnd());
        String updText = firstPart + lastPart;

        tArea.setText(updText);
        tArea.positionCaret(range.getStart());
    }
}
