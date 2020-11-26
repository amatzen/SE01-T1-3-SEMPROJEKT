package dk.sdu.mmmi.swe20.t1.g3.Models;

import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;

import java.util.ArrayList;

// https://www.geeksforgeeks.org/singleton-class-java/
public class Inventory {
    private static Inventory singleInstance = null;

    private ArrayList<Item> inventory;

    private Inventory () {
        inventory = new ArrayList<Item>();
    }

    public static Inventory getInstance()
    {
        if (singleInstance == null)
            singleInstance = new Inventory();

        return singleInstance;
    }

    // Set
    public void setInventory(ArrayList<Item> inventory) { this.inventory = inventory; }

    // Get
    public ArrayList<Item> getInventory() { return inventory; }
}
