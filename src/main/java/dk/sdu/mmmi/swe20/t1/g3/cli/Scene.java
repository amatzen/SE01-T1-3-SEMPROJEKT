package dk.sdu.mmmi.swe20.t1.g3.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene extends worldofzuul.Room {
    public String slug;
    public HashMap<String, String> exitsString;
    public HashMap<String, Scene> exits;
    public ArrayList<String> options;

    public Scene(String slug, String description, Map<String, String> exits) {
        super(description);
        this.slug = slug;
        this.exitsString = new HashMap<String, String>(exits);
    }

    public Scene(String slug, String description, Map<String, String> exits, ArrayList<String> options) {
        super(description);
        this.slug = slug;
        this.exitsString = new HashMap<String, String>(exits);
        this.options = options;
    }



    // Getters
    public String getSlug() {
        return slug;
    }

    public HashMap<String, String> getExitsString() {
        return exitsString;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    // Setters
    public void setExits(HashMap<String, Scene> exits) {
        this.exits = exits;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
