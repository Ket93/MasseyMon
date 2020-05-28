import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NPC{
    private Image sprite;
    private boolean battlable;
    private int positionX,positionY;

    public NPC(Image pic) {
       // String positions [] = line.split(",");
        sprite = pic;
        battlable = true;
      //  positionX = Integer.parseInt(positions[0]);
      //  positionY = Integer.parseInt(positions[1]);
    }

    public Image getSprite() {
        return sprite;
    }

    public boolean getBattlable() {
        return battlable;
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }
}
