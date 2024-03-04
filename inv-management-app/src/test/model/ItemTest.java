package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private Item testItem;

    @BeforeEach
    public void runBefore() {

        testItem = new Item(1000, "Sweater", "black", "1", 1, "in stock");
    }

    @Test
    public void testConstructor() {
        assertEquals(1000, testItem.getItemCode());
        assertEquals("Sweater", testItem.getName());
        assertEquals("black", testItem.getColour());
        assertEquals("1", testItem.getSize());
        assertEquals("in stock", testItem.getStatus());
    }

    @Test
    public void testSetStockCnt() {
        testItem.setStockCount(2);
        assertEquals(2, testItem.getStockCount());
        assertEquals("in stock", testItem.getStatus());
    }

    @Test
    public void testIsInStock() {
        assertTrue(testItem.isInStock());
        assertEquals(1, testItem.getStockCount());
    }

    @Test
    public void testNotInStock() {
        testItem.setStockCount(0);
        assertFalse(testItem.isInStock());
        assertEquals(0, testItem.getStockCount());
    }

    @Test
    public void testNegStockCount() {
        testItem.setStockCount(-5);
        assertFalse(testItem.isInStock());
        assertEquals(0, testItem.getStockCount());
    }

    @Test
    public void testStatusUpdate() {
        testItem.setStockCount(5);
        assertEquals(5, testItem.getStockCount());
        assertEquals("in stock", testItem.statusUpdate());
    }

    @Test
    public void testStatusUpdateZero() {
        testItem.setStockCount(0);
        assertEquals(0, testItem.getStockCount());
        assertEquals("out of stock", testItem.statusUpdate());
    }

    @Test
    public void testStatusUpdateNegVal() {
        testItem.setStockCount(-5);
        assertEquals(-5, testItem.getStockCount());
        assertEquals("invalid", testItem.statusUpdate());
    }

    @Test
    public void testToJSON() {
        testItem.toJson();
    }


}