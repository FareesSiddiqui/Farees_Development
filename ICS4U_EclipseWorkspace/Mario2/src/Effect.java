import processing.core.*;
class Effect {
	int x;
	int y;
	int xvel;
	int yvel;

	int animationId;

	int frame;
	
	public static PApplet pp;

	Effect(int a1, int x1, int y1, PApplet app) {
		animationId = a1;
		x = x1;
		y = y1;
		pp=app;
	}

	public static Animation blockBreak1;
	public static Animation blockBounce1;
	public static Animation GoombaDying;

	public static PImage[] quadTextures = new PImage[3];

	public static void initEffects() {
		blockBreak1 = new Animation("Animations/MarioBlockBreak1/", "png", 33, 1, pp);
		blockBounce1 = new Animation("Animations/MarioBlockBounce/", "png", 10, 1, pp);
		GoombaDying = new Animation("Animations/GoombaDying/", "png", 50, 1, pp);

		quadTextures[0] = pp.loadImage("Blocks/Block1.gif");
		quadTextures[1] = pp.loadImage("Blocks/Block4.gif");
		quadTextures[2] = pp.loadImage("Blocks/Block100.gif");
	}

	void display() {

		if (animationId == 1) {
			blockBreak1.displayFrame(frame, x, y);
		} else if (animationId == 2) {
			blockBounce1.displayFrame(frame, x, y);
		} else if (animationId == 3) {
			GoombaDying.displayFrame(frame, x, y);
		}
		frame++;
	}

	boolean doneRendering() {
		int iC = 0;
		if (animationId == 1) {
			iC = blockBreak1.imageCount;
		} else if (animationId == 2) {
			iC = blockBounce1.imageCount;
		} else if (animationId == 3) {
			iC = GoombaDying.imageCount;
		}
		return (frame + 1 == iC);
	}
}