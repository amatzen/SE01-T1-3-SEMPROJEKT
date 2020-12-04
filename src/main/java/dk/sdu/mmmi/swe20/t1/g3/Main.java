package dk.sdu.mmmi.swe20.t1.g3;

import dk.sdu.mmmi.swe20.t1.g3.Utilities.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stageInstance;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Game g = new Game("start");
        Thread gameThread = new Thread(g::play);
        gameThread.setDaemon(true);
        gameThread.start();

        stageInstance = stage;

        stage.setTitle("World of Fish");
        stage.setWidth(1400);
        stage.setHeight(900);
        stage.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("Views/Application.fxml"));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("Views/Assets/main.css").toString());

        stage.setScene(scene);
        stage.show();
    }


}
