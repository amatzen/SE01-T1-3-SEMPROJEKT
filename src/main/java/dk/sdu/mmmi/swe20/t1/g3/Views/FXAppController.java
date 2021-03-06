package dk.sdu.mmmi.swe20.t1.g3.Views;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Main;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;
import dk.sdu.mmmi.swe20.t1.g3.Views.Objects.Player;
import dk.sdu.mmmi.swe20.t1.g3.Views.Objects.PlayerAction;
import dk.sdu.mmmi.swe20.t1.g3.Views.Objects.PlayerActionIdenticator;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
    ArrayList<Item> itemsSpawnedObj = new ArrayList<>();
    ArrayList<Rectangle> itemsSpawned = new ArrayList<>();

    /**
     * The PlayerAction Object.
     */
    PlayerAction pa = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pubSub.addListener("fx_sceneChanged", (type, object) -> onSceneChange(object));
        pubSub.addListener("fx_respawnItems", (type, object) -> {
            despawnItems();
            spawnItems(sceneController.getCurrentScene());
        });
        pubSub.addListener("fx_notify", (type, object) -> {
            String raw = (String) object;
            String[] formatted = raw.split("#");

            Platform.runLater(() -> {
                try {
                    Notifications.create()
                            .darkStyle()
                            .title(formatted[0])
                            .text(formatted[1])
                            .hideAfter(Duration.seconds(5))
                            .show();
                } catch (Exception e) {
                    // Nothing
                }
            });
        });

        pubSub.addListener("gameFinished", ((type, object) -> {
            Platform.runLater(() -> {
                try {
                    Rectangle box = new Rectangle(1400, 900);

                    InputStream is = Main.class.getResourceAsStream("Views/Assets/EndScene.png");
                    Image img = new Image(is);

                    box.setFill(new ImagePattern(img));

                    AppWindow.getChildren().add(box);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }));

        pubSub.publish("fx_sceneChanged", sceneController.getCurrentScene().getSlug());
        spawnPlayer();
    }

    private void spawnPlayer() {
        Player player = new Player(this, (1400 / 2) - 40 / 2, (840 / 2) - 70 / 2, 55, 80, Color.BLUE);
        pa = new PlayerAction(this, new PlayerActionIdenticator(player), player);

        Platform.runLater(() -> {
            AppWindow.getScene().setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case W, A, S, D -> player.handleKeyPress(e.getCode());
                    case UP, LEFT, DOWN, RIGHT -> handleSceneKeyPress(e.getCode());

                    case E -> {
                        player.stopMovement();
                        pa.handleInteraction();
                    }

                    case X -> {
                        player.stopMovement();
                        pa.handleSceneSwitch();
                    }
                }
            });

            AppWindow.getScene().setOnKeyReleased(e -> {
                switch (e.getCode()) {
                    case W, A, S, D -> player.handleKeyRelease(e.getCode());
                }
            });

            AppWindow.getChildren().add(1, player);
            AppWindow.getChildren().add(2, pa.getPai().getView());
        });
    }

    private void onSceneChange(Object payload) {
        Scene s = sceneController.getSceneBySlug((String) payload);

        Platform.runLater(() -> {
            // Set background
            if (s.getSceneURL() != "") {
                GameWindow.setStyle("-fx-background-color: #fff;");

                Rectangle backdrop = new Rectangle(1400, 900);

                try {
                    InputStream is = Main.class.getResourceAsStream(s.getSceneURL());
                    Image img = new Image(is);

                    backdrop.setFill(new ImagePattern(img));
                } catch (Exception e) {
                    backdrop.setFill(Color.BLACK);
                }

                if (GameWindow.getChildren().size() > 0)
                    GameWindow.getChildren().remove(0);

                GameWindow.getChildren().add(backdrop);
            }
        });

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

    /**
     * Gets items spawned.
     *
     * @return the items spawned
     */
    public ArrayList<Item> getItemsSpawnedObj() {
        return itemsSpawnedObj;
    }

    public ArrayList<Rectangle> getItemsSpawned() {
        return itemsSpawned;
    }

    /*
    #
    # Private
    #
    */


    @FXML
    private void setSceneLabel(String sceneName) {
        UI_SceneLabel.setText(sceneName);
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

            itemsSpawnedObj.clear();
            itemsSpawned.clear();
            for (Item item : itemsNotPickedUp) {

                SceneLocation pos = item.getSpawns().get(s);
                if (pos.getX() == 0 && pos.getY() == 0) continue;

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

                itemsSpawnedObj.add(item);
                itemsSpawned.add(box);

                GameWindow.getChildren().add(box);
            }
        });
    }

    private void despawnItems() {
        Platform.runLater(() -> {
            for (Rectangle i : itemsSpawned) {
                GameWindow.getChildren().remove(i);
            }
        });
    }


}
