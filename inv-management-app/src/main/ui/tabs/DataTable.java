package ui.tabs;

import model.EventLog;
import model.Item;
import model.Store;
import persistence.Reader;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;

// Represents data from store inventory
public class DataTable {
    private static final String JSON_STORE = "./data/store.json";
    private Store store;
    private DefaultTableModel defaultTableModel;
    private Reader reader;
    private JTable table;
    private int rowInt;
    private Object[][] data;

    // EFFECTS: constructs DataTable
    public DataTable() {
        store = new Store("store 2", 1001);
        data = new Object[6][10];
        invData();
        populateData();
        table();

    }

    // MODIFIES: this
    // EFFECTS: adds items initialized to store inventory
    public void invData() {
        Item i10 = new Item(1001, "Sweater", "black", "1",10, "in stock");
        Item i11 = new Item(1003, "Sweater", "black", "3", 9, "in stock");
        Item i12 = new Item(1102, "Jeans", "blue", "2", 0, "out of stock");
        Item i13 = new Item(1103, "Jeans", "blue", "3", 15, "in stock");

        store.addItem(i10, false);
        store.addItem(i11, false);
        store.addItem(i12, false);
        store.addItem(i13, false);

    }

    // SOURCE: https://stackoverflow.com/questions/20012772/how-to-populate-a-jtable-from-an-arraylits
    // REQUIRES: inventory size not null
    // MODIFIES: this
    // EFFECTS: populates data from store inventory to Object[][] to be added to table
    private void populateData() {
        int i = 0;
        if (store.getInventory().size() != 0) {
            for (Item item : store.getInventory()) {
                data[i][0] = item.getItemCode();
                data[i][1] = item.getName();
                data[i][2] = item.getColour();
                data[i][3] = item.getSize();
                data[i][4] = item.getStockCount();
                data[i][5] = item.getStatus();
                i++;
            }
        }
    }

    // EFFECTS: returns JTable of data from store
    private JTable table() {
        String[] columnsNames = {"Item Code", "Name", "Colour", "Item Size", "Stock Count", "Status"};

        defaultTableModel = new DefaultTableModel(data, columnsNames);
        defaultTableModel.setColumnIdentifiers(columnsNames);
        tableSettings();

        return table;
    }

    // EFFECTS: sets settings for table
    public void tableSettings() {
        table = new JTable(defaultTableModel);
        selectRow();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setEnabled(true);
    }



    // Source: https://stackoverflow.com/questions/5520548/is-there-anyway-i-can-highlight-a-row-in-jtable
    // MODIFIES: this
    // EFFECTS: highlights selected row in pink
    public void selectRow() {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selection = table.getSelectedRow();
                    table.setSelectionBackground(Color.PINK);
//                    table.setRowSelectionInterval(selection, selection);
                }
            }
        });
    }

    // SOURCE: for toggleSortOrder - https://stackoverflow.com/questions/63364682/how-can-i-sort-jtable-with-a-button
    // EFFECTS: sorts cells under column 5
    public void sortStatus() {
        table.setAutoCreateRowSorter(true);
        table.getRowSorter().toggleSortOrder(5);
        store.tableSortedByStatus();
    }

    // SOURCE: for toggleSortOrder - https://stackoverflow.com/questions/63364682/how-can-i-sort-jtable-with-a-button
    // EFFECTS: sorts cells under column 2
    public void sortName() {
        table.setAutoCreateRowSorter(true);
        table.getRowSorter().toggleSortOrder(2);
        store.tableSortedByName();
    }

    // SOURCE: https://stackoverflow.com/questions/23465295/remove-a-selected-row-from-jtable-on-button-click
    //         https://stackoverflow.com/questions/29345792/java-jtable-getting-the-data-of-the-selected-row
    //         https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#:~:text=To%20retrieve%20the%20
    //         current%20selection,for%20the%20table's%20column%20model.
    // MODIFIES: this
    // EFFECTS: removes selected row from table model and store
    public void removeRow() {
        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        Item selectedItem = null;

        if (defaultTableModel.getRowCount() > -1) {
//            rowInt = table.getSelectedRow();
            rowInt = table.convertRowIndexToModel(table.getSelectedRow());
            Object rowData = defaultTableModel.getValueAt(rowInt, 0);
            String item = rowData.toString();
            int code = Integer.parseInt(item);

            for (Item i : store.getInventory()) {
                if (i.getItemCode() == code) {
                    selectedItem = i;
                }
            }
            if (selectedItem != null && store.getInventory().contains(selectedItem)) {
                store.removeItem(selectedItem);
            }
            defaultTableModel.removeRow(modelRow);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds row to store and table
    public void addNewRow(Item i) {
        store.addItem(i, true);
        int code = i.getItemCode();
        String name = i.getName();
        String col = i.getColour();
        String siz = i.getSize();
        int count = i.getStockCount();
        String status = i.getStatus();
        Object[] row = {code, name, col, siz, count, status};
        populateData();
        defaultTableModel.addRow(row);
    }


    // MODIFIES: this
    // EFFECTS: loads inventory from file
    public void loadStore() throws IOException {
        reader = new Reader(JSON_STORE);
        store = reader.read();
        populateData();
        table();
    }

    // EFFECTS: returns JTable
    public JTable getTable() {
        return table;
    }

    // EFFECTS: returns store
    public Store getStore() {
        return store;
    }

    // EFFECTS: returns default table model
    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

}
