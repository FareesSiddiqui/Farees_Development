import processing.core.*;
public class Main extends PApplet{
	
	Player mario;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");

	}

	public void settings() {
		size(1500, 700);
		mario = new Player(width/2, height/2, this);
	}
	
	public void setup() {
		
	}
	
	public void draw() {
		background(255);
		mario.update();
		
	}
	
	public void keyPressed() {
		mario.keyp();
		
	}
	
	public void keyReleased() {
		mario.keyr();
	}
}
