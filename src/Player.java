import java.awt.*;
import java.io.*; 
import javax.imageio.*;

public class Player {
	public static final int BOY = 0, GIRL = 1, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10, IDLE = 0;
	private int frame, dir, extra, wait, delay,worldX,worldY,screenX,screenY;
	private Image[] sprites;

	public Player(int gen) {
		worldX = 289;
		worldY = 285;
		screenY = 285;
		screenX = 289;
		frame = 6;
		extra = 0;
		wait = 0;
		delay = 30;
		if (gen == BOY) {
			load(BOY);
		} else {
			load(GIRL);
		}
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

	public void move(int dir,int picIndex) {

		if (dir == UP) {
			if (screenY > 398 || worldY < 398) {
				screenY -=1;
			}
			worldY -= 1;
		} else if (dir == RIGHT) {
			if (screenX < 478 || worldX > MasseyMon.getMap(picIndex).getMapWidth() - 478){
				screenX +=1;
			}
			worldX += 1;
		} else if (dir == DOWN) {
			if (screenY < 398 || worldY > MasseyMon.getMap(picIndex).getMapHeight() - 398) {
				screenY +=1;
			}
			worldY += 1;
		} else if (dir == LEFT) {
			if (screenX > 478 || worldX < 478){
				screenX -=1;
			}
			worldX -= 1;
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
}
	
