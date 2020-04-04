public class Pokemon {
    private String name,type,strong,weak;
    private int hp,attack,defense,speed,level;

    public Pokemon (String line){
        String [] stats = line.split(" ");
        System.out.println(stats[0]);
        name = stats[1];
        type = stats [2];
        hp = Integer.parseInt(stats[3]);
        attack = Integer.parseInt(stats[4]);
        defense = Integer.parseInt(stats[5]);
        speed = Integer.parseInt(stats[6]);
    }

    public String getName(){return name; }
    public String getType(){return type; }
    public int getHp(){return hp;}
    public int getAttack(){return attack;}
    public int getDefense(){return defense;}
    public int getSpeed(){return speed;}
}
