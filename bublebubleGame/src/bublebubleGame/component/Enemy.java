package bublebubleGame.component;

import javax.swing.ImageIcon;

import javax.swing.JLabel;

import bublebubleGame.BubbleGame;
import bublebubleGame.Moveable;
import bublebubleGame.direction.EnemyDirection;
import bublebubleGame.service.BackgroundEnemyService;
import bublebubleGame.service.BackgroundEnemyService2;
import bublebubleGame.service.BackgroundEnemyService3;
import bublebubleGame.service.BackgroundPlayerService;
import bublebubleGame.service.BackgroundPlayerService2;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy extends JLabel implements Moveable {
   private int x;
   private int y;

   private boolean left;
   private boolean right;
   private boolean up;
   private boolean down;
   private boolean isDead;
   
   private int state; // 0(살아있는상태),1(물방울에같힌상태)

   private EnemyDirection enemyDirection;

   private boolean leftCrash;
   private boolean rightCrash;

   private static final int SPEED = 3;
   private static final int JUMPSPEED = 1;

   private ImageIcon enemyR;
   private ImageIcon enemyL;

   public Enemy() {
      initObject();
      initSetting();
      setState(0);
      setEnemyDirection(EnemyDirection.RIGHT);
      left();
      initBackgroundEnemyService();
   }

   private void initObject() {
      enemyR = new ImageIcon("image/enemyR.png");
      enemyL = new ImageIcon("image/enemyL.png");
   }

   private void initSetting() {
	  // Enemy생성시 위치 랜덤 설정
      x = (int) (Math.random() * 899) + 100;
      y = 178;

      left = false;
      right = false;
      setUp(false);
      down = false;

      setLeftCrash(false);
      setRightCrash(false);

      setIcon(enemyR);
      setSize(50, 50);
      setLocation(x, y);
   }

   private void initBackgroundEnemyService() {
      int nextLevel=BubbleGame.getNextLevel();
      // BackgroundEnemyService를 1, 2, 3 3개를 만들어서 nextLevel값에 따라 변경
	   if(nextLevel == 1) {
		   new Thread(new BackgroundEnemyService(this)).start();
	   }else if(nextLevel == 2) {
		   new Thread(new BackgroundEnemyService2(this)).start();
	   }else if(nextLevel == 3) {
		   new Thread(new BackgroundEnemyService3(this)).start();
	   }
   }

   @Override
   public void up() {
      setUp(true);
      Thread t = new Thread(() -> {

         for (int i = 0; i < 130 / JUMPSPEED; i++) {
            y = y - (JUMPSPEED);
            setLocation(x, y);
            try {
               Thread.sleep(5);
            } catch (Exception e) {
               System.out.println("위쪽 이동중 인터럽트 발생 : " + e.getMessage());
            }
         }

         setUp(false);
         down();
      });

      t.start();

   }

   @Override
   public void down() {
      down = true;
      Thread t = new Thread(() -> {
         while (down) {
            y = y + (JUMPSPEED);
            setLocation(x, y);
            try {
               Thread.sleep(3);
            } catch (Exception e) {
               System.out.println("아래쪽 이동중 인터럽트 발생 : " + e.getMessage());
            }
         }

      });

      t.start();

   }

   @Override
   public void left() {
      setEnemyDirection(EnemyDirection.LEFT);
      setIcon(enemyL);
      left = true;

      Thread t = new Thread(() -> {
         while (left) {
            x = x - SPEED;
            setLocation(x, y);

            try {
               Thread.sleep(10);
            } catch (Exception e) {
               System.out.println("왼쪽 이동중 인터럽트 발생 : " + e.getMessage());
            }
         }

      });

      t.start();

   }

   @Override
   public void right() {
      setEnemyDirection(EnemyDirection.RIGHT);
      setIcon(enemyR);

      right = true;

      Thread t = new Thread(() -> {
         while (right) {
            x = x + SPEED;
            setLocation(x, y);

            try {
               Thread.sleep(10);
            } catch (Exception e) {
               System.out.println("오른쪽 이동중 인터럽트 발생 : " + e.getMessage());
            }
         }

      });

      t.start();

   }

   public boolean isUp() {
      return up;
   }

   public void setUp(boolean up) {
      this.up = up;
   }

   public int getState() {
      return state;
   }

   public void setState(int state) {
      this.state = state;
   }

   public EnemyDirection getEnemyDirection() {
      return enemyDirection;
   }

   public void setEnemyDirection(EnemyDirection enemyDirection) {
      this.enemyDirection = enemyDirection;
   }

   public boolean isLeftCrash() {
      return leftCrash;
   }

   public void setLeftCrash(boolean leftCrash) {
      this.leftCrash = leftCrash;
   }

   public boolean isRightCrash() {
      return rightCrash;
   }

   public void setRightCrash(boolean rightCrash) {
      this.rightCrash = rightCrash;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public boolean isLeft() {
      return left;
   }

   public boolean isRight() {
      return right;
   }

   public boolean isDown() {
      return down;
   }

   public static int getSpeed() {
      return SPEED;
   }

   public static int getJumpspeed() {
      return JUMPSPEED;
   }

   public ImageIcon getEnemyR() {
      return enemyR;
   }

   public ImageIcon getEnemyL() {
      return enemyL;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public void setLeft(boolean left) {
      this.left = left;
   }

   public void setRight(boolean right) {
      this.right = right;
   }

   public void setDown(boolean down) {
      this.down = down;
   }

   public void setEnemyR(ImageIcon enemyR) {
      this.enemyR = enemyR;
   }

   public void setEnemyL(ImageIcon enemyL) {
      this.enemyL = enemyL;
   }

   public boolean isDead() {
      return isDead;
   }
}