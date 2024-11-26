package testPackage;

import cookbook.model.Recipe;
import cookbook.services.JSonServices;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSonServicesTest {

    @Test
    public void testSaveRecipesToJson() {
        List<Recipe> recipes = Arrays.asList(new Recipe("Paprikás krumpli", 40, Arrays.asList("Burgonya", "Paprika"), "Főzzük puhára."));
        String filePath = "test_recipes.json";
        JSonServices.saveRecipesToJson(recipes, filePath);
        File file = new File(filePath);
        assertTrue(file.exists(), "A JSON fájl mentése nem sikerült.");
        file.delete(); // Tisztítás
    }

    @Test
    public void testLoadRecipesFromFile() {
        String filePath = "test_recipes.json";
        List<Recipe> recipes = Arrays.asList(new Recipe("Paprikás krumpli", 40, Arrays.asList("Burgonya", "Paprika"), "Főzzük puhára."));
        JSonServices.saveRecipesToJson(recipes, filePath);
        List<Recipe> loadedRecipes = JSonServices.loadRecipesFromFile(filePath);
        assertEquals(recipes.size(), loadedRecipes.size(), "A JSON fájl betöltése nem sikerült.");
        assertEquals("Paprikás krumpli", loadedRecipes.get(0).getNameOfFood(), "A betöltött recept neve hibás.");
        new File(filePath).delete(); // Tisztítás
    }

    @Test
    public void testLoadInvalidJsonStructure() {
        String invalidJsonFilePath = "src/test/resources/wrongformat.json";
        // Létrehozunk egy nem megfelelő JSON-t
        String invalidJsonContent = "[{\"unknownField\": \"value\"}, {\"name\": \"Gulyás\", \"ingredients\": \"not a list\"}]";
        try (FileWriter writer = new FileWriter(invalidJsonFilePath)) {
            writer.write(invalidJsonContent);
        } catch (Exception e) {
            fail("A teszt JSON fájl mentése nem sikerült.");
        }
        List<Recipe> loadedRecipes = JSonServices.loadRecipesFromFile(invalidJsonFilePath);
        assertTrue(loadedRecipes.isEmpty(), "A hibás JSON-t nem szabadott volna betölteni.");
    }

}
