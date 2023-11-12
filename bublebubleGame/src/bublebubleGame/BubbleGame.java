package bublebubleGame;

import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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
public class BubbleGame extends JFrame {

	private BubbleGame mContext = this;
	private JLabel frontMap;

	private Player player;
	private Enemy enemy;

	private List<Bubble> bubbleList;

	private LevelManager levelManager; // 레벨
	// 나중에 업데이트 가능하도록 레이블을 인스턴스 변수로 설정
	private JLabel scoreLabel; // 점수
	private JLabel levelJLabel; // 레벨

	public BubbleGame() {
		initObject();
		initSetting();
		initListener();
		initThread();
		setVisible(true);
		levelManager = new LevelManager(this); // 레벨 매니저에 BubbleGame 인스턴스 전달
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
	 * 점수는 1점 오르고 while문이 종료되었지만 백그라운드 화면이 하얀색으로 날라감
	 */
	private void initThread() {
		
		 boolean[] isRunning = {true}; // 배열을 사용하여 
		
		new Thread(() -> {
			enemy.start();
//			gameLoop();
			
//			boolean isRunning = true;
			
			while (isRunning[0]) {
	            // 플레이어가 적을 처치했는지 확인하고 점수 업데이트
	            boolean enemiesKilled = playerKilledEnemies();
	            if (enemiesKilled) {
	                SwingUtilities.invokeLater(() -> {
	                    levelManager.updateScore(1);
	                    System.out.println("Player killed an enemy! Current Score: " + levelManager.getCurrentScore());

	                    // 레벨 업 조건 충족 여부 확인
	                    boolean levelUp = shouldLevelUP();
	                    if (levelUp) {
	                        levelManager.increaseLevel(player);
	                        System.out.println("Level up! Current Level: " + levelManager.getCurrentLevel());
	                    }

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

	// 레벨 업 조건 충족 여부 확인하는 코드
	public boolean shouldLevelUP() {

		// levelManager가 null인지 확인
		if (levelManager != null) {
			// 레벨 조건
			int scoreThreshold = 1000;
			int enemiesThreshold = 10;

			// 현재 점수와 죽인 적 확인
			int currentScore = levelManager.getCurrentScore();
			int enemiesDefeated = levelManager.getEnemiesDefeated();

			// 플레이어 레벨 업 조건 확인
			if (currentScore >= scoreThreshold && enemiesDefeated >= enemiesThreshold) {
                levelManager.increaseLevel(player); // 레벨 상승
                levelManager.updateScore(-scoreThreshold); // 점수 차감
                return true;
            }
		}
		return false;
	}

	// 플레이어가 적을 죽였는지 확인하는 코드 구현
	private boolean playerKilledEnemies() {
		boolean enemiesKilled = false; // 기본값
		
		// bubbleList를 반복하고 각 버블의 상태 확인 , 버블의 상태 == 1이면 levelManager.increaseEnemiesDefeated() 호츌
		for (Bubble bubble : bubbleList) {
			if (bubble.getState() == 1) { // 버블 안에 있는 상태 1
				// 플레이어가 적 공격하는데 성공
				levelManager.increaseEnemiesDefeated(); // 처치한 적 수 증가
				bubble.setState(0); // 상태를 0으로 재설정
				enemiesKilled = true; // 적을 한 명 이상 처치하면 true
				System.out.println("Enemy killed! State: " + bubble.getState()); // 메서드가 호출되고 있는지 상태가 업데이트 되는지 확인
			}
		}
		return enemiesKilled;
	}

	public static void main(String[] args) {
		new BubbleGame();
	
	}

	// 게임 진행 로직의 일부로 게임 루프 중 또는 게임에서 특정 이벤트가 발생할 때 호출
	// 게임 루프의 각 반복마다 shouldLevelUP을 검사하여 플레이어가 다음 레벨로 진행해야 하는지 여부 결정
	// 적을 죽였을때도 똑같이 playerKilledEnemies가 호출
	private void gameLoop() {
		shouldLevelUP(); // 레벨 업 조건 확인
		playerKilledEnemies(); // 죽인 적 
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

	public JLabel getLevelJLabel() {
		return levelJLabel;
	}

	public void setLevelJLabel(JLabel levelJLabel) {
		this.levelJLabel = levelJLabel;
	}

}