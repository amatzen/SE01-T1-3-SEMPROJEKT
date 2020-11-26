package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import worldofzuul.Command;
import worldofzuul.CommandWord;

import java.util.ArrayList;

public class Game extends worldofzuul.Game {
    public ArrayList<Scene> scenes = new ArrayList<Scene>();
    protected Scene currentScene;

    public Game(String startScene) {
        this.parser = new Parser();
        this.scenes = Scenes.getScenes();

        currentScene = Scenes.getSceneBySlug(startScene);
        currentScene.displayScene();
    }

    protected boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("Denne kommando findes ikke. Mente du?");
            parser.showCommands();
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
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
            currentScene.displayScene();
        }
    }

    public void printHelp() {
        System.out.println("I World of Fish skal du løse missioner som bidrager til miljøet.");
        System.out.println("Spillet er opdelt i forskellige rum, hvor der er opgaver. Det handler om at besvare rigtigt på alle opgaver.");
        System.out.println();
        System.out.println("Du kan se hvad du kan gøre her:");
        parser.showCommands();
    }

}
