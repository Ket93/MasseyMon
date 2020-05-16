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
            pic = ImageIO.read(new File("Images/Battles/revivePic.png"));
            pic = pic.getScaledInstance(50,50,Image.SCALE_SMOOTH);
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
        g.drawImage(pic,400,70+75*order,null);
        g.drawString("Revive",500,120+75*order);
        g.drawString("x"+Player.items[order],825,110+75*order);
    }
}