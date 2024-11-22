package testPackage;

import cookbook.controller.RecipeMethodsController;
import cookbook.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void testAddNewRecipe() {
        Recipe recipe = new Recipe("Spaghetti", 30, Arrays.asList("Pasta", "Tomato Sauce"), "Cook pasta and add sauce");
        testController.addNewRecipe(recipe);
        assertEquals(1, testController.getAllRecipes().size(), "Should have one recipe after adding one.");
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
    void testSearchRecipeByName() {
        Recipe recipe1 = new Recipe("Pasta", 15, Arrays.asList("Flour", "Eggs"), "Cook pasta");
        Recipe recipe2 = new Recipe("Pizza", 25, Arrays.asList("Cheese", "Tomato"), "Bake pizza");
        testController.addNewRecipe(recipe1);
        testController.addNewRecipe(recipe2);

        List<Recipe> results = testController.getAllRecipes().stream()
                .filter(recipe -> recipe.getNameOfFood().equalsIgnoreCase("Pasta"))
                .toList();

        assertEquals(1, results.size(), "Egyetlen .");
        assertEquals("Pasta", results.get(0).getNameOfFood());
    }
}


