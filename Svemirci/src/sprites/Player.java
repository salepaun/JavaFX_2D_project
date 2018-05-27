package sprites;

import cameras.Camera;
import com.sun.prism.ps.Shader;
import java.util.LinkedList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import main.Main;

public class Player extends Sprite implements EventHandler<KeyEvent> {

    private static enum States {
        LEFT, RIGHT, STALL, UP, DOWN, HORIZONTAL_RELEASE, VERTICAL_RELEASE
    };
    private static final double PLAYER_VELOCITY = 10;

    private List<Shot> shots = new LinkedList<>();

    private double velocityHorizontal = 0;
    private double velocityVertical = 0;
    
    private States state = States.STALL;

    private Rectangle body;
    private Rectangle gun;

    private Camera camera;
    
    boolean upPressed = false;
    boolean downPressed = false;
    boolean rightPressed = false;
    boolean leftPressed = false;

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

        getChildren().addAll(e1, a1, a2);
    }

    private void setVelocity() {
        /*switch (state) {
            case STALL:
                velocityHorizontal = 0;
                velocityVertical = 0;
                break;
            case HORIZONTAL_RELEASE:
                velocityHorizontal = 0;
                break;
            case VERTICAL_RELEASE:
                velocityVertical = 0;
                break;
            case RIGHT:
                velocityHorizontal = PLAYER_VELOCITY;
                break;
            case LEFT:
                velocityHorizontal = -PLAYER_VELOCITY;
                break;
            case UP:
                velocityVertical = -PLAYER_VELOCITY;
                break;
            case DOWN:
                velocityVertical = +PLAYER_VELOCITY;
                break;
            default:
                break;
        }*/
        
        velocityHorizontal = 0;
        velocityVertical = 0;
        
        if(upPressed) velocityVertical-=PLAYER_VELOCITY;
        if(downPressed) velocityVertical+=PLAYER_VELOCITY;
        if(leftPressed) velocityHorizontal-=PLAYER_VELOCITY;
        if(rightPressed) velocityHorizontal+=PLAYER_VELOCITY;
        
    }

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> s) {
        shots = s;
    }

    private void makeShot() {
        Shot shot = new Shot();
        shot.setTranslateX(getTranslateX());
        shot.setTranslateY(getTranslateY() - 10);
        shots.add(shot);
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
        
        if (getTranslateY() + velocityVertical < body.getHeight()/ 2 + 5) {
            setTranslateY(body.getHeight()/ 2 + 5);
        } else if (getTranslateY() + velocityVertical > Main.WINDOW_HEIGHT - body.getHeight()/ 2 - 5) {
            setTranslateY(Main.WINDOW_HEIGHT - body.getHeight()/ 2 - 5);
        } else {
            setTranslateY(getTranslateY() + velocityVertical);
        }
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            rightPressed = true;
            //state = States.RIGHT;
            setVelocity();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
            //state = States.LEFT;
            leftPressed = true;
            setVelocity();
        } else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            //state = States.HORIZONTAL_RELEASE;
            rightPressed = false;
            setVelocity();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
            //state = States.HORIZONTAL_RELEASE;
            leftPressed = false;
            setVelocity();
        } else if (event.getCode() == KeyCode.UP && event.getEventType() == KeyEvent.KEY_PRESSED) {
            //state = States.UP;
            upPressed = true;
            setVelocity();
        } else if (event.getCode() == KeyCode.DOWN && event.getEventType() == KeyEvent.KEY_PRESSED) {
            //state = States.DOWN;
            downPressed = true;
            setVelocity();
        } else if (event.getCode() == KeyCode.UP && event.getEventType() == KeyEvent.KEY_RELEASED) {
            //state = States.VERTICAL_RELEASE;
            upPressed = false;
            setVelocity();
        } else if (event.getCode() == KeyCode.DOWN && event.getEventType() == KeyEvent.KEY_RELEASED) {
            //state = States.VERTICAL_RELEASE;
            downPressed = false;
            setVelocity();
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED) {
            makeShot();
        } else if ((event.getCode() == KeyCode.DIGIT1) && (event.getEventType() == KeyEvent.KEY_PRESSED)) {
            camera.setDefault();
        } else if ((event.getCode() == KeyCode.DIGIT2) && (event.getEventType() == KeyEvent.KEY_PRESSED)) {
            camera.setFixed(this);
        }
    }

}
