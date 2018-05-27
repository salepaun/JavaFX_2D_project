package cameras;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Translate;

public class Camera extends Group
{
  private boolean fixed;
  
  public Camera()
  {
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
    getTransforms().add(new Translate(600.0D - node.getTranslateX(), 0.0D));
  }
  
  public void update(Node node) {
    if (fixed) {
      setFixed(node);
    }
  }
}
