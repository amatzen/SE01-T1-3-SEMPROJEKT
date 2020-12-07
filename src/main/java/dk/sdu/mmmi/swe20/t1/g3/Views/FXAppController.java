package dk.sdu.mmmi.swe20.t1.g3.Views;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Main;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.FXUtils;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;
import dk.sdu.mmmi.swe20.t1.g3.Views.Objects.Player;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class FXAppController implements Initializable {
    SceneController sceneController = SceneController.getInstance();
    ItemController itemController = ItemController.getInstance();
    PubSub pubSub = PubSub.getInstance();

    @FXML
    AnchorPane AppWindow;

    @FXML
    AnchorPane GameWindow;

    @FXML
    AnchorPane UI_Inventory;

    @FXML
    Text UI_SceneLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pubSub.addListener("fx_sceneChanged", (type, object) -> onSceneChange(object));
        pubSub.addListener("fx_notify", (type, object) -> {
            String raw = (String) object;
            String[] formatted = raw.split("#");

            //new FXUtils().emitNotify(formatted[0], formatted[1], 10);

            Platform.runLater(() -> {
                Notifications.create()
                        .darkStyle()
                        .title(formatted[0])
                        .text(formatted[1])
                        .hideAfter(Duration.seconds(5))
                        .show();
            });
        });

        setSceneLabel(sceneController.getCurrentScene().getName());
        spawnItems(sceneController.getCurrentScene());
        spawnPlayer();

    }

    private void spawnPlayer() {
        Player player = new Player((1400/2) - 40/2, (840/2) - 70/2, 40, 70, Color.BLUE);

        Platform.runLater(() -> {
            AppWindow.getScene().setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case W, A, S, D -> player.handleKeyPress(e.getCode());
                    case UP, LEFT, DOWN, RIGHT -> handleSceneKeyPress(e.getCode());

                    case E -> {
                        player.stopMovement();

                        String whatToPickup = new FXUtils().prompt("Hvad skal jeg samle op?", "Skriv genstandens navn");
                        if(whatToPickup.equals("")) return;
                        pubSub.publish("executeCommand", "pickup " + whatToPickup);
                    }
                }
            });

            AppWindow.getScene().setOnKeyReleased(e -> {
                switch (e.getCode()) {
                    case W, A, S, D -> player.handleKeyRelease(e.getCode());
                }
            });

            AppWindow.getChildren().add(1,player);
        });
    }

    private void onSceneChange() {
        onSceneChange(null);
    }
    private void onSceneChange(Object payload) {
        Scene s = sceneController.getSceneBySlug((String) payload);

        setSceneLabel(s.getName());
        spawnItems(s);

    }

    private void handleSceneKeyPress(KeyCode keyCode) {
        switch (keyCode) {
            case UP -> pubSub.publish("executeCommand", "go op");
            case LEFT -> pubSub.publish("executeCommand", "go venstre");
            case DOWN -> pubSub.publish("executeCommand", "go ned");
            case RIGHT -> pubSub.publish("executeCommand", "go højre");
        }

    }

    @FXML
    private void setSceneLabel(String sceneName) {
        UI_SceneLabel.setText(sceneName);
    }

    private void spawnItems(Scene s) {
        Platform.runLater(() -> {
            ArrayList<Item> i = itemController.getItemsByScene(s);
            for (Item item : i) {
                SceneLocation pos = item.getSpawns().get(s);
                if(pos.getX() == 0 && pos.getY() == 0) continue;

                Rectangle box = new Rectangle();
                box.setX(pos.getX());
                box.setY(pos.getY());

                box.setWidth(120);
                box.setHeight(120);

                InputStream is = Main.class.getResourceAsStream(item.getTexture());
                Image img = new Image(is);

                box.setFill(new ImagePattern(img));

                GameWindow.getChildren().add(box);
            }
        });
    }

    @FXML
    public void setGameWindow(String scene) {
        GameWindow.getChildren().clear();
        try {
            ClassLoader classLoader = FXMLLoader.getDefaultClassLoader();
            URL url = classLoader.getResource(scene);
            GameWindow.getChildren().setAll((Collection<? extends Node>) FXMLLoader.load(url));
            GameWindow.setStyle("-fx-background-color: aliceblue;");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
