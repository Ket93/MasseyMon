//Kevin Cui
//Menu.java
//Class for the menu screen. This class allows the user to select and go into the item or Pokemon menu classes.
//It will display a box with the options for those menus and an exit

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Menu { // class for the home menu screen
    private static Image pointer; // declaring pointer image
    private static int posX,posY; // declaring X and Y positions for the pointer

    public Menu() throws IOException { // constructor methods
        posX = 605; // initializing pointer X position
        posY = 186; // initializing pointer Y position
        pointer = ImageIO.read(new File("Images/Menu/MenuPointer.png")); // loading the pointer image
    }
    public static void display(Graphics g){ // method to display the menu box
        Graphics2D g2d = (Graphics2D)g;
        int borderWidth = 5; // setting the borderWidth of the rectangle
        ((Graphics2D) g).setStroke(new BasicStroke(borderWidth)); // setting the border width
        Font optionFont = new Font("Consolas", 0, 27); // initializing the font
        g.setColor(new Color(255,255,255)); // setting the colour
        g.fillRect(600,150, 160,200); // drawing the white rectangle
        g.setColor(new Color(120,105,200)); // setting new color
        g.drawRect(600,150,160,200); // drawing the border rect

        g.drawImage(pointer , posX , posY, null); // drawing the pointer arrow
        g2d.setFont(optionFont); //  setting the font
        g2d.setColor(Color.black); // setting the font color
        g2d.drawString("Pokemon", 643, 205); // writing the Pokemon option
        g2d.drawString("Items", 643, 245); // writing the Items option
        g2d.drawString("Exit", 643, 285); // writing the Exit option
    }

    public static int getPosY(){return posY;} // getter method for the Y position of the pointer

    public static void resetPosY(){
        posY = 186;
    } // method to reset the Y position of the pointer when the user leaves the screen

    public static void setPosY(int val){ // setter for the Y position of the pointer
        if (posY + val >= 186 && posY + val <= 266) { // if adding the value will still put it in range of one of the options then add the value
            posY += val;
        }
    }

}
