//Kevin Cui and Dimitrios Christopoulos
//Items.java
//Class to create the items and display them on the items menu screen as well as in battle. Also creates the functionality
// of each item and what it does.

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Items { // Class for making, using and displaying items
    private static int posX,posY; // position the items will be drawn at in the menu
    private static Image pointer; // pointer for the menu items
    private static Image bagBackground; // bag background on menu
    private static Image bag; // bag image on menu
    private Image[] itemPics = new Image[12]; // array for images of all 12 items
    private static int[] nums = new int[12]; //  array for amount of each item
    private int [] offsets = {0,4,10,14,19,24,0,3,9,15,21,25}; // offsets for where the items will be draw on the battle screen
    private String justUsed; // string to store the item that was just used
    private static String [] itemNames = {"Potion","Super Potion","Hyper Potion","Max Potion","Full Restore","Revive","Max Revive","Poke Ball","Great Ball","Ultra Ball","Master Ball","???"}; // Names of all the items
    public Items () throws IOException { // constructor method
        posX = 533; // starting X position for menu items
        posY = 207; // starting Y position for menu items
        // loading pictures for pointer, bag and background
        pointer = ImageIO.read(new File("Images/Menu/MenuPointer.png"));
        bag = ImageIO.read(new File("Images/Menu/PokemonBag.png"));
        bagBackground = ImageIO.read(new File("Images/Menu/BagBackground.png"));
        for (int i = 1; i < 13; i++){ // for loop to load all the item images and amounts into the arrays
            String path = String.format("Images/Battles/Items/Item%d.png",i); // file path of the image
            Image pic = ImageIO.read(new File(path)); // getthing the image at the file path
            pic = pic.getScaledInstance(50,50,Image.SCALE_SMOOTH); // resizing the image
            itemPics[i-1] = pic; // adding the picture to the array
            nums[i-1] = 10; // adding the amount to the array
        }
    }
    public String getUsed(){
        return justUsed;
    } // getter for the most recently used item
    public static void addItem(int i){ // adds an item to the total for an inputted item
        if (nums[i] <999) { // if the item total is below 999
            nums[i]++; // add one the to quantity of the item
        }
    }
    public int[] getNums(){
        return nums;
    } // getting the array of quantity of items
    public void draw(Graphics g, int i){ // drawing the items in the battle screen
        int offset = offsets[i]; // getting the offset for each item
        Image pic = itemPics[i]; // getting the image of each item
        String text2 = "x"+nums[i]; // getting the amount of each item
        String text = itemNames[i]; // getting the name of each item
        if (i > 5){ // if i is over 5 reset it to 0 so the position stays in line
            i -= 6;
        }
        g.drawImage(pic,400,62+75*i+offset,null); // drawing each item image
        g.drawString(text,500,105+75*i+offset); // writing the name of the item
        g.drawString(text2,825,110+75*i+offset); // writing the amount of each item
    }

    public static int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    } // randint method
    public void use(Pokemon poke, int i) throws FileNotFoundException { // method for item functionality when it is used
        if (i == 10){
            System.out.println("used master ball");
            MasseyMon.frame.captureEnemy();
        }
        else if(i == 9){
            int x = randint(1,4);
            if (x != 4){
                MasseyMon.frame.captureEnemy();
            }
        }
        else if(i == 8){
            int x = randint(1,2);
            if (x == 1){
                MasseyMon.frame.captureEnemy();
            }
        }
        else if(i == 7){
            int x = randint(1,4);
            if (x == 1){
                MasseyMon.frame.captureEnemy();
            }
        }
        if (i == 6){ // for max revive
            if (poke.getHP() <= 0){ // if the pokemon is fainted
                poke.revive(1.0); // revive the pokemon to full HP
            }
        }
        else if(i == 5){ // for revive
            if (poke.getHP() <= 0){ // if the pokemon is fainted
                poke.revive(0.5); // revive the pokemon to half HP
            }
        }
        else if(i == 4){ // for full restore
            poke.heal(poke.getMaxHP()); // bring the Pokemon to full HP
        }
        else if(i == 3){ // for max potion
            poke.heal(poke.getMaxHP()); // bring the Pokemon to full HP
        }
        else{ // for potion, super potion and hyper potion
            if (poke.getHP() < poke.getMaxHP()){ // if the Pokemon is not at max HP
                int scale = 30 * (1 + i); // scaling the healing based on the i value
                poke.heal(scale); // healing the pokemon
            }
        }
        justUsed = itemNames[i]; // setting the most recently used item
        nums[i] --; // subtracting from the item quantity if its used
    }
    public static void display (Graphics g){ // method to display the menu screen items
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(new Color(245,245,220)); // setting the color
        g.drawImage(bagBackground,250,110,null); // drawing the background
        g.drawImage(bag,280,230,null); // drawing the bag picture
        g.fillRect(530,110,235,500); // drawing a white rectangle around the background
        g.drawImage(pointer , posX , posY, null); // drawing the pointer
        Font titleFont = new Font("Consolas", 0, 35); // declaring the title font
        Font itemFont = new Font("Consolas", 0, 20); // declaring the item font
        g2d.setFont(titleFont); // setting the title font
        g2d.setColor(Color.black); // re-setting the color
        g2d.drawString("Items", 593, 165); // drawing the items title
        g2d.setFont(itemFont); // setting the item font

        for (int i = 0; i<10;i++){ // looping through each item and drawing the name
            if (i ==6){ // skipping item 6 since it cannot be bought
                i++;
            }
            if (i < 7) { // if i is above 7 then re-adjust the offsets
                g2d.drawString(itemNames[i], 570, 225 + i * 40);
                g2d.drawString("x" + nums[i], 725, 225 + i * 40);
            }
            else{ // if i is below 7 then use different offsets
                g2d.drawString(itemNames[i], 570, 185 + i * 40);
                g2d.drawString("x" + nums[i], 725, 185 + i * 40);
            }
        }
        g2d.drawString("Exit", 570, 585); // drawing the exit text

    }
    public static int getPosY(){return posY;} // getter for the Y position of the pointer arrow
    public static void resetPosY(){
        posY = 207;
    } // method to reset the Y position of the pointer arrow
    public static void setPosY(int val){ // setter for the Y position of the pointer arrow
        if (posY + val >= 207 && posY + val <= 567) { // only setting it if it is within the range that has items
            posY += val;
        }
    }
}
