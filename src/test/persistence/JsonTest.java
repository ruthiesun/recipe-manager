package persistence;

import model.*;

import java.util.ArrayList;

// Sets up a list of recipe projects for the persistence package tests
public abstract class JsonTest {
    protected Recipe recipe1;
    protected Recipe recipe2;
    protected WorkingRecipe wr;
    protected ArrayList<WorkingRecipe> nr;
    ArrayList<RecipeProject> rpList;

    protected void setup() {
        // construct list of 2 recipe projects

        IngredientList ingredients = new IngredientList();
        InstructionList instructions = new InstructionList();
        nr = new ArrayList<>();


        ingredients.addIngredient(new Ingredient(1,"cup","water"));
        instructions.addInstruction(new Instruction("boil"));
        recipe1 = new Recipe(ingredients,instructions);
        instructions.addInstruction(new Instruction("boil again"));
        recipe2 = new Recipe(ingredients,instructions);

        nr.add(new WorkingRecipe(recipe2,new ArrayList<>()));
        wr = new WorkingRecipe(recipe1,nr);
        RecipeProject rpAssignedFinal = new RecipeProject("hot water",wr,recipe2);
        RecipeProject rpNullFinal = new RecipeProject("cold water",wr,null);

        rpList = new ArrayList<>();
        rpList.add(rpAssignedFinal);
        rpList.add(rpNullFinal);
    }
}
