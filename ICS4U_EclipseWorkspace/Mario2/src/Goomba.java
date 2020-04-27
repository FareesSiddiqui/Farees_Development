import processing.core.*;
public class Goomba {
	float x;
	float y;
	int facingDirection;
	float xvel;
	float yvel;

	float pathLeft;
	float pathRight;

	boolean onGround;

	int health;
	
	protected final PApplet pp;

	Animation walkAnimation;

	Goomba(int x1, int y1, PApplet app) {
		pp = app;
		reset(x1, y1);

		pathLeft = x - 20;
		pathRight = x + 20;

		walkAnimation = new Animation("GoombaWalk/", "gif", 2, 3, pp);
	}

	void reset(int x1, int y1) {
		x = x1;
		y = y1;
		xvel = 3;
		yvel = 0;
		onGround = false;

		health = 100;

		facingDirection = pp.RIGHT;

		facingDirection = pp.RIGHT;
	}

	void setPath(int l1, int r1) {
		pathLeft = l1;
		pathRight = r1;
	}

	void jump() {
		if (yvel == 0 && onGround) {
			yvel = 40;
			onGround = false;
		}
	}

	boolean damage(int d1) {
		health -= d1;
		if (health <= 0) {
			Effect newEffect = new Effect(3, (int) x - 25, (int) y, pp);
			Main.effects.add(newEffect);

			return true;
		}
		return false;
	}

	void update() {
		x += xvel;

		if (x < pathLeft) {
			x = pathLeft;
			xvel = pp.abs(xvel);
		} else if (x > pathRight) {
			x = pathRight;
			xvel = -pp.abs(xvel);
		}

		y -= yvel;
	}

	void display() {
		pp.pushMatrix();

		pp.translate(0, 0);
		pp.translate(x, y);

		pp.scale((float)0.6, (float)0.6);

		if (facingDirection == pp.LEFT) {
			pp.translate(walkAnimation.images[0].width, 0);
			pp.scale(-1, 1);
		}
		walkAnimation.display(0, 0);

		pp.popMatrix();
	}
}
