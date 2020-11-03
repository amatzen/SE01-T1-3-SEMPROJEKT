package dk.sdu.mmmi.swe20.t1.g3.cli;

import worldofzuul.Command;
import worldofzuul.Parser;
import worldofzuul.Room;

import java.util.ArrayList;

public class Game extends worldofzuul.Game {
    public ArrayList<Scene> scenes = new ArrayList<Scene>();
    protected Scene currentScene;

    public Game(String startScene) {
        this.parser = new Parser();
        this.scenes = Scenes.getScenes();

        currentScene = (new Scenes()).getSceneBySlug(startScene);
    }

    @Override
    public void play() {
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    @Override
    public void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Scene nextScene = currentScene.getExit(direction);

        if (nextScene == null) {
            System.out.println("There is no door!");
        }
        else {
            currentScene = nextScene;
            System.out.println(currentScene.getDescription());
        }
    }

}
