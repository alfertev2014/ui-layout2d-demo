package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.SizePolicy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface BoxLayout extends Layout {

    int getSpacing();

    enum Direction {
        Vertical,
        Horizontal
    }

    Direction getDirection();

    default boolean isVertical() {
        return getDirection().equals(Direction.Vertical);
    }

    default boolean isHorizontal() {
        return getDirection().equals(Direction.Horizontal);
    }

    @Override
    default void updateLayout(Rectangle bounds) {
        List<Dimension> boundsHints = getBoundsHints(bounds);

        if (! boundsHints.isEmpty()) {
            placeChildren(boundsHints);
        }
    }

    private void placeChildren(List<Dimension> boundsHints) {
        int i = 0;
        int length = 0;
        for (LayoutItem n : getItems()) {
            Dimension size = boundsHints.get(i);
            if (isVertical()) {
                n.setBounds(new Rectangle(0, length, size.width, size.height));
                length += size.height + getSpacing();
            } else {
                n.setBounds(new Rectangle(length, 0, size.width, size.height));
                length += size.width + getSpacing();
            }
            ++i;
        }
    }

    private List<Dimension> getBoundsHints(Rectangle bounds) {
        List<Dimension> boundsHints = new ArrayList<>();

        List<? extends LayoutItem> content = getItems();
        if (content.isEmpty())
            return boundsHints;

        int spacing = getSpacing();

        int preferredLength = spacing * (content.size() - 1);

        int growCount = 0;
        int shrinkCount = 0;
        for (LayoutItem n : content) {
            SizePolicy policy = isVertical()
                    ? n.getVerticalPolicy()
                    : n.getHorizontalPolicy();
            if (policy.isExpanding() && policy.isGrowing()) {
                ++growCount;
            }
            if (policy.isShrinking()) {
                ++shrinkCount;
            }
            Dimension sizeHint = n.getSizeHint();
            preferredLength += isVertical() ? sizeHint.height : sizeHint.width;
        }


        int freeLength = (isVertical() ? bounds.height : bounds.width) - preferredLength;

        int stretchedCount =
                freeLength >= 0
                        ? growCount
                        : shrinkCount;

        int i = 1;
        for (LayoutItem n : content) {
            Dimension size = n.getSizeHint();
            Dimension hint = (Dimension) size.clone();
            int possibleLength = isVertical() ? hint.height : hint.width;

            SizePolicy policy = isVertical() ? n.getVerticalPolicy() : n.getHorizontalPolicy();
            if (policy.isExpanding()) {
                int stretchLength = freeLength * i / stretchedCount - freeLength * (i - 1) / stretchedCount;
                if (isVertical()) {
                    hint.height += stretchLength;
                } else {
                    hint.width += stretchLength;
                }

                if (policy.isGrowing()) {
                    if (isVertical()) {
                        if (possibleLength > size.height) {
                            hint.height = possibleLength;
                        }
                    } else {
                        if (possibleLength > size.width) {
                            hint.width = possibleLength;
                        }
                    }
                }
                if (policy.isShrinking()) {
                    if (isVertical()) {
                        if (possibleLength < size.height) {
                            hint.height = possibleLength;
                        }
                    } else {
                        if (possibleLength < size.width) {
                            hint.width = possibleLength;
                        }
                    }
                }
            }

            SizePolicy crossPolicy = n.getHorizontalPolicy();

            if (isVertical()) {
                if (crossPolicy.isExpanding()) {
                    hint.width = bounds.width;
                } else if (size.width < bounds.width) {
                    hint.width = size.width;
                }
            } else {
                if (crossPolicy.isExpanding()) {
                    hint.height = bounds.height;
                } else if (size.height < bounds.height) {
                    hint.height = size.height;
                }
            }

            if (! crossPolicy.isGrowing()) {
                if (isVertical()) {
                    if (hint.width < size.width) {
                        hint.width = size.width;
                    }
                } else {
                    if (hint.height < size.height) {
                        hint.height = size.height;
                    }
                }
            }
            if (! crossPolicy.isShrinking()) {
                if (hint.width > size.width) {
                    hint.width = size.width;
                }
            }

            boundsHints.add(hint);
            ++i;
        }
        return boundsHints;
    }

    @Override
    default Dimension getSizeHint() {
        List<? extends LayoutItem> content = getItems();

        Dimension res = new Dimension(0, 0);

        if (content.isEmpty())
            return res;

        for (LayoutItem n : content) {
            if (n.getSizeHint().width > res.width) {
                res.width = n.getSizeHint().width;
            }

            res.height += n.getSizeHint().height;
        }

        res.height += (content.size() - 1) * getSpacing();

        return res;
    }

    static BoxLayout vertical(int spacing, LayoutItem ...nodes) {
        return of(Direction.Vertical, spacing, List.of(nodes));
    }

    static BoxLayout horizontal(int spacing, LayoutItem ...nodes) {
        return of(Direction.Horizontal, spacing, List.of(nodes));
    }

    static BoxLayout of(Direction direction, int spacing, LayoutItem ...nodes) {
        return of(direction, spacing, List.of(nodes));
    }

    static BoxLayout of(Direction direction, int spacing, List<? extends LayoutItem> content) {
        return new BoxLayout() {
            @Override
            public int getSpacing() {
                return spacing;
            }

            @Override
            public Direction getDirection() {
                return direction;
            }

            @Override
            public List<? extends LayoutItem> getItems() {
                return content;
            }
        };
    }
}
