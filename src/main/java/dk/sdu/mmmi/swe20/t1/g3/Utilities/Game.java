package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import worldofzuul.Command;
import worldofzuul.CommandWord;

import java.util.HashMap;
import java.util.Map;

public class Game extends worldofzuul.Game {
    private SceneController sceneController = SceneController.getInstance();

    public Game(String startScene) {
        this.parser = new Parser();

        sceneController.goToScene(startScene);
        sceneController.getCurrentScene().displayScene();
    }

    protected boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("Denne kommando findes ikke. Mente du?");
                parser.showCommands();
                return false;
            case GO:
                goRoom(command);
                break;
            case PICKUP:
                pickupItem(command);
                break;
            case INVENTORY:
                printInventory();
                break;
            case HELP:
                printHelp();
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
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

        Scene nextScene = sceneController.getCurrentScene().getExit(direction);

        if (nextScene == null) {
            System.out.println("There is no door!");
        }
        else {
            try {
                sceneController.goToScene(nextScene);
                sceneController.getCurrentScene().displayScene();
            }
            catch (Exception e) {
                System.out.println("Der skete en fejl!");
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
        }
    }

    void printInventory() {
        InventoryController inventoryController = InventoryController.getInstance();

        HashMap<Item, Scene> inventory = inventoryController.getInventory();
        System.out.println("Du har følgende i dit inventory:");
        for (Map.Entry<Item, Scene> entry : inventory.entrySet()) {
            System.out.printf(entry.getKey().getName() + "    ");
        }
        System.out.println("");
    }

    void pickupItem(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Hvad skal jeg samle op?");
        }

        ItemController itemController = ItemController.getInstance();

        Scene currentScene = sceneController.getCurrentScene();
        String itemSlug = command.getSecondWord();

        if(itemController.hasItem(currentScene, itemSlug) && itemController.getItemBySlug(itemSlug) != null) {
            InventoryController inventoryController = InventoryController.getInstance();
            Item item = itemController.getItemBySlug(itemSlug);
            inventoryController.addToInventory(item, currentScene);
            String feedbackMessage = item.getItemType() == ItemType.TRASH ?
                    "Jaaa, hvor er du god! Tak fordi du samlede " + item.getName() + " op!"
                    :
                    "Er du sikker på, at " + item.getName() + " ikke høre til i naturen? Anyways, " + item.getName() + " ligger nu i dit inventory!";
            System.out.println(feedbackMessage);
        } else {
            System.out.println("Kunne ikke samle tingen op, vil du prøve igen?");
        }

    }

    public void printHelp() {
        System.out.println("I World of Fish skal du bidrage til miljøet.");
        System.out.println("Spillet er opdelt i forskellige rum, hvor der er ting du skal samle op. Det handler om at samle de ting op, der ikke høre til i naturen.");
        System.out.println();
        System.out.println("Du kan se hvad du kan gøre her:");
        parser.showCommands();
    }

}
