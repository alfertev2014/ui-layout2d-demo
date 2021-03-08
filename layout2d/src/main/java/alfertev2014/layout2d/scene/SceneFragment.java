package alfertev2014.layout2d.scene;

import java.awt.*;
import java.util.List;

public interface SceneFragment extends SceneNode {

    List<SceneNode> getContent();

    @Override
    default void draw(Graphics g, Point offset) {
        for (var node : getContent()) {
            node.draw(g, offset);
        }
    }
}
