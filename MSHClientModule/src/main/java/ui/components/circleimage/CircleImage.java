package ui.components.circleimage;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/28.
 */
public class CircleImage extends ImageView {

    @FXML
    ImageView imageView;

    public CircleImage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CircleImage.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setRadius(50);
    }

    public DoubleProperty radiusProperty() {
        return this.fitWidthProperty();
    }

    public double getRadius() {
        return this.getFitWidth();
    }

    public void setRadius(double radius) {
        this.setFitWidth(radius*2);
        this.setFitHeight(radius*2);
        setClip();

        if (this.getImage() != null) {
            Image image = this.getImage();
            double width = image.getWidth();
            double height = image.getHeight();
            double min = width < height ? width : height;

            Rectangle2D viewportRect = new Rectangle2D((width-min)/2, (height-min)/2, min, min);
            this.setViewport(viewportRect);

        } else {
            Rectangle2D viewportRect = new Rectangle2D(0, 0, radius*2, radius*2);
            this.setViewport(viewportRect);
        }
    }

    private void setClip() {
        Rectangle clip = new Rectangle(
                this.getFitWidth(), this.getFitHeight()
        );
        clip.setArcWidth(getRadius());
        clip.setArcHeight(getRadius());
        this.setClip(clip);
    }
}
