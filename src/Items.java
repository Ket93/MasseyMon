import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Items {
    private static int posX,posY;
    private static Image pointer;
    private static Image bagBackground;
    private static Image bag;

    public Items () throws IOException {
        posX = 533;
        posY = 207;
        pointer = ImageIO.read(new File("Images/Menu/MenuPointer.png"));
        bag = ImageIO.read(new File("Images/Menu/PokemonBag.png"));
        bagBackground = ImageIO.read(new File("Images/Menu/BagBackground.png"));
    }

    public static void display (Graphics g){

        Graphics2D g2d = (Graphics2D)g;
        g.setColor(new Color(245,245,220));
        g.drawImage(bagBackground,250,110,null);
        g.drawImage(bag,280,230,null);
        g.fillRect(530,110,220,500);

        g.drawImage(pointer , posX , posY, null);
        Font titleFont = new Font("Consolas", 0, 35);
        Font itemFont = new Font("Consolas", 0, 25);
        g2d.setFont(titleFont);
        g2d.setColor(Color.black);
        g2d.drawString("Items", 593, 165);
        g2d.setFont(itemFont);
        g2d.drawString("PokeBall", 570, 225);
        g2d.drawString("Potion", 570, 265);
        g2d.drawString("Exit", 570, 305);

    }

    public static int getPosY(){return posY;}
    public static void resetPosY(){
        posY = 207;
    }
    public static void setPosY(int val){
        if (posY + val >= 207 && posY + val <= 287) {
            posY += val;
        }
    }
}
