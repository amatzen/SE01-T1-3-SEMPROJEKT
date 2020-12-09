package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import dk.sdu.mmmi.swe20.t1.g3.Config.Items;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemAction;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneLocation;

import java.util.ArrayList;
import java.util.Map;

public class ItemController {
    // Singleton Pattern Declaration
    public static ItemController instance = null;
    public static ItemController getInstance() {
        if (instance == null) instance = new ItemController();
        return instance;
    }
    // End Singleton Pattern Declaration

    private ArrayList<Item> items = new ArrayList<Item>();

    private ItemController() {
        for (Items item: Items.values()) {
            if(item.getItemAction() == ItemAction.INTERACTABLE) {
                items.add(new Item(item.getSlug(), item.getName(), item.getTexture(), item.getItemType(), item.getSpawns(), item.getItemAction(), item.getRunnable()));
            } else {
                items.add(new Item(item.getSlug(), item.getName(), item.getTexture(), item.getItemType(), item.getSpawns(), item.getItemAction()));
            }
        }
    }

    public ArrayList<Item> getItemsByScene(String slug) {
        return getItemsByScene(SceneController.getInstance().getSceneBySlug(slug));
    }

    public ArrayList<Item> getItemsByScene(Scene scene) {
        ArrayList<Item> itemsInScene = new ArrayList<Item>();

        for (Item item : items) {
            for (Map.Entry<Scene, SceneLocation> e : item.getSpawns().entrySet()) {
                if(e.getKey() == scene) {
                    itemsInScene.add(item);
                }
            }
        }

        return itemsInScene;
    }

    public boolean hasItem(String sceneSlug, String itemSlug) {
        boolean hasItem = false;

        ArrayList<Item> itemsInScene = getItemsByScene(sceneSlug);
        for (Item item: itemsInScene) {
            if (item.getSlug().equals(itemSlug)) {
                hasItem = true;
                break;
            }
        }
        return hasItem;
    }

    public boolean hasItem(Scene scene, String itemSlug) {
        return hasItem(scene.getSlug(), itemSlug);
    }

    public Item getItemBySlug(String slug) {
        return items.stream().filter(x -> x.getSlug().equals(slug)).findFirst().orElse(null);
    }
}
