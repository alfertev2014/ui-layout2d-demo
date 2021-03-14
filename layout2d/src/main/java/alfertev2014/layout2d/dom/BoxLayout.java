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
        List<Dimension> childrenSizes = calculateChildrenSizes(bounds);

        if (! childrenSizes.isEmpty()) {
            placeChildren(childrenSizes);
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

    private List<Dimension> calculateChildrenSizes(Rectangle bounds) {
        List<Dimension> childrenSizes = new ArrayList<>();

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

        int i = 1;
        for (LayoutItem n : content) {
            Dimension hint = n.getSizeHint();
            Dimension itemSize = (Dimension) hint.clone();
            int possibleLength = isVertical() ? itemSize.height : itemSize.width;

            SizePolicy policy = isVertical() ? n.getVerticalPolicy() : n.getHorizontalPolicy();
            if (policy.isExpanding()) {
                int stretchLength = freeLength * i / stretchedCount - freeLength * (i - 1) / stretchedCount;
                if (isVertical()) {
                    itemSize.height += stretchLength;
                } else {
                    itemSize.width += stretchLength;
                }

                if (policy.isGrowing()) {
                    if (isVertical()) {
                        if (possibleLength > hint.height) {
                            itemSize.height = possibleLength;
                        }
                    } else {
                        if (possibleLength > hint.width) {
                            itemSize.width = possibleLength;
                        }
                    }
                }
                if (policy.isShrinking()) {
                    if (isVertical()) {
                        if (possibleLength < hint.height) {
                            itemSize.height = possibleLength;
                        }
                    } else {
                        if (possibleLength < hint.width) {
                            itemSize.width = possibleLength;
                        }
                    }
                }
            }

            SizePolicy crossPolicy = n.getHorizontalPolicy();

            if (isVertical()) {
                if (crossPolicy.isExpanding()) {
                    itemSize.width = bounds.width;
                } else if (hint.width < bounds.width) {
                    itemSize.width = hint.width;
                }
            } else {
                if (crossPolicy.isExpanding()) {
                    itemSize.height = bounds.height;
                } else if (hint.height < bounds.height) {
                    itemSize.height = hint.height;
                }
            }

            if (! crossPolicy.isGrowing()) {
                if (isVertical()) {
                    if (itemSize.width < hint.width) {
                        itemSize.width = hint.width;
                    }
                } else {
                    if (itemSize.height < hint.height) {
                        itemSize.height = hint.height;
                    }
                }
            }
            if (! crossPolicy.isShrinking()) {
                if (itemSize.width > hint.width) {
                    itemSize.width = hint.width;
                }
            }

            childrenSizes.add(itemSize);
            ++i;
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
