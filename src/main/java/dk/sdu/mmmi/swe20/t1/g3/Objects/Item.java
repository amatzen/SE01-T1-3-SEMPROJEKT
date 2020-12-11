package dk.sdu.mmmi.swe20.t1.g3.Objects;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemAction;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * The type Item.
 */
public class Item {
    private final String slug;
    private final String name;
    private final String texture;
    private final ItemType itemType;
    private final ItemAction itemAction;
    private HashMap<Scene, SceneLocation> spawns = new HashMap<Scene, SceneLocation>();

    private Runnable interactHandler;

    /**
     * Instantiates a new Item.
     *
     * @param slug       the slug
     * @param name       the name
     * @param texture    the texture
     * @param itemType   the item type
     * @param spawns     the spawns
     * @param itemAction the item action
     */
    public Item(String slug, String name, String texture, ItemType itemType, HashMap<String, SceneLocation> spawns, ItemAction itemAction) {
        this.slug = slug;
        this.name = name;
        this.texture = texture;
        this.itemType = itemType;
        this.itemAction = itemAction;

        SceneController sceneController = SceneController.getInstance();

        this.spawns = new HashMap<Scene, SceneLocation>(
                spawns.entrySet().stream()
                        .filter(s -> s.getKey() != null & s.getValue() != null)
                        .collect(Collectors.toMap(
                                e -> sceneController.getSceneBySlug(e.getKey()), e -> e.getValue()
                        ))
        );
    }

    /**
     * Instantiates a new Item.
     *
     * @param slug            the slug
     * @param name            the name
     * @param texture         the texture
     * @param itemType        the item type
     * @param spawns          the spawns
     * @param itemAction      the item action
     * @param interactHandler the interact handler
     */
    public Item(String slug, String name, String texture, ItemType itemType, HashMap<String, SceneLocation> spawns, ItemAction itemAction, Runnable interactHandler) {
        this.slug = slug;
        this.name = name;
        this.texture = texture;
        this.itemType = itemType;
        this.itemAction = itemAction;

        this.interactHandler = interactHandler;

        SceneController sceneController = SceneController.getInstance();

        this.spawns = new HashMap<Scene, SceneLocation>(
                spawns.entrySet().stream()
                        .filter(s -> s.getKey() != null & s.getValue() != null)
                        .collect(Collectors.toMap(
                                e -> sceneController.getSceneBySlug(e.getKey()), e -> e.getValue()
                        ))
        );
    }

    /**
     * Gets spawns.
     *
     * @return the spawns
     */
    public HashMap<Scene, SceneLocation> getSpawns() {
        return spawns;
    }

    /**
     * Sets spawns.
     *
     * @param spawns the spawns
     */
    public void setSpawns(HashMap<Scene, SceneLocation> spawns) {
        this.spawns = spawns;
    }

    /**
     * Gets texture.
     *
     * @return the texture
     */
    public String getTexture() {
        return texture;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets slug.
     *
     * @return the slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Gets item type.
     *
     * @return the item type
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Gets item action.
     *
     * @return the item action
     */
    public ItemAction getItemAction() {
        return itemAction;
    }

    /**
     * Gets interact handler.
     *
     * @return the interact handler
     */
    public Runnable getInteractHandler() {
        return interactHandler;
    }
}
