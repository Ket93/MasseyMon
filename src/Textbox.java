import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Textbox {
    private static int count, textX, textY,pauseCount,box,arraySize,temp;
    private static boolean boxFull,textWriting;
    private static Image textBox;
    private static String[][] words = new String[28][2];
    private static int [] wordLen = new int [56];

    public Textbox() throws IOException {
        arraySize = 0;
        box = 0;
        boxFull = false;
        pauseCount = 0;
        textX = 0;
        textY = 640;
        count = 0;
        textWriting = true;
        textBox = ImageIO.read(new File("Images/Text/Textbox.png"));

        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Textbox")));
        for (int i = 0; i < 28; i++) {
            for (int k = 0; k<2; k++) {
                String line = inFile.nextLine();
                words[i][k] = line;
            }
        }

        Scanner myFile = new Scanner(new BufferedReader(new FileReader("Data/TextboxCharacterLength")));
        for (int i = 0; i < 56; i++) {
            int val = myFile.nextInt();
            wordLen[i] = val;
        }
    }

    public static void display(Graphics g, int index, boolean space, boolean one, boolean two, boolean three, boolean four
    ,boolean five, boolean six, boolean seven, boolean eight, boolean nine) {
        Graphics2D g2d = (Graphics2D) g;
        Font optionFont = new Font("Consolas", 0, 20);
        g2d.setFont(optionFont);
        g.setColor(new Color(0, 0, 0));
        g.drawImage(textBox, 222, 595, null);
        textX = 0;
        textWriting = true;
        arraySize = words[index].length;
        if (words[index][1] != null) {
            if (words[index][1].length() < 2) {
                words[index][1] = null;
            }
        }
        if (words[index][1] == null){
            arraySize -= 1;
        }
        if (box == arraySize -1 && space && count == wordLen[box+index*2]){
            count = 0;
            box = 0;
            textWriting = false;
            if (index ==1){
                GamePanel.setStarter(true);
            }
        }
        else if (index == 3 && box == arraySize -1 && space && count == wordLen[box+index*2]){
            count = 0;
            box = 0;
            textWriting = false;
            if (one){
                Items.addItem(0);
                Player.loseMoney(300);
            }
            else if (two){
                Items.addItem(1);
                Player.loseMoney(700);
            }
            else if (three){
                Items.addItem(2);
                Player.loseMoney(1500);
            }
            else if (four){
                Items.addItem(3);
                Player.loseMoney(2500);
            }
            else if (five){
                Items.addItem(4);
                Player.loseMoney(3000);
            }
            else if (six){
                Items.addItem(5);
                Player.loseMoney(1500);
            }
            else if (seven){
                Items.addItem(7);
                Player.loseMoney(200);
            }
            else if (eight){
                Items.addItem(8);
                Player.loseMoney(600);
            }
            else if (nine){
                Items.addItem(9);
                Player.loseMoney(1200);
            }
        }

        for (int i = 0; i < count; i++) {
            if (textX * 12 < 450) {
                g2d.drawString(String.valueOf(words[index][box].charAt(i)), 250 + textX * 12, textY);
                textX += 1;
            }
            else {
                textY += 30;
                if (textY == 760 && textX == 38) {
                    boxFull = true;
                    if (space){
                        boxFull = false;
                        textY = 640;
                        box +=1;
                        count = 0;
                    }
                }
                else {
                    g2d.drawString(String.valueOf(words[index][box].charAt(i)), 250, textY);
                }
                textX = 1;
                    }
                }
                if (pauseCount%5 == 0) {
                    if (count < wordLen[box+index*2]) {
                        if (boxFull == false) {
                            count += 1;

                        }
                    }
                }
                pauseCount += 1;
                textY = 640;
            }
            public static boolean getTextWriting(){
                return textWriting;
            }
            public static void setTextWriting(boolean temp){
                textWriting = temp;
    }
        }

