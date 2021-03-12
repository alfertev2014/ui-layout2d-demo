package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasBackground;
import alfertev2014.layout2d.geom.HasBorder;
import alfertev2014.layout2d.geom.HasFixedSize;
import alfertev2014.layout2d.scene.RectangularFrame;
import alfertev2014.layout2d.scene.SceneNode;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

public interface FrameNode extends ContainerNode, HasFixedSize, HasBackground, HasBorder {

    Layout getLayout();

    @Override
    default List<? extends TreeNode> getContent() {
        return getLayout().getContent();
    }

    // protected
    @Override
    default void handleBoundsChanged() {
        getLayout().updateLayout(getInnerBounds());
    }

    static FrameNode of(Dimension sz, Color background, Color border) {
        return of(sz, background, border, Layout.empty());
    }

    static FrameNode of(Dimension sz, Layout layout) {
        return of(sz, null, null, layout);
    }

    static FrameNode of(Dimension sz, Color background, Color border, Layout layout) {
        return new FrameNode() {
            private Dimension size = sz;
            private Insets paddings = new Insets(0, 0, 0, 0);
            private Rectangle bounds = new Rectangle(0, 0, sz.width, sz.height);

            @Override
            public Dimension getSize() {
                return size;
            }

            @Override
            public void setSize(Dimension value) {
                size = value;
            }

            @Override
            public Layout getLayout() {
                return layout;
            }

            @Override
            public Rectangle getBounds() {
                return bounds;
            }

            @Override
            public void setBounds(Rectangle value) {
                bounds = value;
            }

            @Override
            public Insets getPaddings() {
                return paddings;
            }

            @Override
            public void setPaddings(Insets value) {
                paddings = value;
            }

            @Override
            public int getBorder() {
                return 1;
            }

            @Override
            public Color getBorderColor() {
                return border;
            }

            @Override
            public Color getBackgroundColor() {
                return background;
            }
        };
    }

    @Override
    default Stream<SceneNode> render() {
        return Stream.concat(
                Stream.<SceneNode>of(RectangularFrame.of(getBackgroundColor(), getBorderColor(), getBounds())),
                ContainerNode.super.render()
        );
    }
}
