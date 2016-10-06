package pl.wasu;

public class PaddleMovement {

    public int botMoves;
    public int botDelay = 0;

    public void movePaddleOne(Pong pong) {
        if (pong.W) {
            pong.paddle1.moveUp();
        }
        if (pong.S) {
            pong.paddle1.moveDown();
        }
    }

    public void movePaddleTwo(Pong pong) {
        if (pong.UP) {
            pong.paddle2.moveUp();
        }
        if (pong.DOWN) {
            pong.paddle2.moveDown();
        }
    }
    public void moveBot(Pong pong, Ball ball) {
        if (botDelay > 0) {
            botDelay--;
            if (botDelay == 0) {
                botMoves = 0;
            }
        }
        if (botMoves < 10) {
            if (pong.paddle2.getY() < ball.getY()) {
                pong.paddle2.move(false);
                botMoves++;
            }
            if (pong.paddle2.getY() > ball.getY()) {
                pong.paddle2.move(true);
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
