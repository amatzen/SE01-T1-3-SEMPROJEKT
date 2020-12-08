package dk.sdu.mmmi.swe20.t1.g3.Views;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Main;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.FXUtils;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;
import dk.sdu.mmmi.swe20.t1.g3.Views.Objects.Player;
import dk.sdu.mmmi.swe20.t1.g3.Views.Objects.PlayerActionIdenticator;
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
import java.util.stream.Collectors;

/**
 * The type Fx app controller.
 */
public class FXAppController implements Initializable {
    /**
     * The Scene controller.
     */
    SceneController sceneController = SceneController.getInstance();
    /**
     * The Item controller.
     */
    ItemController itemController = ItemController.getInstance();
    /**
     * The Publish-Subscribe Model.
     */
    PubSub pubSub = PubSub.getInstance();

    /**
     * The AppWindow.
     */
    @FXML
    AnchorPane AppWindow;

    /**
     * The GameWindow.
     */
    @FXML
    AnchorPane GameWindow;

    /**
     * The UI inventory.
     */
    @FXML
    AnchorPane UI_Inventory;

    /**
     * The Scene Label.
     */
    @FXML
    Text UI_SceneLabel;

    /**
     * The Items spawned.
     */
    ArrayList<Rectangle> itemsSpawned = new ArrayList<>();
    /**
     * The PlayerActionIdenticator Object.
     */
    PlayerActionIdenticator pai = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pubSub.addListener("fx_sceneChanged", (type, object) -> onSceneChange(object));
        pubSub.addListener("fx_notify", (type, object) -> {
            String raw = (String) object;
            String[] formatted = raw.split("#");

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
        Player player = new Player(this,(1400/2) - 40/2, (840/2) - 70/2, 40, 70, Color.BLUE);
        pai = new PlayerActionIdenticator(player);

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

            pai.hide();

            AppWindow.getChildren().add(1, player);
            AppWindow.getChildren().add(2, pai.getView());
        });
    }

    private void onSceneChange(Object payload) {
        Scene s = sceneController.getSceneBySlug((String) payload);

        setSceneLabel(s.getName());
        despawnItems();
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

    private void despawnItems() {
        Platform.runLater(() -> {
            for (Rectangle i : itemsSpawned) {
                GameWindow.getChildren().remove(i);
            }
        });
    }

    /**
     * Gets player action identicator.
     *
     * @return the player action identicator
     */
    public PlayerActionIdenticator getPlayerActionIdenticator() {
        return pai;
    }

    private void spawnItems(Scene s) {
        Platform.runLater(() -> {
            InventoryController inventoryController = InventoryController.getInstance();

            ArrayList<Item> items = itemController.getItemsByScene(sceneController.getCurrentScene());
            ArrayList<Item> itemsNotPickedUp = new ArrayList<Item>(
                    items.stream()
                            .filter(x -> !inventoryController.containsRoomItem(sceneController.getCurrentScene(), x))
                            .collect(Collectors.toList())
            );

            itemsSpawned.clear();
            for (Item item : itemsNotPickedUp) {

                SceneLocation pos = item.getSpawns().get(s);
                if(pos.getX() == 0 && pos.getY() == 0) continue;

                Rectangle box = new Rectangle();
                box.setX(pos.getX());
                box.setY(pos.getY());

                box.setWidth(100);
                box.setHeight(100);

                try {
                    InputStream is = Main.class.getResourceAsStream(item.getTexture());
                    Image img = new Image(is);

                    box.setFill(new ImagePattern(img));
                } catch (Exception e) {
                    box.setFill(Color.BLACK);
                }

                itemsSpawned.add(box);
                GameWindow.getChildren().add(box);
            }
        });
    }

    /**
     * Gets items spawned.
     *
     * @return the items spawned
     */
    public ArrayList<Rectangle> getItemsSpawned() {
        return itemsSpawned;
    }

    /**
     * Sets game window.
     *
     * @param scene the scene
     */
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
