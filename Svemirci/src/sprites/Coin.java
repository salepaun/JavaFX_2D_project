/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import main.Main;

/**
 *
 * @author Sale
 */
public class Coin extends Sprite {

    public static final double GRAVITY = 5;
    public static final double MAX_ANGLE_OFFSET = 30;
    public static final double COIN_RADIUS = 20;

    private double speedX;
    private double speedY;
    private Circle body;

    public Coin() {

        body = new Circle(20, Color.YELLOW);
        body.setStroke(Color.BLACK);

        getChildren().add(body);

        Random random = new Random();
        double angle = random.nextDouble() * MAX_ANGLE_OFFSET;

        speedX = 1.0 * Math.sin(Math.toRadians(angle)) / Math.sin(Math.toRadians(90 - angle)) * 3;
        if (random.nextFloat() > 0.5) {
            speedX = -speedX;
        }
        speedY = 1;
    }

    @Override
    public void update() {
        speedY += GRAVITY / 60;

        if (getTranslateX() + speedX < -10) {
        } else if (getTranslateX() + speedX > Main.WINDOW_WIDTH + 10) {
        } else {
            setTranslateX(getTranslateX() + speedX);
        }

        if (getTranslateY() < -20) {

        } else if (getTranslateY() > Main.WINDOW_HEIGHT + 10) {

        } else {
            setTranslateY(getTranslateY() + speedY);
        }

    }

    public boolean IsVisible() {
        boolean ret = true;
        if (getTranslateX() < -10) {
            ret = false;
        } else if (getTranslateX() > Main.WINDOW_WIDTH + 10) {
            ret = false;
        }

        if (getTranslateY() < -10) {
            ret = false;
        } else if (getTranslateY() + speedY > Main.WINDOW_HEIGHT + 10) {
            ret = false;
        }
        return ret;
    }

}
