package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.geom.HasSizeHint;

import java.util.List;

public interface Layout extends TreeFragment, HasLayout, HasSizeHint {

    @Override
    default List<? extends TreeNode> getContent() {
        return getItems();
    }

    List<? extends LayoutItem> getItems();
}
