package sprites;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import main.Main;

public class Enemy extends Sprite {

    public static boolean changeDir = false;

    public static int speed = 2;

    Rectangle body;
    Polygon ear1;
    Polygon ear2;
    Arc mouth;
    Group eyes[];
    int index;

    private boolean isDead = false;
    private List<Coin> coins;
    private static List<EnemyShot> shots = new ArrayList<>();

    public Enemy(int num, List<Coin> coins) {
        Group group = new Group();
        this.coins = coins;
        body = new Rectangle(0, 0, 50, 40);
        index = num & 1;
        // Usi
        ear1 = new Polygon(0, 0, -30, -15, -30, 15);
        ear1.setTranslateY(20);
        ear2 = new Polygon(50, 0, 80, -15, 80, 15);
        ear2.setTranslateY(20);
        ear1.setFill(Color.ANTIQUEWHITE);
        ear2.setFill(Color.ANTIQUEWHITE);

        body.setArcHeight(30);
        body.setArcWidth(30);

        mouth = new Arc(0, 10, 12, 7, 0, -180);
        mouth.setTranslateX(25);
        mouth.setTranslateY(20);

        Group eye = new Group();
        Circle c1 = new Circle(5);
        c1.setFill(Color.WHITE);
        Circle c2 = new Circle(2);
        c2.setFill(Color.BLACK);
        eye.getChildren().addAll(c1, c2);

        eye.setTranslateX(15);
        eye.setTranslateY(17);

        Group eye2 = new Group();
        Circle c12 = new Circle(5);
        c12.setFill(Color.WHITE);
        Circle c22 = new Circle(2);
        c22.setFill(Color.BLACK);
        eye2.getChildren().addAll(c12, c22);

        eye2.setTranslateX(35);
        eye2.setTranslateY(17);

        eyes = new Group[]{eye, eye2};

        body.setFill(Color.YELLOW);
        getChildren().addAll(body, ear1, ear2, mouth, eyes[0], eyes[1]);
        napraviAnimaciju(eyes[index]);
    }

    public void napraviAnimaciju(Group eye) {
        ScaleTransition skaliranje = new ScaleTransition(Duration.millis(1000), eye);
        skaliranje.setFromY(1);
        skaliranje.setToY(0.1);
        skaliranje.setCycleCount(Timeline.INDEFINITE);
        skaliranje.setAutoReverse(true);
        skaliranje.play();

        RotateTransition rtL = new RotateTransition(Duration.seconds(1), ear1);
        rtL.setFromAngle(15);
        rtL.setToAngle(-15);
        rtL.setAutoReverse(true);
        rtL.setCycleCount(-1);
        rtL.play();

        RotateTransition rtR = new RotateTransition(Duration.seconds(1), ear2);
        rtR.setFromAngle(-15);
        rtR.setToAngle(15);
        rtR.setAutoReverse(true);
        rtR.setCycleCount(-1);
        rtR.play();
    }

    public void DeathAnimation(List list) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(.75), this);

        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);

        isDead = true;
        rotateTransition.setOnFinished((event) -> {
            list.remove(this);

            Coin c = new Coin();
            c.setTranslateX(getTranslateX());
            c.setTranslateY(getTranslateY());
            coins.add(c);

        });
        rotateTransition.play();
    }

    @Override
    public void update() {
        double x = getTranslateX();
        setTranslateX(x + speed);
    }

    public void updateDir() {
        double x = getTranslateX();

        if (getTranslateX() < 50) {
            changeDir = true;
        }

        if (getTranslateX() + getBoundsInParent().getWidth() >= Main.WINDOW_WIDTH) {
            changeDir = true;
        }
    }

    public boolean IsDead() {
        return isDead;
    }

    public static List<EnemyShot> getShots() {
        return shots;
    }

}
