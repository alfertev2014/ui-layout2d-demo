package alfertev2014.demo;

import alfertev2014.layout2d.dom.*;
import alfertev2014.layout2d.dom.BoxLayout;
import alfertev2014.layout2d.scene.SceneNode;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class Canvas extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        tree.setBounds(getBounds());
        for (SceneNode n : tree.render().collect(Collectors.toList())) {
            n.draw(g, new Point(0, 0));
        };
    }

    private final LayoutItem tree =
            FrameNode.of(new Dimension(500, 300), Color.YELLOW, Color.BLUE,
                    BoxLayout.of(1,
                            TextNode.of(Color.BLACK, new Font("Serif", Font.PLAIN, 20),
                                    "Hello!\nWorld!!!"
                            )
                    )
            );
}
