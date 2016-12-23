package ui.components.radioboxpane;

import ui.components.mycheckbox.MyCheckBox;
import ui.components.mychoicebox.MyChoiceBox;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sorumi on 16/12/7.
 */
public class RadioBoxPane extends TilePane {

    private ObservableList<Node> items;

    private ObjectProperty<MyCheckBox> value = new SimpleObjectProperty<>();

    private StringProperty text = new SimpleStringProperty("");

    public RadioBoxPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RadioBoxPane.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        items = this.getChildren();

        items.addListener(new ListChangeListener() {

            @Override
            public void onChanged(Change c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        List list = c.getAddedSubList();
                        for (Object object : list) {
                            if (object instanceof MyCheckBox) {
                                MyCheckBox checkBox = (MyCheckBox) object;
                                checkBox.setOnMouseClicked(new EventHandler<Event>() {
                                    @Override
                                    public void handle(Event event) {
                                        setValue(checkBox);
                                    }
                                });
                            }

                        }
                    }
                }

            }
        });

    }

    public ObservableList<Node> getItems() {
        return this.getChildren();
    }

    public int getValueIndex() {
        return getItems().indexOf(getValue());
    }

    public void setValueIndex(int index) {
        MyCheckBox box = (MyCheckBox) getItems().get(index);
        setValue(box);
    }


    private ObjectProperty<MyCheckBox> valueProperty() {
        return value;
    }

    private MyCheckBox getValue() {
        return value.getValue();
    }

    private void setValue(MyCheckBox checkBox) {
        if (getText().equals(checkBox.getText())) {
            checkBox.setIsActiveProperty(false);
            value.setValue(null);
            text.setValue("");
        } else {
            for (Object o : items) {
                if (o instanceof MyCheckBox) {
                    MyCheckBox c = (MyCheckBox) o;
                    c.setIsActiveProperty(false);
                }
            }
            checkBox.setIsActiveProperty(true);
            value.setValue(checkBox);
            text.setValue(checkBox.getText());
        }

    }


    public StringProperty textProperty() {
        return text;
    }

    public String getText() {
        return textProperty().getValue();
    }

}
