package alfertev2014.layout2d.scene;

import java.awt.*;

public interface TextCaption extends SceneNode, RectangleShape {

    Color getColor();

    Font getFont();

    String getText();

    @Override
    default void draw(Graphics g, Point offset) {
        g.setColor(getColor());
        Font font = getFont();
        g.setFont(font);
        Rectangle bounds = getBounds();
        g.drawString(getText(), bounds.x + offset.x, bounds.y + offset.y + font.getSize());
    }

    static TextCaption of(Rectangle bounds, Color color, Font font, String text) {
        return new TextCaption() {
            @Override
            public Rectangle getBounds() {
                return bounds;
            }

            @Override
            public Color getColor() {
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
        };
    }
}
