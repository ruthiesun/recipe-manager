package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Observable;

// Represents a recipe with ingredients and instructions
public class Recipe implements Writable {
    private IngredientList ingredients;
    private InstructionList instructions;

    // EFFECTS: sets ingredients to an empty list of ingredients, sets instructions to an empty list of instructions
    public Recipe() {
        ingredients = new IngredientList();
        instructions = new InstructionList();
    }

    // EFFECTS: creates copies of given recipe elements and sets ingredients and instructions to these copies
    public Recipe(IngredientList newIngredients, InstructionList newInstructions) {
        ingredients = new IngredientList(newIngredients.getIngredientsCopy());
        instructions = new InstructionList(newInstructions.getInstructionsCopy());
    }

    // MODIFIES: this
    // EFFECTS: adds newIngredients to end of ingredients
    public void addIngredients(IngredientList newIngredients) {
        ingredients.addIngredients(newIngredients);
    }

    // MODIFIES: this
    // EFFECTS: adds newIngredients to the ith spot in ingredients (0-based indexing)
    public void insertIngredients(int i, IngredientList newIngredients) {
        ingredients.insertIngredients(i,newIngredients);
    }

    // REQUIRES: 0 <= i < length of instructions
    // MODIFIES: this
    // EFFECTS: deletes the instruction in the ith spot in instructions (0-based indexing)
    public void deleteInstruction(int i) {
        instructions.deleteInstruction(i);
    }

    // REQUIRES: 0 <= i < length of instructions
    // MODIFIES: this
    // EFFECTS: adds newInstructions to the ith spot in instructions (0-based indexing)
    public void insertInstructions(int i, InstructionList newInstructions) {
        instructions.insertInstructions(i,newInstructions);
    }

    // REQUIRES: 0 <= i < length of ingredients
    // MODIFIES: this
    // EFFECTS: deletes the ingredient in the ith spot in ingredients (0-based indexing)
    public void deleteIngredient(int i) {
        ingredients.deleteIngredient(i);
    }

    public IngredientList getIngredientList() {
        return ingredients;
    }

    public InstructionList getInstructionList() {
        return instructions;
    }

    // EFFECTS: creates an ArrayList of Strings that each represent an ingredient in the recipe
    public ArrayList<String> getIngredientsAsStrings() {
        ArrayList<String> printedIngredients = new ArrayList<>();
        ArrayList<Ingredient> ingredients = getIngredientList().getIngredients();

        for (Ingredient ingredient : ingredients) {
            printedIngredients.add(ingredient.getString());
        }
        return printedIngredients;
    }

    // EFFECTS: creates an ArrayList of Strings that each represent an instruction in the recipe
    public ArrayList<String> getInstructionsAsStrings() {
        ArrayList<String> printedInstructions = new ArrayList<>();
        ArrayList<Instruction> instructions = getInstructionList().getInstructions();

        for (int i = 0; i < instructions.size(); i++) {
            printedInstructions.add(instructions.get(i).getString());
        }
        return printedInstructions;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray ingredientJson = new JSONArray();
        JSONArray instructionJson = new JSONArray();

        for (Ingredient i : ingredients.getIngredients()) {
            ingredientJson.put(i.toJson());
        }

        for (Instruction i : instructions.getInstructions()) {
            instructionJson.put(i.toJson());
        }

        json.put("ingredients",ingredientJson);
        json.put("instructions",instructionJson);

        return json;
    }
}
