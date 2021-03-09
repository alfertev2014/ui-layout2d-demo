package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasSizeHint;
import alfertev2014.layout2d.geom.SizePolicy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface BoxLayout extends Layout {

    int getSpacing();

    @Override
    default void updateLayout(Rectangle bounds) {
        List<Dimension> boundsHints = getBoundsHints(bounds);

        placeChildren(boundsHints);
    }

    private void placeChildren(List<Dimension> boundsHints) {
        int i = 0;
        int y = 0;
        for (LayoutItem n : getItems()) {
            Dimension size = boundsHints.get(i);
            n.setBounds(new Rectangle(0, y, size.width, size.height));
            ++i;
            y += size.height + getSpacing();
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
            SizePolicy policy = n.getVerticalPolicy();
            if (policy.isExpanding() && policy.isGrowing()) {
                ++growCount;
            }
            if (policy.isShrinking()) {
                ++shrinkCount;
            }
            Dimension sizeHint = n.getSizeHint();
            preferredLength += sizeHint.height;
        }


        int freeLength = bounds.height - preferredLength;

        int stretchedCount = 0;
        if (freeLength >= 0) {
            stretchedCount = growCount;
        } else {
            stretchedCount = shrinkCount;
        }

        if (stretchedCount <= 0) {
            return boundsHints;
        }

        int i = 1;
        for (LayoutItem n : content) {
            Dimension size = n.getSizeHint();
            Dimension hint = (Dimension) size.clone();
            int preferredHeight = hint.height;

            SizePolicy verticalPolicy = n.getVerticalPolicy();
            if (verticalPolicy.isExpanding()) {
                hint.height += freeLength * i / stretchedCount - freeLength * (i - 1) / stretchedCount;

                if (verticalPolicy.isGrowing()) {
                    if (preferredHeight > size.height) {
                        hint.height = preferredHeight;
                    }
                }
                if (verticalPolicy.isShrinking()) {
                    if (preferredHeight < size.height) {
                        hint.height = preferredHeight;
                    }
                }
            }

            SizePolicy horizontalPolicy = n.getHorizontalPolicy();

            int preferredWidth = bounds.width;
            if (horizontalPolicy.isExpanding()) {
                hint.width = preferredWidth;
            } else if (size.width < preferredWidth) {
                hint.width = size.width;
            }

            if (horizontalPolicy.isGrowing()) {
                if (hint.width < size.width) {
                    hint.width = size.width;
                }
            }
            if (horizontalPolicy.isShrinking()) {
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

        List<Dimension> hints = getHints();

        Dimension res = new Dimension(0, 0);

        if (hints.isEmpty())
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


    private List<Dimension> getHints() {
        return getItems().stream().map(HasSizeHint::getSizeHint).collect(Collectors.toUnmodifiableList());
    }

    static BoxLayout of(int spacing, LayoutItem ...nodes) {
        return of(spacing, List.of(nodes));
    }

    static BoxLayout of(int spacing, List<? extends LayoutItem> content) {
        return new BoxLayout() {
            @Override
            public int getSpacing() {
                return spacing;
            }

            @Override
            public List<? extends LayoutItem> getItems() {
                return content;
            }
        };
    }
}
