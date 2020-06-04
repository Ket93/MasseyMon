import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;

public class Player {
	public static final int BOY = 0, GIRL = 1, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10, IDLE = 0;
	public static int [] items = new int [7];
	private int frame, dir, extra, wait, delay,worldX,worldY,screenX,screenY,money;
	private Image[] sprites;
	private Items myItems;
	private int[] numItems = new int[7];
	public Player(int gen) throws IOException {
		money = 0;
		worldX = 289;
		worldY = 285;
		screenY = 285;
		screenX = 289;
		frame = 6;
		extra = 0;
		wait = 0;
		delay = 30;
		myItems = new Items();
		for (int i = 0; i < 7; i++){
			numItems[i] = 0;
		}
		if (gen == BOY) {
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
	public void load(int gen) {
		sprites = new Image[12];
		for (int i = 0; i < 12; i++) {
			String path = String.format("%s/%s/%s%d.png", "Sprites", "TrainerSprites", "Trainer", i + 1);
			if (gen == GIRL) {
				path += "G";
			}
			try {
				Image pic = ImageIO.read(new File(path));
				pic = pic.getScaledInstance(30, 25, Image.SCALE_SMOOTH);
				sprites[i] = pic;
			} catch (IOException e) {
			}
		}
	}
	public void draw(Graphics g) {
		g.drawImage(sprites[frame], screenX, screenY, null);
	}

	public void move(int dir,int picIndex,int miniPicIndex,boolean mini) {
		if (dir == UP) {
			if (screenY > 398 || worldY < 398) {
				screenY -=7;
			}
			worldY -= 7;
		} else if (dir == RIGHT) {
			if (mini) {
				if (screenX < 478 || worldX > MasseyMon.getMiniMap(picIndex, miniPicIndex).getMapWidth() - 478) {
					screenX += 7;
				}
			}
				else {
					if (screenX < 478 || worldX > MasseyMon.getMap(picIndex).getMapWidth() - 478) {
						screenX += 7;
					}
				}
			worldX += 7;
		}
		else if (dir == DOWN) {
			if (mini){
				if (screenY < 398 || worldY > MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapHeight() - 398){
					screenY += 7;
				}
			}
			else {
				if (screenY < 398 || worldY > MasseyMon.getMap(picIndex).getMapHeight() - 398) {
					screenY += 7;
				}
			}
			worldY += 7;
		}
		else if (dir == LEFT) {
			if (screenX > 478 || worldX < 478){
				screenX -=7;
			}
			worldX -= 7;
		}


		updateFrame();
		frame = dir + extra;
		wait++;
	}

	public void idle(int direction) {
		if (direction == UP) {
			frame = 0;
		}
		else if (direction == RIGHT) {
			frame = 3;
		}
		else if (direction == DOWN) {
			frame = 6;
		}
		else if (direction == LEFT) {
			frame = 9;
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
	}

	public int getWorldX(){return worldX;}
	public int getWorldY(){return worldY;}
	public void setWorldX(int val ){worldX = val;}
	public void setWorldY(int val){worldY = val;}
	public int getScreenX(){return screenX;}
	public void setScreenX(int val){screenX = val;}
	public int getScreenY(){return screenY;}
	public void setScreenY(int val){screenY = val;}
	public int getMoney(){return money;}
	public void addMoney(int val){money+=val;}
	public void loseMoney(int val){money-=val;}
}
	
