import java.util.ArrayList;

import processing.core.*;

public class Player extends Actor {
	float dragX = (float) 0.01;
	float velLimit = 10;
	float inputDelta = (float) 0.5;

	float reloadDelay = 200;
	int lastMilreLoad;

	float projectileSpread = (float) 0.02;

	int animationSpeed = 250;

	int lastMilAni;
	int frameC;
	ArrayList<PImage> images;

	ArrayList<Projectile> projectiles;

	int tintDuration = 50;
	int lastMilTint;

	int state;

	final int NORM = 0;
	final int DEAD = 1;

	Player(int size_, String fileName_) {
		super(fileName_ + "0000.png");
		lastMilAni = Main.scores.pp.millis();
		frameC = 0;

		lastMilreLoad = Main.scores.pp.millis();

		projectiles = new ArrayList<Projectile>();

		images = new ArrayList<PImage>();
		for (int c = 0; c < size_; c++)
			images.add(Main.scores.pp.loadImage(fileName_ + Main.scores.pp.nf(c, 4) + ".png"));
		Particle.img = images.get(0);

		maxHealth = 100;
		health = maxHealth;

		cWidth = Particle.img.width;
		cHeight = Particle.img.height / 2;
	}

	Player(float x_, float y_, int size_, String fileName_) {
		super(x_, y_, fileName_ + "0000.png");
		lastMilAni = Main.scores.pp.millis();
		frameC = 0;

		projectiles = new ArrayList<Projectile>();

		images = new ArrayList<PImage>();
		for (int c = 0; c < size_; c++)
			images.add(Main.scores.pp.loadImage(fileName_ + Main.scores.pp.nf(c, 4) + ".png"));
		Particle.img = images.get(0);

		health = 100;

		cWidth = Particle.img.width;
		cHeight = Particle.img.height / 2;
	}

	@Override
	void update() {
		if (state == NORM && health <= 0) {
			state = DEAD;
		} else if (state == DEAD && Main.camera.loc.y < Main.camera.loc.y + Main.scores.pp.height / 2) {
			movementControl(new PVector());
		} else if (state == NORM) {
			inputCalculations();

			checkWalls();

			animationController();

			prjectileController();
		}

		drawImage();
	}

	void prjectileController() {

		if (Main.zHold && Main.scores.pp.millis() - lastMilreLoad > reloadDelay) {
			PVector v = new PVector(30, 0);
			v.rotate(Main.scores.pp.random(-Main.scores.pp.PI * projectileSpread, Main.scores.pp.PI * projectileSpread));
			projectiles.add(new Projectile(Main.camera.loc.x, Main.camera.loc.y, v.x, v.y, 1, randomTexturedProjectile()));
			lastMilreLoad = Main.scores.pp.millis();
			Main.scores.shot++;
		}

		for (int c = 0; c < projectiles.size(); c++) {
			Projectile p = projectiles.get(c);

			// if off screen
			if (p.dead) {
				projectiles.remove(p);
				continue;
			}

			// if hit boss
			if (Main.boss != null && Main.boss.state != Main.boss.DEAD && p.hit(Main.boss)) {
				if (p.hitBossPart == p.BODY) {
					Main.boss.decreaseHealth(p.power * 1);
					Main.boss.lastMilTint = Main.scores.pp.millis();

					Particle particle = new Particle(p.loc.x, p.loc.y, Particle.TEXT, 0, "-" + p.power);
					particle.floatUp = true;
					Main.particles.add(particle);

					projectiles.remove(p);
					Main.scores.shotHit++;
					continue;

				} else if (p.hitBossPart == p.ARM) {
					Main.boss.decreaseHealth((float) (p.power * .25));
					Main.boss.lastMilTint = Main.scores.pp.millis();

					Particle particle = new Particle(p.loc.x, p.loc.y, Particle.TEXT, 0, "-" + p.power * .25);
					particle.floatUp = true;
					Main.particles.add(particle);

					projectiles.remove(p);
					Main.scores.shotHit++;
					continue;

				} else if (p.hitBossPart == p.CRYSTAL) {
					Main.boss.decreaseHealth(p.power * 5);
					Main.boss.lastMilTint = Main.scores.pp.millis();

					Particle particle = new Particle(p.loc.x, p.loc.y, Particle.TEXT, 0, "-" + p.power * 5);
					particle.floatUp = true;
					Main.particles.add(particle);

					projectiles.remove(p);
					Main.scores.shotHit++;
					continue;

				} else if (!p.bounced) {
					// bounce off shell if hit

					float xDif = Main.scores.pp.max(p.loc.x, Main.camera.loc.x) - Main.scores.pp.min(p.loc.x, Main.camera.loc.x);
					float yDif = Main.scores.pp.max(p.loc.y, Main.camera.loc.y) - Main.scores.pp.min(p.loc.y, Main.camera.loc.y);

					float angle = Main.scores.pp.atan2(yDif, xDif);

					float angleDifference = angle - p.vel.heading();
					angleDifference = p.loc.y < Main.camera.loc.y ? -angleDifference : angleDifference;

					float newAngle = Main.scores.pp.PI - p.vel.heading() - angleDifference * 2;

					PVector temp = new PVector(p.vel.mag(), 0);

					temp.rotate(newAngle);

					p.vel = temp;
					p.bounced = true;

				}
			}

			// if hit enemy
			for (int i = 0; i < Main.enemies.size(); i++) {
				Enemy e = Main.enemies.get(i);
				if (p.hit(e)) {
					if (e.state == e.NORM) {
						e.decreaseHealth(p.power);
						e.lastMilTint = Main.scores.pp.millis();

						Particle particle = new Particle(p.loc.x, p.loc.y, Particle.TEXT, 0, "-" + p.power);
						particle.floatUp = true;
						Main.particles.add(particle);

						Main.scores.shotHit++;
					}

					projectiles.remove(p);
					continue;
				}
			}

			p.update();
		}
	}

	String randomTexturedProjectile() {
		int rand = (int) Main.scores.pp.random(0, 8);
		return "Sprites/Projectiles/Projectile000" + rand + ".png";
	}

	void inputCalculations() {
		// make a new input vector
		PVector input = new PVector();

		// set the input vector
		if (Main.rightHold)
			input.x += 1;
		if (Main.leftHold)
			input.x -= 1;
		if (Main.downHold)
			input.y += 1;
		if (Main.upHold)
			input.y -= 1;

		// make it so when it goes diaginal that it wont go twice as fast
		input.normalize();
		movementControl(input);
	}

	void movementControl(PVector input_) {
		// speedup for slowdown the input vector by delta

		input_.mult(inputDelta);

		// gravity
		input_.add(new PVector(0, Main.gravity));

		// make input the acc
		Main.camera.acc = input_;

		// limit speed of the aircraft
		if (Main.shiftHold)
			Main.camera.vel.limit(velLimit / 2);
		else
			Main.camera.vel.limit(velLimit);

		// movement dampening on x axis
		Main.camera.vel.sub(new PVector(Main.camera.vel.x * dragX * Main.deltaTime, 0));

		// calculate physics
		Main.camera.simplePhysicsCal();
	}

	void animationController() {

		float angleStep = (float) (velLimit * .1);
		float angleChangePoint1 = (float) 2.5;
		float angleChangePoint2 = 7;

		if (Main.rightHold) {

			// boost mode
			if (Main.camera.vel.y > angleStep * angleChangePoint1) {
				// if going down
				if (Main.camera.vel.y > angleStep * angleChangePoint2)
					// larger angle for faster lowering speed
					animate(10, 11, animationSpeed, true);
				else
					animate(12, 13, animationSpeed, true);

			} else if (Main.camera.vel.y < -angleStep * angleChangePoint1) {
				// if going up
				if (Main.camera.vel.y < -angleStep * angleChangePoint2)
					// larger angle for faster ascending speed
					animate(18, 19, animationSpeed, true);
				else
					animate(16, 17, animationSpeed, true);

			} else {
				// if not up or down
				animate(14, 15, animationSpeed, true);
			}

		} else {

			// not boost mode
			if (Main.camera.vel.y > angleStep * angleChangePoint1) {
				// if going down
				if (Main.camera.vel.y > angleStep * angleChangePoint2)
					// larger angle for faster lowering speed
					animate(0, 1, animationSpeed, true);
				else
					animate(2, 3, animationSpeed, true);

			} else if (Main.camera.vel.y < -angleStep * angleChangePoint1) {
				// if going up
				if (Main.camera.vel.y < -angleStep * angleChangePoint2)
					// larger angle for faster ascending speed
					animate(8, 9, animationSpeed, true);
				else
					animate(6, 7, animationSpeed, true);

			} else {
				// if not up or down
				animate(4, 5, animationSpeed, true);
			}
		}
	}

	void animate(int min, int max, int aniSpeed, boolean loop) {
		if (Main.scores.pp.millis() - lastMilAni > aniSpeed) {
			frameC++;
			lastMilAni = Main.scores.pp.millis();
		}

		if (frameC < min)
			frameC = min;
		else if (loop && frameC > max)
			frameC = min;
		else if (!loop && frameC > max)
			frameC = max;

		Particle.img = images.get(frameC);
	}

	@Override
	void drawImage() {
		Main.scores.pp.pushMatrix();
		Main.scores.pp.translate(Main.camera.loc.x, Main.camera.loc.y);

		if (state == DEAD)
			Main.scores.pp.tint(100);
		else if (Main.scores.pp.millis() - lastMilTint < tintDuration)
			Main.scores.pp.tint(255, 0, 0);

		if (imgFlip)
			Main.scores.pp.scale(-1, 1);

		if (Particle.img != null)
			Main.scores.pp.image(Particle.img, 0, 0);

		Main.scores.pp.noTint();

		Main.scores.pp.popMatrix();
	}

	@Override
	void checkWalls() {

		float camWidthMin = -Main.camera.loc.x;
		float camWidthMax = -Main.camera.loc.x + Main.scores.pp.width;
		float camHeightMin = Main.camera.loc.y - Main.scores.pp.height;
		float camHeightMax = Main.camera.loc.y;

		rightWall = Main.camera.loc.x + cWidth / 2 > camWidthMax;
		leftWall = Main.camera.loc.x - cWidth / 2 < camWidthMin;
		topWall = Main.camera.loc.y - cHeight / 2 < camHeightMin + Main.ground.height;
		bottomWall = Main.camera.loc.y + cHeight / 2 > camHeightMax - Main.ground.height;

		if (leftWall) {
			Main.camera.loc.x = camWidthMin + cWidth / 2;
			Main.camera.vel.x = 0;
		}
		if (topWall) {
			Main.camera.vel.y *= -0.5;
			Main.camera.loc.y = camHeightMin + Main.ground.height + cHeight / 2;
		}
		if (bottomWall) {
			Main.camera.vel.y *= -0.5;
			Main.camera.loc.y = camHeightMax - Main.ground.height - cHeight / 2;
		}
	}

}
