package cookbook.controller;

import cookbook.model.Recipe;
import cookbook.services.JSonServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class RecipeMethodsController {
    private List<Recipe> allRecipes;

    public RecipeMethodsController() {
        // Alapértelmezett fájlból betöltés
        this.allRecipes = JSonServices.loadRecipesFromFile(JSonServices.getDefaultFilePath());
    }


    public List<Recipe> getAllRecipes() {
        if (allRecipes == null || allRecipes.isEmpty()) {
            System.out.println("Nincsenek még felvett receptjeid.");
            return Collections.emptyList();
        } else {
            return allRecipes;
        }
    }

    public void addNewRecipe(Recipe recipe) {
        allRecipes.add(recipe);
        JSonServices.saveRecipesToJson(allRecipes);
    }

    public void createNewEmptyJson(String filePath) {
        this.allRecipes = new ArrayList<>(); // Új üres lista
        JSonServices.setFileName(filePath); // Beállítjuk az új fájlt
        JSonServices.saveRecipesToJson(allRecipes, filePath); // Üres fájl mentése
        loadRecipesFromFile(filePath);
    }

    public void loadRecipesFromFile(String filePath) {
        allRecipes = JSonServices.loadRecipesFromFile(filePath);
        JSonServices.setFileName(filePath);
    }

    public void saveRecipesAs(String filePath) {
        JSonServices.saveRecipesToJson(allRecipes, filePath);
    }


    public void deleteRecipe(Recipe recipe) {
        allRecipes.remove(recipe);
        JSonServices.saveRecipesToJson(allRecipes); // Frissíti a JSON fájlt a receptek aktuális listájával
    }

    public void updateRecipe(Recipe updatedRecipe) {
        int index = allRecipes.indexOf(updatedRecipe);
        if (index != -1) {
            allRecipes.set(index, updatedRecipe); // Frissítjük a meglévő receptet a listában
        }
        JSonServices.saveRecipesToJson(allRecipes); // Mentés az aktuális JSON fájlba
    }

    public void clearAllRecipes() {
        allRecipes.clear();  // Törli az összes receptet a lista memóriájából
    }


}
