package bublebubleGame.choice;

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
	
	public static int getCharacter() {
		return character;
	}
	
	public ChoiceFrame() {
		
		super("캐릭터 선택창"); //타이틀
    	JPanel jPanel = new JPanel();
    	JButton btn1 = new JButton("1번 캐릭터");
    	JButton btn2 = new JButton("2번 캐릭터");
    	setSize(300, 200); //창 크기 설정
    	jPanel.add(btn1);
    	jPanel.add(btn2);
    	
    	
    	//ChoiceFrame에 캐릭터 이미지 추가
    	JLabel label1 = new JLabel();
    	JLabel label2 = new JLabel();
    	
    	character1 = new ImageIcon("image/playerR.png");
    	character2 = new ImageIcon("image/Bub2Right.png");
    	
    	label1.setIcon(character1);
		label2.setIcon(character2);
		
		label1.setBounds(65, 50, 50, 50);
		getContentPane().add(label1);
		
		label2.setBounds(160, 50, 50, 50);
		getContentPane().add(label2);
    			
    	add(jPanel);
    	

    	Dimension frameSize = getSize();

    	Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
    	setLocation((windowSize.width - frameSize.width) / 2,
    			(windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	setVisible(true);
    	
    	//1번 캐릭터 버큰 클릭 시 발동 이벤트
    	btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character=1;
                new BubbleGame();
                setVisible(false); // 창 안보이게 하기 
            }
        });
    	
    	//2번 캐릭터 버큰 클릭 시 발동 이벤트
    	btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	character=2;
                new BubbleGame();
                setVisible(false); // 창 안보이게 하기 
            }
        });
	}
}
