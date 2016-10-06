package pl.wasu.objectsRendering;

import pl.wasu.Ball;

import java.awt.*;

public class BallRenderer {
    public Color ballColor;

    public void ballRender(Ball ball, Graphics2D g) {
        if (ball.getAmountOfHits() > 7) {
            ballColor = Color.GREEN;
        } else {
            ballColor = Color.CYAN;
        }
        g.setColor(ballColor);
        g.fillOval(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());
    }
}
