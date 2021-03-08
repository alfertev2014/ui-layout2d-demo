package alfertev2014.layout2d.dom;

import java.util.List;

public interface Layout extends TreeFragment, LayoutItem, HasLayout {

    @Override
    default List<? extends TreeNode> getContent() {
        return getItems();
    }

    List<? extends LayoutItem> getItems();
}
