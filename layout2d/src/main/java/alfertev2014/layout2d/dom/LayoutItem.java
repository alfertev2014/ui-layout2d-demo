package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.*;
import alfertev2014.layout2d.scene.SceneNode;

import java.awt.*;
import java.util.stream.Stream;

public interface LayoutItem extends TreeNode, HasLayout, HasBounds, HasSizeHint, HasSizePolicy, HasAlignment {

    @Override
    default void updateLayout(Rectangle bounds) {
        setBounds(bounds);
        handleBoundsChanged();
    }

    // protected
    void handleBoundsChanged();

    @Override
    default Alignment getVerticalAlignment() {
        return Alignment.justify();
    }

    @Override
    default Alignment getHorizontalAlignment() {
        return Alignment.justify();
    }

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
