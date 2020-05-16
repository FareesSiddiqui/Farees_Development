import processing.core.*;

public class Main extends PApplet{
	
	testPlayer p;
	
	PImage bg;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PApplet.main("Main");
		
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
		bg = loadImage("bg.jpg");
		p = new testPlayer(50, 900, 2, this);
	}
	
	@Override
	public void draw() {
		background(219, 176, 88);
		
		int x = p.x+100;
		
		copy(bg, x, 0, bg.width-p.x, height, 0, 0, bg.width-p.x, height);
		
		int x2 = bg.width - x+100;
		
		if (x2 < width) {
		  copy(bg, 0, 0, bg.width-p.x, height, x2, 0, bg.width-p.x, height);
		}
		
		p.update();
		
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
		
	}

}
