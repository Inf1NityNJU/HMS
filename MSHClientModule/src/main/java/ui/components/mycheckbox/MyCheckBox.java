package ui.components.mycheckbox;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/22.
 */
public class MyCheckBox extends HBox {

    @FXML
    private Label textLabel;

    @FXML
    private Rectangle borderRect;

    @FXML
    private Rectangle fillRect;

    private BooleanProperty isActive = new SimpleBooleanProperty(false);
    private BooleanProperty isAbled = new SimpleBooleanProperty(false);

    public MyCheckBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyCheckBox.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setIsActiveProperty(false);
        this.setIsAbledProperty(true);
    }

    public StringProperty textProperty() {
        return textLabel.textProperty();
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }


    public BooleanProperty isAbledProperty() {
        return this.isAbled;
    }

    public Boolean getIsAbledProperty() {
        return isAbled.get();
    }

    public void setIsAbledProperty(Boolean isAbled) {
        this.isAbled.setValue(isAbled);
        updateCurcor();
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

    private void updateView() {
        String gray = "B3B3B3";
        String color = "00CCCC";

        if (getIsActiveProperty().booleanValue()) {
            fillRect.setVisible(true);
            borderRect.setStroke(Color.web(color));
            textLabel.setTextFill(Color.web(color));
        } else {
            fillRect.setVisible(false);
            borderRect.setStroke(Color.web(gray));
            textLabel.setTextFill(Color.web(gray));
        }
    }

    private void updateCurcor() {
        if (getIsAbledProperty().booleanValue()) {
            this.setCursor(Cursor.HAND);
        } else {
            this.setCursor(Cursor.DEFAULT);
        }
    }

    @FXML
    private void click() {
        if (getIsAbledProperty().booleanValue()) {
            setIsActiveProperty(!getIsActiveProperty());
        }
    }

}
