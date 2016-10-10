package pl.wasu;

public class Paddle {

    private int paddleNumber;
    private int x;
    private int y;

    private int paddleWidth = 30;
    private int paddleHeight = 175;
    private int speed = 15;
    private int score;

    public void setScore(int score) {
        this.score = score;
    }

    public int getPaddleNumber() {
        return paddleNumber;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public int getScore() {
        return score;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Paddle(BoardSize boardSize, int paddleNumber) {
        this.paddleNumber = paddleNumber;
        if (paddleNumber == 1) {
            this.x = 0;
        }
        if (paddleNumber == 2) {
            this.x = boardSize.getBoardWidth() - paddleWidth - 1;
        }
        this.y = boardSize.getBoardHeight()/2 - this.paddleHeight/2;
    }

    public void move(BoardSize boardSize, boolean up) {
        if(up) {
            moveUp();
        } else {
            moveDown(boardSize);
        }
    }

    public void moveUp() {
        if (y - speed > 0) {
            y-= speed;
        } else {
            y = 0;
        }
    }
    public void moveDown(BoardSize boardSize) {
        if (y + paddleHeight + speed < boardSize.getBoardHeight()) {
            y+= speed;
        } else {
            y = boardSize.getBoardHeight() - paddleHeight;
        }
    }


}
