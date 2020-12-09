package dk.sdu.mmmi.swe20.t1.g3.Config;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemAction;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Game;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.scene.Scene;
import javafx.scene.shape.Box;

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
                "skov", new SceneLocation(800,500)
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
            "",
            ItemType.PLASTIC,
            Map.of(
                    "skov", new SceneLocation(0, 0),
                    "havbund", new SceneLocation(0, 0)
            ),
            ItemAction.PICKUPABLE
    ),
    PLASTBUNKE(
            "plastbunke",
            "Plastbunke",
            "",
            ItemType.PLASTIC,
            Map.of(
                    "hav", new SceneLocation(0, 0),
                    "koralrev", new SceneLocation(0, 0)
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
                System.out.println("Test");

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