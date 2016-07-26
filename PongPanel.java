// Klasa PongPanel!!!

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.Timer;
// w tym miejscu definiuję nową klasę, która będzie rozszerzała obiekt JPanel
public class PongPanel extends JPanel implements ActionListener, KeyListener{
	
	//Add three booleans representing three status of the game - beginning, game and GAME OVER
	//Boolean is setted up as true - we want to begin program with that screen always
	private boolean showTitleScreen = true;
	private boolean playing = false;
	private boolean gameOver = false;

	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean wPressed = false;
	private boolean sPressed = false;
	
	private int ballX = 250;
	private int ballY = 250;
	private int diameter = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	private int playerOneX = 25;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 50;
	//Adding Player TWO (second Paddle)
	private int playerTwoX = 465;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 50;

	private int paddleSpeed = 5;

	private int playerOneScore = 0;
	private int playerTwoScore = 0;

	// constructing a PongPanel
	public PongPanel() {
		setBackground(Color.BLACK);

		//setting listening to key presses
		setFocusable(true);
		addKeyListener(this);

		// callint a step() with 60 fps
		Timer timer = new Timer(1000/60, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		step();
	}

	public void step() {

		if(playing) {
				
			// move player One
			if(upPressed) {
				if (playerOneY-paddleSpeed > 0) {
					playerOneY -= paddleSpeed;
				}
			}
			if(downPressed) {
				if (playerOneY + paddleSpeed + playerOneHeight < getHeight()) {
					playerOneY += paddleSpeed;
				}
			}
			//move player two
			if(wPressed) {
				if (playerTwoY-paddleSpeed > 0) {
					playerTwoY -= paddleSpeed;
				}
			}
			if(sPressed) {
				if (playerTwoY + paddleSpeed + playerTwoHeight < getHeight()) {
					playerTwoY += paddleSpeed;
				}
			}
		}
		// I will define here, where the ball will be after it moves!
		int nextBallLeft = ballX + ballDeltaX;
		int nextBallRight = ballX + diameter + ballDeltaX;
		int nextBallTop = ballY + ballDeltaY;
		int nextBallBottom = ballY + diameter + ballDeltaY;

		int playerOneRight = playerOneX + playerOneWidth;
		int playerOneTop = playerOneY;
		int playerOneBottom = playerOneY + playerOneHeight;

		int playerTwoLeft = playerTwoX;
		int playerTwoTop = playerTwoY;
		int playerTwoBottom = playerTwoY + playerTwoHeight;


		// what if ball bounce off top and bottom of screen??
		if (nextBallTop < 0 || nextBallBottom > getHeight()) {
			ballDeltaY *= -1;
		}

		//When the ball go off the left side!
		if (nextBallLeft < playerOneRight) {
			// is it going to miss the paddle?
			if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

				playerTwoScore++;

				if (playerTwoScore == 3) {
					playing = false;
					gameOver = true;
				}

				ballX = 250;
				ballY = 250;
			}
			else {
				ballDeltaX *= -1;
			}
		}
		//Will the ball go off the righr side?
		if (nextBallRight > playerTwoLeft) {
			//is it going to miss the paddle?
			if(nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

				playerOneScore++;

				if (playerTwoScore == 3) {
					playing = false;
					gameOver = true;
				}

				ballX = 250;
				ballY = 250;
			}
			else {
				ballDeltaX *= -1;
			}
		}

		//moving the ball
		ballX += ballDeltaX;
		ballY += ballDeltaY;

		//when the ball has moved, we need to tell JPanel to repaint itself
		repaint();
	}
	// paint the game screen

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setColor(Color.white);

		if (showTitleScreen) {

			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString("Pong", 165, 100);

			g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
			g.drawString("Press 'P' to play", 175, 400);
		}
		else if (playing) {

			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			//Draw dashed line down center

			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			//Draw goal line on each side
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

			//Draw the SCORES for each player
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 400, 400);

			// Draw the BALL!
			g.fillOval(ballX, ballY, diameter, diameter);
			// Player One Paddle
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			//Print second PADDLE
			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
		}
		else if (gameOver) {
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 400, 100);

			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.drawString("Player One Wins!", 165, 200);
			} else {
				g.drawString("Player Two Wins!", 165, 200);
			}

			g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
			g.drawString("Press space to restart.", 150, 400);


		}
		 Toolkit.getDefaultToolkit().sync();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {

		if (showTitleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_P) {
				showTitleScreen = false;
				playing = true;
			}
		}
		else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		}
		else if (gameOver) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				gameOver = false;
				showTitleScreen = true;
				playerOneY = 250;
				playerTwoY = 250;
				ballX = 250;
				ballY = 250;
				playerOneScore = 0;
				playerTwoScore = 0;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (playing) {

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = false;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
			else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = false;
			}
			else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = false;
			}
		}
	}
}