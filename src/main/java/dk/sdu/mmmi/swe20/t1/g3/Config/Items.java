package dk.sdu.mmmi.swe20.t1.g3.Config;

import dk.sdu.mmmi.swe20.t1.g3.Types.ItemAction;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;
import io.github.techrobby.SimplePubSub.PubSub;

import java.util.HashMap;
import java.util.Map;

public enum Items {
    // Pickupables
    SKRALD(
            "skrald",
            "Skrald",
            "Views/Items/SKRALD.png",
            ItemType.TRASH,
            Map.of(
                "start", new SceneLocation(430,560),
                "skov", new SceneLocation(800,500),
                "strandv2", new SceneLocation(600, 100),
                "klippekant", new SceneLocation(200,600)
            ),
            ItemAction.PICKUPABLE
    ),
    TANG(
            "tang",
            "Tang",
            "Views/Items/TANG.png",
            ItemType.BIO,
            Map.of(
                "strand", new SceneLocation(500,300),
                "koralrev", new SceneLocation(250, 600),
                "skibsvrag", new SceneLocation(200, 600),
                "klippekant", new SceneLocation(500, 600),
                "strandv2", new SceneLocation(790, 600)
            ),
            ItemAction.PICKUPABLE
    ),
    PLASTIKFLASKE(
            "plastikflaske",
            "Plastikflaske",
            "Views/Items/PLASTIKFLASKE.png",
            ItemType.PLASTIC,
            Map.of(
                "hav", new SceneLocation(400, 360),
                "klippekant", new SceneLocation(800, 600),
                "skibsvrag", new SceneLocation(1200, 700),
                "koralrev", new SceneLocation(800, 600),
                "klippetop", new SceneLocation(700, 430)
            ),
        ItemAction.PICKUPABLE
    ),
    PLASTIKBUNKE(
            "plastikbunke",
            "Plastikbunken",
            "Views/Items/PLASTIKBUNKE.png",
            ItemType.PLASTIC,
            Map.of(
                "start", new SceneLocation(400, 360),
                "skibsvrag", new SceneLocation(300, 600),
                "koralrev", new SceneLocation(250, 450),
                "klippekant", new SceneLocation(150, 500),
                "strandv2", new SceneLocation(1000, 515)
            ),
            ItemAction.PICKUPABLE
    ),
    BLÅMUSLING(
            "blåmusling",
            "Blåmuslingen",
            "Views/Items/BLÅMUSLING.png",
            ItemType.BIO,
            Map.of(
                "strand", new SceneLocation(400, 550),
                "skibsvrag", new SceneLocation(1200, 440),
                "strandv2", new SceneLocation(250, 450),
                "skov", new SceneLocation(1200, 300),
                "klippekant", new SceneLocation(800, 450)
            ),
            ItemAction.PICKUPABLE
    ),

    // Interactables
    SKRALDESPAND(
            "skraldespand",
            "Skraldespand",
            "Views/Items/SKRALDESPAND.png",
            ItemType.INTERACTABLE,
            Map.of(
                "start", new SceneLocation(600, 200)
            ),
            ItemAction.INTERACTABLE,
            () -> {
                PubSub.getInstance().publish("executeCommand","dump");
            }
    );

    private String slug, name, texture;
    private ItemType itemType;
    private HashMap<String, SceneLocation> spawns;
    private ItemAction itemAction;
    private Runnable runnable;
    Items(String slug, String name, String texture, ItemType itemType, Map<String, SceneLocation> spawns, ItemAction itemAction) {
        this.slug = slug;
        this.name = name;
        this.texture = texture;
        this.itemType = itemType;
        this.spawns = new HashMap<String, SceneLocation>( spawns );
        this.itemAction = itemAction;
    }
    Items(String slug, String name, String texture, ItemType itemType, Map<String, SceneLocation> spawns, ItemAction itemAction, Runnable runnable) {
        this.slug = slug;
        this.name = name;
        this.texture = texture;
        this.itemType = itemType;
        this.spawns = new HashMap<String, SceneLocation>( spawns );
        this.itemAction = itemAction;
        this.runnable = runnable;
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

    public ItemAction getItemAction() {
        return itemAction;
    }

    public Runnable getRunnable() {
        return runnable;
    }
}