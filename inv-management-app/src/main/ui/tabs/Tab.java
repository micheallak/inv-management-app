package ui.tabs;

import ui.InventoryUI;

import javax.swing.*;
import java.awt.*;

// Source: SmartHomeUI(Problem Bank) - based on Tabs class
// represents tabs of different jpanels
public abstract class Tab extends JPanel {
    private final InventoryUI control;

    // REQUIRES: InventoryUI control that holds the tab
    // constructor creates a tab
    public Tab(InventoryUI control) {
        this.control = control;
    }

    // EFFECTS: creates and returns rows with buttons
    public JPanel buttons(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    // EFFECTS: return InventoryUI control for tab
    public InventoryUI getControl() {
        return control;
    }
}
