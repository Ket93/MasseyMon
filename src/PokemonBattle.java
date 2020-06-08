//Dimitrios Christopoulos
//PokemonBattle.java
//This class is responsible for the simulation of a pokemon battle. There are 2 arraylists of pokemon, one is the users' and one is controlled by the AI. if all your pokemon run out of hp in a battle you lose and your position
//in the world is reset to your house, adn if you win you get xp, money and possibly progress the story
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PokemonBattle {
    private ArrayList<String> myTexts = new ArrayList<String>();//arraylist that is responsible for displaying text
    private ArrayList<Pokemon> evolutions = new ArrayList<Pokemon>();//an arraylist that keeps track of which pokemon are supposed to evolve at the end of a battle, if any
    private int textIndex;//an index used to keep track of what text is supposed to be showing
    private boolean fleeable;//boolean used to determine whether or not you can run from a battle
    private TypeChart myChart = new TypeChart();//a type chart that is minaly used by the AI
    private Rectangle fightButton, bagButton, pokeButton, runButton, backArrowRect, upArrowRect, downArrowRect;//buttons to determine what the user is doing
    private Image pokeArenaBack, pokeBox, backArrow, itemMenu, switchBackground, pokeArrowUp, pokeArrowDown;//images that correspond with the buttons
    private Font gameFont, smallerGameFont, switchFont, bigFont;//different size fonts
    private ArrayList<Rectangle> rectButtons;//an arraylist of the rect buttons above
    private Rectangle[] bagRects = new Rectangle[9];//an array of rectangels that are used while in the "bag" menu
    private Rectangle[] switchPokeRects = new Rectangle[7];//an array of rectangels that are used in the "switch pokemon" menu
    private boolean cFight, cPokes, cBag, cRun, doneTurn;//booleans used to determine what the player is doing on a giver turn and if a turn is over
    private Attack attackC;//choices that correspond with the user's action
    private int indexC,itemC,level;
    private String choice, text;
    private boolean dead,learningNewMove, stopGame,battleOver2;//booleans that help stop and start the game according to what is going on
    private Player curGuy;//the player
    private Pokemon myCurPoke, enemyCurPoke;
    private ArrayList <Pokemon> allPokemon = new ArrayList<Pokemon>();//all pokemon that loaded
    private ArrayList <Pokemon> enemyPokes = new ArrayList<Pokemon>();//the enemy pokemon
    private ArrayList <Pokemon> myPokes = new ArrayList<Pokemon>();//the user's pokemon
    private ArrayList <Attack> allAttacks = new ArrayList<Attack>();//all attacks that are loaded
    private int[] numItems = new int[7];
    private Items myItems;//the palyer's items
    private JTextArea myArea;//a JTextArea that shows text
    private Attack attackUsed, moveLearning;
    private boolean choosing, waiting, waitingEnd;
    private Pokemon pokeC;
    private Sound pressingSound;//a sound that is played when the user clicks their mosue button 1 or presses space
    public PokemonBattle(ArrayList <Pokemon> myPokes2, ArrayList <Pokemon> enemyPokes2, Player curGuy2) throws IOException {
        myArea = MasseyMon.frame.getTextArea2();
        myPokes = myPokes2;
        enemyPokes = enemyPokes2;
        curGuy = curGuy2;
        load();
        loadImageFont();
    }
    public void load() throws FileNotFoundException {//this method loads most of the variables
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Pokemon2.txt")));
        String dumInp = inFile.nextLine();
        while (inFile.hasNext()) {//loading all Pokemon
            String line = inFile.nextLine();
            Pokemon newPoke = new Pokemon(line);
            allPokemon.add(newPoke);
        }
        inFile.close();
        inFile = new Scanner(new BufferedReader(new FileReader("Data/Moves.txt")));
        while (inFile.hasNext()) {//loading all Attacks
            String line = inFile.nextLine();
            Attack newAtk = new Attack(line);
            allAttacks.add(newAtk);
        }
        inFile.close();
        fightButton = new Rectangle(471, 608, 238, 90);//making the buttons
        bagButton = new Rectangle(710, 608, 238, 90);
        pokeButton = new Rectangle(471, 699, 238, 90);
        runButton = new Rectangle(710, 699, 238, 90);
        upArrowRect = new Rectangle(265,285,49,25);
        downArrowRect = new Rectangle(265,335,49,25);
        backArrowRect = new Rectangle(10, 10, 58, 62);
        rectButtons = new ArrayList<Rectangle>();
        rectButtons.add(fightButton);//adding the rects to an array
        rectButtons.add(bagButton);
        rectButtons.add(pokeButton);
        rectButtons.add(runButton);
        pressingSound = new Sound("Music/Battle/pressingSound.wav",75);//sound that is made when the player does an action
        stopGame = true;//this variable controls whether or not the player needs to read text or make decisions in the battle, if they ahve to read text then they cant make decisions
        fleeable = MasseyMon.frame.getFleeable();//calling a method that determines if they are able to run from the battle based off of who they are battling
        textIndex = 0;
        myTexts = new ArrayList<String>();
        if (!fleeable){//if you are fighting a trainer, display this text
            fillTextArray("You have been challenged by an enemy PKMN Trainer!");
            String text = String.format("The enemy trainer sent out %s!",enemyPokes.get(0).getName());
            fillTextArray(text);
        }
        else{//if it is a wild encoutner, display htis
            String text = String.format("A wild %s appeared!",enemyPokes.get(0).getName());
            fillTextArray(text);
        }
        String text2 = String.format("You sent out %s!",myPokes.get(0).getName());//showing your pokemon
        fillTextArray(text2);
        String text3 = String.format("What will %s do?",myPokes.get(0).getName());
        fillTextArray(text3);
        myArea.setText(myTexts.get(0));//setting variables
        choice = "none";
        battleOver2 = false;
        waiting = false;
        waitingEnd = false;
        learningNewMove = false;
        for (int i = 0; i < 6; i++) {
            bagRects[i] = new Rectangle(392, 57+80*i, 510, 55);
            switchPokeRects[i] = new Rectangle(143, 20 + 105 * i, 650, 105);
        }
        switchPokeRects[6] = backArrowRect;//assigning buttons
        bagRects[6] = upArrowRect;
        bagRects[7] = downArrowRect;
        bagRects[8] = backArrowRect;
        dead = false;
        allPokemon = new ArrayList<Pokemon>();
        allAttacks = new ArrayList<Attack>();
        numItems = curGuy.getNumItems();
        level = 0;
    }
    public void loadImageFont(){//this method loads the imags and fonts
        try {
            pokeArenaBack = ImageIO.read(new File("Images/Battles/PokeBattle2.jpg"));
            pokeArenaBack = pokeArenaBack.getScaledInstance(956,800,Image.SCALE_SMOOTH);//background pic
            switchBackground = ImageIO.read(new File("Images/Battles/switchBackground.png"));//various arrows and pictures of buttons
            pokeBox = ImageIO.read(new File("Images/Battles/pokeBox.png"));
            backArrow = ImageIO.read(new File("Images/Battles/arrow.png"));
            itemMenu = ImageIO.read(new File("Images/Battles/itemMenu.png"));
            pokeArrowDown = ImageIO.read(new File("Images/Battles/pokeArrowDown.png"));
            pokeArrowDown = pokeArrowDown.getScaledInstance(50,25,Image.SCALE_SMOOTH);
            pokeArrowUp = ImageIO.read(new File("Images/Battles/pokeArrowUp.png"));
            pokeArrowUp = pokeArrowUp.getScaledInstance(50,25,Image.SCALE_SMOOTH);
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));//different size fonts
            gameFont = gameFont.deriveFont(40f);
            smallerGameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
            smallerGameFont = gameFont.deriveFont(35f);
            switchFont = gameFont.deriveFont(45f);
            bigFont = gameFont.deriveFont(70f);
        }
        catch (IOException | FontFormatException e) { }
    }
    public void setDoneTurn(boolean d){//setter method that determines if the round will start
        doneTurn = d;
    }
    public String getChoice() {//getter method for what the user is doing currently
        return choice;
    }
    public void setChoice(String s) {//setter method used to update the game based on the player's actions
        choice = s;
    }
    public boolean getDead() {//getter method for whether or not the user's pokemon are all dead
        return dead;
    }
    public void setDead(boolean d) {//setter method for above
        dead = d;
    }
    public ArrayList<Pokemon> getMyPokes() {//getter method for enemy pokes
        return myPokes;
    }
    public ArrayList<Pokemon> getEnemyPokes() {//getter method for enemy pokes
        return enemyPokes;
    }
    public void AISwitch() {//this method makes the AI switch pokemon, usually due to an unfavorable type matchup
        ArrayList<Double> vals = new ArrayList<Double>();//keeping track of all possibilities
        for (Pokemon poke : enemyPokes) {
            if (poke.getHP() > 0) {
                vals.add(myChart.getPokeEffect(myPokes.get(0), poke));//adding the mto the arraylist
            }
        }
        double smallest = 4.0;
        int index = -1;
        for (Double val : vals) {
            if (val < smallest) {
                val = smallest;
                index = vals.indexOf(val);//finding the best scenario
            }
        }
        if (index == -1 && enemyCurPoke.getHP() <= 0){//if all matchups are bad and the AI must switch because the curent pokemon is dead
            for (Pokemon poke : enemyPokes) {
                if (poke.getHP() > 0) {
                    index = enemyPokes.indexOf(poke);//switch to the first one available
                }
            }
            if (index == -1){//if all the enemy pokemon have fainted
                String text = String.format("The enemy %s fainted! You win!",enemyCurPoke.getName());//displaying text saying that you won the battle
                myTexts.add(text);//add the text
                textIndex++;//increase the index
                myArea.setText(myTexts.get(textIndex));//display the text
            }
            else{
                Pokemon curPoke = enemyPokes.get(0);//this part just switches the current enemy with a pokemon on the other team
                Pokemon switchPoke = enemyPokes.get(index);
                enemyPokes.set(0, switchPoke);
                enemyPokes.set(index, curPoke);
                enemyCurPoke = enemyPokes.get(0);
            }
        }
        else if (index == -1) {//if it is a bad matchup but the pokemon cant switch
            AIAttack(myPokes.get(0));//attack
        }
        else {
            Pokemon curPoke = enemyPokes.get(0);//if it is a normal switch scenario
            Pokemon switchPoke = enemyPokes.get(index);
            enemyPokes.set(0, switchPoke);//switch
            enemyPokes.set(index, curPoke);
        }
    }
    public ArrayList<Pokemon> getEvolutions(){//getter method for the pokemon that will evolve at the end of the battle
        return evolutions;
    }
    public boolean getChoosing(){//getter method for if the user is choosing an item or pokemon
        return choosing;
    }
    public boolean switchable(){//determining whether or not it is possible for the enemy to swtich out their current pokemon
        ArrayList<Double> vals = new ArrayList<Double>();//this is similar to the other method called "AISwitch", it just returns a boolean instead of switching the pokemon
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
    public Point getMousePosition2() {//a string of getter methods used to get the mouse position from game panel
        Point mouse2 = MasseyMon.frame.getMousePosition();
        Point mouse;
        if (mouse2 == null) {//if its off the screen
            mouse = new Point(0, 0);//make it at 0,0
        }
        else{
            mouse = new Point(mouse2.x-7,mouse2.y-31);//some offsets are needed
        }
        return mouse;//return the mouse position
    }
    public void setChoosing(boolean c){
        choosing = c;
    }
    public boolean getStopGame(){//getter method to see if the game should be paused
        return stopGame;
    }
    public void checkCollision() {//method that checks collision with buttons
        cFight = false;//booleans that keep track of what the user is going to do that turn
        cPokes = false;
        cBag = false;
        cRun = false;
        doneTurn = false;
        Point mouse = getMousePosition2();//getting the mosue
        if (getChoice().equals("fight")) {//if they click while in the "fight" menu
            for (Rectangle item : rectButtons) {
                if (item.contains(mouse)) {
                    if (!learningNewMove){//if they want to use an attack
                        if (rectButtons.indexOf(item) < myPokes.get(0).getMoves().size()) {//find the index of the attack
                            Pokemon atker = myPokes.get(0);//set your current pokemon as the attacker
                            attackC = atker.getMoves().get(rectButtons.indexOf(item));//set the chosen attack
                            if (attackC.getPP() > 0){//if they can use the attack
                                cFight = true;//make their decision "fight"
                                setChoice("none");
                                doneTurn = true;
                                pressingSound.play();//play a sound
                            }
                        }
                    }
                    else{
                        ArrayList<Attack> curAttacks = new ArrayList<Attack>();//if they are learning a new move
                        curAttacks = myCurPoke.getMoves();// the current attacks that the pokemon has
                        curAttacks.set(rectButtons.indexOf(item),moveLearning);// get rid of the old attack and replace it with the noew one
                        learningNewMove = false;// not learning a new move
                        String text = String.format("%s learned %s",myCurPoke.getName(),moveLearning.getName());//display text
                        fillTextArray(text);//display the appropriate text
                    }
                }
            }
        }
        else if (getChoice().equals("pokemon") || getDead() && getStopGame() == false) {//if theyre switching pokemon
            for (int i = 0; i < 6; i++) {
                if (switchPokeRects[i].contains(mouse)) {// if they clicked on a button
                    if (i != 0) {
                        if (getMyPokes().size() > i){
                            if (getMyPokes().get(i).getHP() > 0) {
                                indexC = i;//find the index
                                if (getDead()){//if theyre dead
                                    pokeSwitch(indexC);//switch the pokemon immediately
                                    String text = String.format("What will %s do?",myCurPoke.getName());
                                    fillTextArray(text);
                                    textIndex++;
                                    myArea.setText(myTexts.get(textIndex));
                                }
                                else{//if theyre not
                                    cPokes = true;
                                    setChoice("none");
                                    doneTurn = true;//do it during the turn
                                }
                                pressingSound.play();//play a sound
                            }
                        }
                    }
                }
            }
            if (backArrowRect.contains(mouse)) {//if they want to go back
                if (getDead() == false) {// if theyre not dead
                    setChoice("none");//reset the choice
                    myTexts.add(String.format("What will %s do?",myCurPoke.getName()));//add text
                    textIndex++;
                    myArea.setText(myTexts.get(textIndex));
                    pressingSound.play();
                }
            }
        }
        else if (getChoice().equals("bag")){//if they want to use an item
            if (getChoosing() == false){//if heyre not choosing which pokemon to use it on
                for (int i = 0; i < 6; i++){
                    if (bagRects[i].contains(mouse)){//if they clicked a button
                        if (numItems[i] > 0){
                            itemC = i+6*level;
                            if (itemC > 6 && itemC != 11){
                                if (fleeable){
                                    cBag = true;//mark that theyr using the bag
                                    setChoice("none");//reset choice
                                    pokeC = myCurPoke;
                                    doneTurn = true;//this is their choice of action for the turn
                                    pressingSound.play();//paly sound
                                }
                            }
                            else{
                                setChoosing(true);//reset choice
                                pressingSound.play();//play the sound
                            }
                        }
                    }
                }
                if (bagRects[6].contains(mouse)){//if theyre scrollin through the options
                    if (level == 1){// if they can
                        level--;//go down a level
                        pressingSound.play();//play the osund
                    }
                }
                else if(bagRects[7].contains(mouse)){//same as above
                    if (level == 0){
                        level++;
                        pressingSound.play();
                    }
                }
                else if (backArrowRect.contains(mouse)) {//if they want to go back
                    setChoice("none");//reset their choice
                    myTexts.add(String.format("What will %s do?",myCurPoke.getName()));
                    textIndex++;
                    myArea.setText(myTexts.get(textIndex));
                    pressingSound.play();
                }
            }
            else{//if theya re choosing which pokemon to use it on
                for (int i = 0; i < 6; i++) {
                    if (switchPokeRects[i].contains(mouse)) {//if htey clicked abutton
                        if (getMyPokes().size() > i){
                            Pokemon myPoke = getMyPokes().get(i);//mark the pokemon
                            int myPokeHP = getMyPokes().get(i).getHP();
                            int myPokeMaxHP = getMyPokes().get(i).getMaxHP();
                            if (itemC >= 0 && itemC <= 4){//potions
                                if (myPokeHP > 0 && myPokeHP < myPokeMaxHP){
                                    pokeC = myPoke;
                                    cBag = true;//reset the choice and use the item on that pokemon during the turn
                                    doneTurn = true;
                                    setChoice("none");
                                    setChoosing(false);
                                    pressingSound.play();
                                }
                            }
                            else if(itemC == 5 || itemC == 6){//if its a revive
                                if (myPokeHP <= 0){//check if your pokemon is dead
                                    pokeC = myPoke;
                                    cBag = true;
                                    doneTurn = true;
                                    setChoice("none");//reset choice
                                    setChoosing(false);
                                    pressingSound.play();
                                }
                            }
                        }
                    }
                }
                if (backArrowRect.contains(mouse)) {//if they want to go back
                    setChoosing(false);//reset
                    pressingSound.play();
                }
            }
        }
        else if (getChoice().equals("run")) {//if they want to run
            cRun = true;//try to run
            setChoice("none");//reset choice
            doneTurn = true;
            pressingSound.play();
        }
        else if (getChoice().equals("none")) {//if theyre at the start of the decision
            if (fightButton.contains(mouse)) {//depending on the button they choose, change their decision
                setChoice("fight");
                myTexts.add(String.format("What attack will %s use?",myCurPoke.getName()));//display text accordingly
                textIndex++;
                myArea.setText(myTexts.get(textIndex));
                pressingSound.play();
            } else if (bagButton.contains(mouse)) {
                setChoice("bag");
                pressingSound.play();
            } else if (pokeButton.contains(mouse)) {
                setChoice("pokemon");
                pressingSound.play();
            } else if (runButton.contains(mouse)) {
                pressingSound.play();
                myArea.setVisible(false);
                cRun = true;
            }
        }
    }
    public void setWaitingEnd(boolean b){//setter method for the boolean that determines if the battle is almost
        waitingEnd = b;
    }
    public boolean isLastAlive(){//method that returns if the current enemy pokemon is the last one alive
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
    public void dontLearnMove(){//if they press backspace while trying to learn a new move, they dont learn anything
        setChoice("none");
        learningNewMove = false;
    }
    public void fillTextArray(String s){//method that adds text to a queue to be displayed and pauses the battle until the player reads all the text
        if (learningNewMove == false){
            myTexts.add(s);
            stopGame = true;
        }
    }
    public void fillTextArraySpecial(String s){//another version of this that bypasses a restriction
        myTexts.add(s);
        stopGame = true;
    }
    public void setMoveLearning(Attack atk){//keeping track of an attack that your pokemon is currently trying to learn
        moveLearning = atk;
    }
    public void AITurn(Pokemon enemyPoke) {//method that takes care of the ai's turn
        if (isBad() && isLastAlive() == false && switchable()) {//if the matchup is bad and theya rent hte last one alive
            AISwitch();//siwtch pokemon
            String text = String.format("The Enemy Trainer switched out to %s",enemyPokes.get(0).getName());//show text
            fillTextArray(text);
        }
        else {//checking if the enemy pokemon needs to be healed, they can only be healed once
            if ((float) enemyPoke.getHP() / (float) enemyPoke.getMaxHP() <= 0.20 && enemyPoke.getHealed() == false && fleeable == false) {//getting a ercent of heir max hp, if it is below a certain point, heal them
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
    public void AIAttack(Pokemon enemyPoke) {//method that makes the ai attack
        ArrayList<Attack> enemyAttacks = new ArrayList<Attack>();
        enemyAttacks = getEnemyPokes().get(0).getMoves();
        ArrayList<Double> atkMults = new ArrayList<Double>();
        Pokemon myPoke = getMyPokes().get(0);
        int index = 0;
        double highest = 0.0;
        for (Attack atk : enemyAttacks) {
            if (!(atk == null)){
                double val = myChart.getEffect(atk, myPoke);//finds the attack that has the best type matchup and uses it
                atkMults.add(val);
                if (val > highest) {
                    index = enemyAttacks.indexOf(atk);
                }
            }
        }
        setAttack(enemyAttacks.get(index));
        enemyPoke.doAttack(enemyAttacks.get(index), myPoke);
    }
    public Attack getAttack(){//getter method that returns attack
        return attackUsed;
    }
    public void setAttack(Attack atk){//setter method for attack
        attackUsed = atk;
    }
    public void pokeSwitch(int i) {//method that switches your pokemon
        Pokemon curPoke = myPokes.get(0);//select your current pokemon
        Pokemon switchPoke = myPokes.get(i);//select  the pokemon that you want to swtich to
        myPokes.set(0, switchPoke);//switc them
        myPokes.set(i, curPoke);
        myCurPoke = myPokes.get(0);
        if (getDead()) {//if youre dead
            setDead(false);//you switched, so youre no longer dead
            setDoneTurn(false);//reset options
            setChoice("none");
        }
    }
    public boolean isBad() {//method that returns whether or not the type matchup is bad for the ai
        Pokemon myPoke = myPokes.get(0);
        Pokemon enemyPoke = enemyPokes.get(0);
        double val = myChart.getPokeEffect(myPoke, enemyPoke);
        if (val <= 1.0) {
            return false;
        }
        return true;
    }

    public void draw(Graphics g) {//method that draws pokemon battle
        Point mouse = getMousePosition2();//get mouse
        g.drawImage(pokeArenaBack, 0, -5, null);//draw background
        myCurPoke.drawGood(g);//drawing your pokemon
        if (!waiting && !learningNewMove){//if the game isnt supposed to be paused and the pokemon is not learning a new move,
            enemyCurPoke.drawBad(g);//draw the enemy pokemon;
        }
        g.setColor(Color.BLACK);//seting color
        if (choice.equals("pokemon") || getDead() && stopGame == false){//if youre trying to swtich pokemon and youre dead,
            myArea.setVisible(false);//make the jtextarea invisible
            g.drawImage(switchBackground, 0, 0, null);//draw the boxes and backgrounds
            g.drawImage(pokeBox, 231, 650, null);
            g.drawImage(backArrow, 10, 10, null);
            g.setColor(Color.BLACK);
            g.setFont(gameFont);
            g.drawString("What Pokemon will you switch to?", 275, 710);
            for (int i = 0; i < myPokes.size(); i++) {
                myPokes.get(i).drawDisplay(g, i);
                g.drawString("HP: ", 273, 100 + 105 * i);
            }
            for (Rectangle item : switchPokeRects) {//go through the poke rects
                if (item.contains(mouse)) {//if they ar ehovering over it
                    Graphics2D g2d = (Graphics2D) g;//draw an outline
                    g2d.setStroke(new BasicStroke(3));
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(item.x, item.y, item.width, item.height);
                    g2d.setStroke(new BasicStroke(1));
                }
            }
        }
        else if (choice.equals(("none"))) {//if hteyr enot hoosing anything
            myArea.setVisible(true);//make the jtextarea ture
            g.setFont(gameFont);//set the font to a certain font
            g.drawString("Fight", 562, 660);//draw the strings that show your options
            g.drawString("Bag", 805, 660);
            g.drawString("Pokemon", 542, 747);
            g.drawString("Run", 805, 747);
        }
        else if (choice.equals("fight")) {
            myArea.setVisible(true);
            myCurPoke.drawMoves(g);//drawing the pokemon's moves
        }
        else if (choice.equals("bag")) {//if they're drawing the bag
            if (getChoosing() == false){//if they're not choosing the pokemon to use it on
                myArea.setVisible(false);
                g.drawImage(itemMenu, 0, 0, null);//draw images and backgrounds
                g.drawImage(backArrow, backArrowRect.x,backArrowRect.y,null);
                g.drawImage(pokeArrowUp, 265,285,null);
                g.drawImage(pokeArrowDown, 265,335,null);
                for (int i = 0+6*level; i < 6+6*level; i++){//looping through visible items
                    myItems.draw(g,i);//draw the items
                }
                g.setFont(bigFont);
                g.drawString("BAG",140,87);//draing strings
                for (Rectangle item : bagRects) {//making the same outline as the bag
                    if (item.contains(mouse)) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setStroke(new BasicStroke(3));
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(item.x, item.y, item.width, item.height);
                        g2d.setStroke(new BasicStroke(1));
                    }
                }
            }
            else{//if they are chhosing which pokemon to use it on
                myArea.setVisible(false);
                g.drawImage(switchBackground, 0, 0, null);//draw backgrounds and buttons
                g.drawImage(pokeBox, 231, 650, null);
                g.drawImage(backArrow, 10, 10, null);
                g.setColor(Color.BLACK);
                g.setFont(smallerGameFont);
                g.drawString("What Pokemon will you use your item on?", 275, 710);
                for (int i = 0; i < myPokes.size(); i++) {
                    myPokes.get(i).drawDisplay(g, i);
                    g.drawString("HP: ", 273, 100 + 105 * i);
                }
                for (Rectangle item : switchPokeRects) {
                    if (item.contains(mouse)) {//loop through pokemon and draw outlines
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setStroke(new BasicStroke(3));
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(item.x, item.y, item.width, item.height);
                        g2d.setStroke(new BasicStroke(1));
                    }
                }
            }
        }
    }
    public void goNext(){//this method is called every time you press space in the pokemon battle and are trying to go to the next text showing
        if (textIndex+1 < myTexts.size()){
            textIndex++;//if you have not seen all the text
            myArea.setText(myTexts.get(textIndex));//see the next text
            pressingSound.play();//play the pressing sound
        }
        if (textIndex+1 == myTexts.size()){//if oyu have seen all neccesary text
            if (waiting){//you can now click and make decisions
                waiting = false;
            }
            stopGame = false;//the game is not being paused anymore
        }
    }
    public String battleOver() {//method that returns whether or not the battle is over
        boolean myTeamAlive = false;
        for (Pokemon item : myPokes) {//finding if you have not lost the battle
            if (item.getHP() > 0) {
                myTeamAlive = true;
            }
        }
        boolean enemyTeamAlive = false;
        for (Pokemon item : enemyPokes) {
            if (item.getHP() > 0) {
                enemyTeamAlive = true;//finding if you have won the battle
            }
        }
        if (myTeamAlive == true) {//if you are alive and the enemy is not
            if (enemyTeamAlive == false) {
                return "win";//you win
            } else {
                return "";//if you are both alive, the battle is not over
            }
        } else {
            return "loss";//if you are dead, you lost
        }
    }
    public void myTurnRun() {//if you chose to run
        if (fleeable) {//if you CAN run
            MasseyMon.frame.inBattle = false;//stop the battle
        }
        else {//if you cant run
            String text = "You couldn't run away!";//you waste a turn
            fillTextArray(text);
        }
    }
    public void myTurnAttack() {//if you chose to attack
        Pokemon enemyPoke = enemyPokes.get(0);//determine the attack user
        myPokes.get(0).doAttack(attackC,enemyPoke);//make your pokemon do the attack
        String text = String.format("%s used %s!",myCurPoke.getName(),attackC.getName());//show text
        fillTextArray(text);
        fillTextArray(myCurPoke.getEffect());//show different text based off of how effective the attack was
    }
    public void myTurnSwitch() {//if you chose to switch pokemon
        pokeSwitch(indexC);//switch with the pokemon at that index
        String text = String.format("You switched out into %s!",myCurPoke.getName());//show text
        fillTextArray(text);//fill the queue
    }
    public void myTurnBag() throws FileNotFoundException {//
        if (itemC <=10 && itemC >= 7){
            pokeC = enemyPokes.get(0);
            myItems.use(pokeC,itemC);
        }
        myItems.use(pokeC,itemC);
        String text = String.format("You used a %s on %s!",curGuy.getItems().getUsed(),pokeC.getName());
        fillTextArray(text);
        if (waitingEnd){
            fillTextArray("And it worked! You captured the wild Pokemon!");
        }
    }
    public void Start(Graphics g) throws FileNotFoundException {//method that runs every frame, determiens battle
        enemyCurPoke = enemyPokes.get(0);//enemy poke
        myCurPoke = myPokes.get(0);//my poke
        if (doneTurn && learningNewMove == false) {//if the user has made a decision for this turn
            if (enemyCurPoke.getSpeed() > myCurPoke.getSpeed() && cBag == false && cPokes == false && cRun == false) {//if the enemy pokemon is faster
                AITurn(enemyCurPoke);//ai does their turn
                draw(g);//draw the battle
                if (myCurPoke.getHP() > 0){//if my poke is stil lalive
                    if (cFight) {//make their decision
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
            else {//if the user is faster or has priority
                if (learningNewMove == false){//if theyre not learninga new move
                    if (cFight) {//make an action based of off their decision
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
                    if (enemyCurPoke.getHP() > 0 && waitingEnd == false){
                        AITurn(enemyCurPoke);
                    }
                }
            }
            if (!battleOver2 && learningNewMove == false){//if the battle is not over and a pokemon fainted
                if (myCurPoke.getHP() <= 0){
                    setDead(true);//display text and make the boolean true
                    String text = String.format("%s fainted!",myCurPoke.getName());
                    fillTextArray(text);
                }
                if (enemyCurPoke.getHP() <= 0){
                    upgradeTeam();//give xp to your pokemon
                    String text = String.format("The enemy %s fainted!",enemyCurPoke.getName());
                    fillTextArray(text);
                    AISwitch();//make the ai switch
                    String text2 = String.format("The enemy trainer sent out %s!",enemyCurPoke.getName());
                    fillTextArray(text2);
                    waiting = true;//waiting for user to see text
                }
                doneTurn = false;
                if (myCurPoke.getHP() > 0){//if your pokemon i alive
                    String text = String.format("What will %s do?",myCurPoke.getName());
                    fillTextArray(text);//start showing text for next round
                    textIndex++;
                    myArea.setText(myTexts.get(textIndex));
                }
            }
        }
        draw(g);//draw
        update();//update variables
        if ((battleOver().equals("") == false && learningNewMove == false || waitingEnd && textIndex+1 == myTexts.size())){
            for (Pokemon item: myPokes){
                if (item.getEvolveAtEnd()){
                    evolutions.add(item);
                }
            }
            if (battleOver().equals("win") || waitingEnd){//if the battle is over
                if (MasseyMon.frame.getBattlingBrock()){//this section determines if you  were fighting a gym leader and if you were, it makes it so that the program keeps track of the fact that you beat it
                    MasseyMon.frame.setBeatBrock(true);
                }
                else if (MasseyMon.frame.getBattlingMisty()){
                    MasseyMon.frame.setBeatMisty(true);
                }
                else if (MasseyMon.frame.getBattlingGiov()){
                    MasseyMon.frame.setBeatBrock(true);
                }
                else if (MasseyMon.frame.getBattlingChamp()){
                    MasseyMon.frame.setBeatBrock(true);
                }
                if (fleeable == false){//if its a trainer battle
                    int x = randint(500,1000);//get money
                    curGuy.addMoney(x);
                }
            }
            else{
                if (fleeable == false){//if oyu lost and its a trainer battle
                    int x = randint(500,1000);
                    curGuy.addMoney(-1*x);//lose money
                }
                MasseyMon.frame.getGame().dieInBattle();//reset your position
            }
            MasseyMon.frame.inBattle = false;//get out of the battle
        }
    }
    public static int randint(int low, int high){//randint method
        return (int)(Math.random()*(high-low+1)+low);
    }
    public void pokeLearningMove(Attack atk){//showing that a pokemon is learning a new move
        setChoice("fight");
        learningNewMove = true;
        myArea.setFont(smallerGameFont);//showing text, and pausing the game
        String text = String.format("%s would like to learn the new move %s, which move will he forget? Press backspace to not learn the move.",myCurPoke.getName(),atk.getName());
        fillTextArraySpecial(text);
    }
    public JTextArea getMyArea(){//method that gets the j text area
        return myArea;
    }
    public void upgradeTeam() throws FileNotFoundException {//method that goes through your team and sees if any of them are supposed to evolve
        myCurPoke.gainXP(enemyCurPoke.getLevel());//giving xp to the pokemon
        for (Pokemon item: myPokes){
            if (item.getLevel() == 15){
                if (item.shouldEvolve()){
                    item.setEvolveAtEnd(true);//setting their booleans to true
                }
            }
            else if(item.getLevel() == 30){
                if (item.shouldEvolve()){
                    item.setEvolveAtEnd(true);
                }
            }
        }
    }
    public void update(){//updating variables
        numItems = curGuy.getNumItems();
        myItems = curGuy.getItems();
        myCurPoke = myPokes.get(0);
        enemyCurPoke = enemyPokes.get(0);
    }
}
