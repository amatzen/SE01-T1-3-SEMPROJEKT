package dk.sdu.mmmi.swe20.t1.g3.Views;

import dk.sdu.mmmi.swe20.t1.g3.Services.Communicator;
import dk.sdu.mmmi.swe20.t1.g3.Types.Recipient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FXAppController {
    public FXAppController() {
        /*try {
            FXMLLoader f = new FXMLLoader(getClass().getResource("partials/inventory.fxml"));
            Node node = f.load();
            UI_Inventory.getChildren().setAll(node);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }*/

    }

    @FXML
    public AnchorPane GameWindow;

    @FXML
    public AnchorPane UI_Inventory;

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
