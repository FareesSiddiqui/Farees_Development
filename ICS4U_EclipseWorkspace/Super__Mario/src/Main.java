import processing.core.*;
import sprites.S4P;

import sprites.*;
import sprites.maths.*;
import sprites.utils.*;

public class Main extends PApplet{
	
	static Mario mario;
	static Goomba goomba;
	
	Sprite mForward;
	Sprite mBack;
	Sprite standL;
	Sprite standR;
	Sprite jumpL;
	Sprite jumpR;
	int bgX, bgY;
	
	float cameraXpos = 0, cameraXvel = 0;
	
	PImage[] img = new PImage[9];
	
	static PImage goombaI;
	
	StopWatch sw = new StopWatch();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");

	}
	
	public void pre() {
		// Calculate time difference since last call
		float elapsedTime = (float) sw.getElapsedTime();  
		S4P.updateSprites(elapsedTime);
	}

	public void settings() {
		size(1280, 720);
		mario = new Mario(100, 100, 5, 300, this);
		goomba = new Goomba(500, 585, 3, 100, this);
	}
	
	public void setup() {
		mForward = new Sprite(this, "marioF.png", 4, 1, 1);
		mBack = new Sprite(this, "marioBack.png", 4, 1, 1);
		standL = new Sprite(this, "standingL.png", 1, 1, 1);
		standR = new Sprite(this, "standingR.png", 1, 1, 1);
		jumpL = new Sprite(this, "jumpL.png", 1, 1, 1);
		jumpR = new Sprite(this, "jumpR.png", 1, 1, 1);
		goombaI = loadImage("goomba.png");
		goombaI.resize(91, 130);
		for(int i=0;i<9;i++){
		    img[i] = loadImage("data/map/LevelPiece"+(i+1)+".png");
		}
		
		
		init();
		registerMethod("pre", this);
	}
	
	public void init() {
		standR.setVisible(true);
		standR.setXY(mario.mX, mario.mY);
		standL.setVisible(false);
		mForward.setVisible(false);
		mBack.setVisible(false);
		
		jumpL.setVisible(false);
		jumpR.setVisible(false);
		
		mForward.setDead(false);
		mForward.setFrameSequence(0, 3, 0.2f);
		mBack.setFrameSequence(0, 3, 0.2f);
	}
	
	
	/*
	 * Method to check collision between two circles
	 * 
	 * @param c1x the x coordinate of the first object
	 * 
	 * @param c1y the y coordinate of the first object
	 * 
	 * @param c1r the radius of the first object
	 * 
	 * @param c2x the x coordinate of the second object
	 * 
	 * @param c2y the y coordinate of the second object
	 *  
	 * @param c2r the radius of the second object object
	 * 
	 * @return returns if a collision is detected object
	 */
	// CIRCLE/CIRCLE
	boolean collide(float c1x, float c1y, float c1r, float c2x, float c2y, float c2r) {
	  float distX = c1x - c2x;
	  float distY = c1y - c2y;
	  float distance = sqrt( (distX*distX) + (distY*distY) );

	  if (distance <= c1r+c2r) {
	    return true;
	  }
	  return false;
	}
	
	void game() {
		mario.update();
		mario.move();
		goomba.update();
		
		if(collide(mario.mX, mario.mY, 68, goomba.gX, goomba.gY, 25) && mario.jumping) {
			goomba.setHealth(0);
			mario.jump();
		}
		
		
		if(mario.getKeys(1)) {
			mForward.setVisible(true);
			
			standR.setVisible(false);
			standL.setVisible(false);
			
			jumpL.setVisible(false);
			jumpR.setVisible(false);

		}
		
		else if(!mario.getKeys(1)) {
			mForward.setVisible(false);
		}
		
		if(mario.getKeys(0)) {
			mBack.setVisible(true);
			
			standR.setVisible(false);
			standL.setVisible(false);
			
			jumpL.setVisible(false);
			jumpR.setVisible(false);
		}
		
		else if(!mario.getKeys(0)) {
			mBack.setVisible(false);
		}
		
		if(!mario.getKeys(0) && !mario.getKeys(1) && mario.getLastKey() == 'd') {
			standR.setVisible(true);
			standL.setVisible(false);
			mForward.setVisible(false);
			mBack.setVisible(false);
			
			jumpL.setVisible(false);
			jumpR.setVisible(false);
		}
		
		else if(!mario.getKeys(0) && !mario.getKeys(1) && mario.getLastKey() == 'a') {
			standR.setVisible(false);
			standL.setVisible(true);
			mForward.setVisible(false);
			mBack.setVisible(false);
			
			jumpL.setVisible(false);
			jumpR.setVisible(false);
		}
		
		if(mario.getKeys(2) && mario.getLastKey() == 'd' ) {
			standR.setVisible(false);
			standL.setVisible(false);
			mForward.setVisible(false);
			mBack.setVisible(false);
			
			jumpL.setVisible(false);
			jumpR.setVisible(true);
		}
		
		if(mario.getKeys(2) && mario.getLastKey() == 'a' ) {
			standR.setVisible(false);
			standL.setVisible(false);
			mForward.setVisible(false);
			mBack.setVisible(false);
			
			jumpL.setVisible(true);
			jumpR.setVisible(false);
		}
		
		
		mForward.setXY(mario.mX,mario.mY);
		mBack.setXY(mario.mX,mario.mY);
		
		standR.setXY(mario.mX,mario.mY);
		standL.setXY(mario.mX,mario.mY);
		
		jumpL.setXY(mario.mX,mario.mY);
		jumpR.setXY(mario.mX,mario.mY);
		
		mario.update();
		
		S4P.drawSprites();
	}
	
	public void mousePressed() {
		System.out.println("MouseX: "+mouseX+" MouseY: "+mouseY+" Mario_x: "+mario.mX+" Mario_Y"+mario.mY+" Width: "+standR.getWidth()+" Height: "+standR.getHeight());
	}
	
	
	public void draw() {
		background(0,100,190);
		
		cameraXpos += cameraXvel;
		if(cameraXpos<0)
		  cameraXpos = 0;
		if(cameraXpos>9295)
		  cameraXpos = 9295;
		fill(0,100,190);
		cameraXvel = (float) ((mario.mX-(cameraXpos+width/2)));
		
		pushMatrix();
		  
		translate(-cameraXpos, 0);

//		scale(1);
//		  
//		int imageNumber1 = (int)(cameraXpos/(1280));
//		int imageNumber2 = (int)(cameraXpos/(1280))+1;
//		  
//		if(imageNumber1<=9&&imageNumber1>=0)
//		  image(img[imageNumber1], imageNumber1*1280, 370);
//		if(imageNumber2<=9&&imageNumber2>=0)
//		  image(img[imageNumber2], imageNumber2*1280, 370);
//		
//		
		pushMatrix();
//		scale(1);
		  
		int imageNumber1 = (int)(cameraXpos/(1280));
		int imageNumber2 = (int)(cameraXpos/(1280))+1;
		  
		if(imageNumber1<=9&&imageNumber1>=0)
		  image(img[imageNumber1], imageNumber1*1280, 370);
		if(imageNumber2<=9&&imageNumber2>=0)
		  image(img[imageNumber2], imageNumber2*1280, 370);
		
		
		mario.update();
		game();
		popMatrix();
		
		popMatrix();
	}
	
	public void keyPressed() {
		if(key == 'a' || key == 'A') {
			mario.setKeys(0, true);
			mario.last = 'a';
			
		}
		
		if(key == 'd' || key == 'D') {
			mario.setKeys(1, true);
			mario.last = 'd';
		}

		if(key == ' ' || key == ' '|| key == 'w') {
			mario.setKeys(2, true);
		}
		
	}
	
	public void keyReleased() {
		if(key == 'a' || key == 'A') {
			mario.setKeys(0, false);
			mario.last = 'a';
		}
		
		if(key == 'd' || key == 'D') {
			mario.setKeys(1, false);
		}

		if(key == ' ' || key == ' ' || key == 'w') {
			mario.setKeys(2, false);
		}
	}
}
