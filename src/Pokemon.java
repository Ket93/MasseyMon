//Dimitrios Christopoulos
//Pokemon.java
//Object class for Pokemon. Contains the Pokemon name, type, stats, evolutions, and number.
// Also draws attacks, pokemon switching, evolution and damage calculations.

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Pokemon{
	private int hp,maxHP,num,atk,def,spatk,spdef,speed,extra,level;
	private String type1,type2,resistance,weakness,name;
	private boolean healed;
	private ArrayList<Attack> pokeAttacks = new ArrayList<Attack>();
	private ArrayList<Integer> moves = new ArrayList<Integer>(30);
	private Image myPokeImage,enemyPokeImage,displayImage,evoImageNext;
	private Font gameFont,smallerGameFont,switchFont;
	private int levelProg, levelGoal;
	private boolean finalEvo,evolveAtEnd;
	private String effect;
	public Pokemon(String line){
		String [] stats = line.split(",");
		extra = 0;
		type2 = "N/A";
		if (stats.length == 11){
			extra = 1;
			type2 = stats[3];
		}
		num = Integer.parseInt(stats[0]);
		name = stats[1];
		type1 = (stats[2]);
		hp = Integer.parseInt(stats[3+extra]);
		maxHP = Integer.parseInt(stats[3+extra]);
		atk = Integer.parseInt(stats[4+extra]);
		def = Integer.parseInt(stats[5+extra]);
		spatk = Integer.parseInt(stats[6+extra]);
		spdef = Integer.parseInt(stats[7+extra]);
		speed = Integer.parseInt(stats[8+extra]);
		String last = stats[9+extra];
		effect = "";
		for (int i = 0; i < 30; i++){
			moves.add(-1);
		}
		makeMoves();
		if (last.equals("F")){
			finalEvo = false;
		}
		else{
			finalEvo = true;
		}
		level = 5;
		levelProg = 4*level - 1;
		levelGoal = 4*level;
		healed = false;
		try{
			gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
			gameFont = gameFont.deriveFont(40f);
			smallerGameFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/gameFont.ttf"));
			smallerGameFont = gameFont.deriveFont(35f);
			switchFont = gameFont.deriveFont(45f);
			loadImage();
		}
		catch (IOException | FontFormatException e) {}
	}
	public void setEffect(String s){
		effect = s;
	}
	public void setLevel(int l){
		level = l;
	}
	public String getEffect(){
		return effect;
	}
	public void heal(){
		healed = true;
		hp = maxHP;
	}
	public boolean getHealed(){ return healed; }
	public int getNum(){return num;}
	public int getHP(){return hp;}
	public String getType1(){
		return type1;
	}
	public String getType2(){
		return type2;
	}
	public String getName(){
		return name;
	}
	public void makeMoves(){
		if (name.equals("Bulbasaur") || name.equals("Ivysaur") || name.equals("Venusaur")){
			moves.set(1,84);
			moves.set(5,1);
			moves.set(10,63);
			moves.set(17,50);
			moves.set(24,55);
		}
		else if(name.equals("Charmander") || name.equals("Charmeleon") || name.equals("Charizard")){
			moves.set(1,68);
			moves.set(5,28);
			moves.set(10,31);
			moves.set(17,96);
			moves.set(24,33);
		}
		else if(name.equals("Squirtle") || name.equals("Wartortle") || name.equals("Blastoise")){
			moves.set(1,84);
			moves.set(5,11);
			moves.set(10,12);
			moves.set(17,45);
			moves.set(24,41);
		}
		else if(name.equals("Caterpie") || name.equals("Metapod") || name.equals("Butterfree")){
			moves.set(1,84);
			moves.set(5,91);
			moves.set(9,48);
			moves.set(16,38);
			moves.set(21,64);
		}
		else if(name.equals("Weedle") || name.equals("Kakuna") || name.equals("Beedrill")){
			moves.set(1,84);
			moves.set(5,92);
			moves.set(9,57);
			moves.set(16,2);
			moves.set(21,63);
		}
		else if(name.equals("Pidgey") || name.equals("Pidgeot") || name.equals("Pidgeotto")){
			moves.set(1,84);
			moves.set(5,37);
			moves.set(10,35);
			moves.set(16,34);
			moves.set(22,25);
		}
		else if(name.equals("Rattata") || name.equals("Raticate")){
			moves.set(1,68);
			moves.set(5,36);
			moves.set(8,72);
			moves.set(14,78);
			moves.set(23,85);
		}
		else if(name.equals("Ekans") || name.equals("Arbok")){
			moves.set(1,84);
			moves.set(5,75);
			moves.set(9,74);
			moves.set(16,83);
			moves.set(24,86);
		}
		else if(name.equals("Vulpix") || name.equals("Ninetales")){
			moves.set(1,84);
			moves.set(5,28);
			moves.set(9,32);
			moves.set(17,33);
			moves.set(25,30);
		}
		else if(name.equals("Zubat") || name.equals("Golbat")){
			moves.set(1,48);
			moves.set(5,96);
			moves.set(10,6);
			moves.set(16,35);
			moves.set(21,71);
		}
		else if(name.equals("Oddish") || name.equals("Gloom") || name.equals("Vileplume")){
			moves.set(1,84);
			moves.set(5,1);
			moves.set(8,63);
			moves.set(16,74);
			moves.set(22,76);
		}
		else if(name.equals("Paras") || name.equals("Parasect")){
			moves.set(1,68);
			moves.set(5,91);
			moves.set(10,50);
			moves.set(17,36);
			moves.set(24,76);
		}
		else if(name.equals("Venonat") || name.equals("Venomoth")){
			moves.set(1,84);
			moves.set(5,75);
			moves.set(10,92);
			moves.set(19,50);
			moves.set(26,55);
		}
		else if(name.equals("Meowth") || name.equals("Persian")){
			moves.set(1,68);
			moves.set(5,53);
			moves.set(9,78);
			moves.set(16,85);
			moves.set(22,51);
		}
		else if(name.equals("Diglett") || name.equals("Dugtrio")){
			moves.set(1,84);
			moves.set(5,66);
			moves.set(11,19);
			moves.set(19,65);
			moves.set(25,26);
		}
		else if(name.equals("Mankey") || name.equals("Primeape")){
			moves.set(1,68);
			moves.set(5,36);
			moves.set(9,52);
			moves.set(14,67);
			moves.set(20,86);
		}
		else if(name.equals("Growlithe") || name.equals("Arcanine")){
			moves.set(1,84);
			moves.set(5,28);
			moves.set(9,32);
			moves.set(17,78);
			moves.set(25,33);
		}
		else if(name.equals("Poliwag") || name.equals("Poliwhirl") || name.equals("Poliwrath")){
			moves.set(1,84);
			moves.set(5,11);
			moves.set(11,3);
			moves.set(19,12);
			moves.set(26,41);
		}
		else if(name.equals("Abra") || name.equals("Kadabra") || name.equals("Alakazam")){
			moves.set(1,84);
			moves.set(5,15);
			moves.set(11,59);
			moves.set(20,24);
			moves.set(27,60);
		}
		else if(name.equals("Machop") || name.equals("Machoke") || name.equals("Machamp")){
			moves.set(1,84);
			moves.set(5,47);
			moves.set(10,67);
			moves.set(17,78);
			moves.set(23,81);
		}
		else if(name.equals("Bellsprout") || name.equals("Weepinbell") || name.equals("Victreebell")){
			moves.set(1,84);
			moves.set(5,92);
			moves.set(11,63);
			moves.set(17,50);
			moves.set(22,76);
		}
		else if(name.equals("Magnemite") || name.equals("Magneton")){
			moves.set(1,84);
			moves.set(5,63);
			moves.set(12,88);
			moves.set(19,89);
			moves.set(25,87);
		}
		else if(name.equals("Onix")){
			moves.set(1,84);
			moves.set(5,66);
			moves.set(9,19);
			moves.set(17,26);
			moves.set(23,65);
		}
		else if(name.equals("Ryhorn") || name.equals("Rhydon")){
			moves.set(1,84);
			moves.set(5,40);
			moves.set(10,78);
			moves.set(17,26);
			moves.set(25,65);
		}
		else if(name.equals("Kangaskhan")){
			moves.set(1,84);
			moves.set(5,78);
			moves.set(8,79);
			moves.set(14,86);
			moves.set(21,86);
		}
		else if(name.equals("Kabuto") || name.equals("Kabutops") || name.equals("Aerodactyl")){
			moves.set(1,84);
			moves.set(5,94);
			moves.set(9,12);
			moves.set(17,26);
			moves.set(24,70);
		}
	}
	public void setHP(int h){hp = h;}
	public int getSpecialDefence(){return spdef;}
	public int getDefence(){return def;}
	public int getMaxHP(){return maxHP;}
	public int getLevel(){return level;}
	public int getSpeed(){return speed;}
	public void setAtkPP(Attack atk, int p){
		atk.setPP(p);
	}
	public void learnMove(Attack atk){
		if (pokeAttacks.size()<4){
			pokeAttacks.add(atk);
		}
		else{
			for (int i = 0; i < 4; i++){
				if (pokeAttacks.get(i) == null){
					pokeAttacks.set(i,atk);
					break;
				}
			}
		}
	}
	public void setString(double d){
		if (d == 0.0){
			setEffect("It had no effect!");
		}
		else if (d == 2.0){
			setEffect("It's super effective!");
		}
		else if (d == 4.0){
			setEffect("It's super effective");
		}
		else{
			setEffect("It's effective!");
		}
	}
	public void drawEvo(Graphics g){
		g.drawImage(enemyPokeImage,350,200,null);
	}
	public void drawEvoNext(Graphics g){
		g.drawImage(evoImageNext,350,200,null);
	}
	public static int randint(int low, int high){
		return (int)(Math.random()*(high-low+1)+low);
	}
	public ArrayList<Attack> getMoves(){
		return pokeAttacks;
	}
	public void doAttack(Attack atkDone, Pokemon defender){
		TypeChart myChart = new TypeChart();
		int myRandInt = randint(1,100);
		double mod,STAB,crit,rand,typeMult;
		STAB = 1;
		if (type1.equals(atkDone.getType()) || type2.equals(atkDone.getType())){
			STAB = 1.5;
		}
		crit = 1.0;
		if (myRandInt <= 100 && myRandInt >= 85){
			crit = 1.5;
		}
		myRandInt = randint(0,15);
		rand = 1+myRandInt/100.0;
		typeMult = myChart.getEffect(atkDone,defender);
		setString(typeMult);
		mod = crit*rand*STAB*typeMult;
		int atkDmg, defDef;
		if (atkDone.getDmgType().equals("Physical")){
			atkDmg = atk;
			defDef = defender.getDefence();
		}
		else if (atkDone.getDmgType().equals("Special")){
			atkDmg = spatk;
			defDef = defender.getSpecialDefence();
		}
		else{
			atkDmg = 0;
			defDef = 1;
		}
		double damageDone = ((((2.0*((float)level)/5.0+2)*((float)atkDone.getDmg())*((float)atkDmg)/((float)defDef))+2)/50.0);
		damageDone *= mod;
		defender.setHP((int)(defender.getHP()-damageDone));
		if (defender.getHP() < 0){
			defender.setHP(0);
		}
		setAtkPP(atkDone,atkDone.getPP()-1);
	}
	public void loadImage() throws IOException {
		String path = String.format("Sprites/Pokemon/P%dM.png",num);
		Image pic = ImageIO.read(new File(path));
		pic = pic.getScaledInstance(300,233,Image.SCALE_SMOOTH);
		myPokeImage = pic;
		String path2 = String.format("Sprites/Pokemon/P%d.png",num);
		Image pic2 = ImageIO.read(new File(path2));
		pic2 = pic2.getScaledInstance(220,180,Image.SCALE_SMOOTH);
		enemyPokeImage = pic2;
		pic2 = pic2.getScaledInstance(122,100,Image.SCALE_SMOOTH);
		displayImage = pic2;
		if (!finalEvo){
			String path3 = String.format("Sprites/Pokemon/P%d.png",num+1);
			evoImageNext = ImageIO.read(new File(path3)).getScaledInstance(220,180,Image.SCALE_SMOOTH);
		}
	}

	public void drawMenu(Graphics g, int coord){
		ArrayList<Pokemon> myTeam = MasseyMon.frame.getMyPokes();
		Graphics2D g2d = (Graphics2D)g;
		Font nameFont = new Font("Consolas", 0, 25);
		Font typeFont = new Font("Consolas", 0, 15);
		g2d.setFont(nameFont);
		g2d.setColor(Color.black);
		g.drawImage(displayImage,180,150,null);
		g.drawString(name,320,140);
		g2d.setFont(typeFont);
		g.drawString(type1,335,160);
		g.drawString(type2,385,160);
		g.drawString("Level:" + Integer.toString(level),350,180);
		g.drawString("HP: " + Integer.toString(hp)+"/"+Integer.toString(maxHP),340,200);
		int moveY = 220;
		int adjustX = 0;
		for (int i =0;i<pokeAttacks.size();i++){
			g.drawString(pokeAttacks.get(i).getName(),315 + i*adjustX*65,moveY);
			adjustX += 1;
			if (i == 1){
				moveY += 20;
				adjustX = 0;
			}
		}

	}
	public int getHPWidth(int i){
		float max = (float) maxHP;
		float cur = (float) hp;
		return (int)((cur/max)*i);
	}
	public int getEXWidth(){
		float max = (float) levelGoal;
		float cur = (float) levelProg;
		return (int)((cur/max)*359);
	}
	public void updateLevel() throws FileNotFoundException {
		while (levelProg >= levelGoal){
			level++;
			levelProg -= levelGoal;
			levelGoal = 4*level;
			if (moves.get(level-1) != -1){
				String line = "";
				Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Moves.txt")));
				for (int i = 0; i < moves.get(level); i++){
					System.out.println(i);
					line = inFile.nextLine();
				}
				Attack newAtk = new Attack(line);
				if (pokeAttacks.size() < 4){
					System.out.println(newAtk.getName());
					learnMove(newAtk);
				}
				else{
					MasseyMon.frame.getPokeBattle().setMoveLearning(newAtk);
					MasseyMon.frame.getPokeBattle().pokeLearningMove(newAtk);
				}
			}
		}
		hp++;
		maxHP++;
		atk++;
		def++;
		spatk++;
		spdef++;
		speed++;
	}
	public void heal(int i){
		hp += i;
		if (hp > maxHP){
			hp = maxHP;
		}
	}
	public void revive(double d){
		hp = (int)(maxHP*d);
	}
	public void gainXP(int i) throws FileNotFoundException {
		levelProg += i;
		updateLevel();
	}
	public void drawGood(Graphics g){
		g.setColor(Color.GREEN);
		g.drawImage(myPokeImage,92,370,null);
		g.fillRect(749,478,getHPWidth(182),18);
		g.setColor(Color.BLACK);
		g.setFont(gameFont);
		g.drawString(name,570,455);
		g.drawString(""+level,872,459);
		g.setColor(Color.BLUE);
		g.fillRect(575,556,getEXWidth(),10);
	}
	public void drawBad(Graphics g){
		g.setColor(Color.GREEN);
		g.drawImage(enemyPokeImage,620,175,null);
		g.fillRect(188,149,getHPWidth(182),18);
		g.setColor(Color.BLACK);
		g.setFont(gameFont);
		g.drawString(name,15,125);
		g.drawString(""+level,308,130);
	}
	public void drawDisplay(Graphics g, int i){
		g.drawImage(displayImage,143,20+105*i,null);
		g.setColor(Color.BLACK);
		g.setFont(switchFont);
		g.drawString(name,273,65+105*i);
		g.drawString(""+hp+"/"+maxHP,543,105+105*i);
		g.setColor(Color.GREEN);
		g.fillRect(333,80+105*i,getHPWidth(200),25);
	}
	public void drawNone(Graphics g){
		String text = String.format("What will %s do?",name);
		g.drawString(text,50,640);
	}
	public void drawMoves(Graphics g){
		g.setFont(smallerGameFont);
		for (Attack atk: pokeAttacks){
			atk.draw(g,pokeAttacks.indexOf(atk));
		}
	}
	public void setEvolveAtEnd(boolean b){
		evolveAtEnd = b;
	}
	public boolean getEvolveAtEnd(){
		return evolveAtEnd;
	}
	public boolean shouldEvolve(){
		if (!finalEvo){
			return true;
		}
		return false;
	}
	public void evolve() throws IOException {
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Pokemon2.txt")));
		String line = "";
		line = inFile.nextLine();
		for (int i = 0; i < num+1; i++){
			line = inFile.nextLine();
		}
		String [] stats = line.split(",");
		extra = 0;
		type2 = "N/A";
		if (stats.length == 11){
			extra = 1;
			type2 = stats[3];
		}
		num = Integer.parseInt(stats[0]);
		name = stats[1];
		type1 = (stats[2]);
		hp = Integer.parseInt(stats[3+extra]);
		maxHP = Integer.parseInt(stats[3+extra]);
		atk = Integer.parseInt(stats[4+extra]);
		def = Integer.parseInt(stats[5+extra]);
		spatk = Integer.parseInt(stats[6+extra]);
		spdef = Integer.parseInt(stats[7+extra]);
		speed = Integer.parseInt(stats[8+extra]);
		String last = stats[9+extra];
		effect = "";
		if (last.equals("F")){
			finalEvo = false;
		}
		else{
			finalEvo = true;
		}
		levelProg = 0;
		levelGoal = 4*level;
	}
}
