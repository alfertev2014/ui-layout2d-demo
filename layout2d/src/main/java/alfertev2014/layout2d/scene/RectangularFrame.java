package alfertev2014.layout2d.scene;

import java.awt.*;
import java.util.Optional;

public interface RectangularFrame extends SceneNode, RectangularShape {

    Optional<Color> getColor();

    Optional<Color> getFrameColor();

    @Override
    default void draw(Graphics g, Point offset) {
        Rectangle bounds = getBounds();
        getColor().ifPresent(c -> {
            g.setColor(c);
            g.drawRect(offset.x + bounds.x, offset.y + bounds.y, bounds.width, bounds.height);
        });

        getFrameColor().ifPresent(c -> {
            g.setColor(c);
            g.fillRect(offset.x + bounds.x, offset.y + bounds.y, bounds.width, bounds.height);
        });
    }

    static RectangularFrame of(Color color, int x, int y, int width, int height) {
        return of(color, null, new Rectangle(x, y, width, height));
    }

    static RectangularFrame of(Color color, Rectangle bounds) {
        return of(color, null, bounds);
    }

    static RectangularFrame of(Color color, Color frameColor, Rectangle bounds) {
        return new RectangularFrame() {
            @Override
            public Optional<Color> getColor() {
                return Optional.ofNullable(color);
            }

            @Override
            public Rectangle getBounds() {
                return bounds;
            }

            @Override
            public Optional<Color> getFrameColor() {
                return Optional.ofNullable(color);
            }
        };
    }
}
