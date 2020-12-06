package dk.sdu.mmmi.swe20.t1.g3;

import dk.sdu.mmmi.swe20.t1.g3.Utilities.Game;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application {
    private PubSub pubSub = PubSub.getInstance();
    Game g = new Game("start");
    Thread gameThread = new Thread(g::play);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        gameThread.setDaemon(true);
        gameThread.start();

        stage.setTitle("World of Fish");
        stage.setWidth(1400);
        stage.setHeight(900);
        stage.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("Views/Application.fxml"));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("Views/Assets/main.css").toString());

        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                stop();
                Platform.exit();
            }
        });
        stage.show();
    }

    @Override
    public void stop(){
        pubSub.publish("exitApplication", true);
        gameThread.interrupt();
        System.exit(0);
    }

}
