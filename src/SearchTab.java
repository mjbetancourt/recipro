import javafx.scene.control.*;

/**
 * Created by dillon on 9/29/16.
 */
class SearchTab extends Tab {

    private Connect connection;

    SearchTab(Connect connection) {
        super("Searching...");

        this.connection = connection;

        TableView table = new TableView();
        TableColumn recipeColumn = new TableColumn("Recipe");

        table.getColumns().add(recipeColumn);

        // tab needs a layout manager
        // maybe add a search field at the top to do a new search without returning to the home tab?
        setContent(table);
    }
}
