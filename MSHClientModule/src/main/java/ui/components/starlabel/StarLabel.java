package ui.components.starlabel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by Sorumi on 16/12/3.
 */
public class StarLabel extends HBox {

    @FXML
    private Label numLabel;

    @FXML
    private Label starLabel;

    private IntegerProperty star = new SimpleIntegerProperty(0);

    public StarLabel() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StarLabel.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        starLabel.setText("\ue803");

    }

    public IntegerProperty starProperty() {
        return star;
    }

    public Integer getStar() {
        return star.getValue();
    }

    public void setStar(Integer star) {
        this.star.setValue(star);
        numLabel.setText(star + "");
    }


}
