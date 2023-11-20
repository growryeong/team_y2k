package bublebubleGame.service;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bublebubleGame.BubbleGame;
import bublebubleGame.component.Bubble;
import bublebubleGame.component.Player;
import bublebubleGame.level.LevelManager;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class BackgroundPlayerService implements Runnable {
	private BufferedImage image;
	private Player player;
	private List<Bubble> bubbleList;
	private BubbleGame mContext;
	private LevelManager levelManager;
	
	static int nextLevel;

	public BackgroundPlayerService(BubbleGame mContext, Player player) {
		this.mContext = mContext;
		this.player = player;
		this.bubbleList = mContext.getBubbleList();
		this.setmContext(mContext);
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("맵1");
		while (true) {
			try {

				new Thread(() -> {
					for (int i = 0; i < bubbleList.size(); i++) {
						if (bubbleList.get(i).getState() == 1) {
							if (Math.abs(player.getX() - bubbleList.get(i).getX()) < 10
									&& Math.abs(player.getY() - bubbleList.get(i).getY()) < 50) {
								Bubble bubble = bubbleList.get(i);
								bubble.bubbledCount();
								bubble.bubbledClear();		
							}
						}

					}
				}).start();

				Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
				Color rightColor = new Color(image.getRGB(player.getX() + 50 + 10, player.getY() + 25));
				Color centerColor = new Color(image.getRGB(player.getX() + 25, player.getY() + 25));

				int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5)
						+ image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5);

				if (bottomColor != -2) {
					player.setDown(false);
				} else if (!player.isUp() && !player.isDown()) {
					player.down();
				}

				if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
					player.setLeft(false);
					player.setLeftCrash(true);
				} else if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
					player.setRight(false);
					player.setRightCrash(true);
				} else {
					player.setLeftCrash(false);
					player.setRightCrash(false);
				}
				
				LevelManager levelManager = new LevelManager(mContext);
				int score = levelManager.getCurrentScore();
				if (leftColor.getRed() == 0 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0 && score==1) {
					mContext.setVisible(false);
				}

				Thread.sleep(10);
				

			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
		}

	}
	
	public BubbleGame getmContext() {
		return mContext;
	}

	public void setmContext(BubbleGame mContext) {
		this.mContext = mContext;
	}

	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return image;
	}

}