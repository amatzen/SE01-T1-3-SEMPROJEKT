package dk.sdu.mmmi.swe20.t1.g3.cli;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Scenes {
    public static ArrayList<Scene> scenes = new ArrayList<Scene>();

    public void loadScenes() {
        createScene("start", "Velkommen til World of Fish!", Map.of("Stranden", "strand"));
        createScene("strand", "Velkommen til Stranden!", Map.of("Tilbage", "start"));
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

        scenes.forEach(scene -> {});
    }

    void createScene(String slug, String description, Map<String, String> exitsString) {
        scenes.add(new Scene(slug, description, exitsString));
    }

    void createScene(String slug, String description, Map<String, String> exitsString, ArrayList<String> options) {
        scenes.add(new Scene(slug, description, exitsString, options));
    }

    public Scene getSceneBySlug(String slug) {
        // https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html
        // https://www.codegrepper.com/code-examples/delphi/java+list+find+object+by+property

        return scenes.stream()
                .filter(scene -> slug.equals(scene.getSlug()))
                .findFirst()
                .orElse(null);
    }
}
