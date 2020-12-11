package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import dk.sdu.mmmi.swe20.t1.g3.Config.Items;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemAction;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Item controller.
 */
public class ItemController {
    /**
     * The constant instance.
     */
// Singleton Pattern Declaration
    public static ItemController instance = null;
    private final ArrayList<Item> items = new ArrayList<Item>();
    // End Singleton Pattern Declaration

    private ItemController() {
        for (Items item : Items.values()) {
            if (item.getItemAction() == ItemAction.INTERACTABLE) {
                items.add(new Item(item.getSlug(), item.getName(), item.getTexture(), item.getItemType(), item.getSpawns(), item.getItemAction(), item.getRunnable()));
            } else {
                items.add(new Item(item.getSlug(), item.getName(), item.getTexture(), item.getItemType(), item.getSpawns(), item.getItemAction()));
            }
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ItemController getInstance() {
        if (instance == null) instance = new ItemController();
        return instance;
    }

    /**
     * Remove scene from item.
     *
     * @param item  the item
     * @param scene the scene
     */
    public void removeSceneFromItem(Item item, Scene scene) {
        HashMap<Scene, SceneLocation> initialSpawns = item.getSpawns();
        HashMap<Scene, SceneLocation> newSpawns = new HashMap<>();

        for (Map.Entry<Scene, SceneLocation> e : initialSpawns.entrySet()) {
            if (e.getKey() != scene) {
                newSpawns.put(e.getKey(), e.getValue());
            }
        }

        item.setSpawns(newSpawns);
    }

    /**
     * Gets items by scene.
     *
     * @param slug the slug
     * @return the items by scene
     */
    public ArrayList<Item> getItemsByScene(String slug) {
        return getItemsByScene(SceneController.getInstance().getSceneBySlug(slug));
    }

    /**
     * Gets items by scene.
     *
     * @param scene the scene
     * @return the items by scene
     */
    public ArrayList<Item> getItemsByScene(Scene scene) {
        ArrayList<Item> itemsInScene = new ArrayList<Item>();

        for (Item item : items) {
            for (Map.Entry<Scene, SceneLocation> e : item.getSpawns().entrySet()) {
                if (e.getKey() == scene) {
                    itemsInScene.add(item);
                }
            }
        }

        return itemsInScene;
    }

    /**
     * Has item boolean.
     *
     * @param sceneSlug the scene slug
     * @param itemSlug  the item slug
     * @return the boolean
     */
    public boolean hasItem(String sceneSlug, String itemSlug) {
        boolean hasItem = false;

        ArrayList<Item> itemsInScene = getItemsByScene(sceneSlug);
        for (Item item : itemsInScene) {
            if (item.getSlug().equals(itemSlug)) {
                hasItem = true;
                break;
            }
        }
        return hasItem;
    }

    /**
     * Has item boolean.
     *
     * @param scene    the scene
     * @param itemSlug the item slug
     * @return the boolean
     */
    public boolean hasItem(Scene scene, String itemSlug) {
        return hasItem(scene.getSlug(), itemSlug);
    }

    /**
     * Gets item by slug.
     *
     * @param slug the slug
     * @return the item by slug
     */
    public Item getItemBySlug(String slug) {
        return items.stream().filter(x -> x.getSlug().equals(slug)).findFirst().orElse(null);
    }
}
