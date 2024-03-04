package model;

import org.json.JSONObject;
import persistence.Writable;

public class Item implements Writable {
    private final int itemCode;
    private final String name;
    private final String colour;
    private final String size;
    private int stockCount;
    private String status;

    // EFFECTS: constructs an item with the item code, name, colour, size, stock count, status
    public Item(int code, String nm, String colour, String size, int stockCount, String status) {
        this.itemCode = code;
        this.name = nm;
        this.colour = colour;
        this.size = size;
        this.stockCount = stockCount;
        this.status = status;
    }

    // getters
    public int getItemCode() {
        return itemCode;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public String getSize() {
        return size;
    }

    public int getStockCount() {
        return stockCount;
    }

    public String getStatus() {
        return status;
    }

    // setters
    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // MODIFIES: this
    // EFFECTS: if item stock count < 0, sets stock count to 0 and returns false, true otherwise;
    //          false indicates item is not in stock, true indicates item is in stock
    public boolean isInStock() {
        if (this.stockCount < 0) {
            setStockCount(0);
            return false;
        } else {
            return this.stockCount != 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the status of item based on the stock count;
    //          status updated to "in stock" if stock count is > 0
    //          status updated to "out stock" if stock count is = 0
    //          status updated to "invalid" if stock count is < 0
    public String statusUpdate() {
        if (this.stockCount > 0) {
            setStatus("in stock");
        } else if (this.stockCount == 0) {
            setStatus("out of stock");
        } else {
            setStatus("invalid");
        }
        return getStatus();
    }

    // SOURCE: Modelled after JsonSerializationDemo toJson() method
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemCode", itemCode);
        json.put("name", name);
        json.put("colour", colour);
        json.put("size", size);
        json.put("stockCount", stockCount);
        json.put("status", status);
        return json;
    }


}
