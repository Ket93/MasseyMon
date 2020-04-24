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
		load();
		loadImages();
		inBattle = true;
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
	private int mx,my;
	private boolean pokemon;
	private boolean bag;
	private boolean menu;
	private int direction;
	public boolean ready = true;
	private Image back, oakLab,pokeArenaBack;
	private BufferedImage mask, oakLabMask;
	private String choice;
	private boolean[] keys;
	private Image[] battleSprites;
	private Rectangle fightButton,bagButton,pokeButton,runButton;
	private ArrayList<Rectangle> rectButtons;
	private Font gameFont,smallerGameFont;
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
			}
			catch (IOException e) {
			}
		}
		backgroundMasks = new BufferedImage[4];
		fightButton = new Rectangle(464,584,236,86);
		bagButton = new Rectangle(701,584,236,86);
		pokeButton = new Rectangle(464,671,236,86);
		runButton = new Rectangle(701,671,236,86);
		rectButtons = new ArrayList<Rectangle>();
		rectButtons.add(fightButton);
        rectButtons.add(bagButton);
        rectButtons.add(pokeButton);
        rectButtons.add(runButton);
        choice = "none";
		for (int i = 0; i < 4; i++) {
			String path = String.format("%s/%s/%s%d%s.png", "Images", "Masks", "Background", i, "Mask");
			try {
				BufferedImage pic = ImageIO.read(new File(path));
				backgroundMasks[i] = pic;
			}
			catch (IOException e) {
			}
		}
		try{
			gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
			gameFont = gameFont.deriveFont(40f);
			smallerGameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
			smallerGameFont = gameFont.deriveFont(35f);
		}
		catch (IOException | FontFormatException e) {
			e.printStackTrace();
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
			//back = ImageIO.read(new File("Images/Towns/palletTown.png"));
			pokeArenaBack = ImageIO.read(new File("Images/Battles/PokeBattle2.jpg"));
			pokeArenaBack = pokeArenaBack.getScaledInstance(945,770,Image.SCALE_SMOOTH);
			//oakLab = ImageIO.read(new File("Images/Buildings/ProfessorOakLab.png"));
			//mask = ImageIO.read(new File("Images/Towns/palletTownMask.png"));
			//oakLabMask = ImageIO.read(new File("Images/Buildings/ProfessorOakLabMask.png"));
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
					attacker.doAttack(attacker.getMoves().get(rectButtons.indexOf(item)));
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
			battleSprites = MasseyMon.frame.getPokeImages(curPokes);
			g.drawImage(battleSprites[0],90,355,null);
			g.drawImage(battleSprites[1],620,175,null);
			g.setColor(Color.black);
			if (choice.equals(("none"))){
				g.setFont(gameFont);
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
			g.drawImage(backgrounds[picIndex], (int) positions[picIndex].getX(), (int) positions[picIndex].getY(), this);
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

