import java.util.ArrayList;

import processing.core.*;

public class Boss extends Enemy{
	ArrayList<Projectile> projectiles;

	final int SHOOT = 10;
	final int SHIELD = 11;
	final int LASER = 12;

	int lastState;

	boolean animateReverse;
	int animateState;

	int lastMilSate;
	int stateSwitchDelay;

	int lastMilShoot;
	int shootDelay;

	int lastMilLaser;
	int laserDelay;

	float range = 100;

	boolean laser;

	boolean active;

	int laserPower = 10;
	int projectilePower = 20;

	PImage laserImg;

	Boss(float x_, float y_) {
		super(x_, y_, -1, 50, 17, "Sprites/Boss/Boss");
		state = (int) Main.scores.pp.random(11, 13);
		lastState = SHIELD;
		animationSpeed = 75;
		stateSwitchDelay = 1000;
		lastMilSate = Main.scores.pp.millis();
		shootDelay = 1000;
		lastMilShoot = Main.scores.pp.millis();
		laserDelay = 250;
		lastMilLaser = Main.scores.pp.millis();

		laserImg = Main.scores.pp.loadImage("Sprites/Projectiles/Laser0000.png");

		Main.camera.smoothFactor = (float) 0.1;

		projectiles = new ArrayList<Projectile>();
	}

	@Override
	void update() {

		if (active) {

			if (state != DEAD && health <= 0) {
				state = DEAD;
				Main.scores.score += 1000;

				Main.textFields.add(new TextField("Well, good job. You can now continue", 5000, Main.scores.pp));

				Particle particle = new Particle(Main.camera.loc.x, Main.camera.loc.y, Particle.ANIM, 4, "Sprites/Exposions/ExplosionLarge");
				particle.animationSpeed = 100;
				Main.particles.add(particle);
			}

			if (state == DEAD) {
				Main.camera.acc.y = Main.gravity;
				Main.camera.simplePhysicsCal();
				if (Main.camera.loc.y > Main.scores.pp.height + Particle.img.height)
					Main.boss = null;
			} else {

				Main.camera.des.x = (float) (-Main.camera.loc.x + Main.scores.pp.width * 0.8);

				Main.camera.smoothMove();
				Main.camera.simplePhysicsCal();

				stateController();

				actionController();
				prjectileController();
				laserController();
			}

		} else {
			Main.camera.loc.x = (float) (-Main.camera.loc.x + Main.scores.pp.width * 1.2);
		}

		drawImage();
	}

	void prjectileController() {
		for (int c = 0; c < projectiles.size(); c++) {
			Projectile p = projectiles.get(c);

			if (p.dead) {
				projectiles.remove(p);
				continue;
			}

			if (p.hit(Main.player)) {
				Main.player.decreaseHealth(p.power);
				Main.player.lastMilTint = Main.scores.pp.millis();
				projectiles.remove(p);
				continue;
			}

			p.update();
		}
	}

	void actionController() {
		if (Main.scores.pp.millis() - lastMilShoot > shootDelay && animationDone() && state == SHOOT) {

			// do shoot code

			for (int c = -15; c <= 15; c += 2) {
				int tempC = c;
				int delta = 100;
				PVector v = new PVector(-30, 0);
				if (c > 0) {
					v.rotate((float) ((Main.scores.pp.map(c, 15, 0, 0, 15)) * 0.006));
					tempC = c + delta + c * 15;
				} else {
					v.rotate((float) ((Main.scores.pp.map(c, -15, 0, 0, 15)) * -0.006));
					tempC = c - delta + c * 15;
				}

				projectiles.add(new Projectile(Main.camera.loc.x + 150, Main.camera.loc.y + tempC, v.x, v.y, projectilePower,
						"Sprites/Projectiles/BossProjectile0000.png"));
			}
			lastMilShoot = Main.scores.pp.millis();
		} else {
			laser = animationDone() && state == LASER;
		}

	}

	void stateController() {
		if (Main.player.state == Main.player.DEAD) {
			state = SHIELD;
		} else if (Main.scores.pp.millis() - lastMilSate > stateSwitchDelay && animationDone() && (int) Main.scores.pp.random(200) == 0) {
			if (state == SHIELD) {
				if ((int) Main.scores.pp.random(5) == 0) {
					stateSwitchDelay = 500;
					state = SHOOT;
				} else {
					state = LASER;
					stateSwitchDelay = 1000;
				}

			} else if (state == LASER || state == SHOOT) {
				stateSwitchDelay = 750;
				state = SHIELD;
			}

			lastMilSate = Main.scores.pp.millis();
		}

		animationController();

		lastState = state;
	}

	void animationController() {
		if (lastState == SHIELD && state == SHOOT) {
			animateState = SHOOT;
			animateReverse = false;
			frameC = 8;
		}

		if (lastState == SHIELD && state == LASER) {
			animateState = LASER;
			animateReverse = false;
			frameC = 0;
		}

		if (lastState == LASER && state == SHIELD) {
			animateState = LASER;
			animateReverse = true;
		}

		if (lastState == SHOOT && state == SHIELD) {
			animateState = SHOOT;
			animateReverse = true;
		}

		if (animateState == SHOOT) {
			if (animateReverse)
				animate(8, 16, animationSpeed, false, true);
			else
				animate(8, 16, animationSpeed, false, false);
		}

		if (animateState == LASER) {
			if (animateReverse)
				animate(0, 7, animationSpeed, false, true);
			else
				animate(0, 7, animationSpeed, false, false);
		}

		// animate(0,0,animationSpeed,false,false);
	}

	boolean animationDone() {
		return ((state == SHIELD && (frameC == 0 || frameC == 8)) || (state == LASER && frameC == 7)
				|| (state == SHOOT && frameC == 16));
	}

	void laserController() {
		if (laser) {
			float offsetX = 55;
			if (Main.scores.pp.millis() - lastMilLaser > laserDelay) {
				lastMilLaser = Main.scores.pp.millis();
				// box inside box collision

				float aWidthMin = Main.camera.loc.x - laserImg.width;
				float aWidthMax = Main.camera.loc.x + offsetX;
				float aHeightMin = Main.camera.loc.y - laserImg.height / 2;
				float aHeightMax = Main.camera.loc.y + laserImg.height / 2;

				float bWidthMin = Main.camera.loc.x - Main.player.cWidth / 2;
				float bWidthMax = Main.camera.loc.x +  Main.player.cWidth / 2;
				float bHeightMin = Main.camera.loc.y - Main.player.cHeight / 2;
				float bHeightMax = Main.camera.loc.y + Main.player.cHeight / 2;

				if (aWidthMax > bWidthMin && bWidthMax > aWidthMin && aHeightMax > bHeightMin
						&& bHeightMax > aHeightMin) {

					Main.player.decreaseHealth(laserPower);
					Main.player.lastMilTint = Main.scores.pp.millis();

					Particle particle = new Particle(Main.camera.loc.x, Main.camera.loc.y, Particle.TEXT, 0, "-" + laserPower);
					particle.floatUp = true;
					Main.particles.add(particle);

				}
			}

			Main.scores.pp.pushMatrix();
			Main.scores.pp.translate(Main.camera.loc.x - laserImg.width / 2 + offsetX, Main.camera.loc.y);

			Main.scores.pp.image(laserImg, 0, 0);

			Main.scores.pp.popMatrix();

		}
	}

	@Override
	void drawImage() {
		Main.scores.pp.pushMatrix();
		Main.scores.pp.translate(Main.camera.loc.x, Main.camera.loc.y);

		if (imgFlip)
			Main.scores.pp.scale(-1, 1);

		if (state == DEAD)
			Main.scores.pp.tint(100);
		else if (Main.scores.pp.millis() - lastMilTint < tintDuration)
			Main.scores.pp.tint(255, 0, 0);

		if (Particle.img != null)
			Main.scores.pp.image(Particle.img, 0, 0);

		Main.scores.pp.noTint();

		Main.scores.pp.popMatrix();
	}
}
