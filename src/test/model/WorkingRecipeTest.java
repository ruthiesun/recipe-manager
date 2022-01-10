package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Unit tests for WorkingRecipe
public class WorkingRecipeTest {
    private Recipe recipe;
    private WorkingRecipe workingRecipe;
    private Ingredient ingredient;
    private Instruction instruction;

    @BeforeEach
    public void setup() {
        IngredientList ingredients = new IngredientList();
        InstructionList instructions = new InstructionList();
        ingredient = new Ingredient(1,"a","b");
        instruction = new Instruction("x");
        ingredients.addIngredient(ingredient);
        instructions.addInstruction(instruction);
        recipe = new Recipe(ingredients,instructions);
        workingRecipe = new WorkingRecipe(recipe);
    }

    @Test
    public void testConstructor1() {
        assertEquals(workingRecipe.getCurrentRecipe(),recipe);
        assertEquals(workingRecipe.getNewRecipes().size(),0);
    }

    @Test
    public void testConstructor2() {
        WorkingRecipe wr = new WorkingRecipe(recipe,new ArrayList<>());

        assertEquals(workingRecipe.getCurrentRecipe(),recipe);
        assertEquals(workingRecipe.getNewRecipes().size(),0);
    }

    @Test
    public void testMakeNewDraft() {
        workingRecipe.makeNewDraft();
        workingRecipe.makeNewDraft();
        workingRecipe.makeNewDraft();

        assertEquals(workingRecipe.getNewRecipes().size(), 3);
        for (WorkingRecipe w : workingRecipe.getNewRecipes()) {
            ArrayList<Ingredient> currIngredients = w.getCurrentRecipe().getIngredientList().getIngredients();
            ArrayList<Instruction> currInstructions = w.getCurrentRecipe().getInstructionList().getInstructions();
            for (Ingredient i : currIngredients) {
                assertEquals(i.getQuantity(),ingredient.getQuantity());
                assertEquals(i.getUnit(),ingredient.getUnit());
                assertEquals(i.getName(),ingredient.getName());
            }
            for (Instruction i : currInstructions) {
                assertEquals(i.getString(),instruction.getString());
            }
        }
    }

    @Test
    public void testGetNewRecipeBeginning() {
        workingRecipe.makeNewDraft();
        workingRecipe.makeNewDraft();
        workingRecipe.makeNewDraft();

        WorkingRecipe w = workingRecipe.getNewRecipe(0);

        ArrayList<Ingredient> currIngredients = w.getCurrentRecipe().getIngredientList().getIngredients();
        ArrayList<Instruction> currInstructions = w.getCurrentRecipe().getInstructionList().getInstructions();
        for (Ingredient i : currIngredients) {
            assertEquals(i.getQuantity(),ingredient.getQuantity());
            assertEquals(i.getUnit(),ingredient.getUnit());
            assertEquals(i.getName(),ingredient.getName());
        }
        for (Instruction i : currInstructions) {
            assertEquals(i.getString(),instruction.getString());
        }
    }

    @Test
    public void testGetNewRecipeMiddle() {
        workingRecipe.makeNewDraft();
        workingRecipe.makeNewDraft();
        workingRecipe.makeNewDraft();

        WorkingRecipe w = workingRecipe.getNewRecipe(1);

        ArrayList<Ingredient> currIngredients = w.getCurrentRecipe().getIngredientList().getIngredients();
        ArrayList<Instruction> currInstructions = w.getCurrentRecipe().getInstructionList().getInstructions();
        for (Ingredient i : currIngredients) {
            assertEquals(i.getQuantity(),ingredient.getQuantity());
            assertEquals(i.getUnit(),ingredient.getUnit());
            assertEquals(i.getName(),ingredient.getName());
        }
        for (Instruction i : currInstructions) {
            assertEquals(i.getString(),instruction.getString());
        }
    }

    @Test
    public void testGetNewRecipeEnd() {
        workingRecipe.makeNewDraft();
        workingRecipe.makeNewDraft();
        workingRecipe.makeNewDraft();

        WorkingRecipe w = workingRecipe.getNewRecipe(2);

        ArrayList<Ingredient> currIngredients = w.getCurrentRecipe().getIngredientList().getIngredients();
        ArrayList<Instruction> currInstructions = w.getCurrentRecipe().getInstructionList().getInstructions();
        for (Ingredient i : currIngredients) {
            assertEquals(i.getQuantity(),ingredient.getQuantity());
            assertEquals(i.getUnit(),ingredient.getUnit());
            assertEquals(i.getName(),ingredient.getName());
        }
        for (Instruction i : currInstructions) {
            assertEquals(i.getString(),instruction.getString());
        }
    }

    @Test
    public void testGetCurrentRecipe() {
        assertEquals(workingRecipe.getCurrentRecipe(),recipe);
    }

    @Test
    public void testGetNewRecipes() {
        assertEquals(workingRecipe.getNewRecipes().size(),0);
    }

    @Test
    public void testToJson() {
        ArrayList<WorkingRecipe> wrList = new ArrayList<>();
        wrList.add(workingRecipe);
        wrList.add(workingRecipe);
        WorkingRecipe workingRecipeWithNewRecipes = new WorkingRecipe(recipe,wrList);

        JSONObject jsonObject = workingRecipeWithNewRecipes.toJson();

        String jsonCurrRecipe = jsonObject.getJSONObject("current recipe").toString();
        JSONArray jsonNextRecipes = jsonObject.getJSONArray("new recipes");
        assertEquals (workingRecipe.getCurrentRecipe().toJson().toString(),jsonCurrRecipe);

        for (Object wr : jsonNextRecipes) {
            String nextWr = ((JSONObject) wr).getJSONObject("current recipe").toString();
            assertEquals(recipe.toJson().toString(),nextWr);
        }
    }
}
