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
    @Test
    public void testToString() {
        Recipe recipe = new Recipe("Palacsinta", 30, Arrays.asList("Liszt", "Tej", "Tojás"), "Keverjük össze és süssük meg.");
        String expectedString = "Étel: Palacsinta', elkészítési idő: 30 perc, hozzávalók Liszt, Tej, Tojás, elkészítés: Keverjük össze és süssük meg.";
        assertEquals(expectedString, recipe.toString(), "A toString metódus hibás eredményt adott.");
    }

    @Test
    public void testListToString() {
        Recipe recipe = new Recipe("Pizza", 120, Arrays.asList("Liszt", "Élesztő", "Paradicsomszósz"), "Készítsük el a tésztát.");
        String expectedIngredients = "Liszt, Élesztő, Paradicsomszósz";
        assertEquals(expectedIngredients, String.join(", ", recipe.getIngredients()), "A hozzávalók listája nem lett helyesen szöveggé alakítva.");
    }

}
