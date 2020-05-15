import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Items {
    private static int posX,posY;
    private static Image pointer;
    private static Image bagBackground;
    private static Image bag;
    private Image[] itemPics = new Image[7];
    private int[] nums = new int[7];
    private String [] itemNames = {"Potion","Super Potion","Hyper Potion","Hyper Potion","Max Potion","Full Restore","Revive","Max Revive"};
    public Items () throws IOException {
        posX = 533;
        posY = 207;
        pointer = ImageIO.read(new File("Images/Menu/MenuPointer.png"));
        bag = ImageIO.read(new File("Images/Menu/PokemonBag.png"));
        bagBackground = ImageIO.read(new File("Images/Menu/BagBackground.png"));
        for (int i = 1; i < 8; i++){
            String path = String.format("Images/Battles/Items/Item%d.png",i);
            Image pic = ImageIO.read(new File(path));
            pic = pic.getScaledInstance(50,50,Image.SCALE_SMOOTH);
            itemPics[i-1] = pic;
            nums[i-1] = 999;
        }
    }
    public int[] getNums(){
        return nums;
    }
    public void draw(Graphics g, int i){
        int offset = 0;
        if (i == 1){
            offset = 4;
        }
        else if (i == 2){
            offset = 10;
        }
        else if (i == 3){
            offset = 14;
        }
        else if (i == 4){
            offset = 19;
        }
        else if (i == 5){
            offset = 24;
        }
        g.drawImage(itemPics[i],400,62+75*i+offset,null);
        g.drawString("Potion",500,105+75*i+offset);
        g.drawString("x"+nums[i],825,110+75*i+offset);
    }
    public void use(Pokemon poke, int i){
        if (i == 6){
            if (poke.getHP() <= 0){
                poke.revive(1.0);
            }
        }
        else if(i == 5){
            if (poke.getHP() <= 0){
                poke.revive(1.0);
            }
        }
        else if(i == 4){
            poke.heal(poke.getMaxHP());
        }
        else if(i == 3){
            poke.heal(poke.getMaxHP());
        }
        else{
            if (poke.getHP() < poke.getMaxHP()){
                int scale = 50 * (1 + 1);
                poke.heal(scale);
            }
        }
        nums[i] --;
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
