package cookbook.model;
import java.util.List;

public class Recipe {
    private String name;
    private int timeToMake; //percbwn
    private List<String> ingredients;
    private String description;

    public Recipe(String name, int timeToMake, List<String> ingredients, String description) {
        this.name = name;
        this.timeToMake = timeToMake;
        this.ingredients = ingredients;
        this.description = description;
    }
    public String getName() {
        return name;
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
    public void setName(String name) {
        this.name = name;
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

}
