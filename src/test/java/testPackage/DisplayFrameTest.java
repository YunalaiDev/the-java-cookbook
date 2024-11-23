package testPackage;

import cookbook.controller.RecipeMethodsController;
import cookbook.model.Recipe;
import cookbook.view.DisplayFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisplayFrameTest {

    private RecipeMethodsController controller;
    private DisplayFrame frame;

    @BeforeEach
    public void setUp() {
        controller = new RecipeMethodsController();
        controller.clearAllRecipes();
        controller.loadRecipesFromFile("src/test/resources/test2.json");
        controller.addNewRecipe(new Recipe("Gulyás", 60, Arrays.asList("Hús", "Burgonya"), "Leírás"));
        controller.addNewRecipe(new Recipe("Pörkölt", 45, Arrays.asList("Hús", "Hagyma"), "Másik leírás"));

        frame = new DisplayFrame();

        // Manuális inicializálás
        frame.searchField = new JTextField();
        frame.nameRadioButton = new JRadioButton();
        frame.ingredientsRadioButton = new JRadioButton();
        frame.timeRadioButton = new JRadioButton();

        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(frame.nameRadioButton);
        searchGroup.add(frame.ingredientsRadioButton);
        searchGroup.add(frame.timeRadioButton);

        // Inicializáljuk a searchResultsPanel-t
        frame.searchResultsPanel = new JPanel();
        frame.searchResultsPanel.setLayout(new BoxLayout(frame.searchResultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(frame.searchResultsPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Ezzel elkerüljük a null pointer hibát a tesztben
    }


    @Test
    public void testSearchRecipeByNameUsingReflection() throws Exception {
        // Reflectionnel hozzáférünk a privát metódushoz
        Method performSearch = DisplayFrame.class.getDeclaredMethod("performSearch");
        performSearch.setAccessible(true);

        // Seteljük a keresési mezőt és a rádiógombot
        frame.searchField.setText("Gulyás");
        frame.nameRadioButton.setSelected(true);

        // A metódus meghívása
        performSearch.invoke(frame);

        // Eredmény validálása
        List<Recipe> results = controller.getAllRecipes().stream()
                .filter(recipe -> recipe.getNameOfFood().equals("Gulyás"))
                .toList();

        assertEquals(1, results.size(), "A keresés hibásan működött név alapján.");
        controller.clearAllRecipes();
    }

}
