package ui.components.mychoicebox;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/28.
 */
public class MyChoiceBox extends ChoiceBox {

    public MyChoiceBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyChoiceBox.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
