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

    //Visszatér az összes tárolt recepttel
    public List<Recipe> getAllRecipes() {
        if (allRecipes == null || allRecipes.isEmpty()) {
            System.out.println("Nincsenek még felvett receptjeid.");
            return Collections.emptyList();
        } else {
            return allRecipes;
        }
    }

    //Új recept felvétele (ßJSonServices fájlkezelés miatt kell)
    public void addNewRecipe(Recipe recipe) {
        allRecipes.add(recipe);
        JSonServices.saveRecipesToJson(allRecipes);
    }

    //Üres fájlhoz üres lista is kell
    public void createNewEmptyJson(String filePath) {
        this.allRecipes = new ArrayList<>(); // Új üres lista
        JSonServices.setFileName(filePath); // Beállítjuk az új fájlt
        JSonServices.saveRecipesToJson(allRecipes, filePath); // Üres fájl mentése
        loadRecipesFromFile(filePath);
    }

    //Receptek betöltése a kapott fájlból
    public void loadRecipesFromFile(String filePath) {
        allRecipes = JSonServices.loadRecipesFromFile(filePath);
        JSonServices.setFileName(filePath);
    }

    //Receptek mentése a paraméterként kapott fájlba
    public void saveRecipesAs(String filePath) {
        JSonServices.saveRecipesToJson(allRecipes, filePath);
    }

    //Recept törlése
    public void deleteRecipe(Recipe recipe) {
        allRecipes.remove(recipe);
        JSonServices.saveRecipesToJson(allRecipes); // Frissíti a JSON fájlt a receptek aktuális listájával
    }

    //Recept szerkesztése
    public void updateRecipe(Recipe updatedRecipe) {
        int index = allRecipes.indexOf(updatedRecipe);
        if (index != -1) {
            allRecipes.set(index, updatedRecipe); // Frissítjük a meglévő receptet a listában
        }
        JSonServices.saveRecipesToJson(allRecipes); // Mentés az aktuális JSON fájlba
    }

    //Receptek törlése
    public void clearAllRecipes() {
        allRecipes.clear();  // Törli az összes receptet a lista memóriájából
    }

}
