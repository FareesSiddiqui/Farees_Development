import processing.core.*;
public class Animation {
	PImage[] images;
	int imageCount;
	int frame;
	int speed;
	protected final PApplet pp;

	Animation(String imagePrefix, String imageSuffix, int count, int s1, PApplet app) {
		imageCount = count;
		images = new PImage[imageCount];
		speed = s1;
		pp = app;

		for (int i = 0; i < imageCount; i++) {
			// Use nf() to number format 'i' into four digits
			String filename = imagePrefix + pp.nf(i, 4) + "." + imageSuffix;
			images[i] = pp.loadImage(filename);
		}
	}

	void displayStill(float xpos, float ypos) {
		pp.image(images[frame], xpos, ypos);
	}

	void displayFrame(int f, float xpos, float ypos) {
		pp.image(images[f], xpos, ypos);
	}

	void increaseFrame() {
		if (Main.frameCount % speed == 0)
			frame = (frame + 1) % imageCount;
	}

	void resetFrame() {
		frame = 0;
	}

	boolean doneRendering() {
		return (frame + 1 == imageCount);
	}

	void display(float xpos, float ypos) {
		if (Main.frameCount % speed == 0)
			frame = (frame + 1) % imageCount;
		pp.image(images[frame], xpos, ypos);
	}

	int getWidth() {
		return images[0].width;
	}
}
