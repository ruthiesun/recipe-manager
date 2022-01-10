package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/* Represents a reader that reads recipe projects from JSON data stored in file
 * This class references code from JsonSerializationDemo
 * Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads recipe project from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<RecipeProject> read() throws IOException {
        ArrayList<RecipeProject> rpList = new ArrayList<>();
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipeProjects(jsonObject);
    }

    // EFFECTS: parses list of recipe projects from JSON object and returns it
    private ArrayList<RecipeProject> parseRecipeProjects(JSONObject jsonObject) {
        JSONArray projectArray = jsonObject.getJSONArray("projects");
        ArrayList<RecipeProject> projects = new ArrayList<>();

        for (Object i : projectArray) {
            JSONObject nextProject = (JSONObject) i;
            projects.add(parseRecipeProject(nextProject));
        }

        return projects;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses recipe project from JSON object and returns it
    private RecipeProject parseRecipeProject(JSONObject rpJson) {
        String name = rpJson.getString("name");
        WorkingRecipe wr = parseWorkingRecipe(rpJson.getJSONObject("working recipe"));
        if (!rpJson.isNull(("final recipe"))) {
            Recipe fn = parseRecipe(rpJson.getJSONObject("final recipe"));
            return new RecipeProject(name, wr, fn);
        } else {
            return new RecipeProject(name, wr, null);
        }

    }

    // EFFECTS: parses working recipe tree from JSON object and returns it
    private WorkingRecipe parseWorkingRecipe(JSONObject wrJson) {
        Recipe cr = parseRecipe(wrJson.getJSONObject("current recipe"));
        ArrayList<WorkingRecipe> wrs = new ArrayList<>();
        JSONArray wrJsonArray = wrJson.getJSONArray("new recipes");

        for (Object wr : wrJsonArray) {
            JSONObject nextWr = (JSONObject) wr;
            wrs.add(parseWorkingRecipe(nextWr));
        }
        return new WorkingRecipe(cr, wrs);
    }

    // EFFECTS: parses recipe from JSON object and returns it
    private Recipe parseRecipe(JSONObject recipeJson) {
        IngredientList ingredients = new IngredientList();
        InstructionList instructions = new InstructionList();
        JSONArray ingredientsJsonArray = recipeJson.getJSONArray("ingredients");
        JSONArray instructionsJsonArray = recipeJson.getJSONArray("instructions");

        for (Object i : ingredientsJsonArray) {
            JSONObject nextIngredient = (JSONObject) i;
            ingredients.addIngredient(parseIngredient(nextIngredient));
        }

        for (Object i : instructionsJsonArray) {
            JSONObject nextInstruction = (JSONObject) i;
            instructions.addInstruction(parseInstruction(nextInstruction));
        }

        return new Recipe(ingredients, instructions);
    }

    // EFFECTS: parses ingredient from JSON object and returns it
    private Ingredient parseIngredient(JSONObject ingredientJson) {
        float quantity = ingredientJson.getFloat("quantity");
        String unit = ingredientJson.getString("unit");
        String name = ingredientJson.getString("name");

        return new Ingredient(quantity, unit, name);
    }

    // EFFECTS: parses instruction from JSON object and returns it
    private Instruction parseInstruction(JSONObject instructionJson) {
        return new Instruction(instructionJson.getString("instruction"));
    }
}
