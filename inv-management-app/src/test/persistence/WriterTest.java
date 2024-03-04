package persistence;

import model.Item;
import model.Store;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest extends JsonTest {
    // SOURCE: Modelled after JsonSerializationDemo JsonWriterTest
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testWriterInvalidFile() {
        try {
            Store s = new Store("store1", 1000);
            Writer writer = new Writer("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStore() {
        try {
            Store s = new Store("store1", 1000);
            Writer writer = new Writer("./data/testWriterEmptyStore.json");
            writer.open();
            writer.write(s);
            writer.close();

            Reader reader = new Reader("./data/testWriterEmptyStore.json");
            s = reader.read();
            assertEquals("store1", s.getStoreName());
            assertEquals(0, s.getInventory().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGenStore() {
        try {
            Store s = new Store("store1", 1000);
            s.addItem(new Item(10, "shirt", "black", "s", 1, "in stock"),
                    true);
            s.addItem(new Item(20, "shirt", "black", "m", 1, "in stock"),
                    true);
            Writer writer = new Writer("./data/testWriterGenStore.json");
            writer.open();
            writer.write(s);
            writer.close();

            Reader reader = new Reader("./data/testWriterGenStore.json");
            s = reader.read();
            assertEquals("store1", s.getStoreName());
            List<Item> items = s.getInventory();
            assertEquals(2, items.size());
            checkItem(10, "shirt", "black", "s", 1, items.get(0));
            checkItem(20, "shirt", "black", "m", 1, items.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
