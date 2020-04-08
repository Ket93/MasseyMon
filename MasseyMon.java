import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*;
import java.util.*;//imports

public class MasseyMon extends JFrame {
	GamePanel game;
	javax.swing.Timer myTimer;
    public MasseyMon(){
		super("MasseyMon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer = new javax.swing.Timer(10,new TickListener());
		setSize(956,795);
		game = new GamePanel();
		add(game);
		setResizable(false);
		setVisible(true);
		start();
    }
    public static void main(String[] arguments) throws IOException{
		MasseyMon frame = new MasseyMon();		
    }
    public void start(){
    	myTimer.start();
    }
    class TickListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(game!= null){
            	game.move();
                game.repaint();
            }
        }
    }
}
class GamePanel extends JPanel{
	public boolean ready = true;
	private Image back;
	private int mx,my;
	private boolean[] keys;
	Player myGuy;
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10;
	public GamePanel(){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		myGuy = new Player(0);
        try {
    		back = ImageIO.read(new File("Images/Towns/palletTown.png"));
		} 
		catch (IOException e) {}
		setSize(956,795);
		addMouseListener(new clickListener());
		addKeyListener(new moveListener());
	}
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    public void paintComponent(Graphics g){    	
    	g.drawImage(back,0,0,this);
    	myGuy.draw(g);
    }
    class clickListener implements MouseListener{
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
    }
    class moveListener implements KeyListener{
    	public void keyTyped(KeyEvent e) {
    	}
	    public void keyPressed(KeyEvent e) {
	    	keys[e.getKeyCode()] = true;
		}
		public void keyReleased(KeyEvent e){
			keys[e.getKeyCode()] =  false;
		}
    }
    public void move(){
    	if (keys[KeyEvent.VK_UP]){
    		myGuy.move(UP);
    	}
    	else if(keys[KeyEvent.VK_DOWN]){
    		myGuy.move(DOWN);
    	}
    	else if(keys[KeyEvent.VK_RIGHT]){
    		myGuy.move(RIGHT);
    	}
    	else if(keys[KeyEvent.VK_LEFT]){
    		myGuy.move(LEFT);
    	}
    	else{
    		myGuy.resetExtra();
    	}
    }
}
