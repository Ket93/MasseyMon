import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Potion{
    public static Image pic;
    public static int order;
    public Potion(){
        order = 0;
        try{
            System.out.println("whats up");
            pic = ImageIO.read(new File("Images/Battles/potionimage.png"));
            pic = pic.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        }
        catch (IOException e) { }
    }
    public void use(Pokemon poke){
        if (poke.getHP() > 0){
            poke.setHP(poke.getHP() + 50);
            if (poke.getHP() > poke.getMaxHP()){
                poke.setHP(poke.getMaxHP());
            }
        }
    }
    public static void drawMenu(Graphics g){
        g.drawImage(pic,400,62+75*order,null);
        g.drawString("Potion",500,105+75*order);
        g.drawString("x"+Player.items[order],825,110+75*order);
    }
}
