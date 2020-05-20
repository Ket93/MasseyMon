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


}
