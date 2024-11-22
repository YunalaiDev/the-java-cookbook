package cookbook.view;

import cookbook.controller.RecipeMethodsController;
import cookbook.model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RecipeEditDialog extends JDialog {
    private final RecipeMethodsController controller;
    private Recipe recipe;
    private JTextField nameField;
    private JTextField timeField;
    private JTextArea ingredientsArea;
    private JTextArea descriptionArea;
    private boolean isUpdated = false;

    public RecipeEditDialog(Frame owner, RecipeMethodsController controller, Recipe recipe) {
        super(owner, "Recept Szerkesztése", true);
        this.controller = controller;
        this.recipe = recipe;
        initializeUI();
    }

    private void initializeUI() {
        setSize(800, 600);
        setLocationRelativeTo(getParent());

        // Tartalom panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        // Recept neve
        contentPanel.add(createEditableField("Recept neve:", recipe.getNameOfFood(), true));

        // Elkészítési idő
        contentPanel.add(createEditableField("Elkészítési idő (percben):", Integer.toString(recipe.getTimeToMake()), true));

        // Hozzávalók
        contentPanel.add(createEditableField("Hozzávalók (vesszővel elválasztva):", String.join(", ", recipe.getIngredients()), false));

        // Elkészítés
        contentPanel.add(createEditableField("Elkészítés:", recipe.getDescription(), false));

        // Görgetősáv hozzáadása hosszú tartalom esetén
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        // Gombok
        JButton updateButton = new JButton("Módosítás");
        StyleManager.styleButton(updateButton);
        updateButton.addActionListener(e -> {
            if (validateInput()) {
                updateRecipe();
                isUpdated = true; // Jelzi, hogy történt módosítás
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

    private JPanel createEditableField(String label, String value, boolean isSingleLine) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        JLabel jLabel = new JLabel(label);
        StyleManager.styleLabel(jLabel);
        panel.add(jLabel, BorderLayout.NORTH);

        if (isSingleLine) {
            JTextField textField = new JTextField(value);
            StyleManager.styleTextField(textField);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleManager.BORDER_COLOR, 1), // Szegély
                    BorderFactory.createEmptyBorder(15, 25, 15, 25) // Belső margó
            ));
            if (label.startsWith("Elkészítési idő")) {
                timeField = textField;
            } else {
                nameField = textField;
            }
            panel.add(textField, BorderLayout.CENTER);
        } else {
            JTextArea textArea = new JTextArea(value);
            textArea.setLineWrap(true); // Automatikus tördelés
            textArea.setWrapStyleWord(true); // Szavak tördelése
            StyleManager.styleTextArea(textArea);
            textArea.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleManager.BORDER_COLOR, 1), // Szegély
                    BorderFactory.createEmptyBorder(20, 20, 20, 20) // Külső margó
            ));
            StyleManager.styleScrollPane(scrollPane);

            if (label.startsWith("Hozzávalók")) {
                ingredientsArea = textArea;
            } else {
                descriptionArea = textArea;
            }

            panel.add(scrollPane, BorderLayout.CENTER);
        }

        return panel;
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
