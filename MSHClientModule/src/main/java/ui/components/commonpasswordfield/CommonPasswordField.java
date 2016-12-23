package ui.components.commonpasswordfield;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/27.
 */
public class CommonPasswordField extends PasswordField {


    public CommonPasswordField() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CommonPasswordField.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
