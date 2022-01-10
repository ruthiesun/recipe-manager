package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

// A tree-like hierarchy of recipes representing recipe drafts
public class WorkingRecipe implements Writable {
    private Recipe currentRecipe;
    private ArrayList<WorkingRecipe> newRecipes;

    // EFFECTS: sets currentRecipe to chosenRecipe, sets newRecipes to a new empty list
    public WorkingRecipe(Recipe chosenRecipe) {
        currentRecipe = chosenRecipe;
        newRecipes = new ArrayList<>();
    }

    // EFFECTS: sets currentRecipe to chosenRecipe, sets newRecipes to given list of working recipes
    public WorkingRecipe(Recipe chosenRecipe, ArrayList<WorkingRecipe> newRecipes) {
        currentRecipe = chosenRecipe;
        this.newRecipes = newRecipes;
    }

    // MODIFIES: this
    // EFFECTS: adds a new entry into newRecipes by making a new WorkingRecipe with a copy of currentRecipe
    public void makeNewDraft() {
        Recipe newRecipe = new Recipe(currentRecipe.getIngredientList(),currentRecipe.getInstructionList());
        newRecipes.add(new WorkingRecipe(newRecipe));
    }

    // REQUIRES: 0 <= i < length of newRecipes
    // EFFECTS: returns the WorkingRecipe at the ith spot in newRecipes (0-based indexing)
    public WorkingRecipe getNewRecipe(int i) {
        return newRecipes.get(i);
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public ArrayList<WorkingRecipe> getNewRecipes() {
        return newRecipes;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (WorkingRecipe wr : newRecipes) {
            jsonArray.put(wr.toJson());
        }

        json.put("current recipe", currentRecipe.toJson());
        json.put("new recipes", jsonArray);

        return json;
    }
}
