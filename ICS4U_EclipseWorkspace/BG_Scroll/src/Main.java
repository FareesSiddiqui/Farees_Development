import processing.core.*;

public class Main extends PApplet{
	
	Character p;
	
	Pipe[] pipes = new Pipe[6];
	
	PImage bg;
	
	boolean gameOver = false;
	
	boolean test = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PApplet.main("Main");
		
	}
	
	boolean collide(float cx, float cy, float radius, float rx, float ry, float rw, float rh) {

		// temporary variables to set edges for testing
		float testX = cx;
		float testY = cy;

		// which edge is closest?
		if (cx < rx)
			testX = rx; // test left edge
		else if (cx > rx + rw)
			testX = rx + rw; // right edge
		if (cy < ry)
			testY = ry; // top edge
		else if (cy > ry + rh)
			testY = ry + rh; // bottom edge

		// get distance from closest edges
		float distX = cx - testX;
		float distY = cy - testY;
		float distance = sqrt((distX * distX) + (distY * distY));

		// if the distance is less than the radius, collision!
		if (distance <= radius) {
			return true;
		}
		return false;
	}
	
	@Override
	public void settings() {
		//    X     Y
		size(3840, 2160);
		fullScreen();
		
	}
	
	@Override
	public void setup() {
		frameRate(60);
		bg = loadImage("bg4.png");
		p = new Character(50, 900, 2, 2, this);
	}
	
	void game() {
		if(!gameOver) {
			background(0,100,190);
			int x = (int) (p.position.x+100);
				
			copy(bg, x, 0, (int)(bg.width-p.position.x)*2, height, 0, 530, (int)(bg.width-p.position.x)*2 , height);
			pipes[0] = new Pipe(1095-x, 940, (1016-940 ),(1016-940), false, this);
			pipes[1] = new Pipe(1486-x, 903, (1406-1332),(1014-900), false, this);
			pipes[2] = new Pipe(1798-x, 863, (1719-1644),(1017-860), false, this);
			pipes[3] = new Pipe(2227-x, 863, (1111-1034), (1016-860), true, this);
			pipes[4] = new Pipe(6368-x, 940, (1111-1034), (1016-940), true, this);
			pipes[5] = new Pipe(6992-x, 940, (1111-1034), (1016-940), false, this);
			
			for(int i = 0; i < pipes.length; i++) {
				if(collide (p.position.x, p.position.y, 25, pipes[i].getX(), pipes[i].getY(), pipes[i].getW(), pipes[i].getH())) {
					test = !test;
				}
				else if(!collide (p.position.x, p.position.y, 25, pipes[i].getX(), pipes[i].getY(), pipes[i].getW(), pipes[i].getH())) {
					test = false;
				}
				
			}
			
			if(test) {
				println("true");
			}
			else if (!test) {
				println("False");
			}
			
			for(int i = 0; i <= 5; i++) {
				pipes[i].draw();
			}
				
			p.update();
			ellipse(p.position.x/4, p.position.y, 50, 50);
		}
	}
	
	@Override
	public void draw() {
		game();	
		
	}
	
	@Override
	public void keyPressed() {
		p.keyp();
		
	}
	
	@Override
	public void keyReleased() {
		p.keyr();
	}
	
	@Override
	public void mousePressed() {
//		println("X: "+mouseX);
//		println("Y: "+mouseY);
//		println("-----------------");
//		println("PositionX: "+p.position.x+" positionY: "+ p.position.y);
		
		
	}

}
