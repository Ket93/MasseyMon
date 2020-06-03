import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TitleScreen {
    private static Image background,background2,arrowPointer,arrowPointer2,menuBox1,menuBox2,title,instructions,prof;
    private static int posY;
    private static boolean instructionMenu;

    public TitleScreen() throws IOException {
        posY = 305;
        background = ImageIO.read(new File("Images/TitleScreen/Background.jpg"));
        background2 = ImageIO.read(new File("Images/TitleScreen/Background2.jpg"));
        arrowPointer = ImageIO.read(new File("Images/TitleScreen/ArrowPointer.png"));
        arrowPointer2 = ImageIO.read(new File("Images/TitleScreen/ArrowPointer2.png"));
        menuBox1 = ImageIO.read(new File("Images/TitleScreen/MenuBox1.jpg"));
        menuBox2 = ImageIO.read(new File("Images/TitleScreen/MenuBox2.jpg"));
        title = ImageIO.read(new File("Images/TitleScreen/Title.png"));
        instructions = ImageIO.read(new File("Images/TitleScreen/Instructions.png"));
        prof = ImageIO.read(new File("Images/TitleScreen/ProfessorOak.png"));
    }

    public static void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font optionFont = new Font("Consolas", 0, 35);
        g2d.setFont(optionFont);
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0,0,956,795);
        g.drawImage(title,300,0,null);
        g.drawImage(background,0,129,null);
        g.drawImage(menuBox1,370,300,null);
        g.drawImage(menuBox2,370,500,null);
        g.drawImage(arrowPointer,310,posY,null);
        g2d.drawString("Start Game",400,340);
        g2d.drawString("Instructions",380,540);
        if (instructionMenu){
            g.fillRect(0,0,956,795);
            g.drawImage(background2,0,94,null);
            g.drawImage(prof,420,0,null);
            g.drawImage(instructions,150,150, null);
            g.setColor(new Color(255, 0, 0));
            g.drawImage(arrowPointer2,770,710,null);
            g2d.drawString("Back",850,740);
        }
    }

    public static int getPosY(){
        return posY;
    }
    public static void setPosY(int val){
        posY = val;
    }
    public static boolean getInstructionMenu(){
        return instructionMenu;
    }
    public static void setInstructionMenu(boolean b){
        instructionMenu = b;
    }


}
