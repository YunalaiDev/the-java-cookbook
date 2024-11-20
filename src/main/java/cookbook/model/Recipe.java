package cookbook.model;
import java.util.List;

public class Recipe {
    private String nameOfFood;
    private int timeToMake; //percbwn
    private List<String> ingredients;
    private String description;

    public Recipe(String name, int timeToMake, List<String> ingredients, String description) {
        this.nameOfFood = name;
        this.timeToMake = timeToMake;
        this.ingredients = ingredients;
        this.description = description;
    }
    public String getNameOfFood() {
        return nameOfFood;
    }
    public int getTimeToMake() {
        return timeToMake;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public String getDescription() {
        return description;
    }
    public void setNameOfFood(String nameOfFood) {
        this.nameOfFood = nameOfFood;
    }
    public void setTimeToMake(int timeToMake) {
        this.timeToMake = timeToMake;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Étel: " + nameOfFood + '\'' +
                ", elkészítési idő: " + timeToMake +
                " perc, hozzávalók" + listToString(ingredients) +
                ", elkészítés: " + description + '\'';
    }
    // Segédmetódus az ingredients lista egyben való megjelenítéséhez
    private String listToString(List<String> list) {
        return String.join(", ", list);
    }

}
