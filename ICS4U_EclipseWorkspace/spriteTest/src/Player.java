import processing.core.PApplet;
import processing.core.PVector;

public class Player {
		
	PVector position, velocity, gravity;
	
	boolean[] keys = new boolean[5];
	
	protected final PApplet applet;
	
	int mass;
	
	int speed;
	
	float targetX, targetY, easing = (float)0.05;
			
	private boolean hit = false;
	
	private float dx;
	
	private char last;
		
	Player(int x, int y, int m, int speed1, PApplet pp){
		velocity = new PVector(0, 0);
		position = new PVector(x, y);
		gravity = new PVector(0, (float) 1.2);
		mass = m;
		speed = speed1;
		applet = pp;
		
	}
	
	public boolean getKeys(int i) {
		return keys[i];
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public void zeroLast() {
		last = ' ';
	}
	
	public char getLastKey() {
		return last;
	}
	
	void update() {
		
		
		
		position.add(velocity);
		velocity.add(gravity);				
		
		if(keys[0]) {
			targetX = velocity.x - 2;
						
			dx = targetX - velocity.x;
			velocity.x += dx*easing;
			
			if(position.x <= 25) {
				position.x = 40;
				velocity.x = 0;
			}
			
			
		}
		
		
		if(keys[1]) {
			
			targetX = velocity.x + 2;
			
			float dx = targetX - velocity.x;
			velocity.x += dx*easing;
			
			if(position.x >= 9945) {
				position.x = 9945;
				velocity.x = 0;
			}
			
		}
		
		else if(!keys[0] && !keys[1]) {
			targetX = 0;
			dx = targetX-velocity.x;
			velocity.x += dx*0.1;
		}
		
		
		if(position.x >= 9945) {
			velocity.x = 0;
			position.x = 9945;//1475
		}
		
		if(position.x <= 25) {
			position.x = 25;
			velocity.x = 0;
		}
		
		if(position.y >= 575) {
			position.y = 575;
			velocity.y = 0;
			hit = true;
		}
		

		if(keys[2] == true && hit == true) {
			keys[2] = false;
			velocity.y -= 20;
			
			hit = !hit;
		}
		
	}
	
	void keyp() {
		
		if(applet.key == 'w' || applet.key == 'W') {
			keys[2] = true;
			
		}
		
		if(applet.key == 's' || applet.key == 'S') {
			keys[3] = true;
		}

		if(applet.key == 'a' || applet.key == 'A') {
			keys[0] = true;
			last = 'a';
		}
		
		if(applet.key == 'd' || applet.key == 'D') {
			keys[1] = true;
			last = 'd';
		}
		
		if(applet.key == ' ' || applet.key == ' ') {
			keys[4] = true;
		}
		
	}

	void keyr() {
		
		if(applet.key == 'w' || applet.key == 'W') {
			keys[2] = false;
		}
		
		if(applet.key == 's' || applet.key == 'S') {
			keys[3] = false;
		}

		if(applet.key == 'a' || applet.key == 'A') {
			keys[0] = false;
			last = 'a';
		}
		
		if(applet.key == 'd' || applet.key == 'D') {
			keys[1] = false;
			last = 'd';
		}
		
		if(applet.key == ' ' || applet.key == ' ') {
			keys[4] = false;
		}
	}
}
