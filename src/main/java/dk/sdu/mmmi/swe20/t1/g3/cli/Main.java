package dk.sdu.mmmi.swe20.t1.g3.cli;

import worldofzuul.Game;

public class Main {

    public static void main(String[] args) {
        Scenes scenes = new Scenes();

        // Load Scenes
        scenes.loadScenes();

        // Convert
        scenes.convertStringsToScenes();


    }
    
}
