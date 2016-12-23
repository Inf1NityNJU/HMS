package ui.components.statebutton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/17.
 */
public class StateButton extends Label {

    private StringProperty color = new SimpleStringProperty();

    private BooleanProperty isActive = new SimpleBooleanProperty();

    public StateButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StateButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        color.setValue("333333");
        setIsActiveProperty(false);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StringProperty colorProperty() {
        return this.color;
    }

    public void setColorProperty(String colorProperty) {
        this.color.set(colorProperty);

        updateView();
    }

    public String getColorProperty() {
        return this.color.get();
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
        String color = getColorProperty();
        if (color.equals("")) return;

        if (getIsActiveProperty().booleanValue()) {
            this.setStyle("-fx-background-color: #" + color +";"
                    + "-fx-border-color: #00000000;"
                    + "-fx-background-radius: 15px;"
                    + "-fx-border-radius: 15px;");
            this.setTextFill(Color.WHITE);
        } else {
            this.setStyle("-fx-background-color: #00000000;"
                    + "-fx-border-color: #" + color + ";"
                    + "-fx-background-radius: 15px;"
                    + "-fx-border-radius: 15px;");
            this.setTextFill(Color.web(color));
        }
    }

}
