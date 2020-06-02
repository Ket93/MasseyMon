import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class pokeMap { // class for background maps

    private Image map; // image of the map
    private BufferedImage mask; // mask of the map
    private int startPosX, startPosX2, startPosY, startPosY2,startPosX3,startPosY3,startPosX4,startPosY4,startPosX5,startPosY5,startPosX6,startPosY6,startPosX7,startPosY7, mapX, mapY, mapHeight, mapWidth;
    private ArrayList<ArrayList<Pokemon>> trainerPokes;
    public pokeMap(Image background, BufferedImage backgroundMask, String line,ArrayList<ArrayList<Pokemon>> enemyTrainers) throws FileNotFoundException { // constructor
        String[] pos = line.split(","); // splitting the line to get the information and store as variables
        map = background; // setting image
        mask = backgroundMask; // setting mask
        startPosX = Integer.parseInt(pos[0]); // setting X positions where user can appear on the map
        startPosY = Integer.parseInt(pos[1]); // setting Y positions where user can appear on the map
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
        trainerPokes = enemyTrainers; // setting enemy trainer's Pokemon
        mapX = (956 - background.getWidth(null)) / 2; // setting where the map will be drawn. If width is less than screen size draw the map centered
        if (background.getWidth(null) > 956) { // if width is larger than screen size draw at 0
            mapX = 0;
        }
        mapY = (795 - background.getHeight(null)) / 2; // If height is less than screen size draw the map centered
        if (background.getHeight(null) > 795) { // if width is larger than screen size draw at 0
            mapY = 0;
        }
        mapHeight = background.getHeight(null); // setting the map height
        mapWidth = background.getWidth(null); // setting the map width

    }
    public ArrayList<Pokemon> getEnemyTrainers(int i){
        return trainerPokes.get(i);
    } //gets the enemy trainer's Pokemon
    public Image getMap() {
        return map;
    } // getter for map pic
    public void addTrainerPokes(ArrayList<Pokemon> pokes){
        trainerPokes.add(pokes);
    } // adding enemy Pokemon
    public BufferedImage getMask() {
        return mask;
    } // getter for mask

    public int getStartPosX() {
        return startPosX;
    } // getters for all X positions where player can spawn

    public int getStartPosY() {
        return startPosY;
    } // getters for all Y positions where player can spawn

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
        return startPosX6;
    }

    public int getStartPosY6() {
        return startPosY6;
    }

    public int getStartPosX7() {
        return startPosX7;
    }

    public int getStartPosY7() {
        return startPosY7;
    }

    public int getMapX() {
        return mapX;
    } // getter for X coordinate of where to draw map

    public int getMapY() {
        return mapY;
    } // getter for Y coordinate of where to draw map

    public int getMapHeight() {
        return mapHeight;
    } // getter for height of the map

    public int getMapWidth() {
        return mapWidth;
    } // getter for width of the map
}