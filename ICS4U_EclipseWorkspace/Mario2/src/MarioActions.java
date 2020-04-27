import processing.core.*;
public class MarioActions {
	float x;
	float y;
	int facingDirection;
	float xvel;
	float yvel;

	PImage stillImg;
	PImage jumpImg;
	PImage fallImg;

	int livingState;

	int health;

	boolean onGround;
	
	public static PApplet pp;

	Animation walkAnimation;
	Animation explodeAnimation;

	MarioActions(int x1, int y1, PApplet app) {
		resetMario(x1, y1);
		pp = app;
		walkAnimation = new Animation("MarioWalk/", "gif", 4, 2, pp);
		explodeAnimation = new Animation("MarioExplosion/", "gif", 5, 3, pp);
		
		
		stillImg= pp.loadImage("MarioStill.gif");
		jumpImg = pp.loadImage("MarioJump.gif");
		fallImg = pp.loadImage("MarioFall.gif");
		
		differenceBetweenTheSizeOfTheStillImgAndTheWalkingImg = walkAnimation.images[0].width - stillImg.width;
	}

	static int gridAtLocation(float x1, float y1) {
		return (int) (Main.grid[(int) (x1 / Main.gridSize)][(int) (y1 / Main.gridSize)]);
	}

	static int gridify(float x1) {
		return (int) (x1 / Main.gridSize);
	}

	int quadify(float x1) {
		return (int) (gridify(x1) / 2) * 2;
	}

	void drawGrid(int i1, int j1) {
		pp.fill(255, 255, 0);
		pp.rect(i1 * Main.gridSize, j1 * Main.gridSize, Main.gridSize, Main.gridSize);
	}

	int lowerBoundQuad(int x1, int low, int high) {
		while (low <= high) {
			int mid = (low + high) / 2;
			long midVal = Main.distroyedQuadBlocks.get(mid).x;

			if (midVal < x1)
				low = mid + 1;
			else if (midVal > x1)
				high = mid - 1;
			else if (low != mid) // Equal but range is not fully scanned
				high = mid;
			else
				return mid; // key found
		}
		return -1;
	}

	void breakQuadBlock(float x1, float y1) {
		int gx = quadify(x1);
		int gy = quadify(y1) - 1;

		int i = 0;
		i = lowerBoundQuad(gx * Main.gridSize, 0, Main.distroyedQuadBlocks.size() - 1);

		if (i != -1) {
			for (; i < Main.distroyedQuadBlocks.size(); i++) {
				if (Main.distroyedQuadBlocks.get(i).x != gx * Main.gridSize) {
					i = Main.distroyedQuadBlocks.size();
					break;
				}
				pp.println(Main.distroyedQuadBlocks.get(i).y);
				if (Main.distroyedQuadBlocks.get(i).y == gy * Main.gridSize) {
					if (Main.distroyedQuadBlocks.get(i).blockType == 1) {
						Main.distroyedQuadBlocks.get(i).blockType = 0;
						Main.score += 100;
						Effect newEffect = new Effect(1, gx * Main.gridSize - 120, gy * Main.gridSize - 50 - 25, pp);
						Main.effects.add(newEffect);
					} else if (Main.distroyedQuadBlocks.get(i).blockType == 2) {
						Main.distroyedQuadBlocks.get(i).blockType = 4;
						Main.score += 100;
					} else if (Main.distroyedQuadBlocks.get(i).blockType >= 100) {
						if (Main.distroyedQuadBlocks.get(i).blockType == 100) {
							PVector newCoin = new PVector(Main.distroyedQuadBlocks.get(i).x,
									Main.distroyedQuadBlocks.get(i).y - 1);
							Main.coins.add(newCoin);
						} else {
							Item newItem = new Item(Main.distroyedQuadBlocks.get(i).x,
									Main.distroyedQuadBlocks.get(i).y - 2 * Main.gridSize,
									Main.distroyedQuadBlocks.get(i).blockType - 100, pp);
							Main.items.add(newItem);
						}
						Main.distroyedQuadBlocks.get(i).blockType = 4;
						Main.score += 300;
						Effect newEffect = new Effect(2, gx * Main.gridSize - 25, gy * Main.gridSize - 50, pp);
						Main.effects.add(newEffect);
						Main.distroyedQuadBlocks.get(i).disappearTime = 9;
					} else if (Main.distroyedQuadBlocks.get(i).blockType == 4) {
						return;
					}
					break;
				}
			}
			if (i == Main.distroyedQuadBlocks.size())
				return;
		} else {
			return;
		}

		if (Main.distroyedQuadBlocks.get(i).blockType == 0) {
			for (int k = 0; k < 2; k++) {
				for (int j = 0; j < 2; j++) {
					if (gx + k == pp.constrain(gx + k, 0, 1279) && gy + j == pp.constrain(gy + j, 0, 99)) {
						Main.grid[gx + k][gy + j] = 0;
					}
				}
			}
		}
	}

	static float boundingBoxWidth = -7;
	float boundingBoxHeight = -17;
	boolean movingLeftOrRight = false;

	void resetMario(int x1, int y1) {
		x = x1;
		y = y1;
		xvel = 0;
		yvel = 0;
		onGround = false;

		livingState = 0;

		health = 100;

		facingDirection = pp.RIGHT;
	}

	void jump() {
		if (yvel == 0 && onGround) {
			yvel = 40;
			onGround = false;
		}
	}

	void update() {
		if (livingState == 1) {
			return;
		}

		if (health <= 0) {
			explodeAnimation.resetFrame();
			livingState = 1;
			// reset(500,525);
		}

		movingLeftOrRight = false;
		if (Main.keyHeldDown['A']) {
			xvel -= 3;
			facingDirection = pp.LEFT;
			movingLeftOrRight = true;
		}
		if (Main.keyHeldDown['D']) {
			xvel += 3;
			facingDirection = pp.RIGHT;
			movingLeftOrRight = true;
		}

		if (Main.equipedWeapon == 1) {
			xvel = pp.constrain(xvel, -7, 7);
		} else {
			xvel = pp.constrain(xvel, -15, 15);
		}
		if (movingLeftOrRight == false) {
			xvel *= 0.6;
		}
		if (!onGround) {
			yvel -= 3.5;
		}
		yvel = pp.constrain(yvel, -60, 60);

		x += xvel;
		y -= yvel;

		if ((int) (x / Main.gridSize - 1) < 0 || (int) (x / Main.gridSize + 2) >= 1280 || (int) (y / Main.gridSize - 1) < 0
				|| (int) (y / Main.gridSize + 4) >= 100)
			return;

		float boundingBoxLeft = x - boundingBoxWidth;
		float boundingBoxRight = x + 2 * Main.gridSize + boundingBoxWidth;
		float boundingBoxTop = y - boundingBoxHeight;
		float boundingBoxBottom = y + 4 * Main.gridSize;

		if (xvel < 0) {
			for (int i = 0; i < 4; i++) {
				if (gridAtLocation(boundingBoxLeft, boundingBoxTop + i * Main.gridSize) == 1) {
					// drawGrid(gridify(boundingBoxLeft),gridify(boundingBoxTop)+i);
					xvel = 0;
					x = (gridify(boundingBoxLeft) + 1) * Main.gridSize + boundingBoxWidth;
				}
			}
		} else if (xvel > 0) {
			for (int i = 0; i < 4; i++) {
				if (gridAtLocation(boundingBoxRight, boundingBoxTop + i * Main.gridSize) == 1) {
					// drawGrid(gridify(boundingBoxRight),gridify(boundingBoxTop)+i);
					xvel = 0;
					x = (gridify(boundingBoxRight) - 2) * Main.gridSize - boundingBoxWidth;
				}
			}
		}

		boundingBoxLeft = x - boundingBoxWidth;
		boundingBoxRight = x + 2 * Main.gridSize + boundingBoxWidth - 1;

		if (yvel <= 0) {
			onGround = false;
			if (gridAtLocation(boundingBoxLeft, boundingBoxBottom) == 1) {
				// drawGrid(gridify(boundingBoxLeft),gridify(boundingBoxBottom));
				yvel = 0;
				y = gridify(boundingBoxBottom) * Main.gridSize - 4 * Main.gridSize;
				onGround = true;
			} else if (gridAtLocation((boundingBoxLeft + boundingBoxRight) / 2, boundingBoxBottom) == 1) {
				// drawGrid(gridify((boundingBoxLeft+boundingBoxRight)/2),gridify(boundingBoxBottom));
				yvel = 0;
				y = gridify(boundingBoxBottom) * Main.gridSize - 4 * Main.gridSize;
				onGround = true;
			} else if (gridAtLocation(boundingBoxRight, boundingBoxBottom) == 1) {
				// drawGrid(gridify(boundingBoxRight),gridify(boundingBoxBottom));
				yvel = 0;
				y = gridify(boundingBoxBottom) * Main.gridSize - 4 * Main.gridSize;
				onGround = true;
			}
			if (gridAtLocation(boundingBoxLeft, boundingBoxBottom - Main.gridSize) == 1) {
				// drawGrid(gridify(boundingBoxLeft),gridify(boundingBoxBottom)-1);
				yvel = 0;
				y = gridify(boundingBoxBottom) * Main.gridSize - 5 * Main.gridSize;
				onGround = true;
			} else if (gridAtLocation((boundingBoxLeft + boundingBoxRight) / 2, boundingBoxBottom - Main.gridSize) == 1) {
				// drawGrid(gridify((boundingBoxLeft+boundingBoxRight)/2),gridify(boundingBoxBottom)-1);
				yvel = 0;
				y = gridify(boundingBoxBottom) * Main.gridSize - 5 * Main.gridSize;
				onGround = true;
			} else if (gridAtLocation(boundingBoxRight, boundingBoxBottom - Main.gridSize) == 1) {
				// drawGrid(gridify(boundingBoxRight),gridify(boundingBoxBottom)-1);
				yvel = 0;
				y = gridify(boundingBoxBottom) * Main.gridSize - 5 * Main.gridSize;
				onGround = true;
			}
		} else {

			if (gridAtLocation((boundingBoxLeft + boundingBoxRight) / 2, boundingBoxTop) == 1) {
				// drawGrid(gridify((boundingBoxLeft+boundingBoxRight)/2),gridify(boundingBoxTop));
				breakQuadBlock((boundingBoxLeft + boundingBoxRight) / 2, boundingBoxTop);
				yvel = 0;
				y = gridify(boundingBoxTop + Main.gridSize) * Main.gridSize + boundingBoxHeight;
			} else if (gridAtLocation(boundingBoxLeft, boundingBoxTop) == 1) {
				// drawGrid(gridify(boundingBoxLeft),gridify(boundingBoxTop));
				breakQuadBlock(boundingBoxLeft, boundingBoxTop);
				yvel = 0;
				y = gridify(boundingBoxTop + Main.gridSize) * Main.gridSize + boundingBoxHeight;
			} else if (gridAtLocation(boundingBoxRight, boundingBoxTop) == 1) {
				// drawGrid(gridify(boundingBoxRight),gridify(boundingBoxTop));
				breakQuadBlock(boundingBoxRight, boundingBoxTop);
				yvel = 0;
				y = gridify(boundingBoxTop + Main.gridSize) * Main.gridSize + boundingBoxHeight;
			}
		}

		/*
		 * fill(0,255,255); rectMode(CORNERS);
		 * rect(boundingBoxLeft,boundingBoxTop,boundingBoxRight,boundingBoxBottom);
		 * rectMode(CORNER);
		 */

	}

	void display() {
		pp.pushMatrix();

		pp.translate(-2, 8);
		pp.translate(x, y);

		pp.scale((float)0.78, (float)0.78);

		if (facingDirection == pp.LEFT) {
			if (movingLeftOrRight == false && onGround) {
				pp.translate(stillImg.width, 0);
			} else {
				pp.translate(walkAnimation.images[0].width, 0);
			}
			pp.scale(-1, 1);
		} else {
			if (movingLeftOrRight || !onGround) {
				pp.translate(-differenceBetweenTheSizeOfTheStillImgAndTheWalkingImg, 0);
			}
		}

		if (livingState == 1) {
			explodeAnimation.display(0, 5);
			if (explodeAnimation.doneRendering()) {
				resetMario(500, 525);
				new Main().loadLevel();
			}
		} else if (!onGround) {
			if (yvel < 0) {
				pp.image(fallImg, 0, 5);
			} else {
				pp.image(jumpImg, 0, 5);
			}
		} else if (movingLeftOrRight == false) {
			pp.image(stillImg, 0, 5);
		} else {
			walkAnimation.display(0, 5);
		}

		pp.popMatrix();
	}

	int differenceBetweenTheSizeOfTheStillImgAndTheWalkingImg = 0;
}
