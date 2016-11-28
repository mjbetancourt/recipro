import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.collections.*;
import javafx.beans.value.*;
import javafx.beans.property.*;
import javafx.util.Callback;

import java.sql.SQLException;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Dillon Fagan on 9/29/16.
 */
class SearchTab extends Tab {

    private TableView<Recipe> table = new TableView<>();
    private ObservableList<Recipe> rowData = FXCollections.observableArrayList();

    SearchTab() throws SQLException {
        super("Searching...");

        try {
            rowData = Connect.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setup();
    }

    SearchTab(String s) throws SQLException {
        super("Searching...");

        try {
            rowData = Connect.getByKeyword(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setup();
    }

    private void setup() {
        TableColumn recipeColumn = new TableColumn("Recipe");
        recipeColumn.setMinWidth(250);
        recipeColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn instructionsColumn = new TableColumn("Instructions");
        instructionsColumn.setCellValueFactory(new PropertyValueFactory("instructions"));

        setContent(table);

        table.getColumns().addAll(recipeColumn, instructionsColumn);
        table.setItems(rowData);

        setText(String.valueOf(table.getItems().size()) + " Recipes Found");
    }
}
