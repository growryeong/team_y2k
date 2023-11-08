package bubble.test.ex02;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel{
	private int x;
	private int y;
	
	private ImageIcon playerR, playerL;
	
	public Player() {
		initObject();
		initSetting();
	}
	
	private void initObject() {
		playerR = new ImageIcon("Image/PlayerR.png");
		playerL = new ImageIcon("Image/PlayerL.png");
	}
	
	private void initSetting() {
		x = 55;
		y = 535;
		
		// player 자체가 Label이니까
		setIcon(playerR);
		setSize(50,50);
		setLocation(x,y);
	}
}
