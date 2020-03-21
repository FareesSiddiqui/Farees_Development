import processing.core.*;

/**
 * @author Farees
 *
 */
public class Main extends PApplet{
	
	/*Instantiate player class*/
	public static player player;

	
	/*Character Image*/
	public static PImage playerImg;
			
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}

	@Override
	public void settings() {
		size(900, 600);
		
	}
	
	@Override
	public void setup() {
	
	}
	
	public void game() {

	}
	
	@Override
	public void draw() {
		if(mousePressed == true) {
			line(pmouseX, pmouseY, mouseX, mouseY);
		}
		
	}
	
	@Override
    public void keyPressed() {
    	
    }
    
    @Override
    public void keyReleased() {
    	
    }
}
