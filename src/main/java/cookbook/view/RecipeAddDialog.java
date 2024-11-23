package cookbook.view;

import cookbook.controller.RecipeMethodsController;
import cookbook.model.BaseRecipeDialog;
import cookbook.model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class RecipeAddDialog extends BaseRecipeDialog {
    private RecipeMethodsController controller;

    public RecipeAddDialog(Frame owner, RecipeMethodsController controller) {
        super(owner, "Új Recept Hozzáadása");
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        contentPanel.add(createEditableField("Recept neve:", "", true, true));
        contentPanel.add(createEditableField("Elkészítési idő (percben):", "", true, true));
        contentPanel.add(createEditableField("Hozzávalók (vesszővel elválasztva):", "", false, true));
        contentPanel.add(createEditableField("Elkészítés:", "", false, true));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        JButton addButton = new JButton("Hozzáad");
        StyleManager.styleButton(addButton);
        addButton.addActionListener(e -> {
            if (validateInput()) {
                addRecipe();
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
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addRecipe() {
        String name = nameField.getText();
        int time = Integer.parseInt(timeField.getText());
        List<String> ingredients = Arrays.asList(ingredientsArea.getText().split("[,\\n]+\\s*"));
        String description = descriptionArea.getText().replaceAll("\\s*\\n+\\s*", " ").trim();

        Recipe newRecipe = new Recipe(name, time, ingredients, description);
        controller.addNewRecipe(newRecipe);

        JOptionPane.showMessageDialog(this, "A recept hozzáadva!", "Hozzáadás", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean validateInput() {
        try {
            Integer.parseInt(timeField.getText());
            return !nameField.getText().trim().isEmpty() && !ingredientsArea.getText().trim().isEmpty() && !descriptionArea.getText().trim().isEmpty();
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
