package ui;

import model.*;
import ui.exceptions.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Panel that allows for editing ingredients in a recipe
public class IngredientEditorPanel extends EditorPanel {
    private Ingredient ingredientSelected;
    private JTextField fieldAboveQuantity;
    private JTextField fieldSelectedQuantity;
    private JTextField fieldBelowQuantity;
    private JTextField fieldAboveUnit;
    private JTextField fieldSelectedUnit;
    private JTextField fieldBelowUnit;
    private JTextField fieldAboveName;
    private JTextField fieldSelectedName;
    private JTextField fieldBelowName;

    public IngredientEditorPanel(Recipe recipe, RecipeDisplayPanel recipeDisplayPanel) {
        super(recipe, recipeDisplayPanel);
    }

    @Override
    void setupFields() {
        fieldAboveQuantity = new JTextField(NUM_COLUMNS / 3);
        fieldBelowQuantity = new JTextField(NUM_COLUMNS / 3);
        fieldSelectedQuantity = new JTextField(NUM_COLUMNS / 3);
        fieldAboveUnit = new JTextField(NUM_COLUMNS / 3);
        fieldBelowUnit = new JTextField(NUM_COLUMNS / 3);
        fieldSelectedUnit = new JTextField(NUM_COLUMNS / 3);
        fieldAboveName = new JTextField(NUM_COLUMNS / 3);
        fieldBelowName = new JTextField(NUM_COLUMNS / 3);
        fieldSelectedName = new JTextField(NUM_COLUMNS / 3);
    }

    @Override
    void setupPanels() {
        panelAbove.add(fieldAboveQuantity);
        panelAbove.add(fieldAboveUnit);
        panelAbove.add(fieldAboveName);
        panelSelected.add(fieldSelectedQuantity);
        panelSelected.add(fieldSelectedUnit);
        panelSelected.add(fieldSelectedName);
        panelBelow.add(fieldBelowQuantity);
        panelBelow.add(fieldBelowUnit);
        panelBelow.add(fieldBelowName);
    }

    // REQUIRES: ingredient is in the recipe's list of ingredients
    // MODIFIES: this
    // EFFECTS: sets ingredient and index selected to the given ingredient and index
    //          sets the middle text field to display this ingredient
    protected void updateFieldSelected(Ingredient ingredient, int indexSelected) {
        this.ingredientSelected = ingredient;
        this.indexSelected = indexSelected;
        fieldSelectedQuantity.setText(Float.toString(this.ingredientSelected.getQuantity()));
        fieldSelectedUnit.setText(this.ingredientSelected.getUnit());
        fieldSelectedName.setText(this.ingredientSelected.getName());
    }

    // REQUIRES: action performed was clicking the "Apply" button
    // MODIFIES: this
    // EFFECTS: replaces the selected ingredient with 1, 2, or 3 of the ingredients in the text fields
    //          if all text fields are empty, deletes the selected ingredient
    //          does nothing if no ingredient has been selected
    //          does not update the recipe if there is an invalid ingredient entry in the text fields
    @Override
    public void actionPerformed(ActionEvent ae) {
        IngredientList ingredientsToAdd = new IngredientList();
        try {
            boolean aboveValid = checkValid(ingredientsToAdd, fieldAboveQuantity, fieldAboveUnit, fieldAboveName);
            boolean selectedValid = checkValid(ingredientsToAdd, fieldSelectedQuantity, fieldSelectedUnit,
                    fieldSelectedName);
            boolean belowValid = checkValid(ingredientsToAdd, fieldBelowQuantity, fieldBelowUnit, fieldBelowName);

            if (aboveValid && selectedValid && belowValid) {
                recipe.deleteIngredient(indexSelected);
                recipe.insertIngredients(indexSelected, ingredientsToAdd);
                refresh();
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ingredient was added to the end.");
            recipe.insertIngredients(recipe.getIngredientList().getSize(), ingredientsToAdd);
            refresh();
        }
    }

    // MODIFIES: this
    // EFFECTS: if ingredient in the text fields is added to list without invoking an InvalidInputException,
    //          return true
    //          else, return false
    private boolean checkValid(IngredientList list, JTextField quantityField, JTextField unitField,
                               JTextField nameField) {
        try {
            tryToAddIngredient(list, quantityField, unitField, nameField);
            return true;
        } catch (InvalidInputException e) {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if text fields are filled out correctly, then remove any highlighting and add the ingredients to the
    //          list of ingredients
    //          if there is a text field row that is filled out incorrectly, highlight that row of text fields and
    //          throw an InvalidInputException
    private void tryToAddIngredient(IngredientList list, JTextField quantityField, JTextField unitField,
                                    JTextField nameField) throws InvalidInputException {
        try {
            if (fieldsAreFilled(quantityField,unitField,nameField)) {
                unHighlightFields(quantityField,unitField,nameField);
                list.addIngredient(textFieldsToIngredient(quantityField, unitField, nameField));
            }
        } catch (InvalidInputException e) {
            highlightFields(quantityField, unitField, nameField);
            throw new InvalidInputException();
        }
    }

    // EFFECTS: return true if given fields all have something in them, false if they're all empty
    //          throws InvalidInputException otherwise
    private boolean fieldsAreFilled(JTextField f1, JTextField f2, JTextField f3) throws InvalidInputException {
        if (f1.getText().isEmpty() && f2.getText().isEmpty() && f3.getText().isEmpty()) {
            return false;
        }
        if (!f1.getText().isEmpty() && !f2.getText().isEmpty() && !f3.getText().isEmpty()) {
            return true;
        }
        throw new InvalidInputException();
    }

    // EFFECTS: casts given text field to a float
    //          throws InvalidInputException if this cannot be done
    private float textFieldToFloat(JTextField field) throws InvalidInputException {
        try {
            return Float.parseFloat(field.getText());
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
    }

    // EFFECTS: converts given text fields to an Ingredient object and returns it
    //          throws InvalidInputException if the Ingredient object cannot be made from the fields
    private Ingredient textFieldsToIngredient(JTextField quantityField, JTextField unitField, JTextField nameField)
            throws InvalidInputException {
        Float quantity = textFieldToFloat(quantityField);
        String unit = unitField.getText();
        String name = nameField.getText();
        return new Ingredient(quantity, unit, name);
    }

    // MODIFIES: this
    // EFFECTS: highlights the given fields in yellow
    private void highlightFields(JTextField f1, JTextField f2, JTextField f3) {
        f1.setBackground(Color.yellow);
        f2.setBackground(Color.yellow);
        f3.setBackground(Color.yellow);
    }

    // MODIFIES: this
    // EFFECTS: sets background of given fields to white
    private void unHighlightFields(JTextField f1, JTextField f2, JTextField f3) {
        f1.setBackground(Color.white);
        f2.setBackground(Color.white);
        f3.setBackground(Color.white);
    }
}


