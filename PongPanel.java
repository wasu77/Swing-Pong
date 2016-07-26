// Klasa PongPanel!!!

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;
// w tym miejscu definiuję nową klasę, która będzie rozszerzała obiekt JPanel
public class PongPanel extends JPanel implements ActionListener, KeyListener{
	
	private boolean upPressed = false;
	private boolean downPressed = false;
	
	private int ballX = 250;
	private int ballY = 250;
	private int diameter = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	private int playerOneX = 25;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 50;

	private int paddleSpeed = 5;

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
		// I will define here, where the ball will be after it moves!
		int nextBallLeft = ballX + ballDeltaX;
		int nextBallRight = ballX + diameter + ballDeltaX;
		int nextBallTop = ballY + ballDeltaY;
		int nextBallBottom = ballY + diameter + ballDeltaY;

		int playerOneRight = playerOneX + playerOneWidth;
		int playerOneTop = playerOneY;
		int playerOneBottom = playerOneY + playerOneHeight;

		// what if ball bounce off top and bottom of screen??
		if (nextBallTop < 0 || nextBallBottom > getHeight()) {
			ballDeltaY *= -1;
		}

		//When the ball go off the left side!
		if (nextBallLeft < playerOneRight) {
			// is it going to miss the paddle?
			if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

				System.out.println("PLAYER TWO SCORED");

				ballX = 250;
				ballY = 250;
			}
			else {
				ballDeltaX *= -1;
			}
		}
		//Will the ball go off the righr side?
		if (nextBallRight > getWidth()) {
			ballDeltaX *= -1;
		}
		//moving the ball
		ballX += ballDeltaX;
		ballY += ballDeltaY;

		//when the ball has moved, we need to tell JPanel to repaint itself
		repaint();
	}
	// paint a ball AND THE PADDLE

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setColor(Color.white);

		g.fillOval(ballX, ballY, diameter, diameter);

		g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
	}
}