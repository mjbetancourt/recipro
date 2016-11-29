import java.util.ArrayList;

/**
 * Created by dillon on 9/19/16.
 */
public class Recipe {

    private String title;
    private String instructions;

    public Recipe() {}

    public Recipe(String title, String instructions) {
        this.title = title;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }
}
