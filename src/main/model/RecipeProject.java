package model;

import model.exceptions.FinalRecipeNotInWorkingRecipeException;
import model.logging.Event;
import model.logging.EventLog;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Observable;
import java.util.Observer;

// A collection of working versions of a recipe with the final version, if finished
public class RecipeProject implements Writable {
    private String name;
    private WorkingRecipe workingRecipe;
    private Recipe finalRecipe;

    // EFFECTS: sets name to projectName, sets workingRecipes to a new WorkingRecipe constructed with firstRecipe,
    //          sets finalRecipe to null
    public RecipeProject(String projectName, Recipe firstRecipe) {
        name = projectName;
        workingRecipe = new WorkingRecipe(firstRecipe);
        finalRecipe = null;
        log("Started new project");
    }

    // REQUIRES: projectName has not been used before
    //          and finalRecipe is either null or matches a recipe in workingRecipes.
    //          Calling method must be loading saved data
    // EFFECTS: makes a new project given a name, workingRecipe, and finalRecipe
    public RecipeProject(String projectName, WorkingRecipe workingRecipe, Recipe finalRecipe) {
        name = projectName;
        this.workingRecipe = workingRecipe;
        if (finalRecipe == null) {
            this.finalRecipe = null;
        } else {
            this.finalRecipe = findMatchInWorkingRecipes(workingRecipe, finalRecipe).getCurrentRecipe();
        }
        log("Loaded project");
    }

    // EFFECTS: returns the first recipe in workingRecipe matching the given recipe's ingredients and instructions
    //          throws FinalRecipeNotInWorkingRecipeException if there is no match
    public WorkingRecipe findMatchInWorkingRecipes(WorkingRecipe workingRecipe, Recipe recipe) {
        Recipe currRecipe = workingRecipe.getCurrentRecipe();
        if (currRecipe.getInstructionList().equals(recipe.getInstructionList())
                && currRecipe.getIngredientList().equals(recipe.getIngredientList())) {
            return workingRecipe;
        } else {
            for (int i = 0; i < workingRecipe.getNewRecipes().size(); i++) {
                return findMatchInWorkingRecipes(workingRecipe.getNewRecipe(i),recipe);
            }
        }
        throw new FinalRecipeNotInWorkingRecipeException();
    }

    // EFFECTS: returns the first matching recipe object in workingRecipe
    //          throws FinalRecipeNotInWorkingRecipeException if there is no match
    public WorkingRecipe findMatchingObjectInWorkingRecipes(WorkingRecipe workingRecipe, Recipe recipe) {
        Recipe currRecipe = workingRecipe.getCurrentRecipe();
        if (currRecipe.equals(recipe)) {
            return workingRecipe;
        } else {
            for (int i = 0; i < workingRecipe.getNewRecipes().size(); i++) {
                return findMatchInWorkingRecipes(workingRecipe.getNewRecipe(i),recipe);
            }
        }
        throw new FinalRecipeNotInWorkingRecipeException();
    }

    // REQUIRES: chosenRecipe is in the workingRecipes tree
    // MODIFIES: this
    // EFFECTS: finalRecipe is set to chosenRecipe
    public void assignFinalRecipe(Recipe chosenRecipe) {
        finalRecipe = chosenRecipe;
        log("Assigned final recipe");
    }

    // EFFECTS: returns true if final recipe has been assigned, false otherwise
    public boolean isRecipeFinished() {
        return !(finalRecipe == null);
    }

    // REQUIRES: given working recipe is in this project
    // MODIFIES: this
    // EFFECTS: creates a new draft in the given working recipe
    public void makeNewDraft(WorkingRecipe wr) {
        wr.makeNewDraft();
        log("Added new draft");
    }

    // REQUIRES: finalRecipe is not null
    // EFFECTS: returns the finalRecipe
    public Recipe getFinalRecipe() {
        return finalRecipe;
    }

    public WorkingRecipe getWorkingRecipe() {
        return workingRecipe;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("working recipe", workingRecipe.toJson());
        if (finalRecipe != null) {
            json.put("final recipe", finalRecipe.toJson());
        }
        return json;
    }

    // EFFECTS: adds event to the event log with the given description and current date
    public void log(String description) {
        EventLog.getInstance().logEvent(new Event(name + ": " + description));
    }
}
