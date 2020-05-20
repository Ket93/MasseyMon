<<<<<<< HEAD
/*
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.io.IOException;
import java.util.*;

public class PokemonBattle extends JFrame implements ActionListener{
    public static ArrayList<Pokemon> myPokes;
    public static ArrayList<Pokemon> enemyPokes;
    private Pokemon curPoke1,curPoke2;
    private boolean fleeable;
    javax.swing.Timer myTimer;
    GamePanel2 game;
    public PokemonBattle(ArrayList < Pokemon > mine, ArrayList < Pokemon > enemy) {
        super("Pokemon Battle");
        curPoke1 = mine.get(0);
        curPoke2 = enemy.get(0);
        myPokes = mine;
        enemyPokes = enemy;
        myTimer = new javax.swing.Timer(10, this);     // trigger every 10 ms
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(956, 795);
        game = new GamePanel2(this);
        add(game);
        setResizable(false);
        setVisible(true);
    }
    public void start () {
        myTimer.start();
    }
    public int[] getPokes(){
        int [] pokeIndexes = new int[2];
        pokeIndexes[0] = curPoke1.getNum();
        pokeIndexes[1] = curPoke2.getNum();
        return pokeIndexes;
    }
    public void actionPerformed (ActionEvent evt){
        game.move();
        game.repaint();
    }
    public boolean getAlive(ArrayList<Pokemon> pokes){
        boolean MPAlive = false;
        for (Pokemon item: pokes){
            if (item.getHP() > 0){
                MPAlive = true;
            }
        }
        return MPAlive;
    }
    public void start(){
        curPoke1 = myPokes.get(0);
        curPoke2 = enemyPokes.get(0);
        while (getAlive(myPokes) == true && getAlive(enemyPokes) == true){
            if (curPoke1.getSpeed() > curPoke2.getSpeed()){
                userTurn();
                AITurn();
            }
            else{
                AITurn();
                userTurn();
            }
        }
    }
    public static void main (String[]arguments) throws IOException {
        MasseyMon.init();
        PokemonBattle frame = new PokemonBattle(myPokes,enemyPokes);
    }
}

class GamePanel2 extends JPanel implements KeyListener{
    private Image[] battleSprites;
    private int boxx,boxy;
    private boolean []keys;
    private Image back;
    private PokemonBattle mainFrame;
    public GamePanel2(PokemonBattle m){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        back = new ImageIcon("Images/Backgrounds/background2.png").getImage();
        back= back.getScaledInstance(956,795,Image.SCALE_SMOOTH);
        mainFrame = m;
        boxx = 170;
        boxy = 170;
        setSize(800,600);
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void move(){
        if(keys[KeyEvent.VK_RIGHT] ){
            boxx += 5;
        }
        if(keys[KeyEvent.VK_LEFT] ){
            boxx -= 5;
        }
        if(keys[KeyEvent.VK_UP] ){
            boxy -= 5;
        }
        if(keys[KeyEvent.VK_DOWN] ){
            boxy += 5;
        }

        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paintComponent(Graphics g){
        g.drawImage(back,0,-15,null);
        battleSprites = MasseyMon.frame.getPokeImages(mainFrame.getPokes());
        g.drawImage(battleSprites[0],50,550,null);
        g.drawImage(battleSprites[1],500,150,null);
    }
}
 */
=======
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PokemonBattle {
    public static boolean fleeable;
    private TypeChart myChart = new TypeChart();
    private Rectangle fightButton, bagButton, pokeButton, runButton, backArrowRect, upArrowRect, downArrowRect;
    private Image pokeArenaBack, pokeBox, backArrow, itemMenu, switchBackground, pokeArrowUp, pokeArrowDown;
    private Font gameFont, smallerGameFont, switchFont, bigFont;
    private ArrayList<Rectangle> rectButtons;
    private Rectangle[] bagRects = new Rectangle[9];
    private Rectangle[] switchPokeRects = new Rectangle[7];
    private boolean cFight, cPokes, cBag, cRun, doneTurn, captured;
    private Attack attackC;
    private int indexC,itemC,level;
    private String choice, text;
    private boolean dead;
    private Player curGuy;
    private Pokemon myCurPoke, enemyCurPoke;
    private ArrayList <Pokemon> allPokemon = new ArrayList<Pokemon>();
    private ArrayList <Pokemon> enemyPokes = new ArrayList<Pokemon>();
    private ArrayList <Pokemon> myPokes = new ArrayList<Pokemon>();
    private ArrayList <Attack> allAttacks = new ArrayList<Attack>();
    private int[] numItems = new int[7];
    private Items myItems;
    public PokemonBattle(ArrayList <Pokemon> myPokes2, ArrayList <Pokemon> enemyPokes2, Player curGuy2) throws IOException {
        myPokes = myPokes2;
        enemyPokes = enemyPokes2;
        curGuy = curGuy2;
        load();
        loadImageFont();
    }
    public void load() throws FileNotFoundException {
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Pokemon2.txt")));
        String dumInp = inFile.nextLine();
        while (inFile.hasNext()) {
            String line = inFile.nextLine();
            Pokemon newPoke = new Pokemon(line);
            allPokemon.add(newPoke);
        }
        for (int i = 0; i < 6; i++) {
            myPokes.add(allPokemon.get(i));
            enemyPokes.add(allPokemon.get(i + 6));
        }
        inFile = new Scanner(new BufferedReader(new FileReader("Data/Moves.txt")));
        while (inFile.hasNext()) {
            String line = inFile.nextLine();
            Attack newAtk = new Attack(line);
            allAttacks.add(newAtk);
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                myPokes.get(i).learnMove(allAttacks.get(j));
                enemyPokes.get(i).learnMove(allAttacks.get(i));
            }
        }
        fightButton = new Rectangle(471, 608, 238, 90);
        bagButton = new Rectangle(710, 608, 238, 90);
        pokeButton = new Rectangle(471, 699, 238, 90);
        runButton = new Rectangle(710, 699, 238, 90);
        upArrowRect = new Rectangle(265,285,49,25);
        downArrowRect = new Rectangle(265,335,49,25);
        backArrowRect = new Rectangle(10, 10, 58, 62);
        rectButtons = new ArrayList<Rectangle>();
        rectButtons.add(fightButton);
        rectButtons.add(bagButton);
        rectButtons.add(pokeButton);
        rectButtons.add(runButton);
        choice = "none";
        fleeable = true;
        for (int i = 0; i < 6; i++) {
            bagRects[i] = new Rectangle(392, 57+80*i, 510, 55);
            switchPokeRects[i] = new Rectangle(143, 20 + 105 * i, 650, 105);
        }
        switchPokeRects[6] = backArrowRect;
        bagRects[6] = upArrowRect;
        bagRects[7] = downArrowRect;
        bagRects[8] = backArrowRect;
        dead = false;
        allPokemon = new ArrayList<Pokemon>();
        allAttacks = new ArrayList<Attack>();
        numItems = curGuy.getNumItems();
        level = 0;
    }
    public void capture(){
        if (myPokes.size() < 6){
            myPokes.add(enemyCurPoke);

        }
    }
    public void loadImageFont(){
        try {
            pokeArenaBack = ImageIO.read(new File("Images/Battles/PokeBattle2.jpg"));
            pokeArenaBack = pokeArenaBack.getScaledInstance(956,800,Image.SCALE_SMOOTH);
            switchBackground = ImageIO.read(new File("Images/Battles/switchBackground.png"));
            pokeBox = ImageIO.read(new File("Images/Battles/pokeBox.png"));
            backArrow = ImageIO.read(new File("Images/Battles/arrow.png"));
            itemMenu = ImageIO.read(new File("Images/Battles/itemMenu.png"));
            pokeArrowDown = ImageIO.read(new File("Images/Battles/pokeArrowDown.png"));
            pokeArrowDown = pokeArrowDown.getScaledInstance(50,25,Image.SCALE_SMOOTH);
            pokeArrowUp = ImageIO.read(new File("Images/Battles/pokeArrowUp.png"));
            pokeArrowUp = pokeArrowUp.getScaledInstance(50,25,Image.SCALE_SMOOTH);
            pokeArenaBack = pokeArenaBack.getScaledInstance(945, 770, Image.SCALE_SMOOTH);
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
            gameFont = gameFont.deriveFont(40f);
            smallerGameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
            smallerGameFont = gameFont.deriveFont(35f);
            switchFont = gameFont.deriveFont(45f);
            bigFont = gameFont.deriveFont(70f);
        }
        catch (IOException | FontFormatException e) { }
    }
    public void setDoneTurn(boolean d){
        doneTurn = d;
    }
    public String getChoice() {
        return choice;
    }
    public TypeChart getMyChart(){
        return myChart;
    }
    public void setChoice(String s) {
        choice = s;
    }
    public ArrayList<Pokemon> getAllPokemon(){
        return allPokemon;
    }
    public boolean getDead() {
        return dead;
    }
    public Player getCurGuy(){
        return curGuy;
    }
    public void setDead(boolean d) {
        dead = d;
    }
    public ArrayList<Pokemon> getMyPokes() {
        return myPokes;
    }
    public ArrayList<Pokemon> getEnemyPokes() {
        return enemyPokes;
    }
    public ArrayList<Attack> getAllAttacks() {
        return allAttacks;
    }
    public void AISwitch() {
        ArrayList<Double> vals = new ArrayList<Double>();
        for (Pokemon poke : enemyPokes) {
            if (poke.getHP() > 0) {
                vals.add(myChart.getPokeEffect(myPokes.get(0), poke));
            }
        }
        double smallest = 4.0;
        int index = -1;
        for (Double val : vals) {
            if (val < smallest) {
                val = smallest;
                index = vals.indexOf(val);
            }
        }
        if (index == -1) {
            if (enemyCurPoke.getHP() > 0) {
                AIAttack(myPokes.get(0));
            }
            else{
                boolean done = false;
                for (Pokemon poke : enemyPokes) {
                    if (poke.getHP() > 0) {
                        if (done == false) {
                            index = enemyPokes.indexOf(poke);
                            System.out.println(poke.getName());
                            done = true;
                        }
                    }
                }
                if (done == false){
                    AIAttack(myPokes.get(0));
                }
                else{
                    Pokemon curPoke = enemyPokes.get(0);
                    Pokemon switchPoke = enemyPokes.get(index);
                    enemyPokes.set(0, switchPoke);
                    enemyPokes.set(index, curPoke);
                }
            }
        }
        else {
            Pokemon curPoke = enemyPokes.get(0);
            Pokemon switchPoke = enemyPokes.get(index);
            enemyPokes.set(0, switchPoke);
            enemyPokes.set(index, curPoke);
        }
    }

    public Point getMousePosition2() {
        Point mouse2 = MasseyMon.frame.getMousePosition();
        Point mouse;
        if (mouse2 == null) {
            mouse = new Point(0, 0);
        }
        else{
            mouse = new Point(mouse2.x-7,mouse2.y-31);
        }
        System.out.println(mouse);
        return mouse;
    }
    public void checkCollision() {
        cFight = false;
        cPokes = false;
        cBag = false;
        cRun = false;
        doneTurn = false;
        Point mouse = getMousePosition2();
        if (getChoice().equals("fight")) {
            for (Rectangle item : rectButtons) {
                if (item.contains(mouse)) {
                    if (myPokes.get(0).getMoves().get(rectButtons.indexOf(item)) != null) {
                        Pokemon atker = myPokes.get(0);
                        attackC = atker.getMoves().get(rectButtons.indexOf(item));
                        cFight = true;
                        setChoice("none");
                        doneTurn = true;
                    }
                }
            }
        }
        else if (getChoice().equals("pokemon") || getDead()) {
            for (int i = 0; i < 6; i++) {
                if (switchPokeRects[i].contains(mouse)) {
                    if (i != 0) {
                        if (getMyPokes().get(i).getHP() > 0) {
                            indexC = i;
                            if (getDead()){
                                pokeSwitch(indexC);
                            }
                            else{
                                cPokes = true;
                                setChoice("none");
                                doneTurn = true;
                            }
                        }
                    }
                }
            }
            if (backArrowRect.contains(mouse)) {
                if (getDead() == false) {
                    setChoice("none");
                }
            }
        }
        else if (getChoice().equals("bag")){
            for (int i = 0; i < 6; i++){
                if (bagRects[i].contains(mouse)){
                    if (numItems[i] > 0){
                        myItems.use(myCurPoke, i+(level*6));
                        cBag = true;
                        itemC = i;
                        setChoice("none");
                        doneTurn = true;
                    }
                }
            }
            if (bagRects[6].contains(mouse)){
                if (level > 0){
                    level--;
                }
            }
            else if(bagRects[7].contains(mouse)){
                if (level < 1){
                    level++;
                }
            }
            else if (backArrowRect.contains(mouse)) {
                setChoice("none");
            }
        }
        else if (getChoice().equals("run")) {
            cRun = true;
            setChoice("none");
            doneTurn = true;
        }
        else if (getChoice().equals("none")) {
            if (fightButton.contains(mouse)) {
                setChoice("fight");
            } else if (bagButton.contains(mouse)) {
                setChoice("bag");
            } else if (pokeButton.contains(mouse)) {
                setChoice("pokemon");
            } else if (runButton.contains(mouse)) {
                setChoice("run");
            }
        }
    }
    public void AITurn(Pokemon enemyPoke) {
        if (isBad()) {
            AISwitch();
        }
        else {
            if ((float) enemyPoke.getHP() / (float) enemyPoke.getMaxHP() <= 0.20 && enemyPoke.getHealed() == false) {
                enemyPoke.heal();
            }
            else {
                AIAttack(enemyPoke);
            }
        }
    }
    public void AIAttack(Pokemon enemyPoke) {
        ArrayList<Attack> enemyAttacks = new ArrayList<Attack>();
        enemyAttacks = getEnemyPokes().get(0).getMoves();
        ArrayList<Double> atkMults = new ArrayList<Double>();
        Pokemon myPoke = getMyPokes().get(0);
        int index = 0;
        double highest = 0.0;
        for (Attack atk : enemyAttacks) {
            double val = myChart.getEffect(atk, myPoke);
            atkMults.add(val);
            if (val > highest) {
                index = enemyAttacks.indexOf(atk);
            }
        }
        enemyPoke.doAttack(enemyAttacks.get(index), myPoke);
    }
    public void pokeSwitch(int i) {
        Pokemon curPoke = myPokes.get(0);
        Pokemon switchPoke = myPokes.get(i);
        myPokes.set(0, switchPoke);
        myPokes.set(i, curPoke);
        if (getDead()) {
            setDead(false);
            setDoneTurn(false);
            setChoice("none");
        }
    }
    public boolean isBad() {
        Pokemon myPoke = myPokes.get(0);
        Pokemon enemyPoke = enemyPokes.get(0);
        double val = myChart.getPokeEffect(myPoke, enemyPoke);
        if (val <= 1.0) {
            return false;
        }
        return true;
    }

    public void draw(Graphics g) {
        Point mouse = getMousePosition2();
        g.drawImage(pokeArenaBack, 0, -5, null);
        myCurPoke.drawGood(g);
        enemyCurPoke.drawBad(g);
        if (choice.equals("pokemon") || getDead()){
            g.drawImage(switchBackground, 0, 0, null);
            g.drawImage(pokeBox, 231, 650, null);
            g.drawImage(backArrow, 10, 10, null);
            g.setColor(Color.BLACK);
            g.setFont(gameFont);
            g.drawString("What Pokemon will you switch to?", 275, 710);
            for (int i = 0; i < myPokes.size(); i++) {
                myPokes.get(i).drawDisplay(g, i);
                g.drawString("HP: ", 273, 100 + 105 * i);
            }
            for (Rectangle item : switchPokeRects) {
                if (item.contains(mouse)) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setStroke(new BasicStroke(3));
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(item.x, item.y, item.width, item.height);
                    g2d.setStroke(new BasicStroke(1));
                }
            }
        }
        else if (choice.equals(("none"))) {
            g.setFont(gameFont);
            g.drawString("Fight", 562, 660);
            g.drawString("Bag", 805, 660);
            g.drawString("Pokemon", 542, 747);
            g.drawString("Run", 805, 747);
        } else if (choice.equals("fight")) {
            myCurPoke.drawMoves(g);
        }
        else if (choice.equals("bag")) {
            g.drawImage(itemMenu, 0, 0, null);
            g.drawImage(backArrow, backArrowRect.x,backArrowRect.y,null);
            g.drawImage(pokeArrowUp, 265,285,null);
            g.drawImage(pokeArrowDown, 265,335,null);
            for (int i = 0+6*level; i < 6+6*level; i++){
                myItems.draw(g,i);
            }
            g.setFont(bigFont);
            g.drawString("BAG",140,87);
            for (Rectangle item : bagRects) {
                if (item.contains(mouse)) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setStroke(new BasicStroke(3));
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(item.x, item.y, item.width, item.height);
                    g2d.setStroke(new BasicStroke(1));
                }
            }
        }
    }

    public String battleOver() {
        boolean myTeamAlive = false;
        for (Pokemon item : myPokes) {
            if (item.getHP() > 0) {
                myTeamAlive = true;
            }
        }
        boolean enemyTeamAlive = false;
        for (Pokemon item : enemyPokes) {
            if (item.getHP() > 0) {
                enemyTeamAlive = true;
            }
        }
        if (myTeamAlive == true) {
            if (enemyTeamAlive == false) {
                return "win";
            } else {
                return "";
            }
        } else {
            return "loss";
        }
    }
    public void myTurnRun() {
        if (fleeable) {
            MasseyMon.inBattle = false;
        } else {
            System.out.println("You couldn't run away");
        }
    }
    public void myTurnAttack() {
        Pokemon enemyPoke = enemyPokes.get(0);
        myPokes.get(0).doAttack(attackC,enemyPoke);
    }
    public void myTurnSwitch() {
        pokeSwitch(indexC);
    }
    public void myTurnBag(){
        myItems.use(myCurPoke,itemC);
    }
    public void Start(Graphics g){
        enemyCurPoke = enemyPokes.get(0);
        myCurPoke = myPokes.get(0);
        if (doneTurn) {
            if (enemyCurPoke.getSpeed() > myCurPoke.getSpeed()) {
                AITurn(enemyCurPoke);
                if (myCurPoke.getHP() > 0){
                    if (cFight) {
                        myTurnAttack();
                        cFight = false;
                    } else if (cPokes) {
                        myTurnSwitch();
                        cPokes = false;
                    } else if (cRun) {
                        myTurnRun();
                        cRun = false;
                    }
                    else if(cBag) {
                        myTurnBag();
                        cBag = false;
                    }
                }
            }
            else {
                if (cFight) {
                    myTurnAttack();
                    cFight = false;
                } else if (cPokes) {
                    myTurnSwitch();
                    cPokes = false;
                } else if (cRun) {
                    myTurnRun();
                    cRun = false;
                }
                else if(cBag){
                    myTurnBag();
                    cBag = false;
                    System.out.println("bagged");
                }
                if (enemyCurPoke.getHP() > 0){
                    AITurn(enemyCurPoke);
                }
            }
            if (myCurPoke.getHP() <= 0){
                setDead(true);
            }
            if (enemyCurPoke.getHP() <= 0){
                upgradeTeam();
                AISwitch();
            }
            doneTurn = false;
        }
        draw(g);
        update();
        if (battleOver().equals("") == false){
            MasseyMon.inBattle = false;
        }
    }
    public void upgradeTeam(){
        for (Pokemon item: myPokes){
            if (item == myCurPoke){
                item.gainXP(enemyCurPoke.getLevel());
            }
            else{
                item.gainXP(enemyCurPoke.getLevel()/2);
            }
        }
    }
    public void update(){
        numItems = curGuy.getNumItems();
        myItems = curGuy.getItems();
    }
}
>>>>>>> 2358c72a9e35d03e984d71a17a0e0f9690129439
