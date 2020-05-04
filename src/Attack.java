import java.awt.*;

public class Attack{
    private String name,dmgType,type;
    private int dmg,accuracy,times,pp,maxPP,num;
    String[] stats = new String[7];
    private int [] xcords = {485,721,484,721};
    private int [] ycords = {627,627,713,713};
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
    public void setPP(int p){pp = p;}
    public void draw(Graphics g, int i){
        int x = xcords[i];
        int y = ycords[i];
        g.drawString(name,x,y);
        g.drawString(type,x+110,y);
        g.drawString("DMG: "+dmg,x,y+30);
        g.drawString("PP: "+pp+"/"+maxPP,x+100,y+30);
    }
}