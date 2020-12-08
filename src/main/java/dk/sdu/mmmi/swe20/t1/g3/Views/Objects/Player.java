package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import dk.sdu.mmmi.swe20.t1.g3.Utilities.FXUtils;
import dk.sdu.mmmi.swe20.t1.g3.Views.FXAppController;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Sprite {
    private FXAppController fxAppController = null;
    private HashMap<String, Boolean> animations = new HashMap<>();

    private final double DEFAULT_MOVEMENT_FACTOR = 5;
    private final double DEFAULT_MOVEMENT_FACTOR_MODIFIER = 0.85;

    private double centerX, centerY = 0;

    public Player(FXAppController appController, int x, int y, int w, int h, Color color) {
        super(x, y, w, h, "player", color);
        fxAppController = appController;

        centerX = getBoundsInParent().getCenterX();
        centerY = getBoundsInParent().getCenterY();

        animations.put("w", false);
        animations.put("a", false);
        animations.put("s", false);
        animations.put("d", false);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkDistanceToItem();

                double MOVEMENT_FACTOR = DEFAULT_MOVEMENT_FACTOR;
                int dx = 0, dy = 0;

                if (animations.entrySet().stream().filter(Map.Entry::getValue).count() > 1) {
                    MOVEMENT_FACTOR *= DEFAULT_MOVEMENT_FACTOR_MODIFIER;
                }

                boolean
                    okUp, okDown, okLeft, okRight;

                okUp    = centerY - height/2  >= 0;
                okDown  = centerY + height  <= 900;

                okLeft  = centerX - width/2   >= 0;
                okRight = centerX + width  <= 1400;

                if (getAnimation("w") && okUp   ) dy -= MOVEMENT_FACTOR;
                if (getAnimation("s") && okDown ) dy += MOVEMENT_FACTOR;
                if (getAnimation("a") && okLeft ) dx -= MOVEMENT_FACTOR;
                if (getAnimation("d") && okRight) dx += MOVEMENT_FACTOR;

                setX(getX() + dx);
                setY(getY() + dy);

                centerX = getBoundsInParent().getCenterX();
                centerY = getBoundsInParent().getCenterY();
            }
        };
        timer.start();

    }

    private boolean getAnimation(String s) {
        return animations.get(s);
    }

    public void stopMovement() {
        for (KeyCode keyCode: new KeyCode[]{KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D}) {
            handleKeyRelease(keyCode);
        }
    }

    public void handleKeyPress(KeyCode keyCode) {
        switch (keyCode) {
            case W -> animations.put("w", true);
            case A -> animations.put("a", true);
            case S -> animations.put("s", true);
            case D -> animations.put("d", true);
        }
    }

    public void handleKeyRelease(KeyCode keyCode) {
        switch (keyCode) {
            case W -> animations.put("w", false);
            case A -> animations.put("a", false);
            case S -> animations.put("s", false);
            case D -> animations.put("d", false);
        }
    }

    private void checkDistanceToItem() {
        boolean closeToItem = false;

        ArrayList<Rectangle> itemsSpawned = fxAppController.getItemsSpawned();
        for ( Rectangle i : itemsSpawned ) {
            var x_1 = this.getBoundsInParent().getCenterX();
            var y_1 = this.getBoundsInParent().getCenterY();

            var x_2 = i.getBoundsInParent().getCenterX();
            var y_2 = i.getBoundsInParent().getCenterY();

            if ( new FXUtils().calculateDistanceBetweenPoints(x_1, y_1, x_2, y_2) <= 140 ) closeToItem = true;
        }

        if(closeToItem) {
            fxAppController.getPlayerActionIdenticator().setText("E");
            fxAppController.getPlayerActionIdenticator().show();
        } else {
            fxAppController.getPlayerActionIdenticator().hide();
        }
    }

    /*
    private boolean checkShapeIntersection(Shape shape) {
        boolean collisionDetected = false;
        for (Shape static_bloc : nodes) {
            if (static_bloc != shape) {
                static_bloc.setFill(Color.GREEN);

                Shape intersect = Shape.intersect(shape, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                }
            }
        }

        return collisionDetected;
    }

     */

}
