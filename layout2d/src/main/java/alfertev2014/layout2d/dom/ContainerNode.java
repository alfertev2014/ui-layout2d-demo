package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasPaddings;
import alfertev2014.layout2d.scene.SceneContainer;
import alfertev2014.layout2d.scene.SceneNode;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ContainerNode extends TreeFragment, LayoutItem, HasPaddings {

    default Rectangle getInnerBounds() {
        Rectangle bounds = getBounds();
        Point location = bounds.getLocation();
        Insets paddings = getPaddingsValue();

        return new Rectangle(location.x + paddings.left, location.y + paddings.top,
                bounds.width - paddings.left - paddings.right,
                bounds.height - paddings.top - paddings.bottom);
    }

    @Override
    default Stream<SceneNode> render() {
        Point offset = getInnerBounds().getLocation();
        List<SceneNode> children = TreeFragment.super.render().collect(Collectors.toList());

        return Stream.of(new SceneContainer() {
            @Override
            public List<SceneNode> getContent() {
                return children;
            }

            @Override
            public Point getOffset() {
                return offset;
            }
        });
    }
}
