import java.util.ArrayList;

/**
 * Created by dillon on 9/19/16.
 */
public class Recipe {

    private String title;
    private ArrayList<String> ingredients;
    private String instructions;

    public Recipe(String title, ArrayList<String> ingredients, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}