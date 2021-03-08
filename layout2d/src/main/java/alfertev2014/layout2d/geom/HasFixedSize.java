package alfertev2014.layout2d.geom;

import java.awt.*;

public interface HasFixedSize extends HasSizeHint {

    default int getWidth() {
        return getSize().width;
    }

    default void setWidth(int value) {
        setSize(new Dimension(value, getHeight()));
    }

    default int getHeight() {
        return getSize().height;
    }

    default void setHeight(int value) {
        setSize(new Dimension(getWidth(), value));
    }

    Dimension getSize();
    void setSize(Dimension value);

    @Override
    default Dimension getSizeHint() {
        return getSize();
    }

    @Override
    default SizePolicy getVerticalPolicy() {
        return SizePolicy.fixed();
    }

    @Override
    default SizePolicy getHorizontalPolicy() {
        return SizePolicy.fixed();
    }
}
