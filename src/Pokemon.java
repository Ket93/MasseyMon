import java.util.ArrayList;
class Pokemon{
	private int hp,maxHP,num,atk,def,spatk,spdef,speed,extra;
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
	public void learnMove(Attack atk){
		if (pokeAttacks.size()<4){
			pokeAttacks.add(atk);
		}
	}
	public ArrayList<Attack> getMoves(){
		return pokeAttacks;
	}
	public void doAttack(Attack atk){
		int x = 1;
	}
}
class TypeChart{
	private double [][] types;
	private ArrayList<String> typeVals;
	public TypeChart(){
	    double[][] doubles = {{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.0,1.0},{1.0,0.5,0.5,1.0,2.0,2.0,1.0,1.0,1.0,1.0,1.0,2.0,0.5,1.0,0.5},{1.0,2.0,0.5,1.0,0.5,1.0,1.0,1.0,2.0,1.0,1.0,1.0,2.0,1.0,0.5},{1.0,1.0,2.0,0.5,0.5,1.0,1.0,1.0,0.0,2.0,1.0,1.0,1.0,1.0,0.5},{1,0,0.5,2.0,1.0,0.5,1.0,1.0,0.5,2.0,0.5,1.0,0.5,2.0,1.0,0.5},{1.0,1.0,0.5,1.0,2.0,0.5,1.0,1.0,2.0,2.0,1.0,1.0,1.0,1.0,2.0},{2.0,1.0,1.0,1.0,1.0,2.0,1.0,0.5,1.0,0.5,0.5,0.5,2.0,0.0,1.0},{1.0,1.0,1.0,1.0,2.0,1.0,1.0,0.5,0.5,1.0,1.0,2.0,0.5,0.5,1.0},{1.0,2.0,1.0,2.0,0.5,1.0,1.0,2.0,1.0,0.0,1.0,0.5,2.0,1.0,1.0},{1.0,1.0,1.0,0.5,2.0,1.0,2.0,1.0,1.0,1.0,1.0,2.0,0.5,1.0,1.0},{1.0,1.0,1.0,1.0,1.0,1.0,2.0,2.0,1.0,1.0,0.5,1.0,1.0,1.0,1.0},{1.0,0.5,1.0,1.0,2.0,1.0,0.5,2.0,1.0,0.5,2.0,1.0,1.0,0.5,1.0},{1.0,2.0,1.0,1.0,1.0,2.0,0.5,1.0,0.5,2.0,1.0,2.0,1.0,1.0,1.0},{0.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,2.0,1.0},{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,2.0}};
		ArrayList<String> Ts = new ArrayList<String>();
		String [] Ts2 = {"normal","fire","water","electric","grass","ice","fighting","poison","ground","flying","psychic","bug","rock","ghost","dragon"};
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
	private String name,special,type;
	private int dmg,accuracy,times,pp,maxPP,num;
	String[] stats = new String[7];
	public Attack(String line){
		stats = line.split(",");
		name = stats[0];
		type = stats[1];
		special = stats[2];
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
}