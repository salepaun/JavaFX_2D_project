/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author Sale
 */
public class Particle extends Sprite {

    public static final int radius = 4;
    
    Circle particle;

    public Particle(Duration d) {
        particle = new Circle(radius);
        getChildren().add(particle);
        napraviAnimaciju(d);
    }

    void napraviAnimaciju(Duration duration) {

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(particle.fillProperty(), Color.ORANGE),
                        new KeyValue(particle.translateYProperty(), Thruster.height-3),
                        new KeyValue(particle.scaleXProperty(), 1),
                        new KeyValue(particle.scaleYProperty(), 1),
                        new KeyValue(particle.opacityProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(particle.fillProperty(), Color.RED),
                        new KeyValue(particle.translateYProperty(), Thruster.height * 2 ),
                        new KeyValue(particle.scaleXProperty(), 1.5),
                        new KeyValue(particle.scaleYProperty(), 1.5),
                        new KeyValue(particle.opacityProperty(), 0.9)
                ),
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(particle.fillProperty(), Color.BLUEVIOLET),
                        new KeyValue(particle.translateYProperty(), Thruster.height * 2.5 ),
                        new KeyValue(particle.scaleXProperty(), 1.5),
                        new KeyValue(particle.scaleYProperty(), 1.5),
                        new KeyValue(particle.opacityProperty(), .2)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setDelay(duration);
        timeline.play();

    }

    @Override
    public void update() {
    }

}
