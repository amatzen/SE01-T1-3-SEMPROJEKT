package dk.sdu.mmmi.swe20.t1.g3;

import dk.sdu.mmmi.swe20.t1.g3.Utilities.Scenes;

public class Main {
    public static void main(String[] args) {
        // Load Scenes
        Scenes.loadScenes();

        // Convert
        Scenes.convertStringsToScenes();

        welcome();

        Game g = new Game("start");
        g.play();

    }

    public static void welcome() {
        System.out.println("");
        System.out.println("🐟 Welcome to World of Fish");
    }
    
}
