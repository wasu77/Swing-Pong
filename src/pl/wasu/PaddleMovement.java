package pl.wasu;

public class PaddleMovement {

    private int botMoves;
    private int botDelay = 0;

    public void movePaddleOne(BoardSize boardSize, Pong pong) {
        if (pong.W) {
            pong.paddle1.moveUp();
        }
        if (pong.S) {
            pong.paddle1.moveDown(boardSize);
        }
    }

    public void movePaddleTwo(BoardSize boardSize, Pong pong) {
        if (pong.UP) {
            pong.paddle2.moveUp();
        }
        if (pong.DOWN) {
            pong.paddle2.moveDown(boardSize);
        }
    }
    public void moveBot(Pong pong, Ball ball, BoardSize boardSize) {
        if (botDelay > 0) {
            botDelay--;
            if (botDelay == 0) {
                botMoves = 0;
            }
        }
        if (botMoves < 10) {
            if (pong.paddle2.getY() < ball.getY()) {
                pong.paddle2.move(boardSize, false);
                botMoves++;
            }
            if (pong.paddle2.getY() > ball.getY()) {
                pong.paddle2.move(boardSize, true);
                botMoves++;
            }
            if (pong.botDifficulty == 0) {
                botDelay = 10;
            }
            if (pong.botDifficulty == 1) {
                botDelay = 7;
            }
            if (pong.botDifficulty == 2) {
                botDelay = 4;
            }
        }
    }
}
