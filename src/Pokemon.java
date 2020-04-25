import java.util.ArrayList;
class Pokemon{
	private int hp,maxHP,num,atk,def,spatk,spdef,speed,extra,level;
	private String type1,type2,resistance,weakness,name;
	private ArrayList<Attack> pokeAttacks = new ArrayList<Attack>();
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
	public void learnMove(Attack atk){
		if (pokeAttacks.size()<4){
			pokeAttacks.add(atk);
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
		crit = 1;
		if (myRandInt <= 100 && myRandInt >= 85){
			crit = 1.5;
		}
		myRandInt = randint(0,15);
		rand = 1+myRandInt/100;
		typeMult = myChart.getEffect(atkDone,defender);
		mod = crit*rand*STAB*typeMult;
		int atkDmg, defDef;
		if (atkDone.getDmgType().equals("Physical")){
			System.out.println("x");
			atkDmg = atk;
			defDef = defender.getDefence();
		}
		else if (atkDone.getDmgType().equals("Special")){
			atkDmg = spatk;
			defDef = defender.getSpecialDefence();
		}
		else{
			System.out.println("Z");
			atkDmg = 0;
			defDef = 1;
		}
		double damageDone = ((((2*level/5+2)*atkDone.getDmg()*atkDmg/defDef)+2)/50);
		damageDone *= mod;
		System.out.println(damageDone);
		defender.setHP((int)(defender.getHP()-damageDone));
	}
}
class TypeChart{
	private double [][] types;
	private ArrayList<String> typeVals;
	public TypeChart(){
	    double[][] doubles = {{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.0,1.0},{1.0,0.5,0.5,1.0,2.0,2.0,1.0,1.0,1.0,1.0,1.0,2.0,0.5,1.0,0.5},{1.0,2.0,0.5,1.0,0.5,1.0,1.0,1.0,2.0,1.0,1.0,1.0,2.0,1.0,0.5},{1.0,1.0,2.0,0.5,0.5,1.0,1.0,1.0,0.0,2.0,1.0,1.0,1.0,1.0,0.5},{1,0,0.5,2.0,1.0,0.5,1.0,1.0,0.5,2.0,0.5,1.0,0.5,2.0,1.0,0.5},{1.0,1.0,0.5,1.0,2.0,0.5,1.0,1.0,2.0,2.0,1.0,1.0,1.0,1.0,2.0},{2.0,1.0,1.0,1.0,1.0,2.0,1.0,0.5,1.0,0.5,0.5,0.5,2.0,0.0,1.0},{1.0,1.0,1.0,1.0,2.0,1.0,1.0,0.5,0.5,1.0,1.0,2.0,0.5,0.5,1.0},{1.0,2.0,1.0,2.0,0.5,1.0,1.0,2.0,1.0,0.0,1.0,0.5,2.0,1.0,1.0},{1.0,1.0,1.0,0.5,2.0,1.0,2.0,1.0,1.0,1.0,1.0,2.0,0.5,1.0,1.0},{1.0,1.0,1.0,1.0,1.0,1.0,2.0,2.0,1.0,1.0,0.5,1.0,1.0,1.0,1.0},{1.0,0.5,1.0,1.0,2.0,1.0,0.5,2.0,1.0,0.5,2.0,1.0,1.0,0.5,1.0},{1.0,2.0,1.0,1.0,1.0,2.0,0.5,1.0,0.5,2.0,1.0,2.0,1.0,1.0,1.0},{0.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,2.0,1.0},{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,2.0}};
		ArrayList<String> Ts = new ArrayList<String>();
		String [] Ts2 = {"Normal","Fire","Water","Electric","Grass","Ice","Fighting","Poison","Ground","Flying","Psychic","Bug","Rock","Ghost","Dragon"};
		for (String item : Ts2){
			Ts.add(item);
		}
		types = doubles;
		typeVals = Ts;
	}
	public double getEffect(Attack atk, Pokemon def){
		double tot;
		int index1 = typeVals.indexOf(atk.getType());
		int index2 = typeVals.indexOf(def.getType1());
		tot = types[index2][index1];
		if (def.getType2().equals("N/A") == false){
			int index3 = typeVals.indexOf(def.getType2());
			tot *= types[index3][index1];
		}
		return tot;
	}
}
class Attack{
	private String name,dmgType,type;
	private int dmg,accuracy,times,pp,maxPP,num;
	String[] stats = new String[7];
	public Attack(String line){
		stats = line.split(",");
		name = stats[0];
		type = stats[1];
		dmgType = stats[2];
		dmg = Integer.parseInt(stats[3]);
		accuracy = Integer.parseInt(stats[4]);
		pp = Integer.parseInt(stats[5]);
		maxPP = Integer.parseInt(stats[5]);
	}
	public String getName(){
		return name;
	}
	public String getType(){
		return type;
	}
	public int getDmg(){
		return dmg;
	}
	public int getPP(){return pp;}
	public int getMaxPP(){return maxPP;}
	public String getDmgType(){return dmgType;}
}