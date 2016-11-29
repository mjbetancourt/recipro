import javafx.scene.control.Tab;
import javafx.scene.web.HTMLEditor;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

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
    RecipeTab() throws SQLException {
        super("New Recipe");

        try {
            setRecipeEditable(true);
            isRecipeNew = true;

            toolBar.getItems().add(editButton);

            layout.getChildren().addAll(toolBar, editor);

            setContent(layout);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor to create a new RecipeTab that displays an
     * existing recipe in the database.
     * @param recipe an existing recipe to display
     */
    RecipeTab(Recipe recipe) throws SQLException {
        super(recipe.getTitle());

        try {
            setRecipeEditable(false);
            isRecipeNew = false;

            toolBar.getItems().add(editButton);

            layout.getChildren().addAll(toolBar, editor);

            setContent(layout);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines whether the recipe is view-only or editable by the user.
     */
    private void setRecipeEditable(boolean editable) throws SQLException {
        if (editable) {
            isRecipeEditable = true;
            editor.setDisable(false);

            if (isRecipeNew) {
                editButton.setText("Save");
                editButton.setOnAction(a -> {
                    try {
                        saveNewRecipe();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                editButton.setText("Done");
                editButton.setOnAction(a -> {
                    try {
                        saveChanges();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else {
            isRecipeEditable = false;
            editor.setDisable(true);

            editButton.setText("Edit");
            editButton.setOnAction(a -> {
                try {
                    setRecipeEditable(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void saveNewRecipe() throws SQLException {
        try {
            isRecipeNew = false;
            setRecipeEditable(false);
            Connect.add("New Recipe", " ", editor.getHtmlText());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("New recipe added.");
    }

    private void saveChanges() throws SQLException {
        try {
            setRecipeEditable(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Changes saved.");
    }
}
