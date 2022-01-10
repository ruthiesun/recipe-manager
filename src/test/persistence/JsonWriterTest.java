package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/* Tests for JsonWriter
 * This class references code from JsonSerializationDemo
 * Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGeneralProjects() {
        try {
            setup();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralProjects.json");
            writer.open();
            writer.writeProjects(rpList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralProjects.json");
            ArrayList<RecipeProject> rpListFromJson = reader.read();
            for (int i = 0; i < rpList.size(); i++) {
                RecipeProject currRp = rpList.get(i);
                WorkingRecipe currRpWr = currRp.getWorkingRecipe();
                RecipeProject currRpFromJson = rpListFromJson.get(i);
                WorkingRecipe currRpWrFromJson = rpListFromJson.get(i).getWorkingRecipe();

                assertEquals(currRpFromJson.getName(), currRp.getName());
                assertEquals(currRpWrFromJson.toJson().toString(),currRpWr.toJson().toString());
                if (currRp.isRecipeFinished()) {
                    Recipe finalRecipe = currRp.getFinalRecipe();
                    Recipe finalRecipeFromJson = currRpFromJson.getFinalRecipe();
                    assertEquals(finalRecipe.getIngredientList(),finalRecipeFromJson.getIngredientList());
                    assertEquals(finalRecipe.getInstructionList(),finalRecipeFromJson.getInstructionList());
                } else {
                    assertFalse(currRpFromJson.isRecipeFinished());
                }
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNoProjects() {
        try {
            ArrayList<RecipeProject> rpList = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterNoProjects.json");
            writer.open();
            writer.writeProjects(rpList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoProjects.json");
            rpList = reader.read();
            assertEquals(0, rpList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
