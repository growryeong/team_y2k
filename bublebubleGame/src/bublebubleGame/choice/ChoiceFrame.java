package bublebubleGame.choice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import bublebubleGame.BubbleGame;
import bublebubleGame.component.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChoiceFrame extends JFrame {
	
	static int character;
	
	private Player player;
	
	private ImageIcon character1;
	private ImageIcon character2;
	private ImageIcon character3;
	private ImageIcon choose;
	
	private JButton oneBtn;
	private JButton twoBtn;
	private JButton threeBtn;
	
	ImageIcon oneBtnImg = new ImageIcon("image/1.png");
	ImageIcon twoBtnImg = new ImageIcon("image/2.png");
	ImageIcon threeBtnImg = new ImageIcon("image/3.png");
	
	private JLabel choiceMap;
	
	public static int getCharacter() {
		return character;
	}
	
	public ChoiceFrame() {
		
		super("캐릭터 선택창"); //타이틀
    	JPanel jPanel = new JPanel();
    	setSize(1000, 640); //창 크기 설정
    	
    	choiceMap = new JLabel(new ImageIcon("image/choiceback.png"));
    	
    	setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    	setContentPane(choiceMap);
    	
    	//ChoiceFrame에 캐릭터 이미지 추가
    	JLabel label1 = new JLabel();
    	JLabel label2 = new JLabel();
    	JLabel label3 = new JLabel();
    	JLabel label4 = new JLabel();
    	
    	character1 = new ImageIcon("image/choice1.png");
    	character2 = new ImageIcon("image/choice2.png");
    	character3 = new ImageIcon("image/choice3.png");
    	choose = new ImageIcon("image/choose.png");
    	
    	label1.setIcon(character1);
		label2.setIcon(character2);
		label3.setIcon(character3);
		label4.setIcon(choose);
		
		label1.setBounds(140, 250, 250, 250);
		getContentPane().add(label1);
		
		label2.setBounds(440, 250, 250, 250);
		getContentPane().add(label2);
		
		label3.setBounds(740, 250, 250, 250);
		getContentPane().add(label3);
		
		label4.setBounds(200, 10, 600, 230);
		getContentPane().add(label4);
    	
		oneBtn = new JButton(oneBtnImg);
		twoBtn = new JButton(twoBtnImg);
		threeBtn = new JButton(threeBtnImg);
		
	    oneBtn.setBounds(150, 200, 100, 100);
	    twoBtn.setBounds(445, 200, 100, 100);
	    threeBtn.setBounds(745, 200, 100, 100);
	    
	    choiceMap.add(oneBtn);
	    choiceMap.add(twoBtn);
	    choiceMap.add(threeBtn);
		
    	add(jPanel);
    	
    	Dimension frameSize = getSize();

    	Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
    	setLocation((windowSize.width - frameSize.width) / 2,
    			(windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	setVisible(true);
    	
    	//1번 캐릭터 버큰 클릭 시 발동 이벤트
    	oneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character=1;
                new BubbleGame();
                setVisible(false); // 창 안보이게 하기 
            }
        });
    	
    	//2번 캐릭터 버큰 클릭 시 발동 이벤트
    	twoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	character=2;
                new BubbleGame();
                setVisible(false); // 창 안보이게 하기 
            }
        });
    	
    	//3번 캐릭터 버큰 클릭 시 발동 이벤트
    	threeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	character=3;
                new BubbleGame();
                setVisible(false); // 창 안보이게 하기 
            }
        });
	}
}
