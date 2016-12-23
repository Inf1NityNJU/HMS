package ui.components.commontextarea;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/28.
 */
public class CommonTextArea extends TextArea {

    public CommonTextArea() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CommonTextArea.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
