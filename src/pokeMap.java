import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class pokeMap {
    private int posIndex, picIndex, miniPosIndex, miniPicIndex;
    private Image[] backgrounds;
    private Image[][] miniBackgrounds;
    private BufferedImage[] backgroundMasks;
    private BufferedImage[][] miniBackgroundMasks;
    private Rectangle[] positions;
    private Rectangle[][] miniPositions;
    private int[][] myGuyPositions, miniMyGuyPositions;
    private int myGuyPositionCount, miniMyGuyPositionCount;

    private Image map;
    private BufferedImage mask;
    private int startPosX, startPosX2, startPosY, startPosY2, mapX, mapY, mapHeight, mapWidth;


    public pokeMap(Image background, BufferedImage backgroundMask, String line) throws FileNotFoundException {

        String[] pos = line.split(",");
        map = background;
        mask = backgroundMask;
        startPosX = Integer.parseInt(pos[0]);
        startPosY = Integer.parseInt(pos[1]);
        startPosX2 = Integer.parseInt(pos[2]);
        startPosY2 = Integer.parseInt(pos[3]);
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

    public int getStartPosX2(){
        return startPosX2;
    }

    public int getStartPosY2(){
        return startPosY2;
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
















/*
        picIndex = 0;
        posIndex = -1;
        miniPosIndex = -1;
        miniPicIndex = 0;
        myGuyPositionCount = 0;
        miniMyGuyPositionCount = 0;
        backgrounds = new Image[2];
        for (int i = 0; i < 4; i++) {
            String path = String.format("%s/%s/%s%d.png", "Images", "Backgrounds", "Background", i);
            try {
                Image pic = ImageIO.read(new File(path));
                backgrounds[i] = pic;
            }
            catch (IOException e) {
            }
        }

        miniBackgrounds = new Image[1][2];
        for (int k = 0; k <1; k++){
            for (int i = 0; i < 2; i++) {
                String path = String.format("%s/%s/%s%d/%s%d.png", "Images", "MiniBackgrounds","Town ",k, "MiniBackground", i);
                try {
                    Image pic = ImageIO.read(new File(path));
                    miniBackgrounds[k][i] = pic;
                } catch (IOException e) {
              }
            }
        }

        backgroundMasks = new BufferedImage[2];
        for (int i = 0; i < 4; i++) {
            String path = String.format("%s/%s/%s%d%s.png", "Images", "Masks", "Background", i, "Mask");
            try {
                BufferedImage pic = ImageIO.read(new File(path));
                backgroundMasks[i] = pic;
            }
            catch (IOException e) {
            }
        }

        miniBackgroundMasks = new BufferedImage[1][2];
        for (int k = 0; k<1; k++){
            for (int i = 0; i < 2; i++) {
                String path = String.format("%s/%s/%s%d/%s%d%s.png", "Images", "MiniMasks", "Town ",k,"Background", i, "Mask");
                try {
                    BufferedImage pic = ImageIO.read(new File(path));
                    miniBackgroundMasks[k][i] = pic;
                } catch (IOException e) {
                }
            }
        }

        positions = new Rectangle[2];
        for (int i = 0; i < 2; i++) {
            Rectangle rect = new Rectangle((956 - backgrounds[i].getWidth(null)) / 2, (795 - backgrounds[i].getHeight(null)) / 2, backgrounds[i].getWidth(null), backgrounds[i].getHeight(null));
            positions[i] = rect;
        }

        miniPositions = new Rectangle [1][2];
        for (int i = 0; i<1; i++) {
            for (int k = 0; k < 2; k++) {
                Rectangle myRect = new Rectangle((956 - miniBackgrounds[i][k].getWidth(null)) / 2, (795 -miniBackgrounds[i][k].getHeight(null)) / 2, miniBackgrounds[i][k].getWidth(null), miniBackgrounds[i][k].getHeight(null));
                miniPositions[i][k] = myRect;
            }
        }

        myGuyPositions = new int [1][4];
        Scanner inFile = new Scanner (new BufferedReader( new FileReader("Data/PlayerPositions.txt")));
        while (inFile.hasNextLine()){
            String line = inFile.nextLine();
            String [] coordinates = line.split(",");
            myGuyPositions[myGuyPositionCount][0] = Integer.parseInt(coordinates[0]);
            myGuyPositions[myGuyPositionCount][1] = Integer.parseInt(coordinates[1]);
            myGuyPositions[myGuyPositionCount][2] = Integer.parseInt(coordinates[2]);
            myGuyPositions[myGuyPositionCount][3] = Integer.parseInt(coordinates[3]);
            myGuyPositionCount ++;
        }

        miniMyGuyPositions = new int [2][4];
        Scanner myFile = new Scanner (new BufferedReader( new FileReader("Data/miniPlayerPositions")));
        while (myFile.hasNextLine()){
            String line = myFile.nextLine();
            String [] coordinates = line.split(",");
            miniMyGuyPositions[myGuyPositionCount][0] = Integer.parseInt(coordinates[0]);
            miniMyGuyPositions[myGuyPositionCount][1] = Integer.parseInt(coordinates[1]);
            miniMyGuyPositions[myGuyPositionCount][2] = Integer.parseInt(coordinates[2]);
            miniMyGuyPositions[myGuyPositionCount][3] = Integer.parseInt(coordinates[3]);
            miniMyGuyPositionCount ++;
        }
    }

    public Image getBackground(boolean mini){
        if (mini){
            return miniBackgrounds[picIndex][miniPicIndex];
        }
        return backgrounds[picIndex];
    }

    public BufferedImage getBackgroundMask(boolean mini){
        if (mini){
            return miniBackgroundMasks[picIndex][miniPicIndex];
        }
        return backgroundMasks[picIndex];
    }

    public int getPositionX(boolean mini){
        if (mini){
            return (int) miniPositions[picIndex][miniPicIndex].getX();
        }
        return (int) positions[picIndex].getX();
    }

    public int getPositionY(boolean mini){
        if (mini){
            return (int) miniPositions[picIndex][miniPicIndex].getY();
        }
        return (int) positions[picIndex].getY();
    }

    public int getPicIndex(){
        return picIndex;
    }

    public void setPicIndex (int n){
        picIndex += n;
    }

    public int getPosIndex(){
        return posIndex;
    }
    public void setPosIndex(int n){
        posIndex += n;
    }

    public int getMiniPosIndex(){
        return miniPosIndex;
    }
    public void setMiniPosIndex(int n){
        miniPosIndex = n;
    }

    public int getMiniPicIndex(){
        return miniPicIndex;
    }
    public void setMiniPicIndex(int n){
        miniPicIndex= n;
    }

    public Rectangle2D getPositions(){
        return positions[picIndex];
    }

    public int[][] getMyGuyPositions(){
        return myGuyPositions;
    }

    public int[][] getMiniMyGuyPositions(){
        return miniMyGuyPositions;
    }
*/

}
