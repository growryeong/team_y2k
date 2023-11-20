package bublebubleGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bublebubleGame.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 게임 종료 시 이미지 보여줌
public class ShowGameOver extends JLabel{
	
	// 의존성 콤포지션
	private BubbleGame mContext;
	
	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean down;

	private ImageIcon gameOver;



	public ShowGameOver(BubbleGame mContext){
		this.mContext = mContext;
		initObject();
		initSetting();
	}
	
	private void initObject() {
		gameOver = new ImageIcon("image/gameovershow.png");
	}
	
	private void initSetting() {
		down = false;
		
		x = 150;
		y = 150;
		
		setIcon(gameOver);
		setSize(700,394);
		setLocation(50,50);
		
	}


}