import processing.core.*;

public class Enemy {
	
	public static int x, y, speed;
	
	public static String gun;
	
	protected final PApplet pp;
	
	Enemy(int x1, int y1, int speed1, String gun1, PApplet applet){
		this.x = x1;
		this.y = y1;
		this.speed = speed1;
		this.gun = gun1;
		this.pp = applet;
	}
	
	
	
	

}
