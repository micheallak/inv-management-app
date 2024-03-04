package ui;

// Source: SmartHomeUI(Problem Bank) - based on ButtonNames class
// represents names of buttons
public enum ButtonNames {
    VIEW_ALL("View all inventory");

    private final String name;

    ButtonNames(String name) {
        this.name = name;
    }

    // EFFECTS: return name value of button
    public String getValue() {
        return name;
    }


}
