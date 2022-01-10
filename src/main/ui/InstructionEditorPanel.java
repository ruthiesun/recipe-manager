package ui;

import model.Instruction;
import model.InstructionList;
import model.Recipe;

import javax.swing.*;
import java.awt.event.ActionEvent;

// Panel that allows for instructions in a recipe
public class InstructionEditorPanel extends EditorPanel {
    private Instruction instructionSelected;
    private JTextField fieldAbove;
    private JTextField fieldSelected;
    private JTextField fieldBelow;

    public InstructionEditorPanel(Recipe recipe, RecipeDisplayPanel recipeDisplayPanel) {
        super(recipe,recipeDisplayPanel);
    }

    @Override
    void setupFields() {
        fieldAbove = new JTextField(NUM_COLUMNS);
        fieldBelow = new JTextField(NUM_COLUMNS);
        fieldSelected = new JTextField(NUM_COLUMNS);
    }

    @Override
    void setupPanels() {
        panelAbove.add(fieldAbove);
        panelSelected.add(fieldSelected);
        panelBelow.add(fieldBelow);
    }

    // REQUIRES: instruction is in the recipe's list of instructions
    // MODIFIES: this
    // EFFECTS: sets instruction and index selected to the given instruction and index
    //          sets the middle text field to display this instruction
    protected void updateFieldSelected(Instruction instruction, int indexSelected) {
        this.instructionSelected = instruction;
        this.indexSelected = indexSelected;
        fieldSelected.setText(this.instructionSelected.getString());
    }

    // REQUIRES: action performed was clicking the "Apply" button
    // MODIFIES: this
    // EFFECTS: replaces the selected instruction with 1, 2, or 3 of the instructions in the text fields
    //          if all text fields are empty, deletes the selected instruction
    //          does nothing if no instruction has been selected
    @Override
    public void actionPerformed(ActionEvent ae) {
        InstructionList instructionsToAdd = new InstructionList();
        try {
            if (!fieldAbove.getText().isEmpty()) {
                instructionsToAdd.addInstruction(new Instruction(fieldAbove.getText()));
            }
            if (!fieldSelected.getText().isEmpty()) {
                instructionsToAdd.addInstruction(new Instruction(fieldSelected.getText()));
            }
            if (!fieldSelected.getText().isEmpty()) {
                instructionsToAdd.addInstruction(new Instruction(fieldBelow.getText()));
            }
            recipe.deleteInstruction(indexSelected);
            recipe.insertInstructions(indexSelected, instructionsToAdd);
            refresh();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Instruction was added to the end.");
            recipe.insertInstructions(recipe.getInstructionList().getSize(), instructionsToAdd);
            refresh();
        }
    }
}
