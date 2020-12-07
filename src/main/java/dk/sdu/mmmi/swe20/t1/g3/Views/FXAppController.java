package dk.sdu.mmmi.swe20.t1.g3.Views;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.FXUtils;
import dk.sdu.mmmi.swe20.t1.g3.Views.Objects.Player;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXAppController implements Initializable {
    SceneController sceneController = SceneController.getInstance();
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
        setSceneLabel(sceneController.getCurrentScene().getName());

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
        setSceneLabel(sceneController.getSceneBySlug((String) payload).getName());

        // TODO: Lav ItemSpawner, evt. i Views/ItemFactory
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

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

}
