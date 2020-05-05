import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class pokeMap {

    private Image map;
    private BufferedImage mask;
    private int startPosX, startPosX2, startPosY, startPosY2,startPosX3,startPosY3,startPosX4,startPosY4,startPosX5,startPosY5,startPosX6,startPosY6,startPosX7,startPosY7, mapX, mapY, mapHeight, mapWidth;


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
        startPosX4 = Integer.parseInt(pos[6]);
        startPosY4 = Integer.parseInt(pos[7]);
        startPosX5 = Integer.parseInt(pos[8]);
        startPosY5 = Integer.parseInt(pos[9]);
        startPosX6 = Integer.parseInt(pos[10]);
        startPosY6 = Integer.parseInt(pos[11]);
        startPosX7 = Integer.parseInt(pos[12]);
        startPosY7 = Integer.parseInt(pos[13]);
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

    public int getStartPosX4() {
        return startPosX4;
    }

    public int getStartPosY4() {
        return startPosY4;
    }

    public int getStartPosX5() {
        return startPosX5;
    }

    public int getStartPosY5() {
        return startPosY5;
    }

    public int getStartPosX6() {
        return startPosX4;
    }

    public int getStartPosY6() {
        return startPosY4;
    }

    public int getStartPosX7() {
        return startPosX5;
    }

    public int getStartPosY7() {
        return startPosY5;
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