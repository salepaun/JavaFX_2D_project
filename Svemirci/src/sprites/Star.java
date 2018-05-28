package sprites;

import java.util.Random;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import main.Main;

public class Star extends Sprite {

    public static final int MAX_RADIUS = 10;

    private int x;
    private int y;
    private int z;
    private double radius;
    private int speed = 10;
    
    private Circle circle;

    private Random r = new Random();

    public Star() {

        x = r.nextInt(Main.WINDOW_WIDTH) - Main.WINDOW_WIDTH / 2;
        y = r.nextInt(Main.WINDOW_HEIGHT) - Main.WINDOW_HEIGHT / 2;
        z = r.nextInt(Main.WINDOW_WIDTH);

        setTranslateX(x);
        setTranslateY(y);
        
       /*  //creating path.
        Path path = new Path();

        //creating 1st line.
        MoveTo moveTo = new MoveTo(108, 71);

        //creating 1st line.
        LineTo line1 = new LineTo(321,161);

        //creating 2nd line.
        LineTo line2 = new LineTo(126, 232);

        //creating 3rd line.
        LineTo line3 = new LineTo(232, 52);

        //creating 4th line.
        LineTo line4 = new LineTo(269, 250);

        //creating 5th line.
        LineTo line5 = new LineTo(108, 71);

        //adding elements to the path.
        path.getElements().add(moveTo);
        path.getElements().addAll(line1,line2,line3,line4,line5);
        path.setFill(Color.WHITE);
        path.setStroke(Color.WHITE);
        */
        

        circle = new Circle(1, Color.WHITE);
        getChildren().addAll(circle);
    }

    @Override
    public void update() {
        z -= speed;
        
        if (z < 1) {
            z = Main.WINDOW_WIDTH;
            x = r.nextInt(Main.WINDOW_WIDTH) - Main.WINDOW_WIDTH / 2;
            y = r.nextInt(Main.WINDOW_HEIGHT) - Main.WINDOW_HEIGHT / 2;
        }

        int sx = (int) ((float) (1.0 * x) / z * Main.WINDOW_WIDTH);
        int sy = (int) ((float) (1.0 * y) / z * Main.WINDOW_HEIGHT);

        radius = 1.0 * z / Main.WINDOW_WIDTH * (-MAX_RADIUS) + MAX_RADIUS;
        
        circle.setRadius(radius);
        setTranslateX(sx);
        setTranslateY(sy);
    }

}
