import processing.core.*;
public class Item extends Actor {
	final int TYPE;

	final static int FULLHEALTH = 0;
	final static int HEALTH20 = 1;
	final static int SHOOTSPEED = 2;
	final static int SHOOTSTRAIGHT = 3;

	PFont font;

	float randomNumber = Main.scores.pp.random(1000);

	Item(float x_, float y_, int type_) {
		super(x_, y_);
		TYPE = type_;

		font = Main.scores.getFont();

		cWidth = 30;
		cHeight = 30;
	}

	@Override
	void update() {
		checkWalls();

		if (rightWall || leftWall || topWall || bottomWall) {
			Main.items.remove(this);
			return;
		}

		drawImage();
	}

	@Override
	  void drawImage() {
		Main.scores.pp.pushMatrix();
		Main.scores.pp.translate(Main.camera.loc.x,Main.camera.loc.y+Main.scores.pp.sin((float) ((Main.scores.pp.millis()+randomNumber)*0.005))*5);

	    if (TYPE == FULLHEALTH) {
	    	Main.scores.pp.fill(237, 37, 37);
	    	Main.scores.pp.stroke(255,255,255);
	    	Main.scores.pp.strokeWeight(3);
	    	Main.scores.pp.rect(0,0,cWidth,cHeight);
	    	Main.scores.pp.noStroke();
	    	Main.scores.pp.textAlign(Main.scores.pp.CENTER);
	    	Main.scores.pp.fill(255,255,255);
	    	Main.scores.pp.textFont(font, 20);
	    	Main.scores.pp.text("+", (float)0, (float) ((float)cHeight*.35));
	    } else if (TYPE == HEALTH20) {
	    	Main.scores.pp.fill(232, 173, 46);
	    	Main.scores.pp.stroke(255,255,255);
	    	Main.scores.pp.strokeWeight(3);
	    	Main.scores.pp.rect(0,0,cWidth,cHeight);
	    	Main.scores.pp.noStroke();
	    	Main.scores.pp.textAlign(Main.scores.pp.CENTER);
	    	Main.scores.pp.fill(255,255,255);
	    	Main.scores.pp.textFont(font, 20);
	    	Main.scores.pp.text("+", (float)0, (float) ((float)cHeight*.35));
	    } else if (TYPE == SHOOTSPEED) {
	    	Main.scores.pp.fill(47, 217, 239);
	    	Main.scores.pp.stroke(255,255,255);
	    	Main.scores.pp.strokeWeight(3);
	    	Main.scores.pp.rect(0,0,cWidth,cHeight);
	    	Main.scores.pp.noStroke();
	    	Main.scores.pp.textAlign(Main.scores.pp.CENTER);
	    	Main.scores.pp.fill(255,255,255);
	    	Main.scores.pp.textFont(font, 20);
	    	Main.scores.pp.text("S", (float)1.5, (float) ((float)cHeight*.35));
	    } else if (TYPE == SHOOTSTRAIGHT) {
	    	Main.scores.pp.fill(46, 232, 80);
	    	Main.scores.pp.stroke(255,255,255);
	    	Main.scores.pp.strokeWeight(3);
	    	Main.scores.pp.rect(0,0,cWidth,cHeight);
	    	Main.scores.pp.noStroke();
	    	Main.scores.pp.textAlign(Main.scores.pp.CENTER);
	    	Main.scores.pp.fill(255,255,255);
	    	Main.scores.pp.textFont(font, 20);
	    	Main.scores.pp.text("A", (float)1.25, (float) ((float)cHeight*.35));
	    }

	    Main.scores.pp.popMatrix();
	}
}
