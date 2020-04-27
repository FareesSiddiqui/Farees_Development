import processing.core.*;
public class TextField extends GameObject{

	  int life;
	  int birthMil;

	  String text;

	  PFont font;

	  PImage img;
	  
	  public static PApplet pp;
	  
	  TextField(String text_, int life_, PApplet app) {
	    super();
	    pp = app;
	    font = Main.scores.getFont();
	    img = pp.loadImage("Sprites/HUD.png");
	    loc = new PVector(pp.width/2,-img.height/2);
	    life = life_;
	    des = loc.copy();
	    smoothFactor = (float) .1;
	    text = text_;
	    birthMil = pp.millis();
	  }

	  @Override
	  void update() {

	    des.y = img.height/2;

	    if (pp.millis() - birthMil < life) {
	      smoothMove();
	      simplePhysicsCal();
	    } else {
	      acc.y = (float) -0.5;
	      simplePhysicsCal();
	    }

	    if (loc.y < -img.height) {
	      Main.textFields.remove(this);
	    }



	    drawImage();
	  }

	  void drawImage() {
	    pp.pushMatrix();
	    pp.translate(loc.x,loc.y);
	    pp.pushMatrix();
	    pp.scale(1,-1);

	    pp.image(img,0,0);
	    pp.popMatrix();
	    pp.textAlign(pp.CENTER);
	    pp.fill(255, 255, 255);
	    pp.textFont(font, 20);
	    pp.text(text,0,(float) 7.5);

	    pp.popMatrix();

	  }
}
