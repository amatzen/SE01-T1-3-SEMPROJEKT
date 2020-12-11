package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
    public final double width, height;
    final String type;
    boolean dead = false;

    Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);

        this.width = w;
        this.height = h;

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }
}
