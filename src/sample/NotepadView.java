package sample;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NotepadView extends VBox {
    private HBox topButtons = new HBox();

    private MenuButton file = new MenuButton("File");
    private MenuItem newFile = new MenuItem("New");
    private MenuItem open = new MenuItem("Open...");
    private MenuItem save = new MenuItem("Save");

    private MenuButton edit = new MenuButton("Edit");
    private MenuItem cut = new MenuItem("Cut");
    private MenuItem copy = new MenuItem("Copy");
    private MenuItem paste = new MenuItem("Paste");
    private MenuItem delete = new MenuItem("Delete");

    private MenuButton help = new MenuButton("Help");
    private MenuItem viewHelp = new MenuItem("Get Help");

    private TextArea tArea = new TextArea();

    private NotepadViewModel viewModel;

    public NotepadView(NotepadViewModel viewModel) {
        this.viewModel = viewModel;
        configureView();
        tArea.textProperty().bindBidirectional(viewModel.textAreaProperty());
    }

    private void configureView() {
        // Set options
        tArea.setWrapText(true);
        tArea.setStyle("-fx-focus-color: transparent;");
        VBox.setVgrow(tArea, Priority.ALWAYS);
        file.setStyle("-fx-focus-color: transparent;");
        edit.setStyle("-fx-focus-color: transparent;");
        help.setStyle("-fx-focus-color: transparent;");

        // Set event handlers
        newFile.setOnAction( event -> viewModel.newFile() );
        open.setOnAction( event -> viewModel.open() );
        save.setOnAction( event -> viewModel.save() );

        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        file.getItems().addAll(newFile, open, save);

        cut.setOnAction( event -> viewModel.cut(tArea.getSelectedText(), tArea.getSelection()) );
        copy.setOnAction( event -> viewModel.copy(tArea.getSelectedText()) );
        paste.setOnAction( event -> viewModel.paste(tArea.getSelection()) );
        delete.setOnAction( event -> viewModel.delete(tArea.getSelection()) );

        cut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        paste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        delete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));

        edit.getItems().addAll(cut, copy, paste, delete);

        viewHelp.setOnAction( event -> viewModel.help() );
        help.getItems().addAll(viewHelp);

        topButtons.getChildren().addAll(file, edit, help);
        this.getChildren().addAll(topButtons, tArea);
    }
}
