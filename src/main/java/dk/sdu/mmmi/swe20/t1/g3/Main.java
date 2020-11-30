package dk.sdu.mmmi.swe20.t1.g3;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Game;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Scenes;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stageInstance;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stageInstance = stage;

        stage.setTitle("World of Fish");
        stage.setWidth(1600);
        stage.setHeight(900);
        stage.setResizable(false);
        stage.show();

        Game g = new Game("start");
        Thread gameThread = new Thread(g::play);
        gameThread.setDaemon(true);
        gameThread.start();
    }
}
