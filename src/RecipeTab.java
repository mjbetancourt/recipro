import javafx.scene.control.Tab;
import javafx.scene.web.HTMLEditor;

/**
 * Created by dillon on 9/29/16.
 */
class RecipeTab extends Tab {

    private boolean isRecipeEditable;

    /**
     * Constructor to create a new RecipeTab that accepts a new recipe
     * for the database.
     */
    RecipeTab() {
        super("New Recipe");
        isRecipeEditable = true;

        // temporary editor; tab needs a layout manager
        // needs button to exit editing mode
        HTMLEditor editor = new HTMLEditor();

        setContent(editor);
    }

    /**
     * Constructor to create a new RecipeTab that displays an
     * existing recipe in the database.
     * @param recipe an existing recipe to display
     */
    RecipeTab(Recipe recipe) {
        super(recipe.getTitle());
        isRecipeEditable = false;
    }
}
