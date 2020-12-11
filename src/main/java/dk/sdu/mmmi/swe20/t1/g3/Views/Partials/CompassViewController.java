package dk.sdu.mmmi.swe20.t1.g3.Views.Partials;

import dk.sdu.mmmi.swe20.t1.g3.Config.Direction;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class CompassViewController implements Initializable {

    @FXML
    SVGPath Arrow_Left;

    @FXML
    SVGPath Arrow_Right;

    @FXML
    SVGPath Arrow_Up;

    @FXML
    SVGPath Arrow_Down;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Arrow_Down  .setFill(Color.TRANSPARENT);
        Arrow_Left  .setFill(Color.TRANSPARENT);
        Arrow_Right .setFill(Color.TRANSPARENT);
        Arrow_Up    .setFill(Color.TRANSPARENT);

        PubSub.getInstance().addListener("fx_sceneChanged", (type, object) -> {
            Scene s = SceneController.getInstance().getSceneBySlug((String) object);

            for (Direction d : Direction.values()) {
                if(s.getExit(d.getDirectionString()) == null) {
                    setArrow(d, false);
                } else {
                    setArrow(d, true);
                }
            }

        });
    }

    public void setArrow(Direction dir, boolean show) {
        SVGPath modifier = null;

        switch (dir) {
            case UP     -> modifier  = Arrow_Up;
            case DOWN   -> modifier  = Arrow_Down;
            case LEFT   -> modifier  = Arrow_Left;
            case RIGHT  -> modifier  = Arrow_Right;
        }

        if (modifier == null) return;

        if(show) {
            modifier.setFill(Color.WHITE);
            modifier.setStroke(Color.GRAY);
        } else {
            modifier.setFill(Color.TRANSPARENT);
            modifier.setStroke(Color.TRANSPARENT);
        }
    }
}
