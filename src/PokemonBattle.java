import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PokemonBattle {
    private ArrayList<String> myTexts = new ArrayList<String>();
    private int textIndex;
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
    private JTextArea myArea;
    private boolean stopGame, tryingRun;
    private Attack attackUsed;
    public PokemonBattle(ArrayList <Pokemon> myPokes2, ArrayList <Pokemon> enemyPokes2, Player curGuy2) throws IOException {
        myArea = MasseyMon.frame.getTextArea2();
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
            System.out.println(newAtk.getDmg());
            System.out.println(newAtk.getName());
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                myPokes.get(i).learnMove(allAttacks.get(j));
                enemyPokes.get(i).learnMove(allAttacks.get(i));
            }
        }
        textIndex = 0;
        fillTextArray("You are battling PKMN Trainer Ronald!");
        String text = String.format("The enemy trainer sent out %s!",enemyPokes.get(0).getName());
        fillTextArray(text);
        String text2 = String.format("You sent out %s!",myPokes.get(0).getName());
        fillTextArray(text2);
        String text3 = String.format("What will %s do?",myPokes.get(0).getName());
        fillTextArray(text3);
        System.out.println(myTexts.size());
        myArea.setText(myTexts.get(textIndex));
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
        stopGame = true;
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
        if (index == -1 && enemyCurPoke.getHP() <= 0){
            for (Pokemon poke : enemyPokes) {
                if (poke.getHP() > 0) {
                    index = enemyPokes.indexOf(poke);
                }
            }
            if (index == -1){
                String text = String.format("The enemy %s fainted! You win!",enemyCurPoke.getName());
                myTexts.add(text);
                textIndex++;
                myArea.setText(myTexts.get(textIndex));
                tryingRun = true;
            }
            else{
                Pokemon curPoke = enemyPokes.get(0);
                Pokemon switchPoke = enemyPokes.get(index);
                enemyPokes.set(0, switchPoke);
                enemyPokes.set(index, curPoke);
            }
        }
        else if (index == -1) {
            AIAttack(myPokes.get(0));
        }
        else {
            Pokemon curPoke = enemyPokes.get(0);
            Pokemon switchPoke = enemyPokes.get(index);
            enemyPokes.set(0, switchPoke);
            enemyPokes.set(index, curPoke);
        }
    }
    public boolean switchable(){
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
        if (index == -1){
            return false;
        }
        return true;
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
        return mouse;
    }
    public boolean getStopGame(){
        return stopGame;
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
        else if (getChoice().equals("pokemon") || getDead() && getStopGame() == false) {
            for (int i = 0; i < 6; i++) {
                if (switchPokeRects[i].contains(mouse)) {
                    if (i != 0) {
                        if (getMyPokes().get(i).getHP() > 0) {
                            indexC = i;
                            if (getDead()){
                                pokeSwitch(indexC);
                                String text = String.format("What will %s do?",myCurPoke.getName());
                                fillTextArray(text);
                                textIndex++;
                                myArea.setText(myTexts.get(textIndex));
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
                    myTexts.add(String.format("What will %s do?",myCurPoke.getName()));
                    textIndex++;
                    myArea.setText(myTexts.get(textIndex));
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
                myTexts.add(String.format("What will %s do?",myCurPoke.getName()));
                textIndex++;
                myArea.setText(myTexts.get(textIndex));
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
                myTexts.add(String.format("What attack will %s use?",myCurPoke.getName()));
                textIndex++;
                myArea.setText(myTexts.get(textIndex));
            } else if (bagButton.contains(mouse)) {
                setChoice("bag");
            } else if (pokeButton.contains(mouse)) {
                setChoice("pokemon");
            } else if (runButton.contains(mouse)) {
                setChoice("run");
            }
        }
    }
    public boolean isLastAlive(){
        boolean last = false;
        int count = 0;
        for (Pokemon item: enemyPokes){
            if (item.getHP() > 0){
                count++;
            }
        }
        if (count == 1){
            last = true;
        }
        return last;
    }
    public void fillTextArray(String s){
        myTexts.add(s);
        stopGame = true;
    }
    public void AITurn(Pokemon enemyPoke) {
        if (isBad() && isLastAlive() == false && switchable()) {
            AISwitch();
            String text = String.format("The Enemy Trainer switched out to %s",enemyPokes.get(0).getName());
            fillTextArray(text);
        }
        else {
            if ((float) enemyPoke.getHP() / (float) enemyPoke.getMaxHP() <= 0.20 && enemyPoke.getHealed() == false) {
                enemyPoke.heal();
                String text = String.format("The Enemy Trainer used a Max Potion!",enemyPokes.get(0).getName());
                fillTextArray(text);
            }
            else {
                AIAttack(enemyPoke);
                String text = String.format("The Enemy %s used %s!",enemyPokes.get(0).getName(),getAttack().getName());
                fillTextArray(text);
                fillTextArray(enemyCurPoke.getEffect());
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
        setAttack(enemyAttacks.get(index));
        enemyPoke.doAttack(enemyAttacks.get(index), myPoke);
    }
    public Attack getAttack(){
        return attackUsed;
    }
    public void setAttack(Attack atk){
        attackUsed = atk;
    }
    public void pokeSwitch(int i) {
        Pokemon curPoke = myPokes.get(0);
        Pokemon switchPoke = myPokes.get(i);
        myPokes.set(0, switchPoke);
        myPokes.set(i, curPoke);
        myCurPoke = myPokes.get(0);
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
        if (choice.equals("pokemon") || getDead() && stopGame == false){
            myArea.setVisible(false);
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
            myArea.setVisible(true);
            g.setFont(gameFont);
            g.drawString("Fight", 562, 660);
            g.drawString("Bag", 805, 660);
            g.drawString("Pokemon", 542, 747);
            g.drawString("Run", 805, 747);
        }
        else if (choice.equals("fight")) {
            myArea.setVisible(true);
            myCurPoke.drawMoves(g);
        }
        else if (choice.equals("bag")) {
            myArea.setVisible(false);
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
    public void goNext(){
        if (textIndex+1 < myTexts.size()){
            textIndex++;
            myArea.setText(myTexts.get(textIndex));
        }
        if (textIndex+1 == myTexts.size()){
            stopGame = false;
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
            String text = "You ran away!";
            fillTextArray(text);
            tryingRun = true;
        }
        else {
            String text = "You couldn't run away!";
            fillTextArray(text);
        }
    }
    public void myTurnAttack() {
        Pokemon enemyPoke = enemyPokes.get(0);
        myPokes.get(0).doAttack(attackC,enemyPoke);
        String text = String.format("%s used %s!",myCurPoke.getName(),attackC.getName());
        fillTextArray(text);
        fillTextArray(myCurPoke.getEffect());
    }
    public void setString(String s){
        text = s;
    }
    public void myTurnSwitch() {
        pokeSwitch(indexC);
        String text = String.format("You switched out into %s!",myCurPoke.getName());
        fillTextArray(text);
    }
    public void myTurnBag(){
        myItems.use(myCurPoke,itemC);
        String text = String.format("You used a %s!",curGuy.getItems().getUsed());
        fillTextArray(text);
    }
    public void Start(Graphics g){
        enemyCurPoke = enemyPokes.get(0);
        myCurPoke = myPokes.get(0);
        if (doneTurn) {
            if (enemyCurPoke.getSpeed() > myCurPoke.getSpeed()) {
                AITurn(enemyCurPoke);
                draw(g);
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
                }
                if (enemyCurPoke.getHP() > 0){
                    AITurn(enemyCurPoke);
                }
            }
            if (myCurPoke.getHP() <= 0){
                setDead(true);
                String text = String.format("%s fainted!",myCurPoke.getName());
                fillTextArray(text);
            }
            if (enemyCurPoke.getHP() <= 0){
                upgradeTeam();
                AISwitch();
                String text = String.format("The enemy %s fainted!",enemyCurPoke.getName());
                fillTextArray(text);
            }
            doneTurn = false;
            if (myCurPoke.getHP() > 0){
                String text = String.format("What will %s do?",myCurPoke.getName());
                fillTextArray(text);
                textIndex++;
                myArea.setText(myTexts.get(textIndex));
            }
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
