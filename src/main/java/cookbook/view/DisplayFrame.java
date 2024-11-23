package cookbook.view;

import cookbook.controller.RecipeMethodsController;
import cookbook.model.Recipe;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayFrame extends JFrame {
    private RecipeMethodsController recipeController;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    public JTextField searchField;
    public JRadioButton nameRadioButton;
    public JRadioButton ingredientsRadioButton;
    public JRadioButton timeRadioButton;
    private ButtonGroup searchGroup;
    public JPanel searchResultsPanel;

    public DisplayFrame() {
        recipeController = new RecipeMethodsController();
        initializeUI();
    }

    public void initializeUI() {
        setTitle("The Java Cookbook");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/dish.png")); //A logo készítője: Pause08 (via flaticon.com)
        Image image = originalIcon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        setIconImage(image);
        if (System.getProperty("os.name").startsWith("Mac")) { // macOS specifikus beállítás hogy én is láthassam az ikont:)
            Taskbar.getTaskbar().setIconImage(image);
        }
        // Menüsáv létrehozása
        JMenuBar menuBar = new JMenuBar();

        // Fájl menü létrehozása
        JMenu fileMenu = new JMenu("Fájl");
        JMenuItem loadMenuItem = new JMenuItem("JSON betöltése...");
        JMenuItem saveAsMenuItem = new JMenuItem("Mentés másként...");
        JMenuItem newEmptyMenuItem = new JMenuItem("Üres...");
        JMenuItem exitMenuItem = new JMenuItem("Kilépés");
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(newEmptyMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // Egyéb menük létrehozása
        JMenuItem listMenuItem = new JMenuItem("Receptek listázása");
        JMenuItem addMenuItem = new JMenuItem("Recept hozzáadása");
        JMenuItem searchMenuItem = new JMenuItem("Recept keresése");
        JMenuItem editMenuItem = new JMenuItem("Recept szerkesztése");
        JMenuItem deleteMenuItem = new JMenuItem("Recept törlése");
        menuBar.add(listMenuItem);
        menuBar.add(addMenuItem);
        menuBar.add(searchMenuItem);
        menuBar.add(editMenuItem);
        menuBar.add(deleteMenuItem);

        setJMenuBar(menuBar);

        // Menü eseménykezelők
        loadMenuItem.addActionListener(e -> loadNewRecipeFile());
        saveAsMenuItem.addActionListener(e -> saveRecipesAs());
        newEmptyMenuItem.addActionListener(e -> createNewEmptyFile());
        exitMenuItem.addActionListener(e -> System.exit(0));

        listMenuItem.addActionListener(e -> listRecipes());
        addMenuItem.addActionListener(e -> addRecipe());
        searchMenuItem.addActionListener(e -> searchRecipe());
        editMenuItem.addActionListener(e -> editRecipe());
        deleteMenuItem.addActionListener(e -> deleteRecipe());
        JPanel mainPanel = new JPanel(new BorderLayout());
        StyleManager.stylePanel(mainPanel);
        // JList és JScrollPane
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        // JList stílusának alkalmazása
        StyleManager.styleList(list);
        JScrollPane listScrollPane = new JScrollPane(list);
        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double-click
                    int index = list.getSelectedIndex();
                    if (index != -1) {
                        showRecipeDetails(recipeController.getAllRecipes().get(index));
                    }
                }
            }
        });
        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
        listRecipes();
    }

    private void listRecipes() {
        List<Recipe> recipes = recipeController.getAllRecipes();
        listModel.clear();
        recipes.forEach(recipe -> listModel.addElement(recipe.getNameOfFood()));
    }

    private void saveRecipesAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Mentés másként...");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON fájlok", "json"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON fájlok (*.json)", "json"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Ellenőrizzük, hogy a fájl .json kiterjesztéssel rendelkezik-e
            if (!fileToSave.getName().endsWith(".json")) {
                fileToSave = new File(fileToSave.toString() + ".json");
            }
            recipeController.saveRecipesAs(fileToSave.getAbsolutePath());
        }
    }

    private void loadNewRecipeFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON Fájlok", "json"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            recipeController.loadRecipesFromFile(selectedFile.getAbsolutePath());
            listRecipes(); // Frissíti a listát az új fájl adataival
        }
    }

    private void createNewEmptyFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Új üres JSON fájl létrehozása...");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON fájlok", "json"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON fájlok (*.json)", "json"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Ellenőrizzük, hogy a fájl .json kiterjesztéssel rendelkezik-e
            if (!fileToSave.getName().endsWith(".json")) {
                fileToSave = new File(fileToSave.toString() + ".json");
            }
            recipeController.createNewEmptyJson(fileToSave.getAbsolutePath());
            listRecipes();
        }

    }

    private void addRecipe() {
        RecipeAddDialog dialog = new RecipeAddDialog(this, recipeController);
        dialog.setVisible(true);
        listRecipes(); // Frissíti a receptek listáját a hozzáadás után
    }

    private boolean validateInput(JTextField nameField, JTextField timeField) {
        return !nameField.getText().trim().isEmpty() && tryParseInt(timeField.getText()) > 0;
    }

    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void showRecipeDetails(Recipe recipe) {
        RecipeDetailsDialog dialog = new RecipeDetailsDialog(this, recipe);
        dialog.setVisible(true);
    }

    public void searchRecipe() {
        JDialog searchDialog = new JDialog(this, "Recept keresése", true);
        searchDialog.setSize(900, 600);
        searchDialog.setLocationRelativeTo(this);

        JPanel searchPanel = new JPanel(new BorderLayout());
        StyleManager.stylePanel(searchPanel);

        // Kereső mező
        searchField = new JTextField(20);
        StyleManager.styleTextField(searchField);
        searchPanel.add(searchField, BorderLayout.NORTH);

        // Rádiógombok
        nameRadioButton = new JRadioButton("Recept neve");
        ingredientsRadioButton = new JRadioButton("Recept összetevői");
        timeRadioButton = new JRadioButton("Recept ideje (percben kevesebb mint)");
        StyleManager.styleRadioButton(nameRadioButton);
        StyleManager.styleRadioButton(ingredientsRadioButton);
        StyleManager.styleRadioButton(timeRadioButton);
        nameRadioButton.setSelected(true);

        searchGroup = new ButtonGroup();
        searchGroup.add(nameRadioButton);
        searchGroup.add(ingredientsRadioButton);
        searchGroup.add(timeRadioButton);

        JPanel radioPanel = new JPanel(new GridLayout(3, 1));
        radioPanel.add(nameRadioButton);
        radioPanel.add(ingredientsRadioButton);
        radioPanel.add(timeRadioButton);
        searchPanel.add(radioPanel, BorderLayout.CENTER);

        // Keresés gomb
        JButton searchButton = new JButton("Keresés");
        StyleManager.styleButton(searchButton);
        searchButton.addActionListener(e -> performSearch());
        searchPanel.add(searchButton, BorderLayout.SOUTH);

        // Eredmények megjelenítése
        searchResultsPanel = new JPanel();
        searchResultsPanel.setLayout(new BoxLayout(searchResultsPanel, BoxLayout.Y_AXIS));
        StyleManager.stylePanel(searchResultsPanel);
        JScrollPane scrollPane = new JScrollPane(searchResultsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        searchDialog.add(searchPanel, BorderLayout.NORTH);
        searchDialog.add(scrollPane, BorderLayout.CENTER);

        searchDialog.setVisible(true);
    }

    private void performSearch() {
        String searchText = searchField.getText().trim();
        List<Recipe> filteredRecipes = recipeController.getAllRecipes();

        if (nameRadioButton.isSelected()) {
            filteredRecipes = filteredRecipes.stream()
                    .filter(recipe -> recipe.getNameOfFood().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());
        } else if (ingredientsRadioButton.isSelected()) {
            filteredRecipes = filteredRecipes.stream()
                    .filter(recipe -> recipe.getIngredients().stream().anyMatch(ing -> ing.toLowerCase().contains(searchText.toLowerCase())))
                    .collect(Collectors.toList());
        } else if (timeRadioButton.isSelected()) {
            int maxTime = tryParseInt(searchText);
            if (maxTime > 0) {
                filteredRecipes = filteredRecipes.stream()
                        .filter(recipe -> recipe.getTimeToMake() <= maxTime)
                        .collect(Collectors.toList());
            }
        }
        updateSearchResults(filteredRecipes);
    }

    private void updateSearchResults(List<Recipe> recipes) {
        searchResultsPanel.removeAll();
        if (recipes.isEmpty()) {
            searchResultsPanel.add(new JLabel("Nincs találat"));
        } else {
            for (Recipe recipe : recipes) {
                JPanel recipePanel = new JPanel(new BorderLayout());
                JLabel nameLabel = new JLabel(recipe.getNameOfFood(), JLabel.LEFT);
                nameLabel.setFont(StyleManager.MAIN_FONT);
                recipePanel.add(nameLabel, BorderLayout.CENTER);
                recipePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                recipePanel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        showRecipeDetails(recipe);
                    }
                });
                searchResultsPanel.add(recipePanel);
            }
        }
        searchResultsPanel.revalidate();
        searchResultsPanel.repaint();
    }

    private void editRecipe() {
        int index = list.getSelectedIndex();
        if (index != -1) {
            Recipe recipeToEdit = recipeController.getAllRecipes().get(index);
            RecipeEditDialog dialog = new RecipeEditDialog(this, recipeController, recipeToEdit);
            dialog.setVisible(true);

            if (dialog.isUpdated()) { // Csak akkor jelenik meg a JTable, ha történt módosítás
                listRecipes(); // Frissíti a listát a szerkesztés után
                Recipe updatedRecipe = recipeController.getAllRecipes().get(index);
                RecipeUpdateTableDialog updateDialog = new RecipeUpdateTableDialog(this, updatedRecipe);
                updateDialog.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Válassz egy receptet a szerkesztéshez!", "Hiba", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteRecipe() {
        int index = list.getSelectedIndex();
        if (index != -1) {
            Recipe recipeToDelete = recipeController.getAllRecipes().get(index);
            int response = JOptionPane.showConfirmDialog(this,
                    "Biztosan törölni szeretnéd a következő receptet: " + recipeToDelete.getNameOfFood() + "?",
                    "Recept törlése",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                recipeController.deleteRecipe(recipeToDelete);
                listRecipes(); // Frissíti a listát a törlés után
                JOptionPane.showMessageDialog(this, "A recept törölve!", "Törlés", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Válassz egy receptet a törléshez!", "Hiba", JOptionPane.WARNING_MESSAGE);
        }
    }

}
