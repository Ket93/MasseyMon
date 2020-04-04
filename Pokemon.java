public class Pokemon {
    private String name,type,strong,weak;
    private int hp,attack,defense,speed,level;

    public Pokemon (String line){
        String [] stats = line.split(" ");
        name = stats[0];
        type = stats [1];
        hp = Integer.parseInt(stats[2]);
        attack = Integer.parseInt(stats[3]);
        defense = Integer.parseInt(stats[4]);
        speed = Integer.parseInt(stats[5]);
    }
}
