package ui.components.selectpane;

import javafx.beans.property.*;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sorumi on 16/11/25.
 */
public class SelectPane extends AnchorPane {

    @FXML
    private Label typeLabel;

    @FXML
    private TilePane optionPane;

    private ObservableList<Node> labels;

    private StringProperty value = new SimpleStringProperty("");

    private ObjectProperty<EventHandler<Event>> onValueChanged = new SimpleObjectProperty<EventHandler<Event>>();

    public SelectPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelectPane.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        hideOptions();

        setOnValueChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

            }
        });

        labels = optionPane.getChildren();

        labels.addListener(new ListChangeListener() {

            @Override
            public void onChanged(Change c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        List list = c.getAddedSubList();
                        for (Object object : list) {
                            if (object instanceof Label) {
                                Label label = (Label) object;
                                label.setOnMouseClicked(new EventHandler<Event>() {
                                    @Override
                                    public void handle(Event event) {
                                        hideOptions();
                                        typeLabel.setText(label.getText());
                                        onValueChangedProperty().getValue().handle(event);
                                    }
                                });
                            }

                        }
                    }
                }

            }
        });
    }

    public ObservableList<Node> getLabels() {
        return optionPane.getChildren();
    }

    public StringProperty textProperty() {
        return typeLabel.textProperty();
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public DoubleProperty labelWidthProperty() {
        return typeLabel.prefWidthProperty();
    }

    public double getLabelWidth() {
        return typeLabel.getPrefWidth();
    }

    public void setLabelWidth(double width) {
        typeLabel.setPrefWidth(width);
    }

    public StringProperty valueProperty() {
        return typeLabel.textProperty();
    }

    public String getValue() {
        return value.getValue();
    }

    public void setValue(String value) {
        this.value.setValue(value);
    }



    public final ObjectProperty<EventHandler<Event>> onValueChangedProperty() {
        return onValueChanged;
    }

    public final void setOnValueChanged(EventHandler<Event> handler) {
        onValueChanged.set(handler);
    }

    public final EventHandler<Event> getOnValueChanged() {
        return onValueChanged.get();

    }
    @FXML
    public void showOptions() {
        typeLabel.setStyle("-fx-border-style: solid solid hidden solid;");
        optionPane.setManaged(true);
        optionPane.setVisible(true);
    }

    @FXML
    public void hideOptions() {
        typeLabel.setStyle("-fx-border-style: solid;");
        optionPane.setManaged(false);
        optionPane.setVisible(false);
    }
}
