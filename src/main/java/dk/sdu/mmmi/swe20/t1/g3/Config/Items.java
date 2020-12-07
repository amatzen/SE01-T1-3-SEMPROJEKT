package dk.sdu.mmmi.swe20.t1.g3.Config;

import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;
import javafx.scene.shape.Box;

import java.util.HashMap;
import java.util.Map;

public enum Items {
    SKRALD(
        "skrald",
        "Skrald",
        "Views/Items/SKRALD.png",
        ItemType.TRASH,
        Map.of(
            "start", new SceneLocation(40,50),
            "skov", new SceneLocation(0,0)
        )
    ),
    TANG(
        "tang",
        "Tang",
        "Views/Items/TANG.png",
        ItemType.BIO,
        Map.of(
            "strand", new SceneLocation(0,0),
            "hav", new SceneLocation(0, 0),
            "skibsvrag", new SceneLocation(0, 0)
        )
    ),
    PLASTIKFLASKE(
            "plastikflaske",
            "Plastikflaske",
            "",
            ItemType.PLASTIC,
            Map.of(
                    "skov", new SceneLocation(0, 0),
                    "havbund", new SceneLocation(0, 0)
            )
    ),
    PLASTBUNKE(
            "plastbunke",
            "Bunke af plastik",
            "",
            ItemType.PLASTIC,
            Map.of(
                    "hav", new SceneLocation(0, 0),
                    "koralrev", new SceneLocation(0, 0)
            )
    );

    private String slug, name, texture;
    private ItemType itemType;
    private HashMap<String, SceneLocation> spawns;
    Items(String slug, String name, String texture, ItemType itemType, Map<String, SceneLocation> spawns) {
        this.slug = slug;
        this.name = name;
        this.texture = texture;
        this.itemType = itemType;
        this.spawns = new HashMap<String, SceneLocation>( spawns );
    }

    public String getSlug() {
        return slug;
    }

    public String getTexture() {
        return texture;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, SceneLocation> getSpawns() {
        return spawns;
    }
}