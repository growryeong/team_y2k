package bublebubleGame;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

public class UsingDoubleBuffering extends Frame implements Runnable{
	
	private Image offScreenImage;
	private Graphics offScreen;
	private Image img;
	private Thread th;
	private int speed_x, speed_y;
	private int x, y;
	private boolean usedbuffer = true;
	
	public UsingDoubleBuffering() {
		super("UsingDoubleBuffering");
		
		//initlodation();
		MediaTracker tracker = new MediaTracker(this);
		//img = Toolkit.getDefaultToolkit().get("img");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

		
}
