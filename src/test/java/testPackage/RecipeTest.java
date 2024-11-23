package testPackage;

import cookbook.model.Recipe;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    public void testSetNameOfFood() {
        Recipe recipe = new Recipe("Régi név", 30, Arrays.asList("Hozzávaló1"), "Leírás");
        recipe.setNameOfFood("Új név");
        assertEquals("Új név", recipe.getNameOfFood(), "A recept neve nem frissült megfelelően.");
    }

    @Test
    public void testSetIngredients() {
        Recipe recipe = new Recipe("Étel", 20, Arrays.asList("Régi hozzávaló"), "Leírás");
        List<String> newIngredients = Arrays.asList("Új hozzávaló1", "Új hozzávaló2");
        recipe.setIngredients(newIngredients);
        assertEquals(newIngredients, recipe.getIngredients(), "A hozzávalók frissítése nem sikerült.");
    }
}
