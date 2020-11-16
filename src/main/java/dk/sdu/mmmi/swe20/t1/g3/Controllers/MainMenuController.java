package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainMenuController extends FXGLMenu {
    public MainMenuController(MenuType menuType) {
        super(menuType);

    }

    @Override
    protected Button createActionButton(StringBinding name, Runnable action) {
        return new Button(name.get());
    }

    @Override
    protected Button createActionButton(String name, Runnable action) {
        return new Button(name);
    }

    @Override
    protected Node createBackground(double w, double h) {
        return new Rectangle(w, h, Color.GRAY);
    }

    @Override
    protected Node createProfileView(String profileName) {
        return new Text(profileName);
    }

    @Override
    protected Node createTitleView(String title) {
        return new Text(title);
    }

    @Override
    protected Node createVersionView(String version) {
        return new Text(version);
    }
}
