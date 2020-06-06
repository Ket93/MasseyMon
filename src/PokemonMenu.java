import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PokemonMenu { // class for the "Pokemon" section of the menu screen
    private static boolean moveX,displayButton; // declaring boolean for if X is moving and if the user is in the right position for the display button
    private static int posX,posY; // declaring ints for the position of the red outline box
    private static Image background,pointer; // declaring images for the background and the pointer arrow

    public PokemonMenu() throws IOException { // constructor method
        displayButton = false; // initializing the display button as false
        moveX = true; // initializing moveX as true
        posX = 161; // initializing X position of the outline box
        posY = 110; // initializing the Y position of the outline box
        pointer = ImageIO.read(new File("Images/Menu/MenuPointer.png")); // loading pointer image
        background = ImageIO.read(new File("Images/Menu/PokemonBackground.png")); // loading background image
    }

    public static void display (Graphics g){ // method to display the Pokemon menu screen
        Graphics2D g2d = (Graphics2D)g;
        g.drawImage(background,155,100,null); // drawing the background image
        g.setColor(new Color(255,165,0)); // setting the colour
        g.drawRect(152,100,653,541); // drawing the outer rectangle

        g.setColor(new Color(0,0,0)); // setting the colour to black to draw the inner rects
        // drawing the 6 inner rects that will contain the 6 Pokemon's information
        g.drawRect(161,110,316,150);
        g.drawRect(161,260,316,150);
        g.drawRect(161,410,316,150);
        g.drawRect(477,130,320,150);
        g.drawRect(477,280,320,150);
        g.drawRect(477,430,320,150);

        g.setColor(new Color(255,0,0)); // setting the colour to red
        if (!displayButton) { // if dispayButton is false then draw the red outline
            g.drawRect(posX, posY, 316, 150);
        }

        Font optionFont = new Font("Consolas", 0, 35); // initializing the font
        g2d.setFont(optionFont); // setting the font
        g2d.setColor(Color.black); // setting the font colour
        // drawing the numbers to label each of the six boxes
        g.drawString("1.", 175,150);
        g.drawString("3.", 175,295);
        g.drawString("5.", 175,445);
        g.drawString("2.", 500,165);
        g.drawString("4.", 500,315);
        g.drawString("6.", 500,470);
        g.drawString("Exit",600,622);

        for (int i =0;i<MasseyMon.frame.getMyPokes().size();i++){
            MasseyMon.frame.getMyPokes().get(i).drawMenu(g,i); // calling the drawMenu method to draw the information for  each Pokemon the user has
        }

        if (displayButton){ // if the user is in range of the bottom the draw the pointer image
            g.drawImage(pointer,560,600,null);
        }
    }

    public static void resetPosXY() { // method that resets the X and Y position of the red outline for when the user leaves the screen
        posY = 110;
        posX = 161;
        displayButton = false; // Also set displayButton to false
    }
    public static void setPosY(){ // method to set the Y position so that the display box moves down
        if (posY == 430){ // if the Y position is tat the bottom then set displayButton to true and don't move the X position
            moveX = false;
            displayButton = true;
        }
        else { // if the Y position is not at the bottom then keep displayButton false
            displayButton = false;
            if (posY == 110 || posY == 260 || posY == 410) { // if the Y position is at one of these only add 20 to it
                posY += 20;
            } else { // if it is at a position that requires a big drop add 130 to it
                posY += 130;
            }
            moveX = true; // after the Y position changes move X can become true
        }
    }
    public static void setPosYUP() { // setting the Y position if the user is going up the boxes
        if (displayButton){ // if the displayButton is true and going up then set the Y position to 430
            posY = 430;
        }
        else{ // if the displayButton is not true
        if (posY ==110){ // if the Y position is already at the top do not let X move
            moveX = false;
        }
        else { // if the Y position is not at the top
            if (posY == 130 || posY == 280 || posY == 430) { // if it is in a position of a small jump then subtract 20
                posY -= 20;
            } else { // if it is in a position of a big jump then subtract 130
                posY -= 130;
            }
            moveX = true; // allow the X position to move
          }
        }
        displayButton = false; // make displayButton false since it isn't at the bottom
    }

    public static void setPosX() { // method to set the X position of the outline
        if (moveX) { // if X is allowed to move
            if (posX == 161) { // if it is currently on the left move it to the right by adding
                posX += 316;
            } else { // otherwise if it is already on the right side the subtract to moe it to the left side
                posX -= 316;
            }
        }
    }
    public static boolean getDisplayButton(){return displayButton;} // getter method for displabyButton
}
