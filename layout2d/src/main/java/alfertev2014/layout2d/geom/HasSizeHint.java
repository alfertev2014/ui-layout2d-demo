package alfertev2014.layout2d.geom;

import java.awt.*;

public interface HasSizeHint {

    Dimension getSizeHint();

    SizePolicy getVerticalPolicy();
    SizePolicy getHorizontalPolicy();
}
