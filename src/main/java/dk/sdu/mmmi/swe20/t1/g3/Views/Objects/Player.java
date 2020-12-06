package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class Player extends Sprite {

    private HashMap<String, Boolean> animations = new HashMap<>();

    private final double DEFAULT_MOVEMENT_FACTOR = 5;
    private final double DEFAULT_MOVEMENT_FACTOR_MODIFIER = 0.85;

    public Player(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, "player", color);

        animations.put("w", false);
        animations.put("a", false);
        animations.put("s", false);
        animations.put("d", false);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double MOVEMENT_FACTOR = DEFAULT_MOVEMENT_FACTOR;
                int dx = 0, dy = 0;

                if (animations.entrySet().stream().filter(Map.Entry::getValue).count() > 1) {
                    MOVEMENT_FACTOR *= DEFAULT_MOVEMENT_FACTOR_MODIFIER;
                }

                if (getAnimation("w")) dy -= MOVEMENT_FACTOR;
                if (getAnimation("s")) dy += MOVEMENT_FACTOR;
                if (getAnimation("a")) dx -= MOVEMENT_FACTOR;
                if (getAnimation("d")) dx += MOVEMENT_FACTOR;

                setX(getX() + dx);
                setY(getY() + dy);
            }
        };
        timer.start();
    }

    private boolean getAnimation(String s) {
        return animations.get(s);
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

}
