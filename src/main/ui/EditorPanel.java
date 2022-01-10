package ui;

import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Panel that contains components that allow for recipe editing
public abstract class EditorPanel extends JPanel implements ActionListener {
    protected static final int NUM_COLUMNS = 30;
    protected JPanel panelAbove;
    protected JPanel panelSelected;
    protected JPanel panelBelow;

    protected JButton applyButton;
    protected Recipe recipe;
    protected int indexSelected;
    protected RecipeDisplayPanel recipeDisplayPanel;

    // REQUIRES: display panel is the calling display panel object
    // EFFECTS: sets recipe and display panel to the given recipe and display panel; sets index of selected
    //          list object to -1
    public EditorPanel(Recipe recipe, RecipeDisplayPanel recipeDisplayPanel) {
        this.recipe = recipe;
        this.recipeDisplayPanel = recipeDisplayPanel;
        indexSelected = -1;
        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets up the GUI
    private void setup() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        applyButton = new JButton("Apply changes");
        applyButton.addActionListener(this);
        setupFields();
        panelAbove = new JPanel();
        panelAbove.setLayout(new FlowLayout(FlowLayout.LEADING,0,5));
        panelSelected = new JPanel();
        panelSelected.setLayout(new FlowLayout(FlowLayout.LEADING,0,5));
        panelBelow = new JPanel();
        panelBelow.setLayout(new FlowLayout(FlowLayout.LEADING,0,5));
        setupPanels();
        add(panelAbove);
        add(panelSelected);
        add(panelBelow);
        add(applyButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up the text fields
    abstract void setupFields();

    // MODIFIES: this
    // EFFECTS: adds text fields to the panels that contain them
    abstract void setupPanels();

    // MODIFIES: this
    // EFFECTS: reruns setup, validates and repaints this, refreshes the recipe display panel
    public void refresh() {
        removeAll();
        setup();
        validate();
        repaint();
        recipeDisplayPanel.refresh();
    }
}
