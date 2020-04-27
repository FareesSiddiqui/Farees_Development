import processing.core.*;
public class Item {
	float x;
	float y;
	float xvel;
	float yvel;

	boolean onGround;

	int itemType;
	
	protected final PApplet pp;

	Animation movingAnimation;

	Item(int x1, int y1, int t1, PApplet app) {
		itemType = t1;
		pp = app;
		int s1 = 0;

		if (itemType == 1) {
			movingAnimation = new Animation("Items/SuperMushroom/", "gif", 1, 100, pp);
			s1 = 5;
		} else if (itemType == 2) {
			movingAnimation = new Animation("Items/FireFlower/", "gif", 2, 3, pp);
		} else if (itemType == 3) {
			movingAnimation = new Animation("Items/CapeFeather/", "gif", 1, 100, pp);
		} else if (itemType == 4) {
			movingAnimation = new Animation("Items/SuperStar/", "gif", 4, 2, pp);
			s1 = 7;
		} else {
			movingAnimation = new Animation("Items/SuperMushroom/", "gif", 1, 100, pp);
		}

		x = x1;
		y = y1;
		xvel = s1;
		yvel = 20;

		onGround = false;
	}

	void update() {
		if (!onGround) {
			yvel -= 3.5;
		}
		yvel = pp.constrain(yvel, -60, 60);

		x += xvel;
		y -= yvel;

		if ((int) (x / Main.gridSize - 1) < 0 || (int) (x / Main.gridSize + 2) >= 1280 || (int) (y / Main.gridSize - 1) < 0
				|| (int) (y / Main.gridSize + 4) >= 100)
			return;

		float boundingBoxLeft = x;
		float boundingBoxRight = x + 2 * Main.gridSize;
		float boundingBoxTop = y;
		float boundingBoxBottom = y + 2 * Main.gridSize;

		if (yvel <= 0) {
			onGround = false;
			if (MarioActions.gridAtLocation(boundingBoxLeft, boundingBoxBottom) == 1) {
				// drawGrid(MarioActions.gridify(boundingBoxLeft),MarioActions.gridify(boundingBoxBottom));
				yvel = 0;
				y = MarioActions.gridify(boundingBoxBottom) * Main.gridSize - 2 * Main.gridSize;
				onGround = true;
			} else if (MarioActions.gridAtLocation((boundingBoxLeft + boundingBoxRight) / 2, boundingBoxBottom) == 1) {
				// drawGrid(MarioActions.gridify((boundingBoxLeft+boundingBoxRight)/2),MarioActions.gridify(boundingBoxBottom));
				yvel = 0;
				y = MarioActions.gridify(boundingBoxBottom) * Main.gridSize - 2 * Main.gridSize;
				onGround = true;
			} else if (MarioActions.gridAtLocation(boundingBoxRight, boundingBoxBottom) == 1) {
				// drawGrid(MarioActions.gridify(boundingBoxRight),MarioActions.gridify(boundingBoxBottom));
				yvel = 0;
				y = MarioActions.gridify(boundingBoxBottom) * Main.gridSize - 2 * Main.gridSize;
				onGround = true;
			}
			if (MarioActions.gridAtLocation(boundingBoxLeft, boundingBoxBottom - Main.gridSize) == 1) {
				// drawGrid(MarioActions.gridify(boundingBoxLeft),MarioActions.gridify(boundingBoxBottom)-1);
				yvel = 0;
				y = MarioActions.gridify(boundingBoxBottom) * Main.gridSize - 3 * Main.gridSize;
				onGround = true;
			} else if (MarioActions.gridAtLocation((boundingBoxLeft + boundingBoxRight) / 2, boundingBoxBottom - Main.gridSize) == 1) {
				// drawGrid(MarioActions.gridify((boundingBoxLeft+boundingBoxRight)/2),MarioActions.gridify(boundingBoxBottom)-1);
				yvel = 0;
				y = MarioActions.gridify(boundingBoxBottom) * Main.gridSize - 3 * Main.gridSize;
				onGround = true;
			} else if (MarioActions.gridAtLocation(boundingBoxRight, boundingBoxBottom - Main.gridSize) == 1) {
				// drawGrid(MarioActions.gridify(boundingBoxRight),MarioActions.gridify(boundingBoxBottom)-1);
				yvel = 0;
				y = MarioActions.gridify(boundingBoxBottom) * Main.gridSize - 3 * Main.gridSize;
				onGround = true;
			}
		}

		boundingBoxTop = y;
		boundingBoxBottom = y + 2 * Main.gridSize;

		if (xvel < 0) {
			for (int i = 0; i < 2; i++) {
				if (MarioActions.gridAtLocation(boundingBoxLeft, boundingBoxTop + i * Main.gridSize) == 1) {
					// drawGrid(MarioActions.gridify(boundingBoxLeft),MarioActions.gridify(boundingBoxTop)+i);
					xvel *= -1;
					x = (MarioActions.gridify(boundingBoxLeft) + 1) * Main.gridSize + MarioActions.boundingBoxWidth;
					break;
				}
			}
		}
		if (xvel > 0) {
			for (int i = 0; i < 2; i++) {
				if (MarioActions.gridAtLocation(boundingBoxRight, boundingBoxTop + i * Main.gridSize) == 1) {
					// drawGrid(MarioActions.gridify(boundingBoxRight),MarioActions.gridify(boundingBoxTop)+i);
					xvel *= -1;
					x = (MarioActions.gridify(boundingBoxRight) - 2) * Main.gridSize - MarioActions.boundingBoxWidth;
					break;
				}
			}
		}
	}

	void display() {
		pp.pushMatrix();

		pp.translate(x, y);

		movingAnimation.display(0, 5);

		pp.popMatrix();
	}
}
