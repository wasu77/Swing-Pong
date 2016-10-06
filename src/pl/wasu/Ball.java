package pl.wasu;

import pl.wasu.enums.CollisionResult;

import java.util.Random;

public class Ball {
    private int x;
    private int y;
    private int motionX;
    private int motionY;
    private Random random;
    private int radius = 25;
    private int amountOfHits;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getAmountOfHits() { return amountOfHits;}

    public Ball(Pong pong) {
        this.random = new Random();
        start(pong);
    }
    public void start(Pong pong) {
        this.amountOfHits = 0;

        this.y = pong.getBoardHeight()/2 - radius/2;
        this.x = pong.getBoardWidth()/2 - radius/2;

        this.motionY = -2 + random.nextInt(4);
        this.motionX = -1 + random.nextInt(1);

        if (motionY == 0) {
            motionY = 1;
        }
    }

    public void update(Paddle paddle1, Paddle paddle2, Pong pong) {
        int ballSpeed = 5;

        this.x += motionX * ballSpeed;
        this.y += motionY * ballSpeed;
        int score1 = paddle1.getScore();
        int score2 = paddle2.getScore();

        //Collision with walls
        if (this.y < 0 || this.y > pong.getBoardHeight() - radius) {
            this.motionY = -motionY;
        }
        /*Collision with paddle on the left side (Paddle 1)*/
        if (checkCollision(paddle1) == CollisionResult.HIT) {
            this.motionX = 1 + (amountOfHits/5);
            int[] list = {-2, -1, 1, 2};
            this.motionY = list[random.nextInt(list.length)];
            amountOfHits++;
        } else if (checkCollision(paddle1) == CollisionResult.MISS) {
            score2++;
            paddle2.setScore(score2);
            start(pong);
        }
        /*Collision with paddle on the right side (Paddle 2)*/
        if (checkCollision(paddle2) == CollisionResult.HIT) {
            this.motionX = -1 - (amountOfHits/5);
            int[] list = {-2, -1, 1, 2};
            this.motionY = list[random.nextInt(list.length)];
            amountOfHits++;
        } else if (checkCollision(paddle2) == CollisionResult.MISS) {
            score1++;
            paddle1.setScore(score1);
            start(pong);
        }
    }

    public CollisionResult checkCollision(Paddle paddle) {

        if (this.x < paddle.getX() + paddle.getPaddleWidth() && this.x + radius > paddle.getX() && this.y < paddle.getY() + paddle.getPaddleHeight() && this.y +radius > paddle.getY()) {
            return CollisionResult.HIT; // miss the paddle
        } else if ((paddle.getX() > x + radius && paddle.getPaddleNumber() == 1) || (paddle.getX() < x && paddle.getPaddleNumber() == 2)) {
            return CollisionResult.MISS; //hit the paddle
        }
        return CollisionResult.NONE; //nothing - ball is flying
    }
}
