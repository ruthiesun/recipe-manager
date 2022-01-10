package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

//Unit tests for IngredientList
public class IngredientListTest {
    private IngredientList ingredientsConstructor1;
    private IngredientList ingredientsConstructor2;
    private Ingredient i1;
    private Ingredient i2;
    private Ingredient i3;

    @BeforeEach
    public void setup() {
        ingredientsConstructor1 = new IngredientList();
        i1 = new Ingredient(1,"a","b");
        i2 = new Ingredient(2,"c","d");
        i3 = new Ingredient(3,"e","f");
        ArrayList<Ingredient> iList = new ArrayList<>();
        iList.add(i1);
        iList.add(i2);
        iList.add(i3);
        ingredientsConstructor2 = new IngredientList(iList);
    }

    @Test
    public void testConstructor1() {
        assertEquals(ingredientsConstructor1.getIngredients().size(),0);
    }

    @Test
    public void testConstructor2() {
        assertEquals(ingredientsConstructor2.getIngredients().size(),3);
        assertEquals(ingredientsConstructor2.getIngredients().get(0),i1);
        assertEquals(ingredientsConstructor2.getIngredients().get(1),i2);
        assertEquals(ingredientsConstructor2.getIngredients().get(2),i3);
    }

    @Test
    public void testDeleteIngredientBeginning() {
        ingredientsConstructor2.deleteIngredient(0);

        assertEquals(ingredientsConstructor2.getSize(),2);
        assertEquals(ingredientsConstructor2.getIngredients().get(0),i2);
        assertEquals(ingredientsConstructor2.getIngredients().get(1),i3);
    }

    @Test
    public void testDeleteIngredientMiddle() {
        ingredientsConstructor2.deleteIngredient(1);

        assertEquals(ingredientsConstructor2.getSize(),2);
        assertEquals(ingredientsConstructor2.getIngredients().get(0),i1);
        assertEquals(ingredientsConstructor2.getIngredients().get(1),i3);
    }

    @Test
    public void testDeleteIngredientEnd() {
        ingredientsConstructor2.deleteIngredient(2);

        assertEquals(ingredientsConstructor2.getSize(),2);
        assertEquals(ingredientsConstructor2.getIngredients().get(0),i1);
        assertEquals(ingredientsConstructor2.getIngredients().get(1),i2);
    }

    @Test
    public void testDeleteIngredientMultiple() {
        ingredientsConstructor2.deleteIngredient(0);
        ingredientsConstructor2.deleteIngredient(0);

        assertEquals(ingredientsConstructor2.getSize(),1);
        assertEquals(ingredientsConstructor2.getIngredients().get(0),i3);
    }

    @Test
    public void testAddIngredient() {
        Ingredient i4 = new Ingredient(1,"g","h");

        ingredientsConstructor2.addIngredient(i4);

        assertEquals(ingredientsConstructor2.getSize(),4);
        assertEquals(ingredientsConstructor2.getIngredients().get(3),i4);
    }

    @Test
    public void testAddIngredientMultiple() {
        Ingredient i4 = new Ingredient(1,"g","h");
        Ingredient i5 = new Ingredient(2,"i","j");

        ingredientsConstructor2.addIngredient(i4);
        ingredientsConstructor2.addIngredient(i5);

        assertEquals(ingredientsConstructor2.getSize(),5);
        assertEquals(ingredientsConstructor2.getIngredients().get(3),i4);
        assertEquals(ingredientsConstructor2.getIngredients().get(4),i5);
    }

    @Test
    public void testInsertIngredientsGeneral() {
        Ingredient i4 = new Ingredient(1,"g","h");
        Ingredient i5 = new Ingredient(2,"i","j");
        ingredientsConstructor1.addIngredient(i4);
        ingredientsConstructor1.addIngredient(i5);

        ingredientsConstructor2.insertIngredients(0,ingredientsConstructor1);
        ingredientsConstructor2.insertIngredients(0,ingredientsConstructor1);

        assertEquals(ingredientsConstructor2.getSize(),7);
        assertEquals(ingredientsConstructor2.getIngredients().get(0),i4);
        assertEquals(ingredientsConstructor2.getIngredients().get(1),i5);
        assertEquals(ingredientsConstructor2.getIngredients().get(2),i4);
        assertEquals(ingredientsConstructor2.getIngredients().get(3),i5);
    }

    @Test
    public void testInsertIngredientsStart() {
        Ingredient i4 = new Ingredient(1,"g","h");
        ingredientsConstructor1.addIngredient(i4);

        ingredientsConstructor2.insertIngredients(0,ingredientsConstructor1);

        assertEquals(ingredientsConstructor2.getSize(),4);
        assertEquals(ingredientsConstructor2.getIngredients().get(0),i4);
    }

    @Test
    public void testInsertIngredientsEnd() {
        Ingredient i4 = new Ingredient(1,"g","h");
        ingredientsConstructor1.addIngredient(i4);

        ingredientsConstructor2.insertIngredients(ingredientsConstructor2.getSize() - 1, ingredientsConstructor1);

        assertEquals(ingredientsConstructor2.getSize(),4);
        assertEquals(ingredientsConstructor2.getIngredients().get(ingredientsConstructor2.getSize() - 2),i4);
    }


    @Test
    public void testGetIngredientsCopy() {
        ArrayList<Ingredient> i = ingredientsConstructor2.getIngredientsCopy();

        assertEquals(i.size(),3);
        assertNotSame(i.get(0),i1);
        assertEquals(i.get(0).getQuantity(),1);
        assertEquals(i.get(0).getUnit(),"a");
        assertEquals(i.get(0).getName(),"b");
        assertNotSame(i.get(1),i2);
        assertEquals(i.get(1).getQuantity(),2);
        assertEquals(i.get(1).getUnit(),"c");
        assertEquals(i.get(1).getName(),"d");
        assertNotSame(i.get(2),i3);
        assertEquals(i.get(2).getQuantity(),3);
        assertEquals(i.get(2).getUnit(),"e");
        assertEquals(i.get(2).getName(),"f");
    }

    @Test
    public void testGetSize() {
        int size = ingredientsConstructor2.getSize();

        assertEquals(size, 3);
    }

    @Test
    public void testGetIngredients() {
        ArrayList<Ingredient> i = ingredientsConstructor2.getIngredients();

        assertEquals(i.size(),3);
        assertEquals(i.get(0),i1);
        assertEquals(i.get(1),i2);
        assertEquals(i.get(2),i3);
    }

    @Test
    public void testEqualsSameObject() {
        IngredientList ingredients = ingredientsConstructor2;
        boolean isEqual = ingredientsConstructor2.equals(ingredients);

        assertTrue(isEqual);
    }

    @Test
    public void testEqualsNull() {
        IngredientList ingredients = null;
        boolean isEqual = ingredientsConstructor2.equals(ingredients);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffClass() {
        int ingredients = 0;
        boolean isEqual = ingredientsConstructor2.equals(ingredients);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffSizes() {
        boolean isEqual = ingredientsConstructor2.equals(ingredientsConstructor1);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffFields() {
        Ingredient i4 = new Ingredient(1,"g","h");
        Ingredient i5 = new Ingredient(2,"i","j");
        Ingredient i6 = new Ingredient(3, "k","l");
        ArrayList<Ingredient> iList = new ArrayList<>();
        iList.add(i4);
        iList.add(i5);
        iList.add(i6);
        IngredientList ingredients = new IngredientList(iList);

        boolean isEqual = ingredientsConstructor2.equals(ingredients);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsSameFields() {
        ArrayList<Ingredient> iList = new ArrayList<>();
        iList.add(i1);
        iList.add(i2);
        iList.add(i3);
        IngredientList ingredients = new IngredientList(iList);

        boolean isEqual = ingredientsConstructor2.equals(ingredients);

        assertTrue(isEqual);
    }

    @Test
    public void testHashCode() {
        int hash = ingredientsConstructor2.hashCode();

        assertEquals(Objects.hash(ingredientsConstructor2.getIngredients()),hash);
    }
}
