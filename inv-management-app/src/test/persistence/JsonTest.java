package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

// SOURCE: Modelled after JsonSerializationDemo JsonTest
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkItem(int itemCode, String name, String colour, String size, int stockCount, Item item) {
        assertEquals(itemCode, item.getItemCode());
        assertEquals(name, item.getName());
        assertEquals(colour, item.getColour());
        assertEquals(size, item.getSize());
        assertEquals(stockCount, item.getStockCount());
    }

}

