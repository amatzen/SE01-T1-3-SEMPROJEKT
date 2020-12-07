package dk.sdu.mmmi.swe20.t1.g3.Objects;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Scene extends worldofzuul.Room {
    public String slug; // Utrolig kort, alfanumerisk ID
    public String name; // Navnet på scenen, bruges til identifikation mellem scener.
    private HashMap<String, String> exitsString;
    private HashMap<String, Scene> exits;
    private ArrayList<String> itemsString;
    private ArrayList<Item> items;

    // Constructor without any particular options / games
    public Scene(String slug, String name, String description, Map<String, String> exits) {
        super(description);
        this.name = name;
        this.slug = slug;
        this.exitsString = new HashMap<String, String>(exits);
    }

    public void displayScene() {
        System.out.printf("======== %s ========\n", this.slug.toUpperCase());
        System.out.println(this.getDescription());

        ItemController itemController = ItemController.getInstance();
        InventoryController inventoryController = InventoryController.getInstance();

        ArrayList<Item> items = itemController.getItemsByScene(this);
        ArrayList<Item> itemsNotPickedUp = new ArrayList<Item>(items.stream().filter(x -> !inventoryController.containsRoomItem(this, x)).collect(Collectors.toList()));

        if(itemsNotPickedUp.size() > 0) {
            System.out.println("");
            System.out.println("I dette rum ser du:");
            for (Item item: itemsNotPickedUp) {
                System.out.println(item.getName());
            }
        }

        System.out.println();
        displayExits();
    }

    private void displayExits() {
        SceneController sceneController = SceneController.getInstance();

        System.out.println("Du har følgende udgange:");
        exitsString.forEach((key, value) -> {
            System.out.println(key + " -> " + sceneController.getSceneBySlug(value).getName());
        });
        System.out.println();
    }

    // Getters
    public String getSlug() {
        return slug;
    }
    public HashMap<String, String> getExitsString() {
        return exitsString;
    }
    public String getDescription() { return super.getShortDescription(); }
    public String getName() { return name; }

    @Override
    public Scene getExit(String direction) {
        return this.exits.get(direction);
    }

    // Setters
    public void setExits(HashMap<String, Scene> exits) {
        this.exits = exits;
    }
}
