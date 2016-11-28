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

    private Connect connection;
    private TableView table = new TableView();
    private ObservableList<String> rowData = FXCollections.observableArrayList();

    SearchTab(Connect connection) throws SQLException {
        super("Searching...");

        this.connection = connection;

        try {
            populateTableWithResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableColumn<ObservableList<String>, String> recipeColumn = new TableColumn<>("Recipe");
        recipeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> cdf) {
                return new SimpleStringProperty(cdf.getValue().get(0));
            }
        });

        table.getColumns().add(recipeColumn);

        // tab needs a layout manager
        // maybe add a search field at the top to do a new search without returning to the home tab?
        setContent(table);
    }

    private void populateTableWithResults() throws SQLException {
        try {
            rowData = connection.getResults();
            table.getItems().setAll(rowData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
