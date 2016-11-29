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
            setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    SearchTab(String s) throws SQLException {
        super("Searching...");

        try {
            rowData = Connect.getByKeyword(s);
            setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setup() throws SQLException {
        TableColumn recipeColumn = new TableColumn("Recipe");
        recipeColumn.setMinWidth(300);
        recipeColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        setContent(table);

        table.setRowFactory(t -> {
            TableRow<Recipe> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        RecipeTab newRecipeTab = new RecipeTab(row.getItem());
                        getTabPane().getTabs().addAll(newRecipeTab);
                        getTabPane().getSelectionModel().select(newRecipeTab);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            return row ;
        });

        table.getColumns().addAll(recipeColumn);
        table.setItems(rowData);

        setText(String.valueOf(table.getItems().size()) + " Recipes Found");
    }
}
