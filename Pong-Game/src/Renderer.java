import javax.swing.*;


import java.awt.*;

public class Renderer extends JPanel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Pong.pong.render((Graphics2D) g);

    }
}
