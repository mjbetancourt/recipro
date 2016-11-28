import javafx.scene.control.Tab;
import javafx.scene.web.HTMLEditor;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Created by dillon on 9/29/16.
 */
class RecipeTab extends Tab {

    private boolean isRecipeEditable;
    private boolean isRecipeNew;

    private VBox layout = new VBox();
    private ToolBar toolBar = new ToolBar();
    private Button editButton = new Button();
    private HTMLEditor editor = new HTMLEditor();

    /**
     * Constructor to create a new RecipeTab that accepts a new recipe
     * for the database.
     */
    RecipeTab() {
        super("New Recipe");

        setRecipeEditable(true);
        isRecipeNew = true;

        toolBar.getItems().add(editButton);

        layout.getChildren().addAll(toolBar, editor);

        setContent(layout);
    }

    /**
     * Constructor to create a new RecipeTab that displays an
     * existing recipe in the database.
     * @param recipe an existing recipe to display
     */
    RecipeTab(Recipe recipe) {
        super(recipe.getTitle());

        setRecipeEditable(false);
        isRecipeNew = false;

        toolBar.getItems().add(editButton);

        layout.getChildren().addAll(toolBar, editor);

        setContent(layout);
    }

    /**
     * Determines whether the recipe is view-only or editable by the user.
     */
    private void setRecipeEditable(boolean editable) {
        if (editable) {
            isRecipeEditable = true;
            editor.setDisable(false);

            if (isRecipeNew) {
                editButton.setText("Save");
                editButton.setOnAction(a -> {
                    saveNewRecipe();
                });
            } else {
                editButton.setText("Done");
                editButton.setOnAction(a -> {
                    saveChanges();
                });
            }
        } else {
            isRecipeEditable = false;
            editor.setDisable(true);

            editButton.setText("Edit");
            editButton.setOnAction(a -> {
                setRecipeEditable(true);
            });
        }
    }

    private void saveNewRecipe() {
        setRecipeEditable(false);
    }

    private void saveChanges() {
        setRecipeEditable(false);
    }
}
