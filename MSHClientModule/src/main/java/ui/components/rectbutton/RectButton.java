package ui.components.rectbutton;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/18.
 */
public class RectButton extends Button {

    public enum RectButtonSize{
        Large, Small
    }

    private String color = "00CCCC";

    private ObjectProperty<RectButtonSize> size = new SimpleObjectProperty<RectButtonSize>();

    private BooleanProperty isActive = new SimpleBooleanProperty();

    private BooleanProperty isAbled = new SimpleBooleanProperty();

    public RectButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RectButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        size.setValue(RectButtonSize.Large);
        setIsActiveProperty(true);
        setIsAbledProperty(true);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectProperty<RectButtonSize> sizeProperty() {
        return this.size;
    }

    public RectButtonSize getSizeProperty() {
        return size.get();
    }

    public void setSizeProperty(RectButtonSize size) {
        this.size.setValue(size);
        if (size.equals(RectButtonSize.Large)) {
            this.setPrefSize(100, 40);
        } else if (size.equals(RectButtonSize.Small)) {
            this.setPrefSize(80, 30);
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
        } else {
            color = "DDDDDD";
        }

        this.setDisable(!isAbled);
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
