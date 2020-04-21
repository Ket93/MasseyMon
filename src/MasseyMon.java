import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.*;

public class MasseyMon extends JFrame {
	public static MasseyMon frame;
	GamePanel game;
	javax.swing.Timer myTimer;
	private ArrayList<Image> myPokeImages = new ArrayList<Image>();
	private ArrayList<Image> enemyPokeImages = new ArrayList<Image>();
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
		frame = new MasseyMon();
    }
	public void loadImages() throws IOException{
		for (int i = 1; i < 152; i++){
			String path = String.format("Sprites/Pokemon/P%dM.png",i);
			Image pic = ImageIO.read(new File(path));
			myPokeImages.add(pic);
		}
		for (int i = 1; i < 152; i++){
			String path = String.format("Sprites/Pokemon/P%d.png",i);
			Image pic = ImageIO.read(new File(path));
			enemyPokeImages.add(pic);
		}
	}
	public Image[] getPokeImages(int [] indexes){
		Image[] newSprites = new Image[2];
		newSprites[0] = myPokeImages.get(indexes[0]);
		newSprites[1] = enemyPokeImages.get(indexes[1]);
		return newSprites;
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

class GamePanel extends JPanel {
	private int posIndex;
	private Image[] backgrounds;
	private BufferedImage[] backgroundMasks;
	private Rectangle[] positions;
	private int playerPositionCount;
	private int  [][] playerPositions;
	private int picIndex;
	private boolean pokemon;
	private boolean bag;
	private boolean menu;
	private int direction;
	public boolean ready = true;
	private Image back, oakLab;
	private BufferedImage mask, oakLabMask;
	private boolean[] keys;
	Textbox myTextBox;
	PokemonMenu myPokeMenu;
	Items myItem;
	Menu myMenu;
	Player myGuy;
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10;

	public GamePanel() throws IOException {
		playerPositionCount = 0;

		backgrounds = new Image[4];
		for (int i = 0; i < 4; i++) {
			String path = String.format("%s/%s/%s%d.png", "Images", "Towns", "Background", i);
			try {
				Image pic = ImageIO.read(new File(path));
				backgrounds[i] = pic;
			} catch (IOException e) {
			}
		}

		backgroundMasks = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			String path = String.format("%s/%s/%s%d%s.png", "Images", "Masks", "Background", i, "Mask");
			try {
				BufferedImage pic = ImageIO.read(new File(path));
				backgroundMasks[i] = pic;
			} catch (IOException e) {
			}
		}

		positions = new Rectangle[4];
		for (int i = 0; i < 4; i++) {
			Rectangle rect = new Rectangle((956 - backgrounds[i].getWidth(null)) / 2, (795 - backgrounds[i].getHeight(null)) / 2, backgrounds[i].getWidth(null), backgrounds[i].getHeight(null));
			positions[i] = rect;
		}

		playerPositions = new int [3][4];
			Scanner inFile = new Scanner (new BufferedReader( new FileReader("Data/PlayerPositions.txt")));
			while (inFile.hasNextLine()){
				String line = inFile.nextLine();
				String [] coordinates = line.split(",");
				playerPositions[playerPositionCount][0] = Integer.parseInt(coordinates[0]);
				playerPositions[playerPositionCount][1] = Integer.parseInt(coordinates[1]);
				playerPositions[playerPositionCount][2] = Integer.parseInt(coordinates[2]);
				playerPositions[playerPositionCount][3] = Integer.parseInt(coordinates[3]);
				playerPositionCount ++;
			}
			inFile.close();

		posIndex = 0;
		picIndex = 1;
		pokemon = false;
		bag = false;
		menu = false;
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		myGuy = new Player(0);
		myPokeMenu = new PokemonMenu();
		myMenu = new Menu();
		myItem = new Items();
		myTextBox = new Textbox();
		try {
			back = ImageIO.read(new File("Images/Towns/palletTown.png"));
			oakLab = ImageIO.read(new File("Images/Buildings/ProfessorOakLab.png"));
			mask = ImageIO.read(new File("Images/Towns/palletTownMask.png"));
			oakLabMask = ImageIO.read(new File("Images/Buildings/ProfessorOakLabMask.png"));
		} catch (IOException e) {
		}
		setSize(956, 795);
		addMouseListener(new clickListener());
		addKeyListener(new moveListener());
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,956,795);
		if ((int)positions[picIndex].getWidth() > 956 ||(int)positions[picIndex].getHeight() > 795) {
			g.drawImage(backgrounds[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY(), this);
		}
		else{
			
		}
		myGuy.draw(g);

		if (menu) {
			Menu.display(g);
			if (bag) {
				Items.display(g);
			}
			if (pokemon) {
				PokemonMenu.display(g);
			}
		}
	}

	class clickListener implements MouseListener {
		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}
	}

	class moveListener implements KeyListener {
		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_M && keys[e.getKeyCode()] == false) {
				menu = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN && keys[e.getKeyCode()] == false && menu && !bag && !pokemon) {
				Menu.setPosY(40);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && keys[e.getKeyCode()] == false && menu && !bag && !pokemon) {
				Menu.setPosY(-40);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN && keys[e.getKeyCode()] == false && bag) {
				Items.setPosY(40);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && keys[e.getKeyCode()] == false && bag) {
				Items.setPosY(-40);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN && keys[e.getKeyCode()] == false && pokemon) {
				PokemonMenu.setPosY();
				PokemonMenu.setPosX();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && keys[e.getKeyCode()] == false && pokemon) {
				PokemonMenu.setPosYUP();
				PokemonMenu.setPosX();
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Menu.getPosY() == 186 && !pokemon) {
				PokemonMenu.resetPosXY();
				pokemon = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Menu.getPosY() == 226 && !bag) {
				Items.resetPosY();
				bag = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Menu.getPosY() == 266 && !bag && !pokemon) {
				Menu.resetPosY();
				menu = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && Items.getPosY() == 287 && bag) {
				Menu.resetPosY();
				bag = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER && keys[e.getKeyCode()] == false && PokemonMenu.getDisplayButton() && pokemon) {
				Menu.resetPosY();
				pokemon = false;
			}

			keys[e.getKeyCode()] = true;
		}

		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
		}
	}

	public void move() {
		if (!menu) {
			if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) && clear(Player.getPx(), Player.getPy() - 1, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && clear(Player.getPx() + 19, Player.getPy() - 1, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
				direction = UP;
				myGuy.move(direction);
				if (checkBuilding(Player.getPx(), Player.getPy() - 1, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && checkBuilding(Player.getPx() + 19, Player.getPy() - 1, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
					picIndex++;
					if (posIndex%2!=0) {
						posIndex++;
					}
					Player.setPx(playerPositions[posIndex+1][0]);
					Player.setPy(playerPositions[posIndex+1][1]);
				}
				if (checkBuilding2(Player.getPx(), Player.getPy() - 1, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && checkBuilding2(Player.getPx() + 19, Player.getPy() - 1, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
					picIndex--;
					if (posIndex%2==0) {
						posIndex--;
					}
					Player.setPx(playerPositions[posIndex+1][0]);
					Player.setPy(playerPositions[posIndex+1][1]);
				}
				if (checkNextRoute(Player.getPx(), Player.getPy() -1, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && checkNextRoute(Player.getPx() +19, Player.getPy() -1, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
					picIndex+=2;
					posIndex++;
					Player.setPx(playerPositions[posIndex + 1][0]);
					Player.setPy(playerPositions[posIndex + 1][1]);
				}

			} else if ((keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) && clear(Player.getPx(), Player.getPy() + 27, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && clear(Player.getPx() + 19, Player.getPy() + 27, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
				direction = DOWN;
				myGuy.move(direction);

				if (checkExit1(Player.getPx() - 1, Player.getPy() + 27, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && checkExit1(Player.getPx() - 1, Player.getPy() + 27, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
					picIndex--;
					Player.setPx(playerPositions[posIndex+1][2]);
					Player.setPy(playerPositions[posIndex+1][3]);
				}
				if (checkExit2(Player.getPx() - 1, Player.getPy() + 27, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && checkExit2(Player.getPx() - 1, Player.getPy() + 27, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
					picIndex++;
					Player.setPx(playerPositions[posIndex+1][2]);
					Player.setPy(playerPositions[posIndex+1][3]);
				}
			}
			else if ((keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) && clear(Player.getPx() + 20, Player.getPy(), backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && clear(Player.getPx() + 20, Player.getPy() + 26, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
					direction = RIGHT;
					myGuy.move(direction);
			}
			else if ((keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) && clear(Player.getPx() - 1, Player.getPy(), backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY()) && clear(Player.getPx() - 1, Player.getPy() + 26, backgroundMasks[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY())) {
				direction = LEFT;
				myGuy.move(direction);
			}
			else {
				myGuy.resetExtra();
				myGuy.idle(direction);
					}
				}
			}

			private boolean clear ( int x, int y, BufferedImage maskPic ,int posX, int posY){
				int WALL = 0xFF0000FF;
				if (x < (0 + posX) || x >=  (maskPic.getWidth(null) + posX) || y < (0 + posY) || y >= (maskPic.getHeight(null) + posY)) {
					return false;
				}
				int c = maskPic.getRGB(x - posX, y -posY);
				return c != WALL;
			}

			private boolean checkBuilding ( int x, int y, BufferedImage maskPic ,int posX, int posY){
				int WALL = 0xFF00FF00;
				if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
					return false;
				}
				int c = maskPic.getRGB(x - posX, y - posY);
				return c == WALL;
			}

			private boolean checkExit1 ( int x, int y, BufferedImage maskPic ,int posX, int posY){
				int WALL = 0xFFFF0000;
				if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
					return false;
				}
				int c = maskPic.getRGB(x - posX, y - posY);
				return c == WALL;
			}

			private boolean checkExit2 ( int x, int y, BufferedImage maskPic ,int posX, int posY){
				int WALL = 0xFF00FFFF;
				if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
					return false;
				}
				int c = maskPic.getRGB(x - posX, y - posY);
				return c == WALL;
	}

			private boolean checkBuilding2 ( int x, int y, BufferedImage maskPic ,int posX, int posY){
				int WALL = 0xFFFF00FF;
				if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
					return false;
				}
				int c = maskPic.getRGB(x - posX, y - posY);
				return c == WALL;
			}

			private boolean checkNextRoute ( int x, int y, BufferedImage maskPic ,int posX, int posY){
				int WALL = 0xFFFFFF00;
				if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
					return false;
				}
				int c = maskPic.getRGB(x - posX, y - posY);
				return c == WALL;
			}




			public boolean getMenu () {
				return menu;
			}
		}

