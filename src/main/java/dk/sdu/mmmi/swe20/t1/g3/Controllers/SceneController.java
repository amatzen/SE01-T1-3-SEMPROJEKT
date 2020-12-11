package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import dk.sdu.mmmi.swe20.t1.g3.Config.Scenes;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import io.github.techrobby.SimplePubSub.PubSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * The type Scene controller.
 */
public class SceneController {
    /**
     * The constant instance.
     */
// Singleton Pattern Declaration
    public static SceneController instance = null;
    private final ArrayList<Scene> scenes = new ArrayList<>();
    private final PubSub pubSub = PubSub.getInstance();
    // End Singleton Pattern Declaration
    private Scene currentScene;

    private SceneController() {
        for (Scenes scene : Scenes.values()) {
            scenes.add(new Scene(scene.getSlug(), scene.getName(), scene.getDescription(), scene.getSceneURL(), scene.getExitsString()));
        }

        this.convertStringsToScenes();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SceneController getInstance() {
        if (instance == null) instance = new SceneController();
        return instance;
    }

    /*
     * Convert foreign key strings to objects
     */
    private void convertStringsToScenes() {
        scenes.forEach(scene -> {
            HashMap<String, String> data = scene.getExitsString();

            HashMap<String, Scene> sce = new HashMap<String, Scene>(
                    data.entrySet().stream()
                            .filter(s -> s.getKey() != null & s.getValue() != null)
                            .collect(Collectors.toMap(e -> e.getKey(), e -> getSceneBySlug(e.getValue())))
            );

            scene.setExits(sce);

        });
    }

    /**
     * Gets scene by slug.
     *
     * @param slug the slug
     * @return the scene by slug
     * @throws NullPointerException the null pointer exception
     */
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

    /**
     * Go to scene.
     *
     * @param scene the scene
     * @throws Exception the exception
     */
    public void goToScene(Scene scene) throws Exception {
        if (!scenes.contains(scene)) {
            throw new Exception("Scene not found");
        }

        pubSub.publish("fx_sceneChanged", scene.getSlug());
        currentScene = scene;
    }

    /**
     * Go to scene.
     *
     * @param slug the slug
     * @throws NullPointerException the null pointer exception
     */
    public void goToScene(String slug) throws NullPointerException {
        currentScene = this.getSceneBySlug(slug);
    }

    /**
     * Gets current scene.
     *
     * @return the current scene
     */
    public Scene getCurrentScene() {
        return currentScene;
    }
}
