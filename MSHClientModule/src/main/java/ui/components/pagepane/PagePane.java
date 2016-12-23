package ui.components.pagepane;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by Sorumi on 16/12/3.
 */
public class PagePane extends HBox {

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private HBox pageHBox;

    private Button currentButton;

    private IntegerProperty count = new SimpleIntegerProperty(1);
    private IntegerProperty currentPage = new SimpleIntegerProperty(1);

    private ObjectProperty<EventHandler<Event>> onPageChanged = new SimpleObjectProperty<EventHandler<Event>>();

    public PagePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PagePane.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        leftButton.setText("\uf104");
        rightButton.setText("\uf105");

        setPageCount();

        setOnPageChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

            }
        });

        leftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setCurrentPage(getCurrentPage()-1);
                onPageChangedProperty().get().handle(event);
            }
        });

        rightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setCurrentPage(getCurrentPage()+1);
                onPageChangedProperty().get().handle(event);
            }
        });

    }

    /* count */

    public IntegerProperty countProperty() {
        return count;
    }

    public Integer getCount() {
        return count.getValue();
    }

    public void setCount(Integer integer) {
        count.setValue(integer);
        setPageCount();
    }

    /* currentPage */

    public IntegerProperty currentPageProperty() {
        return currentPage;
    }

    public Integer getCurrentPage() {
        return currentPage.getValue();
    }

    public void setCurrentPage(Integer page) {
        if (page == 0) {
            leftButton.setDisable(true);
            rightButton.setDisable(true);
            return;
        }
        if (page <= getCount()) {
            Button button = (Button)pageHBox.getChildren().get(page-1);
            setCurrentButton(button);
        }

        leftButton.setDisable(page == 1);
        rightButton.setDisable(page == getCount());

        currentPage.setValue(page);
    }

    /* onPageChanged */

    public final ObjectProperty<EventHandler<Event>> onPageChangedProperty() {
        return onPageChanged;
    }

    public final void setOnPageChanged(EventHandler<Event> handler) {
        onPageChanged.set(handler);
    }

    public final EventHandler<Event> getOnPageChanged() {
        return onPageChanged.get();

    }

    /* private */

    private void setPageCount() {
        pageHBox.getChildren().clear();

        if (getCount() <= 0) {
            setCurrentPage(0);
        } else {
            for (int i = 1; i <= getCount(); i++) {
                Button button = new Button(i + "");
                button.getStyleClass().add("page-button");

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        setCurrentPage(pageHBox.getChildren().indexOf(button)+1);
                        onPageChangedProperty().get().handle(event);
                    }
                });
                pageHBox.getChildren().add(button);
            }
            setCurrentPage(1);
        }


    }

    private void setCurrentButton(Button button) {
        if (currentButton != null) {
            currentButton.getStyleClass().remove("selected");
        }

        currentButton = button;
        button.getStyleClass().add("selected");
    }

}
