package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class EnemyShot extends Sprite {

    public static final double CHANGE_DIR_TIME = 0.1;

    private static final double SHOT_VELOCITY = 5;
    private static final double ANGLE_SPEED = 5;

    private double speedx;

    private long nextChangeDirTime;

    private Polygon body;

    public EnemyShot() {
        body = new Polygon(-5, -2.5, 0, -7.5, 5, -2.5, 5, 2.5, 0, 7.5, -5, 2.5);
        body.setScaleX(2);
        body.setScaleY(2);
        body.setFill(Color.GREEN);
        getChildren().addAll(body);
        speedx = SHOT_VELOCITY / Math.sqrt(2);
        nextChangeDirTime = System.nanoTime() + nextChangeDirTime * 1000000000;
    }

    @Override
    public void update() {

        long now = System.nanoTime();
        if (now >= nextChangeDirTime) {
            speedx = -speedx;
            nextChangeDirTime = (long) (now + 2 * CHANGE_DIR_TIME * 1000000000);
        }

        setTranslateY(getTranslateY() + SHOT_VELOCITY);
        setTranslateX(getTranslateX() + speedx);
        body.setRotate(body.getRotate() + ANGLE_SPEED);
    }

}
