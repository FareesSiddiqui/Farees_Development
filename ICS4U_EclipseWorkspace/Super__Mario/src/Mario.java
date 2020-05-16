import processing.core.*;

public class Mario extends Character{
	int mX, mY, mS, mH;
	Mario(int x1, int y1, int speed1, int health1,PApplet applet){
		super(x1, y1, speed1, health1, applet);
		mX = x1;
		mY = y1;
		mS = speed1;
		setHealth(health1);
	}
	
	void resetXY(int _x, int _y) {
		mX = _x;
		mY = _y;
	}
	
	void update(){        	
    	velocity += gravity;
    	
    	mY += velocity;
    	
    	if(mY >= 585 && !onObs) {//585
    		mY= 585;
    		hit = true;
    		setVel(0);
    		jumping = false;
    		
    	}
    	
    	if(onObs) {
    		if(mY >= obsH) {
    			mY = obsH;
    		}
    	}
    	

    }
	
	void move() {
    	if(getKeys(0)) {
    		mX -= mS;
    	}
    	
    	if(getKeys(1) && mX<= 9945) {
    		mX += mS;
    	}
    	
    	if(mX >= 9945)
    		mX = 9945;
    	
    	if(mX >= 720 && mX <= 895 && mY >= 585 && !jumping && getKeys(1)) {
    		mX = 720;
    	}
    	
    	if(mX >= 720 && mX <= 895 && mY >= 585 && !jumping && getKeys(0)) {
    		mX = 895;
    	}
    	
    	if(mX > 720 && mX <= 895 && jumping) {
    		onObs = true;
    		obsH = 485;
    	}
    	
    	if(mX < 720 || mX >= 895 && mX < 1230) {
    		onObs = false;
    		
    	}
    	
    	//second pipe
    	if(mX >= 1230 && mX <= 1390 && jumping) {
    		obsH = 430;
    		onObs = true;
    		hit = true;
    	}
    	
    	if(mX > 1390 && mX < 1620) {
    		onObs = false;
    	}
    	
    	if(mX >= 1230 && mX <= 1390 && mY >= 585 && !jumping && getKeys(1)) {
    		mX = 1230;
    	}
    	
    	if(mX >= 1230 && mX <= 1390 && mY >= 585 && !jumping && getKeys(0)) {
    		mX = 1390;
    	}
    	
    	//Third pipe
    	if(mX >= 1620 && mX <= 1790 && jumping) {
    		obsH = 380;
    		onObs = true;
    		hit = true;
    	}
    	
    	if(mX > 1390 && mX < 1620 || mX > 1790 && mX < 2185) {
    		onObs = false;
    	}
    	
    	if(mX >= 1620 && mX <= 1790 && mY >= 585 && !jumping && getKeys(1)) {
    		mX = 1620;
    	}
    	
    	if(mX >= 1620 && mX <= 1790 && mY >= 585 && !jumping && getKeys(0)) {
    		mX = 1790;
    	}
    	
    	//Fourth Pipe
    	if(mX >= 2185 && mX <= 2345 && jumping) {
    		obsH = 380;
    		onObs = true;
    		hit = true;
    	}
    	
    	
    	if(mX >= 2185 && mX <= 2320 && mY >= 585 && !jumping && getKeys(1)) {
    		mX = 2185;
    	}
    	
    	if(mX >= 2185 && mX <= 2320 && mY >= 585 && !jumping && getKeys(0)) {
    		mX = 2320;
    	}
    	
    	if(mX < 2185 && mX > 2320 || mX > 2320 && mX < 5995) {
    		onObs = false;
    	}
    	
    	
    	if(getKeys(2) == true && hit == true) {
    		jump();
    		setKeys(2, !getKeys(2));
    	}   	
    	
    	applet.rectMode(applet.CENTER);
    	applet.noFill();
    	applet.ellipse(mX, mY, 135, 135);
    	
    }
	
	

}
