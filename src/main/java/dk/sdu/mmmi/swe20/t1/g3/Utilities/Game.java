package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import worldofzuul.Command;
import worldofzuul.CommandWord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Game extends worldofzuul.Game {
    private final SceneController sceneController = SceneController.getInstance();
    private Parser parser;

    public Game(String startScene) {
        this.parser = new dk.sdu.mmmi.swe20.t1.g3.Utilities.Parser();

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
        AtomicBoolean finished = new AtomicBoolean(false);

        // https://www.baeldung.com/java-asynchronous-programming
        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future futureTask = threadpool.submit(() -> {
            pubSub.addListener("executeCommand", ((type, object) -> {
                processCommand(parser.getCommandFromString((String) object));
                System.out.print("> ");
            }));
            pubSub.addListener("exitApplication", ((type, object) -> {
                threadpool.shutdownNow();
            }));
        });

        while (!finished.get()) {
            Command command = parser.getCommand();
            finished.set(processCommand(command));
        }
        System.out.println("Tak for spillet!");
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
                e.printStackTrace();
            }
        }
    }

    void printInventory() {
        InventoryController.getInstance().printInventory();
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
            String feedbackMessage = item.getItemType() != ItemType.BIO ?
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
