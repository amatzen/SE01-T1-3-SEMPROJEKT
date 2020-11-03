package dk.sdu.mmmi.swe20.t1.g3.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static dk.sdu.mmmi.swe20.t1.g3.Utils.isIntelliJ;

public class Scenes {
    public static ArrayList<Scene> scenes = new ArrayList<Scene>();

    public void loadScenes() {
        createScene(
                "start",
                "Velkommen til World of Fish!",
                Map.of(
                        "venstre", "strand"
                )
        );
        createScene(
                "strand",
                "Velkommen til Stranden!",
                Map.of("Tilbage", "start")
        );
    }

    public void convertStringsToScenes() {
        scenes.forEach(scene -> {
            HashMap<String, String> data = scene.getExitsString();

            HashMap<String, Scene> sce = new HashMap<String, Scene>(
                    data.entrySet().stream()
                        .filter(s -> s.getKey() != null & s.getValue() != null)
                        .collect(Collectors.toMap(e->e.getKey(), e->getSceneBySlug(e.getValue())))
                    );

            scene.setExits(sce);

        });

        if(isIntelliJ()) {
            // Log to debugger
            scenes.forEach(scene -> {});
        }
    }

    /*
     * Constructor without Tasks
     *
     * Slug is required, a unique string identifier
     * Description is required, which will be public for the end user
     * Then exits as a Key-Value Map. The KV-Map is formatted as { ExitDescription<String>, ExitSlug<String> }
     */
    void createScene(String slug, String description, Map<String, String> exitsString) {
        scenes.add(new Scene(slug, description, exitsString));
    }

    /*
     * Constructor with Tasks
     *
     * Slug is required, a unique string identifier
     * Description is required, which will be public for the end user
     * Then exits as a Key-Value Map. The KV-Map is formatted as { ExitDescription<String>, ExitSlug<String> }
     *
     * Then options as an ArrayList. The ArrayList is formatted as [TaskSlug<String>...]
     */
    void createScene(String slug, String description, Map<String, String> exitsString, ArrayList<String> tasks) {
        scenes.add(new Scene(slug, description, exitsString, tasks));
    }

    /*
     * Method to return all scenes
     */
    public static ArrayList<Scene> getScenes() {
        return scenes;
    }

    /*
     * Method to return the Scene as Object from the Slug String
     */
    public static Scene getSceneBySlug(String slug) {
        // https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
        // https://www.codegrepper.com/code-examples/delphi/java+list+find+object+by+property

        return scenes.stream()
                .filter(scene -> slug.equals(scene.getSlug()))
                .findFirst()
                .orElse(null);
    }
}
