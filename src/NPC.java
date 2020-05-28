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
    private boolean talkable;

    public NPC(Image pic) {
        sprite = pic;
        battlable = true;
        talkable = true;
    }

    public Image getSprite() {
        return sprite;
    }

    public boolean getBattlable() {
        return battlable;
    }
}
