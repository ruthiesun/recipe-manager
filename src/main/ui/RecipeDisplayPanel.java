package ui;

import model.Ingredient;
import model.Instruction;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// Panel that displays a recipe
public class RecipeDisplayPanel extends JPanel {
    public static final Dimension HALF_PANEL_DIMENSION = new Dimension(RecipeViewerFrame.WIDTH / 2,
            RecipeViewerFrame.HEIGHT / 2);
    private Recipe recipe;
    private JPanel ingredientPanel;
    private JPanel instructionPanel;
    private JList<String> ingredientList;
    private JList<String> instructionList;
    private InstructionEditorPanel instructionEditorPanel;
    private IngredientEditorPanel ingredientEditorPanel;
    private MouseListener mouseListenerInstruction = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                JList<String> selection = (JList<String>) e.getSource();
                int selectedIndex = selection.getSelectedIndex();
                Instruction selectedInstruction = recipe.getInstructionList().getInstructions().get(selectedIndex);
                instructionEditorPanel.updateFieldSelected(selectedInstruction,selectedIndex);
            } catch (ArrayIndexOutOfBoundsException exception) {
                //do nothing
            }
        }
    };
    private MouseListener mouseListenerIngredient = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                JList<String> selection = (JList<String>) e.getSource();
                int selectedIndex = selection.getSelectedIndex();
                Ingredient selectedIngredient = recipe.getIngredientList().getIngredients().get(selectedIndex);
                ingredientEditorPanel.updateFieldSelected(selectedIngredient,selectedIndex);
            } catch (ArrayIndexOutOfBoundsException exception) {
                //do nothing
            }
        }
    };

    // EFFECTS: sets recipe to given recipe
    public RecipeDisplayPanel(Recipe recipe) {
        this.recipe = recipe;
        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets up the GUI
    private void setup() {
        ingredientPanel = new JPanel();
        instructionPanel = new JPanel();
        ingredientEditorPanel = new IngredientEditorPanel(recipe,this);
        instructionEditorPanel = new InstructionEditorPanel(recipe,this);
        setPreferredSize(new Dimension(RecipeViewerFrame.WIDTH, RecipeViewerFrame.HEIGHT));
        setLayout(new FlowLayout());
        makeIngredientsPanel();
        makeInstructionsPanel();
        add(ingredientPanel);
        add(instructionPanel);
    }

    // MODIFIES: this
    // EFFECTS: constructs the panel that displays instructions
    private void makeInstructionsPanel() {
        setupHalfPanels(instructionPanel,"Instructions");
        String[] instructions = recipe.getInstructionsAsStrings().toArray(new String[0]);

        instructionList = new JList<>(instructions);
        instructionList.addMouseListener(mouseListenerInstruction);
        instructionList.setLayoutOrientation(JList.VERTICAL);
        instructionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScroller = new JScrollPane(instructionList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScroller.setPreferredSize(HALF_PANEL_DIMENSION);
        listScroller.setAlignmentX(RIGHT_ALIGNMENT);

        instructionPanel.add(listScroller,BorderLayout.CENTER);
        instructionPanel.add(instructionEditorPanel,BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: constructs the panel that displays ingredients
    private void makeIngredientsPanel() {
        setupHalfPanels(ingredientPanel,"Ingredients");
        String[] ingredients = recipe.getIngredientsAsStrings().toArray(new String[0]);

        ingredientList = new JList<>(ingredients);
        ingredientList.addMouseListener(mouseListenerIngredient);
        ingredientList.setLayoutOrientation(JList.VERTICAL);
        ingredientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScroller = new JScrollPane(ingredientList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScroller.setPreferredSize(HALF_PANEL_DIMENSION);
        listScroller.setAlignmentX(RIGHT_ALIGNMENT);

        ingredientPanel.add(listScroller,BorderLayout.CENTER);
        ingredientPanel.add(ingredientEditorPanel,BorderLayout.SOUTH);
    }

    // MODIFIES: panel
    // EFFECTS: sets up given panel and creates a label for it with title
    private void setupHalfPanels(JPanel panel,String title) {
        panel.setPreferredSize(HALF_PANEL_DIMENSION);
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(title);
        panel.add(label,BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: reruns setup and validates and repaints this
    public void refresh() {
        removeAll();
        setup();
        validate();
        repaint();
    }
}
