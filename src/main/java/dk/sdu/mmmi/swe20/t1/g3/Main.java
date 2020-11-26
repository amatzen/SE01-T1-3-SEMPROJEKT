package dk.sdu.mmmi.swe20.t1.g3;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.localization.Language;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Scenes;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {
    private Game g = new Game("start");
    private Scene currentScene;

    private final GameFactory gameFactory = new GameFactory();
    private Entity player, item;

    public enum EntityType {
        PLAYER, ITEM
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1400);
        settings.setHeight(900);
        settings.setTitle("World of Fish");

        List<Language> langs = new ArrayList<>();
        langs.add(Language.DANISH);
        langs.add(Language.ENGLISH);

        settings.setSupportedLanguages(langs);

        settings.setDeveloperMenuEnabled(true);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);

        // https://github.com/AlmasB/FXGL/issues/898
        settings.setDefaultLanguage(Language.DANISH);

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
    protected void initInput() {
        onKey(KeyCode.W, "Gå op", () -> player.translateY(-5));
        onKey(KeyCode.A, "Gå til venstre", () -> player.translateX(-5));
        onKey(KeyCode.S, "Gå ned", () -> player.translateY( 5));
        onKey(KeyCode.D, "Gå til højre", () -> player.translateX( 5));
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.BLACK);
        getGameWorld().addEntityFactory(gameFactory);

        currentScene = Scenes.getSceneBySlug("start");

        getGameScene().getViewport().setBounds(0,0, 1400, 900);
        getGameScene().getViewport().setWidth(1400);
        getGameScene().getViewport().setHeight(900);

        player = spawn("player", getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);
        item = spawn("item", 400, 400);

        //FXGL.setLevelFromMap("tmx/testigen-skov.tmx");
        g.play();
    }

    @Override
    protected void initUI() {
        super.initUI();
    }

    protected void setLevel(String levelName) {
        player.setAnchoredPosition(new Point2D(50, 50));
        FXGL.setLevelFromMap("tmx/"+levelName+".tmx");
    }

    public static void main(String[] args) {

        Scenes.loadScenes();
        Scenes.convertStringsToScenes();

        launch(args);
    }
}
