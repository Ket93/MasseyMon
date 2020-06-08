import java.awt.*;

public class Attack{ // class for Pokemon attacks
    private String name,dmgType,type; // declaring string variables
    private int dmg,accuracy,pp,maxPP; // declaring int variables
    String[] stats = new String[7]; // array for stats of each attack
    private int [] xcords = {495,732,495,732}; // array for X positions of writing text
    private int [] ycords = {645,645,732,732}; // array for Y positions of writing text

    public Attack(String line){ // constructor method
        stats = line.split(","); // splitting up the stats array at the commas
        name = stats[0]; // setting the name of the attack
        type = stats[1]; // setting the type of the attack
        dmgType = stats[2]; // setting the damage type of the attack (physical, special)
        dmg = Integer.parseInt(stats[3]); // setting the damage of the attack
        accuracy = Integer.parseInt(stats[4]); // setting the accuracy of the attack
        pp = Integer.parseInt(stats[5]); // setting the power points of the attack (how many times it can be used)
        maxPP = Integer.parseInt(stats[5]); // setting the maximum power points of the attack
    }
    public String getName(){
        return name;
    } // getter for the name of the attack
    public String getType(){
        return type;
    } // getter for the type of the attack
    public int getDmg(){
        return dmg;
    } // getter for the damage of the attack
    public int getPP(){return pp;} // getter for the PP of the attack
    public int getMaxPP(){return maxPP;} // getter for the maximum PP of the attack
    public String getDmgType(){return dmgType;} //getter for the damage type
    public void setPP(int p){pp = p;} // setter for the power points
    public void draw(Graphics g, int i){ // drawing the text for attacks during the battle
        int x = xcords[i]; // setting the X coordinates that the attack will be written at
        int y = ycords[i]; // setting the Y coordinates that the attack will be written at
        g.drawString(name,x,y); // drawing the name of the attack
        g.drawString("DMG: "+dmg,x,y+30); // drawing the damage of the attack
        g.drawString("PP: "+pp+"/"+maxPP,x+100,y+30); // drawing the power points of the attack
    }
}