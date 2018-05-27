package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Shot extends Sprite {

    private static final double SHOT_VELOCITY = -5;
    private static final double ANGLE_SPEED = 5;
    private Polygon body;
    
    
    public Shot() {
        //body = new Circle(0, 0, 3);
        //body = new Polygon(0,0,0,15,15,0);
        body = new Polygon(-5,-2.5,0,-7.5,5,-2.5,5,2.5,0,7.5,-5,2.5);
        body.setFill(Color.RED);
        getChildren().addAll(body);
    }
    
    @Override
    public void update() {
        setTranslateY(getTranslateY() + SHOT_VELOCITY);
        body.setRotate(body.getRotate() + ANGLE_SPEED);
    }
    
}
