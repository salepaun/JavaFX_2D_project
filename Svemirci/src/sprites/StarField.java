package sprites;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class StarField extends Sprite {

    public static int MAX_STAR_NUM = 40;

    private List<Star> stars;

    public StarField() {
        stars = new ArrayList<>();
        for (int i = 0; i < MAX_STAR_NUM; i++) {
            Star s = new Star();
            stars.add(s);
            getChildren().add(s);
        }
    }

    @Override
    public void update() {
        for (Star s : stars) {
            s.update();
        }

    }

}
