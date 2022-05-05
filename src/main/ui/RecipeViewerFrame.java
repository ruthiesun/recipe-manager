package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Window that displays a recipe and allows for edits and other options
public class RecipeViewerFrame extends JFrame implements ActionListener {
    public static final int WIDTH = 700;
    public static final int HEIGHT = RecipeManagerApp.HEIGHT;
    private Recipe recipe;
    private JPanel viewingPanel;
    private JToolBar menu;
    private JButton makeFinalButton;
    private JButton newProjectWithRecipeButton;
    private JButton newDraftButton;
    private ProjectViewerPanel projectViewerPanel;

    // REQUIRES: viewer panel is the calling viewer panel object
    // EFFECTS: sets recipe and viewer panel to the given recipe and viewer panel
    public RecipeViewerFrame(Recipe recipe, ProjectViewerPanel projectViewerPanel) {
        super("Recipe");
        this.recipe = recipe;
        this.projectViewerPanel = projectViewerPanel;
        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets up the GUI
    public void setup() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH + 50,HEIGHT - 150));
        setLocationRelativeTo(null);
        setResizable(false);
        loadMenu();
        loadViewingPanel();
        add(menu,BorderLayout.NORTH);
        add(viewingPanel,BorderLayout.CENTER);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons
    private void makeButtons() {
        makeFinalButton = new JButton("Set as final draft");
        makeFinalButton.addActionListener(this);
        newDraftButton = new JButton("Make new draft");
        newDraftButton.addActionListener(this);
        newProjectWithRecipeButton = new JButton("Start new project with this draft");
        newProjectWithRecipeButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: creates the menu toolbar
    private void loadMenu() {
        menu = new JToolBar();
        menu.setPreferredSize(new Dimension(WIDTH,RecipeManagerApp.TOOLBAR_HEIGHT));
        menu.setLayout(new FlowLayout());
        makeButtons();
        menu.add(makeFinalButton);
        menu.add(newDraftButton);
        menu.add(newProjectWithRecipeButton);
        menu.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the viewing panel
    private void loadViewingPanel() {
        viewingPanel = new RecipeDisplayPanel(recipe);
    }

    // REQUIRES: action event source is a JButton
    // MODIFIES: this
    // EFFECTS: executes option selected
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedButton = (JButton) e.getSource();
        if (selectedButton == makeFinalButton) {
            projectViewerPanel.setFinal(recipe);
            refresh();
        } else if (selectedButton == newDraftButton) {
            projectViewerPanel.newDraft(recipe);
            refresh();
        } else if (selectedButton == newProjectWithRecipeButton) {
            projectViewerPanel.newProject(recipe);
            refresh();
        }
    }

    // MODIFIES: this
    // EFFECTS: validates and repaints this; validates and repaints the viewer panel
    public void refresh() {
        validate();
        repaint();
        projectViewerPanel.validate();
        projectViewerPanel.repaint();
    }
}
