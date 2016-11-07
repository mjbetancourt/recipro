import javafx.scene.control.Tab;

/**
 * Created by dillon on 9/29/16.
 */
class SearchTab extends Tab {

    private Connect connection;

    SearchTab(Connect connection) {
        super("Searching...");

        this.connection = connection;
    }
}
