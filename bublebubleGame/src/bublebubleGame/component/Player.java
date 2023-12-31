package bublebubleGame.component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bublebubleGame.BubbleGame;
import bublebubleGame.Moveable;
import bublebubleGame.choice.ChoiceFrame;
import bublebubleGame.direction.PlayerDirection;
import bublebubleGame.service.BackgroundPlayerService;
import bublebubleGame.service.BackgroundPlayerService2;
import bublebubleGame.service.BackgroundPlayerService3;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends JLabel implements Moveable {

   private int x;
   private int y;
   
   private BubbleGame mContext;
   private ChoiceFrame choiceFrame;

   private boolean left;
   private boolean right;
   private boolean up;
   private boolean down;
   private PlayerDirection playerDirection;

   private boolean leftCrash;
   private boolean rightCrash;

   private static final int SPEED = 4;
   private static final int JUMPSPEED = 2; 

   private ImageIcon playerR;
   private ImageIcon playerL;

   public Player(BubbleGame mContext) {
      this.mContext = mContext;
      initObject();
      initSetting();
      initBackgroundPlayerService();
   }
   
   private void initObject() {
		int character=ChoiceFrame.getCharacter(); //ChoiceFrame의 character값 가져오기
		//System.out.println(character);
		
		// character의 값에 따라 캐릭터 이미지 변경
		if(character == 1) {
			playerR = new ImageIcon("image/playerR.png");
			playerL = new ImageIcon("image/playerL.png");
		}else if(character == 2) {
			playerR = new ImageIcon("image/Bub2Right.png");
			playerL = new ImageIcon("image/Bub2Left.png");
		}else if(character == 3) {
			playerR = new ImageIcon("image/Bub3Right.png");
			playerL = new ImageIcon("image/Bub3Left.png");
		}
	}

   
   private void initSetting() {
      x = 55;
      y = 535;

      left = false;
      right = false;
      up = false;
      down = false;
      
      playerDirection = PlayerDirection.RIGHT;

      leftCrash = false;
      rightCrash = false;

      setIcon(playerR);
      setSize(50, 50);
      setLocation(x, y);
   }
   
   private void initBackgroundPlayerService() {
	   int nextLevel=BubbleGame.getNextLevel();
	   // BackgroundPlayerService1, 2, 3을 만들어 nextLevel의 값에 따라 변경
	   if(nextLevel == 1) {
		   new Thread(new BackgroundPlayerService(mContext, this)).start();
	   }else if(nextLevel == 2) {
		   new Thread(new BackgroundPlayerService2(mContext, this)).start();
	   }else if(nextLevel == 3) {
		   new Thread(new BackgroundPlayerService3(mContext, this)).start();
	   }
   }
 

   @Override
   public void up() {
      up = true;
      new Thread(() -> {

         for (int i = 0; i < 130/JUMPSPEED; i++) {
            y = y - (JUMPSPEED);
            setLocation(x, y);
            try {
               Thread.sleep(5);
            } catch (Exception e) {
               System.out.println("위쪽 이동중 인터럽트 발생 : " + e.getMessage());
            }
         }

         up = false;
         down();
      }).start();

   }

   @Override
   public void down() {
      //System.out.println("하강중");
      down = true;
      new Thread(() -> {
         while (down) {
            y = y + (JUMPSPEED);
            setLocation(x, y);
            try {
               Thread.sleep(3);
            } catch (Exception e) {
               System.out.println("아래쪽 이동중 인터럽트 발생 : " + e.getMessage());
            }
         }

      }).start();
   }

   @Override
   public void left() {
      setIcon(playerL);
      playerDirection = PlayerDirection.LEFT;
      left = leftCrash ? false : true;
      

      new Thread(() -> {
         while (left) {
            x = x - SPEED;
            setLocation(x, y);

            try {
               Thread.sleep(10);
            } catch (Exception e) {
               System.out.println("왼쪽 이동중 인터럽트 발생 : " + e.getMessage());
            }
         }
      }).start();

   }

   @Override
   public void right() {
      setIcon(playerR);
      playerDirection = PlayerDirection.RIGHT;
      right = rightCrash ? false : true;
      

      new Thread(() -> {
         while (right) {
            x = x + SPEED;
            setLocation(x, y);

            try {
               Thread.sleep(10);
            } catch (Exception e) {
               System.out.println("오른쪽 이동중 인터럽트 발생 : " + e.getMessage());
            }
         }
      }).start();

   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public BubbleGame getmContext() {
      return mContext;
   }

   public boolean isLeft() {
      return left;
   }

   public boolean isRight() {
      return right;
   }

   public boolean isUp() {
      return up;
   }

   public boolean isDown() {
      return down;
   }

   public PlayerDirection getPlayerDirection() {
      return playerDirection;
   }

   public boolean isLeftCrash() {
      return leftCrash;
   }

   public boolean isRightCrash() {
      return rightCrash;
   }

   public static int getSpeed() {
      return SPEED;
   }

   public static int getJumpspeed() {
      return JUMPSPEED;
   }

   public ImageIcon getPlayerR() {
      return playerR;
   }

   public ImageIcon getPlayerL() {
      return playerL;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public void setmContext(BubbleGame mContext) {
      this.mContext = mContext;
   }

   public void setLeft(boolean left) {
      this.left = left;
   }

   public void setRight(boolean right) {
      this.right = right;
   }

   public void setUp(boolean up) {
      this.up = up;
   }

   public void setDown(boolean down) {
      this.down = down;
   }

   public void setPlayerDirection(PlayerDirection playerDirection) {
      this.playerDirection = playerDirection;
   }

   public void setLeftCrash(boolean leftCrash) {
      this.leftCrash = leftCrash;
   }

   public void setRightCrash(boolean rightCrash) {
      this.rightCrash = rightCrash;
   }

   public void setPlayerR(ImageIcon playerR) {
      this.playerR = playerR;
   }

   public void setPlayerL(ImageIcon playerL) {
      this.playerL = playerL;
   }

}
