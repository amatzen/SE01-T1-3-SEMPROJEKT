package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import javafx.animation.Animation;


import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.image.BufferedImage;

//extends x
public abstract class Enitity {

    private final int UP = 0;
    private final int DOWN = 1;
    private final int RIGHT = 2;
    private final int LEFT = 3;
    protected int currentAnimation;

    private Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;


    float dx;
    float dy;

    protected float maxSpeed;
    protected float acc;
    protected float deacc;




    public Enitity(Sprite sprite, Vector2f origin, int size) {
        this. sprite = sprite;
        pos = origin;
        this.size = size;

        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(right), 10);
    }

    public void setAnimation(int i, BufferedImage[], frame, int delay) {
        ani.setFrames(frames);
        ani.setDelay(delay);

    }

    public void animate() {
        if (up) {
            if (currentAnimation != up || ani.getDelay() == -1) {
                setAnimation(up, sprite.getSpriteArray(up), 5);
            }
        }

        else if(down) {
            if(currentAnimation != down || ani.getDelay() == -1) {
                setAnimation(down, sprite.getSpriteArray(down), 5);
            }
        }
        else if(right) {
            if(currentAnimation != right || ani.getDelay() == -1) {
                setAnimation(right, sprite.getSpriteArray(right), 5);
            }
        }
        else if (left) {
            if(currentAnimation != left || ani.getDelay() == -1) {
                setAnimation(left, sprite.getSpriteArray(left), 5);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation));
        }
    }

    public void update() {
        animate();
        setHitBoxDirection();
        ani.update();
    }

    public abstract void render(Graphic2D g);
    public void input(KeyHandler key, Mousehandler mouse)
}

