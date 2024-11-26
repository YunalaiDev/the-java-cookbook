package testPackage;

import cookbook.controller.RecipeMethodsController;
import cookbook.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeMethodsControllerTest {
    private RecipeMethodsController testController;
    private final String TEST_JSON_PATH = "src/test/resources/test.json";

    @BeforeEach
    void setUp() {
        testController = new RecipeMethodsController();
        testController.loadRecipesFromFile(TEST_JSON_PATH);
        testController.clearAllRecipes();
   }

    @Test
    public void testAddNewRecipe() {
        Recipe recipe = new Recipe("Lecsó", 30, Arrays.asList("Paprika", "Paradicsom", "Hagyma"), "Süssük össze az egészet.");
        testController.addNewRecipe(recipe);
        List<Recipe> recipes = testController.getAllRecipes();
        assertTrue(recipes.contains(recipe), "A recept hozzáadása nem sikerült.");
    }

    @Test
    void testDeleteRecipe() {
        Recipe recipe = new Recipe("Pancake", 20, Arrays.asList("Flour", "Eggs", "Milk"), "Mix ingredients and fry");
        testController.addNewRecipe(recipe);
        assertEquals(1, testController.getAllRecipes().size(), "Should have one recipe after adding.");
        testController.deleteRecipe(recipe);
        assertTrue(testController.getAllRecipes().isEmpty(), "Recipe list should be empty after deletion.");
    }

    @Test
    public void testGetAllRecipes_EmptyList() {
        testController.clearAllRecipes();
        List<Recipe> recipes = testController.getAllRecipes();
        assertTrue(recipes.isEmpty(), "Az üres lista tesztje nem sikerült.");
    }

    @Test
    public void testUpdateRecipe() {
        Recipe recipe = new Recipe("Pörkölt", 90, Arrays.asList("Hús", "Hagyma", "Fűszerpaprika"), "Főzzük puhára.");
        testController.addNewRecipe(recipe);
        recipe.setTimeToMake(120);
        testController.updateRecipe(recipe);
        List<Recipe> recipes = testController.getAllRecipes();
        assertEquals(120, recipes.get(0).getTimeToMake(), "A recept frissítése nem sikerült.");
    }

    @Test
    public void testLoadEmptyRecipeList() {
        String emptyJsonPath = "src/test/resources/empty.json";
        try (FileWriter writer = new FileWriter(emptyJsonPath)) {
            writer.write("[]"); // Üres JSON
        } catch (Exception e) {
            fail("Nem sikerült létrehozni az üres JSON fájlt.");
        }

        testController.loadRecipesFromFile(emptyJsonPath);
        assertTrue(testController.getAllRecipes().isEmpty(), "Az üres JSON fájl betöltése nem üres listát adott vissza.");
    }

    @Test
    public void testCreateNewEmptyJson() {
        String newJsonPath = "src/test/resources/new_empty.json";
        testController.createNewEmptyJson(newJsonPath);
        assertTrue(testController.getAllRecipes().isEmpty(), "Az új üres JSON fájl létrehozása nem sikerült.");
    }


}


