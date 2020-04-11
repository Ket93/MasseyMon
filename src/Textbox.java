import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Textbox {
    private static Image textBox;

    public Textbox () throws IOException {
        textBox = ImageIO.read(new File("Images/Text/Textbox.png"));
    }

    public static void display (Graphics g){
        g.drawImage(textBox,222,595,null);
    }
}
