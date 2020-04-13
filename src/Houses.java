import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Houses {
    private static Image oakLab;

    public Houses() throws IOException {
        oakLab = ImageIO.read(new File("Images/Buildings/ProfessorOakLab.png"));
    }

    public static void displayLab (Graphics g){
        g.drawImage(oakLab,0,0,null);
    }
}
