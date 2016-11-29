import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.collections.*;
import javafx.beans.value.*;
import javafx.beans.property.*;
import javafx.util.Callback;

import java.sql.SQLException;

/**
 * Created by Dillon Fagan on 9/29/16.
 */
class SearchTab extends Tab {

    private TableView table = new TableView();
    private ObservableList<String> rowData = FXCollections.observableArrayList();

    SearchTab(String s) throws SQLException {
        super("Searching...");

        try {
            rowData = Connect.getByKeyword(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableColumn<ObservableList<String>, String> recipeColumn = new TableColumn<>("Recipe");

        table.getColumns().add(recipeColumn);

        setContent(table);

        table.setItems(rowData);

        setText(String.valueOf(table.getItems().size()) + " Recipes Found");
    }
}
