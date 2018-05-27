package sprites;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Background extends Sprite {
    
    public Background(int width, int height) {
        Rectangle background = new Rectangle(0, 0, width + 10, height + 10);
        Stop[] stops = new Stop[]{new Stop(0, Color.BLACK),new Stop(1, Color.DARKBLUE) };
        LinearGradient color = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        background.setFill(color);
        
        getChildren().addAll(background);
    }

    @Override
    public void update() {
        
    }
}
