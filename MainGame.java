import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;

import javax.swing.JPanel;

public class MainGame extends JPanel implements KeyListener, ActionListener
{
	Timer timer;
	int delay = 9;
	
	boolean play = false;
	int score = 0;
	
	private int playerX = 300;
	private int ballPosX = 290;
	private int ballPosY = 350;
	private int ballDirX = getRandomNumberForX();
	private int ballDirY = getRandomNumberForY();
	
	int totalBricks = 40;
	
	Map mapPlay;
	
	public MainGame()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
		mapPlay = new Map(8, 5);
	}

	public void paint(Graphics g)
	{
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 700, 600);
		
		//border
		g.setColor(Color.RED);
		g.fillRect(0, 0, 3, 600);
		g.fillRect(0, 0, 700, 3);
		g.fillRect(683, 0, 3, 600);
		
		//score
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD,22));
		g.drawString("Score : "+score+" / 200", 500, 30 );
		
		//map
		mapPlay.draw((Graphics) g, Color.WHITE);
		
		//paddle
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 8);
		
		if(play == false)
		{
			//game start instructions
			g.setColor(Color.YELLOW);
			g.drawString("Press Enter/Left/Right to Start the game", 150 , 350 );
		}
		else
		{
			g.setColor(Color.green);
			g.fillOval(ballPosX, ballPosY, 20, 20);
		}
		
		if(totalBricks <= 0)
		{
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			

			//background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 700, 600);
			
			//border
			g.setColor(Color.RED);
			g.fillRect(0, 0, 3, 600);
			g.fillRect(0, 0, 700, 3);
			g.fillRect(683, 0, 3, 600);
			
			//Game Win
			g.setColor(Color.YELLOW);
			g.setFont(new Font("serif", Font.BOLD,22));
			g.drawString("You have Won", 200, 300 );
			
			g.setColor(Color.YELLOW);
			g.setFont(new Font("serif", Font.BOLD,22));			
		}
		
		if(ballPosY > 600)
		{
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			
			//background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 700, 600);
			
			//border
			g.setColor(Color.RED);
			g.fillRect(0, 0, 3, 600);
			g.fillRect(0, 0, 700, 3);
			g.fillRect(683, 0, 3, 600);
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD,22));
			g.drawString("Game Over! Score : "+score, 200, 300 );
			
			g.setColor(Color.YELLOW);
			g.setFont(new Font("serif", Font.BOLD,22));
			g.drawString("Press Enter to Restart", 200, 330 );
		}
		g.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// TODO Auto-generated method stub
		
		if(ke.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(playerX < 0)
				playerX = 0;
			else
				moveLeft();
		}
		
		if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(playerX >= 600)
				playerX = 600;
			else
				moveRight();			
		}
		
		if(ke.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(play == false)
			{
			play = true;
		    score = 0;
			
			playerX = 300;
			ballPosX = 290;
			ballPosY = 350;
			ballDirX = getRandomNumberForX();
			ballDirY = getRandomNumberForY();
			totalBricks = 40;
			mapPlay = new Map(8, 5);
			
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(play == true)
		{
			
			Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
			
			if(ballRect.intersects(new Rectangle(playerX, 550, 100, 8)))
			{
				ballDirY = -ballDirY;
			}
			
			for(int i = 0; i < mapPlay.map.length; i++)
			{
				for(int j = 0; j < mapPlay.map[0].length; j++)
				{
					if(mapPlay.map[i][j] > 0)
					{
						int brickX = j*mapPlay.brickWidth + 80;
						int brickY = i*mapPlay.brickHeight + 50;
						int brickWidth = mapPlay.brickWidth;
						int brickHeight = mapPlay.brickHeight;
						
						Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						
						if(ballRect.intersects(brickRect))
						{
							score+=5;
							totalBricks--;
							mapPlay.setBrickValue(0, i, j);
							
							ballDirY = -ballDirY;
						}
					}
				}
			}
						
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			
			if(ballPosX < 0)//for left wall
				ballDirX = -ballDirX;
			if(ballPosY < 0)//for top wall
				ballDirY = -ballDirY;
			if(ballPosX > 670)//for right wall
				ballDirX = -ballDirX;
		}
		repaint();
		
	}

	public int getRandomNumberForX()
	{
		Random random = new Random();
		int randomNumber = 0;
		do
		{
			int max = 2;
			int min = -2;
			randomNumber = min + random.nextInt(max - min + 1);
		}while(randomNumber == 0);
		return randomNumber;
			
	}
	
	public int getRandomNumberForY()
	{
		Random random = new Random();
		int randomNumber = 0;
		do
		{
			int max = -1;
			int min = -4;
			randomNumber = min + random.nextInt(max - min + 1);
		}while(randomNumber == 0);
		return randomNumber;
			
	}
	
	public void moveRight()
	{
		play = true;
		playerX += 20 ;
	}
	
	public void moveLeft()
	{
		play = true;
		playerX -= 20;
	}
}
