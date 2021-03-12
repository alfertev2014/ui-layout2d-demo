package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasBounds;
import alfertev2014.layout2d.geom.HasSizeHint;
import alfertev2014.layout2d.geom.HasSizePolicy;
import alfertev2014.layout2d.geom.SizePolicy;
import alfertev2014.layout2d.scene.SceneNode;

import java.awt.*;
import java.util.stream.Stream;

public interface LayoutItem extends TreeNode, HasSizeHint, HasSizePolicy, HasLayout, HasBounds {

    @Override
    default void updateLayout(Rectangle bounds) {
        setBounds(bounds);
        handleBoundsChanged();
    }

    // protected
    void handleBoundsChanged();

    static LayoutItem of(Layout layout) {
        return of(layout, SizePolicy.preferred(), SizePolicy.preferred());
    }

    static LayoutItem of(Layout layout, SizePolicy verticalPolicy, SizePolicy horizontalPolicy) {
        return new LayoutItem() {
            Rectangle bounds = new Rectangle();

            @Override
            public Rectangle getBounds() {
                return bounds;
            }

            @Override
            public void setBounds(Rectangle value) {
                bounds = value;
            }

            @Override
            public void handleBoundsChanged() {
                layout.updateLayout(getBounds());
            }

            @Override
            public Stream<SceneNode> render() {
                return layout.render();
            }

            @Override
            public Dimension getSizeHint() {
                return layout.getSizeHint();
            }

            @Override
            public SizePolicy getVerticalPolicy() {
                return verticalPolicy;
            }

            @Override
            public SizePolicy getHorizontalPolicy() {
                return horizontalPolicy;
            }
        };
    }
}
