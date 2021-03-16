package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.dom.layout.LayoutItem;
import alfertev2014.layout2d.geom.HasPaddings;
import alfertev2014.layout2d.scene.SceneContainer;
import alfertev2014.layout2d.scene.SceneNode;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ContainerNode extends TreeFragment, LayoutItem, HasPaddings {

    @Override
    default Stream<SceneNode> render() {
        Point offset = getBounds().getLocation();
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

    default Rectangle getInnerBounds() {
        Rectangle bounds = getBounds();
        Insets paddings = getPaddings();

        return new Rectangle(paddings.left, paddings.top,
                bounds.width - paddings.left - paddings.right,
                bounds.height - paddings.top - paddings.bottom);
    }
}
