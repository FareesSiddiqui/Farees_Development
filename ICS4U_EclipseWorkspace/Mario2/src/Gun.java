import processing.core.*;

public class Gun {
	float x;
	float y;
	PImage gunTexture;

	int fireRate;
	int frameLastFired;
	int Damage;
	int firing;

	float rotation;

	int flipl;

	int MuzzleFlashRandomNumber;
	
	public static PApplet pp;
	Gun(float x1, float y1, String TextureName, PApplet app) {
		x = x1;
		y = y1;
		pp = app;
		gunTexture = pp.loadImage(TextureName);

		fireRate = 2;
		Damage = 120;

		rotation = 0;
		flipl = 1;

		MuzzleFlashRandomNumber = 0;

		frameLastFired = Main.frameCount;
	}

	static int equipedWeapon = 2;

	static Gun AK47;

	static PImage[] MuzzleFlashes = new PImage[3];

	public static void initGuns()
	{
	  AK47 = new Gun(-35, -10, "Weapons/AK47.png", pp);

	  for (int i=0; i<3; i++)
	  {
	    MuzzleFlashes[i] = pp.loadImage("Weapons/MuzzleFlash"+i+".png");
	  }

	  pp.strokeWeight(4);
	  pp.rectMode(pp.CORNERS);
//	  pp.stroke(pp.#FFDE64);
	}

	void updateWeapon() {
		AK47.rotation = pp.atan((pp.mouseY - Main.mainPlayer.y - 65) / ((Main.cameraXpos + pp.mouseX) - Main.mainPlayer.x - 25));
		if ((Main.cameraXpos + pp.mouseX) > Main.mainPlayer.x)
			AK47.flipl = 1;
		else
			AK47.flipl = -1;
	}

	void display() {
		updateWeapon();

		if (pp.mousePressed && pp.mouseButton == pp.LEFT && Main.frameCount - frameLastFired > fireRate) {
			frameLastFired = Main.frameCount;

			firing = 1;

			MuzzleFlashRandomNumber = (int) pp.random(3);

			float lineX1 = Main.mainPlayer.x + 25;
			float lineY1 = Main.mainPlayer.y + 65;
			float lineX2 = Main.mainPlayer.x + 25 + 1000 * pp.cos(rotation) * flipl;
			float lineY2 = Main.mainPlayer.y + 65 + 1000 * pp.sin(rotation) * flipl;

			boolean hitSomething = false;
			for (int i = 0; i < Main.goombaEnemies.size(); i++) {
				if (pp.abs(Main.mainPlayer.x - Main.goombaEnemies.get(i).x) < 1200) {
					// rect(goombaEnemies.get(i).x,goombaEnemies.get(i).y,goombaEnemies.get(i).x+50,goombaEnemies.get(i).y+50);

					if (Main.SegmentIntersectRectangle(Main.goombaEnemies.get(i).x, Main.goombaEnemies.get(i).y,
							Main.goombaEnemies.get(i).x + 50, Main.goombaEnemies.get(i).y + 50, lineX1, lineY1, lineX2, lineY2)) {
						// line(lineX1,lineY1,goombaEnemies.get(i).x+25,goombaEnemies.get(i).y+25);
						if (Main.goombaEnemies.get(i).damage(60)) {
							Main.goombaEnemies.remove(i);
							Main.score += 100;
							i--;
							continue;
						}
						hitSomething = true;
					}
				}
			}
			if (!hitSomething) {
				// line(lineX1,lineY1,lineX2,lineY2);
			}
		}

		pp.pushMatrix();

		pp.translate(Main.mainPlayer.x + 25, Main.mainPlayer.y + 65);

		pp.rotate(rotation);

		pp.scale(flipl, 1);

		if (firing > 0) {
			firing--;

			pp.line(0, 0, 1000, 0);

			pp.pushMatrix();
			pp.scale((float)0.25, (float)0.25);
			pp.image(MuzzleFlashes[MuzzleFlashRandomNumber], 150, -MuzzleFlashes[MuzzleFlashRandomNumber].height / 2);
			pp.popMatrix();
		}

		pp.translate(x, y);

		pp.scale((float)0.5, (float)0.5);

		pp.image(gunTexture, 0, 0);

		pp.popMatrix();
	}
}
