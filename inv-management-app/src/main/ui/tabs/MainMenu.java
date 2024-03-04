package ui.tabs;

import ui.ButtonNames;
import ui.InventoryUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Source: SmartHomeUI (problem bank) - modelled after HomeTab
// Represents main menu tab in the GUI
public class MainMenu extends Tab {
    private DataTable dataTable;

    // constructor creates main menu tab and adds image and button to view all
    public MainMenu(InventoryUI control) {
        super(control);
        image();
        dataTable = control.getDataTable();
        placeInvButton();

    }

    // EFFECTS: display image on main menu panel
    public void image() {
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("./data/mainmenu.png"));

        this.add(label);
    }

    // EFFECTS: constructs inventory button and switches to inventory tab on console
    public void placeInvButton() {
        JPanel inventoryBlock = new JPanel();
        JButton inventoryButton = new JButton(ButtonNames.VIEW_ALL.getValue());
        inventoryBlock.add(buttons(inventoryButton));

        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(ButtonNames.VIEW_ALL.getValue())) {
                    getControl().getTabbedPane().setSelectedIndex(InventoryUI.VIEW_ALL_INDEX);
                }
            }
        });

        this.add(inventoryButton);
    }


}
