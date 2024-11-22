package cookbook.view;

import cookbook.controller.RecipeMethodsController;
import cookbook.model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class RecipeAddDialog extends JDialog {
    private RecipeMethodsController controller;
    private JTextField nameField;
    private JTextField timeField;
    private JTextArea ingredientsArea;
    private JTextArea descriptionArea;

    public RecipeAddDialog(Frame owner, RecipeMethodsController controller) {
        super(owner, "Új Recept Hozzáadása", true);
        this.controller = controller;
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
        contentPanel.add(createEditableField("Recept neve:", ""));

        // Elkészítési idő
        contentPanel.add(createEditableField("Elkészítési idő (percben):", ""));

        // Hozzávalók
        contentPanel.add(createEditableField("Hozzávalók (vesszővel elválasztva):", ""));

        // Elkészítés
        contentPanel.add(createEditableField("Elkészítés:", ""));

        // Görgetősáv hozzáadása hosszú tartalom esetén
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        // Gombok
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

        // Elrendezés
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createEditableField(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        JLabel jLabel = new JLabel(label);
        StyleManager.styleLabel(jLabel);
        panel.add(jLabel, BorderLayout.NORTH);

        if (label.startsWith("Elkészítési idő") || label.startsWith("Recept neve")) {
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

    private void addRecipe() {
        String name = nameField.getText();
        int time = Integer.parseInt(timeField.getText());
        List<String> ingredients = Arrays.asList(ingredientsArea.getText().split(",\\s*"));
        String description = descriptionArea.getText();

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
