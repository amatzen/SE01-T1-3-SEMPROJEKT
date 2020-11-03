package dk.sdu.mmmi.swe20.t1.g3.cli;

public class Main {
    public static void main(String[] args) {
        Scenes scenes = new Scenes();

        // Load Scenes
        scenes.loadScenes();

        // Convert
        scenes.convertStringsToScenes();

        welcome();

        Game g = new Game("start");
        g.play();

    }

    public static void welcome() {
        System.out.println("");
        System.out.println("🐟 Welcome to World of Fish");
    }
    
}
