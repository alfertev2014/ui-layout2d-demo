package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasSizeHint;

import java.awt.*;

public interface LayoutItem extends TreeNode, HasSizeHint {

    Rectangle getBounds();
    void setBounds(Rectangle value);
}
