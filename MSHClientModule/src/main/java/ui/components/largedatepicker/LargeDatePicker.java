package ui.components.largedatepicker;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by Sorumi on 16/12/2.
 */
public class LargeDatePicker extends DatePicker {

    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> minDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> maxDate = new SimpleObjectProperty<>();

    public LargeDatePicker() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LargeDatePicker.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return this.valueProperty();
    }

    public void setDate(LocalDate date) {
        setValue(date);
    }

    public LocalDate getDate() {
        return getValue();
    }

    public ObjectProperty<LocalDate> minDateProperty() {
        return this.minDate;
    }

    public void setMinDate(LocalDate date) {
        minDate.setValue(date);
        updateDateFactory();
    }

    public LocalDate getMinDate() {
        return minDate.getValue();
    }

    public ObjectProperty<LocalDate> maxDateProperty() {
        return this.maxDate;
    }

    public void setMaxDate(LocalDate date) {
        maxDate.setValue(date);
        updateDateFactory();
    }

    public LocalDate getMaxDate() {
        return maxDate.getValue();
    }


    private void updateDateFactory() {
        Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (getMinDate()!=null && item.isBefore(getMinDate())) {
                                    setDisable(true);
                                    setStyle("-fx-text-fill: #dddddd;");
                                }

                                if (getMaxDate()!=null && item.isAfter(getMaxDate())) {
                                    setDisable(true);
                                    setStyle("-fx-text-fill: #dddddd;");
                                }
                            }
                        };
                    }
                };
        this.setDayCellFactory(dayCellFactory);
    }

}
