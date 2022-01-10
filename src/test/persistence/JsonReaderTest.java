package persistence;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import model.Recipe;
import model.RecipeProject;
import model.WorkingRecipe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/* Tests for JsonReader
 * This class references code from JsonSerializationDemo
 * Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ArrayList<RecipeProject> rpList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoProjects() {
        JsonReader reader = new JsonReader("./data/testReaderNoProjects.json");
        try {
            ArrayList<RecipeProject> rpList = reader.read();
            assertEquals(0, rpList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralProjects() {
        setup();

        JsonReader reader = new JsonReader("./data/testReaderGeneralProjects.json");
        try {
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
            fail("Couldn't read from file");
        }
    }
}
