package dk.sdu.mmmi.swe20.t1.g3.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene extends worldofzuul.Room {
    public String slug;
    public HashMap<String, String> exitsString;
    public HashMap<String, Scene> exits;
    public ArrayList<String> tasksString;
    public ArrayList<Task> tasks;

    // Constructor without any particular options / games
    public Scene(String slug, String description, Map<String, String> exits) {
        super(description);
        this.slug = slug;
        this.exitsString = new HashMap<String, String>(exits);
    }

    // Constructor with any particular options / games
    public Scene(String slug, String description, Map<String, String> exitsString, ArrayList<String> tasksString) {
        super(description);
        this.slug = slug;
        this.exitsString = new HashMap<String, String>(exitsString);
        this.tasksString = tasksString;
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

    @Override
    public Scene getExit(String direction) {
        return this.exits.get(direction);
    }

    public String getDescription() {
        return super.getShortDescription();
    }

    // Setters
    public void setExits(HashMap<String, Scene> exits) {
        this.exits = exits;
    }
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
