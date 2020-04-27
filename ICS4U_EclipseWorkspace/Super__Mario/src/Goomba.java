import processing.core.*;
import java.util.ArrayList;
public class Goomba extends Character{
	static int gX;
	static int gY;
	static int gS;
	static boolean dead = false;
	static ArrayList<Goomba> gArray = new ArrayList<Goomba>(5);
	
	Goomba(int x1, int y1, int speed1, int health1, PApplet app){
		super(x1, y1, speed1, health1, app);
		gX = x1;
		gY = y1; 
		gS = speed1;	
		setHealth(health1);		
	}
	
	void update() {
		gX -= gS;
		
		if(getHealth() <= 0) {
			dead = true;
		}
		
		if(dead) {
			gX = gY = 0;
		}
		
		if(gX <= 25) {
			gS *= -1;
		}
		
		if(gX >= 695) {
			gS*=-1;
		}
				
		applet.image(Main.goombaI, (float)gX, (float)gY);
	}
	
	

}
