package cookbook.view;

import cookbook.model.Recipe;

import javax.swing.*;
import java.awt.*;

public class RecipeDetailsDialog extends JDialog {
    public RecipeDetailsDialog(Frame parent, Recipe recipe) {
        super(parent, "Recept részletei", true);
        setSize(1200, 750);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        // Stílusos háttér beállítása
        getContentPane().setBackground(StyleManager.MAIN_BACKGROUND_COLOR);

        // Címkék és szövegmezők létrehozása a recept adatainak megjelenítésére
        JPanel dataPanel = new JPanel(new GridLayout(0, 1));
        dataPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR); // Háttérszín beállítása
        dataPanel.add(createLabelField("Recept neve:", recipe.getNameOfFood()));
        dataPanel.add(createLabelField("Elkészítési idő:", recipe.getTimeToMake() + " perc"));
        dataPanel.add(createLabelField("Hozzávalók:", String.join(", ", recipe.getIngredients())));
        dataPanel.add(createLabelField("Elkészítés:", recipe.getDescription()));

        add(dataPanel, BorderLayout.CENTER);

        // Gombok panelje
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR); // Háttérszín beállítása
        JButton closeButton = new JButton("Bezár");
        StyleManager.styleButton(closeButton); // Gomb stílusának alkalmazása
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createLabelField(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleManager.MAIN_BACKGROUND_COLOR); // Háttérszín beállítása
        JLabel jLabel = new JLabel(label);
        StyleManager.styleLabel(jLabel); // Címke stílusának alkalmazása
        panel.add(jLabel, BorderLayout.WEST);

        JTextArea textArea = new JTextArea(value);
        StyleManager.styleTextArea(textArea); // Szövegterület stílusának alkalmazása
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        StyleManager.styleScrollPane(scrollPane); // Görgetősáv stílusának alkalmazása
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
