package sample;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileMenu {

    public static void open(TextArea tArea, Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                String content = listToString(FileUtils.readAll(selectedFile.getPath()));
                tArea.setText(content);
                String fileName = selectedFile.getName();
                Main.setStageTitle(fileName, primaryStage);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void save(TextArea tArea, Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File savedFile = fileChooser.showSaveDialog(primaryStage);
        if (savedFile != null) {
            try {
                String fileName = savedFile.getName();
                Main.setStageTitle(fileName, primaryStage);
                FileUtils.saveFile(tArea.getText(), savedFile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void newFile(TextArea tArea, Stage primaryStage) {
        tArea.clear();
        Main.setStageTitle(null, primaryStage);
    }

    private static String listToString(List<String> lines) {
        String result = "";
        for (String line : lines) {
            result += line + "\n";
        }
        return result;
    }
}
