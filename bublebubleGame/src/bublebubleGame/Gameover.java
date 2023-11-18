package bublebubleGame;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import bublebubleGame.component.Bubble;
import bublebubleGame.component.Enemy;
import bublebubleGame.component.Player;
import bublebubleGame.level.LevelManager;

public class Gameover extends Thread {
	
	private LevelManager levelManager;
	private BubbleGame mContext;
	private Player player;
	private Enemy enemy;
	
	public int cnt;
	public int temp;
	public int num;
	
	private int score;
	
	protected boolean stop = false;
	
	public Gameover() {
		levelManager = new LevelManager(mContext); 
	}
	
	void whileGameover() {
		System.out.println("게임오버 쓰레드 실행중");
	}
	void ifInterrupted() {
		System.out.println("게임오버 interrupted");
	}
	void ifGameoverGone() {
		System.out.println("게임오버 쓰레드 종료");
	}
	
	// 게임 종료 정보 표시
	   private void endGame() {
	      System.out.println("Game Over!");
	      System.out.println("Final Score: " + levelManager.getCurrentScore());
	      System.out.println("Final Level: " + levelManager.getCurrentLevel());
	      SwingUtilities.invokeLater(()->{
	      System.out.println("Game Over! Final Score: " + levelManager.getCurrentScore());
	      showFinalScoreDialog();
	      });
	   }
	   
	//   최종 점수 보여주기
	   public void showFinalScoreDialog() {
		   
	      int fiinalScore = levelManager.getCurrentScore();
	      String message = "Game Over!\nFinal Score: "+fiinalScore;
	      JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
	   }
	
	@Override
    public void run() {
        while(true){
            try{
                Thread.sleep(200);
                //whileGameover();
                boolean check=Bubble.getCheck();
			    
			    if(check) {
			    	levelManager.updateScore(1);
			        endGame();
			        break;
			    }
            } catch (InterruptedException ie){
                ifInterrupted();
                ie.getMessage();
                break;
            }
        }
        ifGameoverGone();
    }
}


