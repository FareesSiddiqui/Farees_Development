import processing.core.*;
import sprites.*;
import sprites.maths.*;
import sprites.utils.*;

public class Player {
		
	PVector position, velocity, gravity, acceleration, friction;
	
	boolean[] keys = new boolean[5];
	
	protected final PApplet applet;
	
	Player(int x, int y, PApplet pp){
		velocity = new PVector(0, 0);
		gravity = new PVector(0, (float) 0.05);
		acceleration = new PVector(0,0);
		friction = new PVector(0, 0);
		position = new PVector(x, y);
		applet = pp;
	}
	
	void update() {
		if(applet.mousePressed) {
			velocity.add(gravity);
			
			if(position.y >= 675) {
				gravity.y = 0;
			} else {
				gravity.y = (float) 0.05;
			}
		}
		
		applet.ellipse(position.x, position.y, 50, 50);
	}
	
	void keyp() {
		
		if(applet.key == 'w' || applet.key == 'W') {
			keys[0] = true;
		}
		
		if(applet.key == 's' || applet.key == 'S') {
			keys[1] = true;
		}

		if(applet.key == 'a' || applet.key == 'A') {
			keys[2] = true;
		}
		
		if(applet.key == 'D' || applet.key == 'D') {
			keys[3] = true;
		}
		
		if(applet.key == ' ' || applet.key == ' ') {
			keys[4] = true;
		}
		
	}

	void keyr() {
		
		if(applet.key == 'w' || applet.key == 'W') {
			keys[0] = false;
		}
		
		if(applet.key == 's' || applet.key == 'S') {
			keys[1] = false;
		}

		if(applet.key == 'a' || applet.key == 'A') {
			keys[2] = false;
		}
		
		if(applet.key == 'D' || applet.key == 'D') {
			keys[3] = false;
		}
		
		if(applet.key == ' ' || applet.key == ' ') {
			keys[4] = false;
		}
	}
}
