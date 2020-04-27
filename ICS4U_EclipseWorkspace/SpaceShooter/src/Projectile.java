import processing.core.*;
import java.util.ArrayList;

public class Projectile {
	PVector loc; // location Vector
	PVector vel; // Velocity Vector
	PVector acc; // Acceleration Vector

	boolean dead;

	PImage img;

	boolean bounced;

	int hitBossPart;

	final int ARM = 0;
	final int BODY = 1;
	final int CRYSTAL = 2;
	final int SHELL = 3;

	int power;

	Projectile() {
		loc = new PVector();
		vel = new PVector();
		acc = new PVector();
	}

	/**
	 * start a new projectile
	 * 
	 * @param x_ x location
	 * @param y_ y location
	 */
	Projectile(float x_, float y_, int power_) {
		loc = new PVector(x_, y_);
		vel = new PVector();
		acc = new PVector();
		power = power_;
	}

	Projectile(float x_, float y_, float xSpeed_, float ySpeed_, int power_, String fileName_) {
		loc = new PVector(x_, y_);
		vel = new PVector(xSpeed_, ySpeed_);
		acc = new PVector();
		img = Main.scores.pp.loadImage(fileName_);
		power = power_;
	}

	void simplePhysicsCal() {
		vel.add(acc.copy().mult(Main.deltaTime));
		loc.add(vel.copy().mult(Main.deltaTime));
	}

	void drawImage() {

		Main.scores.pp.pushMatrix();
		Main.scores.pp.translate(loc.x, loc.y);
		Main.scores.pp.rotate(vel.heading());

		if (img != null)
			Main.scores.pp.image(img, 0, 0);
		else
			Main.scores.pp.ellipse(0, 0, 10, 10);

		Main.scores.pp.popMatrix();
	}

	void update() {
		simplePhysicsCal();
		drawImage();
		checkWalls();
	}

	void checkWalls() {
		float buffer = 100;

		float camWidthMin = -Main.camera.loc.x - buffer;
		float camWidthMax = -Main.camera.loc.x + Main.scores.pp.width + buffer;
		float camHeightMin = Main.camera.loc.y - Main.scores.pp.height - buffer;
		float camHeightMax = Main.camera.loc.y + buffer;
		if (loc.x > camWidthMax || loc.x < camWidthMin || loc.y > camHeightMax || loc.y < camHeightMin) {
			dead = true;
			return;
		}
		dead = false;
	}

	boolean hit(Actor actor) {

		// dot inside box collision
		// dot is a bit ahead of the center of the projectile based on velocity

		float aX = loc.x + vel.x / 2;
		float aY = loc.y + vel.y / 2;

		if (actor.getClass().getSimpleName().equals("Boss")) {
			Boss b = (Boss) actor;

			int fNum = b.frameC;

			int laserGunPath = 10;

			// ellipse(Main.camera.loc.x+80,Main.camera.loc.y,50,50);

			if (fNum == 0 || fNum == 1 || fNum == 2 || fNum == 8) {
				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 40, Main.camera.loc.y) < 310 / 2) {
					hitBossPart = SHELL;
					return true;
				}

				// ellipse(Main.camera.loc.x+40,Main.camera.loc.y,310,310);
			} else if (fNum == 3) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {
					boolean b0 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 113, Main.camera.loc.y - 71) < 60 / 2;
					boolean b1 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 55, Main.camera.loc.y - 85) < 77 / 2;
					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 9, Main.camera.loc.y - 108) < 80 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 65, Main.camera.loc.y - 50) < 200 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 113, Main.camera.loc.y + 71) < 60 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 55, Main.camera.loc.y + 85) < 77 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 9, Main.camera.loc.y + 108) < 80 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 65, Main.camera.loc.y + 50) < 200 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x-113,Main.camera.loc.y-71, 60, 60 );
				// ellipse(Main.camera.loc.x-55 ,Main.camera.loc.y-85, 77, 77 );
				// ellipse(Main.camera.loc.x+9 ,Main.camera.loc.y-108, 80, 80 );
				// ellipse(Main.camera.loc.x+65 ,Main.camera.loc.y-50, 200,200);

			} else if (fNum == 4) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {

					boolean b0 = inBounds(b, aX, aY, 200, 57, -23, -113);

					boolean b1 = inBounds(b, aX, aY, 200, 57, -23, 113);

					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 65, Main.camera.loc.y - 50) < 200 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 65, Main.camera.loc.y + 50) < 200 / 2;

					if (b0 || b1 || b2 || b3) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// rect( Main.camera.loc.x-23 ,Main.camera.loc.y-113, 200,57 );
				// ellipse(Main.camera.loc.x+65 ,Main.camera.loc.y-50 , 200,200);

			} else if (fNum == 5) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {
					boolean b0 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 74, Main.camera.loc.y - 185) < 70 / 2;
					boolean b1 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 21, Main.camera.loc.y - 158) < 70 / 2;
					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 34, Main.camera.loc.y - 132) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 96, Main.camera.loc.y - 46) < 159 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 74, Main.camera.loc.y + 185) < 70 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 21, Main.camera.loc.y + 158) < 70 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 34, Main.camera.loc.y + 132) < 70 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 96, Main.camera.loc.y + 46) < 159 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x-74 ,Main.camera.loc.y-185, 70, 70 );
				// ellipse(Main.camera.loc.x-21 ,Main.camera.loc.y-158, 70, 70 );
				// ellipse(Main.camera.loc.x+34 ,Main.camera.loc.y-132, 70, 70 );
				// ellipse(Main.camera.loc.x+96 ,Main.camera.loc.y-46, 159,159);

			} else if (fNum == 6) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {
					boolean b0 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 33, Main.camera.loc.y - 215) < 70 / 2;
					boolean b1 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 6, Main.camera.loc.y - 176) < 70 / 2;
					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 49, Main.camera.loc.y - 140) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 96, Main.camera.loc.y - 46) < 159 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 33, Main.camera.loc.y + 215) < 70 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 6, Main.camera.loc.y + 176) < 70 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 49, Main.camera.loc.y + 140) < 70 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 96, Main.camera.loc.y + 46) < 159 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x-33 ,Main.camera.loc.y-215, 70, 70 );
				// ellipse(Main.camera.loc.x+6 ,Main.camera.loc.y-176, 70, 70 );
				// ellipse(Main.camera.loc.x+49 ,Main.camera.loc.y-140, 70, 70 );
				// ellipse(Main.camera.loc.x+96 ,Main.camera.loc.y-46, 159,159);

			} else if (fNum == 7) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {
					boolean b0 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 21, Main.camera.loc.y - 292) < 70 / 2;
					boolean b1 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 42, Main.camera.loc.y - 240) < 70 / 2;
					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 70, Main.camera.loc.y - 190) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 93, Main.camera.loc.y - 140) < 70 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 108, Main.camera.loc.y - 57) < 118 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 21, Main.camera.loc.y + 292) < 70 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 42, Main.camera.loc.y + 240) < 70 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 70, Main.camera.loc.y + 190) < 70 / 2;
					boolean b8 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 93, Main.camera.loc.y + 140) < 70 / 2;
					boolean b9 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 108, Main.camera.loc.y + 57) < 118 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x+21 ,Main.camera.loc.y-292, 70, 70 );
				// ellipse(Main.camera.loc.x+42 ,Main.camera.loc.y-240, 70, 70 );
				// ellipse(Main.camera.loc.x+70 ,Main.camera.loc.y-190, 70, 70 );
				// ellipse(Main.camera.loc.x+93 ,Main.camera.loc.y-140, 70, 70 );
				// ellipse(Main.camera.loc.x+108 ,Main.camera.loc.y-57, 118,118);

			} else if (fNum == 9) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {
					boolean b0 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 67, Main.camera.loc.y - 81) < 70 / 2;
					boolean b1 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 47, Main.camera.loc.y - 128) < 70 / 2;
					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 2, Main.camera.loc.y - 158) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 62, Main.camera.loc.y - 163) < 70 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 110, Main.camera.loc.y - 76) < 154 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 67, Main.camera.loc.y + 81) < 70 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 47, Main.camera.loc.y + 128) < 70 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 2, Main.camera.loc.y + 158) < 70 / 2;
					boolean b8 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 62, Main.camera.loc.y + 163) < 70 / 2;
					boolean b9 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 110, Main.camera.loc.y + 76) < 154 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x-67 ,Main.camera.loc.y-81 , 70, 70 );
				// ellipse(Main.camera.loc.x-47 ,Main.camera.loc.y-128, 70, 70 );
				// ellipse(Main.camera.loc.x+2 ,Main.camera.loc.y-158, 70, 70 );
				// ellipse(Main.camera.loc.x+62 ,Main.camera.loc.y-163, 70, 70 );
				// ellipse(Main.camera.loc.x+110,Main.camera.loc.y-76 , 154,154);

			} else if (fNum == 10) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {
					boolean b0 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 40, Main.camera.loc.y - 167) < 70 / 2;
					boolean b1 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 15, Main.camera.loc.y - 200) < 70 / 2;
					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 75, Main.camera.loc.y - 198) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 129, Main.camera.loc.y - 168) < 70 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 156, Main.camera.loc.y - 108) < 70 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y - 47) < 70 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 40, Main.camera.loc.y + 167) < 70 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 15, Main.camera.loc.y + 200) < 70 / 2;
					boolean b8 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 75, Main.camera.loc.y + 198) < 70 / 2;
					boolean b9 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 129, Main.camera.loc.y + 168) < 70 / 2;
					boolean b10 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 156, Main.camera.loc.y + 108) < 70 / 2;
					boolean b11 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y + 47) < 70 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x-40 ,Main.camera.loc.y-167, 70, 70 );
				// ellipse(Main.camera.loc.x+15 ,Main.camera.loc.y-200, 70, 70 );
				// ellipse(Main.camera.loc.x+75 ,Main.camera.loc.y-198, 70, 70 );
				// ellipse(Main.camera.loc.x+129,Main.camera.loc.y-168, 70, 70 );
				// ellipse(Main.camera.loc.x+156,Main.camera.loc.y-108, 70, 70 );
				// ellipse(Main.camera.loc.x+144,Main.camera.loc.y-47 , 70, 70 );

			} else if (fNum == 11) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {
					boolean b0 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 8, Main.camera.loc.y - 225) < 70 / 2;
					boolean b1 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 45, Main.camera.loc.y - 240) < 70 / 2;
					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 110, Main.camera.loc.y - 226) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 147, Main.camera.loc.y - 173) < 70 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 156, Main.camera.loc.y - 108) < 70 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y - 47) < 70 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x - 8, Main.camera.loc.y + 225) < 70 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 45, Main.camera.loc.y + 240) < 70 / 2;
					boolean b8 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 110, Main.camera.loc.y + 226) < 70 / 2;
					boolean b9 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 147, Main.camera.loc.y + 173) < 70 / 2;
					boolean b10 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 156, Main.camera.loc.y + 108) < 70 / 2;
					boolean b11 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y + 47) < 70 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x-8 ,Main.camera.loc.y-225, 70, 70 );
				// ellipse(Main.camera.loc.x+45 ,Main.camera.loc.y-240, 70, 70 );
				// ellipse(Main.camera.loc.x+110,Main.camera.loc.y-226, 70, 70 );
				// ellipse(Main.camera.loc.x+147,Main.camera.loc.y-173, 70, 70 );
				// ellipse(Main.camera.loc.x+156,Main.camera.loc.y-108, 70, 70 );
				// ellipse(Main.camera.loc.x+144,Main.camera.loc.y-47 , 70, 70 );

			} else if (fNum == 12) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {

					boolean b0 = inBounds(b, aX, aY, 60, 144, -153, -130);

					boolean b1 = inBounds(b, aX, aY, 60, 144, -153, 130);

					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 36, Main.camera.loc.y - 274) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 92, Main.camera.loc.y - 268) < 70 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 137, Main.camera.loc.y - 231) < 70 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y - 47) < 70 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 36, Main.camera.loc.y + 274) < 70 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 92, Main.camera.loc.y + 268) < 70 / 2;
					boolean b8 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 137, Main.camera.loc.y + 231) < 70 / 2;
					boolean b9 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y + 47) < 70 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x+36 ,Main.camera.loc.y-274, 70, 70 );
				// ellipse(Main.camera.loc.x+92 ,Main.camera.loc.y-268, 70, 70 );
				// ellipse(Main.camera.loc.x+137,Main.camera.loc.y-231, 70, 70 );
				// ellipse(Main.camera.loc.x+144,Main.camera.loc.y-47 , 70, 70 );
				// rect( Main.camera.loc.x+153,Main.camera.loc.y-130, 60, 144);

			} else if (fNum == 13) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {

					boolean b0 = inBounds(b, aX, aY, 60, 191, -153, -153);

					boolean b1 = inBounds(b, aX, aY, 60, 191, -153, 153);

					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 82, Main.camera.loc.y - 309) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 130, Main.camera.loc.y - 273) < 70 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y - 47) < 70 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 82, Main.camera.loc.y + 309) < 70 / 2;
					boolean b6 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 130, Main.camera.loc.y + 273) < 70 / 2;
					boolean b7 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y + 47) < 70 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5 || b6 || b7) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x+82 ,Main.camera.loc.y-309, 70, 70 );
				// ellipse(Main.camera.loc.x+130,Main.camera.loc.y-273, 70, 70 );
				// ellipse(Main.camera.loc.x+144,Main.camera.loc.y-47 , 70, 70 );
				// rect( Main.camera.loc.x+153,Main.camera.loc.y-153, 60, 191);

			} else if (fNum == 14) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {

					boolean b0 = inBounds(b, aX, aY, 60, 237, -153, -178);

					boolean b1 = inBounds(b, aX, aY, 60, 237, -153, 178);

					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 123, Main.camera.loc.y - 325) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y - 47) < 70 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 123, Main.camera.loc.y + 325) < 70 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y + 47) < 70 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x+123,Main.camera.loc.y-325, 70, 70 );
				// ellipse(Main.camera.loc.x+144,Main.camera.loc.y-47 , 70, 70 );
				// rect( Main.camera.loc.x+153,Main.camera.loc.y-178, 60, 237);

			} else if (fNum == 15) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {

					boolean b0 = inBounds(b, aX, aY, 60, 253, -153, -178);

					boolean b1 = inBounds(b, aX, aY, 60, 253, -153, 178);

					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 145, Main.camera.loc.y - 339) < 58 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y - 47) < 70 / 2;
					boolean b4 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 145, Main.camera.loc.y + 339) < 58 / 2;
					boolean b5 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y + 47) < 70 / 2;

					if (b0 || b1 || b2 || b3 || b4 || b5) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x+145,Main.camera.loc.y-339, 58, 58 );
				// ellipse(Main.camera.loc.x+144,Main.camera.loc.y-47 , 70, 70 );
				// rect( Main.camera.loc.x+153,Main.camera.loc.y-178, 60, 253);

			} else if (fNum == 16) {

				if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 50, Main.camera.loc.y) < 230 / 2
						&& (aY < Main.camera.loc.y - laserGunPath || aY > Main.camera.loc.y + laserGunPath)) {
					hitBossPart = BODY;
					return true;
				} else if (Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 80, Main.camera.loc.y) < 50 / 2) {
					hitBossPart = CRYSTAL;
					return true;
				} else {

					boolean b0 = inBounds(b, aX, aY, 60, 293, -153, -205);

					boolean b1 = inBounds(b, aX, aY, 60, 293, -153, 205);

					boolean b2 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y - 47) < 70 / 2;
					boolean b3 = Main.scores.pp.dist(aX, aY, Main.camera.loc.x + 144, Main.camera.loc.y + 47) < 70 / 2;

					if (b0 || b1 || b2 || b3) {
						hitBossPart = ARM;
						return true;
					}
				}

				// ellipse(Main.camera.loc.x+50,Main.camera.loc.y,230,230);
				//
				// ellipse(Main.camera.loc.x+144,Main.camera.loc.y-47 , 70, 70 );
				// rect( Main.camera.loc.x+153,Main.camera.loc.y-205, 60, 293);

			}

			return false;
		} else {

			float bWidthMin = Main.camera.loc.x - actor.cWidth / 2;
			float bWidthMax = Main.camera.loc.x + actor.cWidth / 2;
			float bHeightMin = Main.camera.loc.y - actor.cHeight / 2;
			float bHeightMax = Main.camera.loc.y + actor.cHeight / 2;

			return aX > bWidthMin && aX < bWidthMax && aY > bHeightMin && aY < bHeightMax;
		}

	}

	boolean inBounds(Boss b_, float aX_, float aY_, float tempCWidth_, float tempCHeight_, float offsetX_,
			float offsetY_) {

		float bWidthMin, bWidthMax, bHeightMin, bHeightMax;

		offsetX_ = -offsetX_;
		

		bWidthMin = Main.camera.loc.x + offsetX_ - tempCWidth_ / 2;
		bWidthMax = Main.camera.loc.x + offsetX_ + tempCWidth_ / 2;
		bHeightMin = Main.camera.loc.y + offsetY_ - tempCHeight_ / 2;
		bHeightMax = Main.camera.loc.y + offsetY_ + tempCHeight_ / 2;

		// rect(b_.loc.x+offsetX_,b_.loc.y+offsetY_,tempCWidth_,tempCHeight_);

		return aX_ > bWidthMin && aX_ < bWidthMax && aY_ > bHeightMin && aY_ < bHeightMax;
	}

}
