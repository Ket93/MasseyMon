import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.*;

public class MasseyMon extends JFrame {
	GamePanel game;
	javax.swing.Timer myTimer;
	public static boolean inBattle;
	public static MasseyMon frame;
	public static ArrayList<pokeMap> maps = new ArrayList<pokeMap>();
	public static ArrayList<ArrayList<pokeMapMini>> miniMaps = new ArrayList<ArrayList<pokeMapMini>>();
	public static ArrayList<Pokemon> myPokes = new ArrayList<Pokemon>();
	public static ArrayList<Pokemon> enemyPokes = new ArrayList<Pokemon>();
	PokemonBattle pokeBattle;
	public MasseyMon() throws IOException {
		super("MasseyMon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer = new javax.swing.Timer(10,new TickListener());
		setSize(956,795);
		loadMaps();
		game = new GamePanel();
		add(game);
		setResizable(false);
		setVisible(true);
		start();
		inBattle = false;
	}
	public static void main(String[] args) throws IOException{
		frame = new MasseyMon();
	}
	public void loadMaps() throws IOException {
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/PlayerPositions.txt")));
		for (int i = 0; i < 5; i++) {
			String line = inFile.nextLine();
			String path = String.format("%s/%s/%s%d.png", "Images", "Backgrounds", "Background", i);
			String pathMask = String.format("%s/%s/%s%d%s.png", "Images", "Masks", "Background", i,"Mask");
			try {
				Image pic = ImageIO.read(new File(path));
				BufferedImage picMask = ImageIO.read(new File(pathMask));
				maps.add(new pokeMap(pic,picMask,line));
			} catch (IOException e) {}
		}
		inFile = new Scanner(new BufferedReader(new FileReader("Data/miniPlayerPositions")));
		for (int k = 0; k <5;k++) {
			miniMaps.add(new ArrayList<pokeMapMini>());
			for (int i = 0; i < 5; i++) {
				String line = inFile.nextLine();
				String path = String.format("%s/%s/%s%d/%s%d.png", "Images", "MiniBackgrounds", "Town ", k, "MiniBackground", i);
				String pathMask = String.format("%s/%s/%s%d/%s%d%s.png", "Images", "MiniMasks","Town ",k, "Background", i, "Mask");
				try {
					Image pic = ImageIO.read(new File(path));
					BufferedImage picMask = ImageIO.read(new File(pathMask));
					miniMaps.get(k).add(new pokeMapMini(pic, picMask, line));
				}
				catch (IOException e) {
				}
			}
		}
	}
	public static pokeMap getMap(int n){
		return maps.get(n);
	}

	public static pokeMapMini getMiniMap( int n ,int k ){
		return miniMaps.get(n).get(k);
	}

	public void startBattle(Graphics g){
		pokeBattle.Start(g);
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
	private static int offsetX,offsetY;
	private int mx,my,picIndex,miniPicIndex;
	private boolean pokemon;
	private boolean bag;
	private boolean menu;
	private int direction;
	private boolean ready = true;
	private static boolean mini;
	private boolean[] keys;
	pokeMap myMap;
	pokeMapMini myMiniMap;
	Textbox myTextBox;
	PokemonMenu myPokeMenu;
	Items myItem;
	Menu myMenu;
	Player myGuy;
	MasseyMon curGame;
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10;
	public GamePanel() throws IOException {
		offsetX = 0;
		offsetY = 0;
		picIndex =0;
		miniPicIndex = -1;
		pokemon = false;
		bag = false;
		menu = false;
		mini = false;
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		myGuy = new Player(0);
		myPokeMenu = new PokemonMenu();
		myMenu = new Menu();
		myItem = new Items();
		myTextBox = new Textbox();
		myMap = (MasseyMon.getMap(picIndex));
		myMiniMap = (MasseyMon.getMiniMap(picIndex,miniPicIndex+1));
		setSize(956, 795);
		addMouseListener(new clickListener());
		addKeyListener(new moveListener());
		curGame = MasseyMon.frame;
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}
	public void paintComponent(Graphics g) {
		if (MasseyMon.inBattle){
			curGame.startBattle(g);
		}
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,956,795);
		offsetX = 0;
		offsetY = 0;
		if (MasseyMon.getMap(picIndex).getMapWidth() > 956){
			offsetX = myGuy.getScreenX() - myGuy.getWorldX();
		}
		if (MasseyMon.getMap(picIndex).getMapHeight() > 795){
			offsetY = myGuy.getScreenY() - myGuy.getWorldY();
		}
		if (mini){
			g.drawImage(MasseyMon.getMiniMap(picIndex,miniPicIndex).getMap(),MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX(),MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY(),this);
		}
		else {
			if (offsetX == 0 && offsetY ==0){
				g.drawImage(MasseyMon.getMap(picIndex).getMap(), MasseyMon.getMap(picIndex).getMapX(), MasseyMon.getMap(picIndex).getMapY(), this);
			}
			else if (offsetX != 0 && offsetY == 0){
				g.drawImage(MasseyMon.getMap(picIndex).getMap(), offsetX, MasseyMon.getMap(picIndex).getMapY(), this);
			}
			else if (offsetX == 0 && offsetY != 0){
				g.drawImage(MasseyMon.getMap(picIndex).getMap(), MasseyMon.getMap(picIndex).getMapX(), offsetY, this);
			}
			else{
				g.drawImage(MasseyMon.getMap(picIndex).getMap(), offsetX, offsetY, this);
			}
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
		//Textbox.display(g);
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
			mx = e.getX();
			my = e.getY();
		}
	}

	class moveListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
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
			if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) && clear(myGuy.getWorldX(), myGuy.getWorldY()-1,myGuy.getWorldX()+19,myGuy.getWorldY()-1) && checkLedge(myGuy.getWorldX(), myGuy.getWorldY()-2,myGuy.getWorldX()+19,myGuy.getWorldY()-2) ) {
				direction = UP;
				myGuy.move(direction,picIndex,miniPicIndex,mini);
				if (checkBuilding(myGuy.getWorldX(), myGuy.getWorldY() - 1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)) {
					mini = true;
					miniPicIndex += 2;

					myGuy.setWorldX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
				}
				else if (checkBuilding2(myGuy.getWorldX(), myGuy.getWorldY() - 1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)) {
					mini = true;
					miniPicIndex += 1;
					myGuy.setWorldX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
				}
				else if (checkBuilding3(myGuy.getWorldX(), myGuy.getWorldY() -1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)){
					mini = true;
					miniPicIndex +=3;
					myGuy.setWorldX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
				}
				else if (checkBuilding4(myGuy.getWorldX(), myGuy.getWorldY() -1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)){
					mini = true;
					miniPicIndex +=4;
					myGuy.setWorldX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
				}
				else if (checkBuilding5(myGuy.getWorldX(), myGuy.getWorldY() -1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)){
					mini = true;
					miniPicIndex +=5;
					myGuy.setWorldX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenY(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosY());
					myGuy.setScreenX(MasseyMon.getMiniMap(picIndex,miniPicIndex).getStartPosX());
				}
				else if (checkNextRoute(myGuy.getWorldX(), myGuy.getWorldY() -1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)) {
					picIndex += 1;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
					if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

						if (myGuy.getWorldY() == 397){
							myGuy.setScreenY(398);
						}

						else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
							myGuy.setScreenY(710);
						}
						else {
							myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
						}
					}

					else{
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
						}
					if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
						if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
							myGuy.setScreenX(478);
						}
					else {
							myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
						}
					}
					else{
						myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
					}
				}

			} else if ((keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) && clear(myGuy.getWorldX(), myGuy.getWorldY() + 27,myGuy.getWorldX()+19,myGuy.getWorldY()+27) && checkLedge(myGuy.getWorldX(), myGuy.getWorldY() + 27,myGuy.getWorldX()+19,myGuy.getWorldY()+27)) {
				direction = DOWN;
				myGuy.move(direction,picIndex,miniPicIndex,mini);

				if (checkExit1(myGuy.getWorldX()-1, myGuy.getWorldY() + 27,myGuy.getWorldX()+20,myGuy.getWorldY()+27)) {
					mini = false;
					miniPicIndex-=2;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX3());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY3());

					if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
						if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
							myGuy.setScreenY(398);
						} else {
							myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY3()));
						}
					}

					else{
						myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY3());
					}
					if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
						if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
							myGuy.setScreenX(478);
						}
						else {
							myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX3()));
						}
					}
					else{
						myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX3());
					}
				}
				else if (checkExit2(myGuy.getWorldX()-1, myGuy.getWorldY() + 27, myGuy.getWorldX()+20,myGuy.getWorldY()+27)) {
					mini = false;
					miniPicIndex-=1;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX2());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY2());
					if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
						if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
							myGuy.setScreenY(398);
						} else {
							myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY2()));
						}
					}

					else{
						myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY2());
					}
					if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
						if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
							myGuy.setScreenX(478);
						}
						else {
							myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX2()));
						}
					}
					else{
						myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX2());
					}
				}
				else if (checkExit3(myGuy.getWorldX()-1, myGuy.getWorldY() + 27, myGuy.getWorldX()+20,myGuy.getWorldY()+27)) {
					mini = false;
					miniPicIndex-=3;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX4());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY4());
					if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
						if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
							myGuy.setScreenY(398);
						} else {
							myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY4()));
						}
					}

					else{
						myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY4());
					}
					if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
						if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
							myGuy.setScreenX(478);
						}
						else {
							myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX4()));
						}
					}
					else{
						myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX4());
					}
				}
				else if (checkExit4(myGuy.getWorldX()-1, myGuy.getWorldY() + 27, myGuy.getWorldX()+20,myGuy.getWorldY()+27)) {
					mini = false;
					miniPicIndex-=4;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX5());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY5());
					if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
						if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
							myGuy.setScreenY(398);
						} else {
							myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY5()));
						}
					}

					else{
						myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY5());
					}
					if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
						if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
							myGuy.setScreenX(478);
						}
						else {
							myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX5()));
						}
					}
					else{
						myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX5());
					}
				}
				else if (checkExit5(myGuy.getWorldX()-1, myGuy.getWorldY() + 27, myGuy.getWorldX()+20,myGuy.getWorldY()+27)) {
					System.out.println(MasseyMon.getMap(picIndex).getStartPosX6());
					mini = false;
					miniPicIndex-=5;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX6());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY6());
					if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
						if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
							myGuy.setScreenY(398);
						} else {
							myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY6()));
						}
					}

					else{
						myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY6());
					}
					if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
						if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
							myGuy.setScreenX(478);
						}
						else {
							myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX6()));
						}
					}
					else{
						myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX6());
					}
				}
				else if (checkPrevRoute(myGuy.getWorldX()-1,myGuy.getWorldY()+27,myGuy.getWorldX()+20,myGuy.getWorldY()+27)){
					picIndex -=1;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
					if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
						if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
							myGuy.setScreenY(0);
						} else {
							myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
						}
					}

					else{
						myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
					}
					if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
						if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
							myGuy.setScreenX(478);
						}
						else {
							myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
						}
					}
					else{
						myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
					}
				}

			}
			else if ((keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) && clear(myGuy.getWorldX() + 20, myGuy.getWorldY(),myGuy.getWorldX() + 20, myGuy.getWorldY() + 26)&& clear(myGuy.getWorldX() + 20, myGuy.getWorldY(),myGuy.getWorldX() + 20, myGuy.getWorldY() + 20)) {
				direction = RIGHT;
				myGuy.move(direction,picIndex,miniPicIndex,mini);
			}
			else if ((keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) && clear(myGuy.getWorldX() - 1, myGuy.getWorldY(),myGuy.getWorldX() - 1, myGuy.getWorldY() + 26) && checkLedge(myGuy.getWorldX() - 1, myGuy.getWorldY(),myGuy.getWorldX() - 1, myGuy.getWorldY() + 20)) {
				direction = LEFT;
				myGuy.move(direction,picIndex,miniPicIndex,mini);
			}
			else {
				myGuy.resetExtra();
				myGuy.idle(direction);
			}
		}
	}

	private boolean clear ( int x, int y, int x2, int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY = MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF0000FF;
		if (x < (0 + posX) || x >=  (maskPic.getWidth(null) + posX) || y < (0 + posY) || y >= (maskPic.getHeight(null) + posY)) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c != WALL && d!=WALL;
	}

	private boolean checkBuilding ( int x, int y,int x2,int y2){
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();

			int WALL = 0xFF00FF00;
			if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
				return false;
			}
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkExit1 ( int x, int y,int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}
		int WALL = 0xFFFF0000;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkExit2 ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF00FFFF;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkBuilding2 ( int x, int y, int x2, int y2) {
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();

			int WALL = 0xFFFF00FF;
			if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
				return false;
			}
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkNextRoute ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFFFF00;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkPrevRoute ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();
		int WALL = 0xFF800000;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkLedge(int x, int y, int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF000080;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		if (c!= WALL && d!= WALL){
			return true;
		}
		else{
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_LEFT]|| keys[KeyEvent.VK_A] || keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				return false;
			}
		}
		return true;
	}

	private boolean checkBuilding3 ( int x, int y, int x2,int y2) {
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();
			int WALL = 0xFF008000;
			if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
				return false;
			}
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkExit3 ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF808000;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkBuilding4 ( int x, int y, int x2,int y2){
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();
			int WALL = 0xFF808080;
			if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
				return false;
			}
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkExit4 ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF008080;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkBuilding5 ( int x, int y, int x2,int y2) {
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();
			int WALL = 0xFF80FF00;
			if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
				return false;
			}
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkExit5 ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFFFF8000;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}


	public boolean getMenu () {
		return menu;
	}
}
