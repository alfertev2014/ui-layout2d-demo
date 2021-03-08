package alfertev2014.demo;

import alfertev2014.layout2d.dom.BoxLayout;
import alfertev2014.layout2d.dom.FrameNode;
import alfertev2014.layout2d.dom.TextNode;
import alfertev2014.layout2d.dom.TreeFragment;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        getTree().render().draw(g, new Point(0, 0));
    }

    private final TreeFragment tree = BoxLayout.of(1,
            FrameNode.of(new Dimension(500, 300),
                    TextNode.of(Color.BLACK, Font.getFont("Monospace"), "Hello!")
            )
    );

    private TreeFragment getTree() {
        return tree;
    }
}
