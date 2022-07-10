package ACMWorkshop;

import java.awt.Color;
import java.awt.Graphics;

public class Map {
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public Map(int row, int col)
	{
		map = new int[row][col];
		
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[0].length; j++)
			{
				map[i][j] = 1;
			}
		}
		
		brickWidth = 540 / col;
		brickHeight = 100 / row;
		
	}
	
	public void draw(Graphics g, Color colorName)
	{
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[0].length; j++)
			{
				if(map[i][j] > 0)
				{
					g.setColor(colorName);
					g.fillRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
					
					g.setColor(Color.BLACK);
					g.drawRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}		
	}
	
	public void setBrickValue(int value, int row, int col)
	{
		map[row][col] = value;
	}
}
