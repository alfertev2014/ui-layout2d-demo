package alfertev2014.layout2d.scene;

import java.awt.*;

public interface ContainerShape extends SceneContainer {

    SceneNode getShapeNode();

    @Override
    default void draw(Graphics g, Point offset) {
        getShapeNode().draw(g, offset);
        SceneContainer.super.draw(g, offset);
    }
}
