package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

//Unit tests for Recipe
public class RecipeTest {
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Ingredient ingredient3;
    private Instruction instruction1;
    private Instruction instruction2;
    private Instruction instruction3;
    private Recipe recipeConstructor1;
    private Recipe recipeConstructor2;
    IngredientList ingredients;
    InstructionList instructions;

    @BeforeEach
    public void setup() {
        ingredients = new IngredientList();
        instructions = new InstructionList();

        ingredient1 = new Ingredient(1,"a","b");
        ingredient2  = new Ingredient(2,"c","d");
        ingredient3  = new Ingredient(3,"e","f");
        instruction1 = new Instruction("x");
        instruction2 = new Instruction("y");
        instruction3 = new Instruction("z");
        ingredients.addIngredient(ingredient1);
        ingredients.addIngredient(ingredient2);
        ingredients.addIngredient(ingredient3);
        instructions.addInstruction(instruction1);
        instructions.addInstruction(instruction2);
        instructions.addInstruction(instruction3);
        recipeConstructor1 = new Recipe();
        recipeConstructor2 = new Recipe();
        recipeConstructor2.addIngredients(ingredients);
        recipeConstructor2.insertInstructions(0,instructions);
    }

    @Test
    public void testConstructor1() {
        assertEquals(recipeConstructor1.getIngredientList().getSize(),0);
        assertEquals(recipeConstructor1.getInstructionList().getSize(),0);
    }

    @Test
    public void testConstructor2() {
        assertEquals(recipeConstructor2.getIngredientList().getSize(),3);
        assertEquals(recipeConstructor2.getInstructionList().getSize(),3);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(0),ingredient1);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(1),ingredient2);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(2),ingredient3);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(0),instruction1);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(1),instruction2);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(2),instruction3);
    }

    @Test
    public void testAddIngredients() {
        IngredientList iList = new IngredientList();
        iList.addIngredient(ingredient1);

        recipeConstructor1.addIngredients(iList);

        assertEquals(recipeConstructor1.getIngredientList().getSize(),1);
        assertEquals(recipeConstructor1.getIngredientList().getIngredients().get(0), ingredient1);
    }

    @Test
    public void testAddIngredientsMultiple() {
        IngredientList iList1 = new IngredientList();
        IngredientList iList2 = new IngredientList();
        iList1.addIngredient(ingredient1);
        iList2.addIngredient(ingredient2);

        recipeConstructor1.addIngredients(iList1);
        recipeConstructor1.addIngredients(iList2);

        assertEquals(recipeConstructor1.getIngredientList().getSize(),2);
        assertEquals(recipeConstructor1.getIngredientList().getIngredients().get(0),ingredient1);
        assertEquals(recipeConstructor1.getIngredientList().getIngredients().get(1),ingredient2);
    }

    @Test
    public void testDeleteInstructionBeginning() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(instruction1);
        i.add(instruction2);
        i.add(instruction3);
        InstructionList iList = new InstructionList(i);
        recipeConstructor1.insertInstructions(0,iList);

        recipeConstructor1.deleteInstruction(0);

        assertEquals(recipeConstructor1.getInstructionList().getSize(),2);
        assertEquals(recipeConstructor1.getInstructionList().getInstructions().get(0),instruction2);
        assertEquals(recipeConstructor1.getInstructionList().getInstructions().get(1),instruction3);
    }

    @Test
    public void testDeleteInstructionMiddle() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(instruction1);
        i.add(instruction2);
        i.add(instruction3);
        InstructionList iList = new InstructionList(i);
        recipeConstructor1.insertInstructions(0,iList);

        recipeConstructor1.deleteInstruction(1);

        assertEquals(recipeConstructor1.getInstructionList().getSize(),2);
        assertEquals(recipeConstructor1.getInstructionList().getInstructions().get(0),instruction1);
        assertEquals(recipeConstructor1.getInstructionList().getInstructions().get(1),instruction3);
    }

    @Test
    public void testDeleteInstructionEnd() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(instruction1);
        i.add(instruction2);
        i.add(instruction3);
        InstructionList iList = new InstructionList(i);
        recipeConstructor1.insertInstructions(0,iList);

        recipeConstructor1.deleteInstruction(2);

        assertEquals(recipeConstructor1.getInstructionList().getSize(),2);
        assertEquals(recipeConstructor1.getInstructionList().getInstructions().get(0),instruction1);
        assertEquals(recipeConstructor1.getInstructionList().getInstructions().get(1),instruction2);
    }

    @Test
    public void testDeleteInstructionMultiple() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(instruction1);
        i.add(instruction2);
        i.add(instruction3);
        InstructionList iList = new InstructionList(i);
        recipeConstructor1.insertInstructions(0,iList);

        recipeConstructor1.deleteInstruction(0);
        recipeConstructor1.deleteInstruction(0);

        assertEquals(recipeConstructor1.getInstructionList().getSize(),1);
        assertEquals(recipeConstructor1.getInstructionList().getInstructions().get(0),instruction3);
    }

    @Test
    public void testInsertInstructionsBeginning() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(instruction3);
        InstructionList iList = new InstructionList(i);

        recipeConstructor2.insertInstructions(0,iList);

        assertEquals(recipeConstructor2.getInstructionList().getSize(),4);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(0),instruction3);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(1),instruction1);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(2),instruction2);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(3),instruction3);
    }

    @Test
    public void testInsertInstructionsMiddle() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(instruction3);
        InstructionList iList = new InstructionList(i);

        recipeConstructor2.insertInstructions(1,iList);

        assertEquals(recipeConstructor2.getInstructionList().getSize(),4);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(0),instruction1);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(1),instruction3);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(2),instruction2);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(3),instruction3);
    }

    @Test
    public void testInsertInstructionsEnd() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(instruction1);
        InstructionList iList = new InstructionList(i);

        recipeConstructor2.insertInstructions(3,iList);

        assertEquals(recipeConstructor2.getInstructionList().getSize(),4);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(0),instruction1);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(1),instruction2);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(2),instruction3);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(3),instruction1);
    }

    @Test
    public void testInsertInstructionsMultiple() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(instruction3);
        InstructionList iList = new InstructionList(i);

        recipeConstructor2.insertInstructions(0,iList);
        recipeConstructor2.insertInstructions(0,iList);

        assertEquals(recipeConstructor2.getInstructionList().getSize(),5);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(0),instruction3);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(1),instruction3);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(2),instruction1);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(3),instruction2);
        assertEquals(recipeConstructor2.getInstructionList().getInstructions().get(4),instruction3);
    }

    @Test
    public void testDeleteIngredientBeginning() {
        recipeConstructor2.deleteIngredient(0);

        assertEquals(recipeConstructor2.getIngredientList().getSize(),2);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(0),ingredient2);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(1),ingredient3);
    }

    @Test
    public void testDeleteIngredientMiddle() {
        recipeConstructor2.deleteIngredient(1);

        assertEquals(recipeConstructor2.getIngredientList().getSize(),2);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(0),ingredient1);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(1),ingredient3);
    }

    @Test
    public void testDeleteIngredientEnd() {
        recipeConstructor2.deleteIngredient(2);

        assertEquals(recipeConstructor2.getIngredientList().getSize(),2);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(0),ingredient1);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(1),ingredient2);
    }

    @Test
    public void testDeleteIngredientMultiple() {
        recipeConstructor2.deleteIngredient(0);
        recipeConstructor2.deleteIngredient(0);

        assertEquals(recipeConstructor2.getIngredientList().getSize(),1);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(0),ingredient3);
    }

    @Test
    public void testInsertIngredientsGeneral() {
        Ingredient i4 = new Ingredient(1,"g","h");
        Ingredient i5 = new Ingredient(2,"i","j");
        IngredientList ingredients2 = new IngredientList();
        ingredients2.addIngredient(i4);
        ingredients2.addIngredient(i5);

        recipeConstructor2.insertIngredients(0,ingredients2);
        recipeConstructor2.insertIngredients(0,ingredients2);

        assertEquals(recipeConstructor2.getIngredientList().getSize(),7);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(0),i4);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(1),i5);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(2),i4);
        assertEquals(recipeConstructor2.getIngredientList().getIngredients().get(3),i5);
    }

    @Test
    public void testInsertIngredientsStart() {
        Ingredient i4 = new Ingredient(1,"g","h");
        IngredientList ingredients2 = new IngredientList();
        ingredients2.addIngredient(i4);

        recipeConstructor2.insertIngredients(0,ingredients2);

        IngredientList ingredientList = recipeConstructor2.getIngredientList();
        assertEquals(ingredientList.getSize(),4);
        assertEquals(ingredientList.getIngredients().get(0),i4);
    }

    @Test
    public void testInsertIngredientsEnd() {
        Ingredient i4 = new Ingredient(1,"g","h");
        IngredientList ingredients2 = new IngredientList();
        ingredients2.addIngredient(i4);
        
        recipeConstructor2.insertIngredients(ingredients.getSize() - 1, ingredients2);

        IngredientList ingredientList = recipeConstructor2.getIngredientList();
        assertEquals(ingredientList.getSize(),4);
        assertEquals(ingredientList.getIngredients().get(ingredientList.getSize() - 2),i4);
    }

    @Test
    public void testGetIngredientsAsStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add(ingredient1.getString());
        list.add(ingredient2.getString());
        list.add(ingredient3.getString());

        ArrayList<String> testList = recipeConstructor2.getIngredientsAsStrings();

        assertEquals(testList.get(0),list.get(0));
        assertEquals(testList.get(1),list.get(1));
        assertEquals(testList.get(2),list.get(2));
    }

    @Test
    public void testGetInstructionsAsStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add(instruction1.getString());
        list.add(instruction2.getString());
        list.add(instruction3.getString());

        ArrayList<String> testList = recipeConstructor2.getInstructionsAsStrings();

        assertEquals(testList.get(0),list.get(0));
        assertEquals(testList.get(1),list.get(1));
        assertEquals(testList.get(2),list.get(2));
    }

    @Test
    public void testGetIngredientList() {
        ArrayList<Ingredient> iList = recipeConstructor2.getIngredientList().getIngredients();
        assertEquals(iList.get(0),ingredient1);
        assertEquals(iList.get(1),ingredient2);
        assertEquals(iList.get(2),ingredient3);
    }

    @Test
    public void testGetInstructionList() {
        ArrayList<Instruction> iList = recipeConstructor2.getInstructionList().getInstructions();
        assertEquals(iList.get(0),instruction1);
        assertEquals(iList.get(1),instruction2);
        assertEquals(iList.get(2),instruction3);
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = recipeConstructor2.toJson();

        JSONArray jsonIngredients = jsonObject.getJSONArray("ingredients");
        JSONArray jsonInstructions = jsonObject.getJSONArray("instructions");
        for (int i = 0; i < ingredients.getSize(); i++) {
            String jsonString = jsonIngredients.getJSONObject(i).toString();
            assertEquals(ingredients.getIngredients().get(i).toJson().toString(),jsonString);
        }
        for (int i = 0; i < instructions.getSize(); i++) {
            String jsonString = jsonInstructions.getJSONObject(i).toString();
            assertEquals(instructions.getInstructions().get(i).toJson().toString(),jsonString);
        }
    }
}
