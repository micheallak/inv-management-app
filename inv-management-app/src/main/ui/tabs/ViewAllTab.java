package ui.tabs;

import model.Store;
import persistence.Writer;
import ui.InventoryUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// SOURCES THAT HELPED:
//         https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
//         https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
// represents view all tab and panels added to it
public class ViewAllTab extends Tab {
    private static DefaultTableModel tableModel;
    private DataTable dataTable;
    private JPanel newPanel;
    private JScrollPane scrollPane;
    private JButton loadButton;
    private Store stores;
    private static final String JSON_STORE = "./data/store.json";

// Constructs View All tab that shows the JTable, and associated buttons
    public ViewAllTab(InventoryUI control) {
        super(control);
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Inventory",
                TitledBorder.CENTER, TitledBorder.TOP));
        dataTable = control.getDataTable();
        tableModel = control.getDataTable().getDefaultTableModel();
        JTable table = dataTable.getTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        newPanel = new JPanel(new GridLayout(10,10));
        this.add(newPanel, BorderLayout.SOUTH);
        buttonToRemove();
        sortBy();
        saveButton();
        placeLoadButton();
    }

    // EFFECTS: creates buttons to sort by name and status
    private void sortBy() {
        JLabel empty = new JLabel("");
        empty.setBounds(325, 300, 100, 30);
        newPanel.add(empty);

        JLabel label = new JLabel("Sort by:");
        label.setBounds(230, 350, 100, 30);
        newPanel.add(label);

        sortButton();
        sortNameButton();

    }

    // MODIFIES: this
    // EFFECTS: sorts rows under status column
    public void sortButton() {
        JButton sortButton = new JButton("Status");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataTable.sortStatus();
            }
        });
        newPanel.add(sortButton);
    }

    // MODIFIES: this
    // EFFECTS: sorts rows under name column
    public void sortNameButton() {
        JButton nameButton = new JButton("Name");
        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataTable.sortName();
            }
        });
        newPanel.add(nameButton);
    }

    // EFFECTS: creates clickable load button
    private void placeLoadButton() {
        JLabel empty = new JLabel("");
        empty.setBounds(325, 300, 100, 30);
        newPanel.add(empty);

        loadButton = new JButton("Load");
        loadFromSavedFile();
        newPanel.add(loadButton);
    }

    // EFFECTS: loads inventory from file
    public void loadFromSavedFile() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataTable.loadStore();
                    scrollPane.setViewportView(dataTable.getTable());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // EFFECTS: creates button that will remove selected row from table, also
    //          adds an image to the button
    private void buttonToRemove() {
        JLabel label = new JLabel("Click to remove:");
        label.setBounds(230, 350, 100, 30);
        newPanel.add(label);
        ImageIcon buttonImage = new ImageIcon("./data/deleteX.png");
        JButton removeButton = new JButton(buttonImage);
        removeButton.setBounds(100,100,100,100);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataTable.removeRow();
            }
        });
        newPanel.add(removeButton);
    }

    // SOURCES: helpful video https://www.youtube.com/watch?v=tg7M9YvYDqo
    // EFFECTS: save button that will save data as JSON file
    private void saveButton() {
        JLabel empty = new JLabel("");
        empty.setBounds(325, 300, 100, 30);
        newPanel.add(empty);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAsJson();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        newPanel.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: uses JSON writer to convert data to JSON objects
    private void saveAsJson() throws IOException {
        Writer writer = new Writer(JSON_STORE);
        stores = dataTable.getStore();
        try {
            writer.open();
            writer.write(stores);
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error");
        }
    }


}


