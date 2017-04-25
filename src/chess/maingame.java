package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class maingame extends JPanel{
	
	private static final long serialVersionUID = 1L;
	String board[][] ={{"bh","bg","bv","bq","bk","bv","bg","bh"},
			{"bp","bp","bp","bp","bp","bp","bp","bp"},
			{"","","","","","","",""},
			{"","","","","","","",""},
			{"","","","","","","",""},
			{"","","","","","","",""},
			{"wp","wp","wp","wp","wp","wp","wp","wp"},
			{"wh","wg","wv","wq","wk","wv","wg","wh"}
			};							//for all the boxes in the board
	int Selected[] = {-1,-1};			//for selected box
	int total_moves = 0;				//for counting total moves 
	int gameover = 0;
	
	//constructor
	public maingame(){
		//super("Chess..!!");
		
		//Mouse Listeners For Clicks ...!!
		addMouseListener(
				//mouse Listener class
			new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {

					int x,y;	//int to get cordinates of board
							
					x = e.getX()/75;
					y = (e.getY())/75;
					
					//if any item selected
					if(Selected[0]!=-1 && Selected[1]!=-1){
							
						//if second click empty 
						if(board[y][x].isEmpty()){
							
							//check for right step
							if(checkstep(board[Selected[1]][Selected[0]],x,y)){
								board[y][x] = board[Selected[1]][Selected[0]];	//replace
								board[Selected[1]][Selected[0]] = "";
								total_moves++;		//count total moves
							}
							
							Selected[0]=-1;
							Selected[1]=-1;
						}
						//if not empty select the clicked one
						else{
							//check for opositions..
							if(board[y][x].charAt(0) != board[Selected[1]][Selected[0]].charAt(0)){
								//check for right sted
								if(checkstep(board[Selected[1]][Selected[0]],x,y)){
									if(board[y][x].charAt(1)=='k'){		//gameover condition
										if(board[y][x].charAt(0)=='w')
											gameover = -1;			//-1 for white
										else
											gameover = 1;			//1for black
									}	
									board[y][x] = board[Selected[1]][Selected[0]];	//replace it
									board[Selected[1]][Selected[0]] = "";			//remove it	
									total_moves++;		//count total moves
								}
								
								Selected[0]=-1;
								Selected[1]=-1;
							}
							//not of oppositions
							else{
								Selected[0] = x;
								Selected[1] = y;
							}
						}
					}
					//donot select the empty ones
					else if(!board[y][x].isEmpty()){ 
						if(check_turns(board[y][x])){
							Selected[0] = x;
							Selected[1] = y;
						}
					}
					//repaint the jframe
					repaint();
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {}

				@Override
				public void mouseExited(MouseEvent arg0) {}
					
				@Override
				public void mousePressed(MouseEvent arg0) {}
					
				@Override
				public void mouseReleased(MouseEvent arg0) {}
			
			}		//mouselistener class finished			
		);		//addmouselistener finshred
	}
	
	
	//function to check the right step is taken
	boolean checkstep(String s,int x,int y){
		
		//for PAWN
		if(s.charAt(1)=='p'){
			
			
			//clicked not empty
			if(!board[y][x].isEmpty()){
						
				//check for right altenate moment to kill opponent
				if( (y==(s.charAt(0)=='w'?(Selected[1])-1:(Selected[1])+1))){
					if(x==Selected[0]-1 || x==Selected[0]+1)		//second click on alternate
						return true;								// not alternate
					else
						return false;
				}
				else
					return false;
				
			}
			else if(Selected[1]==6 && s.charAt(0)=='w'){		//first white move
				//single move
				if(x==Selected[0] && y==(Selected[1])-1)
					return true;
				//double move
				else if(x==Selected[0] && y==(Selected[1])-2){
					//in between piece
					if(board[y+1][x].isEmpty())
						return true;
					else
						return false;
				}
				else
					return false;
				
			}
			else if(Selected[1]==1 && s.charAt(0)=='b'){		//first black move
				if(x==Selected[0] && y==(Selected[1])+1)
					return true;
				else if(x==Selected[0] && y==(Selected[1])+2){
					if(board[y-1][x].isEmpty())
						return true;
					else
						return false;
				}
				else
					return false;
				
			}
			//check for front step
			else if(x==Selected[0] && y==(s.charAt(0)=='w'?(Selected[1])-1:(Selected[1])+1))
				return true;
			//otherwiae it is colrect
			else
				return false;
		}
		
		//for hathi
		else if(s.charAt(1)=='h'){
			//horizontal
			if(x==Selected[0]){
				
				int c;		//c for x cord, c for y cord
			
				//if selected y is smaller than clicked
				if(y<Selected[1])
					c=-1;
				else
					c=1;
				
				//loop to check in between bande
				for(int j=Selected[1]+c; j!=y;j+=c){
					if(!board[j][x].isEmpty())
						return false;
				}
				//all correct
				return true;
				
			}
			//vertical
			else if(y==Selected[1]){
				
				int c;		//cx for x cord, cy for y cord
			
				//if selected y is smaller than clicked
				if(x<Selected[0])
					c=-1;
				else
					c=1;
				
				//loop to check in between bande
				for(int j=Selected[0]+c; j!=x;j+=c){
					if(!board[y][j].isEmpty())
						return false;
				}
				
				//all correct
				return true;
				
			}
			else
				return false;
		}
		
		//for ghoda(horse)
		else if(s.charAt(1)=='g'){
			//2 moment on y and 1 on x
			if(y==Selected[1]+2 || y==Selected[1]-2){
				if(x==Selected[0]+1 || x==Selected[0]-1 )
					return true;
				else
					return false;
			}
			//2 moment on x and 1 on y
			else if(x==Selected[0]+2 || x==Selected[0]-2){
				if(y==Selected[1]+1 || y==Selected[1]-1 )
					return true;
				else
					return false;
			}
			//otherwise
			else
				return false;
			
		}
		
		//for vasir
		else if(s.charAt(1)=='v'){
			
			if((x-Selected[0]) == (y-Selected[1]) || (x-Selected[0]) == -1*(y-Selected[1])){
				
				int cx,cy;		//cx for x cord, cy for y cord
				
				//if selected x is smaller than clicked
				if(x<Selected[0])
					cx=-1;
				else
					cx=1;
				//if selected y is smaller than clicked
				if(y<Selected[1])
					cy=-1;
				else
					cy=1;
				
				//loop to check in between bande
				for(int i=Selected[0]+cx, j=Selected[1]+cy; i!=x;i+=cx,j+=cy){
					if(!board[j][i].isEmpty())
						return false;
				}
				
				//all correct
				return true;			
			}
			else
				return false;
			
		}
		
		//for queen(rani)
		else if(s.charAt(1)=='q'){
			
			//same as vasir
			if((x-Selected[0]) == (y-Selected[1]) || (x-Selected[0]) == -1*(y-Selected[1])){
				
				int cx,cy;		//cx for x cord, cy for y cord
				
				//if selected x is smaller than clicked
				if(x<Selected[0])
					cx=-1;
				else
					cx=1;
				//if selected y is smaller than clicked
				if(y<Selected[1])
					cy=-1;
				else
					cy=1;
				
				//loop to check in between bande
				for(int i=Selected[0]+cx, j=Selected[1]+cy; i!=x;i+=cx,j+=cy){
					if(!board[j][i].isEmpty())
						return false;
				}
				
				//all correct
				return true;			
			}
			//hathi wala horizontal
			else if(x==Selected[0]){
				
				int c;		//cx for x cord, cy for y cord
			
				//if selected y is smaller than clicked
				if(y<Selected[1])
					c=-1;
				else
					c=1;
				
				//loop to check in between bande
				for(int j=Selected[1]+c; j!=y;j+=c){
					if(!board[j][x].isEmpty())
						return false;
				}
				
				//all correct
				return true;
				
			}
			//hathi wala vertical
			else if(y==Selected[1]){
				
				int c;		//cx for x cord, cy for y cord
			
				//if selected y is smaller than clicked
				if(x<Selected[0])
					c=-1;
				else
					c=1;
				
				//loop to check in between bande
				for(int j=Selected[0]+c; j!=x;j+=c){
					if(!board[y][j].isEmpty())
						return false;
				}
				
				//all correct
				return true;
				
			}
			else
				return false;
			
		}
		
		//for king
		else if(s.charAt(1)=='k'){
			int xdisp,ydisp;
			
			xdisp = x-Selected[0];
			ydisp = y-Selected[1];
			
			if((xdisp==1 || xdisp==-1 ||xdisp==0) && (ydisp==1 || ydisp==-1 || ydisp==0))
				return true;
			else
				return false;
		}
		
		return true;
	}
	
	//check for the right banda colour selected
	public boolean check_turns(String s){
		
		//first turn for black
		if(total_moves%2==0 && s.charAt(0)=='b')
			return true;
		//second turn for white
		else if(total_moves%2==1 && s.charAt(0)=='w')
			return true;
		else
			return false;
		
	}
	
	//paint for graphics
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		if(gameover == -1){
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setColor(Color.red);
			g2d.drawString("Black Wins..!!",300, 300);
			return;
		}
		else if(gameover == 1){
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setColor(Color.red);
			g2d.drawString("White Wins..!!",300, 300);
			return;
		}
		
		//board colors
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				//paint white or black
				if((j+i)%2==0)
					g2d.setColor(Color.black);
				else
					g2d.setColor(Color.white);
				
				//selected banda
				if((Selected[0]==i && Selected[1]==j)){
					g2d.setColor(Color.gray);
				}
				
				g2d.fillRect(75*i, (75*j), 75, 75);
				
				g2d.setColor(Color.red);
				//g2d.drawString(board[j][i],(75*i)+40, (75*j)+65);
				
				Image m = null;			//m to get image
				try {
					if(!board[j][i].isEmpty())	
						m = ImageIO.read(new File(board[j][i]+".png"));		//read image
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				g2d.drawImage(m, 75*i, (75*j), 75, 75, null);		//grt image on screen
				
			}
		}
	}
	
	//reset to get default values
	public void reset(){
		board = new String[][]{{"bh","bg","bv","bq","bk","bv","bg","bh"},
					{"bp","bp","bp","bp","bp","bp","bp","bp"},
					{"","","","","","","",""},
					{"","","","","","","",""},
					{"","","","","","","",""},
					{"","","","","","","",""},
					{"wp","wp","wp","wp","wp","wp","wp","wp"},
					{"wh","wg","wv","wq","wk","wv","wg","wh"}
					};	
					
		Selected = new int[]{-1,-1};
		total_moves = 0;
		gameover = 0;
	}
	
	
	/*
	//main functuon
	public static void main(String arg[]){
		maingame mg = new maingame();
		mg.setSize(600, 628);
		mg.setVisible(true);
		mg.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mg.setResizable(false);
	}*/
}
