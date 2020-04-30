import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.*;

public class MasseyMon extends JFrame {
	public static MasseyMon frame;
	GamePanel game;
	javax.swing.Timer myTimer;
	private Pokemon curPoke1,curPoke2;
	public static boolean inBattle;
	public static ArrayList<Pokemon> myPokes = new ArrayList<Pokemon>();
	public static ArrayList<Pokemon> enemyPokes = new ArrayList<Pokemon>();
	private ArrayList<Image> myPokeImages = new ArrayList<Image>();
	private ArrayList<Image> enemyPokeImages = new ArrayList<Image>();
	public static ArrayList<Pokemon> allPokemon = new ArrayList<Pokemon>();
	public static ArrayList<Attack> allAttacks = new ArrayList<Attack>();
	public static ArrayList<pokeMap> maps = new ArrayList<pokeMap>();
	public static ArrayList<pokeMapMini> miniMaps = new ArrayList<pokeMapMini>();
	public MasseyMon() throws IOException {
		super("MasseyMon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer = new javax.swing.Timer(10,new TickListener());
		setSize(956,795);
		load();
		loadImages();
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
	public int[] getPokes(){
		int [] pokeIndexes = new int[2];
		curPoke1 = myPokes.get(0);
		curPoke2 = enemyPokes.get(0);
		pokeIndexes[0] = curPoke1.getNum();
		pokeIndexes[1] = curPoke2.getNum();
		return pokeIndexes;
	}
	public void loadImages() throws IOException{
		for (int i = 1; i < 152; i++){
			String path = String.format("Sprites/Pokemon/P%dM.png",i);
			Image pic = ImageIO.read(new File(path));
			pic = pic.getScaledInstance(300,233,Image.SCALE_SMOOTH);
			myPokeImages.add(pic);
		}
		for (int i = 1; i < 152; i++){
			String path = String.format("Sprites/Pokemon/P%d.png",i);
			Image pic = ImageIO.read(new File(path));
			pic = pic.getScaledInstance(220,180,Image.SCALE_SMOOTH);
			enemyPokeImages.add(pic);
		}
	}
	public void load() throws FileNotFoundException{
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Pokemon2.txt")));
		String dumInp = inFile.nextLine();
		while (inFile.hasNext()){
			String line = inFile.nextLine();
			Pokemon newPoke = new Pokemon(line);
			allPokemon.add(newPoke);
		}
		for (int i = 0; i < 6; i++){
			myPokes.add(allPokemon.get(i));
			enemyPokes.add(allPokemon.get(i+6));
		}
		inFile = new Scanner(new BufferedReader(new FileReader("Data/Moves.txt")));
		while(inFile.hasNext()){
			String line = inFile.nextLine();
			Attack newAtk = new Attack(line);
			allAttacks.add(newAtk);
		}
		for (int i = 0; i < 6; i++){
			myPokes.get(i).learnMove(allAttacks.get(0));
		}


		inFile = new Scanner(new BufferedReader(new FileReader("Data/PlayerPositions.txt")));

		for (int i = 0; i < 2; i++) {
			String line = inFile.nextLine();
			String path = String.format("%s/%s/%s%d.png", "Images", "Backgrounds", "Background", i);
			String pathMask = String.format("%s/%s/%s%d%s.png", "Images", "Masks", "Background", i,"Mask");
			try {
				Image pic = ImageIO.read(new File(path));
				BufferedImage picMask = ImageIO.read(new File(pathMask));
				maps.add(new pokeMap(pic,picMask,line));
			}
			catch (IOException e) {
			}
		}

		inFile = new Scanner(new BufferedReader(new FileReader("Data/miniPlayerPositions")));
		for (int k = 0; k <1;k++) {
			for (int i = 0; i < 2; i++) {
				String line = inFile.nextLine();
				String path = String.format("%s/%s/%s%d/%s%d.png", "Images", "MiniBackgrounds", "Town ", k, "MiniBackground", i);
				String pathMask = String.format("%s/%s/%s%d/%s%d%s.png", "Images", "MiniMasks","Town ",k, "Background", i, "Mask");
				try {
					Image pic = ImageIO.read(new File(path));
					BufferedImage picMask = ImageIO.read(new File(pathMask));
					miniMaps.add(new pokeMapMini(pic, picMask, line));
				} catch (IOException e) {
				}
			}
		}
	}

	public static pokeMap getMap(int n){
		return maps.get(n);
	}

	public static pokeMapMini getMiniMap( int n){
		return miniMaps.get(n);
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
	private static int offsetX,offsetY;
	private int mx,my,picIndex,miniPicIndex;
	private boolean pokemon;
	private boolean bag;
	private boolean menu;
	private int direction;
	private static boolean ifOffsetX,ifOffsetY;
	private boolean ready = true;
	private boolean mini;
	private Image pokeArenaBack;
	private String choice;
	private boolean[] keys;
	private Image[] battleSprites;
	private Rectangle fightButton,bagButton,pokeButton,runButton,myPokeHealth,enemyPokeHealth;
	private int HPRectWidths;
	private ArrayList<Rectangle> rectButtons;
	private Font gameFont,smallerGameFont;
	pokeMap myMap;
	pokeMapMini myMiniMap;
	Textbox myTextBox;
	PokemonMenu myPokeMenu;
	Items myItem;
	Menu myMenu;
	Player myGuy;
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10;
	public GamePanel() throws IOException {

		fightButton = new Rectangle(464,584,236,86);
		bagButton = new Rectangle(701,584,236,86);
		pokeButton = new Rectangle(464,671,236,86);
		runButton = new Rectangle(701,671,236,86);
		HPRectWidths = 182;
		myPokeHealth = new Rectangle(740,460,182,18);
		enemyPokeHealth = new Rectangle(185,142,182,19);
		rectButtons = new ArrayList<Rectangle>();
		rectButtons.add(fightButton);
		rectButtons.add(bagButton);
		rectButtons.add(pokeButton);
		rectButtons.add(runButton);
		choice = "none";

		try{
			gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
			gameFont = gameFont.deriveFont(40f);
			smallerGameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
			smallerGameFont = gameFont.deriveFont(35f);
		}
		catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

		offsetX = 0;
		offsetY = 0;
		picIndex =0;
		miniPicIndex = -1;
		ifOffsetX = false;
		ifOffsetY = false;
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
		myMiniMap = (MasseyMon.getMiniMap(miniPicIndex+1));

		try {
			pokeArenaBack = ImageIO.read(new File("Images/Battles/PokeBattle2.jpg"));
			pokeArenaBack = pokeArenaBack.getScaledInstance(945,770,Image.SCALE_SMOOTH);

		} catch (IOException e) {
			System.out.println("G");
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
	public void checkButtonCollision(int mx, int my){
		if (choice.equals("fight")){
			for (Rectangle item: rectButtons){
				if (item.contains(mx,my)){
					Pokemon attacker = MasseyMon.myPokes.get(0);
					Pokemon defender = MasseyMon.enemyPokes.get(0);
					attacker.doAttack(attacker.getMoves().get(rectButtons.indexOf(item)),defender);
				}
			}
		}
		if (fightButton.contains(mx,my)){
			System.out.println("fight");
			choice = "fight";
		}
		else if (bagButton.contains(mx,my)){
			System.out.println("bag");
			choice = "bag";
		}
		else if (pokeButton.contains(mx,my)){
			System.out.println("pokemon");
			choice = "pokemon";
		}
		else if (runButton.contains(mx,my)){
			System.out.println("run");
			choice = "run";
		}
	}
	public void paintComponent(Graphics g) {
		if (MasseyMon.inBattle){
			g.drawImage(pokeArenaBack,0,-5,null);
			int [] curPokes = MasseyMon.frame.getPokes();
			Pokemon myPoke = MasseyMon.frame.allPokemon.get(curPokes[0]);
			Pokemon enemyPoke = MasseyMon.frame.allPokemon.get(curPokes[1]);
			battleSprites = MasseyMon.frame.getPokeImages(curPokes);
			g.drawImage(battleSprites[0],90,355,null);
			g.drawImage(battleSprites[1],620,175,null);
			g.setFont(gameFont);
			g.setColor(Color.GREEN);
			System.out.println(enemyPoke.getHP()/enemyPoke.getMaxHP());
			g.fillRect((int)myPokeHealth.getX(),(int)myPokeHealth.getY(),(int)(myPokeHealth.getWidth()*myPoke.getHP()/myPoke.getMaxHP()),(int)myPokeHealth.getHeight());
			g.fillRect((int)enemyPokeHealth.getX(),(int)enemyPokeHealth.getY(),(int)(enemyPokeHealth.getWidth()*enemyPoke.getHP()/enemyPoke.getMaxHP()),(int)enemyPokeHealth.getHeight());
			String pokeName = myPoke.getName();
			g.setColor(Color.black);
			g.drawString(pokeName,560,440);
			g.drawString(enemyPoke.getName(),15,125);
			String text = String.format("What  will  %s  do?",pokeName);
			g.drawString(text,50,640);
			if (choice.equals(("none"))){
				g.drawString("Fight",555,640);
				g.drawString("Bag",795,640);
				g.drawString("Pokemon",535,726);
				g.drawString("Run",795,726);
			}
			else if (choice.equals("fight")){
				g.setFont(smallerGameFont);
				Attack curAttack = MasseyMon.myPokes.get(0).getMoves().get(0);
				if (curAttack != null){
					g.drawString(curAttack.getName(),490,630);
					g.drawString(curAttack.getType(),600,630);
					g.drawString("DMG:"+curAttack.getDmg(),490,660);
					g.drawString("PP:"+curAttack.getPP()+"/"+curAttack.getMaxPP(),590,660);
				}
			}
		}
		else{
			g.setColor(new Color(0,0,0));
			g.fillRect(0,0,956,795);
			ifOffsetX = false;
			ifOffsetY = false;
			if (MasseyMon.getMap(picIndex).getMapWidth() > 956){
				ifOffsetX = true;
				offsetX = myGuy.getScreenX() - myGuy.getWorldX();
			}
			if (MasseyMon.getMap(picIndex).getMapHeight() > 795){
				ifOffsetY = true;
				offsetY = myGuy.getScreenY() - myGuy.getWorldY();
			}
			if (mini){
				g.drawImage(MasseyMon.getMiniMap(miniPicIndex).getMap(),MasseyMon.getMiniMap(miniPicIndex).getMapX(),MasseyMon.getMiniMap(miniPicIndex).getMapY(),this);
			}
			else {
				g.drawImage(MasseyMon.getMap(picIndex).getMap(), MasseyMon.getMap(picIndex).getMapX() + offsetX, offsetY, this);
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
			checkButtonCollision(mx,my);
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
			if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) && clear(myGuy.getWorldX(), myGuy.getWorldY()-1,myGuy.getWorldX()+19,myGuy.getWorldY()-1)) {
				direction = UP;
				myGuy.move(direction);
				if (checkBuilding(myGuy.getWorldX(), myGuy.getWorldY() - 1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)) {
					mini = true;
					miniPicIndex += 2;

					myGuy.setWorldX(MasseyMon.getMiniMap(miniPicIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMiniMap(miniPicIndex).getStartPosY());
				}
				if (checkBuilding2(myGuy.getWorldX(), myGuy.getWorldY() - 1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)) {
					mini = true;
					miniPicIndex += 1;

					myGuy.setWorldX(MasseyMon.getMiniMap(miniPicIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMiniMap(miniPicIndex).getStartPosY());
				}
				if (checkNextRoute(myGuy.getWorldX(), myGuy.getWorldY() -1,myGuy.getWorldX()+20,myGuy.getWorldY()-1)) {
					picIndex += 1;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
					myGuy.setScreenY(100);
				}

			} else if ((keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) && clear(myGuy.getWorldX(), myGuy.getWorldY() + 27,myGuy.getWorldX()+19,myGuy.getWorldY()+27)) {
				direction = DOWN;
				myGuy.move(direction);

				if (checkExit1(myGuy.getWorldX()-1, myGuy.getWorldY() + 27,myGuy.getWorldX()+20,myGuy.getWorldY()+27)) {
					mini = false;
					miniPicIndex-=2;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX2());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY2());
				}
				if (checkExit2(myGuy.getWorldX()-1, myGuy.getWorldY() + 27, myGuy.getWorldX()+20,myGuy.getWorldY()+27)) {
					mini = false;
					miniPicIndex-=1;
					myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
					myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
				}
			}
			else if ((keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) && clear(myGuy.getWorldX() + 20, myGuy.getWorldY(),myGuy.getWorldX() + 20, myGuy.getWorldY() + 26)) {
				direction = RIGHT;
				myGuy.move(direction);
			}
			else if ((keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) && clear(myGuy.getWorldX() - 1, myGuy.getWorldY(),myGuy.getWorldX() - 1, myGuy.getWorldY() + 26)) {
				direction = LEFT;
				myGuy.move(direction);
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
			maskPic = MasseyMon.getMiniMap(miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(miniPicIndex).getMapY();
		}

		int WALL = 0xFF0000FF;
		if (x < (0 + posX) || x >=  (maskPic.getWidth(null) + posX) || y < (0 + posY) || y >= (maskPic.getHeight(null) + posY)) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y -posY);
		int d = maskPic.getRGB(x2 - posX, y2 -posY);
		return c != WALL && d!=WALL;
	}

	private boolean checkBuilding ( int x, int y,int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(miniPicIndex).getMapY();
		}
		int WALL = 0xFF00FF00;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkExit1 ( int x, int y,int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(miniPicIndex).getMapY();
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
			maskPic = MasseyMon.getMiniMap(miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(miniPicIndex).getMapY();
		}
		int WALL = 0xFF00FFFF;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkBuilding2 ( int x, int y, int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(miniPicIndex).getMapY();
		}
		int WALL = 0xFFFF00FF;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkNextRoute ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(miniPicIndex).getMapY();
		}
		int WALL = 0xFFFFFF00;
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
	public static boolean getOffsetX(){
		return ifOffsetX;
	}
	public static boolean getOffsetY(){
		return ifOffsetY;
	}
}
