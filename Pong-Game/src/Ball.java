import java.awt.*;
import java.util.Random;

public class Ball {
    private Pong pong;
    //private Paddle paddle;

    public int x, y, radius = 25;

    public int motionX, motionY;

    public int amountOfHits;

    public Random random;

    public Ball(Pong pong) {

        this.pong = pong;

        this.random = new Random();

        spawn();
    }

    public void update(Paddle paddle1, Paddle paddle2) {
        int ballSpeed = 5;

        this.x += motionX * ballSpeed;

        this.y += motionY * ballSpeed;
        //Checking collision with walls
        if (this.y < 0 || this.y + radius > pong.getHeight()) {
            this.motionY = -motionY;
        }
        // Collision type - #1 - ball hit paddle
        if (checkCollision(paddle1) == 1) {
            this.motionX = 1 + (amountOfHits/5);
            //Try to work something to get better results when ball hits the paddle (for paddle2 do exactly the same)
            int[] list = {-2, -1, 1, 2};
            this.motionY = list[random.nextInt(list.length)];
            //this.motionY = -2 + random.nextInt(4);
            amountOfHits++;
            System.out.println(amountOfHits);

        } else if (checkCollision(paddle2) == 1) {
            this.motionX = -1 - (amountOfHits/5);
            int[] list = {-2, -1, 1, 2};
            this.motionY = list[random.nextInt(list.length)];
            amountOfHits++;
            System.out.println(amountOfHits);
        }
        // Collision type - #2 - ball hit the wall, point++ for the opponent
        if (checkCollision(paddle1) == 2) {
            paddle2.score++;
            spawn();

        } else if (checkCollision(paddle2) == 2) {
            paddle1.score++;
            spawn();
        }
    }

    public int checkCollision(Paddle paddle) {
        //Checking collisions with paddles and walls behind them
        if (this.x < paddle.x + paddle.width && this.x + radius > paddle.x && this.y < paddle.y + paddle.height && this.y +radius > paddle.y) {
            return 1; // miss the paddle

        } else if ((paddle.x > x + radius && paddle.paddleNumber == 1) || (paddle.x < x && paddle.paddleNumber == 2)) {
            return 2; //hit the paddle
        }
        return 0; //nothing
    }

    public void spawn() {
        this.amountOfHits = 0;

        this.x = pong.getWidth()/2 - this.radius/2;
        this.y = pong.getHeight()/2 - this.radius/2;

        this.motionY = -2 + random.nextInt(4);
        this.motionX = -1 + random.nextInt(1);

        if (motionY == 0) {
            motionY = 1;
        }
    }

    public void render(Graphics2D g) {
        //Colour of ball will change after 5 hits
        if (amountOfHits < 5) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.PINK);
        }
        g.fillOval(x, y, radius, radius);
    }



}
