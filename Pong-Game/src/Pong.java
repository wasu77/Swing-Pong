import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong implements ActionListener, KeyListener {

    public static Pong pong;

    public Renderer renderer;

    public Paddle player1;

    public Paddle player2;

    public Ball ball;

    public Music music;

    public int paintingCount = 0;

    public int botDifficulty, botMoves, botDelay = 0;

    public boolean bot = false, selectDifficulty, instructions;

    public boolean w, s, up, down;

    public int gameStatus = 0; //0 - stooped, 1 - paused, 2 - game is running

    public int scoreLimit = 7, playerWon;

    private int width = 700, height = 700;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Pong() {
        Timer timer = new Timer(20, this);
        music = new Music();
        //music.startMusic();
        JFrame jFrame = new JFrame("Pong game");
        renderer = new Renderer();
        jFrame.setSize(width , height);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(renderer);
        jFrame.addKeyListener(this);


        start();

        timer.start();
    }

    public void render(Graphics2D g) {
        //Renders different view depends on gameStatus
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*Game status = 0
        * That's a main menu - when "SPACE" is pressed it starts game for two players,
        *                    - when "SHIFT" is pressed it starts game for one player (with Bot)
        *                    - when "I" is pressed it draws game instructions*/

        if (gameStatus == 0) {
            // Remember to fight with drawing strings in good place
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 1, 40));
            g.drawString("PONG", pong.getWidth() / 2 - 75, 50);

            if (instructions) {
                //Block displayed when "I" is pressed - draw some instructions
                g.setColor(Color.WHITE);
                g.setFont(new Font("Verdana", 1, 30));
                g.drawString("Instructions", pong.getWidth()/2 - 115, 100);
            }
            else if (!selectDifficulty) {
                /*In this view, player can choose points that are necessary to win the game*/
                g.setFont(new Font("Arial", 1, 20));
                g.drawString("Choose score limit - LEFT / RIGHT : " + scoreLimit, pong.getWidth()/2 - 185, pong.getHeight()/2 -150);
                g.drawString("2 Players game - press SHIFTm", pong.getWidth() / 2 - 150, pong.getHeight() / 2 - 50);
                g.drawString("Single player game - press SPACE", pong.getWidth() / 2 - 165, pong.getHeight() / 2 - 100);
            }
        }
        if (selectDifficulty) {
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Choose bot difficulty: " + getStringDifficulty(botDifficulty), pong.getWidth()/2 - 150, pong.getHeight()/2 - 50);
            g.drawString("Press Space to play", pong.getWidth()/2 - 150, pong.getHeight()/2 + 25);
        }
        if (gameStatus == 1) {
            g.setColor(Color.CYAN);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSED", pong.getWidth()/2 - 102, pong.getHeight()/2);
        }
        if (gameStatus == 1 || gameStatus == 2) {
            g.setColor(Color.CYAN);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString(String.valueOf(player1.score), pong.getWidth()/2 - 90, 50);
            g.drawString(String.valueOf(player2.score), pong.getWidth()/2 + 65, 50);

            g.setStroke(new BasicStroke(1));
            g.drawLine(width / 2, 0, width / 2, height);

            g.setStroke(new BasicStroke(3));
            player1.render(g);
            player2.render(g);
            ball.render(g);
        }
        if (gameStatus == 3) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PONG", pong.getWidth() / 2 - 75, 50);

            if (bot && playerWon == 2) {
                g.drawString("Computer wins!", pong.getWidth() / 2 - 170, 200);
            }
            else {
                g.drawString("Player " + playerWon + " wins!", pong.getWidth() / 2 - 170, 200);
            }
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Press Space to Play Again" , pong.getWidth() / 2 - 185, 400);
            g.drawString("Press ESC for Main Menu", pong.getWidth() / 2 - 185, 350);
        }

    }
    public String getStringDifficulty(int botDifficulty) {
        String stringDifficulty = "Default";
        switch (botDifficulty) {
            case 0:
                stringDifficulty = "easy";
                break;
            case 1:
                stringDifficulty = "medium";
                break;
            case 2:
                stringDifficulty = "hard";
                break;
        }
        return stringDifficulty;
    }

    public void start() {

        player1 = new Paddle(this, 1);
        player2 = new Paddle(this, 2);
        ball = new Ball(this);
    }

    public void update() {

        if (player1.score == scoreLimit) {
            playerWon = 1;
            gameStatus = 3;
        }

        if (player2.score == scoreLimit) {
            playerWon = 2;
            gameStatus = 3;
        }

        if (w) {
            player1.move(true);
        }
        if (s) {
            player1.move(false);
        }
        if(!bot) {
            if (up) {
                player2.move(true);
            }
            if (down) {
                player2.move(false);
            }
        } else {
            if (botDelay > 0) {
                botDelay--;
                if (botDelay == 0) {
                    botMoves = 0;
                }
            }
            if (botMoves < 10) {
                if (player2.y < ball.y) {
                    player2.move(false);
                    botMoves++;
               }
                if (player2.y > ball.y) {
                    player2.move(true);
                    botMoves++;
                }
                if (botDifficulty == 0) {
                    botDelay = 15;
                }
                if (botDifficulty == 1) {
                    botDelay = 10;
                }
                if (botDifficulty == 2) {
                    botDelay = 5;
                }
            }
        }
        ball.update(player1, player2);
    }
    public void actionPerformed(ActionEvent e) {

        if (gameStatus == 2) {
            update();
        }
        renderer.repaint();
        paintingCount++;

    }
    public static void main(String[] args) {
        pong = new Pong();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int id = keyEvent.getKeyCode();
        if (id == KeyEvent.VK_M) {
            music.startMusic();
        }
        if (id == KeyEvent.VK_W) {
            w = true;
        }
        else if (id == KeyEvent.VK_S) {
            s = true;
        }
        else if (id == KeyEvent.VK_UP) {
            up = true;
        }
        else if (id == KeyEvent.VK_DOWN) {
            down = true;
        }
        else if (id == KeyEvent.VK_RIGHT) {

            if (selectDifficulty) {
                if (botDifficulty < 2) {
                    botDifficulty++;
                }
            }
            else if (gameStatus == 0 && scoreLimit < 15) {
                    scoreLimit++;
            }
        }
        else if (id == KeyEvent.VK_LEFT) {
            if (selectDifficulty) {
                if (botDifficulty > 0) {
                    botDifficulty--;
                }
            }
            else if (gameStatus == 0 && scoreLimit > 2) {
                    scoreLimit--;
                }

        }
        else if (id == KeyEvent.VK_ESCAPE) {
            gameStatus = 0;
            if (selectDifficulty) {
                selectDifficulty = false;
            }
        }
        else if (id == KeyEvent.VK_SHIFT && gameStatus == 0) {
            bot = true;
            selectDifficulty = true;
        }
        else if (id == KeyEvent.VK_SPACE) {
            if (gameStatus == 0) {
                player1.score = 0;
                player2.score = 0;
                gameStatus = 2;
                bot = false;
            }
            else if (gameStatus == 2) {
                gameStatus = 1;
            }
            else if (gameStatus == 1) {
                gameStatus = 2;
            }
            else if (gameStatus == 3) {
                player1.score = 0;
                player2.score = 0;
                if(bot) {
                    bot = true;
                } else {
                    bot = false;
                }
                gameStatus = 2;
            }
            if (selectDifficulty) {
                bot = true;
                gameStatus = 2;
                selectDifficulty = false;
            }
        }
        else if (id == KeyEvent.VK_I && gameStatus == 0) {

            if (!selectDifficulty) {
                instructions = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int id = keyEvent.getKeyCode();
        if (id == KeyEvent.VK_W) {
            w = false;
        }
        else if (id == KeyEvent.VK_S) {
            s = false;
        }
        else if (id == KeyEvent.VK_UP) {
            up = false;
        }
        else if (id == KeyEvent.VK_DOWN) {
            down = false;
        }
        else if (id == KeyEvent.VK_I) {
            instructions = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

}
