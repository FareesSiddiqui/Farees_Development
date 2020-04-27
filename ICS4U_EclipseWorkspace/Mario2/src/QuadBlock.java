import processing.core.*;
public class QuadBlock {
	int x;
	int y;

	int blockType;

	int disappearTime;
	
	protected final PApplet pp;
	
	QuadBlock(float x1, float y1, int t1, PApplet app) {
		x = (int) x1;
		y = (int) y1;
		blockType = t1;
		disappearTime = 0;
		pp = app;
	}

	void display() {
		if (blockType == 0 || disappearTime > 0) {
			// rect(x, y, gridSize*2, gridSize*2);
			disappearTime--;
		} else if (blockType >= 100) {
			pp.image(Effect.quadTextures[2], x, y);
		} else if (blockType == 2 || blockType == 1) {
			pp.image(Effect.quadTextures[0], x, y);
		} else if (blockType == 4) {
			pp.image(Effect.quadTextures[1], x, y);
		}
	}

}
