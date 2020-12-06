package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    boolean dead = false;
    final String type;

    Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    private void moveX(double v) {
        setTranslateX(getTranslateX() + v);
    }
    private void moveY(double v) {
        setTranslateY(getTranslateY() + v);
    }

    public void moveLeft() {
        moveX(-5);
    }

    public void moveRight() { moveX(5); }

    public void moveUp() {
        moveY(-5);
    }

    public void moveDown() {
        moveY(5);
    }
    
}
