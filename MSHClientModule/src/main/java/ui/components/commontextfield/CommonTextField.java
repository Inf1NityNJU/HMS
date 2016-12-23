package ui.components.commontextfield;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/27.
 */
public class CommonTextField extends TextField {


    public CommonTextField() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CommonTextField.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
