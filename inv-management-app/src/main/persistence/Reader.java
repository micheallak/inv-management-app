package persistence;

import model.Item;
import model.Store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// SOURCE: Methods in this class modelled after JsonSerializationDemo JsonReader class
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads Store from JSON data stored in the file
public class Reader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Store from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Store read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStore(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses Store from JSON object and returns it
    private Store parseStore(JSONObject jsonObject) {
        String name = jsonObject.getString("storeName");
        int locationNumber = jsonObject.getInt("locationNumber");
        Store s = new Store(name, locationNumber);
        addInventory(s, jsonObject);
        return s;
    }

    // MODIFIES: s
    // EFFECTS: parses inventory from JSON object and adds them to store
    private void addInventory(Store s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(s, nextItem);
        }
    }

    // MODIFIES: s
    // EFFECTS: parses item from JSON object and adds it to store
    private void addItem(Store s, JSONObject jsonObject) {
        int itemCode = jsonObject.getInt("itemCode");
        String name = jsonObject.getString("name");
        String colour = jsonObject.getString("colour");
        String size = jsonObject.getString("size");
        int stockCount = jsonObject.getInt("stockCount");
        String status = jsonObject.getString("status");
        Item item = new Item(itemCode, name, colour, size, stockCount, status);
        s.addItem(item, false);
    }
}
