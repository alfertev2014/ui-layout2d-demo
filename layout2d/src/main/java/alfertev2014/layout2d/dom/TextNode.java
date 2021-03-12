package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.SizePolicy;
import alfertev2014.layout2d.scene.SceneNode;
import alfertev2014.layout2d.scene.TextCaption;

import java.awt.*;
import java.util.stream.Stream;

public interface TextNode extends LayoutItem {

    Color getTextColor();

    Font getFont();

    String getText();

    @Override
    default void handleBoundsChanged() {
        // do nothing
    }

    @Override
    default Stream<SceneNode> render() {
        return Stream.of(TextCaption.of(getBounds(), getTextColor(), getFont(), getText()));
    }

    @Override
    default SizePolicy getVerticalPolicy() {
        return SizePolicy.expanding();
    }

    @Override
    default SizePolicy getHorizontalPolicy() {
        return SizePolicy.expanding();
    }

    @Override
    default Dimension getSizeHint() {
        return new Dimension();
    }

    static TextNode of(Color color, Font font, String text) {
        return new TextNode() {
            private Rectangle bounds = new Rectangle();

            @Override
            public Color getTextColor() {
                return color;
            }

            @Override
            public Font getFont() {
                return font;
            }

            @Override
            public String getText() {
                return text;
            }

            @Override
            public Rectangle getBounds() {
                return bounds;
            }

            @Override
            public void setBounds(Rectangle value) {
                bounds = value;
            }
        };
    }
}
