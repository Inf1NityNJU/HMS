package ui.components.navbutton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/16.
 */
public class NavButton extends HBox {

    @FXML
    private HBox hBox;

    @FXML
    private Rectangle currentRect;

    @FXML
    private Label iconLabel;

    @FXML
    private Label textLabel;

    private BooleanProperty isCurrent;

    public NavButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NavButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        isCurrent = new SimpleBooleanProperty(true);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    public StringProperty iconProperty() {
        return iconLabel.textProperty();
    }

    public String getIcon() {
        return iconProperty().get();
    }

    public void setIcon(String value) {
        iconProperty().set(value);
    }

    public BooleanProperty isCurrentProperty() {
        return this.isCurrent;
    }

    public Boolean getIsCurrentProperty() {
        return isCurrent.get();
    }

    public void setIsCurrentProperty(Boolean isCurrent) {

        if (isCurrent.booleanValue()) {
            currentRect.setOpacity(1);
            hBox.setStyle("-fx-background-color: #00000030");
        } else {
            currentRect.setOpacity(0);
            hBox.setStyle("-fx-background-color: #00000000");
        }
        this.isCurrent.setValue(isCurrent);
    }

}
