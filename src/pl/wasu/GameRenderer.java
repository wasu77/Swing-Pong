package pl.wasu;

import pl.wasu.enums.GameMode;
import pl.wasu.enums.GameStatus;
import pl.wasu.objectsRendering.BallRenderer;
import pl.wasu.objectsRendering.PaddleRenderer;

import java.awt.*;

public class GameRenderer {

    public GameRenderer() {

    }

    public void render(Graphics2D g, Pong pong, Ball ball, Paddle paddle1, Paddle paddle2, BallRenderer ballR, PaddleRenderer paddleR) {
        drawBoard(pong, g);
        if (pong.getState() == Pong.STATE.MENU) {
            renderMenu(pong, g);
        }
        if (pong.getState() == Pong.STATE.GAME) {
            if (pong.getGameStatusEnum() == GameStatus.RUNNING || pong.getGameStatusEnum() == GameStatus.PAUSED) {
                drawWhenGameIsRunning(pong, g);
                ballR.ballRender(ball, g);
                paddleR.paddle1Render(pong, paddle1, g);
                paddleR.paddle2Render(pong, paddle2, g);
            }
            if (pong.getGameStatusEnum() == GameStatus.PAUSED) {
                drawPauseMenu(pong, g);
            }
            if (pong.finishGameIfScoreLimitExceeded(pong.getScoreLimit()) == GameStatus.FINISHED) {
                drawAfterWin(pong, g);
            }
        }
    }

    public void drawBoard(Pong pong, Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, pong.getBoardWidth(), pong.getBoardHeight());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    public void drawInstructions(Pong pong, Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", 1, 30));
        g.drawString("Instructions", pong.getBoardWidth() / 2 - 115, 100);
        g.setFont(new Font("Verdana", 1, 15));
        g.drawString("Press M to turn ON / OFF music", pong.getBoardWidth()/ 2 - 145, 130);
    }
    public void renderMenu(Pong pong, Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", 1, 40));
        g.drawString("PONG", (pong.getBoardWidth()/ 2) - 62, 50);
        if (pong.instructions) {
            drawInstructions(pong, g);
        } else {
            g.setFont(new Font("Arial", 1, 20));
            g.drawString("Choose score limit - press LEFT / RIGHT : " + pong.getScoreLimit(), pong.getBoardWidth()/2 - 185, pong.getBoardHeight()/2 -150);
            g.drawString("Single player game - press SPACE", pong.getBoardWidth() / 2 - 165, pong.getBoardHeight() / 2 - 100);
            g.drawString("2 Players game - press SHIFT", pong.getBoardWidth() / 2 - 150, pong.getBoardHeight() / 2 - 50);
            g.drawString("Choose bot difficulty - press D : " + getStringDifficulty(pong.botDifficulty), pong.getBoardWidth() / 2 - 170, pong.getBoardHeight() / 2);
        }
    }
    public void drawPauseMenu(Pong pong, Graphics2D g) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", 1, 50));
        g.drawString("PAUSED", pong.getBoardWidth()/2 - 102, pong.getBoardHeight()/2);
    }

    public void drawWhenGameIsRunning(Pong pong, Graphics2D g) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", 1, 50));
        g.drawString(String.valueOf(pong.paddle1.getScore()), pong.getBoardWidth()/2 - 90, 50);
        g.drawString(String.valueOf(pong.paddle2.getScore()), pong.getBoardWidth()/2 + 65, 50);

        g.setStroke(new BasicStroke(1));
        g.drawLine(pong.getBoardWidth() / 2, 0, pong.getBoardWidth() / 2, pong.getBoardHeight());

    }

    public void drawAfterWin(Pong pong, Graphics2D g) {
        if (pong.finishGameIfScoreLimitExceeded(pong.getScoreLimit()) == GameStatus.FINISHED) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PONG", pong.getBoardWidth() / 2 - 75, 50);
            if (pong.gameModeEnum == GameMode.BOT && pong.playerWon == 2) {
                g.drawString("Computer wins!", pong.getBoardWidth() / 2 - 170, 200);
            } else {
                g.drawString("Player " + pong.playerWon + " wins!", pong.getBoardWidth() / 2 - 170, 200);
            }
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Press Space to Play Again", pong.getBoardWidth() / 2 - 185, 400);
            g.drawString("Press ESC for Main Menu", pong.getBoardWidth() / 2 - 185, 350);
        }
    }

    public String getStringDifficulty(int botDifficulty) {
        switch (botDifficulty) {
            case 0:
                return "EASY";
            case 1:
                return "MEDIUM";
            case 2:
                return "HARD";
            default:
                return "default";
        }
    }


}

