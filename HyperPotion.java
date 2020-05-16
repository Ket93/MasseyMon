import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HyperPotion {
    public static Image pic;
    public static int order;
    public HyperPotion(){
        order = 2;
        try{
            pic = ImageIO.read(new File("Images/Battles/hyperPotionPic.png"));
            pic = pic.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        }
        catch (IOException e) { }
    }
    public void use(Pokemon poke){
        if (poke.getHP() > 0){
            poke.setHP(poke.getHP() + 150);
            if (poke.getHP() > poke.getMaxHP()){
                poke.setHP(poke.getMaxHP());
            }
        }
    }
    public static void drawMenu(Graphics g){
        g.drawImage(pic,400,72+75*order,null);
        g.drawString("Hyper Potion",500,110+75*order);
        g.drawString("x"+Player.items[order],825,115+75*order);
    }
}