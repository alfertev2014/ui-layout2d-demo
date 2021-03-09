package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.scene.SceneNode;

import java.util.List;
import java.util.stream.Stream;

public interface TreeFragment extends Renderable {

    List<? extends TreeNode> getContent();

    @Override
    default Stream<SceneNode> render() {
        return getContent().stream().flatMap(Renderable::render);
    }
}
