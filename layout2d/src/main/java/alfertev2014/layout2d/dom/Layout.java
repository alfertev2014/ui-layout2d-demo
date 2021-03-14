package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasSizeHint;

import java.util.Collections;
import java.util.List;

public interface Layout extends TreeFragment, HasLayout, HasSizeHint {

    @Override
    default List<? extends TreeNode> getContent() {
        return getItems();
    }

    List<? extends LayoutItem> getItems();

    static Layout empty() {
        return new BoxLayout() {
            @Override
            public int getSpacing() {
                return 0;
            }

            @Override
            public Direction getDirection() {
                return Direction.Vertical;
            }

            @Override
            public List<? extends LayoutItem> getItems() {
                return Collections.emptyList();
            }
        };
    }
}
