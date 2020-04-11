import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PokemonMenu {
    private static boolean moveX,displayButton;
    private static int posX,posY;
    private static Image background,pointer;

    public PokemonMenu() throws IOException {
        displayButton = false;
        moveX = true;
        posX = 161;
        posY = 110;
        pointer = ImageIO.read(new File("Images/Menu/MenuPointer.png"));
        background = ImageIO.read(new File("Images/Menu/PokemonBackground.png"));
    }

    public static void display (Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g.drawImage(background,155,100,null);
        g.setColor(new Color(255,165,0));
        g.drawRect(152,100,653,541);

        g.setColor(new Color(0,0,0));
        g.drawRect(161,110,316,150);
        g.drawRect(161,260,316,150);
        g.drawRect(161,410,316,150);
        g.drawRect(477,130,320,150);
        g.drawRect(477,280,320,150);
        g.drawRect(477,430,320,150);

        g.setColor(new Color(255,0,0));
        if (!displayButton) {
            System.out.println(posX);
            g.drawRect(posX, posY, 316, 150);
        }

        Font optionFont = new Font("Consolas", 0, 35);
        g2d.setFont(optionFont);
        g2d.setColor(Color.black);
        g.drawString("1.", 175,150);
        g.drawString("3.", 175,295);
        g.drawString("5.", 175,445);
        g.drawString("2.", 500,165);
        g.drawString("4.", 500,315);
        g.drawString("6.", 500,470);
        g.drawString("Exit",600,622);

        if (displayButton){
            g.drawImage(pointer,560,600,null);
        }
    }

    public static int getPosY(){return posY;}
    public static void resetPosXY() {
        posY = 110;
        posX = 161;
        displayButton = false;
    }
    public static void setPosY(){
        if (posY == 430){
            moveX = false;
            displayButton = true;
        }
        else {
            displayButton = false;
            if (posY == 110 || posY == 260 || posY == 410) {
                posY += 20;
            } else {
                posY += 130;
            }
            moveX = true;
        }
    }
    public static void setPosYUP() {
        if (displayButton){
            posY = 430;
        }
        else{
        if (posY ==110){
            moveX = false;
        }
        else {
            if (posY == 130 || posY == 280 || posY == 430) {
                posY -= 20;
            } else {
                posY -= 130;
            }
            moveX = true;
          }
        }
        displayButton = false;
    }

    public static void setPosX() {
        if (moveX) {
            if (posX == 161) {
                posX += 316;
            } else {
                posX -= 316;
            }
        }
    }
    public static boolean getDisplayButton(){return displayButton;}
}
