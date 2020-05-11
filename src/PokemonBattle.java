import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PokemonBattle {
    TypeChart myChart = new TypeChart();
    public static ArrayList<Pokemon> allPokemon = new ArrayList<Pokemon>();
    public static ArrayList<Attack> allAttacks = new ArrayList<Attack>();
    public static ArrayList<Pokemon> myPokes = new ArrayList<Pokemon>();
    public static ArrayList<Pokemon> enemyPokes = new ArrayList<Pokemon>();
    private Rectangle fightButton, bagButton, pokeButton, runButton, myPokeHealth, enemyPokeHealth, backArrowRect;
    private Image pokeArenaBack, pokeBox, backArrow, itemMenu, switchBackground;
    private Font gameFont, smallerGameFont, switchFont;
    private ArrayList<Rectangle> rectButtons;
    private Rectangle[] switchPokeRects = new Rectangle[6];
    private boolean fleeable, cFight, cPokes, cBag, cRun, doneTurn;
    private Attack attackC;
    private int indexC;
    private String choice, text;
    private int HPRectWidths;
    private boolean dead;
    private Pokemon myCurPoke, enemyCurPoke;
    public PokemonBattle() throws IOException {
        fightButton = new Rectangle(464, 584, 236, 86);
        bagButton = new Rectangle(701, 584, 236, 86);
        pokeButton = new Rectangle(464, 675, 236, 86);
        runButton = new Rectangle(701, 671, 236, 86);
        HPRectWidths = 182;
        backArrowRect = new Rectangle(10, 10, 58, 62);
        dead = false;
        load();
        for (int i = 0; i < 6; i++) {
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
        } catch (IOException | FontFormatException e) {
        }
    }
    public void setDoneTurn(boolean d){
        doneTurn = d;
    }
    public String getChoice() {
        return choice;
    }

    public void setChoice(String s) {
        choice = s;
    }

    public boolean getDead() {
        return dead;
    }

    public void setDead(boolean d) {
        dead = d;
    }

    public ArrayList<Pokemon> getAllPokes() {
        return allPokemon;
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

    public boolean isFleeable() {
        return fleeable;
    }

    public void AISwitch() {
        myChart = new TypeChart();
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
            System.out.println("switched to a different pokemon");
        }
    }

    public Point getMousePosition2() {
        Point mouse2 = MasseyMon.frame.getMousePosition();
        if (mouse2 == null) {
            mouse2 = new Point(0, 0);
        }
        return mouse2;
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
                                System.out.println("hi");
                            }
                            else{
                                cPokes = true;
                                setChoice("none");
                                doneTurn = true;
                                System.out.println("pokemon");
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
            Player.drawItems(g);
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

    public void myTurn() {
        if (fleeable) {
            MasseyMon.inBattle = false;
        } else {
            System.out.println("You couldn't run away");
        }
    }

    public void myTurn(Attack atk) {
        Pokemon enemyPoke = enemyPokes.get(0);
        myPokes.get(0).doAttack(atk, enemyPoke);
    }

    public void myTurn(int i) {
        pokeSwitch(i);
    }

    public void Start(Graphics g) {
        enemyCurPoke = enemyPokes.get(0);
        myCurPoke = myPokes.get(0);
        if (doneTurn) {
            if (enemyCurPoke.getSpeed() > myCurPoke.getSpeed()) {
                AITurn(enemyCurPoke);//pokemon should do this
                if (myCurPoke.getHP() > 0){
                    if (cFight) {
                        myTurn(attackC);
                        cFight = false;
                    } else if (cPokes) {
                        myTurn(indexC);
                        cPokes = false;
                    } else if (cRun) {
                        myTurn();
                        cRun = false;
                    }
                }
                else{
                    setDead(true);
                }
            }
            else {
                if (cFight) {
                    myTurn(attackC);
                    cFight = false;
                } else if (cPokes) {
                    myTurn(indexC);
                    cPokes = false;
                } else if (cRun) {
                    myTurn();
                    cRun = false;
                }
                if (enemyCurPoke.getHP() > 0){
                    AITurn(enemyCurPoke);
                }
                else{
                    AISwitch();
                }
            }
            doneTurn = false;
        }
        draw(g);
        if (battleOver().equals("") == false){
            MasseyMon.inBattle = false;
        }
    }
}
