//Kevin Cui
//TitleScreen.java
//Class for the title page of the game. This will display the opening screen as well as buttons to start the game and
//open an instructions screen/


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TitleScreen { // class for the game title screen
    private static Image background,background2,arrowPointer,arrowPointer2,menuBox1,menuBox2,title,instructions,prof; // declaring images that will be displayed
    private static int posY; // Y position for a moving arrow
    private static boolean instructionMenu; // boolean to check if instructions screen is open

    public TitleScreen() throws IOException {
        posY = 305; // initializing moving arrow Y position
        // Loading all pictures that will be displayed
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

    public static void draw(Graphics g){ // method to draw the title screen
        Graphics2D g2d = (Graphics2D) g;
        Font optionFont = new Font("Consolas", 0, 35); // initializing font
        g2d.setFont(optionFont); // setting font
        g.setColor(new Color(0, 0, 0)); // setting color
        g.fillRect(0,0,956,795); // filling the screen black to draw over previous images
        g.drawImage(title,300,0,null); // drawing the MasseyMon title image
        g.drawImage(background,0,129,null); // drawing the Pokemon background image
        g.drawImage(menuBox1,370,300,null); // drawing the red start game box
        g.drawImage(menuBox2,370,500,null); // drawing the blue instructions box
        g.drawImage(arrowPointer,310,posY,null); // drawing the arrow pointer
        g2d.drawString("Start Game",400,340); // writing "Start Game" in the red box
        g2d.drawString("Instructions",380,540); // writing "Instructions" in the blue box
        if (instructionMenu){ // if the instructions screen is pressed
            g.fillRect(0,0,956,795); // filling the screen black to draw over it
            g.drawImage(background2,0,94,null); // drawing another Pokemon background
            g.drawImage(prof,420,0,null); // drawing Professor Oak
            g.drawImage(instructions,150,150, null); // drawing the image that displays the instructions
            g.setColor(new Color(255, 0, 0)); // setting the color red for the back button
            g.drawImage(arrowPointer2,770,710,null); // drawing the arrow pointer image
            g2d.drawString("Back",850,740); // writing the back button so the user can return to the main screen
        }
    }

    public static int getPosY(){
        return posY;
    } // getter for the Y position of the arrow
    public static void setPosY(int val){
        posY = val;
    } // setter for the Y position of the arrow
    public static boolean getInstructionMenu(){ // getter for if the instructions screen is open
        return instructionMenu;
    }
    public static void setInstructionMenu(boolean b){ // setter for the instruction screen boolean
        instructionMenu = b;
    }
}
