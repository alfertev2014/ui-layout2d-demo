package alfertev2014.layout2d.scene;

import java.awt.*;
import java.util.List;

public interface SceneContainer extends SceneNode {

    Point getOffset();

    List<SceneNode> getChildren();

    @Override
    default void draw(Graphics g, Point offset) {
        Point parentOffset = getOffset();
        Point newOffset = new Point(parentOffset.x + offset.x, parentOffset.y + offset.y);
        for (var node : getChildren()) {
            node.draw(g, newOffset);
        }
    }
}
