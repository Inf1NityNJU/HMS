package ui.components.titlelabel;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/17.
 */
public class TitleLabel extends HBox {

    @FXML
    private Label titleLabel;

    public TitleLabel() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TitleLabel.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StringProperty textProperty() {
        return titleLabel.textProperty();
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }


}
