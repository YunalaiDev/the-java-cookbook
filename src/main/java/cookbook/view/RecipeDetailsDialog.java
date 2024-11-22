package cookbook.view;

import cookbook.model.Recipe;

import javax.swing.*;
import java.awt.*;

public class RecipeDetailsDialog extends JDialog {
    public RecipeDetailsDialog(Frame parent, Recipe recipe) {
        super(parent, "Recept részletei", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);

        // Tartalom panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        // Recept neve
        contentPanel.add(createStyledTextArea("Recept neve:", recipe.getNameOfFood()));

        // Elkészítési idő
        contentPanel.add(createStyledTextArea("Elkészítési idő:", recipe.getTimeToMake() + " perc"));

        // Hozzávalók
        contentPanel.add(createStyledTextArea("Hozzávalók:", String.join(", ", recipe.getIngredients())));

        // Elkészítés
        contentPanel.add(createStyledTextArea("Elkészítés:", recipe.getDescription()));

        // Görgetősáv hozzáadása hosszú tartalom esetén
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        // Bezárás gomb
        JButton closeButton = new JButton("Bezár");
        StyleManager.styleButton(closeButton);
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);
        buttonPanel.add(closeButton);

        // Elrendezés
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createStyledTextArea(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        JLabel jLabel = new JLabel(label);
        StyleManager.styleLabel(jLabel);
        panel.add(jLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea(value);
        textArea.setLineWrap(true); // Automatikus tördelés
        textArea.setWrapStyleWord(true); // Szavak tördelése
        textArea.setEditable(false);
        StyleManager.styleTextArea(textArea);

        // Belső margó hozzáadása
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleManager.BORDER_COLOR, 1), // Szegély
                BorderFactory.createEmptyBorder(20, 20, 20, 20) // Külső margó
        ));
        StyleManager.styleScrollPane(scrollPane);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

}
