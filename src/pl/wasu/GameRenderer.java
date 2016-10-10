package pl.wasu;

import pl.wasu.enums.GameMode;
import pl.wasu.enums.GameStatus;
import pl.wasu.objectsRendering.BallRenderer;
import pl.wasu.objectsRendering.PaddleRenderer;

import java.awt.*;

public class GameRenderer {

    public void render(Graphics2D g, BoardSize boardSize, Pong pong, Ball ball, Paddle paddle1, Paddle paddle2, BallRenderer ballR, PaddleRenderer paddleR) {
        drawBoard(boardSize, g);
        if (pong.getState() == Pong.STATE.MENU) {
            renderMenu(boardSize, pong, g);
        }
        if (pong.getState() == Pong.STATE.GAME) {
            if (pong.getGameStatusEnum() == GameStatus.RUNNING || pong.getGameStatusEnum() == GameStatus.PAUSED) {
                drawWhenGameIsRunning(boardSize, pong, g);
                ballR.ballRender(ball, g);
                paddleR.paddle1Render(pong, paddle1, g);
                paddleR.paddle2Render(pong, paddle2, g);
            }
            if (pong.getGameStatusEnum() == GameStatus.PAUSED) {
                drawPauseMenu(boardSize, g);
            }
            if (pong.finishGameIfScoreLimitExceeded(pong.getScoreLimit()) == GameStatus.FINISHED) {
                drawAfterWin(boardSize, pong, g);
            }
        }
    }

    private void drawBoard(BoardSize boardSize, Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, boardSize.getBoardWidth(), boardSize.getBoardHeight());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    private void drawInstructions(BoardSize boardSize, Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", 1, 30));
        g.drawString("Instructions", boardSize.getBoardWidth() / 2 - 115, 100);
        g.setFont(new Font("Verdana", 1, 15));
        g.drawString("Press M to turn ON / OFF music", boardSize.getBoardWidth()/ 2 - 145, 130);
    }
    private void renderMenu(BoardSize boardSize, Pong pong,  Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", 1, 40));
        g.drawString("PONG", (boardSize.getBoardWidth()/ 2) - 62, 50);
        if (pong.instructions) {
            drawInstructions(boardSize, g);
        } else {
            g.setFont(new Font("Arial", 1, 20));
            g.drawString("Choose score limit - press LEFT / RIGHT : " + pong.getScoreLimit(), boardSize.getBoardWidth()/2 - 185, boardSize.getBoardHeight()/2 -150);
            g.drawString("Single player game - press SPACE", boardSize.getBoardWidth() / 2 - 165, boardSize.getBoardHeight() / 2 - 100);
            g.drawString("2 Players game - press SHIFT", boardSize.getBoardWidth() / 2 - 150, boardSize.getBoardHeight() / 2 - 50);
            g.drawString("Choose bot difficulty - press D : " + getStringDifficulty(pong.botDifficulty), boardSize.getBoardWidth() / 2 - 170, boardSize.getBoardHeight() / 2);
        }
    }
    private void drawPauseMenu(BoardSize boardSize, Graphics2D g) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", 1, 50));
        g.drawString("PAUSED", boardSize.getBoardWidth()/2 - 102, boardSize.getBoardHeight()/2);
    }

    private void drawWhenGameIsRunning(BoardSize boardSize, Pong pong, Graphics2D g) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", 1, 50));
        g.drawString(String.valueOf(pong.paddle1.getScore()), boardSize.getBoardWidth()/2 - 90, 50);
        g.drawString(String.valueOf(pong.paddle2.getScore()), boardSize.getBoardWidth()/2 + 65, 50);

        g.setStroke(new BasicStroke(1));
        g.drawLine(boardSize.getBoardWidth() / 2, 0, boardSize.getBoardWidth() / 2, boardSize.getBoardHeight());

    }

    private void drawAfterWin(BoardSize boardSize, Pong pong, Graphics2D g) {
        if (pong.finishGameIfScoreLimitExceeded(pong.getScoreLimit()) == GameStatus.FINISHED) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PONG", boardSize.getBoardWidth() / 2 - 75, 50);
            if (pong.gameModeEnum == GameMode.BOT && pong.playerWon == 2) {
                g.drawString("Computer wins!", boardSize.getBoardWidth() / 2 - 170, 200);
            } else {
                g.drawString("Player " + pong.playerWon + " wins!", boardSize.getBoardWidth() / 2 - 170, 200);
            }
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Press Space to Play Again", boardSize.getBoardWidth() / 2 - 185, 400);
            g.drawString("Press ESC for Main Menu", boardSize.getBoardWidth() / 2 - 185, 350);
        }
    }

    private String getStringDifficulty(int botDifficulty) {
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

