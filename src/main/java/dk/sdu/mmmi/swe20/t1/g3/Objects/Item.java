package dk.sdu.mmmi.swe20.t1.g3.Objects;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Item {
    private String slug, name, texture;
    private ItemType itemType;
    private HashMap<Scene, SceneLocation> spawns = new HashMap<Scene, SceneLocation>();

    public Item(String slug, String name, String texture, ItemType itemType, HashMap<String, SceneLocation> spawns) {
        this.slug = slug;
        this.name = name;
        this.texture = texture;
        this.itemType = itemType;

        SceneController sceneController = SceneController.getInstance();

        this.spawns = new HashMap<Scene, SceneLocation>(
            spawns.entrySet().stream()
                .filter(s -> s.getKey() != null & s.getValue() != null)
                .collect(Collectors.toMap(
                        e->sceneController.getSceneBySlug(e.getKey()), e->e.getValue()
                ))
        );
    }

    public void setSpawns(HashMap<Scene, SceneLocation> spawns) {
        this.spawns = spawns;
    }

    public HashMap<Scene, SceneLocation> getSpawns() {
        return spawns;
    }

    public String getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public ItemType getItemType() {
        return itemType;
    }
}