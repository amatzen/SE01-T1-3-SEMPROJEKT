package dk.sdu.mmmi.swe20.t1.g3.Views;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class FXAppController {
    SceneController sceneController = SceneController.getInstance();
    PubSub pubSub = PubSub.getInstance();

    private String currentSceneName = "";

    @FXML
    public AnchorPane GameWindow;

    @FXML
    public AnchorPane UI_Inventory;

    @FXML
    public Text UI_SceneLabel;

    @FXML
    public void initialize() {
        /*try {
            FXMLLoader f = new FXMLLoader(getClass().getResource("partials/inventory.fxml"));
            Node node = f.load();
            UI_Inventory.getChildren().setAll(node);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }*/

        pubSub.addListener("fx_sceneChanged", (type, object) -> {
            String sceneSlug = (String) object;
            Scene currentScene = sceneController.getSceneBySlug(sceneSlug);
            setSceneLabel(currentScene.getName());
        });

        currentSceneName = sceneController.getCurrentScene().getName();
        setSceneLabel(currentSceneName);

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
}
