package bublebubleGame.component;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bublebubleGame.BubbleGame;
import bublebubleGame.choice.ChoiceFrame;
import bublebubleGame.direction.PlayerDirection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Bubble extends JLabel {
   
//   적의 갯수 카운트
   public int enemyCount = 0;
   
   private BufferedImage image;
   
   private int x;
   private int y;

   private boolean up;
   private boolean left;
   private boolean right;
   
   private int state; // 0(물방울),1(적을 가둔 물방울)

   private ImageIcon bubble;
   private ImageIcon bubbled;
   private ImageIcon bomb;

   private BubbleGame mContext;
   private List<Enemy> enemy;
   private Player player;
   
   static int character;

   public Bubble(BubbleGame mContext, Player player) {
      this.mContext = mContext;
      this.player = player;
      this.enemy = mContext.getEnemy();
      try {
         image = ImageIO.read(new File("image/backgroundMapService.png"));
      } catch (Exception e) {
         e.printStackTrace();
      }

      initObject();
      initSetting();
      initThread();
   }

   private void initObject() {
	  int character=ChoiceFrame.getCharacter();
	  if(character == 1) {
		  bubble = new ImageIcon("image/bubble.png");
		  bubbled = new ImageIcon("image/bubbled.png");
		  bomb = new ImageIcon("image/bomb.png");
	  }
	  if(character == 2) {
		  bubble = new ImageIcon("image/bubble2.png");
		  bubbled = new ImageIcon("image/bubbled2.png");
		  bomb = new ImageIcon("image/bomb.png");
	  }
	  if(character == 3) {
		  bubble = new ImageIcon("image/bubble3.png");
		  bubbled = new ImageIcon("image/bubbled3.png");
		  bomb = new ImageIcon("image/bomb.png");
	  }
   }

   private void initSetting() {
      up = false;
      setLeft(false);
      setRight(false);
      
      check=false;

      this.x = player.getX();
      this.y = player.getY();

      setIcon(bubble);
      setSize(50, 50);

      state = 0;
   }

   private void initThread() {
      new Thread(() -> {
         if (player.getPlayerDirection() == PlayerDirection.LEFT) {
            shootLeft();
         } else {
            shootRight();
         }

      }).start();
   }

   public void shootLeft() {  
      left = true;
		Stop:for(int i=0; i<400; i++) {
			x--;
			setLocation(x, y);
			
			Color leftColor = new Color(image.getRGB(x + 10, y + 25));
          if (leftColor.getRed() == 255 && leftColor.getBlue() == 0 && leftColor.getGreen() == 0) {
             break;
          }
			
			// 아군과 적군의 거리가 10
			for (Enemy e : enemy) {
				if (Math.abs(x - e.getX()) < 10 && Math.abs(y - e.getY()) > 0 && Math.abs(y - e.getY()) < 50) {
					if (e.getState() == 0) {
						attack(e);
						break Stop;
					}
				}
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		shootUp();
   }

   public void shootRight() {
	  
	   	right = true;
		Stop:for(int i=0; i<400; i++) {
			x++;
			setLocation(x, y);
			
			Color rightColor = new Color(image.getRGB(x + 50 + 10, y + 25));
            if (rightColor.getRed() == 255 && rightColor.getBlue() == 0 && rightColor.getGreen() == 0) {
               break;
            }
			
			// 아군과 적군의 거리가 10
			for (Enemy e : enemy) {
				if (Math.abs(x - e.getX()) < 10 && Math.abs(y - e.getY()) > 0 && Math.abs(y - e.getY()) < 50) {
					if (e.getState() == 0) {
						attack(e);
						break Stop;
					}
				}
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		shootUp();
   }

   public void shootUp() {
      up = true;
      while (up) {

         try {
            setLocation(x, y);
            y--;
            Color topColor = new Color(image.getRGB(x + 25, y - 10));
            if (state == 1) {
               if (topColor.getRGB() != -1) {
                  setUp(false);
               }
               Thread.sleep(20);
            } else {
               if (topColor.getRed() == 255 && topColor.getBlue() == 0 && topColor.getGreen() == 0) {
                  setUp(false);
                  bubbleClear();
               }
               Thread.sleep(1);
            }
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }

   public void attack(Enemy e) {
//      setEnemyCount();
      state = 1;
      e.setState(1);
      setIcon(bubbled);
      mContext.remove(e);
      mContext.repaint();
   }
   
   static boolean check;
   
   public static boolean getCheck() {
		return check;
	}
   
   private List<Bubble> bubbleList;
   
   public void bubbleClear() {
	      try {
	         setUp(false);
	         setIcon(bomb);
	         
	         /*
	         // 레벨에 따라 맵에 버블을 날리수 있는 개수가 다르게 설정
        	 // 스페이스바를 연타하게 되면 오류가 좀 있음
	         bubbleList = mContext.getBubbleList();
	         bubbleList.remove(this);
	         */
	         
	         //setEnemyCount();
	         Thread.sleep(1000);
	         mContext.remove(this);
	         mContext.repaint();
	      } catch (InterruptedException e) {
	         e.printStackTrace();
	      }
	   }
   
   // 맵, 서비스맵 변경과 아래키이벤트 조건을 충족하기 위한 변수
   static int nextLevel;
   
   // 아래키이벤트에서 조건을 충족하기 위한 변수
   static int enemykill=0;  
   public static int getEnemykill() {
		return enemykill;
	}
   
   public void bubbledClear() {
      try {
         setUp(false);
         setIcon(bomb);
      // 한번만 실행하기 위해 check가 true인지 false인지 조건을 줌
         
         Thread.sleep(1000);
         mContext.remove(this);
         mContext.repaint();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
   public void bubbledCount() {
	   if(!check) {
		   enemykill++;
		   check=true;
		   System.out.println(enemykill);
	   }
   }
   
//   죽인 적 카운트
   public int setEnemyCount() {
      System.out.println("setEnemyCnt 호출");
      ++enemyCount;
      System.out.println("enemyCount " +enemyCount);
      return enemyCount;
   }
//   죽인 적 카운트한 거 가져오기
   public int getEnemyCount() {
      System.out.println("getenemyCount " +enemyCount);
      return enemyCount;
   }
   
   public boolean isLeft() {
      return left;
   }

   public void setLeft(boolean left) {
      this.left = left;
   }

   public boolean isRight() {
      return right;
   }

   public void setRight(boolean right) {
      this.right = right;
   }

   public int getState() {
      return state;
   }

   public void setState(int state) {
      this.state = state;
   }

   public boolean isUp() {
      return up;
   }

   public void setUp(boolean up) {
      this.up = up;
   }
}
