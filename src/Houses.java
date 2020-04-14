import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Houses {
    private static Player myGuy;
    private static Image oakLab;

    public Houses() throws IOException {
        myGuy = new Player(0);
        oakLab = ImageIO.read(new File("Images/Buildings/ProfessorOakLab.png"));
    }

    public static void displayLab (Graphics g){
        g.drawImage(oakLab,0,0,null);
        Player.setPx(290);
        Player.setPy(500);
        myGuy.draw(g);
    }

}
