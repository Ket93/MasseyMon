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
    private static String[][] words = new String[4][2];
    private static int [] wordLen = new int [8];

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
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k<2; k++) {
                String line = inFile.nextLine();
                words[i][k] = line;
            }
        }

        Scanner myFile = new Scanner(new BufferedReader(new FileReader("Data/TextboxCharacterLength")));
        for (int i = 0; i < 8; i++) {
            int val = myFile.nextInt();
            wordLen[i] = val;
        }
    }

    public static void display(Graphics g, int index, boolean space) {
        Graphics2D g2d = (Graphics2D) g;
        Font optionFont = new Font("Consolas", 0, 20);
        g2d.setFont(optionFont);
        g.setColor(new Color(0, 0, 0));
        g.drawImage(textBox, 222, 595, null);
        textX = 0;
        textWriting = true;
        arraySize = words[index].length;

        if (box == arraySize -1 && space && count == wordLen[box+index*2]){
            count = 0;
            box = 0;
            textWriting = false;
            if (index ==1){
                GamePanel.setStarter(true);
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

