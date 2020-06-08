//Kevin Cui
//pokeMapMini.java
//Class for mini backgrounds. Each background contains some mini backgrounds that you can enter from the background.
//Contains the image, mask, where it should be drawn, dimensions, and where the player will spawn on the image.

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
        mapX = (956 - background.getWidth(null)) / 2; // drawing image X position centered if its smaller than screen
        if (background.getWidth(null) > 956){ // if image is bigger than screen then draw it at 0
            mapX = 0;
        }
        mapY = (795 - background.getHeight(null)) / 2; // drawing image Y position centered if its smaller than screen
        if (background.getHeight(null) > 795){ // if image is bigger than screen then draw it at 0
            mapY = 0;
        }
        mapHeight = background.getHeight(null); // getting height of the image
        mapWidth = background.getWidth(null); // getting width of an image
    }

    public Image getMap(){
        return map;
    } // gets the map of the image

    public BufferedImage getMask(){
        return mask;
    } // gets the mask of the image

    public int getStartPosX(){
        return startPosX;
    } // gets X position to draw the player at when they enter the image

    public int getStartPosY(){
        return startPosY;
    } // gets Y position to draw the player at when they enter the image

    public int getMapX(){
        return mapX;
    } // gets the X position the image is supposed to be drawn at

    public int getMapY(){
        return mapY;
    } // gets the Y position the image is supposed to be drawn at

    public int getMapHeight(){
        return mapHeight;
    } // gets the height of image

    public int getMapWidth(){
        return mapWidth;
    } // gets the width of the image
}
