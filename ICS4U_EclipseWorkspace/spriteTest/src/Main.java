import java.util.ArrayList;
import java.util.Arrays;

import processing.core.*;
import sprites.*;
import sprites.maths.*;
import sprites.utils.*;
/**
 * @author Farees
 *
 */
public class Main extends PApplet{
	
	PImage[] img = new PImage[9];
	public static PImage[] quadTextures = new PImage[3];
	
	float cameraXpos = 0;
	float cameraXvel = 0;
	
	Player mario;
	Sprite mForward;
	Sprite mBack;
	Sprite standL;
	Sprite standR;
	Sprite jumpL;
	Sprite jumpR;
	
	PImage img2;
	
	StopWatch sw = new StopWatch();
	
	
//	int gridHeight = 0;
//	int gridWidth = 0;
//	int gridSize = 25;
//	int time;
//	int score = 0;
//	int FinalScore = 0;
//	int imgScale = 1;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}
	
	public void pre() {
		// Calculate time difference since last call
		float elapsedTime = (float) sw.getElapsedTime();  
		S4P.updateSprites(elapsedTime);
	}

	@Override
	public void settings() {
		size(1280, 720);
	}

	@Override
	public void setup() {
		mario = new Player(1000, 1000, 5, 5, this);
		mForward = new Sprite(this, "/data/marioF.png", 4, 1, 1);
		mBack = new Sprite(this, "/data/marioBack.png", 4, 1, 1);
		standL = new Sprite(this, "/data/standingL.png", 1, 1, 1);
		standR = new Sprite(this, "/data/standingR.png", 1, 1, 1);
		jumpL = new Sprite(this, "/data/jumpL.png", 1, 1, 1);
		jumpR = new Sprite(this, "/data/jumpR.png", 1, 1, 1);
		
		img2 = loadImage("standingR.png");
		
		init();
		registerMethod("pre", this);
		
		for(int i=0;i<9;i++){
		    img[i] = loadImage("map/LevelPiece"+(i+1)+".png");
		}
		
		
		
	}
	
	public void mousePressed() {
		System.out.println("MouseX: "+mouseX+" MouseY: "+mouseY+" Mario_x: "+mario.position.x);
	}
	
	void game2() {
		mario.update();
		
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
		
		
		mForward.setXY(mario.position.x,mario.position.y);
		mBack.setXY(mario.position.x,mario.position.y);
		
		standR.setXY(mario.position.x,mario.position.y);
		standL.setXY(mario.position.x,mario.position.y);
		
		jumpL.setXY(mario.position.x,mario.position.y);
		jumpR.setXY(mario.position.x,mario.position.y);
		
		mario.update();
		
		S4P.drawSprites();
	}
	
	public void init() {
		standR.setVisible(true);
//		standR.setXY(mario.position.x, mario.position.y);
		standL.setVisible(false);
		mForward.setVisible(false);
		mBack.setVisible(false);
		
		jumpL.setVisible(false);
		jumpR.setVisible(false);
		
		mForward.setDead(false);
		mForward.setFrameSequence(0, 3, 0.2f);
		mBack.setFrameSequence(0, 3, 0.2f);
	}
	
	
	public void log(String log) {
		System.out.println(log);
	}
	
	public void log_double(double x, double y) {
		System.out.println("X: " + x +" Y: "+ y);
	}
	
	public void game() {
		
		
	}
	
	@Override
	public void mouseClicked() {
		
		
	}
	
	@Override
	public void draw() {
		background(0,100,190);
		cameraXpos += cameraXvel;
		if(cameraXpos<0)
		  cameraXpos = 0;
		if(cameraXpos>9295)
		  cameraXpos = 9295;
		    
		cameraXvel = (float) ((mario.position.x-(cameraXpos+width/1.25)));
		
		pushMatrix();
		  
		translate(-cameraXpos, 0);

		scale(1);
		  
		int imageNumber1 = (int)(cameraXpos/(1280));
		int imageNumber2 = (int)(cameraXpos/(1280))+1;
		  
		if(imageNumber1<=9&&imageNumber1>=0)
		  image(img[imageNumber1], imageNumber1*1280, mouseY);
		if(imageNumber2<=9&&imageNumber2>=0)
		  image(img[imageNumber2], imageNumber2*1280, mouseY);
		
		System.out.println("Camera: "+cameraXpos+" Player: "+mario.position.x);
		
		pushMatrix();
		game2();
		
		popMatrix();
		
		popMatrix();
		  
		  
		
		
	}
	
	@Override
    public void keyPressed() {
		mario.keyp();
    	
    }
    
    @Override
    public void keyReleased() {
    	mario.keyr();
    	
    }
}
