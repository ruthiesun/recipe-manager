package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

//Unit tests for Ingredient
public class IngredientTest {
    Ingredient ingredient;

    @BeforeEach
    public void setup() {
        ingredient = new Ingredient(0.5F,"a","b");
    }

    @Test
    public void testConstructor() {
        assertEquals(ingredient.getQuantity(),0.5F);
        assertEquals(ingredient.getUnit(),"a");
        assertEquals(ingredient.getName(),"b");
    }

    @Test
    public void testGetQuantity() {
        assertEquals(ingredient.getQuantity(),0.5F);
    }

    @Test
    public void testGetUnit() {
        assertEquals(ingredient.getUnit(),"a");
    }

    @Test
    public void testGetName() {
        assertEquals(ingredient.getName(),"b");
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = ingredient.toJson();

        assertEquals (0.5F,jsonObject.getFloat("quantity"));
        assertEquals ("a",jsonObject.getString("unit"));
        assertEquals ("b",jsonObject.getString("name"));
    }

    @Test
    public void testEqualsSameObject() {
        Ingredient ingredient2 = ingredient;
        boolean isEqual = ingredient.equals(ingredient);

        assertTrue(isEqual);
    }

    @Test
    public void testEqualsNull() {
        Ingredient ingredient2 = null;
        boolean isEqual = ingredient.equals(ingredient2);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffClass() {
        int ingredient2 = 0;
        boolean isEqual = ingredient.equals(ingredient2);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffName() {
       Ingredient ingredient2 = new Ingredient(0.5F,"a","d");
        boolean isEqual = ingredient.equals(ingredient2);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffUnit() {
        Ingredient ingredient2 = new Ingredient(0.5F,"b","b");
        boolean isEqual = ingredient.equals(ingredient2);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffQuantity() {
        Ingredient ingredient2 = new Ingredient(0.6F,"a","b");
        boolean isEqual = ingredient.equals(ingredient2);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsSameFields() {
        Ingredient ingredient2 = new Ingredient(0.5F,"a","b");
        boolean isEqual = ingredient.equals(ingredient2);

        assertTrue(isEqual);
    }

    @Test
    public void testHashCode() {
        int hash = ingredient.hashCode();

        assertEquals(Objects.hash(ingredient.getQuantity(), ingredient.getUnit(), ingredient.getName()),hash);
    }

    @Test
    public void testGetString() {
        String string = ingredient.getString();

        assertTrue(string.equals(Float.toString(0.5F) + " a b"));
    }
}
