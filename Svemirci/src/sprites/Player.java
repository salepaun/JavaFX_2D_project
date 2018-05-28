package sprites;

import cameras.Camera;
import com.sun.prism.ps.Shader;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import main.Main;

public class Player extends Sprite implements EventHandler<KeyEvent> {

    private static final double PLAYER_VELOCITY = 10;

    private List<Shot> shots = new LinkedList<>();

    private double velocityHorizontal = 0;
    private double velocityVertical = 0;

    private States state = States.STALL;

    private Rectangle body;
    private Rectangle gun;
    private Thruster left;
    private Thruster right;

    private Camera camera;

    boolean upPressed = false;
    boolean downPressed = false;
    boolean rightPressed = false;
    boolean leftPressed = false;

    boolean shotPressed = false;

    private boolean isDead = false;

    public Player(Camera cam) {
        camera = cam;

        body = new Rectangle(0, 0, 50, 20);
        body.setTranslateX(-25);
        body.setFill(Color.SKYBLUE);
        gun = new Rectangle(0, 0, 6, 10);
        gun.setTranslateX(-3);
        gun.setTranslateY(-10);
        gun.setFill(Color.SKYBLUE);

        Shape e1 = new Ellipse(20, 10);
        e1.setFill(Color.SKYBLUE);
        Shape e2 = new Ellipse(15, 7.5);
        e2.setTranslateY(5);
        e2.setFill(Color.RED);
        e1 = Shape.subtract(e1, e2);
        e1.setFill(Color.SKYBLUE);

        Arc a1 = new Arc(0, 0, 10, 30, 0, 180);
        a1.setType(ArcType.CHORD);
        a1.setTranslateY(-3);
        a1.setFill(Color.SKYBLUE);

        Arc a2 = new Arc(0, 0, 5, 15, 0, 180);
        a2.setType(ArcType.CHORD);
        a2.setTranslateY(-10);
        a2.setFill(Color.BLACK);

        left = new Thruster();
        right = new Thruster();
        left.setTranslateX(-11 - Thruster.width);
        left.setTranslateY(-3);

        right.setTranslateX(11);
        right.setTranslateY(-3);

        getChildren().addAll(left, right, e1, a1, a2);
    }

    private void setVelocity() {
        velocityHorizontal = 0;
        velocityVertical = 0;

        if (upPressed) {
            velocityVertical -= PLAYER_VELOCITY;
        }
        if (downPressed) {
            velocityVertical += PLAYER_VELOCITY;
        }
        if (leftPressed) {
            velocityHorizontal -= PLAYER_VELOCITY;
        }
        if (rightPressed) {
            velocityHorizontal += PLAYER_VELOCITY;
        }

    }

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> s) {
        shots = s;
    }

    private void makeShot() {
        if (!shotPressed) {
            Shot shot = new Shot();
            shot.setTranslateX(getTranslateX());
            shot.setTranslateY(getTranslateY() - 10);
            shots.add(shot);
            shotPressed = true;
        }
    }

    @Override
    public void update() {
        if (getTranslateX() + velocityHorizontal < body.getWidth() / 2 + 5) {
            setTranslateX(body.getWidth() / 2 + 5);
        } else if (getTranslateX() + velocityHorizontal > Main.WINDOW_WIDTH - body.getWidth() / 2 - 5) {
            setTranslateX(Main.WINDOW_WIDTH - body.getWidth() / 2 - 5);
        } else {
            setTranslateX(getTranslateX() + velocityHorizontal);
        }

        if (getTranslateY() + velocityVertical < body.getHeight() / 2 + 5) {
            setTranslateY(body.getHeight() / 2 + 5);
        } else if (getTranslateY() + velocityVertical > Main.WINDOW_HEIGHT - body.getHeight() / 2 - 5) {
            setTranslateY(Main.WINDOW_HEIGHT - body.getHeight() / 2 - 5);
        } else {
            setTranslateY(getTranslateY() + velocityVertical);
        }

    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            rightPressed = true;
            setVelocity();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            leftPressed = true;
            setVelocity();
        } else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            rightPressed = false;
            setVelocity();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            leftPressed = false;
            setVelocity();
        } else if (event.getCode() == KeyCode.UP && event.getEventType() == KeyEvent.KEY_PRESSED) {
            upPressed = true;
            setVelocity();
        } else if (event.getCode() == KeyCode.DOWN && event.getEventType() == KeyEvent.KEY_PRESSED) {
            downPressed = true;
            setVelocity();
        } else if (event.getCode() == KeyCode.UP && event.getEventType() == KeyEvent.KEY_RELEASED) {
            upPressed = false;
            setVelocity();
        } else if (event.getCode() == KeyCode.DOWN && event.getEventType() == KeyEvent.KEY_RELEASED) {
            downPressed = false;
            setVelocity();
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED) {
            makeShot();
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_RELEASED) {
            shotPressed = false;
        } else if ((event.getCode()
                == KeyCode.DIGIT1) && (event.getEventType() == KeyEvent.KEY_PRESSED)) {
            camera.setDefault();
        } else if ((event.getCode()
                == KeyCode.DIGIT2) && (event.getEventType() == KeyEvent.KEY_PRESSED)) {
            camera.setFixed(this);
        }
    }

    public void DeathAnimation() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), this);

        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);

        rotateTransition.setOnFinished((event) -> {
            isDead = true;
        });
        rotateTransition.play();
    }

    public boolean IsDead() {
        return isDead;
    }

}
