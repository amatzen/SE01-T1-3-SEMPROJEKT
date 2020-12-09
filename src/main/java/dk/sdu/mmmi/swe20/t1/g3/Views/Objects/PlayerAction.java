package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import dk.sdu.mmmi.swe20.t1.g3.Config.Direction;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemAction;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.FXUtils;
import dk.sdu.mmmi.swe20.t1.g3.Views.FXAppController;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerAction {
    private final PubSub pubSub = PubSub.getInstance();

    private final FXAppController appController;
    private final PlayerActionIdenticator pai;
    private final Player player;

    private Item closeToItem;
    private Direction closeToDir;

    private HashMap<String, Boolean> ct = new HashMap<>();

    public PlayerAction(FXAppController appController, PlayerActionIdenticator pai, Player player) {
        this.appController = appController;
        this.pai = pai;
        this.player = player;

        animationTimer();
    }

    public void handleInteraction() {
        if( closeToItem == null ) return;

        if( closeToItem.getItemAction().equals( ItemAction.PICKUPABLE )) {
            pubSub.publish("executeCommand", "pickup " + closeToItem.getSlug());
        } else if ( closeToItem.getItemAction().equals( ItemAction.INTERACTABLE ) ) {
            pubSub.publish("executeCommand", "interact " + closeToItem.getSlug());
        }
    }

    public void handleSceneSwitch() {
        SceneController sceneController = SceneController.getInstance();

        if ( closeToDir == null ) return;
        try {
            pubSub.publish("executeCommand", String.format("go %s", closeToDir.getDirectionString()));

            // Move player
            double  curX = player.getX(),
                    curY = player.getY();

            double  newX = curX*(-1),
                    newY = curY*(-1);

            if(closeToDir == Direction.LEFT || closeToDir == Direction.RIGHT)
                player.setX(newX);
            else
                player.setY(newY);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public PlayerActionIdenticator getPai() {
        return pai;
    }

    private void checkDistanceToEdge() {
        final SceneController sceneController = SceneController.getInstance();

        boolean closeTo = false;
        closeToDir = null;

        double x_1 = player.getBoundsInParent().getCenterX();
        double y_1 = player.getBoundsInParent().getCenterY();


        // top
        if ( new FXUtils().calculateDistanceBetweenPointAndLine(new Point2D(x_1,y_1), 0, 1, 0) <= 100       && !closeTo
                && sceneController.getCurrentScene().getExit((Direction.UP).getDirectionString()) != null ) {
            closeTo = true;
            closeToDir = Direction.UP;
        }

        // bottom
        if ( new FXUtils().calculateDistanceBetweenPointAndLine(new Point2D(x_1,y_1), 0, 1, -900) <= 150    && !closeTo
                && sceneController.getCurrentScene().getExit((Direction.DOWN).getDirectionString()) != null ) {
            closeTo = true;
            closeToDir = Direction.DOWN;
        }

        // left
        if ( new FXUtils().calculateDistanceBetweenPointAndLine(new Point2D(x_1,y_1), 2, 0, 0) <= 100       && !closeTo
                && sceneController.getCurrentScene().getExit((Direction.LEFT).getDirectionString()) != null ) {
            closeTo = true;
            closeToDir = Direction.LEFT;
        }

        // right
        if ( new FXUtils().calculateDistanceBetweenPointAndLine(new Point2D(x_1,y_1), 2, 0, -2800) <= 100   && !closeTo
                && sceneController.getCurrentScene().getExit((Direction.RIGHT).getDirectionString()) != null ) {
            closeTo = true;
            closeToDir = Direction.RIGHT;
        }

        distanceDistanceToEdgePai(closeTo);

    }

    private void distanceDistanceToEdgePai(Boolean closeTo) {
        if(closeTo) {
            getPai().setText("X");
            ct.put("distToEdge", true);
        } else {
            ct.put("distToEdge", false);
        }
    }

    private void checkDistanceToItem() {
        boolean closeTo = false;
        closeToItem = null;

        ArrayList<Rectangle> itemsSpawned = appController.getItemsSpawned();
        for ( Rectangle i : itemsSpawned ) {
            double x_1 = player.getBoundsInParent().getCenterX();
            double y_1 = player.getBoundsInParent().getCenterY();

            double x_2 = i.getBoundsInParent().getCenterX();
            double y_2 = i.getBoundsInParent().getCenterY();

            if ( new FXUtils().calculateDistanceBetweenPoints(x_1, y_1, x_2, y_2) <= 140 ) {
                closeTo = true;
                int a = itemsSpawned.indexOf(i);
                Item item = appController.getItemsSpawnedObj().get(a);
                closeToItem = item;
            }
        }

        if(closeTo) {
            getPai().setText("E");
            ct.put("distToItem", true);
        } else {
            ct.put("distToItem", false);
        }
    }

    private void setIdenticator() {
        for (Map.Entry<String, Boolean> entry : ct.entrySet()) {
            if(entry.getValue()) {
                getPai().show();
                return;
            }
        }

        getPai().hide();
    }

    private void animationTimer() {
        AnimationTimer t = new AnimationTimer() {
            @Override
            public void handle(long l) {
                checkDistanceToItem();
                checkDistanceToEdge();

                setIdenticator();
            }
        };
        t.start();
    }
}
