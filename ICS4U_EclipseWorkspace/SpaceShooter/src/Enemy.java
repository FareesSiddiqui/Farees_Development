import java.util.ArrayList;

import processing.core.PImage;

public class Enemy extends Actor{
	 int animationSpeed = (int)Main.scores.pp.random(100-20,100+20);


	  int lastMilAni;
	  int frameC;

	  final int TYPE;

	  int tintDuration = 50;
	  int lastMilTint;
	  ArrayList<PImage> images;

	  boolean lastHitPlayer;

	  int state;

	  final int DEAD = -1;
	  final int NORM = 0;

	  Enemy(int type_) {
	    //int health_, int size_, String fileName_
	    super(Main.E_FILE_NAMES[type_]+"0000.png");
	    lastMilAni = Main.scores.pp.millis();
	    frameC = 0;

	    images = new ArrayList<PImage>();
	    for (int c = 0; c < Main.E_FRAMES[type_]; c++)
	      images.add(Main.scores.pp.loadImage(Main.E_FILE_NAMES[type_]+Main.scores.pp.nf(c,4)+".png"));
	    Particle.img = images.get(0);

	    health = Main.E_HEALTH[type_];

	    cWidth = Particle.img.width;
	    cHeight = Particle.img.height;

	    TYPE = type_;
	  }

	  Enemy(float x_, float y_, int type_) {
	    super(x_, y_,Main.E_FILE_NAMES[type_]+"0000.png");
	    lastMilAni = Main.scores.pp.millis();
	    frameC = 0;

	    images = new ArrayList<PImage>();
	    for (int c = 0; c < Main.E_FRAMES[type_]; c++)
	      images.add(Main.scores.pp.loadImage(Main.E_FILE_NAMES[type_]+Main.scores.pp.nf(c,4)+".png"));
	    Particle.img = images.get(0);

	    health = Main.E_HEALTH[type_];

	    cWidth = Particle.img.width;
	    cHeight = Particle.img.height;

	    TYPE = type_;
	  }

	  Enemy(float x_, float y_, int type_, int health_, int size_, String fileName_) {
	    super(x_, y_,fileName_+"0000.png");
	    lastMilAni = Main.scores.pp.millis();
	    frameC = 0;

	    images = new ArrayList<PImage>();
	    for (int c = 0; c < size_; c++)
	      images.add(Main.scores.pp.loadImage(fileName_+Main.scores.pp.nf(c,4)+".png"));
	    Particle.img = images.get(0);

	    health = health_;

	    cWidth = Particle.img.width;
	    cHeight = Particle.img.height;

	    TYPE = type_;
	  }

	  @Override
	  void update() {

	    checkWalls();

	    if(state == NORM && health <= 0) {
	      Main.scores.score += Main.E_SCORE[TYPE];

	    if ((int)Main.scores.pp.random(5) == 0) {
	      if ((int)Main.scores.pp.random(5) == 0)
	        Main.items.add(new Item(Main.camera.loc.x,Main.camera.loc.y,Item.FULLHEALTH));
	      else
	        Main.items.add(new Item(Main.camera.loc.x,Main.camera.loc.y,(int)Main.scores.pp.random(1,4)));
	    }

	    Main.particles.add(new Particle(Main.camera.loc.x, Main.camera.loc.y, Particle.ANIM, 4, "Sprites/Exposions/ExplosionSmall"));

	      state = DEAD;
	    } else if (state == DEAD) {
	      Main.camera.acc.y = Main.gravity;

	    } else if (leftWall || topWall || bottomWall) {
	      Main.enemies.remove(this);
	      return;
	    }

	    Main.camera.simplePhysicsCal();

	    if (state == NORM)
	      animate(0, images.size()-1,animationSpeed,true,false);

	    damageIfHit();

	    drawImage();
	  }

	  void damageIfHit() {
	    boolean hitPlayer = hitCharacter(Main.player);



	    if (TYPE < Main.E_DAMAGE.length && TYPE >= 0 && hitPlayer && !lastHitPlayer){
	      Main.player.decreaseHealth(Main.E_DAMAGE[TYPE]);

	      Particle particle = new Particle(Main.camera.loc.x, Main.camera.loc.y, Particle.TEXT, 0, "-"+Main.E_DAMAGE[TYPE]);
	      particle.floatUp = true;
	      Main.particles.add(particle);

	      Main.player.lastMilTint = Main.scores.pp.millis();
	    }

	    lastHitPlayer = hitPlayer;
	  }

	  void animate(int min, int max, int aniSpeed, boolean loop, boolean reverse) {

	    if (Main.scores.pp.millis() - lastMilAni > aniSpeed) {
	      if (reverse)
	        frameC--;
	      else
	        frameC++;

	      lastMilAni = Main.scores.pp.millis();
	    }
	    if (reverse) {
	      if (frameC > max)
	      frameC = max;
	      else if (loop && frameC < min)
	      frameC = max;
	      else if (!loop && frameC < min)
	      frameC = min;
	      } else {
	        if (frameC < min)
	        frameC = min;
	        else if (loop && frameC > max)
	        frameC = min;
	        else if (!loop && frameC > max)
	        frameC = max;
	      }

	      Particle.img = images.get(frameC);
	    }


	  @Override
	  void drawImage() {
		Main.scores.pp.pushMatrix();
		Main.scores.pp.translate(Main.camera.loc.x,Main.camera.loc.y);

	    if (state == DEAD)
	    	Main.scores.pp.tint(100);
	    else if (Main.scores.pp.millis() - lastMilTint < tintDuration)
	    	Main.scores.pp.tint(255, 0, 0);

	    if(Particle.img != null)
	    	Main.scores.pp.image(Particle.img, 0, 0);

	    Main.scores.pp.noTint();

	    Main.scores.pp.popMatrix();
	  }
}
