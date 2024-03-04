package persistence;

import model.Store;
import org.json.JSONObject;
import java.io.*;

// SOURCE: Methods in this class modelled after JsonSerializationDemo JsonWriter class
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a writer that writes JSON representation of Store to file
public class Writer {
    private static final int TAB = 4;
    private PrintWriter writers;
    private String location;

    // EFFECTS: constructs writer to write data to file
    public Writer(String location) {
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Store to file
    public void write(Store s) {
        JSONObject json = s.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writers.print(json);
    }

    // MODIFIES: this
    // EFFECTS: opens writers and throws FileNotFoundException if destination file cant be opened
    public void open() throws FileNotFoundException {
        writers = new PrintWriter(new File(location));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writers.close();
    }

}
