package dk.sdu.mmmi.swe20.t1.g3.Objects;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Scene.
 */
public class Scene extends worldofzuul.Room {
    /**
     * The Slug.
     */
    public String slug; // Utrolig kort, alfanumerisk ID
    /**
     * The Name.
     */
    public String name; // Navnet på scenen, bruges til identifikation mellem scener.
    private final String sceneURL;
    private final HashMap<String, String> exitsString;
    private HashMap<String, Scene> exits;

    /**
     * Instantiates a new Scene.
     *
     * @param slug        the slug
     * @param name        the name
     * @param description the description
     * @param sceneURL    the scene url
     * @param exits       the exits
     */
// Constructor without any particular options / games
    public Scene(String slug, String name, String description, String sceneURL, Map<String, String> exits) {
        super(description);
        this.name = name;
        this.slug = slug;
        this.sceneURL = sceneURL;
        this.exitsString = new HashMap<String, String>(exits);
    }

    /**
     * Display scene.
     */
    public void displayScene() {
        System.out.printf("======== %s ========\n", this.slug.toUpperCase());
        System.out.println(this.getDescription());

        ItemController itemController = ItemController.getInstance();
        InventoryController inventoryController = InventoryController.getInstance();

        ArrayList<Item> items = itemController.getItemsByScene(this);
        ArrayList<Item> itemsNotPickedUp = new ArrayList<Item>(items.stream().filter(x -> !inventoryController.containsRoomItem(this, x)).collect(Collectors.toList()));

        if (itemsNotPickedUp.size() > 0) {
            System.out.println();
            System.out.println("I dette rum ser du:");
            for (Item item : itemsNotPickedUp) {
                System.out.println(item.getName() + (item.getItemAction() == ItemAction.INTERACTABLE ? " (Kan ikke samles op)" : ""));
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

    /**
     * Gets slug.
     *
     * @return the slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Gets exits string.
     *
     * @return the exits string
     */
    public HashMap<String, String> getExitsString() {
        return exitsString;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return super.getShortDescription();
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
     * Gets scene url.
     *
     * @return the scene url
     */
    public String getSceneURL() {
        return sceneURL;
    }

    @Override
    public Scene getExit(String direction) {
        return this.exits.get(direction);
    }

    /**
     * Sets exits.
     *
     * @param exits the exits
     */
// Setters
    public void setExits(HashMap<String, Scene> exits) {
        this.exits = exits;
    }
}
