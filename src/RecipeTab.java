import javafx.scene.control.Tab;
import javafx.scene.web.HTMLEditor;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

/**
 * Created by dillon on 9/29/16.
 */
class RecipeTab extends Tab {

    private Recipe recipe = new Recipe();
    private boolean isRecipeEditable;

    private VBox layout = new VBox();
    private ToolBar toolBar = new ToolBar();
    private TextField titleField = new TextField();
    private Button editButton = new Button();
    private Button deleteButton = new Button("Delete");
    private HTMLEditor editor = new HTMLEditor();

    /**
     * Constructor to create a new RecipeTab that accepts a new recipe
     * for the database.
     */
    RecipeTab() throws SQLException {
        super("New Recipe");

        titleField.setPromptText("New Recipe");

        editButton.setText("Save");
        editButton.setOnAction(a -> {
            try {
                saveNewRecipe();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        setup();
    }

    /**
     * Constructor to create a new RecipeTab that displays an
     * existing recipe in the database.
     * @param recipe an existing recipe to display
     */
    RecipeTab(Recipe recipe) throws SQLException {
        super(recipe.getTitle());

        this.recipe = recipe;

        titleField.setText(recipe.getTitle());
        editor.setHtmlText(recipe.getInstructions());

        try {
            setRecipeEditable(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setup();
    }

    private void setup() {
        deleteButton.setOnAction(a -> {
            delete();
        });

        toolBar.getItems().addAll(titleField, deleteButton, editButton);
        layout.getChildren().addAll(toolBar, editor);

        setContent(layout);
    }

    /**
     * Determines whether the recipe is view-only or editable by the user.
     */
    private void setRecipeEditable(boolean editable) throws SQLException {
        if (editable) {
            isRecipeEditable = true;
            titleField.setDisable(false);
            editor.setDisable(false);

            editButton.setText("Save Changes");
            editButton.setOnAction(a -> {
                try {
                    saveChanges();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            isRecipeEditable = false;
            titleField.setDisable(true);
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
            setRecipeEditable(false);

            String title;
            if (titleField.getText() == "") {
                title = "New Recipe";
            } else {
                title = titleField.getText();
            }

            Connect.add(title, " ", editor.getHtmlText());

            setText(titleField.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveChanges() throws SQLException {
        try {
            setRecipeEditable(false);
            Connect.update(0, titleField.getText(), " ", editor.getHtmlText());

            setText(titleField.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user whether or not to delete the open recipe.
     * If confirmed, the record is deleted (if it exists) and this tab is closed.
     */
    private void delete() {
        // prompt
        // delete recipe
        getTabPane().getTabs().removeAll(this);
    }
}
