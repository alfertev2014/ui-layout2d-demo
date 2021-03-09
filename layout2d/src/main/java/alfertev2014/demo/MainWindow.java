package alfertev2014.demo;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() throws HeadlessException {
        setSize(500, 400);
        setTitle("Layouts");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        var canvas = new Canvas();
        canvas.setVisible(true);
        contentPane.add(canvas);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new MainWindow();
            ex.setVisible(true);
        });
    }
}
