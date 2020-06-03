import java.awt.*;
import java.awt.image.BufferedImage;

public class pokeMapMini { // class for mini background maps (houses, gyms, Pokemon Center, etc)

    private Image map; // image of the map
    private BufferedImage mask; // image of the mask
    private int startPosX, startPosY, mapX, mapY, mapHeight, mapWidth;

    public pokeMapMini (Image background, BufferedImage backgroundMask, String line){ // constructor

        String [] pos = line.split(","); // splitting the line into an array to assign to variables
        map = background; // setting map pic
        mask = backgroundMask; // setting mask pic
        startPosX = Integer.parseInt(pos[0]); // setting the X position where the character will spawn
        startPosY = Integer.parseInt(pos[1]); // setting the Y position where the character will spawn
        mapX = (956 - background.getWidth(null)) / 2; //
        if (background.getWidth(null) > 956){
            mapX = 0;
        }
        mapY = (795 - background.getHeight(null)) / 2;
        if (background.getHeight(null) > 795){
            mapY = 0;
        }
        mapHeight = background.getHeight(null);
        mapWidth = background.getWidth(null);
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
