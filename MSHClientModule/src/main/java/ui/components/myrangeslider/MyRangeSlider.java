package ui.components.myrangeslider;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/28.
 */
public class MyRangeSlider extends Pane {

    @FXML
    private Label leftThumb;

    @FXML
    private Label rightThumb;

    @FXML
    private Label leftLabel;

    @FXML
    private Label rightLabel;

    @FXML
    private Rectangle activeRect;

    private StringProperty prefix = new SimpleStringProperty("");
    private IntegerProperty minRange = new SimpleIntegerProperty(0);
    private IntegerProperty maxRange = new SimpleIntegerProperty(1000);
    private IntegerProperty minValue = new SimpleIntegerProperty();
    private IntegerProperty maxValue = new SimpleIntegerProperty();

    private double orgSceneX;
    private double orgTranslateX;

    public MyRangeSlider() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyRangeSlider.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setLabel();
    }

    @FXML
    private void pressLeftThumb(MouseEvent event) {
        Label thumb = (Label) event.getSource();
        orgSceneX = event.getSceneX();
        orgTranslateX = thumb.getTranslateX();
    }

    @FXML
    private void dragLeftThumb(MouseEvent event) {
        Label thumb = (Label) event.getSource();

        double offsetX = event.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        double minX = -10 -thumb.getLayoutX();
        double maxX = rightThumb.getLayoutX() + rightThumb.getTranslateX()-thumb.getLayoutX();

        if (newTranslateX < minX) {
            newTranslateX = minX;
        } else if (newTranslateX > maxX){
            newTranslateX = maxX;
        }

        thumb.setTranslateX(newTranslateX);
        activeRect.setTranslateX(newTranslateX);
        activeRect.setWidth(250-newTranslateX+rightThumb.getTranslateX());
        setLabel();
    }

    @FXML
    private void pressRightThumb(MouseEvent event) {
        Label thumb = (Label) event.getSource();
        orgSceneX = event.getSceneX();
        orgTranslateX = thumb.getTranslateX();
    }

    @FXML
    private void dragRightThumb(MouseEvent event) {
        Label thumb = (Label) event.getSource();

        double offsetX = event.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        double minX = leftThumb.getLayoutX() + leftThumb.getTranslateX()-thumb.getLayoutX();
        double maxX = 590-thumb.getLayoutX();

        if (newTranslateX < minX) {
            newTranslateX = minX;
        } else if (newTranslateX > maxX){
            newTranslateX = maxX;
        }

        thumb.setTranslateX(newTranslateX);
        activeRect.setWidth(250-leftThumb.getTranslateX()+newTranslateX);
        setLabel();

    }

    private void setLabel() {
        int leftX = (int) (leftThumb.getLayoutX()+leftThumb.getTranslateX()+10)/6*10;
        minValue.setValue(leftX);
        leftLabel.setText(prefix.getValue() + " " + leftX);

        int rightX = (int) (rightThumb.getLayoutX()+rightThumb.getTranslateX()+10)/6*10;
        maxValue.setValue(rightX);
        rightLabel.setText(prefix.getValue() + " " + rightX);
    }

    public StringProperty prefixProperty() {
        return prefix;
    }

    public String getPrefix() {
        return prefix.get();
    }

    public void setPrefix(String value) {
        prefix.set(value);
        setLabel();
    }

    public IntegerProperty minRangeProperty() {
        return minRange;
    }

    public Integer getMinRange() {
        return minRange.getValue();
    }

    public void setMinRange(Integer value) {
        minRange.setValue(value);
        setLabel();
    }

    public IntegerProperty maxRangeProperty() {
        return maxRange;
    }

    public Integer getMaxRange() {
        return maxRange.getValue();
    }

    public void setMaxRange(Integer value) {
        maxRange.setValue(value);
        setLabel();
    }

    public Integer getMinValue() {
        return minValue.getValue();
    }

    public Integer getMaxValue() {
        return maxValue.getValue();
    }


}
