import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PokemonBattle {
    private TypeChart myChart = new TypeChart();
    private Rectangle fightButton, bagButton, pokeButton, runButton, myPokeHealth, enemyPokeHealth, backArrowRect;
    private Image pokeArenaBack, pokeBox, backArrow, itemMenu, switchBackground;
    private Font gameFont, smallerGameFont, switchFont, bigFont;
    private ArrayList<Rectangle> rectButtons;
    private Rectangle[] bagRects = new Rectangle[6];
    private Rectangle[] switchPokeRects = new Rectangle[6];
    private boolean fleeable, cFight, cPokes, cBag, cRun, doneTurn;
    private Attack attackC;
    private int indexC;
    private String choice, text;
    private int itemC;
    private int HPRectWidths;
    private boolean dead;
    private Player curGuy;
    private Pokemon myCurPoke, enemyCurPoke;
    private ArrayList <Pokemon> allPokemon, enemyPokes, myPokes;
    private ArrayList <Attack> allAttacks;
    private int[] numItems = new int[7];
    private Items myItems;
    public PokemonBattle(ArrayList <Pokemon> myPokes2, ArrayList <Pokemon> enemyPokes2, Player curGuy2) throws IOException {
        fightButton = new Rectangle(464, 584, 236, 86);
        bagButton = new Rectangle(701, 584, 236, 86);
        pokeButton = new Rectangle(464, 675, 236, 86);
        runButton = new Rectangle(701, 671, 236, 86);
        HPRectWidths = 182;
        backArrowRect = new Rectangle(10, 10, 58, 62);
        dead = false;
        myPokes = myPokes2;
        enemyPokes = enemyPokes2;
        curGuy = curGuy2;
        curGuy = curGuy2;
        allPokemon = new ArrayList<Pokemon>();
        allAttacks = new ArrayList<Attack>();
        numItems = curGuy.getNumItems();
        load();
        for (int i = 0; i < 6; i++) {
            bagRects[i] = new Rectangle(392, 57+80*i, 510, 55);
            switchPokeRects[i] = new Rectangle(143, 20 + 105 * i, 650, 105);
        }
        rectButtons = new ArrayList<Rectangle>();
        rectButtons.add(fightButton);
        rectButtons.add(bagButton);
        rectButtons.add(pokeButton);
        rectButtons.add(runButton);
        choice = "none";
        try {
            pokeArenaBack = ImageIO.read(new File("Images/Battles/PokeBattle2.jpg"));
            switchBackground = ImageIO.read(new File("Images/Battles/switchBackground.png"));
            pokeBox = ImageIO.read(new File("Images/Battles/pokeBox.png"));
            backArrow = ImageIO.read(new File("Images/Battles/arrow.png"));
            itemMenu = ImageIO.read(new File("Images/Battles/itemMenu.png"));
            pokeArenaBack = pokeArenaBack.getScaledInstance(945, 770, Image.SCALE_SMOOTH);
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
            gameFont = gameFont.deriveFont(40f);
            smallerGameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
            smallerGameFont = gameFont.deriveFont(35f);
            switchFont = gameFont.deriveFont(45f);
            bigFont = gameFont.deriveFont(70f);
        } catch (IOException | FontFormatException e) {
        }
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
                    System.out.println("switched to a different pokemon cause dead");
                }
            }
        }
        else {
            Pokemon curPoke = enemyPokes.get(0);
            Pokemon switchPoke = enemyPokes.get(index);
            enemyPokes.set(0, switchPoke);
            enemyPokes.set(index, curPoke);
            System.out.println(index);
            System.out.println("switched to a different pokemon");
        }
    }

    public Point getMousePosition2() {
        Point mouse2 = MasseyMon.frame.getMousePosition();
        Point mouse;
        if (mouse2 == null) {
            mouse = new Point(0, 0);
        }
        else{
            mouse = new Point(mouse2.x-7,mouse2.y-30);
        }
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
                    System.out.println(numItems[i]);
                    System.out.println(i);
                    if (numItems[i] > 0){
                        myItems.use(myCurPoke, i);
                        cBag = true;
                        itemC = i;
                        setChoice("none");
                        doneTurn = true;
                    }
                }
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
                System.out.println("switched");
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
            if ((float) enemyPoke.getHP() / (float) enemyPoke.getMaxHP() <= 0.20) {
                heal(enemyPoke);
            } else {
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

    public void heal(Pokemon poke) {
        System.out.println("healed" + poke.getName());
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
            System.out.println(allPokemon.get(i + 6).getName());
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
            g.drawString("Fight", 555, 640);
            g.drawString("Bag", 795, 640);
            g.drawString("Pokemon", 535, 726);
            g.drawString("Run", 795, 726);
        } else if (choice.equals("fight")) {
            myCurPoke.drawMoves(g);
        }
        else if (choice.equals("bag")) {
            g.drawImage(itemMenu, 0, 0, null);
            for (int i = 0; i < 6; i++){
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
    public void update(){
        numItems = curGuy.getNumItems();
        myItems = curGuy.getItems();
    }
}
