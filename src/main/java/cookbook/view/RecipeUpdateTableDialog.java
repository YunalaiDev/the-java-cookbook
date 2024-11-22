package cookbook.view;

import cookbook.model.Recipe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecipeUpdateTableDialog extends JDialog {
    public RecipeUpdateTableDialog(Frame owner, Recipe recipe) {
        super(owner, "Módosított recept adatai", true);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(owner);

        // Táblázat adatainak előkészítése
        String[] columnNames = {"Tulajdonság", "Érték"};
        Object[][] data = {
                {"Recept neve", recipe.getNameOfFood()},
                {"Elkészítési idő (percben)", recipe.getTimeToMake()},
                {"Hozzávalók", String.join(", ", recipe.getIngredients())},
                {"Elkészítés", recipe.getDescription()}
        };

        // Táblázat létrehozása
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        table.setEnabled(false); // A táblázat nem szerkeszthető
        JScrollPane tableScrollPane = new JScrollPane(table);

        // OK gomb
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        // Layout beállítása
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
