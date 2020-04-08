import java.awt.*;// assicated with that location within a 10 pixel radius. These emotions are shown using colours.
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*;
import java.util.*;

public class Player{
	public static final int BOY = 0, GIRL = 1, UP = 0, RIGHT = 3, DOWN = 6, LEFT = 9, IDLE = -1;
	private int px,py,frame,dir,extra,wait,delay;
	private Image[] sprites;
	public Player(int gen){
		px = 100;
		py = 200;
		frame = 0;
		extra = 0;
		wait = 0;
		delay = 30;
		if (gen == BOY){
			load(BOY);
		}
		else{
			load(GIRL);
		}
	}
	public void load(int gen){
		sprites = new Image[12];
		for (int i = 0; i < 12; i++){
			String path = String.format("%s/%s/%s%d.png","Sprites","TrainerSprites","Trainer",i+1);
			if (gen == GIRL){
				path += "G";
			}
			try{
				Image pic = ImageIO.read(new File(path));
				pic = pic.getScaledInstance(30,25,Image.SCALE_SMOOTH);
				sprites[i] = pic;
			}
			catch (IOException e) {}
		}
	}
	public void draw(Graphics g){
		g.drawImage(sprites[frame],px,py,null);
	}
	public void move(int dir){
		if (dir == UP){
			py -= 1;
			dir = UP;
		}
		else if(dir == RIGHT){
			px += 1;
			dir = RIGHT;
		}
		else if(dir == DOWN){
			py += 1;
			dir = DOWN;
		}
		else if(dir == LEFT){
			px -= 1;
			dir = LEFT;
		}
		updateFrame();
		frame = dir + extra;
		wait++;
	}
	public void updateFrame(){
		if (wait % delay == 0){
			extra++;
			if (extra > 2){
				extra = 1;
			}
		}
	}
}