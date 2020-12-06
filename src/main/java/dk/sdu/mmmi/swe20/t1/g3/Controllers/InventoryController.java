package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneItem;
import io.github.techrobby.SimplePubSub.PubSub;

import java.util.ArrayList;
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

    PubSub pubSub = PubSub.getInstance();

    private ArrayList<SceneItem> inventory = new ArrayList<>();

    private InventoryController() { }

    /**
     * Add item to inventory
     *
     * @param item  the item
     * @param scene the scene
     */
    public void addToInventory(Item item, Scene scene) {
        inventory.add(new SceneItem(scene, item));
        pubSub.publish("fx_inventoryChanged", true);
    }

    /**
     * Drop item from inventory
     *
     * @param item  the item
     * @param scene the scene
     */
    public void dropItemFromInventory(Item item, Scene scene) {
        SceneItem entry = inventory.stream().filter(x->{
            return x.getItem().equals(item) && x.getScene().equals(scene);
        }).findFirst().orElse(null);

        if (entry != null) inventory.remove(entry);
        pubSub.publish("fx_inventoryChanged", true);
    }

    /**
     * Dump inventory.
     */
    public void dumpInventory() {
        inventory.clear();
        pubSub.publish("fx_inventoryChanged", true);
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public ArrayList<SceneItem> getInventory() {
        return inventory;
    }

    /**
     * Print inventory.
     */
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

    /**
     * Contains only trash boolean.
     *
     * @return the boolean
     */
    public boolean containsOnlyTrash() {
        for (SceneItem entry: inventory) {
            if(
                entry.getItem().getItemType() != ItemType.TRASH &&
                entry.getItem().getItemType() != ItemType.PLASTIC &&
                entry.getItem().getItemType()  != ItemType.METAL
            ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Contains room item boolean.
     *
     * @param scene the scene
     * @param item  the item
     * @return the boolean
     */
    public boolean containsRoomItem(Scene scene, Item item) {

        for(SceneItem entry : inventory) {
            if(entry.getItem() == item && entry.getScene() == scene) {
                return true;
            }
        }

        return false;
    }
}
