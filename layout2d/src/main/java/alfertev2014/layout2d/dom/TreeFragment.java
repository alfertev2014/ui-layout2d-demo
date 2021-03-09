package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.scene.SceneFragment;
import alfertev2014.layout2d.scene.SceneNode;

import java.util.List;
import java.util.stream.Collectors;

public interface TreeFragment extends Renderable {

    List<? extends TreeNode> getContent();

    @Override
    default SceneNode render() {
        final List<SceneNode> content = getContent().stream()
                .map(Renderable::render)
                .collect(Collectors.toList());
        return (SceneFragment) () -> content;
    }
}
