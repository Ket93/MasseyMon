import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class pokeMap {

    private Image map;
    private BufferedImage mask;
    private int startPosX, startPosX2, startPosY, startPosY2,startPosX3,startPosY3, mapX, mapY, mapHeight, mapWidth;


    public pokeMap(Image background, BufferedImage backgroundMask, String line) throws FileNotFoundException {

        String[] pos = line.split(",");
        map = background;
        mask = backgroundMask;
        startPosX = Integer.parseInt(pos[0]);
        startPosY = Integer.parseInt(pos[1]);
        startPosX2 = Integer.parseInt(pos[2]);
        startPosY2 = Integer.parseInt(pos[3]);
        startPosX3 = Integer.parseInt(pos[4]);
        startPosY3 = Integer.parseInt(pos[5]);
        mapX = (956 - background.getWidth(null)) / 2;
        if (background.getWidth(null) > 956) {
            mapX = 0;
        }
        mapY = (795 - background.getHeight(null)) / 2;
        if (background.getHeight(null) > 795) {
            mapY = 0;
        }
        mapHeight = background.getHeight(null);
        mapWidth = background.getWidth(null);

    }

    public Image getMap() {
        return map;
    }

    public BufferedImage getMask() {
        return mask;
    }

    public int getStartPosX() {
        return startPosX;
    }

    public int getStartPosY() {
        return startPosY;
    }

    public int getStartPosX2() {
        return startPosX2;
    }

    public int getStartPosY2() {
        return startPosY2;
    }

    public int getStartPosX3() {
        return startPosX3;
    }

    public int getStartPosY3() {
        return startPosY3;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }
}