package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasFixedSize;

import java.awt.*;
import java.util.List;

public interface FrameNode extends ContainerNode, HasFixedSize {

    static FrameNode of(Dimension sz, TreeNode ...content) {
        return of(sz, List.of(content));
    }

    static FrameNode of(Dimension sz, List<? extends TreeNode> content) {
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
            public List<? extends TreeNode> getContent() {
                return content;
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
