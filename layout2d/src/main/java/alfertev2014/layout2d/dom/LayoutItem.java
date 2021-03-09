package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasSizeHint;
import alfertev2014.layout2d.geom.HasSizePolicy;
import alfertev2014.layout2d.geom.SizePolicy;
import alfertev2014.layout2d.scene.SceneNode;

import java.awt.*;

public interface LayoutItem extends TreeNode, HasSizeHint, HasSizePolicy {

    Rectangle getBounds();
    void setBounds(Rectangle value);

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
                layout.updateLayout(bounds);
            }

            @Override
            public SceneNode render() {
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