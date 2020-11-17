package dk.sdu.mmmi.swe20.t1.g3;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.MainMenuController;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Scenes;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Utils;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class Main extends GameApplication {
    /*
    public static void main(String[] args) {
        // Load Scenes
        Scenes.loadScenes();

        // Convert
        Scenes.convertStringsToScenes();

        welcome();

        Game g = new Game("start");
        g.play();

    }

    public static void welcome() {
        System.out.println("");
        System.out.println("🐟 Welcome to World of Fish");
    }
     */

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1400);
        settings.setHeight(900);
        settings.setTitle("World of Fish");

        settings.setMenuEnabled(true);

        if(Utils.isIntelliJ()) {
            settings.setDeveloperMenuEnabled(true);
        }

        /*
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenuController(MenuType.MAIN_MENU);
            }
        });
         */
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.BLACK);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
