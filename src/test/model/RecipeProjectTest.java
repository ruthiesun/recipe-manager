package model;

import model.exceptions.FinalRecipeNotInWorkingRecipeException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//Unit tests for RecipeProject
class RecipeProjectTest {
    private RecipeProject project;
    private String name;
    private Recipe firstRecipe;

    @BeforeEach
    public void setup() {
        IngredientList ingredients = new IngredientList();
        InstructionList instructions = new InstructionList();
        name = "a";
        ingredients.addIngredient(new Ingredient(1,"a","b"));
        instructions.addInstruction(new Instruction("x"));
        firstRecipe = new Recipe(ingredients,instructions);
        project = new RecipeProject(name,firstRecipe);
    }

    @Test
    public void testConstructor1() {
        assertEquals(project.getName(),name);
        assertEquals(project.getWorkingRecipe().getCurrentRecipe(),firstRecipe);
    }

    @Test
    public void testConstructor2Null() {
        RecipeProject projectConstructor2 = new RecipeProject(name, project.getWorkingRecipe(),null);
        assertEquals(projectConstructor2.getName(),name);
        assertEquals(projectConstructor2.getWorkingRecipe().getCurrentRecipe(),firstRecipe);
        assertFalse(projectConstructor2.isRecipeFinished());
    }

    @Test
    public void testConstructor2NotNull() {
        Recipe r = new Recipe();
        ArrayList<WorkingRecipe> newRecipes = new ArrayList<>();
        newRecipes.add(project.getWorkingRecipe());
        WorkingRecipe wr = new WorkingRecipe(r,newRecipes);

        try {
            RecipeProject projectConstructor2 = new RecipeProject(name, wr, firstRecipe);
            assertEquals(projectConstructor2.getName(), name);
            assertEquals(projectConstructor2.getWorkingRecipe().getCurrentRecipe(), r);
            assertEquals(projectConstructor2.getFinalRecipe(), firstRecipe);
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            fail();
        }
    }

    @Test
    public void testConstructor2NotNullNoMatch() {
        Recipe r = new Recipe();

        try {
            RecipeProject projectConstructor2 = new RecipeProject(name, project.getWorkingRecipe(), r);
            fail();
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            //expected
        }
    }

    @Test
    public void testMakeNewDraft() {
        setup();
        WorkingRecipe workingRecipe = project.getWorkingRecipe();
        assertEquals(workingRecipe.getNewRecipes().size(), 0);

        project.makeNewDraft(workingRecipe);
        project.makeNewDraft(workingRecipe);

        assertEquals(workingRecipe.getNewRecipes().size(), 2);
    }


    @Test
    public void testAssignFinalRecipe() {
        project.assignFinalRecipe(firstRecipe);

        assertEquals(project.getFinalRecipe(),firstRecipe);
    }

    @Test
    public void testIsRecipeFinishedFinished() {
        project.assignFinalRecipe(firstRecipe);

        assertTrue(project.isRecipeFinished());
    }

    @Test
    public void testIsRecipeFinishedUnfinished() {
        assertFalse(project.isRecipeFinished());
    }

    @Test
    public void testFindMatchInWorkingRecipesShallow() {
        Recipe draft1 = project.getWorkingRecipe().getCurrentRecipe();

        Recipe r = new Recipe();
        WorkingRecipe wr = new WorkingRecipe(r);
        try {
            wr = project.findMatchInWorkingRecipes(project.getWorkingRecipe(), draft1);
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            fail();
        }
        assertEquals(wr,project.getWorkingRecipe());
    }

    @Test
    public void testFindMatchInWorkingRecipesDeep() {
        project.getWorkingRecipe().makeNewDraft();
        Recipe draft2 = project.getWorkingRecipe().getNewRecipes().get(0).getCurrentRecipe();
        Ingredient i = new Ingredient(1, "p","q");
        ArrayList<Ingredient> list = new ArrayList<>();
        list.add(i);
        project.getWorkingRecipe().getCurrentRecipe().addIngredients(new IngredientList(list));

        Recipe r = new Recipe();
        WorkingRecipe wr = new WorkingRecipe(r);
        try {
            wr = project.findMatchInWorkingRecipes(project.getWorkingRecipe(), draft2);
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            fail();
        }
        assertEquals(wr,project.getWorkingRecipe().getNewRecipe(0));
    }

    @Test
    public void testFindMatchInWorkingRecipesExpectExceptionShallow() {
        Recipe r = new Recipe();
        WorkingRecipe wr = new WorkingRecipe(r);
        try {
            wr = project.findMatchInWorkingRecipes(project.getWorkingRecipe(), r);
            fail();
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            //expected
        }
    }

    @Test
    public void testFindMatchInWorkingRecipesExpectExceptionDeep() {
        project.getWorkingRecipe().makeNewDraft();
        project.getWorkingRecipe().makeNewDraft();

        Recipe r = new Recipe();
        WorkingRecipe wr = new WorkingRecipe(r);
        try {
            wr = project.findMatchInWorkingRecipes(project.getWorkingRecipe(), r);
            fail();
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            //expected
        }
    }

    @Test
    public void testFindMatchingObjectInWorkingRecipesShallow() {
        Recipe draft1 = project.getWorkingRecipe().getCurrentRecipe();

        Recipe r = new Recipe();
        WorkingRecipe wr = new WorkingRecipe(r);
        try {
            wr = project.findMatchingObjectInWorkingRecipes(project.getWorkingRecipe(), draft1);
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            fail();
        }
        assertEquals(wr,project.getWorkingRecipe());
    }

    @Test
    public void testFindMatchingObjectInWorkingRecipesDeep() {
        project.getWorkingRecipe().makeNewDraft();
        Recipe draft2 = project.getWorkingRecipe().getNewRecipes().get(0).getCurrentRecipe();

        Recipe r = new Recipe();
        WorkingRecipe wr = new WorkingRecipe(r);
        try {
            wr = project.findMatchingObjectInWorkingRecipes(project.getWorkingRecipe(), draft2);
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            fail();
        }
        assertEquals(wr,project.getWorkingRecipe().getNewRecipe(0));
    }

    @Test
    public void testFindMatchingObjectInWorkingRecipesExpectExceptionShallow() {
        Recipe r = new Recipe();
        WorkingRecipe wr = new WorkingRecipe(r);
        try {
            wr = project.findMatchingObjectInWorkingRecipes(project.getWorkingRecipe(), r);
            fail();
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            //expected
        }
    }

    @Test
    public void testFindMatchingObjectInWorkingRecipesExpectExceptionDeep() {
        project.getWorkingRecipe().makeNewDraft();
        project.getWorkingRecipe().makeNewDraft();

        Recipe r = new Recipe();
        WorkingRecipe wr = new WorkingRecipe(r);
        try {
            wr = project.findMatchingObjectInWorkingRecipes(project.getWorkingRecipe(), r);
            fail();
        } catch (FinalRecipeNotInWorkingRecipeException e) {
            //expected
        }
    }

    @Test
    public void testGetFinalRecipe() {
        project.assignFinalRecipe(firstRecipe);

        Recipe f = project.getFinalRecipe();

        assertEquals(f,firstRecipe);
    }

    @Test
    public void testGetWorkingRecipe() {
        assertEquals(project.getWorkingRecipe().getCurrentRecipe(),firstRecipe);
    }

    @Test
    public void testGetName() {
        assertEquals(project.getName(),name);
    }

    @Test
    public void testToJson() {
        project.assignFinalRecipe(firstRecipe);

        JSONObject jsonObject = project.toJson();

        String jsonName = jsonObject.getString("name");
        String jsonWR = jsonObject.getJSONObject("working recipe").toString();
        String jsonFR = jsonObject.getJSONObject("final recipe").toString();
        assertEquals (name,jsonName);
        assertEquals (project.getWorkingRecipe().toJson().toString(),jsonWR);
        assertEquals(project.getFinalRecipe().toJson().toString(),jsonFR);
    }
}