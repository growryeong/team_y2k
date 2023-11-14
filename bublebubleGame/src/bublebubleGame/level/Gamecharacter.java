package bublebubleGame.level;

import bublebubleGame.component.Bubble;

/* LevelManager 클래스에서 increaseLevel 메소드에
 *  mContext.getBubbleList().add((Bubble) enemyBubble);  이 줄에서
 *  (Bubble) enemyBubble 이 부분이 Cannot cast from Enemy to Bubble 라고 오류가 떠서
 *  Enemy 클래스와 Bubble 클래스를 상속 또는 인터페이스로 구현해야해서
 *  공통 인터페이스 구현
 * */
public interface Gamecharacter {
	void setState(int state);
}
