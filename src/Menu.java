import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Menu {
    private static Image pointer;
    private static int posX,posY;

    public Menu() throws IOException {
        posX = 605;
        posY = 186;
        pointer = ImageIO.read(new File("Images/Menu/MenuPointer.png"));
    }
    public static void display(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        int borderWidth = 5;
        ((Graphics2D) g).setStroke(new BasicStroke(borderWidth));
        Font optionFont = new Font("Consolas", 0, 27);
        g.setColor(new Color(255,255,255));
        g.fillRect(600,150, 160,200);
        g.setColor(new Color(120,105,200));
        g.drawRect(600,150,160,200);

        g.drawImage(pointer , posX , posY, null);
        g2d.setFont(optionFont);
        g2d.setColor(Color.black);
        g2d.drawString("Pokemon", 643, 205);
        g2d.drawString("Items", 643, 245);
        g2d.drawString("Exit", 643, 285);
    }

    public static int getPosY(){return posY;}

    public static void resetPosY(){
        posY = 186;
    }

    public static void setPosY(int val){
        if (posY + val >= 186 && posY + val <= 266) {
            posY += val;
        }
    }

}
