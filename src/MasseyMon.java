import jdk.swing.interop.SwingInterOpUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Key;
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
	public static ArrayList<NPC> trainers = new ArrayList<NPC>();
	public static ArrayList<ArrayList<NPC>> battleTrainers  = new ArrayList<ArrayList<NPC>>();
	public static ArrayList<Pokemon> myPokes = new ArrayList<Pokemon>();
	public static ArrayList<Pokemon> enemyPokes = new ArrayList<Pokemon>();
	public static Image [] starters = new Image [3];
	PokemonBattle pokeBattle;
	public MasseyMon() throws IOException{
		super("MasseyMon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer = new javax.swing.Timer(10,new TickListener());
		load();
		game = new GamePanel();
		add(game);
		pack();
		setVisible(true);
		setResizable(false);
		start();
		inBattle = false;
	}
	public static void main(String[] args) throws IOException{
		frame = new MasseyMon();
	}
	public JTextArea getTextArea2(){
		return game.getTextArea();
	}
	public void startBattle(Graphics g, Player myGuy) throws IOException {
		pokeBattle = new PokemonBattle(myPokes, enemyPokes, myGuy);
		pokeBattle.Start(g);
	}
	public PokemonBattle getPokeBattle(){ return pokeBattle; }
	public void load() throws IOException {
		starters [0] = ImageIO.read(new File("Sprites/Pokemon/P1.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);
		starters [1] = ImageIO.read(new File("Sprites/Pokemon/P4.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);
		starters [2] = ImageIO.read(new File("Sprites/Pokemon/P7.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);

		Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/PlayerPositions.txt")));
		for (int i = 0; i < 13; i++) {
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
		for (int k = 0; k <13;k++) {
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

		for (int i =0; i<18;i++){
			String path = String.format("%s/%s/%s%d.png", "Images", "NPCs", "Trainer", i);
			Image pic = ImageIO.read(new File(path));
			try {
				trainers.add(new NPC(pic));
			}
			catch (Exception e) {
			}
		}
	}
	public static NPC getTrainers(int n){
		return trainers.get(n);
	}

	public static pokeMap getMap(int n){
		return maps.get(n);
	}

	public static pokeMapMini getMiniMap( int n ,int k ) {
		return miniMaps.get(n).get(k);
	}

	public static NPC getBattleTrainers(int n,int v){
		return battleTrainers.get(n).get(v);
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
	private int mx,my,picIndex,miniPicIndex,npc1,npc2,starterIndex,trainerTextIndex;
	private boolean pokemon;
	private boolean bag;
	private boolean menu;
	private boolean spacePressed,movable,talking,startGame,hasStarter,trainerText;
	private int direction;
	private boolean ready = true;
	private static boolean mini,starter;
	private boolean[] keys;
	private Image [] starters;
	private Image selectBox;
	private boolean animating;
	private float frame;
	private Composite var;
	pokeMap myMap;
	pokeMapMini myMiniMap;
	Textbox myTextBox;
	PokemonMenu myPokeMenu;
	Items myItem;
	Menu myMenu;
	Player myGuy;
	NPC myNPC;
	private boolean started;
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10;
	private JTextArea myArea;
	public GamePanel() throws IOException{
		setLayout(null);
		myArea = new JTextArea();
		myArea.setBackground(new Color(0,0,0,0));
		myArea.setBounds(40,635,390,125);
		myArea.setVisible(true);
		myArea.setEditable(false);
		myArea.setHighlighter(null);
		myArea.setWrapStyleWord(true);
		myArea.setLineWrap(true);
		myArea.setFont(new Font("Font/gameFont.ttf",Font.BOLD,30));
		add(myArea);
		movable = false;
		offsetX = 0;
		offsetY = 0;
		starterIndex = 0;
		picIndex = 0;
		miniPicIndex = -1;
		hasStarter = false;
		spacePressed = false;
		trainerText = false;
		pokemon = false;
		bag = false;
		menu = false;
		mini = false;
		talking = false;
		startGame = false;
		starters = MasseyMon.starters;
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		myGuy = new Player(0);
		myNPC = new NPC(MasseyMon.getTrainers(0).getSprite());
		myPokeMenu = new PokemonMenu();
		myMenu = new Menu();
		myItem = new Items();
		myTextBox = new Textbox();
		myMap = (MasseyMon.getMap(picIndex));
		started = false;
		frame = (float)(frame);
		myMiniMap = (MasseyMon.getMiniMap(picIndex,miniPicIndex+1));
		selectBox = ImageIO.read(new File("Images/Text/SelectBox.jpg")).getScaledInstance(300,300,Image.SCALE_SMOOTH);;
		setPreferredSize(new Dimension(956,795));
		addMouseListener(new clickListener());
		addKeyListener(new moveListener());
	}
	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}
	public JTextArea getTextArea(){
		return myArea;
	}
	public void paintComponent(Graphics g) {
		if (!MasseyMon.inBattle) {
			if (!talking && !starter) {
				movable = true;
				g.setColor(new Color(0, 0, 0));
				g.fillRect(0, 0, 956, 795);
				offsetX = 0;
				offsetY = 0;
				if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
					offsetX = myGuy.getScreenX() - myGuy.getWorldX();
				}
				if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
					offsetY = myGuy.getScreenY() - myGuy.getWorldY();
				}
				if (mini) {
					g.drawImage(MasseyMon.getMiniMap(picIndex, miniPicIndex).getMap(), MasseyMon.getMiniMap(picIndex, miniPicIndex).getMapX(), MasseyMon.getMiniMap(picIndex, miniPicIndex).getMapY(), this);
				} else {
					if (offsetX == 0 && offsetY == 0) {
						g.drawImage(MasseyMon.getMap(picIndex).getMap(), MasseyMon.getMap(picIndex).getMapX(), MasseyMon.getMap(picIndex).getMapY(), this);
					} else if (offsetX != 0 && offsetY == 0) {
						g.drawImage(MasseyMon.getMap(picIndex).getMap(), offsetX, MasseyMon.getMap(picIndex).getMapY(), this);
					} else if (offsetX == 0 && offsetY != 0) {
						g.drawImage(MasseyMon.getMap(picIndex).getMap(), MasseyMon.getMap(picIndex).getMapX(), offsetY, this);
					} else {
						g.drawImage(MasseyMon.getMap(picIndex).getMap(), offsetX, offsetY, this);
					}
				}
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
			if (startGame) {
				if (Textbox.getTextWriting()) {
					Textbox.display(g, 0, spacePressed);
					movable = false;
					spacePressed = false;
				} else {
					talking = false;
					startGame = false;
				}
			}
			myGuy.draw(g);
			if (picIndex == 0 && miniPicIndex == 1) {
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 1, spacePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					if (!movable) {
						talking = false;
						starter = false;
						g.setColor(new Color(0, 0, 0));
						g.fillRect(0, 0, 956, 795);
						g.drawImage(MasseyMon.getMiniMap(0, 1).getMap(), MasseyMon.getMiniMap(0, 1).getMapX(), MasseyMon.getMiniMap(0, 1).getMapY(), this);
						if (!spacePressed) {
							starter = true;
							g.setColor(new Color(255, 255, 255));
							g.fillRect(350, 250, 300, 300);
							g.drawImage(selectBox, 350, 250, this);
							g.drawImage(starters[starterIndex], 420, 300, this);
							movable = false;
							hasStarter = true;
						}
					}
				}
			}
			if (pokeCenter()) {
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 2, spacePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(2, 0).getMap(), MasseyMon.getMiniMap(2, 0).getMapX(), MasseyMon.getMiniMap(2, 0).getMapY(), this);
					talking = false;
					movable = true;
				}
			}

			if (pokeShop()) {
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 3, spacePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(2, 1).getMap(), MasseyMon.getMiniMap(2, 1).getMapX(), MasseyMon.getMiniMap(2, 1).getMapY(), this);
					talking = false;
					movable = true;
				}
			}

			if (trainerText){
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g,trainerTextIndex, spacePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					talking = false;
					movable = true;
				}
			}

			if (movable) {
				if (picIndex == 0 && miniPicIndex == 1) {
					g.drawImage(MasseyMon.getTrainers(0).getSprite(), 475, 300, this);
				}
				if (pokeCenter()) {
					g.drawImage(MasseyMon.getTrainers(1).getSprite(), 462, 290, this);
					myGuy.draw(g);
				}
				if (pokeShop()) {
					g.drawImage(MasseyMon.getTrainers(2).getSprite(), 358, 350, this);
					myGuy.draw(g);
				}
				if (pokeHouse()) {
					g.drawImage(MasseyMon.getTrainers(npc1).getSprite(), 407, 385, this);
					g.drawImage(MasseyMon.getTrainers(npc2).getSprite(), 612, 360, this);
					myGuy.draw(g);
				}
			}
		}
		else{
			if (started == false){
				try {
					MasseyMon.frame.startBattle(g,myGuy);
					started = true;
				//	Sound pokeMusic = new Sound("Music/Battle/pokeBattleMusic.mp3",1);
				}
				catch (IOException e) {}
			}
			else{
				MasseyMon.frame.getPokeBattle().Start(g);
			}
		}
		spacePressed = false;
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
			if (MasseyMon.inBattle){
				if (!MasseyMon.frame.getPokeBattle().getStopGame()){
					MasseyMon.frame.getPokeBattle().checkCollision();
				}
			}
		}
	}

	class moveListener implements KeyListener {
		public void keyTyped(KeyEvent e) {
			if (keys[KeyEvent.VK_SPACE]){
				if (MasseyMon.inBattle) {
					MasseyMon.frame.getPokeBattle().goNext();
				}
				else{
					//MasseyMon.frame.inBattle = true;
				}
			}
		}
		public void keyPressed(KeyEvent e) {
			if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && !movable){
				starterIndex -= 1;
				if (starterIndex == -1){
					starterIndex = 2;
				}
			}
			if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && !movable){
				starterIndex += 1;
				if (starterIndex == 3){
					starterIndex = 0;
				}
			}

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

			if (e.getKeyCode () == KeyEvent.VK_SPACE){
				spacePressed = true;
			}
			keys[e.getKeyCode()] = true;
		}

		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
		}
	}


	public void move() {
		if (!menu) {
			if (movable) {
				if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) && clear(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 19, myGuy.getWorldY() - 1) && (checkLedge(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2)
						&& checkLedgeLeft(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2) && checkLedgeRight(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2))) {
					direction = UP;
					myGuy.move(direction, picIndex, miniPicIndex, mini);
					if (checkBuilding(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
						mini = true;
						miniPicIndex += 2;
						npc1 = randint (3,17);
						npc2 = randint(3,17);
						myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						Textbox.setTextWriting(true);
					} else if (checkBuilding2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
						mini = true;
						miniPicIndex += 1;
						npc1 = randint (3,17);
						npc2 = randint(3,17);
						myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						Textbox.setTextWriting(true);
					} else if (checkBuilding3(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
						mini = true;
						miniPicIndex += 3;
						npc1 = randint (3,17);
						npc2 = randint(3,17);
						myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
					} else if (checkBuilding4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
						mini = true;
						miniPicIndex += 4;
						npc1 = randint (3,17);
						npc2 = randint(3,17);
						myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
					} else if (checkBuilding5(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
						mini = true;
						miniPicIndex += 5;
						npc1 = randint (3,17);
						npc2 = randint(3,17);
						myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
						myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
					} else if (checkNextRoute(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
						//if (hasStarter) {
							picIndex += 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

								if (myGuy.getWorldY() == 397 || myGuy.getWorldY() == 900 || myGuy.getWorldY() == 860) {
									myGuy.setScreenY(398);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));

								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
						//	}
						}
					}
					else if (checkPrevRoute(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
						picIndex -= 1;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() == 290 || myGuy.getWorldY() == 300 || myGuy.getWorldY() == 420) {
								myGuy.setScreenY(300);
							} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(0);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
						}
					}
					if (checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer3(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer5(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}

				} else if ((keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) && clear(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27) && (checkLedge(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27)
						&& checkLedgeLeft(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27) && checkLedgeRight(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27))){
					direction = DOWN;
					myGuy.move(direction, picIndex, miniPicIndex, mini);

					if (checkExit1(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
						mini = false;
						miniPicIndex -= 2;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX3());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY3());

						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(398);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY3()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY3());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX3()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX3());
						}
					} else if (checkExit2(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
						mini = false;
						miniPicIndex -= 1;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX2());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY2());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(398);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY2()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY2());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX2()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX2());
						}
					} else if (checkExit3(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
						mini = false;
						miniPicIndex -= 3;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX4());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY4());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() == 975) {
								myGuy.setScreenY(500);
							} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(398);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY4()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY4());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() == 285) {
								myGuy.setScreenX(200);
							} else if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX4()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX4());
						}
					} else if (checkExit4(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
						mini = false;
						miniPicIndex -= 4;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX5());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY5());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(398);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY5()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY5());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX5()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX5());
						}
					} else if (checkExit5(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
						mini = false;
						miniPicIndex -= 5;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX6());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY6());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(398);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY6()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY6());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX6()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX6());
						}
					} else if (checkPrevRoute(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
						picIndex -= 1;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() == 290 || myGuy.getWorldY() == 300 || myGuy.getWorldY() == 420) {
								myGuy.setScreenY(300);
							} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(0);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
						}
					}
					else if (checkNextRoute(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
						picIndex += 1;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

							if (myGuy.getWorldY() == 397 || myGuy.getWorldY() == 900 || myGuy.getWorldY() == 860) {
								myGuy.setScreenY(398);
							}

							else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));

							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
						}
					}
					if (checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer6(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}

				} else if ((keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) && clear(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26) && (checkLedge(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20)
						&& checkLedgeLeft(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && checkLedgeRight(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20))) {
					direction = RIGHT;
					myGuy.move(direction, picIndex, miniPicIndex, mini);

					if (checkNextRoute(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26)) {
						picIndex += 1;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

							if (myGuy.getWorldY() == 860 || myGuy.getWorldY() == 740) {
								myGuy.setScreenY(398);
							}

							else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));

							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
						}
					}
					if (checkPrevRoute(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26)) {
						picIndex -= 1;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() == 650 || myGuy.getWorldY() == 300 || myGuy.getWorldY() == 420 ) {
								myGuy.setScreenY(300);
							} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(0);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() == 1460) {
								myGuy.setScreenX(900);
							} else if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
						}
					}
					if (checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
					if (checkTrainer3(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}
				} else if ((keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) && clear(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26) && (checkLedge(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20)
						&& checkLedgeLeft(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20) && checkLedgeRight(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20))) {
					direction = LEFT;
					myGuy.move(direction, picIndex, miniPicIndex, mini);

					if (checkPrevRoute(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26)) {
						picIndex -= 1;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
							if (myGuy.getWorldY() == 650 || myGuy.getWorldY() == 300 || myGuy.getWorldY() == 420) {
								myGuy.setScreenY(300);
							} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(0);
							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() == 1460) {
								myGuy.setScreenX(900);
							} else if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
						}
					}
					else if (checkNextRoute(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26)) {
						picIndex += 1;
						myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
						myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
						if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

							if (myGuy.getWorldY() == 397 || myGuy.getWorldY() == 900 || myGuy.getWorldY() == 860) {
								myGuy.setScreenY(398);
							}

							else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));

							} else {
								myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
							}
						} else {
							myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
						}
						if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
							if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
								myGuy.setScreenX(478);
							} else {
								myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
							}
						} else {
							myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
						}
					}
					if (checkTrainer4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)){
						talking = true;
						trainerText = true;
						Textbox.setTextWriting(true);
						trainerTextIndex = randint(4,10);
					}

				} else {
					myGuy.resetExtra();
					myGuy.idle(direction);

					if (direction == UP && (hasStarter || mini)){
						if (checkTalk(myGuy.getWorldX()+7, myGuy.getWorldY() - 20, myGuy.getWorldX() + 14, myGuy.getWorldY() - 20)){
							talking = true;
						}
					}
					if (direction == DOWN) {
						if (checkTalk(myGuy.getWorldX(), myGuy.getWorldY() + 37, myGuy.getWorldX() + 20, myGuy.getWorldY() + 37)) {
							talking = true;
						}
					}
					if (direction == RIGHT) {
						if (checkTalk(myGuy.getWorldX() + 35, myGuy.getWorldY() + 8, myGuy.getWorldX() + 35, myGuy.getWorldY() + 18)) {
							talking = true;
						}
					}
					if (direction == LEFT){
						if (checkTalk(myGuy.getWorldX() - 10, myGuy.getWorldY(), myGuy.getWorldX() - 10, myGuy.getWorldY() + 26)){
							talking = true;
						}
					}
				}
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
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkPrevRoute ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();
		int WALL = 0xFF800000;
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

	private boolean checkLedgeLeft(int x, int y, int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF8080FF;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		if (c!= WALL && d!= WALL){
			return true;
		}
		else{
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_DOWN]|| keys[KeyEvent.VK_S] || keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				return false;
			}
		}
		return true;
	}

	private boolean checkLedgeRight(int x, int y, int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFF8080;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		if (c!= WALL && d!= WALL){
			return true;
		}
		else{
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_LEFT]|| keys[KeyEvent.VK_A] || keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]){
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
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkTalk ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF80FF80;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL && spacePressed;
	}

	private boolean checkTrainer1 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFF80FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer2 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFFFF80;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer3 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF0080FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer4 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFF0080;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer5 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF00FF80;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer6 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF8000FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}





	public boolean getMenu () {
		return menu;
	}

	public static int randint(int low, int high){
		return (int)(Math.random()*(high-low+1)+low);
	}

	public boolean pokeCenter (){
		if (picIndex == 2 && miniPicIndex == 0 || picIndex == 6 && miniPicIndex == 0 || picIndex == 8 && miniPicIndex == 0 || picIndex == 12 && miniPicIndex == 0){
			return true;
		}
		return false;
	}

	public boolean pokeShop(){
		if (picIndex == 2 && miniPicIndex == 1 || picIndex == 6 && miniPicIndex == 1 || picIndex == 12 && miniPicIndex == 1){
			return true;
		}
		return false;
	}

	public boolean pokeHouse(){
		if (picIndex == 2){
			if (miniPicIndex == 2 || miniPicIndex == 3){
				return true;
			}
		}
		if (picIndex == 6){
			if (miniPicIndex == 2 || miniPicIndex == 3){
				return true;
			}
		}
		return false;
	}


	public static void setStarter(boolean temp){
		starter = temp;
	}
}
