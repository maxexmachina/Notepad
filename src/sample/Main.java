package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();


        HBox topButtons = new HBox();
        MenuButton file = new MenuButton("File");

        MenuItem save = new MenuItem("Save");
        file.getItems().addAll(save);
        save.setOnAction(event -> {
            System.out.println("Save");
        });


        MenuButton help = new MenuButton("Help");

        topButtons.getChildren().addAll(file, help);


        TextArea tArea = new TextArea();
        tArea.setWrapText(true);

        vbox.getChildren().addAll(topButtons, tArea);
        VBox.setVgrow(tArea, Priority.ALWAYS);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Notepad");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
