package dk.sdu.mmmi.swe20.t1.g3;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.localization.Language;
import com.almasb.fxgl.dev.DevService;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.MainMenuController;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Utils;
import dk.sdu.mmmi.swe20.t1.g3.gui.MainMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

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

        List<Language> langs = new ArrayList<>();
        langs.add(Language.DANISH);
        langs.add(Language.ENGLISH);

        settings.setSupportedLanguages(langs);

        settings.setMainMenuEnabled(true);

        settings.setDeveloperMenuEnabled(true);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);

        // https://github.com/AlmasB/FXGL/issues/898
        settings.setDefaultLanguage(Language.DANISH);

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu(MenuType.MAIN_MENU);
            }
        });
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.WHITE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
