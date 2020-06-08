//Kevin Cui and Dimitrios Christopoulos
//Player.java
//Class for the player, contains world and screen coordinates, money, sprites, and items. Also draws the sprites for movement
// as well as world and screen movement and draws the player

import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class Player { // class for the player
	public static final int BOY = 0, GIRL = 1, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10, IDLE = 0; // declaring ints for sprites
	public static int [] items = new int [7]; //
	private int frame, dir, extra, wait, delay,worldX,worldY,screenX,screenY; // declaring ints for screen pos, world pos, etc
	private static int money; // int for the money of the player
	private Image[] sprites; // array of images for the sprites of the player
	private Items myItems; // Items object
	private int[] numItems = new int[7];
	public Player(int gen) throws IOException {
		money = 0; // initializing money as 0
		worldX = 389; // initializing the world X position of the player
		worldY = 365; // initializing the world Y position of the player
		screenY = 365; // initializing the screen Y position of the player
		screenX = 389; // initializing the screen X position of the player
		frame = 6; // frame of the sprite
		extra = 0;
		wait = 0;
		delay = 30;
		myItems = new Items();
		for (int i = 0; i < 7; i++){
			numItems[i] = 0;
		}
		if (gen == BOY) { // boy or girl
			load(BOY);
		} else {
			load(GIRL);
		}
		for (int i = 0; i < 7; i++){
			items[i] = 1;
		}
	}
	public int[] getNumItems(){
		numItems = myItems.getNums();
		return numItems;
	}
	public Items getItems(){
		return myItems;
	}
	public void load(int gen) { // load method for the trainer sprites
		sprites = new Image[12]; // array to load images into
		for (int i = 0; i < 12; i++) { // for loop to load the images
			String path = String.format("%s/%s/%s%d.png", "Sprites", "TrainerSprites", "Trainer", i + 1); // formatting the file path
			if (gen == GIRL) {
				path += "G";
			}
			try {
				Image pic = ImageIO.read(new File(path)); // getting the image from the path
				pic = pic.getScaledInstance(30, 25, Image.SCALE_SMOOTH); // re-sizing the image
				sprites[i] = pic; // adding the image to the array
			} catch (IOException e) {
			}
		}
	}
	public void draw(Graphics g) {
		g.drawImage(sprites[frame], screenX, screenY, null);
	} // method to draw the player

	public void move(int dir,int picIndex,int miniPicIndex,boolean mini) { // method to move the player
		if (dir == UP) { // if the direction is up
			if (screenY > 398 || worldY < 398) { // if the player is within boundaries of the world and screen Y
				screenY -=1; // move the player's screen Y
			}
			worldY -= 1; // move the players world Y
		} else if (dir == RIGHT) { // if the direction is right
			if (mini) { // if the playe is on a mini background
				if (screenX < 478 || worldX > MasseyMon.getMiniMap(picIndex, miniPicIndex).getMapWidth() - 478) { // if the player is within boundries of the world and screen X
					screenX += 1; // move the player's screen X
				}
			}
				else { // if the player is not on a mini background
					if (screenX < 478 || worldX > MasseyMon.getMap(picIndex).getMapWidth() - 478) { // re-check the boundaries
						screenX += 1; // move the player's screen X is they are within it
					}
				}
			worldX += 1; // move the player's world X
		}
		else if (dir == DOWN) { // if the direction is down
			if (mini){ // if the player is on a mini background
				if (screenY < 398 || worldY > MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapHeight() - 398){ // checking the boundries to move the screen Y
					screenY += 1; // moving the screen Y
				}
			}
			else { // if the player is not on a mini background
				if (screenY < 398 || worldY > MasseyMon.getMap(picIndex).getMapHeight() - 398) { // re-checking the boundries to move the screen Y
					screenY += 1; // moving the screen Y
				}
			}
			worldY += 1; // moving the world Y
		}
		else if (dir == LEFT) { // if the direction is left
			if (screenX > 478 || worldX < 478){ // checking the boundaries to move the screen X
				screenX -=1; // moving the screen X
			}
			worldX -= 1; // moving the world X
		}


		updateFrame(); // calling update
		frame = dir + extra; // calculating frame
		wait++; // adding to wait
	}

	public void idle(int direction) { // if the player is not moving
		if (direction == UP) { // if direction is up
			frame = 0; // setting the frame to idle facing up
		}
		else if (direction == RIGHT) { // if the direction is right
			frame = 3; // setting the frame to idle facing right
		}
		else if (direction == DOWN) { // if the direction is down
			frame = 6; // setting the frame to idle facing down
		}
		else if (direction == LEFT) { // if the direction is left
			frame = 9; // setting the frame to idle facing down
		}
	}

	public void updateFrame() {
		if (wait % delay == 0) {
			extra++;
			if (extra > 1) {
				extra = 0;
			}
		}
	}

	public void resetExtra() {
		extra = 0;
	} // method to reset extra
	public int getWorldX(){return worldX;} // getter for world X
	public int getWorldY(){return worldY;} // getter for world Y
	public void setWorldX(int val ){worldX = val;} // setter for world X
	public void setWorldY(int val){worldY = val;} // setter for world Y
	public int getScreenX(){return screenX;} // getter for screen X
	public void setScreenX(int val){screenX = val;} // setter for screen X
	public int getScreenY(){return screenY;} // getter for screen Y
	public void setScreenY(int val){screenY = val;} // setter for screen Y
	public static int getMoney(){return money;} // getter for player money
	public static void addMoney(int val){money+=val;} // add to player money
	public static void loseMoney(int val){money-=val;} // subtract from player money
}
	
