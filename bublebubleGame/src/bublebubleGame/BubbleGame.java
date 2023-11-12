package bublebubleGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubblebubbleGame.level.LevelManager;
import bublebubleGame.component.Bubble;
import bublebubleGame.component.Enemy;
import bublebubleGame.component.Player;
import bublebubleGame.music.BGM;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 겟인데어 FrontMap과 BackMap 테스트
 **/

@Getter
@Setter
public class BubbleGame extends JFrame {

	private BubbleGame mContext = this;
	private JLabel frontMap;

	private Player player;
	private Enemy enemy;

	private List<Bubble> bubbleList;

	private LevelManager levelManager; // 레벨

	public BubbleGame() {
		initObject();
		initSetting();
		initListener();
		initThread();
		setVisible(true);
	}

	private void initObject() {
		bubbleList = new ArrayList<>();
		enemy = new Enemy();
		player = new Player(mContext);
		frontMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		levelManager = new LevelManager(); // 레벨 객체
		new BGM();
	}

	private void initSetting() {
		setSize(1000, 640);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(frontMap);
		add(player);
		add(enemy);
	}

	private void initListener() {
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (!player.isLeft())
						player.left();
					break;
				case KeyEvent.VK_RIGHT:
					if (!player.isRight())
						player.right();
					break;
				case KeyEvent.VK_UP:
					if (!player.isUp() && !player.isDown())
						player.up();
					break;
				case KeyEvent.VK_SPACE: 
					Bubble bubble = new Bubble(mContext, enemy, player);
					bubbleList.add(bubble);
					add(bubble);
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					player.setRight(false);
					break;
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				}
			}
		});
	}

	private void initThread() {
		new Thread(()->{
			enemy.start();
			levelManager.start(); // 레벨 시작
		}).start();
	}
	
	// 레벨에 따라 다른 배경 맵
//	private void updateBackgroundMap() {
//		int currentLevel = levelManager.getCurrentLevel(); //현재 레벨
//		String bgackgroundMapPath = "레벨에 따른 이미지" + currentLevel + ".png";
//		frontMap.setIcon(new ImageIcon(backgroundPath)); // 레벨 이미지 
//	}
	

	public static void main(String[] args) {
		new BubbleGame();
	}

	public BubbleGame getmContext() {
		return mContext;
	}

	public JLabel getFrontMap() {
		return frontMap;
	}

	public Player getPlayer() {
		return player;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public List<Bubble> getBubbleList() {
		return bubbleList;
	}

	public void setmContext(BubbleGame mContext) {
		this.mContext = mContext;
	}

	public void setFrontMap(JLabel frontMap) {
		this.frontMap = frontMap;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public void setBubbleList(List<Bubble> bubbleList) {
		this.bubbleList = bubbleList;
	}
}