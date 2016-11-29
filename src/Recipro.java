import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.SQLException;

/**
 * Created by Dillon Fagan on 9/13/16.
 */
public class Recipro extends Application {

    /**
     * The TabPane stores and manages all open tabs.
     */
    private TabPane tabs;

    /**
     * Initializes a new Stage as the main window.
     */
    @Override
    public void start(Stage primaryStage) throws SQLException {
        // Primary layout for the window
        VBox rootLayout = new VBox();

        // Set up the window
        primaryStage.setTitle("Recipro");
        primaryStage.setScene(new Scene(rootLayout, 860, 660));

        // Initialize the tab manager and add a HomeTab
        tabs = new TabPane();
        tabs.setPrefSize(860, 660);
        tabs.getTabs().add(new HomeTab());

        try {
            // Add all items to the root layout
            rootLayout.getChildren().addAll(createMenuBar(), tabs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Show the window
        primaryStage.show();
    }

    /**
     * Returns a completed MenuBar for the window.
     */
    private MenuBar createMenuBar() throws SQLException {
        MenuBar menuBar  = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem newRecipeCommand = new MenuItem("New Recipe...");
        newRecipeCommand.setOnAction(a -> {
            try {
                tabs.getTabs().add(new RecipeTab());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        fileMenu.getItems().add(newRecipeCommand);

        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }
}
