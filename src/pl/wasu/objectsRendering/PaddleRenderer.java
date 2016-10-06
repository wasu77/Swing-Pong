package pl.wasu.objectsRendering;

import pl.wasu.Paddle;
import pl.wasu.Pong;

import java.awt.*;

public class PaddleRenderer {
    Color paddle1Color;
    Color paddle2Color;

    public void paddle1Render(Pong pong, Paddle paddle1, Graphics2D g) {
        if (paddle1.getScore() > pong.getScoreLimit() -2) {
            if (pong.getCountRepaints() % 30 == 0) {
                paddle1Color = Color.MAGENTA;
            } else if (pong.getCountRepaints() % 15 == 0) {
                paddle1Color = Color.YELLOW;
            }
        } else {
            paddle1Color = Color.CYAN;
        }
        g.setColor(paddle1Color);
        g.drawRect(paddle1.getX(), paddle1.getY(), paddle1.getPaddleWidth(), paddle1.getPaddleHeight());
    }
    public void paddle2Render(Pong pong, Paddle paddle2, Graphics2D g) {
        if (paddle2.getScore() > pong.getScoreLimit() -2) {
            if (pong.getCountRepaints() % 30 == 0) {
                paddle2Color = Color.MAGENTA;
            } else if (pong.getCountRepaints() % 15 == 0) {
                paddle2Color = Color.YELLOW;
            }
        } else {
            paddle2Color = Color.CYAN;
        }
        g.setColor(paddle2Color);
        g.drawRect(paddle2.getX(), paddle2.getY(), paddle2.getPaddleWidth(), paddle2.getPaddleHeight());
    }
}
