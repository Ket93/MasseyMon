//Kevin Cui
//Textbox.java
//Class draws text by displaying character by character. Draws text for NPC's that the user can interact with in the game.
//Also allows the user to buy items from the pokeMart after the text is drawn.


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Textbox { // class for text that NPC's say
    private static int count, textX, textY,pauseCount,box,arraySize; // declaring ints for the class
    private static boolean boxFull,textWriting; // declaring booleans
    private static Image textBox; // declaring the text box image
    private static String[][] words = new String[28][2]; // declaring the 2D array of words that will be displayed
    private static int [] wordLen = new int [56]; // declaring the array of lengths of each line

    public Textbox() throws IOException { // constructor method
        arraySize = 0; // tracking size of each words array
        box = 0; // tracking which line of text the user is on
        boxFull = false; // checking if all characters in a line have been displayed
        pauseCount = 0; // slowing down drawing rate of text
        textX = 0; // X offset for where text will be displayed
        textY = 640; // Y position of where text will be displayed
        count = 0;  // counter for which character the loop is on
        textWriting = true; // boolean for if text is currently writing or not
        textBox = ImageIO.read(new File("Images/Text/Textbox.png")); // loading textbox image

        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Textbox"))); // loading words from data file
        for (int i = 0; i < 28; i++) { // outer loop
            for (int k = 0; k<2; k++) { // each text block has 2 lines
                String line = inFile.nextLine(); // getting the line from the file
                words[i][k] = line; // adding the line to the proper position in the array
            }
        }

        Scanner myFile = new Scanner(new BufferedReader(new FileReader("Data/TextboxCharacterLength"))); // loading the ints of the lengths of the lines
        for (int i = 0; i < 56; i++) { // for each line in the file
            int val = myFile.nextInt(); // setting each line to an int
            wordLen[i] = val; // adding that int to the array
        }
    }

    public static void display(Graphics g, int index, boolean space, boolean one, boolean two, boolean three, boolean four
    ,boolean five, boolean six, boolean seven, boolean eight, boolean nine) { // method to display the textbox writing
        Graphics2D g2d = (Graphics2D) g;
        Font optionFont = new Font("Consolas", 0, 20); // declaring font
        g2d.setFont(optionFont); // setting font
        g.setColor(new Color(0, 0, 0)); // setting colour
        g.drawImage(textBox, 222, 595, null); // drawing the textbox image
        textX = 0; // starting the X offset at 0
        textWriting = true; // setting true since text will be writing
        arraySize = words[index].length; // setting arraySize to how big the array at that index is
        if (words[index][1] != null) { // making sure the second line isn't already null
            if (words[index][1].length() < 2) { // if it only has 1 character then make it null for 1 line texts
                words[index][1] = null;
            }
        }
        if (words[index][1] == null){ // if the second line is null subtract it from arraySize
            arraySize -= 1;
        }
        if (box == arraySize -1 && space && count == wordLen[box+index*2]){ // if the user reaches the end of the last line that contains text
            count = 0; // reset count
            box = 0; // reset box
            textWriting = false; // set to false since it isn't writing anymore
            if (index ==1){ // if the user is talking to prof Oak then set starter to true
                GamePanel.setStarter(true);
            }
        }
        else if (index == 3 && box == arraySize -1 && space && count == wordLen[box+index*2]){ // if the user is in the PokeMart
            count = 0; // reset count
            box = 0; // reset box
            textWriting = false; // set to false since text isn't writing anymore
            // Depending on which number the user clicks give them the correct item and subtract the money if they can afford it
            if (one){ // potion
                if (Player.getMoney() >= 300) {
                    Items.addItem(0);
                    Player.loseMoney(300);
                }
            }
            else if (two){ // super potion
                if (Player.getMoney() >= 700) {
                    Items.addItem(1);
                    Player.loseMoney(700);
                }
            }
            else if (three){ // hyper potion
                if (Player.getMoney() >= 1500) {
                    Items.addItem(2);
                    Player.loseMoney(1500);
                }
            }
            else if (four){ // max potion
                if (Player.getMoney() >= 2500) {
                    Items.addItem(3);
                    Player.loseMoney(2500);
                }
            }
            else if (five){ // full restore
                if (Player.getMoney() >= 3000) {
                    Items.addItem(4);
                    Player.loseMoney(3000);
                }
            }
            else if (six){ // revive
                if (Player.getMoney() >= 1500) {
                    Items.addItem(5);
                    Player.loseMoney(1500);
                }
            }
            else if (seven){ // poke ball
                if (Player.getMoney() >= 200) {
                    Items.addItem(7);
                    Player.loseMoney(200);
                }
            }
            else if (eight){ // great ball
                if (Player.getMoney() >= 600) {
                    Items.addItem(8);
                    Player.loseMoney(600);
                }
            }
            else if (nine){ // ultra ball
                if (Player.getMoney() >= 1200) {
                    Items.addItem(9);
                    Player.loseMoney(1200);
                }
            }
        }

        for (int i = 0; i < count; i++) { // loop for drawing the texxt
            if (textX * 12 < 450) { // if the X position is within the text box
                g2d.drawString(String.valueOf(words[index][box].charAt(i)), 250 + textX * 12, textY); // draw the text with the X and Y offsets
                textX += 1; // add one to the X offset so the next letter is after it
            }
            else { // at the end of a line
                textY += 30; // add to the Y position
                if (textY == 760 && textX == 38) { // if the textbox is full
                    boxFull = true; // set the boxFull boolean to true
                    if (space){ // if the user presses space go to the next box or end the text
                        boxFull = false; // resetting the box full variable
                        textY = 640; // resetting X offset
                        box +=1; // adding one to box to go to the next line if there is one
                        count = 0; // resetting count
                    }
                }
                else { // if the text is not at the end of the box
                    g2d.drawString(String.valueOf(words[index][box].charAt(i)), 250, textY); // draw the first character of the next line
                }
                textX = 1; // set offsetX to one more than it should be to start at second character
                    }
                }
                if (pauseCount%5 == 0) { // only drawing a character every 5 frames
                    if (count < wordLen[box+index*2]) { // only adding to count if it is less than the length of the line
                        if (boxFull == false) { // if the box is not full
                            count += 1; // add to count

                        }
                    }
                }
                pauseCount += 1; // add to the pause count every frame
                textY = 640; // reset the position
            }
            public static boolean getTextWriting(){ // getter for if text is writing
                return textWriting;
            }
            public static void setTextWriting(boolean temp){ // setter for if text is writing
                textWriting = temp;
    }
        }

