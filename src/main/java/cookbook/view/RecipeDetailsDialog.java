package cookbook.view;

import cookbook.model.BaseRecipeDialog;
import cookbook.model.Recipe;

import javax.swing.*;
import java.awt.*;

public class RecipeDetailsDialog extends BaseRecipeDialog {
    public RecipeDetailsDialog(Frame owner, Recipe recipe) {
        super(owner, "Recept részletei");
        initializeUI(recipe);
    }

    private void initializeUI(Recipe recipe) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        contentPanel.add(createEditableField("Recept neve:", recipe.getNameOfFood(), true, false));
        contentPanel.add(createEditableField("Elkészítési idő:", recipe.getTimeToMake() + " perc", true, false));
        contentPanel.add(createEditableField("Hozzávalók:", String.join(", ", recipe.getIngredients()), false, false));
        contentPanel.add(createEditableField("Elkészítés:", recipe.getDescription(), false, false));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        JButton closeButton = new JButton("Bezár");
        StyleManager.styleButton(closeButton);
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);
        buttonPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
