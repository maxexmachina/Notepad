package sample;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class NotepadView extends VBox {
    private Stage primaryStage;
    private MenuBar top = new MenuBar();

    private Menu file = new Menu("File");
    private MenuItem newFile = new MenuItem("New");
    private MenuItem open = new MenuItem("Open...");
    private MenuItem save = new MenuItem("Save");

    private Menu edit = new Menu("Edit");
    private MenuItem cut = new MenuItem("Cut");
    private MenuItem copy = new MenuItem("Copy");
    private MenuItem paste = new MenuItem("Paste");
    private MenuItem delete = new MenuItem("Delete");

    private Menu help = new Menu("Help");
    private MenuItem viewHelp = new MenuItem("Get Help");

    private TextArea tArea = new TextArea();

    private NotepadViewModel viewModel;

    public NotepadView(NotepadViewModel viewModel, Stage stage) {
        primaryStage = stage;

        this.viewModel = viewModel;
        configureView();
        if (primaryStage.getTitle() == null) {
            setStageTitle(null, primaryStage);
        }
        tArea.textProperty().bindBidirectional(viewModel.textAreaProperty());
    }

    private void configureView() {
        // Set options
        tArea.setWrapText(true);
        tArea.setStyle("-fx-focus-color: transparent;");
        VBox.setVgrow(tArea, Priority.ALWAYS);

        // Set event handlers
        newFile.setOnAction( event -> {
            tArea.setText("");
            setStageTitle(null, primaryStage);
        });

        open.setOnAction( event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                viewModel.open(selectedFile);
            } catch (IOException e) {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setHeaderText("IOException thrown in Open method\n");
                errorDialog.setContentText(e.getMessage());
                errorDialog.showAndWait();
            }
            if (selectedFile != null)
                setStageTitle(selectedFile.getName(), primaryStage);
        } );

        save.setOnAction( event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File savedFile = fileChooser.showSaveDialog(primaryStage);
            try {
                viewModel.save(savedFile);
            } catch (IOException e) {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setHeaderText("IOException thrown in Save method\n");
                errorDialog.setContentText(e.getMessage());
                errorDialog.showAndWait();
            }
            if (savedFile != null)
                setStageTitle(savedFile.getName(), primaryStage);
        } );

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

        top.getMenus().addAll(file, edit, help);
        getChildren().addAll(top, tArea);
    }

    private void setStageTitle(String fileName, Stage stage) {
        if (fileName != null) {
            stage.setTitle(fileName + " - Notepad");
        } else {
            stage.setTitle("Untitled - Notepad");
        }
    }
}
