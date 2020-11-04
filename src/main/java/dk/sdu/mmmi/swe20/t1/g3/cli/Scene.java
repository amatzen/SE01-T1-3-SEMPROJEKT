package dk.sdu.mmmi.swe20.t1.g3.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene extends worldofzuul.Room {
    public String slug; // Utrolig kort, alfanumerisk ID
    public String name; // Navnet på scenen, bruges til identifikation mellem scener.
    private HashMap<String, String> exitsString;
    private HashMap<String, Scene> exits;
    private ArrayList<String> tasksString;
    private ArrayList<Task> tasks;

    // Constructor without any particular options / games
    public Scene(String slug, String name, String description, Map<String, String> exits) {
        super(description);
        this.name = name;
        this.slug = slug;
        this.exitsString = new HashMap<String, String>(exits);
    }

    // Constructor with any particular options / games
    public Scene(String slug, String name, String description, Map<String, String> exitsString, ArrayList<String> tasksString) {
        super(description);
        this.slug = slug;
        this.name = name;
        this.exitsString = new HashMap<String, String>(exitsString);
        this.tasksString = tasksString;
    }

    public void displayScene() {
        System.out.printf("======== %s ========\n", this.slug.toUpperCase());
        System.out.println(this.getDescription());
        System.out.println();
        displayExits();
    }

    private void displayExits() {
        System.out.println("Du har følgende udgange:");
        exitsString.forEach((key, value) -> {
            System.out.println(key + " -> " + value);
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
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public String getDescription() { return super.getShortDescription(); }

    @Override
    public Scene getExit(String direction) {
        return this.exits.get(direction);
    }

    // Setters
    public void setExits(HashMap<String, Scene> exits) {
        this.exits = exits;
    }
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
