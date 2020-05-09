import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Textbox {
    private static int count, textX, textY;
    private static Image textBox;
    private static String[][] words = new String[1][1];

    public Textbox() throws IOException {
        textX = 0;
        textY = 640;
        count = 0;
        textBox = ImageIO.read(new File("Images/Text/Textbox.png"));

        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Textbox")));
        for (int i = 0; i < 1; i++) {
            String line = inFile.nextLine();
            words[i][0] = line;
        }
    }

    public static void display(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font optionFont = new Font("Consolas", 0, 20);
        g2d.setFont(optionFont);
        g.setColor(new Color(0, 0, 0));
        g.drawImage(textBox, 222, 595, null);
        textX = 0;
        for (int i = 0; i < count; i++) {
            if (250 + textX * 12 < 700) {
                g2d.drawString(String.valueOf(words[0][0].charAt(i)), 250 + textX * 12, textY);
                textX += 1;
            } else {
                textX = 0;
                if (textY + 30 > 750) {
                    textY = 640;
                }
                else {
                    textY += 30;
                         }
                    }
                }
                if (count < 58) {
                    count += 1;
                }
            }
        }

