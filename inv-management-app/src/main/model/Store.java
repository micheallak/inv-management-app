package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Store implements Writable {

    private final String storeName;
    private final int locationNumber;
    private final List<Item> inventory;

    // EFFECTS: constructs a store with a store name, location number
    //          and has an empty list of inventory
    public Store(String nm, int loc) {
        this.storeName = nm;
        this.locationNumber = loc;
        this.inventory = new ArrayList<>();
    }

    // getters
    public String getStoreName() {
        return storeName;
    }

    public int getLocationNumber() {
        return locationNumber;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    // MODIFIES: this
    // EFFECTS: adds i to inventory item list
    public void addItem(Item i, Boolean log) {
        this.inventory.add(i);
        if (log) {
            EventLog.getInstance().logEvent(new Event("Successfully added new item"));
        }
    }

    // SOURCE: SocialMediaProfile(Problem Bank) - based on removeEvent method
    // MODIFIES: this
    // EFFECTS: remove specific item based on the same item code
    //          return true if item successfully removed, if not return false
    public boolean removeItem(Item i) {
        for (Item item : inventory) {
            if (item == i) {
                inventory.remove(i);
                EventLog.getInstance().logEvent(new Event("Successfully removed item"));
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: returns the total stock count of items in inventory
    public int stockCountTotal() {
        int total = 0;
        for (Item i : inventory) {
            if (i.isInStock()) {
                total += i.getStockCount();
            } else {
                total = 0;
            }
        }
        return total;
    }

    // MODIFIES: this
    // EFFECTS: returns the new stock count of specific item i and increases the stock count
    //          by a value
    public int addToStockCount(Item i, int value) {
        int total = 0;
        for (Item item: inventory) {
            if (item == i) {
                total = i.getStockCount();
                i.setStockCount(total += value);
            } else {
                total = i.getStockCount();
            }
        }
        return total;
    }

    // MODIFIES: this
    // EFFECTS: returns the number of item types (different item code) with the status "in stock"
    public int numberOfTypesInStock() {
        int total = 0;
        for (Item i : inventory) {
            if (Objects.equals(i.statusUpdate(), "in stock")) {
                total++;
            }
        }
        return total;
    }

    // SOURCE: JsonSerializationDemo toJson method
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: return json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("storeName", storeName);
        json.put("locationNumber", locationNumber);
        json.put("inventory", invToJson());
        return json;
    }

    // SOURCE: JsonSerializationDemo thingiesToJson method
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns inventory as a JSON array
    private JSONArray invToJson() {
        JSONArray array = new JSONArray();

        for (Item i : inventory) {
            array.put(i.toJson());
        }
        return array;
    }

    // EFFECTS: creates event for when table is sorted by name
    public void tableSortedByName() {
        EventLog.getInstance().logEvent(new Event("Table sorted by name"));
    }

    // EFFECTS: creates event for when table is sorted by status
    public void tableSortedByStatus() {
        EventLog.getInstance().logEvent(new Event("Table sorted by status"));
    }
}



