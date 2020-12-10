package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import dk.sdu.mmmi.swe20.t1.g3.Main;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.FXUtils;
import dk.sdu.mmmi.swe20.t1.g3.Views.FXAppController;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Sprite {
    private FXAppController fxAppController = null;
    private HashMap<String, Boolean> animations = new HashMap<>();

    private final double DEFAULT_MOVEMENT_FACTOR = 5;
    private final double DEFAULT_MOVEMENT_FACTOR_MODIFIER = 0.85;

    // State
    private double centerX, centerY = 0;
    private boolean isWalking = false;
    private boolean isWalkingLeft = false;
    private int spriteIncrement = 0;

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
                double MOVEMENT_FACTOR = DEFAULT_MOVEMENT_FACTOR;
                int dx = 0, dy = 0;

                if (animations.entrySet().stream().filter(Map.Entry::getValue).count() > 1) {
                    MOVEMENT_FACTOR *= DEFAULT_MOVEMENT_FACTOR_MODIFIER;
                }

                if( !getAnimation("w") && !getAnimation("a") && !getAnimation("s") && !getAnimation("d") ) {
                    isWalking = false;
                    return;
                }

                isWalking = true;

                boolean
                    okUp, okDown, okLeft, okRight;

                okUp    = centerY - getHeight()/2  >= 0;
                okDown  = centerY + getHeight()  <= 900;

                okLeft  = centerX - getWidth()/2   >= 0;
                okRight = centerX + getWidth()  <= 1400;

                if (getAnimation("w") && okUp   ) dy -= MOVEMENT_FACTOR;
                if (getAnimation("s") && okDown ) dy += MOVEMENT_FACTOR;
                if (getAnimation("a") && okLeft ) { dx -= MOVEMENT_FACTOR; isWalkingLeft = true; };
                if (getAnimation("d") && okRight) { dx += MOVEMENT_FACTOR; isWalkingLeft = false; };

                setX(getX() + dx);
                setY(getY() + dy);

                centerX = getBoundsInParent().getCenterX();
                centerY = getBoundsInParent().getCenterY();
            }
        };
        timer.start();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            spriteIncrement++;
            if (spriteIncrement > 5) spriteIncrement = 0;
            setPlayerSprite(String.format("Views/Assets/Player/%s/%s.png", isWalking ? "Walking" : "Idling", spriteIncrement));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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

    private void setPlayerSprite(String u) {
        if(isWalkingLeft) {
            setScaleX(-1);
        } else {
            setScaleX(1);
        }

        try {
            InputStream is = Main.class.getResourceAsStream(u);
            setFill(new ImagePattern(new Image(is)));
        } catch (Exception e) {
            setFill(Color.BLACK);
        }

    }

}
