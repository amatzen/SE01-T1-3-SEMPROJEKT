<<<<<<< HEAD
package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import io.github.techrobby.SimplePubSub.PubSub;
import worldofzuul.Command;
import worldofzuul.CommandWord;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Game extends worldofzuul.Game {
    private final SceneController sceneController = SceneController.getInstance();
    private final ItemController itemController = ItemController.getInstance();
    private final InventoryController inventoryController = InventoryController.getInstance();
    private final PubSub pubSub = PubSub.getInstance();

    private final Parser parser = new dk.sdu.mmmi.swe20.t1.g3.Utilities.Parser();

    public Game(String startScene) {
        sceneController.goToScene(startScene);
        sceneController.getCurrentScene().displayScene();

    }

    protected boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN -> {
                System.out.println("Denne kommando findes ikke. Mente du?");
                parser.showCommands();
                return false;
            }
            case GO -> goRoom(command);
            case PICKUP -> pickupItem(command);
            case INVENTORY -> printInventory();
            case HELP -> printHelp();
            case QUIT -> wantToQuit = quit(command);
            case DROP -> dropItem(command);
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
    public void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Hvor vil du gå hen?");
            return;
        }

        String direction = command.getSecondWord();

        Scene nextScene = sceneController.getCurrentScene().getExit(direction);

        if (nextScene == null) {
            System.out.println("Ingen dør!");
            pubSub.publish("fx_notify", "Ingen dør#Ingen dør fundet!");
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
        InventoryController inventoryController = InventoryController.getInstance();

        Scene currentScene = sceneController.getCurrentScene();
        String itemSlug = command.getSecondWord();

        ArrayList<Item> items = itemController.getItemsByScene(sceneController.getCurrentScene());
        ArrayList<Item> itemsNotPickedUp = new ArrayList<Item>(items.stream().filter(x -> !inventoryController.containsRoomItem(sceneController.getCurrentScene(), x)).collect(Collectors.toList()));

        if(itemController.hasItem(currentScene, itemSlug) && itemsNotPickedUp.contains(itemController.getItemBySlug(itemSlug))) {
            Item item = itemController.getItemBySlug(itemSlug);
            inventoryController.addToInventory(item, currentScene);
            String feedbackMessage = item.getItemType() != ItemType.BIO ?
                    "Jaaa, hvor er du god! Tak fordi du samlede " + item.getName() + " op!"
                    :
                    "Er du sikker på, at " + item.getName() + " ikke høre til i naturen? Anyways, " + item.getName() + " ligger nu i dit inventory!";
            System.out.println(feedbackMessage);

            pubSub.publish("fx_sceneChanged", sceneController.getCurrentScene().getSlug());
        } else {
            System.out.println("Kunne ikke samle tingen op, vil du prøve igen?");
            pubSub.publish("fx_notify", "Kunne ikke samle tingen op#Har du måske allerede den i inventoryet?");
        }

    }

    void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Hvad skal jeg lægge tilbage?");
        }
        String itemSlug = command.getSecondWord();
        Scene currentScene = sceneController.getCurrentScene();

        if(inventoryController.containsRoomItem(currentScene,
                itemController.getItemBySlug(command.getSecondWord()))) {
            Item item = itemController.getItemBySlug(itemSlug);
            inventoryController.dropItemFromInventory(item, currentScene);
            String feedbackMessage = item.getItemType() == ItemType.BIO ?
                    "Tak fordi du puttede " + item.getName() + " tilbage i naturen"
                    :
                    "Er du sikker på, at " + item.getName() + " skal tilbage i naturen? " + item.getName() + " ligger nu i naturen igen";
            System.out.println(feedbackMessage);

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
=======
package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.ItemController;
import dk.sdu.mmmi.swe20.t1.g3.Controllers.SceneController;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Types.ItemType;
import io.github.techrobby.SimplePubSub.PubSub;
import worldofzuul.Command;
import worldofzuul.CommandWord;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Game extends worldofzuul.Game {
    private final SceneController sceneController = SceneController.getInstance();
    private final ItemController itemController = ItemController.getInstance();
    private final InventoryController inventoryController = InventoryController.getInstance();
    private final PubSub pubSub = PubSub.getInstance();

    private final Parser parser = new dk.sdu.mmmi.swe20.t1.g3.Utilities.Parser();

    public Game(String startScene) {
        sceneController.goToScene(startScene);
        sceneController.getCurrentScene().displayScene();

    }

    protected boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN -> {
                System.out.println("Denne kommando findes ikke. Mente du?");
                parser.showCommands();
                return false;
            }
            case GO -> goRoom(command);
            case PICKUP -> pickupItem(command);
            case INVENTORY -> printInventory();
            case HELP -> printHelp();
            case QUIT -> wantToQuit = quit(command);
            case DROP -> dropItem(command);
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
    public void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Hvor vil du gå hen?");
            return;
        }

        String direction = command.getSecondWord();

        Scene nextScene = sceneController.getCurrentScene().getExit(direction);

        if (nextScene == null) {
            System.out.println("Ingen dør!");
            pubSub.publish("fx_notify", "Ingen dør#Ingen dør fundet!");
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
        InventoryController inventoryController = InventoryController.getInstance();

        Scene currentScene = sceneController.getCurrentScene();
        String itemSlug = command.getSecondWord();

        ArrayList<Item> items = itemController.getItemsByScene(sceneController.getCurrentScene());
        ArrayList<Item> itemsNotPickedUp = new ArrayList<Item>(items.stream().filter(x -> !inventoryController.containsRoomItem(sceneController.getCurrentScene(), x)).collect(Collectors.toList()));

        if(itemController.hasItem(currentScene, itemSlug) && itemsNotPickedUp.contains(itemController.getItemBySlug(itemSlug))) {
            Item item = itemController.getItemBySlug(itemSlug);
            inventoryController.addToInventory(item, currentScene);
            String feedbackMessage = item.getItemType() != ItemType.BIO ?
                    "Jaaa, hvor er du god! Tak fordi du samlede " + item.getName() + " op!"
                    :
                    "Er du sikker på, at " + item.getName() + " ikke høre til i naturen? Anyways, " + item.getName() + " ligger nu i dit inventory!";
            System.out.println(feedbackMessage);

            pubSub.publish("fx_sceneChanged", sceneController.getCurrentScene().getSlug());
        } else {
            System.out.println("Kunne ikke samle tingen op, vil du prøve igen?");
            pubSub.publish("fx_notify", "Kunne ikke samle tingen op#Har du måske allerede den i inventoryet?");
        }

    }

    void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Hvad skal jeg lægge tilbage?");
        }
        String itemSlug = command.getSecondWord();
        Scene currentScene = sceneController.getCurrentScene();

        if(inventoryController.containsRoomItem(currentScene,
                itemController.getItemBySlug(command.getSecondWord()))) {
            Item item = itemController.getItemBySlug(itemSlug);
            inventoryController.dropItemFromInventory(item, currentScene);
            String feedbackMessage = item.getItemType() == ItemType.BIO ?
                    "Tak fordi du puttede " + item.getName() + " tilbage i naturen"
                    :
                    "Er du sikker på, at " + item.getName() + " skal tilbage i naturen? " + item.getName() + " ligger nu i naturen igen";
            System.out.println(feedbackMessage);
        }
        else {
            Item item = itemController.getItemBySlug(itemSlug);
            String feedbackMessage = item.getItemType() != ItemType.BIO ?
                    "Er du sikker på, at " + item.getName() + " skal tilbage i naturen..? I hvert fald kan " + item.getName() +
                    " kun droppes i rummet du fandt " + item.getName() + " find rummet og prøv igen!"
                    :
                    "God ide at putte " + item.getName() + " tilbage i naturen, men for at putte " + item.getName() +
                    " tilbae i naturen, skal du lægge det i rummet hvor du fandt det. Tak!!";
            System.out.println(feedbackMessage);
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
>>>>>>> a60483aa76c9a64d8bfde1cfd6805f001054b72f
