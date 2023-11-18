package bublebubleGame.choice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


import bublebubleGame.BubbleGame;
import bublebubleGame.component.Player;
import lombok.Getter;
import lombok.Setter;

public class ChoiceStart extends JFrame implements ActionListener{
   
   
//   시작 화면 만들기
   JLabel introImage;
   private String introFileName = "image/bublebubleStart.png";
   public JButton startBtn;
   ImageIcon startBtnImg = new ImageIcon("image/tapToStart.png");
   
   public ChoiceStart() {
	   setTitle("버블버블 게임"); 
       setSize(1000, 640);
       setLayout(null);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       introImage = new JLabel(new ImageIcon(introFileName));
       setLocationRelativeTo(null);
       startBtn = new JButton(startBtnImg);
       startBtn.setBounds(329, 512, 310, 65);
       introImage.add(startBtn);
       startBtn.addActionListener(this);
       setContentPane(introImage);
       setVisible(true);
   }
   
    
    
    private void start() {
       new ChoiceFrame();
      }
    
    public void actionPerformed(ActionEvent e) {
      if(startBtn == e.getSource()) {
         start();
         setVisible(false);
      }
   }
    
}