import java.awt.*;

public class Paddle{

    public int paddleNumber;

    public int x, y, width = 30, height = 175;

    public int score;

    public Color c;

    public Paddle(Pong pong, int paddleNumber) {
        this.paddleNumber = paddleNumber;

        if (paddleNumber == 1) {
            this.x = 0;
        }
        if (paddleNumber == 2) {
            this.x = pong.getWidth() - width - 1;
        }
        this.y = pong.getHeight()/2 - this.height/2;
    }

    public void render(Graphics2D g) {
        if (score < Pong.pong.scoreLimit - 2) {
            g.setColor(Color.CYAN);
        } else {
            if (Pong.pong.paintingCount % 30 == 0) {
                c = Color.MAGENTA;
            } else if (Pong.pong.paintingCount % 15 == 0){
                c = Color.YELLOW;
            }
        }
        g.setColor(c);
        g.drawRect(x, y, width, height);
    }

    public void move(boolean up) {
        int speed = 15;
        if(up) {
            if (y - speed > 0) {
                y -= speed;
            } else {
                y = 0;
            }
        } else {
            if (y + height + speed < Pong.pong.getHeight()) {
                y+= speed;
            } else {
                y = Pong.pong.getHeight() - height -1;
            }
        }
    }
}
