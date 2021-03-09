package alfertev2014.layout2d.dom;

import alfertev2014.layout2d.scene.SceneNode;

import java.util.stream.Stream;

public interface Renderable {

    Stream<SceneNode> render();
}
