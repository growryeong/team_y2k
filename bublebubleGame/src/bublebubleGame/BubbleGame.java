package bublebubleGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bublebubleGame.level.LevelManager;
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
public class BubbleGame extends JFrame implements ActionListener{

//	시작 화면 만들기
	JLabel introImage;
	private String introFileName = "image/bublebubleStart.png";
	public JButton startBtn;
	ImageIcon startBtnImg = new ImageIcon("image/tapToStart.png");
	
	
	private BubbleGame mContext = this;
	private JLabel frontMap;

	private Player player;
	private Enemy enemy;

	private List<Bubble> bubbleList;
	

	private LevelManager levelManager; // 레벨
//	게임 종료 변수
	protected boolean stop;
	
//	public BubbleGame() {
//		stop = false;
////		인트로 이미지
//		introImage = new JLabel(new ImageIcon(introFileName));
//		startBtn = new JButton("게임 시작");
//		startBtn.setFont(new Font("D2Coging", Font.BOLD, 15));
//		startBtn.setBackground(Color.GRAY);
//	
////		스타트 버튼 위치
//		startBtn.setBounds(200, 430, 100, 40);
//		startBtn.setBorderPainted(false);
//		introImage.add(startBtn);
//		start();
//
//
//	}
//	
////	게임 시작
//	public void start() {
////		initData();
////		initLayout();
//		initSetting();
////		스타트 버튼 누르면 아래 함수들 모두 실행
//		initObject();
//		initListener();
//		initThread();
//		setVisible(true);
//	}
//	
////	게임 종료
//	public void stop() {
//		stop = true;
//	}
	
	
//-----------------------------------------------------	

//	public BubbleGame() {
////		initStartLayout();
//		initObject();
//		initSetting();
//		initListener();
//		initThread();
//		setVisible(true);
//	}
//	
////	private void initStartLayout() {
//////		인트로 이미지
////		introImage = new JLabel(new ImageIcon(introFileName));
////		startBtn = new JButton("게임 시작");
////		startBtn.setFont(new Font("D2Coging", Font.BOLD, 15));
////		startBtn.setBackground(Color.GRAY);
////	
//////		스타트 버튼 위치
////		startBtn.setBounds(200, 430, 100, 40);
////		startBtn.setBorderPainted(false);
////		introImage.add(startBtn);
////		setContentPane(introImg);
////	}
//
//	private void initObject() {
////		introImage = new JLabel(new ImageIcon(introFileName));
////		startBtn = new JButton("게임 시작");
////		startBtn.setFont(new Font("D2Coging", Font.BOLD, 15));
////		startBtn.setBackground(Color.GRAY);
//		
//		bubbleList = new ArrayList<>();
//		enemy = new Enemy();
//		player = new Player(mContext);
//
//		frontMap = new JLabel(new ImageIcon("image/backgroundMap3.png"));
//		levelManager = new LevelManager(); // 레벨 객체
//		new BGM();
//	}
//
//	private void initSetting() {
//		setTitle("버블버블 게임");
//		setSize(1000, 640);
//		setLayout(null);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		
////		startBtn.setBounds(200, 430, 100, 40);
////		startBtn.setBorderPainted(false);
////		introImage.add(startBtn);
////		setContentPane(introImage);
//		
//		setLocationRelativeTo(null);
//		setContentPane(frontMap);
//		add(player);
//		add(enemy);
//	}
	
	
	
//-----------------------------------------------------
	public BubbleGame() {
		beforeStarting();
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	private void beforeStarting() {
		setTitle("버블버블 게임");
		setSize(1000, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		introImage = new JLabel(new ImageIcon(introFileName));
		setLayout(null);
		setLocationRelativeTo(null);
		startBtn = new JButton(startBtnImg);
		startBtn.setBounds(340, 482, 335, 39);
//		startBtn.setBorderPainted(false);
		introImage.add(startBtn);
		startBtn.addActionListener(this);
		setContentPane(introImage);
		setVisible(true);
	}
	
	private void start() {
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
		frontMap = new JLabel(new ImageIcon("image/backgroundMap3.png"));
		levelManager = new LevelManager(); // 레벨 객체
		new BGM();
		
	}

	private void initSetting() {
//		setTitle("버블버블 게임");
//		setSize(1000, 640);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		introImage = new JLabel(new ImageIcon(introFileName));
//		setLayout(null);
//		setLocationRelativeTo(null);
//		startBtn = new JButton(startBtnImg);
//		startBtn.setBounds(340, 482, 335, 39);
////		startBtn.setBorderPainted(false);
//		introImage.add(startBtn);
//		startBtn.addActionListener(this);
//		setContentPane(introImage);
//		setVisible(true);
		
//		setTitle("버블버블 게임");
//		setSize(1000, 640);
//		setLayout(null);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		setLocationRelativeTo(null);
		setContentPane(frontMap);
		add(player);
		add(enemy);
	}
	
	
	
//-----------------------------------------------------
//	public BubbleGame() {
//		initObject();
//		initSetting();
//		initListener();
//		initThread();
//		setVisible(true);
//	}
//
//	private void initObject() {
//		bubbleList = new ArrayList<>();
//		enemy = new Enemy();
//		player = new Player(mContext);
//
//		frontMap = new JLabel(new ImageIcon("image/backgroundMap3.png"));
//		levelManager = new LevelManager(); // 레벨 객체
//		new BGM();
//	}
//
//	private void initSetting() {
//		setTitle("버블버블 게임");
//		setSize(1000, 640);
//		setLayout(null);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocationRelativeTo(null);
//		setContentPane(frontMap);
//		add(player);
//		add(enemy);
//	}
	
	
	private void initListener() {
//		System.out.println("키이벤트리스너 호출");
//		startBtn.addActionListener(this);
		
		addKeyListener(new KeyAdapter() {

//			키보드 클릭 이벤트 핸들러
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
	
	public void actionPerformed(ActionEvent e) {
		JButton clickedBtn = (JButton)e.getSource();
		if(startBtn == e.getSource()) {
			start();
		}
	}
//	public void actionPerformed(ActionEvent e) {
//		
//	}

	private void initThread() {
		new Thread(()->{
			enemy.start();
//			levelManager.start();
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