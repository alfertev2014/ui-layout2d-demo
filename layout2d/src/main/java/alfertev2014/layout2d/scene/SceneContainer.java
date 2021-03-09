package alfertev2014.layout2d.scene;

import java.awt.*;
import java.util.List;

public interface SceneContainer extends SceneNode {

    Point getOffset();

    List<? extends SceneNode> getContent();

    @Override
    default void draw(Graphics g, Point offset) {
        Point parentOffset = getOffset();
        Point newOffset = new Point(offset.x + parentOffset.x, offset.y + parentOffset.y);
        for (var node : getContent()) {
            node.draw(g, newOffset);
        }
    }
}
