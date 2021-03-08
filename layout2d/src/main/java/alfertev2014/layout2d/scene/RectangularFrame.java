package alfertev2014.layout2d.scene;

import java.awt.*;

public interface RectangularFrame extends SceneNode, RectangleShape {

    Color getColor();

    @Override
    default void draw(Graphics g, Point offset) {
        Rectangle bounds = getBounds();
        g.setColor(getColor());
        g.drawRect(offset.x + bounds.x, offset.y + bounds.y, bounds.width, bounds.height);
    }

    static RectangularFrame of(Color color, int x, int y, int width, int height) {
        return of(color, new Rectangle(x, y, width, height));
    }

    static RectangularFrame of(Color color, Rectangle bounds) {
        return new RectangularFrame() {
            @Override
            public Color getColor() {
                return color;
            }

            @Override
            public Rectangle getBounds() {
                return bounds;
            }
        };
    }
}
