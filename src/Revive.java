import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Revive {
    public static Image pic;
    public static int order;
    public Revive(){
        order = 5;
        try{
            pic = ImageIO.read(new File("Images/Battles/maxPotionPic.png"));
            pic = pic.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        }
        catch (IOException e) { }
    }
    public void use(Pokemon poke){
        if (poke.getHP() <= 0){
            int hp = (int)((float) poke.getMaxHP()/2.0);
            poke.setHP(hp);
        }
    }
    public static void drawMenu(Graphics g){
        g.drawImage(pic,400,90+85*order,null);
        g.drawString("Potion",440,90+85*order);
        g.drawString(""+Player.items[order],440,90+85*order);
    }
}