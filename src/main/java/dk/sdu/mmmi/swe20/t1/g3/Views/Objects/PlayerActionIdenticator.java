package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PlayerActionIdenticator {
    private double x, y;
    private boolean shown = false;
    private String text = "";

    private StackPane view;

    public PlayerActionIdenticator(Player p) {
        x = p.getBoundsInParent().getMaxX() + 10;
        y = p.getBoundsInParent().getMinY() - 10;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                x = p.getBoundsInParent().getMaxX() + 10;
                y = p.getBoundsInParent().getMinY() - 10;

                view.setTranslateX(x);
                view.setTranslateY(y);
            }
        };
        timer.start();

        view = new StackPane();
        view.getChildren().add(new Rectangle(20, 20, Color.DARKTURQUOISE));
        view.getChildren().add(new Text(""));

        view.setAlignment(Pos.CENTER);

        view.setVisible(false);
    }

    public void show() {
        shown = true;
        ((Text) view.getChildren().get(1)).setText(text);
        view.setVisible(true);
    }

    public void hide() {
        shown = false;
        view.setVisible(false);
    }

    public void setText(String text) {
        this.text = text;
    }

    public StackPane getView() {
        return view;
    }
}
