import java.util.ArrayList;

import processing.core.*;

/**
 * @author Farees
 *
 */
public class Main extends PApplet{
	
	/*Instantiate player class*/
	Player player;
	
	
	/*Screen variable to control what part of the game runs*/
	int screen = 0;
	
	/*Player image*/
	public static PImage playerImg;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}

	@Override
	public void settings() {
		size(600, 600);
		player = new Player(300, 300, 5, this);
			
	}
	
	@Override
	public void setup() {
		playerImg = loadImage("player.png");	
		ellipseMode(CENTER);

	}
	
	public void game() {
		background(60, 75, 114);
		
		player.update();
		
		player.reload();
				
		
	}
	
	@Override
	public void mouseClicked() {
		screen = 1;
	}
	
	@Override
	public void draw() {
		if(screen == 1)
			game();
		
	}
	
	@Override
    public void keyPressed() {
		player.keyp();
    	
    }
    
    @Override
    public void keyReleased() {
    	player.keyr();
    }
}
