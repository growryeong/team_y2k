package bublebubleGame;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import bublebubleGame.level.LevelManager;
import bublebubleGame.choice.ChoiceFrame;
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
public class BubbleGame extends JFrame implements ComponentListener {

	private BubbleGame mContext = this;
	private JLabel frontMap;

	private Player player;
	private Enemy enemy;

	private List<Bubble> bubbleList;

	private LevelManager levelManager; // 레벨
	// 나중에 업데이트 가능하도록 레이블을 인스턴스 변수로 설정
	private JLabel scoreLabel; // 점수
	private JLabel levelJLabel; // 레벨
	
	//private Image buffImage;
	//private Graphics buffg;
	private Graphics bufferGraphics;
	private Image offscreen;
	private Dimension dim;
	
	public BubbleGame() {
		addComponentListener(this);
		initObject();
		initSetting();
		initListener();
		initThread();
		setVisible(true);
		
		levelManager = new LevelManager(this); // 레벨 매니저에 BubbleGame 인스턴스 전달
	}
	
	public void initBufferd() {
        dim = getSize();
        System.out.println(dim.getSize());
        //화면의 크기를 가져온다.
        setBackground(Color.white);
        //배경 색깔을 흰색으로 변경한다. 
        offscreen = createImage(dim.width,dim.height);
        //화면 크기와 똑같은 가상 버퍼(이미지)를 생성한다.
        bufferGraphics = offscreen.getGraphics(); 
        //가상버퍼(이미지)로 부터 그래픽스 객체를 얻어옴
   }
	
	@Override
	public void paint(Graphics g) {
		bufferGraphics.clearRect(0, 0, dim.width, dim.height);
		// 배경이미지 그리기
		BufferedImage bufmap = componenttoImage(frontMap);
		bufferGraphics.drawImage(bufmap, 0, 0, null);
				
		// 캐릭터 그리기
		BufferedImage bufC = componenttoImage(player);
		bufferGraphics.drawImage(bufC, player.getX(), player.getY(), 50, 50, null);
				
		// 캐릭터 그리기
		BufferedImage bufE = componenttoImage(enemy);
		bufferGraphics.drawImage(bufE, enemy.getX(), enemy.getY(), 50, 50, null);
			
		g.drawImage(offscreen, 0, 0, this);
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	private void initObject() {
		bubbleList = new ArrayList<>();
		enemy = new Enemy();
		player = new Player(mContext);

//		frontMap = new JLabel(new ImageIcon("image/backgroundMap3.png"));

		frontMap = new JLabel(new ImageIcon("image/backgroundMap.png"));

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

		// 현재 점수 및 레벨을 표시하는 레이블 추가
		JLabel scoreLabel = new JLabel("score : 0");
		JLabel levelLabel = new JLabel("level : 1");

		// 레이블 위치 설정
		scoreLabel.setBounds(10, 10, 100, 20);
		levelLabel.setBounds(10, 30, 100, 20);

		// 레이블 프레임에 추가
		add(scoreLabel);
		add(levelLabel);

		this.scoreLabel = scoreLabel;
		this.setLevelJLabel(levelLabel);
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

	/*
	 * 오류 내용
	 * 기존 오류는 점수가 1초에 1점씩 올라가 while문을 종료 시키는 부분 추가
	 * while문이 종료되었지만 백그라운드 화면이 하얀색으로 사라짐
	 * 그리고 Enemy가 갇힌 버블이 빠른 속도로 위로 올라감
	 * 
	 * -------------------------------------------
	 * 
	 * LevelManager에 updateScore 메소드에 updateBackgroundMap() 호출 부분과 updateBackgroundMap 메소드 주석처리 하니까
	 * 배경 안사라짐
	 *
	 *---------------------------------------------------
	 * 모든 적 처치 시 게임 종료 되는 부분 추가 후 게임 종료 시 현재 점수, 레벨 보여주기
	 */
	private void initThread() {
		
		 boolean[] isRunning = {true}; // 배열을 사용하여 
		
		new Thread(() -> {
			enemy.start();

			
			while (isRunning[0]) {
	            // 플레이어가 적을 처치했는지 확인하고 점수 업데이트
	            boolean enemiesKilled = playerKilledEnemies();
	            if (enemiesKilled) {
	                SwingUtilities.invokeLater(() -> {
	                    levelManager.updateScore(1);
	                    System.out.println("Player killed an enemy! Current Score: " + levelManager.getCurrentScore());

	                
	                    isRunning[0] = false; // 루프 종료
	                });
	            }
	            	
	            // 과도한 루프 실행 방지를 위한 시간 지연
	            try {
	                Thread.sleep(1000); // 필요에 따라 시간 수정
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
			}
		}).start();
	}

	// 플레이어가 적을 죽였는지 확인하는 코드 구현
	private boolean playerKilledEnemies() {
		boolean enemiesKilled = false; // 기본값
		
		// bubbleList를 반복하고 각 버블의 상태 확인 , 버블의 상태 == 1이면 levelManager.increaseEnemiesDefeated() 호츌
		for (Bubble bubble : bubbleList) {
			if (bubble.getState() == 1) { // 버블 안에 있는 상태 1
				// 플레이어가 적 공격하는데 성공
				levelManager.increaseEnemiesDefeated(); // 처치한 적 수 증가
				bubble.setState(1); // 상태를 0으로 재설정
				enemiesKilled = true; // 적을 한 명 이상 처치하면 true
				System.out.println("Enemy killed! State: " + bubble.getState()); // 메서드가 호출되고 있는지 상태가 업데이트 되는지 확인
			}
		}
		return enemiesKilled;
	}

	public static void main(String[] args) {
		new ChoiceFrame();
	
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

	public LevelManager getLevelManager() {
		// Bubble 클래스에서 getLevelManager 사용하기 위해서
		return levelManager;
	}

	public JLabel getScoreLabel() {
		// Getter 메소드 추가
		return scoreLabel;
	}

	public void setLevelJLabel(JLabel levelJLabel) {
		this.levelJLabel = levelJLabel;
	}
	
	// JLabel을 Buffered Image로 변환
	public BufferedImage componenttoImage(Component component) {
		BufferedImage img = new BufferedImage(component.getWidth(), component.getHeight(),  BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics g = img.getGraphics();
		g.setColor(component.getBackground());
		g.setFont(component.getFont());
		component.paintAll(g);
		Rectangle region = new Rectangle(0, 0, img.getWidth(), img.getHeight());
		return img.getSubimage(region.x, region.y, region.width, region.height);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		initBufferd();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// 더블 버퍼링
	/*
	@Override
	protected void paintComponent(Graphics g) {
		if(buffg == null) {
			buffImage = createImage(this.getWidth(), this.getHeight());
			if(buffImage == null) {
				System.out.println("오프 스크린 생성 실패");
			} else {
				buffg = buffImage.getGraphics();
			}
		} 
		
		super.paintComponents(buffg);
		
		// 배경이미지 그리기
		BufferedImage bufmap = componenttoImage(frontMap);
		buffg.drawImage(bufmap, 0, 0, null);
		
		// 캐릭터 그리기
		BufferedImage bufC = componenttoImage(player);
		buffg.drawImage(bufC, player.getX(), player.getY(), 50, 50, null);
		
		// 캐릭터 그리기
		BufferedImage bufE = componenttoImage(enemy);
		buffg.drawImage(bufE, enemy.getX(), enemy.getY(), 50, 50, null);
	
		g.drawImage(buffImage, 0, 0, this);
	}
	*/

}
