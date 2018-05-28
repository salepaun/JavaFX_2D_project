/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Sale
 */
public class Thruster extends Sprite {

    public static final int width = 10;
    public static final int height = 20;
    public static final int NUM_PARTICLES = 10;

    Particle[] particles;

    public Thruster() {
        Rectangle rect = new Rectangle(width, height, Color.SKYBLUE);
        rect.setArcHeight(5);
        rect.setArcWidth(5);

        particles = new Particle[NUM_PARTICLES];

        for (int i = 0; i < NUM_PARTICLES; i++) {
            particles[i] = new Particle(Duration.seconds(i * 2.0 / NUM_PARTICLES));
            particles[i].setTranslateX(5);
        }
        getChildren().addAll(particles);
        getChildren().addAll(rect);

    }

    @Override
    public void update() {

    }

}
