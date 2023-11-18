package bublebubleGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
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
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import bublebubleGame.choice.ChoiceFrame;
import bublebubleGame.choice.ChoiceStart;
import bublebubleGame.component.Bubble;
import bublebubleGame.component.Enemy;
import bublebubleGame.component.Player;
import bublebubleGame.level.LevelManager;
import bublebubleGame.music.BGM;
import bublebubleGame.service.BackgroundPlayerService;
import lombok.Getter;
import lombok.Setter;


/**
 * @author 겟인데어 FrontMap과 BackMap 테스트
 **/

@Getter
@Setter
public class BubbleGame extends JFrame implements ComponentListener, Runnable {

   private BubbleGame mContext = this;
   private JLabel frontMap;

   private Player player;
   
   // Enemy를 List로 관리
   private List<Enemy> enemy;
   
   private Bubble bubble;

   private List<Bubble> bubbleList;
   
   private BufferedImage image;

   // 종료 변수   
   protected boolean stop = false;

   public int cnt;
   public int num;
   public int temp;
//   시작 화면 만들기
//   JLabel introImage;
//   private String introFileName = "image/bublebubleStart.png";
//   public JButton startBtn;
//   ImageIcon startBtnImg = new ImageIcon("image/tapToStart.png");
   

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
      setVisible(true); 
      // 레벨 매니저에 BubbleGame 인스턴스 전달
      levelManager = new LevelManager(this); 
   }
//   private void beforeStarting() {
//      setTitle("버블버블 게임");
//      setSize(1000, 640);
//      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      introImage = new JLabel(new ImageIcon(introFileName));
//      setLayout(null);
//      setLocationRelativeTo(null);
//      startBtn = new JButton(startBtnImg);
//      startBtn.setBounds(340, 482, 335, 39);
////      startBtn.setBorderPainted(false);
//      introImage.add(startBtn);
//      startBtn.addActionListener(this);
//      setContentPane(introImage);
//      setVisible(true);
//   }
   

   
   public void initBufferd() {
        dim = getSize();
        //System.out.println(dim.getSize());
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
      bufferGraphics.drawImage(bufmap, 6, 30, null);
            
      // 캐릭터 그리기
      BufferedImage bufC = componenttoImage(player);
      bufferGraphics.drawImage(bufC, player.getX()+6, player.getY()+30, 50, 50, null);
            
      // Enemy 더블 버퍼링 미구현
      //BufferedImage bufE = componenttoImage(enemy);
      //bufferGraphics.drawImage(bufE, enemy.getX()+6, enemy.getY()+30, 50, 50, null);
         
      g.drawImage(offscreen, 0, 0, this);
      
   }
   
   @Override
   public void update(Graphics g) {
      paint(g);
   }
   
   // portal
   private ImageIcon portal;
   
   private void initObject() {
      bubbleList = new ArrayList<>();
      enemy = new ArrayList<Enemy>();
      player = new Player(mContext);

      // nextLevel값에 따라 backgorundMap(1~3)변경
      if(nextLevel==1) {
    	  frontMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
      }
      if(nextLevel==2) {
    	  frontMap = new JLabel(new ImageIcon("image/backgroundMap2.png"));
      }
      if(nextLevel==3) {
    	  frontMap = new JLabel(new ImageIcon("image/backgroundMap3.png"));
      }
   }

   private void initSetting() {
     setTitle("버블버블 게임");
     setSize(1000, 640);
     setLayout(null);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setContentPane(frontMap);
     add(player);
     
     // nextLevel에 따라 Enemy의 수 변경
     if(nextLevel==1) {
    	 enemy.add(new Enemy());
    	 for(Enemy e : enemy) add(e);
     }else if(nextLevel==2) {
    	 enemy.add(new Enemy());
    	 enemy.add(new Enemy());
    	 for(Enemy e : enemy) add(e);
     }else if(nextLevel==3) {
    	 enemy.add(new Enemy());
    	 enemy.add(new Enemy());
    	 enemy.add(new Enemy());
    	 for(Enemy e : enemy) add(e);
     }
     
     // 포탈 이미지
     JLabel portal1 = new JLabel();
     portal = new ImageIcon("image/portal.png");
     portal1.setIcon(portal);
     portal1.setBounds(870, 510, 50, 65);
     add(portal1);
     
     cnt=0;
     
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

   static int nextLevel=1;
   
   public static int getNextLevel() {
		return nextLevel;
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
               Bubble bubble = new Bubble(mContext, player);
               bubbleList.add(bubble);
               add(bubble);
               break;
            case KeyEvent.VK_DOWN:
            	// 포탈을 타는 버튼
            	BackgroundPlayerService bgs = new BackgroundPlayerService(mContext, player);
            	
            	image = bgs.getImage();
            	Color centerColor = new Color(image.getRGB(player.getX() + 25, player.getY() + 25));
            	
            	// bubbledClear가 실행될 때 enemykill의 값이 1씩 오르도록 설정함
            	int enemykill = Bubble.getEnemykill();
            	
            	// BackgroundPlayerService이미지 오른쪽 아래 검정색 공간을 추가
            	// 플레이어의 위치가 검정색 위에 있고 적을 죽이면 오르는 nextLevel값이 조건을 충족하면 창을 다시 실행
            	// nextLevel의 값(0, 1, 2)에 따라 백그라운드 맵과 서비스맵이 변경 되도록 설정함
            	if (centerColor.getRed() == 0 && centerColor.getBlue() == 0 && centerColor.getGreen() == 0 
            			&& nextLevel==1 && enemykill ==1) {
            		nextLevel++;
					setVisible(false);
					check=false;;
					new BubbleGame();
				}else if(centerColor.getRed() == 0 && centerColor.getBlue() == 0 && centerColor.getGreen() == 0 
            			&& nextLevel==2 && enemykill ==3) {
					nextLevel++;
					setVisible(false);
					check=false;;
					new BubbleGame();
				}
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
      JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
   }
   
//   종료 함수
   public void stop() {
      stop = true;
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
   
   @Override
   public void run() {
	   while(true) {
		   try {

				new Thread(() -> {

					
				}).start();
				
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
		}
		   
   }
		  
   
   // 플레이어가 적을 죽였는지 확인하는 코드 구현
   private void playerKilledEnemies() {
    System.out.println("playerKilledEnemies 호출");
    
    if(cnt == 1) {
       stop();
         endGame();

    }else if(cnt == 3) {
       stop();
         endGame();

    }else if(cnt == 6) {
       stop();
         endGame();
    }
   }
   
   static boolean check;
   
   public static void main(String[] args) {
	  
	  Gameover over = new Gameover();
	  over.start();
	  new BGM();
	  
      new ChoiceStart();
   
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

}