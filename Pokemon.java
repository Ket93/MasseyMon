<<<<<<< HEAD
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Pokemon{
	private int hp,maxHP,num,atk,def,spatk,spdef,speed,extra,level;
	private String type1,type2,resistance,weakness,name;
	private ArrayList<Attack> pokeAttacks = new ArrayList<Attack>();
	private Image myPokeImage,enemyPokeImage,displayImage;
	private Font gameFont,smallerGameFont,switchFont;
	public Pokemon(String line){
		String [] stats = line.split(",");
		extra = 0;
		type2 = "N/A";
		if (stats.length == 10){
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
		level = 10;
		for (int i = 0; i < 4; i++){
			pokeAttacks.add(null);
		}
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
	}
	public int getHPWidth(int i){
		float max = (float) maxHP;
		float cur = (float) hp;
		return (int)((cur/max)*i);
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
	public void drawGood(Graphics g){
		g.setColor(Color.GREEN);
		g.drawImage(myPokeImage,90,355,null);
		g.fillRect(740,460,getHPWidth(182),18);
		g.setColor(Color.BLACK);
		g.setFont(gameFont);
		g.drawString(name,560,440);
	}
	public void drawBad(Graphics g){
		g.setColor(Color.GREEN);
		g.drawImage(enemyPokeImage,620,175,null);
		g.fillRect(185,143,getHPWidth(182),18);
		g.setColor(Color.BLACK);
		g.setFont(gameFont);
		g.drawString(name,15,125);
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
		g.setFont(gameFont);
		String text = String.format("What attack will %s use?",name);
		g.drawString(text,50,640);
		g.setFont(smallerGameFont);
		for (int i = 0; i < 4; i++){
			if (pokeAttacks.get(i) != null){
				pokeAttacks.get(i).draw(g,i);
			}
		}
	}
}
=======
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Pokemon{
	private int hp,maxHP,num,atk,def,spatk,spdef,speed,extra,level;
	private String type1,type2,resistance,weakness,name;
	private ArrayList<Attack> pokeAttacks = new ArrayList<Attack>();
	private Image myPokeImage,enemyPokeImage,displayImage;
	private Font gameFont,smallerGameFont,switchFont;
	public Pokemon(String line){
		String [] stats = line.split(",");
		extra = 0;
		type2 = "N/A";
		if (stats.length == 10){
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
		level = 10;
		for (int i = 0; i < 4; i++){
			pokeAttacks.add(null);
		}
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
	}
	public int getHPWidth(int i){
		float max = (float) maxHP;
		float cur = (float) hp;
		return (int)((cur/max)*i);
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
	public void drawGood(Graphics g){
		g.setColor(Color.GREEN);
		g.drawImage(myPokeImage,90,355,null);
		g.fillRect(740,460,getHPWidth(182),18);
		g.setColor(Color.BLACK);
		g.setFont(gameFont);
		g.drawString(name,560,440);
	}
	public void drawBad(Graphics g){
		g.setColor(Color.GREEN);
		g.drawImage(enemyPokeImage,620,175,null);
		g.fillRect(185,143,getHPWidth(182),18);
		g.setColor(Color.BLACK);
		g.setFont(gameFont);
		g.drawString(name,15,125);
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
		g.setFont(gameFont);
		String text = String.format("What attack will %s use?",name);
		g.drawString(text,50,640);
		g.setFont(smallerGameFont);
		for (int i = 0; i < 4; i++){
			if (pokeAttacks.get(i) != null){
				pokeAttacks.get(i).draw(g,i);
			}
		}
	}
}
>>>>>>> 44cf0e272420c5d321c39b7b2c798d3bc241b883
