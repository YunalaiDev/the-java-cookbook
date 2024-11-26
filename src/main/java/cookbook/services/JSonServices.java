package cookbook.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cookbook.model.Recipe;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSonServices {
    private static String FILE_NAME = "src/main/resources/recipes.json"; // Alapértelmezett fájl útvonal

    public static String getDefaultFilePath() {
        return FILE_NAME;
    }

    public static void setFileName(String newFileName) {
        FILE_NAME = newFileName;
    }
    
    public static List<Recipe> loadRecipesFromFile(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type typeOfT = new TypeToken<List<Recipe>>() {}.getType();
            List<Recipe> recipes = gson.fromJson(reader, typeOfT);
            return recipes != null ? recipes : new ArrayList<>(); // Ha az eredmény null, üres listát adunk vissza
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "A fájl nem található: " + filePath, "Fájl Hiba", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>(); // Ha a fájl nem létezik, üres listát adunk vissza
        } catch (com.google.gson.JsonSyntaxException e) {
            JOptionPane.showMessageDialog(null, "Hibás JSON formátum: " + e.getMessage(), "JSON Hiba", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>(); // Ha a JSON nem megfelelő, üres listát adunk vissza
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hiba történt a fájl olvasása során: " + e.getMessage(), "Olvasási Hiba", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>(); // Ha olvasási hiba lép fel, üres listát adunk vissza
        }
    }

    // Receptek mentése az alapértelmezett fájlba
    public static void saveRecipesToJson(List<Recipe> recipes) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            new Gson().toJson(recipes, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hiba történt a receptek mentésekor: " + e.getMessage(), "Mentési Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Receptek mentése más, megadott fájlba
    public static void saveRecipesToJson(List<Recipe> recipes, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            new Gson().toJson(recipes, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hiba történt a receptek mentésekor a következő helyen: " + filePath, "Mentési Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
}
