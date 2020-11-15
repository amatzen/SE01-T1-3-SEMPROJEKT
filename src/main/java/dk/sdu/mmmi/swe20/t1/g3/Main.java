package dk.sdu.mmmi.swe20.t1.g3;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Scenes;

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
