package dk.sdu.mmmi.swe20.t1.g3.Controllers;

import dk.sdu.mmmi.swe20.t1.g3.Models.Inventory;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;

import java.util.ArrayList;

public class InventoryController {
    private Inventory model;
    private ArrayList<Item> inv = new ArrayList<>();


    public InventoryController () {
        this.model = Inventory.getInstance();
    }

    public void loadInventory() {
        inv = model.getInventory();
    }

    public void saveInventory() {
        model.setInventory(inv);
    }


    public void addToInventory(Item i) {
        inv.add(i);
    }

    public void removeFromInventory(Item i) {
        inv.remove(i);
    }

    public boolean inventoryContains(Item i) {
        boolean inventoryContains = false;

        return inv.indexOf(i)==-1 ? true : false;
    }

    public ArrayList<Item> getInventory() {
        return inv;
    }

}
