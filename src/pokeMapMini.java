import java.awt.*;
import java.awt.image.BufferedImage;

public class pokeMapMini {

    private Image map;
    private BufferedImage mask;
    private int startPosX, startPosY, mapX, mapY, mapHeight, mapWidth;

    public pokeMapMini (Image background, BufferedImage backgroundMask, String line){

        String [] pos = line.split(",");
        map = background;
        mask = backgroundMask;
        startPosX = Integer.parseInt(pos[0]);
        startPosY = Integer.parseInt(pos[1]);
        mapX = (956 - background.getWidth(null)) / 2;
        mapY = (795 - background.getHeight(null)) / 2;
        mapHeight = background.getHeight(null);
        mapWidth = background.getWidth(null) / 2;
    }

    public Image getMap(){
        return map;
    }

    public BufferedImage getMask(){
        return mask;
    }

    public int getStartPosX(){
        return startPosX;
    }

    public int getStartPosY(){
        return startPosY;
    }

    public int getMapX(){
        return mapX;
    }

    public int getMapY(){
        return mapY;
    }

    public int getMapHeight(){
        return mapHeight;
    }

    public int getMapWidth(){
        return mapWidth;
    }
}
