package ui.components.tinybutton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/18.
 */
public class TinyButton extends Button {

    private String color = "00CCCC";

    private BooleanProperty isActive = new SimpleBooleanProperty();

    private BooleanProperty isAbled = new SimpleBooleanProperty();

    public TinyButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TinyButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        setIsActiveProperty(true);
        setIsAbledProperty(true);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BooleanProperty isActiveProperty() {
        return this.isActive;
    }

    public Boolean getIsActiveProperty() {
        return isActive.get();
    }

    public void setIsActiveProperty(Boolean isActive) {
        this.isActive.setValue(isActive);

        updateView();
    }

    public BooleanProperty isAbledProperty() {
        return this.isAbled;
    }

    public Boolean getIsAbledProperty() {
        return isAbled.get();
    }

    public void setIsAbledProperty(Boolean isAbled) {
        this.isAbled.setValue(isAbled);

        if (isAbled.booleanValue()) {
            color = "00CCCC";
            this.setCursor(Cursor.HAND);
        } else {
            color = "DDDDDD";
            this.setCursor(Cursor.DEFAULT);
        }

        updateView();
    }

    private void updateView() {
        if (getIsActiveProperty().booleanValue()) {
            this.setStyle("-fx-background-color: #" + color + ";"
                    + "-fx-border-color: #" + color + ";");
            this.setTextFill(Color.WHITE);
        } else {
            this.setStyle("-fx-background-color: #00000000;"
                    + "-fx-border-color: #" + color + ";");
            this.setTextFill(Color.web(color));
        }
    }

}
