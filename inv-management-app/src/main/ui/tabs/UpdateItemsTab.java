package ui.tabs;

import model.Item;
import model.Store;
import ui.InventoryUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// SOURCES:
//         https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
//         https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
//         https://stackoverflow.com/questions/21110926/put-jlabels-and-jtextfield-in-same-frame-as-jtable
// represents add items tab
public class UpdateItemsTab extends Tab {
    private JTextField itemCode;
    private JTextField itemName;
    private JTextField colour;
    private JTextField size;
    private JTextField stock;
    private JTextField status;
    private static DefaultTableModel tableModel;
    private DataTable dataTable;
    private JPanel newPanel;
    private Store stores;

    // constructor creates add items tab and displays form to add new item to data
    public UpdateItemsTab(InventoryUI control) {
        super(control);
        dataTable = control.getDataTable();
        stores = dataTable.getStore();
        newPanel = new JPanel(new GridLayout(10,10));
        tableModel = control.getDataTable().getDefaultTableModel();
        newLabel();
        enterItemCode();
        enterItemName();
        enterColour();
        enterSize();
        enterCount();
        enterStatus();
        addButton();
        emptySpace();
        emptySpace();
        this.add(newPanel, BorderLayout.SOUTH);
    }


    // EFFECTS: creates label and a text field for input
    private void enterItemCode() {
        JLabel label = new JLabel("Item Code");
        label.setBounds(250, 0, 100, 25);
        newPanel.add(label);

        itemCode = new JTextField();
        itemCode.setBounds(250, 250, 150, 25);
        newPanel.add(itemCode);
    }

    // EFFECTS: creates label and a text field for input
    private void enterItemName() {
        JLabel label = new JLabel("Name");
        label.setBounds(325, 300, 100, 30);
        newPanel.add(label);

        itemName = new JTextField();
        itemName.setBounds(320, 300, 150, 30);
        newPanel.add(itemName);
    }

    // EFFECTS: creates label and a text field for input
    private void enterColour() {
        JLabel label = new JLabel("Colour");
        label.setBounds(230, 350, 100, 30);
        newPanel.add(label);

        colour = new JTextField();
        colour.setBounds(320, 350, 150, 30);
        newPanel.add(colour);
    }

    // EFFECTS: creates label and a text field for input
    private void enterSize() {
        JLabel label = new JLabel("Size");
        label.setBounds(230, 400, 100, 30);
        newPanel.add(label);

        size = new JTextField();
        size.setBounds(320, 400, 150, 30);
        newPanel.add(size);
    }

    // EFFECTS: creates label and a text field for input
    private void enterCount() {
        JLabel label = new JLabel("Stock count");
        label.setBounds(230, 450, 100, 30);
        newPanel.add(label);

        stock = new JTextField();
        stock.setBounds(320, 450, 150, 30);
        newPanel.add(stock);
    }

    // EFFECTS: creates label and a text field for input
    private void enterStatus() {
        JLabel label = new JLabel("In/Out of stock?");
        label.setBounds(230, 450, 100, 30);
        newPanel.add(label);

        status = new JTextField();
        status.setBounds(320, 450, 150, 30);
        newPanel.add(status);
    }

    // EFFECTS: creates empty space in panel
    private void emptySpace() {
        JLabel label = new JLabel("");
        label.setBounds(325, 300, 100, 30);
        newPanel.add(label);
    }

    // MODIFIES: this
    // EFFECTS: creates add button which will add item to table and store when clicking button
    private void addButton() {
        emptySpace();

        JButton add = new JButton("Add item");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int code = Integer.parseInt(itemCode.getText());
                String name = itemName.getText();
                String col = colour.getText();
                String siz = size.getText();
                int count = Integer.parseInt(stock.getText());
                String stat = status.getText();

                Item i = new Item(code, name, col, siz, count, stat);

                dataTable.addNewRow(i);
                //addNew(tableModel, code, name, col, siz, count,status);
                clearText();

            }
        });
        newPanel.add(add);
        add.setBounds(300, 300, 100, 20);
    }


    // EFFECTS: clears text fields
    private void clearText() {
        itemCode.setText("");
        itemName.setText("");
        colour.setText("");
        size.setText("");
        stock.setText("");
        status.setText("");
    }

    // EFFECTS: creates label
    private void newLabel() {
        JLabel label = new JLabel("Enter new item details:");
        label.setBounds(230, 450, 100, 30);
        newPanel.add(label);

        JLabel label2 = new JLabel("");
        label2.setBounds(230, 450, 100, 30);
        newPanel.add(label2);
    }
}
