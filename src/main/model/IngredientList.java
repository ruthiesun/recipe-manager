package model;

import java.util.ArrayList;
import java.util.Objects;

// Represents a list of ingredients in a recipe
public class IngredientList {
    private ArrayList<Ingredient> ingredients;

    // EFFECTS: ingredients is set to an empty list
    public IngredientList() {
        ingredients = new ArrayList<>();
    }

    // EFFECTS: ingredients is set to the given ingredient list
    public IngredientList(ArrayList<Ingredient> i) {
        ingredients = i;
    }

    // REQUIRES: 0 <= i < length of ingredients
    // MODIFIES: this
    // EFFECTS: deletes the ith ingredient in ingredients (0-based indexing)
    protected void deleteIngredient(int i) {
        ingredients.remove(i);
    }

    // MODIFIES: this
    // EFFECTS: adds newIngredient to the end of ingredients
    public void addIngredient(Ingredient newIngredient) {
        ingredients.add(newIngredient);
    }

    // MODIFIES: this
    // EFFECTS: adds newIngredients to the end of ingredients
    public void addIngredients(IngredientList newIngredients) {
        ingredients.addAll(newIngredients.getIngredients());
    }

    // MODIFIES: this
    // EFFECTS: adds newIngredients to the ith spot in ingredients (0-based indexing)
    protected void insertIngredients(int i, IngredientList newIngredients) {
        ingredients.addAll(i,newIngredients.getIngredients());
    }

    // EFFECTS: creates a copy of ingredients and returns the copy
    public ArrayList<Ingredient> getIngredientsCopy() {
        ArrayList<Ingredient> copy = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            float quantity = ingredient.getQuantity();
            String unit = ingredient.getUnit();
            String name = ingredient.getName();
            copy.add(new Ingredient(quantity,unit,name));
        }
        return copy;
    }

    // EFFECTS: returns the length of the ingredients list
    public int getSize() {
        return ingredients.size();
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    // EFFECTS: returns true if this and given object are both ingredient lists with the same ingredients;
    //          false otherwise
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientList that = (IngredientList) o;
        if (ingredients.size() != that.ingredients.size()) {
            return false;
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (!Objects.equals(ingredients.get(i), that.ingredients.get(i))) {
                return false;
            }
        }
        return true;
    }

    /*
     * EFFECTS: returns hashcode based on the list of ingredients
     */
    @Override
    public int hashCode() {
        return Objects.hash(ingredients);
    }
}
