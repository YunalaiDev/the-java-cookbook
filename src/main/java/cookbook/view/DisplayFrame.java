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

public class DisplayFrame extends JFrame {
    private RecipeMethodsController recipeController;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    public DisplayFrame() {
        recipeController = new RecipeMethodsController();
        initializeUI();
    }

    private void initializeUI() {
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
        JMenuItem exitMenuItem = new JMenuItem("Kilépés");
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveAsMenuItem);
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

    private void addRecipe() {
        JDialog dialog = new JDialog(this, "Új Recept Hozzáadása", true);
        dialog.setMinimumSize(new Dimension(1200, 750));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        StyleManager.stylePanel(formPanel);

        JTextField nameField = new JTextField();
        StyleManager.styleTextField(nameField);
        JTextField timeField = new JTextField();
        StyleManager.styleTextField(timeField);
        JTextArea ingredientsArea = new JTextArea(5, 20);
        StyleManager.styleTextArea(ingredientsArea);
        JTextArea descriptionArea = new JTextArea(3, 20);
        StyleManager.styleTextArea(descriptionArea);

        formPanel.add(new JLabel("Recept neve:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Elkészítési idő (percben):"));
        formPanel.add(timeField);
        formPanel.add(new JLabel("Hozzávalók (vesszővel elválasztva):"));
        formPanel.add(new JScrollPane(ingredientsArea));
        formPanel.add(new JLabel("Elkészítés:"));
        formPanel.add(new JScrollPane(descriptionArea));

        JButton submitButton = new JButton("Hozzáad");
        StyleManager.styleButton(submitButton);
        submitButton.addActionListener(e -> {
            if (validateInput(nameField, timeField)) {
                String name = nameField.getText();
                int time = tryParseInt(timeField.getText());
                List<String> ingredients = Arrays.asList(ingredientsArea.getText().split(",\\s*"));
                String description = descriptionArea.getText();

                Recipe newRecipe = new Recipe(name, time, ingredients, description);
                recipeController.addNewRecipe(newRecipe);
                listRecipes();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Kérlek, minden mezőt helyesen tölts ki!", "Hibás adatok", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
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
        new RecipeDetailsDialog(this, recipe);
    }


    private void searchRecipe() {
        System.out.println("Recept keresése...");
    }

    private void editRecipe() {
        int index = list.getSelectedIndex();
        if (index != -1) {
            Recipe recipeToEdit = recipeController.getAllRecipes().get(index);
            JDialog dialog = new JDialog(this, "Recept Szerkesztése", true);
            dialog.setMinimumSize(new Dimension(1200, 750));
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(this);

            JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            StyleManager.stylePanel(formPanel);

            JTextField nameField = new JTextField(recipeToEdit.getNameOfFood());
            StyleManager.styleTextField(nameField);
            JTextField timeField = new JTextField(Integer.toString(recipeToEdit.getTimeToMake()));
            StyleManager.styleTextField(timeField);
            JTextArea ingredientsArea = new JTextArea(String.join(", ", recipeToEdit.getIngredients()));
            StyleManager.styleTextArea(ingredientsArea);
            JTextArea descriptionArea = new JTextArea(recipeToEdit.getDescription());
            StyleManager.styleTextArea(descriptionArea);

            formPanel.add(new JLabel("Recept neve:"));
            formPanel.add(nameField);
            formPanel.add(new JLabel("Elkészítési idő (percben):"));
            formPanel.add(timeField);
            formPanel.add(new JLabel("Hozzávalók (vesszővel elválasztva):"));
            formPanel.add(new JScrollPane(ingredientsArea));
            formPanel.add(new JLabel("Elkészítés:"));
            formPanel.add(new JScrollPane(descriptionArea));

            JButton updateButton = new JButton("Módosítás");
            StyleManager.styleButton(updateButton);
            updateButton.addActionListener(e -> {
                if (validateInput(nameField, timeField)) {
                    String name = nameField.getText();
                    int time = tryParseInt(timeField.getText());
                    List<String> ingredients = Arrays.asList(ingredientsArea.getText().split(",\\s*"));
                    String description = descriptionArea.getText();

                    recipeToEdit.setNameOfFood(name);
                    recipeToEdit.setTimeToMake(time);
                    recipeToEdit.setIngredients(ingredients);
                    recipeToEdit.setDescription(description);

                    recipeController.updateRecipe(recipeToEdit);
                    listRecipes();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Kérlek, minden mezőt helyesen tölts ki!", "Hibás adatok", JOptionPane.ERROR_MESSAGE);
                }
            });

            JButton cancelButton = new JButton("Mégse");
            StyleManager.styleButton(cancelButton);
            cancelButton.addActionListener(e -> dialog.dispose());

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(updateButton);
            buttonPanel.add(cancelButton);
            dialog.add(formPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);

            dialog.setVisible(true);
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
