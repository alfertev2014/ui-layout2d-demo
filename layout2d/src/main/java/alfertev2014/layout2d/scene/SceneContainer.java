package alfertev2014.layout2d.scene;

import java.awt.*;
import java.util.List;

public interface SceneContainer extends SceneFragment {

    Point getOffset();

    @Override
    default void draw(Graphics g, Point offset) {
        Point parentOffset = getOffset();
        SceneFragment.super.draw(g, new Point(offset.x + parentOffset.x, offset.y + parentOffset.y));
    }
}
