
package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;

public class Sprite extends Rectangle {
    boolean dead = false;
    final String type;

    public final double width, height;

    Sprite(int x, int y, int w, int h, String type, Color color) {
        super(w, h, color);

        this.width = w;
        this.height = h;

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    private final BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32:
    public int w:
    public int h:
    private int wSprite;
    private int hSprite;

    public Sprite(String file) {
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("loading: " + file "...");
        SPRITESHEET = loadSprite(file);
    }

    public Sprite(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("loading: " + file "...");
        SPRITESHEET = loadSprite(file);



    }



}
