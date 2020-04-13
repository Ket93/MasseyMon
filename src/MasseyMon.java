import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

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
	private boolean lab;
	private boolean pokemon;
	private boolean bag;
	private boolean menu;
	private int direction;
	public boolean ready = true;
	private Image back;
	private BufferedImage mask;
	private boolean[] keys;
	Houses myHouse;
	Textbox myTextBox;
	PokemonMenu myPokeMenu;
	Items myItem;
	Menu myMenu;
	Player myGuy;
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10;
	public GamePanel() throws IOException {
		lab = false;
		pokemon = false;
		bag = false;
		menu = false;
		keys = new boolean[KeyEvent.KEY_LAST+1];
		myGuy = new Player(0);
		myPokeMenu = new PokemonMenu();
		myMenu = new Menu();
		myItem = new Items();
		myTextBox = new Textbox();
		myHouse = new Houses();
        try {
    		back = ImageIO.read(new File("Images/Towns/palletTown.png"));
    		mask = ImageIO.read(new File("Images/Towns/palletTownMask.png"));
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
		if (!lab) {
			g.drawImage(back, 0, 0, this);
		}
		if (menu) {
			Menu.display(g);
			if (bag){
				Items.display(g);
			}
			if (pokemon){
				PokemonMenu.display(g);
			}
		}
		if(lab){
			Houses.displayLab(g);
		}
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
    		if (e.getKeyCode() == KeyEvent.VK_M && keys[e.getKeyCode()] == false){
    			menu = true;
			}
    		if (e.getKeyCode() == KeyEvent.VK_DOWN && keys[e.getKeyCode()] == false && menu &&!bag && !pokemon ){
    			Menu.setPosY(40);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && keys[e.getKeyCode()] == false && menu && !bag && !pokemon){
				Menu.setPosY(-40);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN && keys[e.getKeyCode()] == false && bag){
				Items.setPosY(40);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && keys[e.getKeyCode()] == false && bag){
				Items.setPosY(-40);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN && keys[e.getKeyCode()] == false && pokemon){
				PokemonMenu.setPosY();
				PokemonMenu.setPosX();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && keys[e.getKeyCode()] == false && pokemon){
				PokemonMenu.setPosYUP();
				PokemonMenu.setPosX();
			}




			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Menu.getPosY() == 186 && !pokemon){
				PokemonMenu.resetPosXY();
				pokemon = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Menu.getPosY() == 226 && !bag){
				Items.resetPosY();
				bag = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Menu.getPosY() == 266 && !bag && !pokemon){
				Menu.resetPosY();
				menu = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Items.getPosY() == 287 && bag){
				Menu.resetPosY();
				bag = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && PokemonMenu.getDisplayButton() && pokemon){
				Menu.resetPosY();
				pokemon = false;
			}


	    	keys[e.getKeyCode()] = true;
		}
		public void keyReleased(KeyEvent e){
			keys[e.getKeyCode()] =  false;
		}
    }
    public void move() {
		if (!menu) {
			if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) && clear(Player.getPx(),Player.getPy()-1) && clear(Player.getPx()+19,Player.getPy()-1)) {
				direction = UP;
				myGuy.move(direction);
			}
			else if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) && profDoor(Player.getPx(),Player.getPy()-1) && profDoor(Player.getPx()+19,Player.getPy()-1)){
				lab = true;
			}
			else if ((keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) && clear(Player.getPx() + 10,Player.getPy()+27) && clear (Player.getPx()+19,Player.getPy()+27)) {
				direction = DOWN;
				myGuy.move(direction);
			}
			else if ((keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) && clear(Player.getPx() + 20,Player.getPy()) && clear (Player.getPx() + 20,Player.getPy()+26)) {
				direction = RIGHT;
				myGuy.move(direction);
			}
			else if ((keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) && clear(Player.getPx() - 1,Player.getPy()) && clear (Player.getPx() -1, Player.getPy()+26)) {
				direction = LEFT;
				myGuy.move(direction);
			}
			else {
				myGuy.resetExtra();
				myGuy.idle(direction);
			}
		}
	}

	private boolean clear(int x, int y){
		int WALL = 0xFF0000FF;
		if(x<0 || x>= mask.getWidth(null) || y<0 || y>= mask.getHeight(null)){
			return false;
		}
		int c = mask.getRGB(x, y);
		return c != WALL;
	}

	private boolean profDoor(int x, int y){
		int Wall = 0xFF00FF00;
		if(x<0 || x>= mask.getWidth(null) || y<0 || y>= mask.getHeight(null)){
			return false;
		}
		int c = mask.getRGB(x, y);
		return c != Wall;
	}

	public boolean getMenu(){return menu;}
}
