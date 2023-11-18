package bublebubleGame.level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bublebubleGame.component.Bubble;
import bublebubleGame.component.Enemy;
import bublebubleGame.component.Player;
import bublebubleGame.direction.EnemyDirection;
import bublebubleGame.BubbleGame;

public class LevelManager {

	private int currentLevel; // 현재 레벨
	private int currentScore; // 현재 점수
	private int enemiesDefeated; // 적을 처치한 횟수
	
	private BubbleGame mContext;
    private List<Enemy> enemyList;
    private List<Bubble> bubbleList;
    
	public LevelManager(BubbleGame mContext) {
		this.mContext = mContext;
		this.currentLevel = 1; // 시작 레벨 초기화 1
		this.currentScore = 0; // 시작 점수 초기화 0
		this.enemiesDefeated = 0; // 처치한 적 초기화
		this.bubbleList = new ArrayList<>(); // 목록 초기화
	}

	public void increaseLevel(Player player) {
		currentLevel++; // 현재 레벨 상승
		int enemyCount = player.getSpeed() * 2; // 플레이어 속도에 따라 적군 수 생성
		 for (int i = 0; i < enemyCount; i++) {
//	            mContext.getBubbleList().addAll((Collection<? extends Bubble>) new Enemy());
			 Enemy enemyBubble = new Enemy();
			 
			 enemyBubble.setState(1); // 초기 상태를 1로 설정하여 살아있는 적 표시
			 
			 // 래밸을 올릴때 enemy object 생성하고 mContext.getBubbleList()에 추가
			 //getBubbleList() -> List<Bubble> = Enemy가 Bubble의 서브 클래스거나 공통 인터페이스 공유해야함
//			 mContext.getBubbleList().add(enemyBubble); 
			 enemyList.add(enemyBubble);
			 }
	}
	
	public boolean getAllEnemiesDefeated() {
	      if(enemyList == null) {
	         return false; // 적 리스트 null일 경우
	      }
	        for (Enemy enemy : enemyList) {
	            if (!enemy.isDead()) {
	                return false; // 적이 죽지 않으면 거짓 반환
	            }
	        }
	        return true; // 모든 적 처치
	    }
	
	// 점수 업데이트 메서드 (점수 채점 조건 충족되면 이 메서드 호출)
	public void updateScore(int points) {
		currentScore += points;
		
		//updateScoreLabel(); // 점수 라벨 업데이트
		
		// 점수 상승 확인 및 배경 맵 업데이트
		if(points > 0) {
			//updateBackgroundMap();
		}
		
		
		
	}
	
	private void updateScoreLabel() {
		
		mContext.getScoreLabel().setText("Score: " + currentScore);
		
	}
	
	// 점수가 증가하면 배경 맵 변경
	private void updateBackgroundMap() {
		int currentLevel = getCurrentLevel(); //현재 레벨
		String bgackgroundMapPath = "image/backgroundMapService3" + currentLevel + ".png";
		mContext.getFrontMap().setIcon(new ImageIcon(bgackgroundMapPath));
	}

	// 현재 점수 불러오는 메소드
	public int getCurrentScore() {
		return currentScore;
	}
	
	// 현재 레벨 반환하는 메소드
	public int getCurrentLevel() {
		return currentLevel;
	}
	// Getter
	public int getEnemiesDefeated() {
		return enemiesDefeated;
	}
	
	// 처치한 적의 수 늘리는 메소드
	public void increaseEnemiesDefeated() {
		enemiesDefeated++;
		System.out.println("Enemies defeated: " + enemiesDefeated);
	}

}
