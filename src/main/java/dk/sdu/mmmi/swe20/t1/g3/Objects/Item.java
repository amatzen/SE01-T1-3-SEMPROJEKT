package dk.sdu.mmmi.swe20.t1.g3.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static dk.sdu.mmmi.swe20.t1.g3.Utilities.Scenes.getSceneBySlug;

class SceneLocation {
    Long x;
    Long y;

    public SceneLocation(Long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }
}

public class Item {
    private static ArrayList<Item> items = new ArrayList<Item>();

    String slug;
    String name;
    String img;
    HashMap<Scene, SceneLocation> spawns = new HashMap<Scene, SceneLocation>();
    HashMap<String, SceneLocation> spawnsString = new HashMap<String, SceneLocation>();

    public Item(String slug, String name, String img) {
        this.slug = slug;
        this.name = name;
        this.img = img;

        Item.items.add(this);
    }
    public Item(String slug, String name, String img, HashMap<String, SceneLocation> locations) {
        this.slug = slug;
        this.name = name;
        this.img = img;

        Item.items.add(this);
    }

    public void setSpawns(HashMap<Scene, SceneLocation> spawns) {
        this.spawns = spawns;
    }

    public String getImage() {
        return img;
    }

    public static void convertStringsToItems () {
        items.forEach(item -> {
            HashMap<String, SceneLocation> data = item.spawnsString;

            HashMap<Scene, SceneLocation> newSpawns = new HashMap<Scene, SceneLocation>(
                    data.entrySet().stream()
                            .filter(s -> s.getKey() != null & s.getValue() != null)
                            .collect(
                                    Collectors.toMap(e->getSceneBySlug(e.getKey()), e->e.getValue())
                            )
            );

            item.setSpawns(newSpawns);

        });
    }
}