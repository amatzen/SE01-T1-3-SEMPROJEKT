package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import dk.sdu.mmmi.swe20.t1.g3.Config.Scenes;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class SceneController {
    // Singleton Pattern Declaration
    public static SceneController instance = null;
    public static SceneController getInstance() {
        if (instance == null) instance = new SceneController();
        return instance;
    }
    // End Singleton Pattern Declaration

    private ArrayList<Scene> scenes = new ArrayList<>();
    private Scene currentScene;

    private SceneController() {
        for(Scenes scene : Scenes.values()) {
            scenes.add(new Scene(scene.getSlug(), scene.getName(), scene.getDescription(), scene.getExitsString()));
        }

        this.convertStringsToScenes();
    };

    /*
     * Convert foreign key strings to objects
     */
    private void convertStringsToScenes() {
        scenes.forEach(scene -> {
            HashMap<String, String> data = scene.getExitsString();

            HashMap<String, Scene> sce = new HashMap<String, Scene>(
                    data.entrySet().stream()
                            .filter(s -> s.getKey() != null & s.getValue() != null)
                            .collect(Collectors.toMap(e->e.getKey(), e->getSceneBySlug(e.getValue())))
            );

            scene.setExits(sce);

        });
    }

    /*
     * Get a specific scene by its slug
     */
    public Scene getSceneBySlug(String slug) throws NullPointerException {
        // https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
        // https://www.codegrepper.com/code-examples/delphi/java+list+find+object+by+property

        return scenes.stream()
                .filter(scene -> slug.equals(scene.getSlug()))
                .findFirst()
                .orElse(null);
    }

    public void goToScene(Scene scene) throws Exception {
        if( !scenes.contains(scene) ) {
            throw new Exception("Scene not found");
        }

        currentScene = scene;
    }

    public void goToScene(String slug) throws NullPointerException {
        currentScene = this.getSceneBySlug(slug);

        // TODO: Implement JFX here
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
