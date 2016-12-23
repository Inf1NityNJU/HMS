package ui.components.sequencebutton;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by Sorumi on 16/12/17.
 */
public class SequenceButton extends HBox {

    public enum SequenceButtonState{
        Ascending, Descending
    }

    @FXML
    private Label textLabel;

    @FXML
    private Label iconLabel;


    private StringProperty text = new SimpleStringProperty();

    private BooleanProperty isActive = new SimpleBooleanProperty();

    private ObjectProperty<SequenceButtonState> state = new SimpleObjectProperty<>();

    public SequenceButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SequenceButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setIsActive(false);
        setState(SequenceButtonState.Descending);
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


    public BooleanProperty isActiveProperty() {
        return this.isActive;
    }

    public Boolean getIsActive() {
        return isActive.get();
    }

    public void setIsActive(Boolean isActive) {
        this.isActive.setValue(isActive);
        updateView();
    }

    private void updateView() {
        if (getIsActive().booleanValue()) {
            textLabel.setTextFill(Color.web("808080"));
            iconLabel.setVisible(true);
        } else {
            textLabel.setTextFill(Color.web("b4b4b4"));
            iconLabel.setVisible(false);
        }
    }


    public ObjectProperty<SequenceButtonState> stateProperty() {
        return this.state;
    }

    public SequenceButtonState getState() {
        return state.get();
    }

    public void setState(SequenceButtonState state) {
        this.state.setValue(state);
        if (state == SequenceButtonState.Ascending) {
            iconLabel.setText("\uf106");
        } else if (state == SequenceButtonState.Descending) {
            iconLabel.setText("\uf107");
        }
    }


}
