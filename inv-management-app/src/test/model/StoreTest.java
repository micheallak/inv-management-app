package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    private Store testStore;

    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    public void runBefore() {
        testStore = new Store("Store1", 1000);

        item1 = new Item(1000, "Sweater", "black", "1", 1, "in stock");
        item2 = new Item(1001, "Sweater", "grey", "1", 2, "in stock");
        item3 = new Item(1000, "shirt", "red", "XS", -3, "invalid");
    }

    @Test
    public void testConstructor() {
        assertEquals("Store1", testStore.getStoreName());
        assertEquals(1000, testStore.getLocationNumber());
        assertEquals(0, testStore.getInventory().size());
    }

    @Test
    public void testAddItem() {
        testStore.addItem(item1, true);
        assertEquals(1, testStore.getInventory().size());
        assertEquals(item1, testStore.getInventory().get(0));
    }

    @Test
    public void testAddMultipleItems() {
        testStore.addItem(item1, true);
        testStore.addItem(item2, true);
        assertEquals(2, testStore.getInventory().size());
        assertEquals(item1, testStore.getInventory().get(0));
        assertEquals(item2, testStore.getInventory().get(1));
    }

    @Test
    public void testRemoveItem() {
        testStore.addItem(item1, true);
        assertEquals(1, testStore.getInventory().size());
        assertEquals(item1, testStore.getInventory().get(0));
        assertTrue(testStore.removeItem(item1));
        assertEquals(0, testStore.getInventory().size());
        assertFalse(testStore.getInventory().contains(item1));
    }

    @Test
    public void testNoRemoval() {
        testStore.addItem(item1, true);
        assertEquals(1, testStore.getInventory().size());
        assertEquals(item1, testStore.getInventory().get(0));
        testStore.removeItem(item2);
        assertEquals(1, testStore.getInventory().size());
    }

    @Test
    public void testMultipleRemovals() {
        testStore.addItem(item1, true);
        testStore.addItem(item2, true);
        assertTrue(testStore.removeItem(item1));
        assertTrue(testStore.removeItem(item2));
        assertEquals(0, testStore.getInventory().size());
        assertFalse(testStore.getInventory().contains(item1));
        assertFalse(testStore.getInventory().contains(item2));
    }

    @Test
    public void testCount() {
        testStore.addItem(item1, true);
        testStore.addItem(item2, true);
        assertEquals(3, testStore.stockCountTotal());
    }

    @Test
    public void testCountWithRemoval() {
        testStore.addItem(item1, true);
        testStore.addItem(item2, true);
        assertEquals(3, testStore.stockCountTotal());
        assertTrue(testStore.removeItem(item2));
        assertEquals(1, testStore.stockCountTotal());
    }

    @Test
    public void testCountWithAdd() {
        testStore.addItem(item1, true);
        testStore.addItem(item2, true);
        assertEquals(3, testStore.stockCountTotal());
        testStore.addItem(new Item(1000, "shirt", "red", "1", 4, "in stock"),
                true);
        assertEquals(7, testStore.stockCountTotal());
    }

    @Test
    public void testZeroCount() {
        testStore.addItem(new Item(10, "shirt", "red", "XS", 0, "out of stock"),
                true);
        assertEquals(1, testStore.getInventory().size());
        assertEquals(0, testStore.stockCountTotal());
    }

    @Test
    public void testInvalidCount() {
        testStore.addItem(new Item(1000, "shirt", "red", "XS", -2, "invalid"),
                true);
        assertEquals(1, testStore.getInventory().size());
        assertEquals(0, testStore.stockCountTotal());
    }

    @Test
    public void testAddStockCount() {
        testStore.addItem(item1, true);
        assertEquals(5, testStore.addToStockCount(item1, 4));
        assertEquals(5, testStore.stockCountTotal());
    }

    @Test
    public void testAddZeroStockCount() {
        testStore.addItem(item1, true);
        assertEquals(1, testStore.addToStockCount(item1, 0));
        assertEquals(1, testStore.stockCountTotal());
    }

    @Test
    public void testAddInvalidStockCount() {
        assertEquals(0, testStore.addToStockCount(item3, -2));
        assertEquals(0, testStore.stockCountTotal());
    }

    @Test
    public void testInvalidItem() {
        testStore.addItem(item1, true);
        assertEquals(2, testStore.addToStockCount(item2, 3));
        assertEquals(1, testStore.stockCountTotal());
    }

    @Test
    public void testBothInvalidInputs() {
        testStore.addItem(item2, true);
        assertEquals(1, testStore.addToStockCount(item1, -3));
        assertEquals(2, testStore.stockCountTotal());
    }

    @Test
    public void testNumberInStock() {
        testStore.addItem(item1, true);
        assertEquals(1, testStore.numberOfTypesInStock());
    }

    @Test
    public void testNumberInStockMultiple() {
        testStore.addItem(item1, true);
        testStore.addItem(item2, true);
        assertEquals(2, testStore.numberOfTypesInStock());
    }

    @Test
    public void testNumInStockNone() {
        testStore.addItem(new Item(1, "shirt", "red", "XS", 0, "out of stock"),
        true);
        assertEquals(0, testStore.numberOfTypesInStock());
    }

    @Test
    public void testNumInStockValidNumInvalidNum() {
        testStore.addItem(item1, true);
        testStore.addItem(item2, true);
        testStore.addItem(new Item(1, "shirt", "red", "XS", 0, "out of stock"),
                true);
        assertEquals(2, testStore.numberOfTypesInStock());
    }

    @Test
    public void testToJson() {
        testStore.toJson();
    }
}