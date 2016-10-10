package pl.wasu;

import pl.wasu.enums.GameMode;
import pl.wasu.enums.GameStatus;
import pl.wasu.objectsRendering.BallRenderer;
import pl.wasu.objectsRendering.PaddleRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends JPanel implements ActionListener, KeyListener{

    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Music music;
    PaddleMovement paddleMovement;
    GameRenderer gameRenderer;
    BallRenderer ballRenderer;
    PaddleRenderer paddleRenderer;
    BoardSize boardSize;
    GameMode gameModeEnum;
    GameStatus gameStatusEnum = GameStatus.STOPPED;



    public enum STATE {
        MENU,
        GAME
    }

    public STATE state = STATE.MENU;
    private int scoreLimit = 7;

    public int botDifficulty;
    public int playerWon;
    public int countRepaints = 0;

    public boolean W;
    public boolean S;
    public boolean UP;
    public boolean DOWN;
    public boolean instructions;

    public GameStatus getGameStatusEnum() {
        return gameStatusEnum;
    }

    public STATE getState() {
        return state;
    }

    public int getScoreLimit() {
        return scoreLimit;
    }

    public int getCountRepaints() {
        return countRepaints;
    }

    public static void main(String[] args) {
        new Pong().start();
    }

    public Pong() {
        Timer timer = new Timer(20, this);
        music = new Music();
        JFrame jFrame = new JFrame("Pong game");
        jFrame.setSize(700, 700);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(this);
        jFrame.addKeyListener(this);

        timer.start();
    }

    public void start() {
        boardSize = new BoardSize();
        paddleMovement = new PaddleMovement();
        gameRenderer = new GameRenderer();
        paddleRenderer = new PaddleRenderer();
        ballRenderer = new BallRenderer();
        paddle1 = new Paddle(boardSize, 1);
        paddle2 = new Paddle(boardSize, 2);
        ball = new Ball(boardSize);
    }

    public void update() {
        paddleMovement.movePaddleOne(boardSize, this);
        if (gameModeEnum == GameMode.MULTIPLAYER) {
            paddleMovement.movePaddleTwo(boardSize, this);
        } else {
            paddleMovement.moveBot(this, ball, boardSize);
        }
        ball.update(paddle1, paddle2, boardSize);
    }

    public void actionPerformed(ActionEvent e) {
        if (gameStatusEnum == GameStatus.RUNNING) {
            update();
        }
        repaint();
        countRepaints++;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        gameRenderer.render((Graphics2D) g, boardSize, this, ball, paddle1, paddle2, ballRenderer, paddleRenderer);
    }



    @Override
    public void keyPressed(KeyEvent e) {
        int idKey = e.getKeyCode();
        if (idKey == KeyEvent.VK_M) {
            music.startMusic();
        }
        handleKeysInGame(idKey);
        handleKeysInMenu(idKey);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int idKey = keyEvent.getKeyCode();
        if (idKey == KeyEvent.VK_W) {
            W = false;
        } else if (idKey == KeyEvent.VK_S) {
            S = false;
        } else if (idKey == KeyEvent.VK_UP) {
            UP = false;
        } else if (idKey == KeyEvent.VK_DOWN ) {
            DOWN = false;
        } else if (idKey == KeyEvent.VK_I) {
            instructions = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    public GameStatus finishGameIfScoreLimitExceeded(int scoreLimit) {
        if (paddle1.getScore() == scoreLimit) {
            playerWon = 1;
            gameStatusEnum = GameStatus.FINISHED;
        }
        if (paddle2.getScore() == scoreLimit) {
            playerWon = 2;
            gameStatusEnum = GameStatus.FINISHED;
        }
        return gameStatusEnum;
    }

    public void resetScoreAndBoard() {
        paddle1.setY(boardSize.getBoardHeight()/2 - paddle1.getPaddleHeight()/2);
        paddle2.setY(boardSize.getBoardHeight()/2 - paddle2.getPaddleHeight()/2);
        paddle1.setScore(0);
        paddle2.setScore(0);
        ball.start(boardSize);
    }

    private void runGameFromMenu() {
        gameStatusEnum = GameStatus.RUNNING;
        state = STATE.GAME;
    }

    private int setBotDifficultyInGame() {
        if (botDifficulty < 2) {
            botDifficulty++;
        } else if (botDifficulty == 2) {
            botDifficulty = 0;
        }
        return botDifficulty;
    }

    private void handleKeysInGame(int idKey) {
        if (state == STATE.GAME) {
            if (idKey == KeyEvent.VK_W) {
                W = true;
            } else if (idKey == KeyEvent.VK_S) {
                S = true;
            } else if (idKey == KeyEvent.VK_UP) {
                UP = true;
            } else if (idKey == KeyEvent.VK_DOWN) {
                DOWN = true;
            } else if (idKey == KeyEvent.VK_SPACE) {
                if (gameStatusEnum == GameStatus.RUNNING) {
                    gameStatusEnum = GameStatus.PAUSED;
                } else if (gameStatusEnum == GameStatus.PAUSED) {
                    gameStatusEnum = GameStatus.RUNNING;
                } else if (gameStatusEnum == GameStatus.FINISHED) {
                    resetScoreAndBoard();
                    gameStatusEnum = GameStatus.RUNNING;
                }
            } else if (idKey == KeyEvent.VK_ESCAPE && (gameStatusEnum == GameStatus.PAUSED || gameStatusEnum == GameStatus.FINISHED)) {
                gameStatusEnum = GameStatus.STOPPED;
                state = STATE.MENU;
            }
        }
    }
    private void handleKeysInMenu(int idKey) {
        if (state == STATE.MENU) {
            resetScoreAndBoard();
            gameModeEnum = GameMode.MULTIPLAYER;
            if (idKey == KeyEvent.VK_LEFT) {
                if (scoreLimit > 2) {
                    scoreLimit--;
                }
            } else if (idKey == KeyEvent.VK_RIGHT) {
                if (scoreLimit < 12) {
                    scoreLimit++;
                }
            } else if (idKey == KeyEvent.VK_SPACE) {
                runGameFromMenu();
            } else if (idKey == KeyEvent.VK_SHIFT) {
                gameModeEnum = GameMode.BOT;
                runGameFromMenu();
            } else if (idKey == KeyEvent.VK_I) {
                instructions = true;
            } else if (idKey == KeyEvent.VK_D) {
                setBotDifficultyInGame();
            }
        }
    }
}

