package pl.wasu.objectsRendering;

import pl.wasu.Paddle;
import pl.wasu.Pong;

import java.awt.*;

public class PaddleRenderer {
    private Color paddle1Color;
    private Color paddle2Color;

    public void paddle1Render(Pong pong, Paddle paddle, Graphics2D g) {
        if (paddle.getScore() > pong.getScoreLimit() - 2) {
            if (pong.getCountRepaints() % 30 == 0) {
                paddle1Color = Color.MAGENTA;
            } else if (pong.getCountRepaints() % 15 == 0) {
                paddle1Color = Color.YELLOW;
            }
        } else {
            paddle1Color = Color.CYAN;
        }
        g.setColor(paddle1Color);
        g.drawRect(paddle.getX(), paddle.getY(), paddle.getPaddleWidth(), paddle.getPaddleHeight());
    }

    public void paddle2Render(Pong pong, Paddle paddle, Graphics2D g) {
        if (paddle.getScore() > pong.getScoreLimit() - 2) {
            if (pong.getCountRepaints() % 30 == 0) {
                paddle2Color = Color.MAGENTA;
            } else if (pong.getCountRepaints() % 15 == 0) {
                paddle2Color = Color.YELLOW;
            }
        } else {
            paddle2Color = Color.CYAN;
        }
        g.setColor(paddle2Color);
        g.drawRect(paddle.getX(), paddle.getY(), paddle.getPaddleWidth(), paddle.getPaddleHeight());
    }

}
