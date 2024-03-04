package ui;

import javax.swing.*;

import model.Event;
import model.EventLog;
import ui.tabs.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//SOURCE: modelled after SmartHomeUI (problem bank)
// represents inventory application which displays different tabs and jpanels
public class InventoryUI extends JFrame {
    public static final int MAIN_MENU_INDEX = 0;
    public static final int ADD_TO_INDEX = 1;
    public static final int VIEW_ALL_INDEX = 2;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    private JLabel statusLabel;
    private static final String STATUS_WORKS = "Now accessing inventory data";
    private JTabbedPane sidebar;
    private DataTable dataTable;

    // runs inventory application
    public static void main(String[] args) {
        InventoryUI invUI = new InventoryUI();
        invUI.setVisible(true);
    }


    //SOURCE: SmartHomeUI (problem bank), Intersection GUI
    //        Try/catch block modelled after JsonSerializationDemo Main method
    //        https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: creates InventoryUI, loads inventory data, displays tabs
    private InventoryUI() {
        super("Inventory Data UI");
        JPanel panel = new JPanel();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        statusLabel = new JLabel(STATUS_WORKS);
        add(statusLabel);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.TOP);

        loadTabs();
        add(sidebar);

        setVisible(true);
        closeWindow();

    }


    // SOURCE: SmartHomeUI (problem bank)
    // EFFECTS: adds tabs to UI
    private void loadTabs() {
        dataTable = new DataTable();
        JPanel inventoryTab = new UpdateItemsTab(this);
        JPanel menuTab = new MainMenu(this);
        JPanel viewAll = new ViewAllTab(this);

        sidebar.add(menuTab, MAIN_MENU_INDEX);
        sidebar.setTitleAt(MAIN_MENU_INDEX, "Main Menu");
        sidebar.add(inventoryTab, ADD_TO_INDEX);
        sidebar.setTitleAt(ADD_TO_INDEX, "Add items");
        sidebar.add(viewAll, VIEW_ALL_INDEX);
        sidebar.setTitleAt(VIEW_ALL_INDEX, "View all");
    }

    // EFFECTS: returns sidebar of UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    // EFFECTS: returns data table
    public DataTable getDataTable() {
        return dataTable;
    }

    // SOURCES:
    //        https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html
    //        https://stackoverflow.com/questions/60516720/java-how-to-print-message-when-a-jframe-is-closed
    // EFFECTS: display event log after closing window
    public void closeWindow() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    // SOURCE: Alarm System printLog method
    // EFFECTS: prints out event log for each event
    public void printLog(EventLog e) {
        for (Event next : e) {
            System.out.println("\n" + next.toString());
        }
    }
}
