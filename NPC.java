import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NPC {
    private Image sprite;
    private boolean battlable;

    public NPC (Image pic){
        sprite = pic;
        battlable = true;
    }

    public Image getSprite(){
        return sprite;
    }

    public boolean getBattlable(){
        return battlable;
    }

}
