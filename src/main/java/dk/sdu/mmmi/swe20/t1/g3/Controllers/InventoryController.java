package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryController {
    // Singleton Pattern Declaration
    public static InventoryController instance = null;
    public static InventoryController getInstance() {
        if (instance == null) instance = new InventoryController();
        return instance;
    }
    // End Singleton Pattern Declaration

    private InventoryController() {}

    private HashMap<Item, Scene> inventory = new HashMap<>();

    public void addToInventory(Item item, Scene scene) {
        inventory.put(item, scene);
    }

    public void dumpInventory() {
        inventory.clear();
    }

    public HashMap<Item, Scene> getInventory() {
        return inventory;
    }

    public boolean containsOnlyTrash() {
        for (Map.Entry<Item, Scene> entry: inventory.entrySet()) {
            if(entry.getKey().getItemType() != ItemType.TRASH ) {
                return false;
            }
        }

        return true;
    }

    public boolean containsRoomItem(Scene scene, Item item) {
        for(Map.Entry<Item, Scene> entry: inventory.entrySet()) {
            if(entry.getKey() == item && entry.getValue() == scene) {
                return true;
            }
        }

        return false;
    }
}
