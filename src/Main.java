import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends Canvas{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4648172894076113183L;

	private static int [][] map;
	
	private static boolean gameRunning;
	
	private static double squareSize;
	
	private static JFrame window;
	
	public Main()
	{
		window = new JFrame("QuHacks");
		window.add(this);
		this.setPreferredSize(new Dimension(700,700));
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public static void main(String [] args)
	{
		//Init stuff
		initMap();
		squareSize = 700 / map.length;
		//build window
		Main main = new Main();
		if(main.getBufferStrategy() == null)
			main.createBufferStrategy(2);
		//Run game
		gameRunning = true;
		double limit = 1000 / 16;
		double time1=0;
		double time2=0;
		double timer=0;
		time1 = System.currentTimeMillis();
		time2 = System.currentTimeMillis();
		while(gameRunning)
		{
			timer+=(time2-time1);
			time1 = time2;
			if(timer > limit)
			{
				timer=0;
				main.repaintImage();
			}
			time2=System.currentTimeMillis();
		}
	}
	
	private void repaintImage()
	{
		BufferStrategy painter = this.getBufferStrategy();
		Graphics2D graphics = (Graphics2D)painter.getDrawGraphics();
		for(int x =0;x<map.length;x++)
		{
			for(int y=0; y<map.length;y++)
			{
				if(map[x][y] == 1)
				{
					graphics.setColor(Color.black);
				}
				else if(map[x][y] == 0)
				{
					graphics.setColor(new Color(195,103,42));
				}
				graphics.fillRect((int)(x*squareSize), (int)(y*squareSize), (int)(squareSize), (int)(squareSize));
			}
		}
		graphics.dispose();
		painter.show();
	}
	
	private static void initMap()
	{
		File mapFile = new File("resources/Map");
		try{
			Scanner mapReader = new Scanner(mapFile);
			String mapLine = mapReader.nextLine();
			map = new int[mapLine.length()][mapLine.length()];
			for(int x=0;x<map.length;x++)
			{
				for(int y=0;y<map.length;y++)
				{
					String mapPiece = mapLine.substring(y,y+1);
					if(mapPiece.equals("X"))
					{
						map[x][y] = 1;
					}
					else if(mapPiece.equals("O"))
					{
						map[x][y] = 0;
					}
					else if(mapPiece.equals("@"))
					{
						map[x][y] = 0;
					}
					else if(mapPiece.equals("!"))
					{
						map[x][y] = 0;
					}
					else
					{
						System.err.println("Unreadable character in the map file. gg noob.");
						System.exit(23);
					}
				}
				if(x!=map.length-1)
					mapLine = mapReader.nextLine();
			}
			mapReader.close();
		}catch(Exception e){e.printStackTrace(); System.err.println("The File could not be read.");}
	}
}
