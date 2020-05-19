import processing.core.*;

public class testPlayer {
	
	int x, y, speed;
	
	protected final PApplet p;
	
	public static boolean[] keys = new boolean[5];
	
	testPlayer(int _x, int _y, int _speed, PApplet _p){
		p = _p;
		speed = _speed;
		x = _x;
		y = _y;
	}
	
	void update() {
		if(keys[0])
			x += speed;
		
		else if(keys[1])
			x -= speed;
		
		p.ellipse(50, 900, 50, 50);
	}
	
	void keyp() {
		if(p.key == 'd') {
			keys[0] = true;
		}
		
		else if(p.key == 'a') {
			keys[1] = true;
		}
	}
	
	void keyr() {
		if(p.key == 'd') {
			keys[0] = false;
		}
		
		else if(p.key == 'a') {
			keys[1] = false;
		}
	}
	
	

}
