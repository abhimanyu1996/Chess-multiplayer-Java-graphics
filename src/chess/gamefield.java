package chess;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class gamefield extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public gamefield(){
		getContentPane().setLayout(
				new BoxLayout(getContentPane(),BoxLayout.X_AXIS)		//set box Layout
				);
		
		//add main Game
		maingame m = new maingame();
		m.setPreferredSize(new Dimension(600,600));
		m.setMinimumSize(new Dimension(600,600));
		m.setMaximumSize(new Dimension(600,600));
		add(m);
		
		//reset button
		Button reset = new Button("Reset");
		reset.addActionListener(
			new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					m.reset();
					m.repaint();
				}
			}//ActionListener end
		);
		add(reset);
		
	}
	
	public static void main(String arg[]){
		
		gamefield gf = new gamefield();
		gf.setSize(700,628);
		gf.setVisible(true);
		gf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gf.setResizable(false);
	}
	
}
