package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasFixedSize;

import java.awt.*;
import java.util.List;

public interface FrameNode extends ContainerNode, HasFixedSize {

    Layout getLayout();

    @Override
    default List<? extends TreeNode> getContent() {
        return getLayout().getContent();
    }

    static FrameNode of(Dimension sz, Layout layout) {
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
                layout.updateLayout(bounds);
            }

            @Override
            public Insets getPaddingsValue() {
                return paddings;
            }

            @Override
            public void setPaddingsValue(Insets value) {
                paddings = value;
            }
        };
    }
}
