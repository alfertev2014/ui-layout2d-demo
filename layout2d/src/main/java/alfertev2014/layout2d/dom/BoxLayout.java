package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.Alignment;
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
        List<Rectangle> childrenSizes = calculateChildrenSizes(bounds);

        if (! childrenSizes.isEmpty()) {
            placeChildren(childrenSizes);
        }
    }

    private void placeChildren(List<Rectangle> boundsHints) {
        int i = 0;
        for (LayoutItem n : getItems()) {
            n.setBounds(boundsHints.get(i));
            ++i;
        }
    }

    private List<Rectangle> calculateChildrenSizes(Rectangle bounds) {
        List<Rectangle> childrenSizes = new ArrayList<>();

        List<? extends LayoutItem> content = getItems();
        if (content.isEmpty())
            return childrenSizes;

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

        int stretchCounter = 1;
        int length = 0;
        for (LayoutItem n : content) {
            Dimension hint = n.getSizeHint();
            Rectangle itemBounds = new Rectangle(hint);
            int possibleLength = isVertical() ? itemBounds.height : itemBounds.width;

            SizePolicy policy = isVertical() ? n.getVerticalPolicy() : n.getHorizontalPolicy();
            if (policy.isExpanding()) {
                int stretchLength = freeLength * stretchCounter / stretchedCount - freeLength * (stretchCounter - 1) / stretchedCount;
                ++stretchCounter;

                if (isVertical()) {
                    itemBounds.height += stretchLength;
                } else {
                    itemBounds.width += stretchLength;
                }

                if (policy.isGrowing()) {
                    if (isVertical()) {
                        if (possibleLength > hint.height) {
                            itemBounds.height = possibleLength;
                        }
                    } else {
                        if (possibleLength > hint.width) {
                            itemBounds.width = possibleLength;
                        }
                    }
                }
                if (policy.isShrinking()) {
                    if (isVertical()) {
                        if (possibleLength < hint.height) {
                            itemBounds.height = possibleLength;
                        }
                    } else {
                        if (possibleLength < hint.width) {
                            itemBounds.width = possibleLength;
                        }
                    }
                }
            }

            Alignment alignment = isVertical() ? n.getVerticalAlignment() : n.getHorizontalAlignment();

            if (isVertical()) {
                if (alignment.isLeading() && alignment.isTrailing()) {
                    itemBounds.width = bounds.width;
                    itemBounds.x = 0;
                } else if (alignment.isTrailing()) {
                    itemBounds.x = bounds.width - hint.width;
                } else if (! alignment.isLeading()) {
                    itemBounds.x = (bounds.width - hint.width) / 2;
                }

                itemBounds.y = length;
                length += itemBounds.height + spacing;
            } else {
                if (alignment.isLeading() && alignment.isTrailing()) {
                    itemBounds.height = bounds.height;
                    itemBounds.y = 0;
                } else if (alignment.isTrailing()) {
                    itemBounds.y = bounds.height - hint.height;
                } else if (! alignment.isLeading()) {
                    itemBounds.y = (bounds.height - hint.height) / 2;
                }

                itemBounds.x = length;
                length += itemBounds.width + spacing;
            }

            childrenSizes.add(itemBounds);
        }
        return childrenSizes;
    }

    @Override
    default Dimension getSizeHint() {
        List<? extends LayoutItem> content = getItems();

        if (content.isEmpty()) {
            return new Dimension(0, 0);
        }

        int spacingSum = (content.size() - 1) * getSpacing();

        Dimension res = isVertical() ?
                new Dimension(0, spacingSum) :
                new Dimension(spacingSum, 0);

        for (LayoutItem n : content) {
            Dimension hint = n.getSizeHint();
            if (isVertical()) {
                if (hint.width > res.width) {
                    res.width = hint.width;
                }

                res.height += hint.height;
            } else {
                if (hint.height > res.height) {
                    res.height = hint.height;
                }

                res.width += hint.width;
            }
        }

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
