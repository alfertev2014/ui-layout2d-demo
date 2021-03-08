package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasPaddings;
import alfertev2014.layout2d.scene.SceneContainer;
import alfertev2014.layout2d.scene.SceneFragment;
import alfertev2014.layout2d.scene.SceneNode;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public interface ContainerNode extends TreeFragment, LayoutItem, HasPaddings {

    @Override
    default SceneNode render() {
        return new SceneContainer() {
            private final Point offset = getBounds().getLocation();
            private final List<SceneNode> children = renderContent();

            @Override
            public List<SceneNode> getChildren() {
                return children;
            }

            @Override
            public Point getOffset() {
                return offset;
            }
        };
    }

    private List<SceneNode> renderContent() {
        return ContainerNode.this.getContent().stream()
                .map(Renderable::render)
                .collect(Collectors.toList());
    }
}
