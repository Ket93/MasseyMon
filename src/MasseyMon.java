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
    public MasseyMon() throws IOException {
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
	private boolean menu;
	private int direction;
	public boolean ready = true;
	private Image back;
	private boolean[] keys;
	Menu myMenu;
	Player myGuy;
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10;
	public GamePanel() throws IOException {
		menu = false;
		keys = new boolean[KeyEvent.KEY_LAST+1];
		myGuy = new Player(0);
		myMenu = new Menu();
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
		if (menu) {
			Menu.display(g);
		}
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
    		if (e.getKeyCode() == KeyEvent.VK_M && keys[e.getKeyCode()] == false){
    			menu = !menu;
			}
    		if (e.getKeyCode() == KeyEvent.VK_DOWN && keys[e.getKeyCode()] == false && menu){
    			Menu.setPosY(40);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && keys[e.getKeyCode()] == false && menu){
				Menu.setPosY(-40);
			}


			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Menu.getPosY() == 266){
				menu = false;
			}
	    	keys[e.getKeyCode()] = true;
		}
		public void keyReleased(KeyEvent e){
			keys[e.getKeyCode()] =  false;
		}
    }
    public void move() {
		if (!menu) {
			if (keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) {
				direction = UP;
				myGuy.move(direction);
			} else if (keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) {
				direction = DOWN;
				myGuy.move(direction);
			} else if (keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) {
				direction = RIGHT;
				myGuy.move(direction);
			} else if (keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) {
				direction = LEFT;
				myGuy.move(direction);
			} else {
				myGuy.resetExtra();
				myGuy.idle(direction);
			}
		}
	}
	public boolean getMenu(){return menu;}
}
