package cameras;

import javafx.scene.*;
import javafx.scene.transform.*;

public class Camera extends Group {

    private boolean fixed;

    public Camera() {
        getTransforms().clear();
        fixed = false;
    }

    public void setDefault() {
        getTransforms().clear();
        fixed = false;
    }

    public void setFixed(Node node) {
        fixed = true;
        getTransforms().clear();
        getTransforms().add(new Translate(main.Main.WINDOW_WIDTH / 2 - node.getTranslateX(), 0));
    }

    public void update(Node node) {
        if (fixed) {
            setFixed(node);
        }
    }
}
