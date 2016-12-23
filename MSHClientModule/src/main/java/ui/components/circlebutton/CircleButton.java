package ui.components.circlebutton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Sorumi on 16/11/27.
 */
public class CircleButton extends Label {

    public enum CircleButtonType {
        Add, Minus, Search
    }

    @FXML
    private Label button;

    private ObjectProperty<CircleButtonType> type = new SimpleObjectProperty<CircleButtonType>();

    private BooleanProperty abled = new SimpleBooleanProperty(true);

    public CircleButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CircleButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public BooleanProperty abledProperty() {
        return abled;
    }

    public Boolean getAbled() {
        return abled.getValue();
    }

    public void setAbled(Boolean abled) {
        this.abled.setValue(abled);
        this.setDisable(!abled);
        if (abled) {
            this.setStyle("-fx-background-color: #00cccc");
        } else {
            this.setStyle("-fx-background-color: #cccccc");
        }
    }

    public ObjectProperty<CircleButtonType> typeProperty() {
        return this.type;
    }

    public CircleButtonType getTypeProperty() {
        return type.get();
    }

    public void setTypeProperty(CircleButtonType type) {
        this.type.setValue(type);

        char code = '\ue800';

        switch (type) {
            case Add:
                code = '\ue800';
                break;
            case Minus:
                code = '\ue801';
                break;
            case Search:
                code = '\ue802';
                break;
        }

        button.setText(code + "");
    }

}
