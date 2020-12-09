package dk.sdu.mmmi.swe20.t1.g3.Views.Partials;

import dk.sdu.mmmi.swe20.t1.g3.Controllers.InventoryController;
import dk.sdu.mmmi.swe20.t1.g3.Main;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;
import dk.sdu.mmmi.swe20.t1.g3.Utilities.SceneItem;
import io.github.techrobby.SimplePubSub.PubSub;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

// <!-- layoutX="483.0" layoutY="825.0" -->

public class InventoryViewController {
    @FXML
    public StackPane itemSlot_0, itemSlot_1, itemSlot_2, itemSlot_3, itemSlot_4;

    HashMap<Integer, Item> invLocation = new HashMap<>();

    PubSub pubSub = PubSub.getInstance();
    InventoryController inventoryController = InventoryController.getInstance();
    HashMap<Item, Long> inventory = new HashMap<>();

    @FXML
    GridPane InventoryGrid;

    @FXML
    public void initialize() {
        pubSub.addListener("fx_inventoryChanged", (type, object) -> {
            updateInventory();

            int i = 0;
            for ( StackPane sp : new StackPane[] { itemSlot_0, itemSlot_1, itemSlot_2, itemSlot_3, itemSlot_4 }) {
                int finalI = i;

                sp.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(invLocation.get(finalI) == null) return;

                        String targetSlug = invLocation.get(finalI).getSlug();
                        pubSub.publish("executeCommand", String.format("drop %s", targetSlug));
                        pubSub.publish("fx_respawnItems", true);
                    }
                });

                i++;
            };
        });

        /*
        Før PubSub:

        Thread updater = new Thread(() -> {
            while (true) {
                updateInventory();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updater.start();
        */
    }

    private void updateInventory() {
        invLocation.clear();
        for (StackPane currentSlot : new StackPane[] { itemSlot_0, itemSlot_1, itemSlot_2, itemSlot_3, itemSlot_4 }) {
            currentSlot.getChildren().get(0).getStyleClass().remove("inventoryField-filled");
            ((Rectangle) currentSlot.getChildren().get(1)).setFill(Color.TRANSPARENT);
            ((Text) ((StackPane) currentSlot.getChildren().get(2)).getChildren().get(1)).setText("0");
            currentSlot.setStyle("-fx-cursor: default;");
        }


        ArrayList<SceneItem> inputInventory = inventoryController.getInventory();

        inventory = new HashMap<>(
            inputInventory.stream()
                .collect(Collectors.groupingBy(SceneItem::getItem, Collectors.counting()))
        );
        inventory.forEach((key, value) -> {
            boolean itemAlreadyInSlot = itemInSlot(key);

            int SLOT_INDEX = !itemAlreadyInSlot ? invLocation.size() : getItemSlot(key);
            invLocation.put(SLOT_INDEX, key);
            StackPane currentSlot = switch (SLOT_INDEX) {
                case 0 -> itemSlot_0;
                case 1 -> itemSlot_1;
                case 2 -> itemSlot_2;
                case 3 -> itemSlot_3;
                case 4 -> itemSlot_4;
                default -> null;
            };

            try {
                if ( !itemAlreadyInSlot ) {
                    assert currentSlot != null;
                    currentSlot.getChildren().get(0).getStyleClass().add("inventoryField-filled");

                    InputStream is = Main.class.getResourceAsStream(key.getTexture());
                    Image tileImg = new Image(is);

                    Rectangle slotPlaceHolder = (Rectangle) currentSlot.getChildren().get(1);
                    slotPlaceHolder.setFill(new ImagePattern(tileImg));
                }

                assert currentSlot != null;
                StackPane slotAmountPane = (StackPane) currentSlot.getChildren().get(2);
                Text slotAmountText = (Text) slotAmountPane.getChildren().get(1);

                slotAmountText.setText(String.format("%x", value));

                currentSlot.setStyle("-fx-cursor: hand;");
            } catch (Exception e) {
                // Nothing :D
            }
        });

    }

    private boolean itemInSlot(Item item) {
        return invLocation.entrySet().stream()
                .anyMatch(x -> x.getValue().equals(item));
    }

    private int getItemSlot(Item item) {
        Map.Entry<Integer, Item> itemEntry = invLocation.entrySet().stream()
                .filter(x -> x.getValue().equals(item))
                .findFirst().orElse(null);

        return itemEntry != null ? itemEntry.getKey() : -1;
    }

}
