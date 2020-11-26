package dk.sdu.mmmi.swe20.t1.g3.Objects;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.level.Level;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.Scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene extends worldofzuul.Room {
    public String slug; // Utrolig kort, alfanumerisk ID
    public String name; // Navnet på scenen, bruges til identifikation mellem scener.
    private HashMap<String, String> exitsString;
    private HashMap<String, Scene> exits;
    private ArrayList<String> itemsString;
    private ArrayList<Item> items;
    private String view;

    // Constructor without any particular items
    public Scene(String slug, String name, String description, Map<String, String> exits) {
        super(description);
        this.name = name;
        this.slug = slug;
        this.exitsString = new HashMap<String, String>(exits);
    }

    // Constructor with any particular items
    public Scene(String slug, String name, String description, Map<String, String> exitsString, ArrayList<String> itemsString) {
        super(description);
        this.slug = slug;
        this.name = name;
        this.exitsString = new HashMap<String, String>(exitsString);
        this.itemsString = itemsString;
    }

    // Constructor with any particular items and view
    public Scene(String slug, String name, String description, Map<String, String> exitsString, ArrayList<String> itemsString, String view) {
        super(description);
        this.slug = slug;
        this.name = name;
        this.exitsString = new HashMap<String, String>(exitsString);
        this.itemsString = itemsString;
        this.view = view;
    }

    // CLI-exclusive
    public void displayScene() {
        System.out.printf("======== %s ========\n", this.slug.toUpperCase());
        System.out.println(this.getDescription());
        System.out.println();
        displayExits();
    }
    private void displayExits() {
        System.out.println("Du har følgende udgange:");
        exitsString.forEach((key, value) -> {
            System.out.println(key + " -> " + Scenes.getSceneBySlug(value).getName());
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
    public ArrayList<Item> getItems() { return items; }
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
    public void setTasks(ArrayList<Item> items) {
        this.items = items;
    }

    public String getView() {
        return this.view;
    }
}
