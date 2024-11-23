package cookbook.view;

import cookbook.controller.RecipeMethodsController;
import cookbook.model.BaseRecipeDialog;
import cookbook.model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RecipeEditDialog extends BaseRecipeDialog {
    private RecipeMethodsController controller;
    private Recipe recipe;
    private boolean isUpdated = false;

    public RecipeEditDialog(Frame owner, RecipeMethodsController controller, Recipe recipe) {
        super(owner, "Recept Szerkesztése");
        this.controller = controller;
        this.recipe = recipe;
        initializeUI();
    }

    private void initializeUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        // Mezők hozzáadása a panelhez
        contentPanel.add(createEditableField("Recept neve:", recipe.getNameOfFood(), true, true));
        contentPanel.add(createEditableField("Elkészítési idő (percben):", Integer.toString(recipe.getTimeToMake()), true, true));
        contentPanel.add(createEditableField("Hozzávalók (vesszővel elválasztva):", String.join(", ", recipe.getIngredients()), false, true));
        contentPanel.add(createEditableField("Elkészítés:", recipe.getDescription(), false, true));

        // Görgetősáv beállítása
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        // Gombok
        JButton updateButton = new JButton("Módosítás");
        StyleManager.styleButton(updateButton);
        updateButton.addActionListener(e -> {
            if (validateInput()) {
                updateRecipe();
                isUpdated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Kérlek, minden mezőt helyesen tölts ki!", "Hibás adatok", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Mégse");
        StyleManager.styleButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        // Elrendezés
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateRecipe() {
        recipe.setNameOfFood(nameField.getText());
        recipe.setTimeToMake(Integer.parseInt(timeField.getText()));
        recipe.setIngredients(Arrays.asList(ingredientsArea.getText().split(",\\s*")));
        recipe.setDescription(descriptionArea.getText());

        controller.updateRecipe(recipe);
        JOptionPane.showMessageDialog(this, "A recept frissítve!", "Szerkesztés", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean validateInput() {
        try {
            Integer.parseInt(timeField.getText());
            return !nameField.getText().trim().isEmpty() && !ingredientsArea.getText().trim().isEmpty() && !descriptionArea.getText().trim().isEmpty();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isUpdated() {
        return isUpdated;
    }
}
