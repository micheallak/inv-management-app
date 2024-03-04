package ui;

import model.Item;
import model.Store;
import persistence.Reader;
import persistence.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// Item lookup application
public class InventoryData {
    private static final String JSON_STORE = "./data/store.json";
    private List<Store> stores;
    private Store selectedStore;
    private Item selectedItem;
    private Scanner input;
    private Writer jsonWriter;
    private Reader jsonReader;

    // SOURCE: Modelled after JsonSerializationDemo JsonTest
    //        https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: runs inventory data/item lookup application
    public InventoryData() throws FileNotFoundException {
        jsonWriter = new Writer(JSON_STORE);
        jsonReader = new Reader(JSON_STORE);
        runData();
    }

    // SOURCE: TellerApp - runTeller method
    // MODIFIES: this
    // EFFECTS: process user inputs
    private void runData() {
        boolean run = true;
        String command = null;

        initializeInventory();
        System.out.println("\nSTORE LOOKUP TOOL\n");
        storeNum();

        while (run) {
            System.out.println("\nACCESSING STORE NUMBER " + selectedStore.getLocationNumber() + " INVENTORY");
            lookupOptions();
            command = input.next();

            if (command.equals("quit")) {
                run = false;
            } else {
                commands(command);
            }
        }
        System.out.println("\nThank you for using the lookup tool!");
    }

    // SOURCE: TellerApp - init method (new scanner and delimiter)
    // MODIFIES: this
    // EFFECTS: initializes store inventory
    private void initializeInventory() {
        stores = new ArrayList<>();

        Store store1 = new Store("Store2", 1001);

        Item i10 = new Item(1001, "Sweater", "black", "1",10, "in stock");
        Item i11 = new Item(1003, "Sweater", "black", "3", 9, "in stock");
        Item i12 = new Item(1102, "Jeans", "blue", "2", 5, "in stock");
        Item i13 = new Item(1103, "Jeans", "blue", "3", 2, "in stock");

        store1.addItem(i10, false);
        store1.addItem(i11, false);
        store1.addItem(i12, false);
        store1.addItem(i13, false);

        stores.add(store1);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: accesses inventory of a specific store number
    private void storeNum() {
        System.out.println("\tEnter store number:\n");
        int code = input.nextInt();
        for (Store s : stores) {
            if (s.getLocationNumber() == code) {
                selectedStore = s;
            }
        }
        if (selectedStore == null) {
            System.out.println("Invalid store number. Please try again.");
            storeNum();
        }
    }

    // SOURCE: TellerApp - based on displayMenu method
    // EFFECTS: lookup options user can select from
    private void lookupOptions() {
        System.out.println("\tEnter 1 to view all inventory");
        System.out.println("\tEnter 2 to view details of an item");
        System.out.println("\tEnter 3 to update stock count");
        System.out.println("\tEnter 4 to add/remove item");
        System.out.println("\tEnter save to save inventory to file");
        System.out.println("\tEnter load to load inventory to file");
        System.out.println("\tEnter quit to exit the program.");
    }

    // SOURCE: TellerApp - processCommand method
    // MODIFIES: this
    // EFFECTS: process lookup options
    private void commands(String command) {
        if (command.equals("1")) {
            viewAll();
        } else if (command.equals("2")) {
            itemDetails();
        } else if (command.equals("3")) {
            updateStock();
        } else if (command.equals("4")) {
            addOrRemove();
        } else if (command.equals("save")) {
            saveStore();
        } else if (command.equals("load")) {
            loadStore();
        } else {
            System.out.println("Try again...");
        }
    }

    // MODIFIES: this
    // EFFECTS: print out list of all items in the store inventory
    //          includes the item code, item name and stock count of item
    //          prints out total number of items in the store inventory at the bottom
    //          prints out number of different items
    private void viewAll() {
        System.out.println("\nItems in inventory:");
        for (Item i : selectedStore.getInventory()) {
            System.out.println("\tItem# " + i.getItemCode() + " " + i.getName());
            System.out.println("\t\tSTOCK COUNT: " + i.getStockCount() + "\n");
        }
        System.out.println("\nTotal number of items in inventory: " + selectedStore.stockCountTotal());
        System.out.println("Number of different items available: " + selectedStore.numberOfTypesInStock());
    }

    // REQUIRES: item must be in the inventory
    // MODIFIES: this
    // EFFECTS: details of specific item, includes item code, name
    //          colour, size, status and number of item available
    private void itemDetails() {
        selectedItem = null;

        System.out.println("\nPlease enter the item code:\n");
        int code = input.nextInt();
        for (Item i : selectedStore.getInventory()) {
            if (i.getItemCode() == code) {
                selectedItem = i;
                System.out.println("Item#: " + selectedItem.getItemCode());
                System.out.println("Name: " + selectedItem.getName());
                System.out.println("Colour: " + selectedItem.getColour());
                System.out.println("Size: " + selectedItem.getSize());
                System.out.println("Status: " + selectedItem.statusUpdate());
                System.out.println("Number of this item available in inventory: " + selectedItem.getStockCount());
            }
        }
        if (selectedItem == null) {
            System.out.println("Invalid item code. Please try again.");
            input.reset();
            itemDetails();
        }
    }

    // REQUIRES: item must be in the inventory
    // MODIFIES: this
    // EFFECTS: changes stock count of item
    private void updateStock() {
        selectedItem = null;

        System.out.println("\nPlease enter the item code:");
        int code = input.nextInt();
        for (Item i : selectedStore.getInventory()) {
            if (i.getItemCode() == code) {
                selectedItem = i;
                System.out.println("Item available? " + selectedItem.isInStock());
                System.out.println("Current stock count: " + selectedItem.getStockCount());
                manualOrAdd();
            }
        }
        if (selectedItem == null) {
            System.out.println("Sorry your request is invalid.");
            input.reset();
            updateStock();
        }
    }

    // EFFECTS: asks user to select between updating stock count value manually or adding to the existing amount
    public void manualOrAdd() {
        selectedItem = null;

        System.out.println("Would you like to manually update or enter a value to increase the stock count by?");
        System.out.println("\tEnter 1 to manually update");
        System.out.println("\tEnter 2 to increase the stock count by value");
        String ans = input.next();

        if (Objects.equals(ans, "1")) {
            manualEntry();
        } else if (Objects.equals(ans, "2")) {
            addToExisting();
        } else {
            manualOrAdd();
        }
        input.reset();
    }

    // REQUIRES: item must be in the inventory
    // MODIFIES: this
    // EFFECTS: update stock count of item to value inputted
    public void manualEntry() {
        verifying();
        if (selectedStore.getInventory().contains(selectedItem)) {
            System.out.println("Enter a value to set the stock count to:");
            int ans = input.nextInt();
            if (ans >= 0) {
                selectedItem.setStockCount(ans);
                System.out.println("Successfully updated stock count:");
                System.out.println("\tItem# " + selectedItem.getItemCode() + ": " + selectedItem.getStockCount());
            } else {
                System.out.println("We are unable to process your request.");
            }
        }
        input.reset();
    }

    // REQUIRES: item must be in the inventory
    // MODIFIES: this
    // EFFECTS: increases total stock count by value inputted
    public void addToExisting() {
        verifying();
        if (selectedStore.getInventory().contains(selectedItem)) {
            System.out.println("Enter a value to increase current stock count by:");
            int ans = input.nextInt();
            if (ans >= 0) {
                selectedStore.addToStockCount(selectedItem, ans);
                System.out.println("Successfully updated stock count:");
                System.out.println("\tItem# " + selectedItem.getItemCode() + ": " + selectedItem.getStockCount());
            } else {
                System.out.println("We are unable to process your request.");
            }
        }
        input.reset();
    }

    // MODIFIES: this
    // EFFECTS: user verifies selected item code, checks to ensure item code exists in inventory
    public void verifying() {
        selectedItem = null;

        System.out.println("Verifying...");
        System.out.println("Please re-enter the item code to confirm.");
        int code = input.nextInt();
        for (Item i : selectedStore.getInventory()) {
            if (i.getItemCode() == code) {
                selectedItem = i;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user to select between adding an item to inventory or removing
    public void addOrRemove() {
        System.out.println("Would you like to add or remove an item?");
        System.out.println("\tEnter 1 to add an item");
        System.out.println("\tEnter 2 to remove an item");
        String ans = input.next();

        if (Objects.equals(ans, "1")) {
            addNew();
        } else if (Objects.equals(ans, "2")) {
            removal();
        } else {
            addOrRemove();
        }
        input.reset();
    }

    // MODIFIES: this
    // EFFECTS: add new item to inventory
    private void addNew() {
        System.out.println("Please enter the item code.\n");
        int code = input.nextInt();
        System.out.println("Please enter the name.\n");
        String name = input.next();
        System.out.println("Please enter the colour.\n");
        String col = input.next();
        System.out.println("Please enter the size.\n");
        String siz = input.next();
        Item i = new Item(code, name, col, siz, 1, "in stock");
        selectedStore.addItem(i, true);
        System.out.println("Item successfully added.");
    }

    // REQUIRES: item must exist in the inventory
    // MODIFIES: this
    // EFFECTS: remove item from inventory
    public void removal() {
        selectedItem = null;

        System.out.println("Please enter the item code.\n");
        int code = input.nextInt();
        for (Item i : selectedStore.getInventory()) {
            if (i.getItemCode() == code) {
                selectedItem = i;
            }
        }
        if (selectedStore.getInventory().contains(selectedItem)) {
            itemRemoval();
        } else {
            System.out.println("\nRequest is invalid. Please enter a pre-existing item in the inventory.\n");
            removal();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user to confirm the correct item that they would like removed
    //          from the inventory, then removes the item if it is verified to be correct
    public void itemRemoval() {
        System.out.println("Confirm item: " + selectedItem.getItemCode() + " " + selectedItem.getName());
        System.out.println("\tEnter y for yes");
        System.out.println("\tEnter n for no");
        String ans = input.next();
        if (Objects.equals(ans, "y")) {
            selectedStore.removeItem(selectedItem);
            System.out.println("Item successfully removed.");
        } else if (Objects.equals(ans, "n")) {
            input.reset();
            removal();
        } else {
            System.out.println("Request is invalid. Please try again.");
            removal();
        }
        input.reset();
    }

    // SOURCE: Modelled after saveWorkRoom method from JsonSerializationDemo
    // EFFECTS: saves inventory to file
    public void saveStore() {
        try {
            jsonWriter.open();
            jsonWriter.write(selectedStore);
            jsonWriter.close();
            System.out.println("Saved " + selectedStore.getLocationNumber() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save file: " + JSON_STORE);
        }
    }

    // SOURCE: Modelled after loadWorkRoom method from JsonSerializationDemo
    // EFFECTS: loads inventory from file
    public void loadStore() {
        try {
            selectedStore = jsonReader.read();
            System.out.println("Successfully loaded " + selectedStore.getLocationNumber() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}