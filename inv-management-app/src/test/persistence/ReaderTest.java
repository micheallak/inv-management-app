package persistence;

import model.Item;
import model.Store;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// SOURCE: Modelled after JsonSerializationDemo JsonReaderTest
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class ReaderTest extends JsonTest {

    @Test
    void testReaderNoFile() {
        Reader reader = new Reader("./data/noSuchFile.json");
        try {
            Store s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStore() {
        Reader reader = new Reader("./data/testReaderEmptyStore.json");
        try {
            Store s = reader.read();
            assertEquals("store1", s.getStoreName());
            assertEquals(0, s.stockCountTotal());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGenStore() {
        Reader reader = new Reader("./data/testReaderGenStore.json");
        try {
            Store s = reader.read();
            assertEquals("store1", s.getStoreName());
            List<Item> items = s.getInventory();
            assertEquals(2, items.size());
            checkItem(10, "shirt", "black", "s", 1, items.get(0));
            checkItem(20, "shirt", "black", "m", 1, items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
