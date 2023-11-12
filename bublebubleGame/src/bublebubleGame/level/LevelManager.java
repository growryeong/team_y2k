package bublebubleGame.level;

import java.util.Collection;

import bublebubleGame.component.Bubble;
import bublebubleGame.component.Enemy;
import bublebubleGame.component.Player;
import bublebubleGame.direction.EnemyDirection;

public class LevelManager {

    private int currentLevel;

    public LevelManager() {
        this.currentLevel = 1; // 시작 레벨 1
    }

    public void increaseLevel(Player player) {
        currentLevel++;
        int enemyCount = player.getSpeed() * 2; // 게임에 따라
        for (int i = 0; i < enemyCount; i++) {
            player.getmContext().getBubbleList().addAll((Collection<? extends Bubble>) new Enemy());
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    // 백그라운드 배경과 적
//	public void start() {
//		initBackgroundEnemyService();
//		setState(0);
//		setEnemyDirection(EnemyDirection.RIGHT);
//		left();
//	}
}
