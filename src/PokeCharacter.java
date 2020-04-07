import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class PokeCharacter {
    private static Image Trainer;
    private static int positionX;
    private static int positionY;

    public PokeCharacter () {
        Trainer = new ImageIcon("Sprites/TrainerStanding.png").getImage();
        positionX = 400;
        positionY = 400;
    }

    public void move () {

    }

    public static void draw (Graphics g){
        g.drawImage (Trainer, positionX, positionY, null);
    }
}
