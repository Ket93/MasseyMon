import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MaxRevive {
    public static Image pic;
    public static int order;
    public MaxRevive(){
        order = 6;
        try{
            pic = ImageIO.read(new File("Images/Battles/maxRevivePic.png"));
            pic = pic.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        }
        catch (IOException e) { }
    }
    public void use(Pokemon poke){
        if (poke.getHP() <= 0){
            poke.setHP(poke.getMaxHP());
        }
    }
    public static void drawMenu(Graphics g){
        g.drawImage(pic,400,60+75*order,null);
        g.drawString("Max Revive",500,120+75*order);
        g.drawString("x"+Player.items[order],825,110+75*order);
    }
}
