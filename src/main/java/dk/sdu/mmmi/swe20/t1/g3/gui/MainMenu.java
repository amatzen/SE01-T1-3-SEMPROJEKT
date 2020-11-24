package dk.sdu.mmmi.swe20.t1.g3.gui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import java.awt.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class MainMenu extends FXGLMenu {

    private ObjectProperty<GameButton> selectedButton;

    public MainMenu(MenuType menuType) {
        super(menuType);

        GameButton btnPlayGame = new GameButton
                ("Start spillet", "Start nyt spil", () -> fireNewGame());
        GameButton btnOptions = new GameButton
                ("Options", "Ændre spilindstillinger", () -> {});
        GameButton btnQuit = new GameButton
                ("Afslut spillet","Gå til skrivebord", () -> fireExit());

        selectedButton = new SimpleObjectProperty<>(btnPlayGame);

        var textDescription = getUIFactoryService().newText("");
        textDescription.textProperty().bind(
                Bindings.createStringBinding(() -> selectedButton.get().description, selectedButton));


        var box = new VBox(10,
                btnPlayGame,
                btnOptions,
                btnQuit,
                new Separator(Orientation.HORIZONTAL),
                textDescription);

        box.setAlignment(Pos.CENTER_LEFT);
        box.setTranslateX(100);
        box.setTranslateY(500);

        getContentRoot().getChildren().addAll(box);
    }

    @Override
    protected Button createActionButton(StringBinding stringBinding, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Button createActionButton(String s, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Node createBackground(double w, double h) {
        return FXGL.texture("mainmenu.png");
    }

    @Override
    protected Node createProfileView(String s) {
        return new Rectangle();
    }

    @Override
    protected Node createTitleView(String s) {
        return new Rectangle();
    }

    @Override
    protected Node createVersionView(String s) {
        return new Rectangle();
    }

    private static final Color SELECTED_COLOR = Color.WHITE;
    private static final Color NOT_SELECTED_COLOR = Color.GREY;

    private class GameButton extends StackPane {
        private String name;
        private String description;
        private Runnable action;


        private Text text;
        private Rectangle selector;

        public GameButton(String name, String description, Runnable action) {
            this.name = name;
            this.description = description;
            this.action = action;

            text = getUIFactoryService().newText(name, Color.WHITE, 36.0);
            text.fillProperty().bind
                    (Bindings.when(focusedProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED_COLOR));
            text.strokeProperty().bind
                    (Bindings.when(focusedProperty()).then(SELECTED_COLOR).otherwise(NOT_SELECTED_COLOR));
            text.setStrokeWidth(0.5);

            selector = new Rectangle(8, 36, Color.ORANGE);
            selector.setTranslateX(-20);
            selector.setTranslateY(-2);
            selector.visibleProperty().bind(focusedProperty());

            focusedProperty().addListener((Observable, oldValue, isSelected) -> {
                if (isSelected) {
                    selectedButton.setValue(this);
                };
            });

            setAlignment(Pos.CENTER_LEFT);
            setFocusTraversable(true);

            setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    action.run();
                }
            });

            getChildren().addAll(selector, text);
        }
    }
}



