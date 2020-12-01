package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryController {
    // Singleton Pattern Declaration
    public static InventoryController instance = null;
    public static InventoryController getInstance() {
        if (instance == null) instance = new InventoryController();
        return instance;
    }
    // End Singleton Pattern Declaration

    private InventoryController() {}

    private ArrayList<SceneItem> inventory = new ArrayList<>();

    public void addToInventory(Item item, Scene scene) {
        inventory.add(new SceneItem(scene, item));
    }

    public void dumpInventory() {
        inventory.clear();
    }

    public ArrayList<SceneItem> getInventory() {
        return inventory;
    }

    public void printInventory() {
        ArrayList<SceneItem> inventory = getInventory();
        Map<String, Long> freqInventory = inventory.stream()
                .collect(Collectors.groupingBy(e->e.getItem().getName(), Collectors.counting()));
        System.out.println("Du har følgende i dit inventory:");

        freqInventory.entrySet().forEach(e -> {
            System.out.printf("%s   ", (e.getValue() > 1) ? e.getKey() + "×" + e.getValue() : e.getKey() );
        });

        System.out.println("");
    }

    public boolean containsOnlyTrash() {
        for (SceneItem entry: inventory) {
            if(entry.getItem().getItemType() != ItemType.TRASH) {
                return false;
            }
        }

        return true;
    }

    public boolean containsRoomItem(Scene scene, Item item) {

        for(SceneItem entry : inventory) {
            if(entry.getItem() == item && entry.getScene() == scene) {
                return true;
            }
        }

        return false;
    }
}
